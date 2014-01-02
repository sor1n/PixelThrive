package net.PixelThrive.Client.GUI;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.blocks.BlockAndItem;
import net.PixelThrive.Client.renders.GuiIcons;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.world.Tile;

public class Category 
{
	private String label;
	private Slot cell;
	public boolean isHovering = false, isSelected = false, cap = false;
	public int x, y, x2, y2, page, maxPages;
	public List<Slot> menu = new ArrayList<Slot>();
	public List<Integer> pages = new ArrayList<Integer>();
	public Button next, back;
	private List<BlockAndItem> obj = new ArrayList<BlockAndItem>();

	public Category(String label, int itemID, int x, int y, List<BlockAndItem> list, int x2, int y2)
	{
		this.x = x;
		this.y = y;
		this.x2 = x2;
		this.y2 = y2;
		this.label = label;
		cell = new Slot(new Rectangle(x, y, Tile.invCellSize, Tile.invCellSize), itemID, 99, 100, 100, 100, true);
		obj = list;
		for(int i = 0; i < 25; i++)
			if((i < obj.size()))
				menu.add(new Slot(new Rectangle((x2 - 1) + i * (Tile.tileSize + 3) - 5 * (i/5) * (Tile.tileSize + 3) + 63, (y2 - 1) + (i/5)*(Tile.tileSize + 3), Tile.invCellSize, Tile.invCellSize), obj.get(i).ID, 99, 100, 100, 100, true, true));
		next = new Button(x2 + 159, y2 + 1, 15, 15, x2 + 159, (int)y2, x2 + Tile.tileSize + 159, y2 + 20, GuiIcons.next, GuiIcons.nextS);
		back = new Button(x2 + 159, y2 + Tile.tileSize*4 + 1, 15, 15, x2 + 159, y2 + Tile.tileSize*3 + 20, x2 + Tile.tileSize + 159, y2 + Tile.tileSize*4 + 20, GuiIcons.back, GuiIcons.backS);
	}

	public String getLabel()
	{
		return label;
	}

	public void tick()
	{	
		if(isSelected) 
			for(int i = 0; i < menu.size(); i++)
			{
				menu.get(i).tick();
				next.tick();
				back.tick();
			}
		for(int j = 0; j < menu.size(); j++) if(menu.get(j).id == 0) menu.remove(j);

		if(next.isClicked() && page < maxPages){			
			page++;
			onClick();
		}
		if(back.isClicked() && page > 0){
			page--;
			onClick();
		}
		if(page > maxPages) page = maxPages;
		maxPages = (obj.size() - 1)/25;

		if(cell.contains(new Point(Main.mouseX, Main.mouseY)))
		{
			isHovering = true;
			if(Main.key != null && Main.key.isMouseLeft) isSelected = true;
		}
		else isHovering = false;
	}
	
	void onClick()
	{
		for(int j = 0; j < menu.size(); j++) menu.get(j).removeItemFromSlot();
		for(int i = 0; i < 25; i++)
			if((i < obj.size() - 25*page))
				menu.add(new Slot(new Rectangle((x2 - 1) + i * (Tile.tileSize + 3) - 5 * (i/5) * (Tile.tileSize + 3) + 63, (y2 - 1) + (i/5)*(Tile.tileSize + 3), Tile.invCellSize, Tile.invCellSize), obj.get(i + 25*page).ID, 99, 100, 100, 100, true, true));
	}

	public void render()
	{
		cell.render(false);
		if(isSelected)
		{
			for(int i = 0; i < menu.size(); i++) menu.get(i).render(false);
			next.render();
			back.render();
		}
		if(isSelected)
		{			
			Text.drawStringWithShadow(String.valueOf(page), (int)x2 + 166, (int)y2 + 36, Color.WHITE, 8, Main.gameFont);
			Text.drawStringWithShadow("/", (int)x2 + 165, (int)y2 + 45, Color.WHITE, 8, Main.gameFont);
			Text.drawStringWithShadow(String.valueOf(maxPages), (int)x2 + 166, (int)y2 + 54, Color.WHITE, 8, Main.gameFont);
		}
		for(int i = 0; i < menu.size(); i++)
		{
			if(isSelected && menu.get(i).contains(Main.mouseX, Main.mouseY))
			{
				Render.setFont(Main.gameFont, 9);
				String name = BlockAndItem.getItemOrBlockName(menu.get(i).id).substring(0, 1).toUpperCase() + BlockAndItem.getItemOrBlockName(menu.get(i).id).substring(1);
				Render.setColor(new Color(60, 60, 60, 255));
				Render.fillRect((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 4, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 10, Render.getFontMetrics().stringWidth(name) + 3, 12);
				Render.setColor(Color.black);
				Render.drawRect((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 4, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 10, Render.getFontMetrics().stringWidth(name) + 3, 12);
				Render.setColor(new Color(20, 10, 50, 180));
				Text.drawStringWithShadow(name, (Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 1, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder, Color.WHITE, 9, Main.gameFont);
			}
		}
	}
}
