package nl.kaaz.primalessence;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import nl.kaaz.primalessence.blocks.container.TestContainerBlock;

public class PrimalEssenceBlocks {

	@GameRegistry.ObjectHolder(PrimalEssence.ID + ":testcontainerblock")
	public static TestContainerBlock testContainerBlock;

	@SideOnly(Side.CLIENT)
	public static void initModels() {
		testContainerBlock.initModel();
	}
}
