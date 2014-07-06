package net.PixelThrive.Client.items;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.GUI.CreativeTabs;
import net.PixelThrive.Client.entities.Player;
import net.PixelThrive.Client.world.World;

public class ItemJewelry extends Item
{
	public int type;
	/**
	 * @param id - the id of the item
	 * @param type - the type of jewelry (0 earrings, 1 bracelet, 2 necklace, 3 ring)
	 */
	public ItemJewelry(int id, int type)
	{
		super(id);
		this.type = type;
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.MISCELANIOUS);
	}

	public void tickMouseHolding()
	{
		if(Main.inv != null) canItemBePlacedInSlot(Main.inv.invJewelry[type]);
	}
	
	public void tickWhileWearing(Player player, World world) {}

	public void onItemRightClick()
	{
		if(Main.inv.invJewelry[type].id == 0){
			Main.inv.removeItemFromInvBar(Main.inv.selection);
			Main.inv.invJewelry[type].id = this.itemID;
		}
	}
}
