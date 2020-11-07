package us.exonorid.discj.test;

import us.exonorid.discj.Bot;
import us.exonorid.discj.BotBuilder;
import us.exonorid.discj.GatewayIntent;
import us.exonorid.discj.MessageListener;
import us.exonorid.discj.entity.Guild;
import us.exonorid.discj.events.MessageCreateEvent;

import static us.exonorid.discj.GatewayIntent.*;

import java.util.Random;

public class DiscJTest {
	//region TOKEN
	private static final byte[] TOKEN_ENC = {
			0x78,
			0x3,
			0x2b,
			0x4c,
			0x36,
			0x70,
			0x30,
			0xc,
			0x7b,
			0x61,
			0x63,
			0x4d,
			0x28,
			0x2,
			0x13,
			0x0,
			0x19,
			0x6b,
			0x74,
			0x5d,
			0x79,
			0x2d,
			0x27,
			0x4c,
			0x56,
			0x6c,
			0x2e,
			0x72,
			0x5a,
			0x66,
			0x51,
			0x57,
			0x5f,
			0xd,
			0x11,
			0x76,
			0x67,
			0xe,
			0x45,
			0x11,
			0x0,
			0x2b,
			0x2d,
			0x3d,
			0x2f,
			0x41,
			0x65,
			0xf,
			0x2,
			0x69,
			0x5,
			0x2f,
			0x34,
			0xa,
			0x2d,
			0x78,
			0x3,
			0x45,
			0x42,
	};
	//endregion
	
	private static String getToken(String key) {
		String token = "";
		for(int i = 0; i < TOKEN_ENC.length; i++)
			token += (char) (TOKEN_ENC[i] ^ key.charAt(i % key.length()));
		return token;
	}
	
	public static void main(String[] args) {
        BotBuilder builder = BotBuilder.create(getToken(args[0]));
        builder.addIntents(GUILD_MESSAGES, DIRECT_MESSAGES);
        Bot bot = builder.build();
        bot.addListener(new TestListener());
	}
	
	static class TestListener extends MessageListener {
		@Override
		public void onMessageCreated(MessageCreateEvent event) {
			System.out.println("Message received!");
			Guild g = event.getGuild();
			if (event.getAuthor().isBot() || g == null) return;
			System.out.println("Guild is valid!");
			if (g.getIDLong() != 665713137244307471L) return;
			System.out.println("In correct guild!");
			event.getChannel().sendMessage(event.getMessage().getContentRaw());
		}
		
		@Override
		public void onReady() {
			System.out.println("Ready!");
		}
	}
}
