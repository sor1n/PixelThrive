package net.PixelThrive.Client.items;

import net.PixelThrive.Client.buff.ArmorBuff;
import net.PixelThrive.Client.materials.ArmorMaterial;

public class ItemStoneArmor extends ItemArmor
{
	public ItemStoneArmor(int id, int type)
	{
		super(id, type, ArmorMaterial.STONE, "Stone");
		addCompleteSetBuff(ArmorBuff.camo);
	}
}
