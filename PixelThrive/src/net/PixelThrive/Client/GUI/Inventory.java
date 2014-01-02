package net.PixelThrive.Client.GUI;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.PixelThrive.Client.Input.Key;
import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.blocks.BlockAndItem;
import net.PixelThrive.Client.crafting.Crafting;
import net.PixelThrive.Client.entities.Drop;
import net.PixelThrive.Client.items.CraftableStack;
import net.PixelThrive.Client.items.Item;
import net.PixelThrive.Client.renders.GuiIcons;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.Tile;

public class Inventory extends GUI
{
	public Slot[] invBar = new Slot[Tile.invLength];
	public Slot[] invBag = new Slot[Tile.invLength * Tile.invHeight];
	public Slot[] invArmor = new Slot[3];
	public Slot[] invVanity = new Slot[3];
	public Slot[] invTrash = new Slot[1];
	public Slot[] invJewelry = new Slot[4];
	public Slot[] invTrinkets = new Slot[3];
	public Slot[][] fullInv = {invBar, invBag, invArmor, invVanity, invTrash, invJewelry, invTrinkets};

	public boolean isOpen = false;
	public boolean isHolding = false;
	public boolean isNearCraftingBench, itemFunc, isNearFurnace, isNearMysticWorktable;

	public int selection = 0;
	public int holdingID = Block.air.blockID;
	public int holdingAmount = 0, b;

	private int keyDelay = 0;
	public Crafting craftingMenu = new Crafting(5, Main.HEIGHT - 99);
	public CreativeMenu creativeMenu = new CreativeMenu(5, Main.HEIGHT - 99);
	public CornerButtons buttons = new CornerButtons();

	private List<ItemFunctionButton> funcButtons = new CopyOnWriteArrayList<ItemFunctionButton>();
	private boolean isIncreasing = true;

	public synchronized List<ItemFunctionButton> getFuncButtons()
	{
		return funcButtons;
	}

	public Inventory()
	{	
		for(int i = 0; i < invBar.length; i++) invBar[i] = new Slot(new Rectangle((i * (Tile.invCellSize + Tile.invCellSpace) + (Main.WIDTH - 5 - ((invBar.length * (Tile.invCellSize + Tile.invCellSpace))))), Main.pixel.height - (Tile.invCellSize + Tile.invBorderSpace), Tile.invCellSize, Tile.invCellSize), Block.air.blockID, 99, 255, 255, 255, false);

		int x = 0, y = 0;
		for(int i = 0; i < invBag.length; i++)
		{
			invBag[i] = new Slot(new Rectangle((x * (Tile.invCellSize + Tile.invCellSpace) + (Main.WIDTH - 5 - ((invBar.length * (Tile.invCellSize + Tile.invCellSpace))))), (y * (Tile.invCellSize + Tile.invCellSpace) + Main.HEIGHT - 82), Tile.invCellSize, Tile.invCellSize), Block.air.blockID, 99, 255, 255, 255, false);
			x++;
			if(x >= Tile.invLength)
			{
				x = 0;
				y++;
			}
		}

		for(int i = 0; i < invArmor.length; i++)invArmor[i] = new Slot(new Rectangle((i*(Tile.invCellSize + Tile.invCellSpace) + (Main.WIDTH - 5 - ((invBar.length * (Tile.invCellSize + Tile.invCellSpace))))), ((Tile.invCellSize + Tile.invCellSpace) + Main.HEIGHT - 82 - 2*(Tile.invCellSize + Tile.invCellSpace)), Tile.invCellSize, Tile.invCellSize), Block.air.blockID, 99, 81, 122, 255, true, new int[] {i, 0});
		for(int i = 0; i < invJewelry.length; i++)invJewelry[i] = new Slot(new Rectangle((i*(Tile.invCellSize + Tile.invCellSpace) + (Main.WIDTH - 5 - ((invBar.length * (Tile.invCellSize + Tile.invCellSpace))))), ((Tile.invCellSize + Tile.invCellSpace) + Main.HEIGHT - 82 - 3*(Tile.invCellSize + Tile.invCellSpace)), Tile.invCellSize, Tile.invCellSize), Block.air.blockID, 99, 255, 0, 0, true, new int[] {1 + i, 1});
		for(int i = 0; i < invVanity.length; i++)invVanity[i] = new Slot(new Rectangle(((i+3)*(Tile.invCellSize + Tile.invCellSpace) + (Main.WIDTH - 5 - ((invBar.length * (Tile.invCellSize + Tile.invCellSpace))))), ((Tile.invCellSize + Tile.invCellSpace) + Main.HEIGHT - 82 - 2*(Tile.invCellSize + Tile.invCellSpace)), Tile.invCellSize, Tile.invCellSize), Block.air.blockID, 99, 255, 174, 0, true, new int[] {3 + i, 0});
		for(int i = 0; i < invTrinkets.length; i++)invTrinkets[i] = new Slot(new Rectangle(((i+4)*(Tile.invCellSize + Tile.invCellSpace) + (Main.WIDTH - 5 - ((invBar.length * (Tile.invCellSize + Tile.invCellSpace))))), ((Tile.invCellSize + Tile.invCellSpace) + Main.HEIGHT - 82 - 3*(Tile.invCellSize + Tile.invCellSpace)), Tile.invCellSize, Tile.invCellSize), Block.air.blockID, 99, 0, 127, 14, true, new int[] {0, 1});
		invTrash[0] = new Slot(new Rectangle((6 * (Tile.invCellSize + Tile.invCellSpace) + (Main.WIDTH - 5 - ((invBar.length * (Tile.invCellSize + Tile.invCellSpace))))), ((Tile.invCellSize + Tile.invCellSpace) + Main.HEIGHT - 82 - 2*(Tile.invCellSize + Tile.invCellSpace)), Tile.invCellSize, Tile.invCellSize), Block.air.blockID, 99, 150, 150, 150, true, new int[] {5, 1});
		openGUI();
		initScroll();
	}

