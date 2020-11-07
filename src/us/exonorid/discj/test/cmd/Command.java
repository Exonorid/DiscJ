package us.exonorid.discj.test.cmd;

import us.exonorid.discj.entity.Message;

import java.util.List;

public abstract class Command {
	
	public List<String> getAliases() {
		return null;
	}
	
	public String getUsage() {
		return "If you're seeing this, that means I haven't set up usage text :/";
	}
	
	public abstract boolean run(Message msg, String content);
}