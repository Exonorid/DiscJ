package us.exonorid.discj;

import com.roxstudio.utils.CUrl;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;
import us.exonorid.discj.entity.Message;
import us.exonorid.discj.events.MessageCreateEvent;
import us.exonorid.discj.presence.Status;

import java.net.URI;
import java.util.List;

//The main interface between the wrapper and the API
public class Client extends WebSocketClient {
    //Enum for packet opcodes
    enum PacketType {
        DISPATCH, HEARTBEAT, IDENTIFY, PRESENCE_UPDATE, VOICE_STATE_UPDATE, NULL, RESUME, RECONNECT, REQUEST_GUILD_MEMBERS, INVALID_SESSION, HELLO, ACK
    }
    
    //Keeps the connection alive
    class Heartbeater implements Runnable {
        private final int hbInterval;
        private boolean hbAck = true;

        public Heartbeater(int hbInterval) {
            this.hbInterval = hbInterval;
        }

        @Override
        public void run() {
            while(true) {
                if(!hbAck) {
                    System.err.println("Connection died!");
                    return;
                }
                hbAck = false;
                Client.this.sendHB();
                try {
                    Thread.sleep(hbInterval);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void ack() {
            hbAck = true;
        }
    }
    
    //Handles incoming packets
    class Handler implements Runnable {
        String message;
        
        public Handler(String message) {
            this.message = message;
        }
        
        @Override
        public void run() {
            Client client = Client.this;
            JSONObject json = new JSONObject(message);
            switch (PacketType.values()[json.getInt("op")]) {
                case DISPATCH: //Handle a dispatch event
                    client.lastSeq = json.getInt("s"); //Keep track of the latest sequence number
                    JSONObject data = json.getJSONObject("d");
                    switch (json.getString("t")) { //Type of event
                        case "READY": //The connection is ready
                            System.out.println(message);
                            client.setSelf(data.getJSONObject("user"));
                            for(MessageListener listener : client.listeners) {
                                listener.onReady();
                            }
                            break;
                        case "MESSAGE_CREATE": //A message was created
                            MessageCreateEvent event = new MessageCreateEvent(new Message(client, data));
                            System.out.println("Event created successfully!");
                            for(MessageListener listener : client.listeners) {
                                listener.onMessageCreated(event);
                            }
                    }
                case HELLO: //Hello
                    hb = new Heartbeater(json.getJSONObject("d").getInt("heartbeat_interval"));
                    hbThread = new Thread(hb, "Heartbeat");
                    hbThread.start(); //Start sending heartbeats
                    identify(); //Identify self to servers
                    break;
                case ACK: //HB acknowledgement
                    hb.ack();
                    //System.out.println("Pong!");
                    break;
            }
        }
    }
    
    //region Fields
    private final String token;
    private Thread hbThread;
    private Heartbeater hb;
    private int lastSeq = -1;
    private short intents = 0;
    private List<MessageListener> listeners;
    //endregion

    Client(URI uri, String token, short intents, List<MessageListener> listeners) {
        super(uri);
        this.token = token;
        this.intents = intents;
        this.listeners = listeners;
    }
    
    //region Internal
    //Sends a heartbeat
    private void sendHB() {
        send(1, (lastSeq == -1 ? "null" : "" + lastSeq));
        //System.out.println("Ping!");
    }
    
    //Gives the Discord servers info about the connection (i.e. the bot token, the connection properties, the initial presence, and intents)
    private void identify() {
        send(2,
                "{" +
                        "\"token\": \"" + token + "\"," +
                        "\"properties\":{" +
                            "\"$os\":\"windows\"," +
                            "\"$browser\":\"discj\"," +
                            "\"$device\":\"discj\"" +
                        "}," +
                        "\"presence\":{" +
                            "\"game\":null," +
                            "\"since\":null," +
                            "\"status\":\"online\"," +
                            "\"afk\":false" +
                        "}," +
                        "\"intents\":" + intents + "" +
                    "}");
    }
    
    //TODO: do this
    //Sets the self user
    private void setSelf(JSONObject info) {
    
    }
    //endregion
    
    //region External
    //Adds an event listener
    public void addListener(MessageListener listener) {
        listeners.add(listener);
    }
    
    //Removes an event listener
    public void removeListener(MessageListener listener) {
        listeners.remove(listener);
    }
    
    //Updates the bot user's status
    public void setStatus(Status status) {
        send(3, status.serialize());
    }
    //endregion
    
    //region Websocket Methods
    //Called when the connection is opened
    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        System.out.println("Connected to servers.");
    }

    //Called when a messages is received from the servers
    @Override
    public void onMessage(String s) {
        //System.out.println("Received: " + s);
        new Thread(new Handler(s)).run();
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }
    //endregion
    
    //region Requests
    //Sends a websocket packet
    private void send(int op, String d) {
        send("{\"op\":" + op + ", \"d\": " + d + "}");
    }
    
    //Logs the message and then passes it to the superclass method
    @Override
    public void send(String text) {
        //System.out.println("Sent: " + text);
        super.send(text);
    }
    
    //Sends a POST request
    public String post(String path, String body) {
        CUrl request = new CUrl("https://discord.com/api" + path).data(body).header("Authorization: Bot " + token).header("Content-Type: application/json");
        String result = new String(request.exec());
        int code = request.getHttpCode();
        //System.out.println("Response code: " + code);
        if(code != 200 && code != 201 && code != 204) {
            System.out.println(body);
            System.err.println(result);
        }
        return result;
    }

    //Sends a GET request
    public String get(String path) {
        return Util.get(path, token).getResponse();
    }
    //endregion
}
