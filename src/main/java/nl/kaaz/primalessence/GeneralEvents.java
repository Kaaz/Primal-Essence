package nl.kaaz.primalessence;

import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import nl.kaaz.primalessence.configuration.Config;

public class GeneralEvents {
	private static final String format_no_channel = TextFormatting.GOLD + "@%s" + TextFormatting.WHITE + ": " + TextFormatting.GRAY + "%s";

	@SubscribeEvent
	public void chatMessageHandler(ServerChatEvent event) {
		System.out.println("beep boop");
		if (!Config.Twitch.enabled) {
			return;
		}
		event.setComponent(new TextComponentString(String.format(format_no_channel, event.getPlayer().getName(), event.getMessage())));
		PrimalEssence.instance.relayMessage(event.getMessage());
	}
}
