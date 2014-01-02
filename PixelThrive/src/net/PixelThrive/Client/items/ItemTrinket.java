package net.PixelThrive.Client.items;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.GUI.CreativeTabs;
import net.PixelThrive.Client.entities.Player;
import net.PixelThrive.Client.world.World;

public class ItemTrinket extends Item
{
	/**
	 * @param id - the id of the item
	 */
	public ItemTrinket(int id)
	{
		super(id);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.MISCELANIOUS);
	}

	public void tickMouseHolding()
	{
		if(Main.inv != null) 
			for(int i = 0; i < Main.inv.invTrinkets.length; i++)
				canItemBePlacedInSlot(Main.inv.invTrinkets[i]);
	}

	public void tickWhileWearing(Player player, World world) {}

	public void onItemRightClick()
	{
		for(int i = 0; i < Main.inv.invTrinkets.length; i++)
			if(Main.inv.invTrinkets[i].id == 0)
			{
				Main.inv.removeItemFromInvBar(Main.inv.selection);
				Main.inv.invTrinkets[i].id = this.itemID;
			}
	}
}
