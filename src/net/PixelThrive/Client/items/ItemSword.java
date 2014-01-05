package net.PixelThrive.Client.items;

import net.PixelThrive.Client.materials.ToolMaterial;
import net.PixelThrive.Client.GUI.CreativeTabs;

public class ItemSword extends Item
{
	protected int baseDMG, level;
	
	public ItemSword(int id, int baseDMG, ToolMaterial mat)
	{
		super(id);
		this.baseDMG = baseDMG;
		setMaxStackSize(1);
		level = mat.getPower();
		switch(level)
		{
		case 0:
			setDescription("Pirated from pirates.", 0x0AFAEA);
			break;
		case 1:
			setDescription("You're now a caveman :D", 0x3FABE0);
			break;
		}
		setCropInHand(false, 0);
		addStat("Base Damage: " + baseDMG);
		setCreativeTab(CreativeTabs.COMBAT);
		setAnimation(poke);
		setCurrentFunction(ItemFunctions.POKE);
	}

	public int getBaseDamage()
	{
		return baseDMG;
	}

	public void onItemLeftClick()
	{
		super.onItemLeftClick();
	}

	public void tickInInventory()
	{
		super.tickInInventory();
	}

	public void renderInPlayerHand(int x, int y, double angle, boolean flipped, boolean small)
	{
		super.renderInPlayerHand(x, y, angle, flipped, small);
//		if(Main.key.isMouseLeft)
//		{
//			switch(getCurrentFunction())
//			{
//			case STAB:
//				if(!flipped) Render.drawImage(Render.rotate(texture.getImageIcon(), -90), x - 5 - (getAnimation().getCurrentFrame() * 4) - holdingOffsX, y + 2);
//				else Render.drawImage(Render.rotate(Render.flipHorizontal(texture.getImageIcon()), 90), x + 4 + (getAnimation().getCurrentFrame() * 4) + holdingOffsX, y + 2);
//				break;
//			case SLASH:
//			case SLAP:
//				if(!flipped) Render.drawImage(Render.rotate(texture.getImageIcon(), -value), x - holdingOffsXLeft, y - (20 - yOffs / 3));
//				else Render.drawImage(Render.flipHorizontal(Render.rotate(texture.getImageIcon(), -value)), x + holdingOffsXRight, y - (20 - yOffs / 3));
//				break;
//			default:
//			case POKE:
//				if(!flipped) Render.drawImage(Render.rotate(texture.getImageIcon(), -90), x - 5 - (getAnimation().getCurrentFrame() * 3) - holdingOffsX, y + 5);
//				else Render.drawImage(Render.rotate(Render.flipHorizontal(texture.getImageIcon()), 90), x + 4 + (getAnimation().getCurrentFrame() * 3) + holdingOffsX, y + 5);
//				break;
//			}
//		}
//		else
//		{
//			if(!flipped) Render.drawImage(texture.getImageIcon(), x - holdingOffsX, y);
//			else Render.drawImage(Render.flipHorizontal(texture.getImageIcon()), x + holdingOffsX, y);
//		}
	}
}
