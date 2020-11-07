package us.exonorid.discj.entity;

import org.json.JSONArray;
import org.json.JSONObject;
import us.exonorid.discj.TimeUtil;

import java.awt.*;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

public class Embed {
	//region Enums
	enum EmbedType {
		RICH("rich"), IMAGE("rich"), VIDEO("video"), GIFV("gifv"), ARTICLE("article"), LINK("link");
		
		public String string;
		
		EmbedType(String name) { string = name; }
		
		public static EmbedType getType(String name) {
			for(EmbedType type : values())
				if(type.string.contentEquals(name))
					return type;
			return null;
		}
	}
	//endregion
	
	//region Containers
	static class EmbedFooter {
		private String text;
		private String iconUrl, proxyIconUrl;
		
		public EmbedFooter(JSONObject json) {
			text = json.getString("text");
			iconUrl = !json.isNull("icon_url") ? json.getString("icon_url") : null;
			proxyIconUrl = !json.isNull("proxy_icon_url") ? json.getString("proxy_icon_url") : null;
		}
		
		public EmbedFooter(String text, String iconUrl, String proxyIconUrl) {
			this.text = text;
			this.iconUrl = iconUrl;
			this.proxyIconUrl = proxyIconUrl;
		}
		
		public EmbedFooter setText(String text) {
			this.text = text;
			return this;
		}
		
		public EmbedFooter setIconUrl(String iconUrl) {
			this.iconUrl = iconUrl;
			return this;
		}
		
		public EmbedFooter setProxyIconUrl(String proxyIconUrl) {
			this.proxyIconUrl = proxyIconUrl;
			return this;
		}
		
		public JSONObject serialize() {
			JSONObject obj = new JSONObject().put("text", text);
			if(iconUrl != null) obj.put("icon_url", iconUrl);
			if(proxyIconUrl != null) obj.put("proxy_icon_url", proxyIconUrl);
			return obj;
		}
	}
	
	static class EmbedImage {
		private String url, proxyUrl;
		private Integer width, height;
		
		public EmbedImage(String url, String proxyUrl, int width, int height) {
			this.url = url;
			this.proxyUrl = proxyUrl;
			this.width = width;
			this.height = height;
		}
		
		public EmbedImage(JSONObject json) {
			if(json.has("url")) url = json.getString("url");
			if(json.has("proxy_url")) proxyUrl = json.getString("proxy_url");
			if(json.has("width")) width = json.getInt("width");
			if(json.has("height")) height = json.getInt("height");
		}
		
		public JSONObject serialize() {
			JSONObject obj = new JSONObject();
			if(url != null) obj.put("url", url);
			if(proxyUrl != null) obj.put("proxy_url", proxyUrl);
			if(width != null) obj.put("width", width);
			if(height != null) obj.put("height", height);
			return obj;
		}
	}
	
	static class EmbedThumbnail {
		private String url, proxyUrl;
		private Integer width, height;
		
		public EmbedThumbnail(String url, String proxyUrl, int width, int height) {
			this.url = url;
			this.proxyUrl = proxyUrl;
			this.width = width;
			this.height = height;
		}
		
		public EmbedThumbnail(JSONObject json) {
			if(json.has("url")) url = json.getString("url");
			if(json.has("proxy_url")) proxyUrl = json.getString("proxy_url");
			if(json.has("width")) width = json.getInt("width");
			if(json.has("height")) height = json.getInt("height");
		}
		
		public JSONObject serialize() {
			JSONObject obj = new JSONObject();
			if(url != null) obj.put("url", url);
			if(proxyUrl != null) obj.put("proxy_url", proxyUrl);
			if(width != null) obj.put("width", width);
			if(height != null) obj.put("height", height);
			return obj;
		}
	}
	
	static class EmbedVideo {
		private String url;
		private Integer width, height;
		
		public EmbedVideo(String url, int width, int height) {
			this.url = url;
			this.width = width;
			this.height = height;
		}
		
		public EmbedVideo(JSONObject json) {
			if(json.has("url")) url = json.getString("url");
			if(json.has("width")) width = json.getInt("width");
			if(json.has("height")) height = json.getInt("height");
		}
		
		public JSONObject serialize() {
			JSONObject obj = new JSONObject();
			if(url != null) obj.put("url", url);
			if(width != null) obj.put("width", width);
			if(height != null) obj.put("height", height);
			return obj;
		}
	}
	
	static class EmbedProvider {
		private String name, url;
		
		public EmbedProvider(String name, String url) {
			this.name = name;
			this.url = url;
		}
		
		public EmbedProvider(JSONObject json) {
			if(json.has("name")) name = json.getString("name");
			if(json.has("url")) url = json.getString("url");
		}
		
