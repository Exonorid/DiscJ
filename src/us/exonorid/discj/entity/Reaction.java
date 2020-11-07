package us.exonorid.discj.entity;

import org.json.JSONObject;

public class Reaction {
	private int count;
	private boolean me;
	private Emoji emoji;
	
	public Reaction(JSONObject json) {
		count = json.getInt("count");
		me = json.getBoolean("me");
		emoji = new Emoji(json.getJSONObject("emoji"));
	}
}
