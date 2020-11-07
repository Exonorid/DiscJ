package us.exonorid.discj.entity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

public class Attachment implements Snowflake {
	private String id;
	private String filename;
	private int size;
	private String url, proxyUrl;
	private Integer width, height;
	
	public Attachment(final @NotNull JSONObject json) {
		id = json.getString("id");
		filename = json.getString("filename");
		size = json.getInt("size");
		url = json.getString("url");
		proxyUrl = json.getString("proxy_url");
		width = !json.isNull("width") ? json.getInt("width") : null;
		height = !json.isNull("height") ? json.getInt("height") : null;
	}
	
	//region Getters
	@Override
	public String getID() {
		return id;
	}
	
	public String getFilename() {return filename;}
	
	public int getSize() { return size; }
	
	public String getURL() { return url; }
	
	public Integer getWidth() { return width; }
	
	public Integer getHeight() { return height; }
	//endregion
}
