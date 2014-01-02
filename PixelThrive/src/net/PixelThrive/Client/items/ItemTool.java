package net.PixelThrive.Client.items;

import net.PixelThrive.Client.materials.ToolMaterial;
import net.PixelThrive.Client.GUI.CreativeTabs;

public class ItemTool extends Item
{
	/**
	 * @param id
	 * @param strength
	 * @param type
	 */
	public ItemTool(int id, ToolMaterial mat)
	{
		super(id);
		this.strength = mat.getStrength();
		this.power = mat.getPower();
		setMaxStackSize(1);
		switch(power)
		{
		case 0:
			setDescription("Wooden tools...Noob", 0xEAF0F2);
			break;
		case 1:
			setDescription("StoneTools™", 0x3FABE0);
			break;
		case 2:
			setDescription("This is pretty strong", 0xEA21EF);
			break;
		}
		setCropInHand(false, 0);
		addStat("Strength: " + (100 - strength));
		addStat("Power: " + power);
		setCreativeTab(CreativeTabs.TOOLS);
	}

	public int getStrength()
	{
		return strength;
	}

	public int getPower()
	{
		return power;
	}
}
