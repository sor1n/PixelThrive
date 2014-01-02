package net.PixelThrive.Client.items;

import net.PixelThrive.Client.GUI.CreativeTabs;

public class ItemGun extends Item
{ 
	protected int baseDMG;
	
	public ItemGun(int id, int baseDMG)
	{
		super(id);
		setCreativeTab(CreativeTabs.COMBAT);
		setMaxStackSize(1);
		setCropInHand(false, 0);
		addStat("Base Damage: " + baseDMG);
		this.baseDMG = baseDMG;
	}
}
