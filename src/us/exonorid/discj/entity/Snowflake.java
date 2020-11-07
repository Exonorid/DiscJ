package us.exonorid.discj.entity;

public interface Snowflake {
	default long getIDLong() {
		return Long.parseUnsignedLong(getID());
	}
	
	String getID();
}
