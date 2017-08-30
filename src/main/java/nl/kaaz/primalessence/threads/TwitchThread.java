package nl.kaaz.primalessence.threads;

import net.minecraft.world.World;
import nl.kaaz.primalessence.configuration.Config;
import nl.kaaz.primalessence.integration.twitch.TwitchListener;

public class TwitchThread extends Thread {
	private TwitchListener twitchListener;

	public TwitchThread() {
		super("twitch-listener-thread");
		twitchListener = new TwitchListener();
		setDaemon(true);
	}

	@Override
	public void run() {
		twitchListener = new TwitchListener();
		twitchListener.connect();
		if (!Config.Twitch.listenChannel.startsWith("#")) {
			Config.Twitch.listenChannel = "#" + Config.Twitch.listenChannel;
		}
		twitchListener.joinChannel(Config.Twitch.listenChannel);
		twitchListener.start();
	}
}
