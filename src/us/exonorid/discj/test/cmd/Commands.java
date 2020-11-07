package us.exonorid.discj.test.cmd;

import us.exonorid.discj.Bot;
import us.exonorid.discj.entity.Message;

public class Commands {
	public static final String PREFIX = "$";
	public Command[] cmds;
	
	public Commands(Bot bot) {
		cmds = new Command[1];
		cmds[0] = new Debug();
	}
	
	public Command getCmdByName(String name) {
		for (Command cmd : cmds) {
			if(cmd.getAliases().contains(name.toLowerCase()))
				return cmd;
		}
		return null;
	}
	
	public boolean handleCmd(Message msg) {
		String content = msg.getContentRaw();
		int cmdEndI = content.indexOf(' ');
		if(cmdEndI == -1)
			cmdEndI = content.length();
		String cmdName = content.substring(PREFIX.length(), cmdEndI);
		Command cmd = getCmdByName(cmdName);
		if(cmd == null) return false;
		return cmd.run(msg, msg.getContentRaw());
	}
}
