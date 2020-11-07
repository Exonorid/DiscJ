package us.exonorid.discj.presence;

import org.json.JSONObject;

public class Status {
	//region Fields
	Long since;
	Activity activity;
	StatusType type;
	boolean afk;
	//endregion
	
	//region Constructors
	public Status(Long since, Activity game, StatusType type, boolean afk) {
		this.since = since;
		activity = game;
		this.type = type;
		this.afk = afk;
	}
	
	protected Status(JSONObject json) {
		if(!json.isNull("since")) since = json.getLong("since");
		if(!json.isNull("game")) activity = new Activity(json.getJSONObject("game"));
		String typeString = json.getString("status");
		for(StatusType checkType : StatusType.values()) {
			if(typeString.contentEquals(checkType.label)) {
				type = checkType;
				break;
			}
		}
		afk = json.getBoolean("afk");
	}
	//endregion
	
	//region Serialize
	public String serialize() {
		JSONObject obj = new JSONObject();
		obj.put("since", since);
		obj.put("game", activity.serialize());
		obj.put("status", type.label);
		obj.put("afk", afk);
		return obj.toString();
	}
	//endregion
}
