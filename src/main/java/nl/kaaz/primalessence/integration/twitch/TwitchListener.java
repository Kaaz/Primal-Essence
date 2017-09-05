package nl.kaaz.primalessence.integration.twitch;

import com.cavariux.twitchirc.Core.TwitchBot;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import nl.kaaz.primalessence.PrimalEssence;
import nl.kaaz.primalessence.configuration.Config;

public class TwitchListener extends TwitchBot {
	private static final String format_no_channel = TextFormatting.GOLD + "@%s" + TextFormatting.WHITE + ": " + TextFormatting.GRAY + "%s";
	private static final String format_with_channel = TextFormatting.DARK_RED + "[%s] " + format_no_channel;
	private boolean outputChannelNames = false;

	public TwitchListener() {
		this.setUsername(Config.Twitch.userName);
		this.setOauth_Key(Config.Twitch.IrcToken);
	}

	@Override
	protected void onMessage(String user, String channel, String message) {
		PrimalEssence.logger.info(String.format("[%s] %s", user, message));
		TextComponentString out;
		if (outputChannelNames) {
			out = new TextComponentString(String.format(format_with_channel, channel, user, message));
		} else {
			out = new TextComponentString(String.format(format_no_channel, user, message));
		}
		FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendChatMsg(out);
//		FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(out);
	}

	public void outputChannelNames(boolean outputChannelNames) {
		this.outputChannelNames = outputChannelNames;
	}
}