package us.exonorid.discj.entity;

import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import us.exonorid.discj.Client;

public class TextChannel extends Channel {
	private TextChannel self;
	private String lastMessageID;
	
	protected TextChannel(Client client, String id, ChannelType type) {
		super(client, id, type);
	}
	
	public TextChannel(Client client, String id) { super(client, id, ChannelType.GENERIC_TEXT_CHANNEL); }
	
	protected void setLastMessageID(String id) {
		lastMessageID = id;
	}
	
	public Message sendMessage(String content) {
		JSONObject response = new JSONObject(getClient().post("/channels/" + getID() + "/messages", "{\"content\":\"" + content + "\"}"));
		return new Message(getClient(), response);
	}
	
	public Message sendMessage(Embed embed) {
		String msgJson = "{\"embed\":" + embed.serialize().toString() + "}";
		System.out.println(msgJson);
		JSONObject response = new JSONObject(getClient().post("/channels/" + getID() + "/messages", msgJson));
		return new Message(getClient(), response);
	}
	
	@Nullable
	public Guild getGuild() {
		if(self == null)
			self = EntityUtils.getTextChannel(getClient(), getID());
		if(self instanceof GuildTextChannel)
			return self.getGuild();
		return null;
	}
	
	public String getLastMessageID() {
		if(lastMessageID == null) {
			if(self == null)
				self = EntityUtils.getTextChannel(getClient(), getID());
			lastMessageID = self.getLastMessageID();
		}
		return lastMessageID;
	}
	
	@Nullable
	public String getName() {
		if(self == null)
			self = EntityUtils.getTextChannel(getClient(), getID());
		if(self instanceof GuildChannel)
			return self.getName();
		return null;
	}
}
