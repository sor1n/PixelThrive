package net.PixelThrive.Client.entities;

import java.awt.Color;
import java.awt.Point;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.GUI.Text;
import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.blocks.BlockAndItem;
import net.PixelThrive.Client.items.CraftableStack;
import net.PixelThrive.Client.items.Item;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.world.Tile;

public class Drop extends Entity
{
	public int amount;
	public int life = 0;
	protected int blockid;
	protected double gravity = 0.1;
	protected double fallingSpeed = 1.5;

	public Drop(int x, int y, int id, int amount)
	{
		super(x, y, Tile.tileSize - 9, Tile.tileSize - 9, new int[]{0, 0}, false);
		health = maxHealth = 0;
		blockid = id;
		this.amount = amount;
		for(int i = 0; i < Block.blocks.size(); i++)
		{
			int[] blok = {Block.blocks.get(i).getTexture().x, Block.blocks.get(i).getTexture().y};
			if(id == Block.blocks.get(i).blockID) this.id = blok;
		}
		for(int i = 0; i < Item.items.size(); i++)
		{
			if(id == Item.items.get(i).itemID) 
			{
				int[] blok = {Item.items.get(i).getTexture().x, Item.items.get(i).getTexture().y};
				this.id = blok;
			}
		}
	}
	
	public Drop()
	{
		this(0, 0, 0, -1);
	}

	public Drop(CraftableStack item, int x, int y)
	{
		this(x, y, item.getID(), item.getAmount());
	}

	public Drop(int x, int y, int[] id, int amount)
	{
		super(x, y, Tile.tileSize - 9, Tile.tileSize - 9, id, false);
		health = maxHealth = 0;
		for(int i = 0; i < Block.blocks.size(); i++)
		{
			int[] blok = {Block.blocks.get(i).getTexture().x, Block.blocks.get(i).getTexture().y};
			if(blok == id) blockid = Block.blocks.get(i).blockID;
		}
		for(int i = 0; i < Item.items.size(); i++)
		{
			int[] blok = {Item.items.get(i).getTexture().x, Item.items.get(i).getTexture().y};
			if(blok == id) blockid = Item.items.get(i).itemID;
		}
		this.amount = amount;
	}

	public void tick()
	{
		life++;

		animation = 0;
		if(!isCollidingWithBlock(new Point((int) x + 2, (int) (y + height)), new Point((int) (x + width - 2), (int) (y + height))))
		{
			y += gravity;
			if(gravity < fallingSpeed) gravity += 0.2;
			isFalling = true;
			isOnGround = false;
		}
		else
		{
			isFalling = false;
			gravity = 0.1;
			isOnGround = true;
		}

		if(Main.world.getBlockID((int)(x / Tile.tileSize), (int)(y / Tile.tileSize)) != 0) this.y -= 22;
		if(isCollidingWithPlayer() && life >= 50 && !Main.inv.isInvFull())
		{
			Main.inv.giveItem(new CraftableStack(blockid, amount + 1));
			despawnEntity();
		}
		if(life >= 10000) despawnEntity();
	}

	public void render()
	{
		if(blockid != 0)
		{
			for(int i = 0; i < Block.blocks.size(); i++)
			{
				if(Block.blocks.get(i).blockID == blockid)
				{
					if(Block.blocks.get(blockid).dropRender()) Render.drawImage(BlockAndItem.getSpriteSheet(blockid).getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY, (int) (x + width) - (int) Main.sX, (int) (y + height) - (int) Main.sY, /**/ (id[0] * Tile.tileSize) + (Tile.tileSize * 0), id[1] * Tile.tileSize, (id[0] * Tile.tileSize) + (Tile.tileSize * 0) + (int) textureWidth, id[1] * Tile.tileSize + (int)height);
					else Block.blocks.get(blockid).renderDrop((int)x - (int)Main.sX, (int)y - (int)Main.sY);
				}
			}
			for(int i = 0; i < Item.items.size(); i++){
				Item it = Item.items.get(i);
				if(it.itemID == blockid) Item.items.get(i).render((int) x - (int) Main.sX, (int) y - (int) Main.sY - (int)height);
			}

			for(int i = 0; i < Block.blocks.size(); i++)
			{
				if(Block.blocks.get(i).blockID == blockid) Text.drawStringWithShadow(Block.blocks.get(i).getName().toUpperCase().substring(0, 1) + Block.blocks.get(i).getName().substring(1) + " [" + (amount + 1) + "]", (int)x - (int)Main.sX - 10, (int)y - (int)Main.sY - 22, Color.WHITE, 8, Main.gameFont);
			}
			for(int i = 0; i < Item.items.size(); i++)
			{
				if(Item.items.get(i).itemID == blockid) Text.drawStringWithShadow(Item.items.get(i).getName().toUpperCase().substring(0, 1) + Item.items.get(i).getName().substring(1) + " [" + (amount + 1) + "]", (int)x - (int)Main.sX - 10, (int)y - (int)Main.sY - 22, Color.WHITE, 8, Main.gameFont);
			}
		}
	}
}
