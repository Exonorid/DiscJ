package us.exonorid.discj.entity;

import org.json.JSONObject;
import us.exonorid.discj.Client;

public class EntityUtils {
	public static TextChannel getTextChannel(Client client, String id) {
		JSONObject json = new JSONObject(client.get("/channels/" + id));
		
		switch(ChannelType.values()[json.getInt("type")]) {
			case GUILD_TEXT:
				return new GuildTextChannel(client, json);
			case DM:
			case GROUP_DM:
			case GUILD_NEWS:
			case GUILD_STORE:
			default:
				System.err.println("Not a valid text channel type!");
				return null;
		}
	}
}
