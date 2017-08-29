package nl.kaaz.primalessence;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import nl.kaaz.primalessence.blocks.PEBlocks;
import nl.kaaz.primalessence.configuration.Config;
import nl.kaaz.primalessence.configuration.binder.ConfigBinder;
import nl.kaaz.primalessence.configuration.binder.annotations.Ignore;
import nl.kaaz.primalessence.items.PEItems;
import nl.kaaz.primalessence.proxies.CommonProxy;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

@Mod(modid = PrimalEssence.ID, name = PrimalEssence.NAME, version = PrimalEssence.VERSION)
public class PrimalEssence {
	public static final String ID = "primalessence";
	public static final String NAME = "Primal Essence";
	public static final String VERSION = "${version}";

	@Mod.Instance
	public static PrimalEssence instance;

	@SidedProxy(clientSide = "nl.kaaz.primalessence.proxies.ClientProxy", serverSide = "nl.kaaz.primalessence.proxies.ServerProxy")
	public static CommonProxy proxy;
	private Logger logger;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = event.getModLog();
		proxy.preInit(event);
		ConfigBinder.preInit(event, Config.class);
		try {
			initializeClass(PEBlocks.class, PEBlocks.instance);
			initializeClass(PEItems.class, PEItems.instance);
		} catch (IllegalAccessException e) {
			logger.fatal("Something is messed up during loading", e);
		}
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent e) {
		proxy.init(e);
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		proxy.postInit(e);
	}

	public static void initializeClass(Class c, Object instance) throws IllegalAccessException {
		for (Field field : c.getFields()) {
			if (!Modifier.isStatic(field.getModifiers()) || !Modifier.isPublic(field.getModifiers()) || (field.getAnnotation(Ignore.class) != null)) {
				continue;
			}
			if (field.get(instance) instanceof PEInitializable) {
				((PEInitializable) field.get(instance)).initialize();
			}
		}
	}
}