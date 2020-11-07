package us.exonorid.discj.entity;

import org.json.JSONObject;
import us.exonorid.discj.Client;
import us.exonorid.discj.TimeUtil;

import java.time.OffsetDateTime;
import java.util.LinkedList;
import java.util.List;

public class Message implements Snowflake {
	//region Enums
	enum MessageType {
		DEFAULT, RECIPIENT_ADD, RECIPIENT_REMOVE, CALL, CHANNEL_NAME_CHANGE, CHANNEL_ICON_CHANGE, CHANNEL_PINNED_MESSAGE,
		GUILD_MEMBER_JOIN, USER_PREMIUM_GUILD_SUBSCRIPTION, USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_1,
		USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_2, USER_PREMIUM_GUILD_SUBSCRIPTION_TIER_3, CHANNEL_FOLLOW_ADD, GUILD_DISCOVERY_DISQUALIFIED,
		GUILD_DISCOVERY_REQUALIFIED
	}
	
	enum MessageFlag {
		CROSSPOSTED, IS_CROSSPOST, SUPPRESS_EMBEDS, SOURCE_MESSAGE_DELETED, URGENT;
		
		public int value = 1 << ordinal();
		
		public List<MessageFlag> getFlags(int flags) {
			List<MessageFlag> flagList = new LinkedList<>();
			for(MessageFlag flagTest : values())
				if((flags & flagTest.value) != 0)
					flagList.add(flagTest);
			return flagList;
		}
		
		public boolean hasFlag(int flags, MessageFlag test) {
			return (flags & test.value) != 0;
		}
	}
	//endregion
	
	//region Containers
	static class ChannelMention implements Snowflake {
		private String id;
		private String name;
		private ChannelType type;
		private Guild guild;
		
		public ChannelMention(JSONObject json, Client c) {
			id = json.getString("id");
			name = json.getString("name");
			type = ChannelType.values()[json.getInt("type")];
			guild = new Guild(json.getString("guild_id"), c);
		}
		
		@Override
		public String getID() {
			return id;
		}
	}
	//endregion
	
	//region Constants
	public static final String ITALICS = "^\\b_((?:__|\\\\[\\s\\S]|[^\\\\_])+?)_\\b|^\\*(?=\\S)((?:\\*\\*|\\\\[\\s\\S]|\\s+(?:\\\\[\\s\\S]|[^\\s\\*\\\\]|\\*\\*)|[^\\s\\*\\\\])+?)\\*(?!\\*)";
	public static final String BOLD = "^\\*\\*((?:\\\\[\\s\\S]|[^\\\\])+?)\\*\\*(?!\\*)";
	public static final String UNDERLINE = "^__((?:\\\\[\\s\\S]|[^\\\\])+?)__(?!_)";
	//endregion
	
	//region Fields
	private String id;
	private TextChannel channel;
	private Guild guild;
	private User author;
	private String content;
	private OffsetDateTime timestamp, editedTimestamp;
	private boolean tts, mentionsEveryone, pinned;
	private List<User> mentioned;
	private List<Role> mentionedRoles;
	private List<ChannelMention> mentionedChannels;
	private List<Attachment> attachments;
	private List<Embed> embeds;
	private List<Reaction> reactions;
	private String webhookID;
	private MessageType type;
	private int flags;
	//endregion
	
	public Message(Client client, JSONObject json) {
		id = json.getString("id");
		channel = new TextChannel(client, json.getString("channel_id"));
		if(json.has("guild_id")) guild = new Guild(json.getString("guild_id"), client);
		if(json.has("member"))
			author = new Member(json.getJSONObject("member"), json.getJSONObject("author"));
		else
			author = new User(json.getJSONObject("author"));
		content = json.getString("content");
		timestamp = TimeUtil.getTimeFromISO(json.getString("timestamp"));
		if(!json.isNull("edited_timestamp")) editedTimestamp = TimeUtil.getTimeFromISO(json.getString("edited_timestamp"));
		tts = json.getBoolean("tts");
		mentionsEveryone = json.getBoolean("mention_everyone");
		pinned = json.getBoolean("pinned");
		mentioned = new LinkedList<>();
		mentionedRoles = new LinkedList<>();
		mentionedChannels = new LinkedList<>();
		attachments = new LinkedList<>();
		embeds = new LinkedList<>();
		reactions = new LinkedList<>();
		for(Object mention : json.getJSONArray("mentions"))
			mentioned.add(new User((JSONObject) mention));
		for(Object role : json.getJSONArray("mention_roles"))
			mentionedRoles.add(new Role((String) role));
		if(json.has("mentioned_channels"))
			for(Object channel : json.getJSONArray("mention_channels"))
				mentionedChannels.add(new ChannelMention((JSONObject) channel, client));
		for(Object attachment : json.getJSONArray("attachments"))
			attachments.add(new Attachment((JSONObject) attachment));
		for(Object embed : json.getJSONArray("embeds"))
			embeds.add(new Embed((JSONObject) embed));
		if(json.has("reactions"))
			for(Object reaction : json.getJSONArray("reactions"))
				reactions.add(new Reaction((JSONObject) reaction));
		pinned = json.getBoolean("pinned");
		if(json.has("webhook_id")) webhookID = json.getString("webhook_id");
		type = MessageType.values()[json.getInt("type")];
		if(json.has("flags")) flags = json.getInt("flags");
	}
	
	//region Methods
	public void addReaction(String emoji) {
		
	}
	//endregion
	
	//region Getters
	@Override
	public String getID() {
		return id;
	}
	
	public boolean isWebhook() {
		return webhookID == null;
	}
	
	public User getAuthor() {
		return author;
	}
	
	public Member getMember() {
		return author instanceof Member ? (Member) author : null;
	}
	
	public TextChannel getChannel() {
		return channel;
	}
	
	public String getContentRaw() {
		return content;
	}
	
	public String getContentDisplay() {
		return content.replaceAll("~~","").replaceAll("`{3}.*\n", "")
				.replaceAll("^\\s{0,3}>\\s?", "")
				.replaceAll("([\\*_]{1,3})(\\S.*?\\S?)\\1","")
				.replaceAll("([\\*_]{1,3})(\\S.*?\\S?)\\1","").replaceAll("`(.+?)`","")
				.replaceAll("(`{3,})(.*?)\\1","");
	}
	
	public List<Attachment> getAttachments() {
		return attachments;
	}
	
	public Guild getGuild() {
		return guild;
	}
	
	public OffsetDateTime getTimeCreated() {
		return timestamp;
	}
	//endregion
}
