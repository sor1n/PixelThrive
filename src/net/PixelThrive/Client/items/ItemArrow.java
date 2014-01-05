package net.PixelThrive.Client.items;

import java.awt.event.MouseEvent;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.GUI.CreativeTabs;
import net.PixelThrive.Client.renders.PlayerAnimation;
import net.PixelThrive.Client.renders.Render;

public class ItemArrow extends Item
{
	protected int minDamage, maxDamage, arrowID;

	private PlayerAnimation stab = new PlayerAnimation(new int[]{4, 5}, 15, itemID, MouseEvent.BUTTON1);
	protected boolean isSwinging = false;
	
	/**
	 * 
	 * @param id
	 * @param minDamage
	 * @param maxDamage
	 * @param arrowID
	 */
	public ItemArrow(int id, int minDamage, int maxDamage, int arrowID)
	{
		super(id);
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.arrowID = arrowID;
		addStat("Maximum Damage: " + maxDamage);
		addStat("Minimum Damage: " + minDamage);
		setCreativeTab(CreativeTabs.COMBAT);
		setFunctions(new ItemFunctions[]{ItemFunctions.DEFAULT, ItemFunctions.STAB, ItemFunctions.THROW});
	}

	public int getMinDamage()
	{
		return minDamage;
	}

	public int getMaxDamage()
	{
		return maxDamage;
	}
	
	public void renderInPlayerHand(int x, int y, double angle, boolean flipped, boolean small)
	{
		if(Main.key.isMouseLeft)
		{
			if(getCurrentFunction() == ItemFunctions.STAB)
			{
				if(!flipped) Render.drawImage(Render.rotate(texture.getImageIcon(), -90), x - 6 - (getAnimation().getCurrentFrame() * 4) - holdingOffsX, y + 2);
				else Render.drawImage(Render.rotate(Render.flipHorizontal(texture.getImageIcon()), 90), x + 5 + (getAnimation().getCurrentFrame() * 4) + holdingOffsX, y + 2);
			}
			else super.renderInPlayerHand(x, y, angle, flipped, small);
		}
		else super.renderInPlayerHand(x, y, angle, flipped, small);
	}

	public void tickInInventory()
	{
		if(getCurrentFunction() == ItemFunctions.STAB && getAnimation() != stab) setAnimation(stab);
	}
}
