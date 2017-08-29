package nl.kaaz.primalessence.configuration;

import nl.kaaz.primalessence.configuration.binder.annotations.Comment;

/**
 * All public static variables are bound to a config file.
 * Subclasses are categories
 */
public class Config {

	@Comment({"Twitch integration"})
	public static class Twitch {
		public static boolean enabled = false;
		public static String AppId = "";
		@Comment({"The twitch channel to listen to"})
		public static String clientSecret = "";
		public static String IrcToken = "";

		public static String listenChannel = "";
	}

	@Comment({"settings intended for debugging and/or development"})
	public static class Development {
		@Comment({"Adds a bunch of extra information you usually don't wanna see"})
		public static boolean debug = false;
	}
}