		public JSONObject serialize() {
			JSONObject obj = new JSONObject();
			if(name != null) obj.put("name", name);
			if(url != null) obj.put("url", url);
			return obj;
		}
	}
	
	static class EmbedAuthor {
		private String name, url, iconUrl, proxyIconUrl;
		
		public EmbedAuthor(String name, String url, String iconUrl, String proxyIconUrl) {
			this.name = name;
			this.url = url;
			this.iconUrl = iconUrl;
			this.proxyIconUrl = proxyIconUrl;
		}
		
		public EmbedAuthor(JSONObject json) {
			if(json.has("name")) name = json.getString("name");
			if(json.has("url")) url = json.getString("url");
			if(json.has("icon_url")) iconUrl = json.getString("icon_url");
			if(json.has("proxy_icon_url")) proxyIconUrl = json.getString("proxy_icon_url");
		}
		
		public JSONObject serialize() {
			JSONObject obj = new JSONObject();
			if(name != null) obj.put("name", name);
			if(url != null) obj.put("url", url);
			if(iconUrl != null) obj.put("icon_url", iconUrl);
			if(proxyIconUrl != null) obj.put("proxy_icon_url", proxyIconUrl);
			return obj;
		}
	}
	
	static class EmbedField {
		private String name, value;
		private boolean inline;
		
		public EmbedField(String name, String value, boolean inline) {
			this.name = name;
			this.value = value;
			this.inline = inline;
		}
		
		public EmbedField(JSONObject json) {
			name = json.getString("name");
			value = json.getString("value");
			if(json.has("inline")) inline = json.getBoolean("inline");
		}
		
		public JSONObject serialize() {
			JSONObject obj = new JSONObject().put("name", name).put("value", value);
			if(inline) obj.put("inline", true);
			return obj;
		}
	}
	//endregion
	
	//region Constants
	public static final int DEFAULT_COLOR = 5198940;
	//endregion
	
	//region Fields
	private String title;
	private EmbedType type;
	private String description;
	private String url;
	private OffsetDateTime timestamp;
	private int color = DEFAULT_COLOR;
	private EmbedFooter footer;
	private EmbedImage image;
	private EmbedThumbnail thumbnail;
	private EmbedVideo video;
	private EmbedProvider provider;
	private EmbedAuthor author;
	private List<EmbedField> fields;
	//endregion
	
	public Embed(JSONObject json) {
		fields = new LinkedList<>();
		if(json.has("title")) title = json.getString("title");
		if(json.has("type")) type = EmbedType.getType(json.getString("type"));
		if(json.has("description")) description = json.getString("description");
		if(json.has("url")) url = json.getString("url");
		if(json.has("timestamp")) timestamp = TimeUtil.getTimeFromISO(json.getString("timestamp"));
		if(json.has("color")) color = json.getInt("color");
		if(json.has("footer")) footer = new EmbedFooter(json.getJSONObject("footer"));
		if(json.has("image")) image = new EmbedImage(json.getJSONObject("image"));
		if(json.has("thumbnail")) thumbnail = new EmbedThumbnail(json.getJSONObject("thumbnail"));
		if(json.has("video")) video = new EmbedVideo(json.getJSONObject("video"));
		if(json.has("provider")) provider = new EmbedProvider(json.getJSONObject("provider"));
		if(json.has("title")) author = new EmbedAuthor(json.getJSONObject("author"));
		if(json.has("fields"))
			for(Object field : json.getJSONArray("fields"))
				fields.add(new EmbedField((JSONObject) field));
	}
	
	public Embed(String title, String description) {
		this.title = title;
		this.description = description;
		type = EmbedType.RICH;
		fields = new LinkedList<EmbedField>();
	}
	
	public Embed addField(String name, String value) {
		return addField(name, value, false);
	}
	
	public Embed addField(String name, String value, boolean inline) {
		fields.add(new EmbedField(name, value, inline));
		return this;
	}
	
	public Embed setTimestamp(OffsetDateTime timestamp) {
		this.timestamp = timestamp;
		return this;
	}
	
	public Embed setColor(Color color) {
		this.color = color == null ? DEFAULT_COLOR : color.getRGB() & 0xffffff;
		return this;
	}
	
	public JSONObject serialize() {
		JSONObject obj = new JSONObject();
		if(title != null) obj.put("title", title);
		if(description != null) obj.put("description", description);
		obj.put("type", type.string);
		if(fields.size() > 0) {
			System.out.println("Has fields");
			JSONArray fieldsArr = new JSONArray();
			for(EmbedField field : fields) {
				JSONObject serialized = field.serialize();
				System.out.println(serialized.toString());
				fieldsArr.put(serialized);
			}
			System.out.println(fieldsArr.toString());
			obj.put("fields", fieldsArr);
		}
		if(timestamp != null) obj.put("timestamp", timestamp.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));
		obj.put("color", color);
		return obj;
	}
}
