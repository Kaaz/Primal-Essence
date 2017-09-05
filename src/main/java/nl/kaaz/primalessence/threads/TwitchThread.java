package nl.kaaz.primalessence.threads;

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
		twitchListener.outputChannelNames(Config.Twitch.listenChannels.length > 1);
		for (String channel : Config.Twitch.listenChannels) {
			if (!channel.startsWith("#")) {
				twitchListener.joinChannel("#" + channel);
			} else {
				twitchListener.joinChannel(channel);
			}
		}
		twitchListener.start();
	}

	public void sendMessage(String msg) {
		twitchListener.sendMessage(msg,twitchListener.getChannels().get(0));
	}
}
