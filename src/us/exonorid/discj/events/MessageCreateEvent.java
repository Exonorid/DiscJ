package us.exonorid.discj.events;

import us.exonorid.discj.entity.Guild;
import us.exonorid.discj.entity.Message;
import us.exonorid.discj.entity.TextChannel;
import us.exonorid.discj.entity.User;

public class MessageCreateEvent {
	Message message;
	
	public MessageCreateEvent(Message m) {
		message = m;
	}
	
	public Message getMessage() {
		return message;
	}
	
	public User getAuthor() {
		return message.getAuthor();
	}
	
	public TextChannel getChannel() {
		return message.getChannel();
	}
	
	public Guild getGuild() {
		return message.getGuild();
	}
}
