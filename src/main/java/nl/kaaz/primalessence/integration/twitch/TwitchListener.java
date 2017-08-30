package nl.kaaz.primalessence.integration.twitch;

import com.cavariux.twitchirc.Core.TwitchBot;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;
import nl.kaaz.primalessence.PrimalEssence;
import nl.kaaz.primalessence.configuration.Config;

public class TwitchListener extends TwitchBot {
	public TwitchListener() {
		this.setUsername(Config.Twitch.userName);
		this.setOauth_Key(Config.Twitch.IrcToken);
	}

	@Override
	protected void onMessage(String user, String channel, String message) {
		PrimalEssence.logger.info(String.format("[%s] %s", user, message));
		FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(
				new TextComponentString(String.format("%s%s%s: %s", TextFormatting.DARK_PURPLE, user, TextFormatting.GRAY, message)));
	}
}