package nl.kaaz.primalessence.proxies;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import nl.kaaz.primalessence.PrimalEssenceBlocks;
import nl.kaaz.primalessence.PrimalEssence;
import nl.kaaz.primalessence.blocks.container.TestContainerBlock;
import nl.kaaz.primalessence.entities.TestContainerTileEntity;

@Mod.EventBusSubscriber
public class CommonProxy {
	public void preInit(FMLPreInitializationEvent e) {
	}

	public void init(FMLInitializationEvent e) {
	}

	public void postInit(FMLPostInitializationEvent e) {
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(new TestContainerBlock());
		GameRegistry.registerTileEntity(TestContainerTileEntity.class, PrimalEssence.ID + "_testcontainerblock");
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().register(new ItemBlock(PrimalEssenceBlocks.testContainerBlock).setRegistryName(PrimalEssenceBlocks.testContainerBlock.getRegistryName()));
	}
}

