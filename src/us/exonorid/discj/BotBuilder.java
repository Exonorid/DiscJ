package us.exonorid.discj;

import java.util.LinkedList;
import java.util.List;

public class BotBuilder {
	private final String token;
	private short intents;
	private List<MessageListener> listeners;
	
	private BotBuilder(String token) {
		this.token = token;
		listeners = new LinkedList<>();
	}
	public static BotBuilder create(String token) {
		return new BotBuilder(token);
	}
	
	public BotBuilder addIntents(GatewayIntent... intents) {
		for(GatewayIntent intent : intents) {
			this.intents |= intent.value;
		}
		return this;
	}
	
	public BotBuilder removeIntents(GatewayIntent... intents) {
		for(GatewayIntent intent : intents) {
			this.intents &= ~intent.value;
		}
		return this;
	}
	
	public BotBuilder addListener(MessageListener listener) {
		listeners.add(listener);
		return this;
	}
	
	public BotBuilder removeListener(MessageListener listener) {
		listeners.remove(listener);
		return this;
	}
	
	public Bot build() {
		return new Bot(token, intents, listeners);
	}
}
