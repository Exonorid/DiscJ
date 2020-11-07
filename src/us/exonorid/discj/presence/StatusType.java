package us.exonorid.discj.presence;

public enum StatusType {
	ONLINE("online"), DO_NOT_DISTURB("dnd"), IDLE("idle"), INVISIBLE("invisible"), OFFLINE("offline");
	
	public final String label;
	
	private StatusType(String label) {
		this.label = label;
	}
}
