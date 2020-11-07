package us.exonorid.discj.test.cmd;

import us.exonorid.discj.entity.Embed;
import us.exonorid.discj.entity.Member;
import us.exonorid.discj.entity.Message;
import us.exonorid.discj.entity.TextChannel;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class Debug extends Command {
	public String[] aliases = { "debug", "dbg" };
	
	@Override
	public List<String> getAliases() {
		return Arrays.asList(aliases);
	}
	
	@Override
	public String getUsage() {
		return "$debug: Prints out information about the message; only usable in debug mode.";
	}
	
	@Override
	public boolean run(Message msg, String content) {
		Member sender = msg.getMember();
		TextChannel ch = msg.getChannel();
		Embed embed = new Embed("Debug info:", "Requested by " + sender.getAsMention())
						.addField("Channel name: ", ch.getName())
						.addField("Channel ID: ", ch.getID())
						.addField("Sender: ", sender.getEffectiveName())
						.addField("Sender ID: ", sender.getID())
						.addField("Message Content: ", msg.getContentRaw())
						.setColor(new Color(0x19, 0x2b, 0xc2));
		ch.sendMessage(embed);
		return true;
	}
	
}
