package us.exonorid.discj.entity;

import org.json.JSONObject;
import us.exonorid.discj.Client;

public class Category extends Channel implements GuildChannel {
	private Guild guild;
	private String name;
	private int position;
	
	protected Category(Client client, JSONObject json) {
		super(client, json.getString("id"), ChannelType.GUILD_CATEGORY);
		guild = new Guild(json.getString("guild_id"), client);
	}
	
	protected Category(Client client, String id, Guild guild) {
		super(client, id, ChannelType.GUILD_CATEGORY);
		this.guild = guild;
	}
	
	//region Getters
	@Override
	public Guild getGuild() {
		return guild;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public int getPosition() {
		return position;
	}
	//endregion
}
