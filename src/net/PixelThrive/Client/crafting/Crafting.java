package net.PixelThrive.Client.crafting;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import net.PixelThrive.Client.*;
import net.PixelThrive.Client.GUI.Button;
import net.PixelThrive.Client.GUI.GUI;
import net.PixelThrive.Client.GUI.Inventory;
import net.PixelThrive.Client.GUI.Slot;
import net.PixelThrive.Client.GUI.Text;
import net.PixelThrive.Client.Input.Key;
import net.PixelThrive.Client.blocks.*;
import net.PixelThrive.Client.entities.Drop;
import net.PixelThrive.Client.items.*;
import net.PixelThrive.Client.renders.GuiIcons;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.Tile;
import java.util.*;
import java.awt.event.*;

public class Crafting extends GUI
{
	public static Slot[] craftingGrid = new Slot[15];
	public Slot[] results = new Slot[25];
	public Button next, back;
	public List<CraftableStack> result = new ArrayList<CraftableStack>();
	public boolean[] craftMultiple;

	public int x, y, page, maxPages, no;

	public Crafting(int x, int y)
	{
		this.x = x;
		this.y = y;
		for(int i = 0; i < craftingGrid.length; i++)
			craftingGrid[i] = new Slot(new Rectangle(x + i*(Tile.tileSize + 3) - 3*(i/3)*(Tile.tileSize + 3), y + (i/3)*(Tile.tileSize + 3), Tile.invCellSize, Tile.invCellSize), Block.air.blockID, 99, 100, 100, 100, true);
		for(int i = 0; i < results.length; i++)
			results[i] = new Slot(new Rectangle(x + i*(Tile.tileSize + 3) - 5*(i/5)*(Tile.tileSize + 3) + 63, y + (i/5)*(Tile.tileSize + 3), Tile.invCellSize, Tile.invCellSize), Block.air.blockID, 99, 240, 240, 240, true);
		next = new Button(x + 159, y + 1, 15, 15, x + 159, (int)y, x + Tile.tileSize + 159, y + 20, GuiIcons.next, GuiIcons.nextS);
		back = new Button(x + 159, y + Tile.tileSize*4 + 1, 15, 15, x + 159, y + Tile.tileSize*3 + 20, x + Tile.tileSize + 159, y + Tile.tileSize*4 + 20, GuiIcons.back, GuiIcons.backS);
	}

