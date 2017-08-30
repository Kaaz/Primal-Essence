package nl.kaaz.primalessence.util;

import net.minecraft.item.ItemStack;

public class ItemUtil {

	public static boolean hasKey(ItemStack stack, String key) {

		return stack.hasTagCompound() && stack.getTagCompound().hasKey(key);
	}
}
