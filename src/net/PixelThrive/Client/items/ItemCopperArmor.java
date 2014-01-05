package net.PixelThrive.Client.items;

import net.PixelThrive.Client.buff.ArmorBuff;
import net.PixelThrive.Client.materials.ArmorMaterial;

public class ItemCopperArmor extends ItemArmor
{
	public ItemCopperArmor(int id, int type)
	{
		super(id, type, ArmorMaterial.COPPER, "Copper");
		addCompleteSetBuff(ArmorBuff.camo);
	}
}
