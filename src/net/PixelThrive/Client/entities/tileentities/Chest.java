package net.PixelThrive.Client.entities.tileentities;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.GUI.*;
import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.items.CraftableStack;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.Tile;
import net.PixelThrive.Client.blocks.*;

public class Chest implements IGUIBlock
{
	public Slot[] chest = new Slot[16];
	private List<Integer> blocks = new ArrayList<Integer>();
	public CraftableStack[] contains = new CraftableStack[16];
	public Image icon, icon2;
	public int randBlock_1, randBlock_2;
	public int chestID;
	public int x, y;
	public static Chest[] chests = new Chest[255];
	public boolean isOpen = false;

	public Chest(int id, int x, int y)
	{
		if(chests[id] == null)
		{
			this.x = x;
			this.y = y;
			chestID = id;
			int xx = 0, yy = 0;
			for(int i = 0; i < chest.length; i++)
			{
				chest[i] = new Slot(new Rectangle((GUI.guiX + xx * (Tile.invCellSize + 1)), (yy * (Tile.invCellSize + 1)) + GUI.guiY, Tile.invCellSize, Tile.invCellSize), Block.air.blockID, 99, 255, 255, 255, false);
				xx++;
				if(xx >= 4)
				{
					xx = 0;
					yy++;
				}
			}
			chests[id] = this;
		}
	}

	public void GUItick()
	{
		for(int i = 0; i < chest.length; i++)
		{
			chest[i].tick();
			contains[i] = (new CraftableStack(chest[i].id, chest[i].itemAmount));
		}
	}
	
	public void render(int x, int y)
	{
		if(isOpen)
		{
			Render.drawImage(SpriteSheet.getIcon(SpriteSheet.Terrain, 5, 1), (x * Tile.tileSize) - (int)Main.sX, (y * Tile.tileSize) - (int)Main.sY);
			if(Chest.getChest(x, y).randBlock_1 > 0) Render.drawImage(Chest.getChest(x, y).icon, (x * Tile.tileSize - (int)Main.sX) + 3, (y * Tile.tileSize - (int)Main.sY) + 3);
			if(Chest.getChest(x, y).randBlock_2 > 0) Render.drawImage(Chest.getChest(x, y).icon2, (x * Tile.tileSize - (int)Main.sX) + 8, (y * Tile.tileSize - (int)Main.sY) + 3);
		}
		else Render.drawImage(SpriteSheet.getIcon(SpriteSheet.Terrain, 4, 1), (x * Tile.tileSize) - (int)Main.sX, (y * Tile.tileSize) - (int)Main.sY);
	}

	public void GUIrender()
	{
		for(int i = 0; i < chest.length; i++) chest[i].render(false);
	}

	public void getRandomBlocks()
	{
		for(int i = 0; i < chest.length; i++) if(chest[i].id != 0) blocks.add(chest[i].id);
		if(blocks.size() > 1)
		{
			randBlock_1 = blocks.get(new Random().nextInt(blocks.size()));
			randBlock_2 = blocks.get(new Random().nextInt(blocks.size()));
			icon = BlockAndItem.getItemOrBlockTexture(randBlock_1).getImageIcon().getScaledInstance(5, 5, 0);
			icon2 = BlockAndItem.getItemOrBlockTexture(randBlock_2).getImageIcon().getScaledInstance(5, 5, 0);
		}
		else randBlock_1 = randBlock_2 = 0;
	}
	
	public void clearChest(int x, int y)
	{
		for(int i = 0; i < getChest(x, y).contains.length; i++) getChest(x, y).contains[i].emptyStack();
		setChestNull(x, y);
	}
	
	public void setChestNull(int x, int y)
	{
		for(int i = 0; i < chests.length; i++) if(chests[i] != null && chests[i].x == x && chests[i].y == y) chests[i] = null;
	}
	
	public static Chest getChest(int x, int y)
	{
		for(int i = 0; i < chests.length; i++) if(chests[i] != null && chests[i].x == x && chests[i].y == y) return chests[i];
		return null;
	}
	
	public static void clearChests()
	{
		for(int i = 0; i < chests.length; i++) chests[i] = null;
	}
}
