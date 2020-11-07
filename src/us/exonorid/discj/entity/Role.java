package us.exonorid.discj.entity;

public class Role implements Snowflake {
	String id;
	
	public Role(String id) {
		this.id = id;
	}
	
	@Override
	public String getID() {
		return id;
	}
}
