package net.PixelThrive.Client.items;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.GUI.CreativeTabs;
import net.PixelThrive.Client.entities.Player;
import net.PixelThrive.Client.materials.ArmorMaterial;
import net.PixelThrive.Client.world.World;

public class ItemArmor extends Item
{
	public int type, defense;
	public String suitName;
	/**
	 * @param id - the id of the item
	 * @param type - the part of the armor (0 helmet, 1 chest, 2 boots)
	 * @param mat - material of armor
	 * @param name - name of the suit
	 */
	public ItemArmor(int id, int type, ArmorMaterial mat, String name)
	{
		super(id);
		if(type == 0) this.defense = mat.getDefenceHelmet();
		if(type == 1) this.defense = mat.getDefenceChest();
		if(type == 2) this.defense = mat.getDefenceBoots();
		this.type = type;
		setMaxStackSize(1);
		switch(mat)
		{
		case WOOD:
			setDescription("Be one with nature", 0xEAF0F2);
			break;
		case STONE:
			setDescription("To fit in with the golems", 0x3FABE0);
			break;
		default:
			setDescription("You're a wanna-be Iron Man.", 0xAAEF12);
			break;
		}
		suitName = name;
		addStat("Defense: " + defense);
		addStat("Whole Set Defence: " + (mat.getDefenceBoots() + mat.getDefenceChest() + mat.getDefenceHelmet()));
		setCreativeTab(CreativeTabs.COMBAT);
	}

	public void tickMouseHolding()
	{
		if(Main.inv != null) canItemBePlacedInSlot(Main.inv.invArmor[type]);
	}
	
	public void tickWhileWearing(Player player, World world) {}

	public String getSuitName() {
		return suitName;
	}

	public void setSuitName(String suitName) {
		this.suitName = suitName;
	}

	public void onItemRightClick()
	{
		if(Main.inv.invArmor[type].id == 0){
			Main.inv.removeItemFromInvBar(Main.inv.selection);
			Main.inv.invArmor[type].id = this.itemID;
		}
	}
}