	public void initScroll()
	{
		getFuncButtons().clear();
		for(int m = 0; m < invBar.length; m++)
		{
			if(BlockAndItem.getItemOrBlock(invBar[selection].id).getFunctions() != null)
			{
				for(int i = 0; i < BlockAndItem.getItemOrBlock(invBar[selection].id).getFunctions().length; i++) getFuncButtons().add(new ItemFunctionButton(invBar[selection].x + (Tile.invCellSize / 2) - (ItemFunctionButton.getSize() / 2), (invBar[0].y - (ItemFunctionButton.getSize() + 4)) - (i * (ItemFunctionButton.getSize() + 4)), BlockAndItem.getItemOrBlock(invBar[selection].id).getFunctions()[i], false));
			}
			selection++;
		}
		selection = 0;
		getFuncButtons().clear();
	}

	public void scroll()
	{
		getFuncButtons().clear();
		if(BlockAndItem.getItemOrBlock(invBar[selection].id).getFunctions() != null) for(int i = 0; i < BlockAndItem.getItemOrBlock(invBar[selection].id).getFunctions().length; i++) getFuncButtons().add(new ItemFunctionButton(invBar[selection].x + (Tile.invCellSize / 2) - (ItemFunctionButton.getSize() / 2), (invBar[0].y - (ItemFunctionButton.getSize() + 4)) - (i * (ItemFunctionButton.getSize() + 4)), BlockAndItem.getItemOrBlock(invBar[selection].id).getFunctions()[i], (BlockAndItem.getItemOrBlock(invBar[selection].id).getCurrentFunction() == BlockAndItem.getItemOrBlock(invBar[selection].id).getFunctions()[i])));
	}

