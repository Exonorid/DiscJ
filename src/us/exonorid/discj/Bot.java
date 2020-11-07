package us.exonorid.discj;

import us.exonorid.discj.presence.Status;

import java.util.List;

public class Bot {
	Client client;
	
	protected Bot(String token, short intents, List<MessageListener> listeners) {
		client = new Client(Util.getGateway(token), token, intents, listeners);
		client.connect();
	}
	
	public void addListener(MessageListener listener) {
		client.addListener(listener);
	}
	
	public void removeListener(MessageListener listener) {
		client.removeListener(listener);
	}
	
	public void setStatus(Status status) {
	client.setStatus(status);
	}
}
