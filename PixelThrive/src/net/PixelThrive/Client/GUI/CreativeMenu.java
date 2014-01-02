package net.PixelThrive.Client.GUI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.blocks.*;
import net.PixelThrive.Client.items.Item;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.world.Tile;

public class CreativeMenu extends GUI
{
	public boolean isOpen = false;

	public int x, y;
	public static List<BlockAndItem> buildBlocks = new ArrayList<BlockAndItem>();
	public static List<BlockAndItem> decoBlocks = new ArrayList<BlockAndItem>();
	public static List<BlockAndItem> storageBlocks = new ArrayList<BlockAndItem>();
	public static List<BlockAndItem> liquids = new ArrayList<BlockAndItem>();
	public static List<BlockAndItem> tools = new ArrayList<BlockAndItem>();
	public static List<BlockAndItem> combat = new ArrayList<BlockAndItem>();
	public static List<BlockAndItem> explosives = new ArrayList<BlockAndItem>();
	public static List<BlockAndItem> potions = new ArrayList<BlockAndItem>();
	public static List<BlockAndItem> misc = new ArrayList<BlockAndItem>();
	private List<BlockAndItem> list;
	public static Category[] categories = new Category[9];
	private int[] icons = new int[9];
	private String[] labels = new String[9];

	public CreativeMenu(int x, int y)
	{
		this.x = x;
		this.y = y;
		icons[0] = Block.stone.blockID;
		icons[1] = Block.zenGlass.blockID;
		icons[2] = Block.chest.blockID;
		icons[3] = Block.water.blockID;
		icons[4] = Item.stonePick.itemID;
		icons[5] = Item.woodenSword.itemID;
		icons[6] = Item.dynamite.itemID;
		icons[7] = Item.regenerationPotion.itemID;
		icons[8] = Item.string.itemID;
		labels[0] = "Building Blox";
		labels[1] = "Decorative Blox";
		labels[2] = "Storage Blox";
		labels[3] = "Liquids";
		labels[4] = "Tools";
		labels[5] = "Combat";
		labels[6] = "Explosive";
		labels[7] = "Potions";
		labels[8] = "Misc.";
		int xx = 0;
		int yy = 0;
		for(int i = 0; i < categories.length; i++)
		{
			if(i == 0) list = buildBlocks;
			else if(i == 1) list = decoBlocks;
			else if(i == 2) list = storageBlocks;
			else if(i == 3) list = liquids;
			else if(i == 4) list = tools;
			else if(i == 5) list = combat;
			else if(i == 6) list = explosives;
			else if(i == 7) list = potions;
			else list = misc;
			categories[i] = new Category(labels[i], icons[i], (x - 1) + (xx * (Tile.invCellSize + Tile.invCellSpace)), (y + 20) + (yy * (Tile.invCellSize + Tile.invCellSpace)), list, x, y);
			xx++;
			if(xx >= 3)
			{
				xx = 0;
				yy++;
			}
		}
	}

