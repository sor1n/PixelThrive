package net.PixelThrive.Client.GUI;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.blocks.BlockAndItem;
import net.PixelThrive.Client.items.CraftableStack;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.world.Tile;

public class ShopButton
{
	private int x, y, keyDelay;
	private Button buy, buy2;
	private boolean canAfford, isSpecial, isHoveringPrice, isHoveringObject;
	private CraftableStack price, object;
	private Slot priceSlot, objectSlot;
	
	public ShopButton(CraftableStack price, CraftableStack object, int x, int y, boolean s)
	{
		this.x = x;
		this.y = y;
		this.price = price;
		this.object = object;
		this.isSpecial = s;
	}
	
	public void tick()
	{
		buy = new Button(x + 13, y + 20, 20, 10, "BUY", 0, 112, 191, 9);	
		buy2 = new Button(x + 13, y + 20, 20, 10, "BUY", 100, 100, 100, 9);
		priceSlot = new Slot(new Rectangle(x, y, Tile.invCellSize, Tile.invCellSize), price.getID(), 99, 100, 100, 100, true);
		objectSlot = new Slot(new Rectangle(x + Tile.tileSize + 13, y, Tile.invCellSize, Tile.invCellSize), object.getID(), 99, 100, 100, 100, true);
		
		if(Main.inv.hasItemInInventory(price.getID()) && Main.inv.getItemAmountOfItemInInventory(price.getID()) >= price.getAmount()) canAfford = true;
		else canAfford = false;
		
		if(canAfford) buy.tick();
		else buy2.tick();
		
		if(keyDelay > 0) keyDelay--;
		if(buy.isClicked() && keyDelay <= 0)
		{			
			keyDelay = 20;
			Main.inv.removeItemsFromInventory(price.getID(), price.getAmount());
			Main.inv.giveItem(object);			
		}

		if(priceSlot.contains(new Point(Main.mouseX, Main.mouseY))) isHoveringPrice = true;
		else isHoveringPrice = false;

		if(objectSlot.contains(new Point(Main.mouseX, Main.mouseY))) isHoveringObject = true;
		else isHoveringObject = false;
	}
	
	public void render()
	{
		if(isSpecial)
		{
			Render.setColor(Color.black);
			Render.fillRect(x - 4, y - 4, 55, 37);
		}
		if(!isSpecial) Render.setColor(new Color(214, 214, 214));
		else Render.setColor(new Color(255, Main.inv.b, 0));
		Render.fillRect(x - 3, y - 3, 53, 35);
		if(canAfford) buy.render();
		else buy2.render();
		priceSlot.render(false);
		objectSlot.render(false);
		if(price.getAmount() > 1) Text.drawStringWithShadow(String.valueOf(price.getAmount()), x + 1, y + 16, Color.WHITE, 8, Main.gameFont);
		if(object.getAmount() > 1) Text.drawStringWithShadow(String.valueOf(object.getAmount()), x + Tile.tileSize*2, y + 16, Color.WHITE, 8, Main.gameFont);
		if(isHoveringPrice)
		{
			Render.setFont(Main.gameFont, 9);
			Render.setColor(new Color(60, 60, 60, 255));
			Render.fillRect((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 4, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 10, Render.getFontMetrics().stringWidth(BlockAndItem.getItemOrBlockName(price.getID()).substring(0, 1).toUpperCase() + BlockAndItem.getItemOrBlockName(price.getID()).substring(1)) + 3, 12);
			Render.setColor(Color.black);
			Render.drawRect((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 4, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 10, Render.getFontMetrics().stringWidth(BlockAndItem.getItemOrBlockName(price.getID()).substring(0, 1).toUpperCase() + BlockAndItem.getItemOrBlockName(price.getID()).substring(1)) + 3, 12);
			Render.setColor(new Color(20, 10, 50, 180));
			Text.drawStringWithShadow(BlockAndItem.getItemOrBlockName(price.getID()).substring(0, 1).toUpperCase() + BlockAndItem.getItemOrBlockName(price.getID()).substring(1), (Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 1, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder, Color.WHITE, 9, Main.gameFont);
		}
		if(isHoveringObject)
		{
			Render.setFont(Main.gameFont, 9);
			Render.setColor(new Color(60, 60, 60, 255));
			Render.fillRect((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 4, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 10, Render.getFontMetrics().stringWidth(BlockAndItem.getItemOrBlockName(object.getID()).substring(0, 1).toUpperCase() + BlockAndItem.getItemOrBlockName(object.getID()).substring(1)) + 3, 12);
			Render.setColor(Color.black);
			Render.drawRect((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 4, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 10, Render.getFontMetrics().stringWidth(BlockAndItem.getItemOrBlockName(object.getID()).substring(0, 1).toUpperCase() + BlockAndItem.getItemOrBlockName(object.getID()).substring(1)) + 3, 12);
			Render.setColor(new Color(20, 10, 50, 180));
			Text.drawStringWithShadow(BlockAndItem.getItemOrBlockName(object.getID()).substring(0, 1).toUpperCase() + BlockAndItem.getItemOrBlockName(object.getID()).substring(1), (Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 1, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder, Color.WHITE, 9, Main.gameFont);
		}
	}

}
