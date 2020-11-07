package us.exonorid.discj.entity;

import org.json.JSONObject;
import us.exonorid.discj.Client;

public class Guild implements Snowflake {
	String id;
	String name;
	Client client;
	
	public Guild(JSONObject json) {
		id = json.getString("id");
		name = json.getString("name");
	}
	
	protected Guild(String id, Client client) {
		this.id = id;
	}
	
	protected void resolve() {
		JSONObject json = new JSONObject(client.get("/guilds/" + id));
		name = json.getString("name");
	}
	
	@Override
	public String getID() {
		return id;
	}
	
	public String getName() {
		if(name == null) resolve();
		return name;
	}
}
