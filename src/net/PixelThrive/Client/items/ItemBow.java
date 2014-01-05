package net.PixelThrive.Client.items;

import net.PixelThrive.Client.*;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Random;

import net.PixelThrive.Client.GUI.CreativeTabs;
import net.PixelThrive.Client.entities.projectiles.*;
import net.PixelThrive.Client.renders.PlayerAnimation;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.Tile;

public class ItemBow extends Item
{
	protected int baseDMG;
	private int shootDelay = 0;
	protected int item;
	protected boolean isShooting = false, isArrowReady = false;
	protected double dir;
	protected int level;
	protected int dragDelay = 0, dragRender = 0;
	private BufferedImage hand = SpriteSheet.getIcon(SpriteSheet.Entity, 6, 0, Tile.tileSize, Tile.tileSize * 2);

	public ItemBow(int id, int baseDMG, int lvl)
	{
		super(id);
		this.baseDMG = baseDMG;
		setMaxStackSize(1);
		level = lvl;
		switch(lvl)
		{
		case 0:
			setDescription("Made In China. ", 0x0AFAEA);
			break;
		case 1:
			setDescription("Meet the Flintstones.", 0xCD0FAE);
			break;
		}
		setCropInHand(false, 0);
		addStat("Base Damage: " + baseDMG);
		setCreativeTab(CreativeTabs.COMBAT);
		setAnimation(new PlayerAnimation(new int[]{7}, 1, itemID, MouseEvent.BUTTON3));
	}

	public int getBaseDamage()
	{
		return baseDMG;
	}

	public void onItemRightClick()
	{
		if(dragDelay > 20)
		{
			dragDelay = 0;
			isArrowReady = true;
		}
	}

	public void tickInInventory()
	{
		if(shootDelay > 0) shootDelay--;
		if(!Main.key.isMouseRight) dragDelay = 0;
		else if(dragDelay < 100)
		{
			dragDelay++;
			if(dragRender < 20) dragRender++;
		}

		if(Main.key.isMouseRight)
		{
			double dx = Main.mouseX - Main.WIDTH / 2;
			double dy = Main.mouseY - Main.HEIGHT / 2;
			dir = Math.atan2(dy, dx);
		}

		if(!Main.key.isMouseRight && isArrowReady)
		{
			isArrowReady = false;
			for(int i = 0; i < Item.items.size(); i++)
			{
				Item item = Item.items.get(i);
				if(item instanceof ItemArrow && (Main.inv.hasItemInInventory(item.itemID) || Main.player.isCreative) && shootDelay <= 0)
				{
					shootDelay = 15;
					isShooting = true;
					if(!Main.player.isCreative) 
					{
						shoot((int)Main.player.getX(), (int)Main.player.getY() - 2, dir, item.itemID);
						Main.inv.removeItemsFromInventory(item.itemID, 1);
					}
					else
					{
						ItemArrow arrow = (ItemArrow) Item.arrow[new Random().nextInt(Item.arrow.length)];
						shoot((int)Main.player.getX(), (int)Main.player.getY() - 2, dir, arrow.itemID);
					}
					break;
				}
				else isShooting = false;
			}
		}
	}

	public void shoot(int x, int y, double dir, int item)
	{
		new ProjectileArrow(x, y, baseDMG, dir, item).spawnEntity();
		dragDelay = -20;
		dragRender = 0;
		this.dir = 0;
	}

	public void renderInPlayerHand(int x, int y, double angle, boolean flipped, boolean small)
	{
		if(Main.key.isMouseRight)
		{
			if(!flipped)
			{
				Render.drawImage(Render.rotate(texture.getImageIcon(), Math.toDegrees(dir) - 180).getScaledInstance(Tile.tileSize + (dragRender / 5), Tile.tileSize, 0), x, y);
				Render.drawImage(hand.getScaledInstance(Tile.tileSize + (5 - (dragRender / 4)), Tile.tileSize * 2, 0), x + Tile.tileSize + (5 - (dragRender / 4)) + 11 - (10 - (dragRender / 4)), y - 14, (x + 11 - (10 - (dragRender / 4)) + Tile.tileSize + (5 - (dragRender / 4))) - Tile.tileSize + (5 - (dragRender / 4)), y - 14 + Tile.tileSize * 2, 0, 0, Tile.tileSize - (5 - (dragRender / 4)), Tile.tileSize * 2);
			}
			else
			{
				Render.drawImage(Render.rotate(texture.getImageIcon(), Math.toDegrees(dir) + 180).getScaledInstance(Tile.tileSize + (dragRender / 3), Tile.tileSize, 0), x - (dragRender / 3), y);
				Render.drawImage(hand.getScaledInstance(Tile.tileSize + (5 - dragRender / 4), Tile.tileSize * 2, 0), x - 6, y - 14);
			}
		}
		else super.renderInPlayerHand(x, y, angle, flipped, small);
	}
}