	public void tick()
	{
		for(int i = 0; i < categories.length; i++) categories[i].tick();

		if(categories[0].isSelected && !categories[0].cap)
		{
			categories[1].isSelected = false;
			categories[2].isSelected = false;
			categories[3].isSelected = false;
			categories[4].isSelected = false;
			categories[5].isSelected = false;
			categories[6].isSelected = false;
			categories[7].isSelected = false;
			categories[8].isSelected = false;
			categories[0].cap = true;
			for(int i = 1; i < categories.length; i++) categories[i].cap = false;
		}
		if(categories[1].isSelected && !categories[1].cap)
		{
			categories[0].isSelected = false;
			categories[2].isSelected = false;
			categories[3].isSelected = false;
			categories[4].isSelected = false;
			categories[5].isSelected = false;
			categories[6].isSelected = false;
			categories[7].isSelected = false;
			categories[8].isSelected = false;
			categories[1].cap = true;
			for(int i = 2; i < categories.length; i++) categories[i].cap = false;
			for(int i = 0; i < categories.length - 8; i++) categories[i].cap = false;
		}
		if(categories[2].isSelected && !categories[2].cap)
		{
			categories[0].isSelected = false;
			categories[1].isSelected = false;
			categories[3].isSelected = false;
			categories[4].isSelected = false;
			categories[5].isSelected = false;
			categories[6].isSelected = false;
			categories[7].isSelected = false;
			categories[8].isSelected = false;
			categories[2].cap = true;
			for(int i = 3; i < categories.length; i++) categories[i].cap = false;
			for(int i = 0; i < categories.length - 7; i++) categories[i].cap = false;
		}
		if(categories[3].isSelected && !categories[3].cap)
		{
			categories[0].isSelected = false;
			categories[1].isSelected = false;
			categories[2].isSelected = false;
			categories[4].isSelected = false;
			categories[5].isSelected = false;
			categories[6].isSelected = false;
			categories[7].isSelected = false;
			categories[8].isSelected = false;
			categories[3].cap = true;
			for(int i = 4; i < categories.length; i++) categories[i].cap = false;
			for(int i = 0; i < categories.length - 6; i++) categories[i].cap = false;
		}
		if(categories[4].isSelected && !categories[4].cap)
		{
			categories[0].isSelected = false;
			categories[1].isSelected = false;
			categories[2].isSelected = false;
			categories[3].isSelected = false;
			categories[5].isSelected = false;
			categories[6].isSelected = false;
			categories[7].isSelected = false;
			categories[8].isSelected = false;
			categories[4].cap = true;
			for(int i = 5; i < categories.length; i++) categories[i].cap = false;
			for(int i = 0; i < categories.length - 5; i++) categories[i].cap = false;
		}
		if(categories[5].isSelected && !categories[5].cap)
		{
			categories[0].isSelected = false;
			categories[1].isSelected = false;
			categories[2].isSelected = false;
			categories[3].isSelected = false;
			categories[4].isSelected = false;
			categories[6].isSelected = false;
			categories[7].isSelected = false;
			categories[8].isSelected = false;
			categories[5].cap = true;
			for(int i = 6; i < categories.length; i++) categories[i].cap = false;
			for(int i = 0; i < categories.length - 4; i++) categories[i].cap = false;
		}
		if(categories[6].isSelected && !categories[6].cap)
		{
			categories[0].isSelected = false;
			categories[1].isSelected = false;
			categories[2].isSelected = false;
			categories[3].isSelected = false;
			categories[4].isSelected = false;
			categories[5].isSelected = false;
			categories[7].isSelected = false;
			categories[8].isSelected = false;
			categories[6].cap = true;
			for(int i = 7; i < categories.length; i++) categories[i].cap = false;
			for(int i = 0; i < categories.length - 3; i++) categories[i].cap = false;
		}
		if(categories[7].isSelected && !categories[7].cap)
		{
			categories[0].isSelected = false;
			categories[1].isSelected = false;
			categories[2].isSelected = false;
			categories[3].isSelected = false;
			categories[4].isSelected = false;
			categories[5].isSelected = false;
			categories[6].isSelected = false;
			categories[8].isSelected = false;
			categories[7].cap = true;
			for(int i = 8; i < categories.length; i++) categories[i].cap = false;
			for(int i = 0; i < categories.length - 2; i++) categories[i].cap = false;
		}
		if(categories[8].isSelected && !categories[8].cap)
		{
			categories[0].isSelected = false;
			categories[1].isSelected = false;
			categories[2].isSelected = false;
			categories[3].isSelected = false;
			categories[4].isSelected = false;
			categories[5].isSelected = false;
			categories[6].isSelected = false;
			categories[7].isSelected = false;
			categories[8].cap = true;
			for(int i = 0; i < categories.length - 1; i++) categories[i].cap = false;
		}
	}

	public void render()
	{
		Render.setColor(new Color(20, 20, 20, 150));
		Render.fillRect(x - 3, y - 3, 180, 100);
		Render.setColor(new Color(20, 20, 20, 200));
		Render.fillRect(x + 58, y - 3, 2, 100);
		for(int i = 0; i < categories.length; i++) categories[i].render();	
		
		for(int i = 0; i < categories.length; i++)
		{
			if(categories[i].isSelected)
			{
				Render.setColor(new Color(190, 190, 190, 110));
				Render.fillRect(categories[i].x, categories[i].y, Tile.invCellSize, Tile.invCellSize);
			}
		}
		for(int i = 0; i < categories.length; i++)
		{
			if(categories[i].isHovering)
			{
				Render.setFont(Main.gameFont, 9);
				String name = labels[i];
				Render.setColor(new Color(60, 60, 60, 255));
				Render.fillRect((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 4, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 10, Render.getFontMetrics().stringWidth(name) + 3, 12);
				Render.setColor(Color.black);
				Render.drawRect((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 4, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 10, Render.getFontMetrics().stringWidth(name) + 3, 12);
				Render.setColor(new Color(20, 10, 50, 180));
				Text.drawStringWithShadow(name, (Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 1, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder, Color.WHITE, 9, Main.gameFont);
			}
		}
	}

	public void openGUI()
	{
		super.openGUI();
		isOpen = true;
	}

	public void closeGUI()
	{
		super.closeGUI();
		isOpen = false;
	}
}
