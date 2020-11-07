package us.exonorid.discj.presence;

public enum ActivityFlag {
	INSTANCE, JOIN, SPECTATE, JOIN_REQUEST, SYNC, PLAY;
	
	public final int value = 1 << this.ordinal();
}
