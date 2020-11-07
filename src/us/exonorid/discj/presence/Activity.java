package us.exonorid.discj.presence;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class Activity {
	//region Containers
	public static class Timestamps {
		long start = -1;
		long end = -1;
		
		public Timestamps (JSONObject json) {
			if(!json.isNull("start")) start = json.getLong("start");
			if(!json.isNull("end")) end = json.getLong("end");
		}
		
		public long getStart() {
			return start;
		}
		
		public long getEnd() {
			return end;
		}
	}
	
	public static class Emoji {
		String name;
		long id = -1;
		boolean animated = false;
		
		public Emoji(JSONObject json) {
			name = json.getString("name");
			if(!json.isNull("id")) id = json.getLong("id");
			if(!json.isNull("animated")) animated = json.getBoolean("animated");
		}
		
		public String getName() {
			return name;
		}
		
		public long getID() {
			return id;
		}
		
		public boolean getAnimated() {
			return animated;
		}
	}
	
	public static class Party {
		String id = null;
		int[] size = null;
		
		public Party(JSONObject json) {
			if(!json.isNull("id")) id = json.getString("id");
			if(!json.isNull("size")) {
				size = new int[2];
				JSONArray arr = json.getJSONArray("size");
				size[0] = arr.getInt(0);
				size[1] = arr.getInt(1);
			}
		}
		
		public String getID() {
			return id;
		}
		
		public int[] getSize() {
			return size;
		}
	}
	
	public static class Assets {
		String largeImg;
		String largeTxt;
		String smallImg;
		String smallTxt;
		
		public Assets(JSONObject json) {
			if(!json.isNull("large_image")) largeImg = json.getString("large_image");
			if(!json.isNull("large_text")) largeTxt = json.getString("large_text");
			if(!json.isNull("small_image")) smallImg = json.getString("small_image");
			if(!json.isNull("small_text")) smallTxt = json.getString("small_text");
		}
		
		public String getlargeImage() {
			return largeImg;
		}
		
		public String getLargeImageText() {
			return largeTxt;
		}
		
		public String getSmallImage() {
			return smallImg;
		}
		
		public String getSmallImageText() {
			return smallTxt;
		}
	}
	
	public static class Secrets {
		String join;
		String spectate;
		String match;
		
		public Secrets(JSONObject json) {
			if(!json.isNull("join")) join = json.getString("join");
			if(!json.isNull("spectate")) spectate = json.getString("spectate");
			if(!json.isNull("match")) match = json.getString("match");
		}
		
		public String getJoinSecret() {
			return join;
		}
		
		public String getSpectateSecret() {
			return spectate;
		}
		
		public String getMatchSecret() {
			return match;
		}
	}
	//endregion
	
	//region Fields
	String name;
	ActivityType type;
	String url = "";
	long createdAt;
	Timestamps timestamps;
	long app_id;
	String details = "";
	String state = "";
	Emoji emoji;
	Party party;
	Assets assets;
	Secrets gameSecrets;
	boolean instanced;
	int flags;
	//endregion
	
	//region Initializers
	public Activity(String name, ActivityType type) {
		this.name = name;
		this.type = type;
	}
	
	protected Activity(JSONObject json) {
		name = json.getString("name");
		type = ActivityType.values()[json.getInt("type")];
		if(!json.isNull("url")) url = json.getString("url");
		createdAt = json.getLong("created_at");
		if(!json.isNull("timestamps")) timestamps = new Timestamps(json.getJSONObject("timestamps"));
		if(!json.isNull("application_id")) app_id = json.getLong("application_id");
		if(!json.isNull("details")) details = json.getString("details");
		if(!json.isNull("state")) state = json.getString("state");
		if(!json.isNull("emoji")) emoji = new Emoji(json.getJSONObject("emoji"));
		if(!json.isNull("party")) party = new Party(json.getJSONObject("party"));
		if(!json.isNull("assets")) assets = new Assets(json.getJSONObject("assets"));
		if(!json.isNull("secrets")) gameSecrets = new Secrets(json.getJSONObject("secrets"));
		if(!json.isNull("instance")) instanced = json.getBoolean("instance");
		if(!json.isNull("flags")) flags = json.getInt("flags");
	}
	
	public Activity setURL(String streamURL) {
		url = streamURL;
		return this;
	}
	//endregion
	
	//region Getters
	public String getName() {
		return name;
	}
	
	public ActivityType getType() {
		return type;
	}
	
	public String getURL() {
		return url;
	}
	
	public long getCreationTime() {
		return createdAt;
	}
	
	public Timestamps getTimestamps() {
		return timestamps;
	}
	
	public long getApplicationID() {
		return app_id;
	}
	
	public String getDetails() {
		return details;
	}
	
	public String getState() {
		return state;
	}
	
	public Emoji getEmoji() {
		return emoji;
	}
	
	public Party getParty() {
		return party;
	}
	
	public Assets getAssets() {
		return assets;
	}
	
	public Secrets getSecrets() {
		return gameSecrets;
	}
	
	public boolean isInstanced() {
		return instanced;
	}
	
	public List<ActivityFlag> getFlags() {
		List<ActivityFlag> flags = new LinkedList<ActivityFlag>();
		for(ActivityFlag flag : ActivityFlag.values()) {
			if ((this.flags & flag.value) != 0)
				flags.add(flag);
		}
		return flags;
	}
	//endregion
	
	//region Serialize
	public JSONObject serialize() {
		JSONObject obj = new JSONObject();
		obj.put("name", name);
		obj.put("type", type);
		if(url != "") obj.put("url", url);
		return obj;
	}
	//endregion
}