	public void click(MouseEvent e)
	{	
		if(isOpen && !Main.player.isCreative) craftingMenu.click(e);

		if(e.getButton() == 1)
		{	
			for(int j = 0; j < fullInv.length; j ++)
				for(int i = 0; i < fullInv[j].length; i++)
				{
					if((isOpen || fullInv[j] == invBar) && fullInv[j][i].contains((new Point(Main.mse.x / Main.SCALE, Main.mse.y / Main.SCALE))) && Main.key.isMouseLeft && !Key.itemFuncButton.isPressed())
					{
						if(fullInv[j] == invTrash)
						{
							if(isHolding)
							{
								invTrash[0].id = holdingID;
								invTrash[0].itemAmount = holdingAmount;
								holdingID = Block.air.blockID;
								holdingAmount = 0;
								isHolding = false;
								break;
							}
							else
							{
								if(invTrash[0].id != Block.air.blockID)
								{
									isHolding = true;
									holdingID = invTrash[0].id;
									holdingAmount = invTrash[0].itemAmount;
									invTrash[0].id = Block.air.blockID;
									invTrash[0].itemAmount = 0;
									break;
								}
							}
						}
						if(!Key.transportKey.isPressed())
						{
							if(fullInv[j][i].id != Block.air.blockID && !isHolding)
							{
								holdingID = fullInv[j][i].id;
								holdingAmount = fullInv[j][i].itemAmount;
								fullInv[j][i].itemAmount = 0;
								fullInv[j][i].id = Block.air.blockID;

								isHolding = true;
								break;
							}
							else if(isHolding && fullInv[j][i].id == Block.air.blockID && (((holdingID >= Block.IDs.length && fullInv[j][i] == Item.getItem(holdingID).getSpecial()) || (holdingID < Block.IDs.length && fullInv[j][i] == Block.getBlock(holdingID).getSpecial())) || (fullInv[j] != invArmor && fullInv[j] != invTrinkets && fullInv[j] != invVanity && fullInv[j] != invJewelry)))
							{
								fullInv[j][i].id = holdingID;
								fullInv[j][i].itemAmount = holdingAmount;
								holdingAmount = 0;
								isHolding = false;
								break;
							}
							else if(isHolding && fullInv[j][i].id != Block.air.blockID && (((holdingID >= Block.IDs.length && fullInv[j][i] == Item.getItem(holdingID).getSpecial()) || (holdingID < Block.IDs.length && fullInv[j][i] == Block.getBlock(holdingID).getSpecial())) || (fullInv[j] != invArmor && fullInv[j] != invTrinkets && fullInv[j] != invVanity && fullInv[j] != invJewelry)))
							{
								if(holdingID != fullInv[j][i].id && holdingID != Block.air.blockID)
								{
									int con = fullInv[j][i].id;
									fullInv[j][i].id = holdingID;
									int oldAmount = holdingAmount;
									holdingAmount = fullInv[j][i].itemAmount;
									fullInv[j][i].itemAmount = oldAmount;
									holdingID = con;
									break;
								}
								else if(holdingID == fullInv[j][i].id && holdingID != Block.air.blockID)
								{
									if(holdingID >= Block.IDs.length)
									{
										if((holdingAmount + fullInv[j][i].itemAmount) < (Item.getItem(holdingID).getMaxStackSize() - 1))
										{
											fullInv[j][i].itemAmount += (holdingAmount + 1);
											holdingAmount = 0;
											isHolding = false;
											break;
										}
										else
										{
											holdingAmount -= ((Item.getItem(holdingID).getMaxStackSize() - 1) - fullInv[j][i].itemAmount);
											fullInv[j][i].itemAmount = (Item.getItem(holdingID).getMaxStackSize() - 1);
											break;
										}
									}
									else
									{
										if((holdingAmount + fullInv[j][i].itemAmount) < (Block.getBlock(holdingID).getMaxStackSize() - 1))
										{
											fullInv[j][i].itemAmount += (holdingAmount + 1);
											holdingAmount = 0;
											isHolding = false;
											break;
										}
										else
										{
											holdingAmount -= ((Block.getBlock(holdingID).getMaxStackSize() - 1) - fullInv[j][i].itemAmount);
											fullInv[j][i].itemAmount = (Block.getBlock(holdingID).getMaxStackSize() - 1);
											break;
										}
									}
								}
							}
						}
						else
						{
							invTrash[0].id = fullInv[j][i].id;
							invTrash[0].itemAmount = fullInv[j][i].itemAmount;
							fullInv[j][i].id = 0;
							fullInv[j][i].itemAmount = -1;
						}
					}
					else if(isHolding)
					{
						if((!GUI.isGUIOpen()) || (GUI.isGUIOpen() && Main.mouseX > GUI.GUIBounds))
						{
							if(isOpen && ((Main.mouseX < invBar[0].x) && (Main.mouseY < craftingMenu.y)))
							{
								if(Main.mouseX > (craftingMenu.x + ((Tile.invCellSize + Tile.invCellSpace) * 4)))
								{
									new Drop((int)Main.player.getX(), (int)Main.player.getY() - 2, holdingID, holdingAmount).spawnEntity();
									holdingAmount = 0;
									isHolding = false;
								}
							}
							else if(!isOpen && ((Main.mouseX < invBar[0].x) || (Main.mouseY < invBar[0].y)))
							{
								new Drop((int)Main.player.getX(), (int)Main.player.getY() - 2, holdingID, holdingAmount).spawnEntity();
								holdingAmount = 0;
								isHolding = false;
							}
						}
					}
				}
		}
		else if(e.getButton() == 3)
		{
			for(int j = 0; j < fullInv.length; j ++)
				for(int i = 0; i < fullInv[j].length; i++)
				{
					if((isOpen || fullInv[j] == invBar) && fullInv[j][i].contains((new Point(Main.mse.x / Main.SCALE, Main.mse.y / Main.SCALE))))
					{
						if(fullInv[j][i].id != Block.air.blockID && !isHolding && fullInv[j][i].itemAmount > 0)
						{
							int old = fullInv[j][i].itemAmount + 1;
							int a = old / 2;
							int b = 0;

							if((a * 2) != old)
							{
								b = a;
								fullInv[j][i].itemAmount = a - 1;
								holdingID = fullInv[j][i].id;
								holdingAmount = b;
								isHolding = true;
							}
							else
							{
								fullInv[j][i].itemAmount = a - 2;
								holdingID = fullInv[j][i].id;
								holdingAmount = a;
								isHolding = true;
							}
						}
						if(isHolding && ((holdingID >= Block.IDs.length && fullInv[j][i] == Item.getItem(holdingID).getSpecial()) || (holdingID < Block.IDs.length && fullInv[j][i] == Block.getBlock(holdingID).getSpecial())))
						{
							if(fullInv[j][i].id == Block.air.blockID)
							{
								if(holdingAmount > 0)
								{
									fullInv[j][i].id = holdingID;
									fullInv[j][i].itemAmount = 0;
									holdingAmount--;
									break;
								}
								else
								{
									fullInv[j][i].id = holdingID;
									fullInv[j][i].itemAmount = 0;
									holdingID = Block.air.blockID;
									holdingAmount = 0;
									isHolding = false;
									break;
								}
							}
							else
							{
								if(holdingID == fullInv[j][i].id)
								{
									if(holdingAmount > 0)
									{
										if(holdingID >= Block.IDs.length)
										{
											if(fullInv[j][i].itemAmount < (Item.getItem(holdingID).getMaxStackSize() - 1))
											{
												fullInv[j][i].itemAmount++;
												holdingAmount--;
											}
										}
										else
										{
											if(fullInv[j][i].itemAmount < (Block.getBlock(holdingID).getMaxStackSize() - 1))
											{
												fullInv[j][i].itemAmount++;
												holdingAmount--;
											}
										}
										break;
									}
									else
									{
										if(holdingID >= Block.IDs.length)
										{
											if(fullInv[j][i].itemAmount < (Item.getItem(holdingID).getMaxStackSize() - 1))
											{
												fullInv[j][i].itemAmount++;
												holdingID = Block.air.blockID;
												holdingAmount = 0;
												isHolding = false;
											}
										}
										else
										{
											if(fullInv[j][i].itemAmount < (Block.getBlock(holdingID).getMaxStackSize() - 1))
											{
												fullInv[j][i].itemAmount++;
												holdingID = Block.air.blockID;
												holdingAmount = 0;
												isHolding = false;
											}
										}
										break;
									}
								}
								else
								{
									int sid = holdingID;
									int samount = holdingAmount;
									holdingID = fullInv[j][i].id;
									holdingAmount = fullInv[j][i].itemAmount;
									fullInv[j][i].id = sid;
									fullInv[j][i].itemAmount = samount;
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
		if(Main.player != null && !Main.player.healthBar.isDead)
		{
			for(int i = 0; i < invBar.length; i++)
			{
				boolean isSelected = false;
				if(i == selection) isSelected = true;
				invBar[i].render(isSelected);
			}
			if(itemFunc) for(ItemFunctionButton butt : getFuncButtons()) butt.render();

			if(Main.inv.isOpen)
			{
				for(int i = 0; i < Main.inv.invBag.length; i++) Main.inv.invBag[i].render(false);
				for(int i = 0; i < Main.inv.invArmor.length; i++) Main.inv.invArmor[i].render(false);
				for(int i = 0; i < Main.inv.invJewelry.length; i++) Main.inv.invJewelry[i].render(false);
				for(int i = 0; i < Main.inv.invVanity.length; i++) Main.inv.invVanity[i].render(false);
				for(int i = 0; i < Main.inv.invTrinkets.length; i++) Main.inv.invTrinkets[i].render(false);
				Main.inv.invTrash[0].render(false);
			}
			if(isOpen && !Main.player.isCreative) craftingMenu.render();
			if(isOpen && Main.player.isCreative) creativeMenu.render();
			for(int l = 0; l < Block.blocks.size(); l++)
			{
				if(Main.inv.isHolding && Block.blocks.get(l).blockID == Main.inv.holdingID)
				{
					Render.drawImage(Block.blocks.get(Main.inv.holdingID).getSpriteSheet().getImage(), (Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder, (Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.tileSize - Tile.invItemBorder, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.tileSize - Tile.invItemBorder, Block.blocks.get(Main.inv.holdingID).getTexture().x * Tile.tileSize, Block.blocks.get(Main.inv.holdingID).getTexture().y * Tile.tileSize, Block.blocks.get(Main.inv.holdingID).getTexture().x * Tile.tileSize + Tile.tileSize, Block.blocks.get(Main.inv.holdingID).getTexture().y * Tile.tileSize + Tile.tileSize);
					if(holdingAmount + 1 > 1) Text.drawStringWithShadow(String.valueOf(holdingAmount + 1), (Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder + 1, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder, 0xffffff, 9, Main.gameFont);
				}
			}
			for(int l = 0; l < Item.items.size(); l++)
				if(Main.inv.isHolding && Item.items.get(l).itemID == Main.inv.holdingID)
				{
					Item ids = Item.items.get(l);
					ids.render((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder);
					if(holdingAmount + 1 > 1) Text.drawStringWithShadow(String.valueOf(holdingAmount + 1), (Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder + 2, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder, 0xffffff, 9, Main.gameFont);
					break;
				}

			for(int l = 0; l < fullInv.length; l++)
			{
				for(int i = 0; i < fullInv[l].length; i++)
				{
					for(int r = 0; r < invBar.length; r++)
						if((fullInv[l][i].contains(new Point(Main.mouseX, Main.mouseY)) && Main.inv.isOpen) || (invBar[r].contains(new Point(Main.mouseX, Main.mouseY)) && !isOpen))
						{
							int id = (invBar[r].contains(new Point(Main.mouseX, Main.mouseY)) && !isOpen)? invBar[r].id: fullInv[l][i].id;
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

									Render.setColor(new Color(20, 10, 50, 180));
									Text.drawStringWithShadow(name, (Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 1, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder, Color.WHITE, 9, Main.gameFont);
								}
							for(int m = 0; m < Item.items.size(); m++)
								if(id != Block.air.blockID && id == Item.items.get(m).itemID && Item.items.get(m).getBuffs().isEmpty()) Item.items.get(m).getItemInfo().render((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder);
							for(int m = 0; m < Item.items.size(); m++)
								if(id != Block.air.blockID && id == Item.items.get(m).itemID && !Item.items.get(m).getBuffs().isEmpty()) Item.items.get(m).getItemArmorInfo().render((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder);
						}
				}
			}

			if(Main.player.armor > 0)
			{
				for(int i = 0; i < Main.player.armor; i++)
				{
					Render.drawImage(SpriteSheet.GUI.getImage(), Main.WIDTH - 20, (i/10)*3 + 15, Main.WIDTH - 20 + Tile.tileSize, 15 + Tile.tileSize + (i/10)*3, GuiIcons.armorOverlay);
					Render.drawImage(SpriteSheet.GUI.getImage(), Main.WIDTH - 20, (i/10)*3 + 15, Main.WIDTH - 20 + Tile.tileSize, 15 + Tile.tileSize + (i/10)*3, GuiIcons.armor);
				}
				Text.drawStringWithShadow(String.valueOf(Main.player.armor), Main.WIDTH - Tile.tileSize, (Main.player.armor/10)*3 + 2*Tile.tileSize + 1, Color.white, 8, Main.gameFont);
			}
			if(b < 160 && isIncreasing){
				b+=5;
				if(b == 160) isIncreasing = false;
			}
			if(b > 0 && !isIncreasing){
				if(b-5 > 0) b-=5;
				else b = 0;
				if(b == 0) isIncreasing = true;
			}
			if(Shop.specialDealItem >= 0 && Main.player.specialDealTime > 0)
				Text.drawStringWithShadow("Special Deal: " + BlockAndItem.getItemOrBlockName(Shop.specialObject.get(Shop.specialDealItem).getID()).substring(0, 1).toUpperCase() + BlockAndItem.getItemOrBlockName(Shop.specialObject.get(Shop.specialDealItem).getID()).substring(1), 30, 10, new Color(255, 255, b), 9, Main.gameFont);
		}
	}

	public void tick()
	{
		if(Main.key != null && Key.itemFuncButton.isPressed())
		{
			for(ItemFunctionButton butt : getFuncButtons())
			{
				if(butt.isClicked)
				{
					if(invBar[selection].id >= Block.IDs.length) Item.items.get(invBar[selection].id - Block.IDs.length).setCurrentFunction(butt.getItemFunction());
					else Block.blocks.get(invBar[selection].id).setCurrentFunction(butt.getItemFunction());
					scroll();
					break;
				}
			}
		}
		
		if(isHolding && holdingID >= Block.IDs.length) Item.items.get(holdingID - Block.IDs.length).tickMouseHolding();
		if(isHolding && holdingID < Block.IDs.length) Block.blocks.get(holdingID).tickMouseHolding();
		if(holdingID == 0)
		{
			holdingAmount = 0;
			isHolding = false;
		}

		if(keyDelay > 0) keyDelay--;
		if(Main.key != null && Key.invKey.isPressed() && keyDelay <= 0 && !buttons.isPaused && !Main.console.showChat)
		{
			Main.skl.closeGUI();
			Main.com.closeGUI();
			Main.shop.closeGUI();
			keyDelay = 20;
			if(isOpen) isOpen = false;
			else isOpen = true;
		}	
		if(Main.key != null && !buttons.isPaused && Key.itemFuncButton.isPressed() && !Main.console.showChat && !isOpen && !Main.skl.isOpen && !Main.shop.isOpen && !Main.com.isOpen) itemFunc = true;
		else itemFunc = false;

		if(isOpen && !Main.player.isCreative) craftingMenu.tick();
		if(isOpen && Main.player.isCreative) creativeMenu.tick();

		for(int i = 0; i < fullInv.length; i++)
		{
			for(int l = 0; l < fullInv[i].length; l++)
			{
				if(fullInv[i][l].id >= Block.IDs.length) Item.getItem(fullInv[i][l].id).tickInInventory();
			}
		}
	}

	public int getUniqueSlot()
	{
		for(int i = 0; i < invBar.length; i++) if(!invBar[i].isSlotFull()) return i;
		return -1;
	}

	public boolean isHotbarFull()
	{
		if(getUniqueSlot() == -1) return true;
		else return false;
	}

	public void giveItem(CraftableStack stack)
	{
		int st = stack.getAmount();

		for(int i = 0; i < invBar.length; i++)
		{
			if(invBar[i].id == 0 || invBar[i].id == stack.getID())
			{
				if(invBar[i].id == 0)
				{
					invBar[i].id = stack.getID();
					invBar[i].itemAmount = -1;
				}
				if((invBar[i].itemAmount + st) < BlockAndItem.getMaxItemOrBlockStackSize(stack.getID()))
				{
					invBar[i].addItemToSlot(stack.getID(), st);	
					return;
				}
				else
				{
					for(int m = invBar[i].itemAmount; m < BlockAndItem.getMaxItemOrBlockStackSize(stack.getID()) - 1; m++)
					{
						invBar[i].itemAmount++;
						st--;
					}
					continue;
				}
			}
		}
		for(int i = 0; i < invBag.length; i++)
		{
			if(invBag[i].id == 0 || invBag[i].id == stack.getID())
			{
				if(invBag[i].id == 0)
				{
					invBag[i].id = stack.getID();
					invBag[i].itemAmount = -1;
				}
				if((invBag[i].itemAmount + st) < BlockAndItem.getMaxItemOrBlockStackSize(stack.getID()))
				{
					invBag[i].addItemToSlot(stack.getID(), st);	
					return;
				}
				else
				{
					for(int m = invBag[i].itemAmount; m < BlockAndItem.getMaxItemOrBlockStackSize(stack.getID()) - 1; m++)
					{
						invBag[i].itemAmount++;
						st--;
					}
					continue;
				}
			}
		}
	}

	public boolean isSlotEmpty(int slot)
	{
		if(invBar[slot].id == Block.air.blockID) return true;
		else return false;
	}

	public void clearInventory()
	{
		for(int i = 0; i < fullInv.length; i++)
			for(int y = 0; y < fullInv[i].length; y++)
				fullInv[i][y].empty();
	}
	
	public void removeItemFromInvBar(int slot)
	{
		if(invBar[slot].itemAmount > 0) invBar[slot].itemAmount--;
		else invBar[slot].id = 0;
	}

	public boolean hasItemInInventory(int id)
	{
		for(int i = 0; i < invBar.length; i++) if(invBar[i].id == id) return true;
		for(int i = 0; i < invBag.length; i++) if(invBag[i].id == id) return true;
		for(int i = 0; i < invArmor.length; i++) if(invArmor[i].id == id) return true;
		for(int i = 0; i < invJewelry.length; i++) if(invJewelry[i].id == id) return true;
		for(int i = 0; i < invVanity.length; i++) if(invVanity[i].id == id) return true;
		for(int i = 0; i < invTrinkets.length; i++) if(invTrinkets[i].id == id) return true;
		if(invTrash[0].id == id) return true;
		return false;
	}

	public int getItemAmountOfItemInInventory(int id)
	{
		if(!hasItemInInventory(id)) return 0;
		else
		{
			int amount = 0;
			for(int i = 0; i < invBar.length; i++) if(invBar[i].id == id) amount += (invBar[i].itemAmount + 1);
			for(int i = 0; i < invBag.length; i++) if(invBag[i].id == id) amount += (invBag[i].itemAmount + 1);
			for(int i = 0; i < invArmor.length; i++) if(invArmor[i].id == id) amount += (invArmor[i].itemAmount + 1);
			for(int i = 0; i < invJewelry.length; i++) if(invJewelry[i].id == id) amount += (invJewelry[i].itemAmount + 1);
			for(int i = 0; i < invVanity.length; i++) if(invVanity[i].id == id) amount += (invVanity[i].itemAmount + 1);
			for(int i = 0; i < invTrinkets.length; i++) if(invTrinkets[i].id == id) amount += (invTrinkets[i].itemAmount + 1);
			if(invTrash[0].id == id) amount = 0;
			return amount;
		}
	}

	public void removeItemsFromInventory(int id, int amount)
	{
		for(int j = 0; j < fullInv.length; j ++)
			for(int i = 0; i < fullInv[j].length; i++)
			{
				if(fullInv[j][i].id == id)
				{
					if((fullInv[j][i].itemAmount) >= (amount))
					{
						fullInv[j][i].itemAmount -= (amount); 
						return;
					}
					else
					{
						int newAmount = amount - (fullInv[j][i].itemAmount + 1);
						fullInv[j][i].id = Block.air.blockID;
						removeItemsFromInventory(id, newAmount);
						return;
					}
				}
			}
	}

	public boolean isInvFull()
	{
		for(int i = 0; i < invBar.length; i++) if(invBar[i].id == 0) return false;
		for(int i = 0; i < invBag.length; i++) if(invBag[i].id == 0) return false;
		return true;
	}
}
