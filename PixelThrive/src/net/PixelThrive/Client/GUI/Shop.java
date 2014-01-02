package net.PixelThrive.Client.GUI;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.PixelThrive.Client.Input.Key;
import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.entities.Player;
import net.PixelThrive.Client.items.CraftableStack;
import net.PixelThrive.Client.items.Item;
import net.PixelThrive.Client.renders.GuiIcons;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.world.Tile;

public class Shop extends GUI
{
	public boolean isOpen = false;
	public static int x;
	public static int y;
	public int keyDelay;
	public int page;
	public int maxPages;
	public static int specialDealItem;
	private Player player;
	private static ShopButton[] shop = new ShopButton[3200000];
	public static ShopButton specialDeal;
	private static List<CraftableStack> price = new ArrayList<CraftableStack>();
	private static List<CraftableStack> object = new ArrayList<CraftableStack>();
	public static List<CraftableStack> specialPrice = new ArrayList<CraftableStack>();
	public static List<CraftableStack> specialObject = new ArrayList<CraftableStack>();
	public Button next, back;

	public Shop(Player player)
	{	
		x = 5;
		y = Main.HEIGHT - 99;		
		this.player = player;
	}

	public void tick() 
	{
		next = new Button(x + 159, y + 1, 16, 16, x + 159, (int)y, x + Tile.tileSize + 159, y + Tile.tileSize, GuiIcons.next, GuiIcons.nextS);
		back = new Button(x + 159, y + Tile.tileSize*4 + 1, 16, 16, x + 159, y + Tile.tileSize*3 + 20, x + Tile.tileSize + 159, y + Tile.tileSize*4 + 20, GuiIcons.back, GuiIcons.backS);
		next.tick();
		back.tick();
		maxPages = price.size()/9;
		if((next.isClicked() || Key.nextPage.isPressed()) && page < maxPages) page++;
		if((back.isClicked() || Key.previousPage.isPressed()) && page > 0) page--;
		if(page > maxPages) page = maxPages;
		for(int i = 0; i < 9; i++) 
			if(i < (price.size() - 9*page) && shop[i + 9*page] == null) 
				shop[i + 9*page] = new ShopButton(price.get(i + 9*page), object.get(i + 9*page), x + 1 + (i/3)*54, y + i*36 - 3*(i/3)*36 - 8, false);
		for(int i = 0; i < 9; i++) 
			if(i < (price.size() - 9*page) && shop[i + 9*page] != null) 
				shop[i + 9*page].tick();
		
		if(player.specialDealTime <= 0)
		{
			specialDeal = new ShopButton(new CraftableStack(Block.air, 0), new CraftableStack(Block.air, 0), x + 1, y - 45, true);
			specialDealItem = -1;
		}
		if(specialDealItem >= 0 && Main.player.specialDealTime > 0) specialDeal.tick();
	}

	public void triggerTick()
	{
		if(keyDelay > 0) keyDelay--;
		if(Main.key != null && Key.shopMenu.isPressed() && keyDelay <= 0 && !Main.console.showChat)
		{
			keyDelay = 20;
			Main.inv.isOpen = false;
			Main.skl.closeGUI();
			Main.com.closeGUI();
			if(isOpen) closeGUI();
			else openGUI();
		}
	}

	public void render() 
	{
		if(player != null && isOpen)
		{
			Render.setColor(new Color(160, 160, 160, 255));
			Render.fillRect(x - 3, y - 12, 181, 109);
			Text.drawStringWithShadow(String.valueOf(page) + "/" + String.valueOf(maxPages), x + 161, y + Tile.tileSize*3, Color.WHITE, 8, Main.gameFont);
			if(specialDealItem >= 0 && Main.player.specialDealTime > 0) specialDeal.render();
			for(int i = 0; i < 9; i++) if(i < (price.size() - 9*page)) shop[i + 9*page].render();
			next.render();
			back.render();
			if(specialDealItem >= 0 && Main.player.specialDealTime > 0) Text.drawStringWithShadow("Time left: " + (Main.player.specialDealTime/10) + "(s)", x + 55, y - 15, Color.white, 10, Main.gameFont);
		}		
	}

	public void openGUI()
	{
		isOpen = true;
		super.openGUI();
	}

	public void closeGUI()
	{
		isOpen = false;
		super.closeGUI();
	}
	
	public static void addTrade(CraftableStack p, CraftableStack o)
	{
		price.add(p);
		object.add(o);
	}
	
	public static void addSpecialTrade(CraftableStack p, CraftableStack o)
	{
		specialPrice.add(p);
		specialObject.add(o);
	}
	
	public static void removeTrade(int i)
	{
		price.remove(i);
		object.remove(i);
	}
	
	public static List<CraftableStack> getPrice() 
	{
		return price;
	}

	public static void setPrice(int index, CraftableStack p) 
	{
		price.set(index, p);
	}

	static
	{
		//Trades
		addTrade(new CraftableStack(Item.coin, 7), new CraftableStack(Item.ironPick, 1));
		addTrade(new CraftableStack(Item.coin, 2), new CraftableStack(Item.bottle, 5));
		addTrade(new CraftableStack(Item.coin, 4), new CraftableStack(Item.nails, 3));
		addTrade(new CraftableStack(Item.coin, 3), new CraftableStack(Block.marble, 1));
		addTrade(new CraftableStack(Item.coin, 1), new CraftableStack(Block.glass, 1));
		addTrade(new CraftableStack(Item.coin, 20), new CraftableStack(Item.diamondDrill, 1));
		addTrade(new CraftableStack(Item.coin, 5), new CraftableStack(Item.copperNeckalce, 1));
		addTrade(new CraftableStack(Item.coin, 7), new CraftableStack(Block.mysticWorktable, 1));
		addTrade(new CraftableStack(Item.coin, 4), new CraftableStack(Item.woodenShears, 1));
		addTrade(new CraftableStack(Item.coin, 3), new CraftableStack(Item.charcoal, 1));
		
		//SpecialTrades
		addSpecialTrade(new CraftableStack(Item.coin, 50), new CraftableStack(Item.bebopBoots, 1));
		addSpecialTrade(new CraftableStack(Item.coin, 200), new CraftableStack(Item.cursersCursor, 1));
	}
}
