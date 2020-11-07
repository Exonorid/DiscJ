package us.exonorid.discj.entity;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;
import org.json.JSONObject;
import us.exonorid.discj.TimeUtil;

import java.time.OffsetDateTime;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Member extends User {
	private List<Role> roles;
	private String nick;
	private OffsetDateTime premiumSince, joinedAt;
	private boolean deaf, mute;
	
	public Member(JSONObject json) {
		this(json, json.getJSONObject("user"));
	}
	
	public Member(@Nullable JSONObject memberJson, JSONObject user) {
		super(user);
		if(memberJson != null) {
			roles = new LinkedList<>();
			for (Object role : memberJson.getJSONArray("roles"))
				roles.add(new Role((String) role));
			if(memberJson.has("nick")) nick = memberJson.getString("nick");
			if(!memberJson.isNull("premium_since")) premiumSince = TimeUtil.getTimeFromISO(memberJson.getString("premium_since"));
			joinedAt = TimeUtil.getTimeFromISO(memberJson.getString("joined_at"));
			deaf = memberJson.getBoolean("deaf");
			mute = memberJson.getBoolean("mute");
		}
	}
	
	public String getEffectiveName() {
		return nick == null ? username : nick;
	}
}
