package net.PixelThrive.Client.items;

import net.PixelThrive.Client.buff.ArmorBuff;
import net.PixelThrive.Client.materials.ArmorMaterial;

public class ItemGoldArmor extends ItemArmor
{
	public ItemGoldArmor(int id, int type)
	{
		super(id, type, ArmorMaterial.GOLD, "Gold");
		addCompleteSetBuff(ArmorBuff.camo);
	}
}
