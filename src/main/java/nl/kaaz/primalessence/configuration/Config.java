package nl.kaaz.primalessence.configuration;

import nl.kaaz.primalessence.configuration.binder.annotations.Comment;

/**
 * All public static variables are bound to a config file.
 * Subclasses are categories
 */
public class Config {

	@Comment({"Twitch integration"})
	public static class Twitch {

		@Comment({"Enable or disable twitch integration"})
		public static boolean enabled = false;

		@Comment({"Optional client id, some functionality requires the client id"})
		public static String clientId = "";

		@Comment({"the username of the listener"})
		public static String userName = "";

		@Comment({"The oauth token to connect to the twitch irc"})
		public static String IrcToken = "";

		@Comment({"The twitch channel to listen to"})
		public static String listenChannel = "";
	}

	@Comment({"settings intended for debugging and/or development"})
	public static class Development {
		@Comment({"Adds a bunch of extra information you usually don't wanna see"})
		public static boolean debug = false;
	}
}
