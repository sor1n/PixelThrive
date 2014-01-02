package net.PixelThrive.Client.items;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.GUI.CreativeTabs;
import net.PixelThrive.Client.entities.Player;
import net.PixelThrive.Client.world.World;

public class ItemVanity extends Item
{
	public int type;
	/**
	 * @param id - the id of the item
	 * @param type - the part of the clothes (0 head, 1 chest, 2 feet)
	 */
	public ItemVanity(int id, int type)
	{
		super(id);
		this.type = type;
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.MISCELANIOUS);
	}

	public void tickMouseHolding()
	{
		if(Main.inv != null) canItemBePlacedInSlot(Main.inv.invVanity[type]);
	}
	
	public void tickWhileWearing(Player player, World world) {}

	public void onItemRightClick()
	{
		if(Main.inv.invVanity[type].id == 0){
			Main.inv.removeItemFromInvBar(Main.inv.selection);
			Main.inv.invVanity[type].id = this.itemID;
		}
	}
}