	public void tick() 
	{	
		if(Main.inv.isNearCraftingBench) no = craftingGrid.length;
		else no = craftingGrid.length - 12;
		if(no == craftingGrid.length)
			for(int i = 0; i < craftingGrid.length; i++) craftingGrid[i].setAvailable(true);
		else{
			for(int i = 0; i < craftingGrid.length - 12; i++) craftingGrid[i].setAvailable(true);
			for(int i = craftingGrid.length - 12; i < craftingGrid.length; i++) craftingGrid[i].setAvailable(false);
		}
		for(int l = 0; l < Recipes.getRecipes().size(); l++)
		{
			CraftableStack[] cr = Recipes.getRecipes().get(l);
			CraftableStack re = Recipes.getResults().get(l);
			int tileEntities = Recipes.getTileEntities().get(l);
			craftMultiple = new boolean[cr.length];
			int[] bm = new int[craftingGrid.length];
			for(int m = 0; m < cr.length; m++)
			{
				result.remove(re);
				for(int i = 0; i < craftingGrid.length; i++){
					if(craftingGrid[i].id == cr[m].getID() && (tileEntities == 0 || (tileEntities == 1 && Main.inv.isNearCraftingBench) || (tileEntities == 2 && Main.inv.isNearFurnace) || (tileEntities == 3 && Main.inv.isNearMysticWorktable))) 
						bm[i] += (craftingGrid[i].itemAmount + 1);
					if(craftingGrid[i].id == cr[m].getID() && bm[i] >= cr[m].getAmount() && !result.contains(re) && (tileEntities == 0 || (tileEntities == 1 && Main.inv.isNearCraftingBench) || (tileEntities == 2 && Main.inv.isNearFurnace) || (tileEntities == 3 && Main.inv.isNearMysticWorktable)))
						craftMultiple[m] = true;
				}
			}
			int cap = 0;
			for(int m = 0; m < cr.length; m++)
			{
				if(!craftMultiple[m])break;
				cap++;
			}
			if(cap >= cr.length) result.add(re);
			maxPages = (result.size() - 1)/results.length;
		}
		if(result.size() <= 0)
		{
			for(int k = 0; k < results.length; k++) results[k].empty();
		}
		else
		{
			for(int i = 0; i < results.length; i++)
			{
				for(int l = 0; l < Recipes.getRecipes().size(); l++)
				{
					CraftableStack[] cr = Recipes.getRecipes().get(l);
					CraftableStack re = Recipes.getResults().get(l);
					boolean clear = false;
					for(int m = 0; m < cr.length; m++)
					{
						if(results[i].id == re.getID() && (results[i].itemAmount + 1) >= re.getAmount())
						{
							clear = true;
							break;
						}
					}
					if(clear) results[i].empty();
				}
			}
		}

		for(int i = 0; i < results.length; i++)
		{
			if(i < (result.size() - 25*page))
			{
				if(results[i].id == result.get(i + 25*page).getID() && results[i].itemAmount == result.get(i + 25*page).getAmount() - 1) break;
				if(results[i].id == Block.air.blockID && result.get(i + 25*page).getID() != 0)
				{
					results[i].id = result.get(i + 25*page).getID();
					results[i].itemAmount = result.get(i + 25*page).getAmount() - 1;
				}
			}
		}
		if(!Main.inv.isNearCraftingBench)
		{
			for(int i = no; i < craftingGrid.length; i++)
				if(craftingGrid[i].id != 0){
					new Drop((int)Main.player.getX() + 4, (int)Main.player.getY() + 4, craftingGrid[i].id, craftingGrid[i].itemAmount).spawnEntity();
					craftingGrid[i].empty();
				}
		}
		next.tick();
		back.tick();
		if((next.isClicked() || Key.nextPage.isPressed()) && page < maxPages) page++;
		if((back.isClicked() || Key.previousPage.isPressed()) && page > 0) page--;
		if(page > maxPages) page = maxPages;
	}

