package us.exonorid.discj.entity;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Emoji implements Snowflake {
	private String id;
	private String name;
	private List<Role> roles;
	private User creator;
	private boolean requireColons, managed, animated, available;
	
	public Emoji(JSONObject json) {
		this.id = json.getString("id");
		this.name = json.getString("name");
		if(json.has("roles")) {
			roles = new LinkedList<>();
			for(Object role : json.getJSONArray("roles"))
				roles.add(new Role((String) role));
		}
		if(json.has("user")) creator = new User(json.getJSONObject("user"));
		if(json.has("require_colons")) requireColons = json.getBoolean("require_colons");
		if(json.has("managed")) managed = json.getBoolean("managed");
		if(json.has("animated")) animated = json.getBoolean("animated");
		if(json.has("available")) available = json.getBoolean("available");
	}
	
	@Override
	public String getID() {
		return id;
	}
}
