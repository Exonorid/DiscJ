package us.exonorid.discj.entity;

import org.json.JSONObject;
import us.exonorid.discj.Client;

public class GuildTextChannel extends TextChannel implements GuildChannel {
	private Guild guild;
	private Category category;
	private String name;
	private String topic;
	private boolean nsfw;
	private int rateLim;
	private int position;
	
	protected GuildTextChannel(Client client, JSONObject json) {
		super(client, json.getString("id"), ChannelType.GUILD_TEXT);
		guild = new Guild(json.getString("guild_id"), client);
		name = json.getString("name");
		if(!json.isNull("topic")) topic = json.getString("topic");
		nsfw = json.getBoolean("nsfw");
		setLastMessageID(json.getString("last_message_id"));
		rateLim = json.getInt("rate_limit_per_user");
		position = json.getInt("position");
		if(!json.isNull("parent_id")) category = new Category(client, json.getString("parent_id"), guild);
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
	
	public String getTopic() {
		return topic;
	}
	
	public boolean isNsfw() {
		return nsfw;
	}
	
	public int getRateLimit() {
		return rateLim;
	}
	
	@Override
	public int getPosition() {
		return position;
	}
	//endregion
}
