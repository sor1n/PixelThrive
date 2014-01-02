package net.PixelThrive.Client.items;

import net.PixelThrive.Client.buff.ArmorBuff;
import net.PixelThrive.Client.materials.ArmorMaterial;

public class ItemIronArmor extends ItemArmor
{
	public ItemIronArmor(int id, int type)
	{
		super(id, type, ArmorMaterial.IRON, "Iron");
		addCompleteSetBuff(ArmorBuff.camo);
	}
}