	public void click(MouseEvent e)
	{
		Inventory inv = Main.inv;
		if(e.getButton() == 1)
		{	
			for(int i = 0; i < results.length; i++)
			{
				Slot res = results[i];
				if(res.contains(new Point(Main.mouseX, Main.mouseY)) && res.id != 0)
				{
					if(!inv.isHolding)
					{
						inv.holdingID = res.id;
						inv.holdingAmount = -1;
						inv.isHolding = true;
					}
					if(inv.holdingID == res.id)
					{
						if((inv.holdingAmount + res.itemAmount) < (BlockAndItem.getMaxItemOrBlockStackSize(inv.holdingID) - 1))
						{
							inv.holdingAmount += (res.itemAmount + 1);
							for(int m = 0; m < Recipes.getRecipes().size(); m++)
							{
								CraftableStack[] cr = Recipes.getRecipes().get(m);
								CraftableStack re = Recipes.getResults().get(m);
								if(re.getAmount() == (res.itemAmount + 1) && re.getID() == res.id)
								{	
									for(int k = 0; k < cr.length; k++) removeForItem(cr[k].getID(), cr[k].getAmount());
								}
							}
						}
					}
				}
			}

			for(int i = 0; i < no; i++)
				if(craftingGrid[i].contains((new Point(Main.mse.x / Main.SCALE, Main.mse.y / Main.SCALE))))
				{
					if(craftingGrid[i].id != Block.air.blockID && !inv.isHolding)
					{
						inv.holdingID = craftingGrid[i].id;
						inv.holdingAmount = craftingGrid[i].itemAmount;
						craftingGrid[i].itemAmount = 0;
						craftingGrid[i].id = Block.air.blockID;

						inv.isHolding = true;
						break;
					}
					else if(inv.isHolding && craftingGrid[i].id == Block.air.blockID)
					{
						craftingGrid[i].id = inv.holdingID;
						craftingGrid[i].itemAmount = inv.holdingAmount;
						inv.holdingAmount = 0;
						inv.isHolding = false;
						break;
					}
					else if(inv.isHolding && craftingGrid[i].id != Block.air.blockID)
					{
						if(inv.holdingID != craftingGrid[i].id && inv.holdingID != Block.air.blockID)
						{
							int con = craftingGrid[i].id;
							craftingGrid[i].id = inv.holdingID;
							int oldAmount = inv.holdingAmount;
							inv.holdingAmount = craftingGrid[i].itemAmount;
							craftingGrid[i].itemAmount = oldAmount;
							inv.holdingID = con;
							break;
						}
						else if(inv.holdingID == craftingGrid[i].id && inv.holdingID != Block.air.blockID)
						{
							if(inv.holdingID >= Block.IDs.length)
							{
								if((inv.holdingAmount + craftingGrid[i].itemAmount) < (Item.getItem(inv.holdingID).getMaxStackSize() - 1))
								{
									craftingGrid[i].itemAmount += (inv.holdingAmount + 1);
									inv.holdingAmount = 0;
									inv.isHolding = false;
									break;
								}
								else
								{
									inv.holdingAmount -= ((Item.getItem(inv.holdingID).getMaxStackSize() - 1) - craftingGrid[i].itemAmount);
									craftingGrid[i].itemAmount = (Item.getItem(inv.holdingID).getMaxStackSize() - 1);
									break;
								}
							}
							else
							{
								if((inv.holdingAmount + craftingGrid[i].itemAmount) < (Block.getBlock(inv.holdingID).getMaxStackSize() - 1))
								{
									craftingGrid[i].itemAmount += (inv.holdingAmount + 1);
									inv.holdingAmount = 0;
									inv.isHolding = false;
									break;
								}
								else
								{
									inv.holdingAmount -= ((Block.getBlock(inv.holdingID).getMaxStackSize() - 1) - craftingGrid[i].itemAmount);
									craftingGrid[i].itemAmount = (Block.getBlock(inv.holdingID).getMaxStackSize() - 1);
									break;
								}
							}
						}
					}
				}
		}
		else if(e.getButton() == 3)
		{
			for(int i = 0; i < no; i++)
			{
				if(craftingGrid[i].contains((new Point(Main.mse.x / Main.SCALE, Main.mse.y / Main.SCALE))))
				{
					if(craftingGrid[i].id != Block.air.blockID && !inv.isHolding && craftingGrid[i].itemAmount > 0)
					{
						int old = craftingGrid[i].itemAmount + 1;
						int a = old / 2;
						int b = 0;

						if((a * 2) != old)
						{
							b = a;
							craftingGrid[i].itemAmount = a - 1;
							inv.holdingID = craftingGrid[i].id;
							inv.holdingAmount = b;
							inv.isHolding = true;
						}
						else
						{
							craftingGrid[i].itemAmount = a - 2;
							inv.holdingID = craftingGrid[i].id;
							inv.holdingAmount = a;
							inv.isHolding = true;
						}
					}
					if(inv.isHolding)
					{
						if(craftingGrid[i].id == Block.air.blockID)
						{
							if(inv.holdingAmount > 0)
							{
								craftingGrid[i].id = inv.holdingID;
								craftingGrid[i].itemAmount = 0;
								inv.holdingAmount--;
								break;
							}
							else
							{
								craftingGrid[i].id = inv.holdingID;
								craftingGrid[i].itemAmount = 0;
								inv.holdingID = Block.air.blockID;
								inv.holdingAmount = 0;
								inv.isHolding = false;
								break;
							}
						}
						else
						{
							if(inv.holdingID == craftingGrid[i].id)
							{
								if(inv.holdingAmount > 0)
								{
									if(inv.holdingID >= Block.IDs.length)
									{
										if(craftingGrid[i].itemAmount < (Item.getItem(inv.holdingID).getMaxStackSize() - 1))
										{
											craftingGrid[i].itemAmount++;
											inv.holdingAmount--;
										}
									}
									else
									{
										if(craftingGrid[i].itemAmount < (Block.getBlock(inv.holdingID).getMaxStackSize() - 1))
										{
											craftingGrid[i].itemAmount++;
											inv.holdingAmount--;
										}
									}
									break;
								}
								else
								{
									if(inv.holdingID >= Block.IDs.length)
									{
										if(craftingGrid[i].itemAmount < (Item.getItem(inv.holdingID).getMaxStackSize() - 1))
										{
											craftingGrid[i].itemAmount++;
											inv.holdingID = Block.air.blockID;
											inv.holdingAmount = 0;
											inv.isHolding = false;
										}
									}
									else
									{
										if(craftingGrid[i].itemAmount < (Block.getBlock(inv.holdingID).getMaxStackSize() - 1))
										{
											craftingGrid[i].itemAmount++;
											inv.holdingID = Block.air.blockID;
											inv.holdingAmount = 0;
											inv.isHolding = false;
										}
									}
									break;
								}
							}
							else
							{
								int sid = inv.holdingID;
								int samount = inv.holdingAmount;
								inv.holdingID = craftingGrid[i].id;
								inv.holdingAmount = craftingGrid[i].itemAmount;
								craftingGrid[i].id = sid;
								craftingGrid[i].itemAmount = samount;
								break;
							}
						}
					}
				}
			}
		}
	}

