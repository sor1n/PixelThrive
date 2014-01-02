package net.PixelThrive.Client.items;

import net.PixelThrive.Client.buff.ArmorBuff;
import net.PixelThrive.Client.materials.ArmorMaterial;

public class ItemWoodenArmor extends ItemArmor
{
	public ItemWoodenArmor(int id, int type)
	{
		super(id, type, ArmorMaterial.WOOD, "Wooden");
		addCompleteSetBuff(ArmorBuff.camo);
	}
}
