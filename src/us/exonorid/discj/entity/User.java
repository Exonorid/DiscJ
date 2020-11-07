package us.exonorid.discj.entity;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class User implements Snowflake {
	enum UserFlags {
		DISCORD_EMPLOYEE, DISCORD_PARTNER, HYPESQUAD, BUG_HUNTER_L1, HOUSE_BRAVERY, HOUSE_BRILLIANCE, HOUSE_BALANCE,
		EARLY_SUPPORTER, TEAM_USER, SYSTEM, BUG_HUNTER_L2, VERIFIED_BOT, VERIFIED_DEVELOPER;
		
		public int value = 1 << ordinal();
		
		public List<UserFlags> getFlags(int flags) {
			List<UserFlags> flagList = new LinkedList<>();
			for(UserFlags flagTest : values())
				if((flags & flagTest.value) != 0)
					flagList.add(flagTest);
			return flagList;
		}
		
		public boolean hasFlag(int flags, UserFlags test) {
			return (flags & test.value) != 0;
		}
	}
	
	String id;
	String username;
	String discrim;
	String avatar = null;
	boolean bot;
	boolean system;
	boolean mfa;
	String locale;
	boolean verified;
	String email;
	int flags;
	int premiumType;
	int publicFlags;
	
	public User(JSONObject json) {
		id = json.getString("id");
		username = json.getString("username");
		discrim = json.getString("discriminator");
		if(!json.isNull("avatar")) avatar = json.getString("avatar");
		bot = json.has("bot");
		if(!json.isNull("system")) system = json.getBoolean("system");
		if(!json.isNull("mfa_enabled")) mfa = json.getBoolean("mfa_enabled");
		if(!json.isNull("locale")) locale = json.getString("locale");
		if(!json.isNull("verified")) verified = json.getBoolean("verified");
		if(!json.isNull("email")) email = json.getString("email");
		if(!json.isNull("flags")) flags = json.getInt("flags");
		if(!json.isNull("premium_type")) premiumType = json.getInt("premium_type");
		if(!json.isNull("public_flags")) publicFlags = json.getInt("public_flags");
	}
	
	@Override
	public String getID() {
		return id;
	}
	
	public boolean isBot() {
		return bot;
	}
	
	public String getAsTag() {
		return username + "#" + discrim;
	}
	
	public String getAsMention() {
		return "<@!" + id + ">";
	}
}