	public void render()
	{
		Render.setColor(new Color(20, 20, 20, 150));
		Render.fillRect(x - 3, y - 3, 180, 100);

		Render.setColor(new Color(20, 20, 20, 200));
		Render.fillRect(x + 58, y - 3, 2, 100);

		for(int i = 0; i < craftingGrid.length; i++) craftingGrid[i].render(false);	

		//Specials		
		int posX = 159, posY = 20;
		if(Main.inv.isNearCraftingBench)Render.drawImage(SpriteSheet.GUI.getImage(), (int)x + posX, (int)y + posY, (int)x + Tile.tileSize + posX, (int)y + Tile.tileSize + posY, GuiIcons.craftingBenchOverlay);
		Render.drawImage(SpriteSheet.GUI.getImage(), (int)x + posX, (int)y + posY, (int)x + Tile.tileSize + posX, (int)y + Tile.tileSize + posY, GuiIcons.craftingBench);

		if(Main.inv.isNearFurnace)Render.drawImage(SpriteSheet.GUI.getImage(), (int)x + posX, (int)y + Tile.tileSize + posY, (int)x + Tile.tileSize + posX, (int)y + Tile.tileSize*2 + posY, GuiIcons.furnaceOverlay);
		Render.drawImage(SpriteSheet.GUI.getImage(), (int)x + posX, (int)y + posY + Tile.tileSize, (int)x + Tile.tileSize + posX, (int)y + Tile.tileSize*2 + posY, GuiIcons.furnace);

		if(Main.inv.isNearMysticWorktable)Render.drawImage(SpriteSheet.GUI.getImage(), (int)x + posX, (int)y + Tile.tileSize*2 + posY, (int)x + Tile.tileSize + posX, (int)y + Tile.tileSize*3 + posY, GuiIcons.mysticalWorktableOverlay);
		Render.drawImage(SpriteSheet.GUI.getImage(), (int)x + posX, (int)y + Tile.tileSize*2 + posY, (int)x + Tile.tileSize + posX, (int)y + Tile.tileSize*3 + posY, GuiIcons.mysticalWorktable);

		next.render();
		back.render();

		for(int r = 0; r < craftingGrid.length; r++)
			if((craftingGrid[r].contains(new Point(Main.mouseX, Main.mouseY)) && Main.inv.isOpen))
			{
				int id = craftingGrid[r].id;
				Render.setFont(Main.gameFont, 10);
				for(int m = 0; m < Block.blocks.size(); m++)
					if(id != Block.air.blockID && id == Block.blocks.get(m).blockID)
					{
						Render.setFont(Main.gameFont, 9);
						String name = Block.blocks.get(m).getName().toUpperCase().substring(0, 1) + Block.blocks.get(m).getName().substring(1);
						Render.setColor(new Color(60, 60, 60, 255));
						Render.fillRect((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 4, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 10, Render.getFontMetrics().stringWidth(name) + 3, 12);
						Render.setColor(Color.black);
						Render.drawRect((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 4, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 10, Render.getFontMetrics().stringWidth(name) + 3, 12);
						Text.drawStringWithShadow(name, (Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 1, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder, Color.WHITE, 9, Main.gameFont);
					}
				for(int m = 0; m < Item.items.size(); m++)
					if(id != Block.air.blockID && id == Item.items.get(m).itemID)
					{
						Render.setFont(Main.gameFont, 9);
						String name = Item.items.get(m).getName().toUpperCase().substring(0, 1) + Item.items.get(m).getName().substring(1);
						Render.setColor(new Color(10, 10, 10, 100));
						Render.fillRect((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 4, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 10, Render.getFontMetrics().stringWidth(name) + 3, 12);
						Render.setColor(Color.black);
						Render.drawRect((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 4, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 10, Render.getFontMetrics().stringWidth(name) + 3, 12);
						Text.drawStringWithShadow(name, (Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 1, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder, Color.WHITE, 9, Main.gameFont);
					}
			}

		for(int i = 0; i < results.length; i++) if(results[i].id != Block.air.blockID) results[i].render(true);
		for(int i = 0; i < results.length; i++)
			if(results[i].id != 0 && results[i].contains(new Point(Main.mouseX, Main.mouseY)))
			{
				Render.setFont(Main.gameFont, 9);
				String name = BlockAndItem.getItemOrBlockName(results[i].id).toUpperCase().substring(0, 1) + BlockAndItem.getItemOrBlockName(results[i].id).substring(1);
				Render.setColor(new Color(60, 60, 60));
				Render.fillRect((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 4, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 55, 100, 55);
				Render.setColor(new Color(150, 150, 150));
				Render.drawRect((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 4, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 55, 100, 55);
				Text.drawStringWithShadow(name, (Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder + 3, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 45, Color.WHITE, 9, Main.gameFont);
				for(int l = 0; l < Recipes.getRecipes().size(); l++)
				{
					CraftableStack[] cr = Recipes.getRecipes().get(l);
					CraftableStack re = Recipes.getResults().get(l);
					if(re.getID() == results[i].id && re.getAmount() == (results[i].itemAmount + 1))
					{
						int am = cr.length;
						Slot[] crafts = new Slot[am];
						for(int m = 0; m < cr.length; m++)
						{
							crafts[m] = new Slot(new Rectangle((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder + 4 + (m * (Tile.invCellSize + Tile.invCellSpace)), (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 35, Tile.invCellSize, Tile.invCellSize), cr[m].getID(), BlockAndItem.getMaxItemOrBlockStackSize(cr[m].getID()), 2, 2, 2, false);
							crafts[m].itemAmount = cr[m].getAmount() - 1;
							crafts[m].render(false);
						}
					}
				}
			}

		Text.drawStringWithShadow(String.valueOf(page), (int)x + 159, (int)y + 95, Color.WHITE, 8, Main.gameFont);
		Text.drawStringWithShadow("/", (int)x + 165, (int)y + 95, Color.WHITE, 8, Main.gameFont);
		Text.drawStringWithShadow(String.valueOf(maxPages), (int)x + 173, (int)y + 95, Color.WHITE, 8, Main.gameFont);
	}

	public void removeForItem(int id, int amount)
	{
		for(int i = 0; i < no; i++)
		{
			if(craftingGrid[i].id == id && (craftingGrid[i].itemAmount + 1) >= amount)
			{
				craftingGrid[i].itemAmount -= amount;
				if(craftingGrid[i].itemAmount <= -1) craftingGrid[i].empty();
				return;
			}
		}
	}

	public static Slot[] getCraftingGrid()
	{
		return craftingGrid;
	}
}