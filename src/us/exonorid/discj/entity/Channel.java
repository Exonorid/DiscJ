package us.exonorid.discj.entity;

import us.exonorid.discj.Client;

public class Channel implements Snowflake {
	private Client client;
	private String id;
	private ChannelType type;
	
	protected Channel(Client client, String id, ChannelType type) {
		this.client = client;
		this.id = id;
		this.type = type;
	}
	
	@Override
	public String getID() {
		return id;
	}
	
	protected Client getClient() {
		return client;
	}
}
