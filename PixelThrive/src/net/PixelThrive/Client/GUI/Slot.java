package net.PixelThrive.Client.GUI;

import java.awt.*;

import net.PixelThrive.Client.Input.Key;
import net.PixelThrive.Client.LoadedImage;
import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.blocks.BlockAndItem;
import net.PixelThrive.Client.items.CraftableStack;
import net.PixelThrive.Client.items.Item;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.Tile;

public class Slot extends Rectangle
{
	private static final long serialVersionUID = 1L;

	public int id = -1;
	public int[] icon;

	public static final LoadedImage cell = new LoadedImage("Slot.png");
	public static final LoadedImage selection = new LoadedImage("Selection.png");
	private int maxItem, r, g, b;
	public int itemAmount = 0;
	private boolean isColorDifferent, isAvailable;
	private int clickDelay = 0;
	private String label = "undefined";
	public boolean isCreativeSlot = false;

	public Slot(Rectangle size, int id, int maxItems, int r, int g, int b, boolean col)
	{
		setBounds(size);
		this.id = id;
		maxItem = maxItems;
		this.r = r;
		this.g = g;
		this.b = b;
		isColorDifferent = col;
		isAvailable = true;
	}

	public Slot(Rectangle size, int id, int maxItems, int r, int g, int b, boolean col, boolean isCreativeSlot)
	{
		setBounds(size);
		this.id = id;
		maxItem = maxItems;
		this.r = r;
		this.g = g;
		this.b = b;
		isColorDifferent = col;
		isAvailable = true;
		this.isCreativeSlot = isCreativeSlot;
	}

	public Slot(Rectangle size, int id, int maxItems, int r, int g, int b, boolean col, String label)
	{
		setBounds(size);
		this.id = id;
		maxItem = maxItems;
		this.r = r;
		this.g = g;
		this.b = b;
		isColorDifferent = col;
		isAvailable = true;
		this.label = label;
	}

	public Slot(Rectangle size, int id, int maxItems, int r, int g, int b, boolean col, int[] pos)
	{
		setBounds(size);
		this.id = id;
		maxItem = maxItems;
		this.r = r;
		this.g = g;
		this.b = b;
		isColorDifferent = col;
		icon = pos;
		isAvailable = true;
	}

	public Slot(Rectangle size, int id, int maxItems, int r, int g, int b, boolean col, int[] pos, boolean ac)
	{
		setBounds(size);
		this.id = id;
		maxItem = maxItems;
		this.r = r;
		this.g = g;
		this.b = b;
		isColorDifferent = col;
		icon = pos;
		isAvailable = ac;
	}

	public void tick()
	{
		if(clickDelay > 0) clickDelay--;

		if(!isCreativeSlot)
		{
			if(Main.key.isMouseLeft && clickDelay <= 0)
			{	
				clickDelay = 12;
				if(contains((new Point(Main.mse.x / Main.SCALE, Main.mse.y / Main.SCALE))))
				{
					if(id != Block.air.blockID && !Main.inv.isHolding)
					{
						Main.inv.holdingID = id;
						Main.inv.holdingAmount = itemAmount;
						itemAmount = 0;
						id = Block.air.blockID;
						Main.inv.isHolding = true;
					}
					else if(Main.inv.isHolding && id == Block.air.blockID)
					{
						id = Main.inv.holdingID;
						itemAmount = Main.inv.holdingAmount;
						Main.inv.holdingAmount = 0;
						Main.inv.isHolding = false;
					}
					else if(Main.inv.isHolding && id != Block.air.blockID)
					{
						if(Main.inv.holdingID != id && Main.inv.holdingID != Block.air.blockID)
						{
							int con = id;
							id = Main.inv.holdingID;
							int oldAmount = Main.inv.holdingAmount;
							Main.inv.holdingAmount = itemAmount;
							itemAmount = oldAmount;
							Main.inv.holdingID = con;
						}
						else if(Main.inv.holdingID == id && Main.inv.holdingID != Block.air.blockID)
						{
							if(Main.inv.holdingID >= Block.IDs.length)
							{
								if((Main.inv.holdingAmount + itemAmount) < (Item.getItem(Main.inv.holdingID).getMaxStackSize() - 1))
								{
									itemAmount += (Main.inv.holdingAmount + 1);
									Main.inv.holdingAmount = 0;
									Main.inv.isHolding = false;
								}
								else
								{
									Main.inv.holdingAmount -= ((Item.getItem(Main.inv.holdingID).getMaxStackSize() - 1) - itemAmount);
									itemAmount = (Item.getItem(Main.inv.holdingID).getMaxStackSize() - 1);
								}
							}
							else
							{
								if((Main.inv.holdingAmount + itemAmount) < (Block.getBlock(Main.inv.holdingID).getMaxStackSize() - 1))
								{
									itemAmount += (Main.inv.holdingAmount + 1);
									Main.inv.holdingAmount = 0;
									Main.inv.isHolding = false;
								}
								else
								{
									Main.inv.holdingAmount -= ((Block.getBlock(Main.inv.holdingID).getMaxStackSize() - 1) - itemAmount);
									itemAmount = (Block.getBlock(Main.inv.holdingID).getMaxStackSize() - 1);
								}
							}
						}
					}
				}
			}
			else if(Main.key.isMouseRight && clickDelay <= 0)
			{
				clickDelay = 12;
				if(contains((new Point(Main.mse.x / Main.SCALE, Main.mse.y / Main.SCALE))))
				{
					if(id != Block.air.blockID && !Main.inv.isHolding && itemAmount > 0)
					{
						int old = itemAmount + 1;
						int a = old / 2;
						int b = 0;

						if((a * 2) != old)
						{
							b = a;
							itemAmount = a - 1;
							Main.inv.holdingID = id;
							Main.inv.holdingAmount = b;
							Main.inv.isHolding = true;
						}
						else
						{
							itemAmount = a - 2;
							Main.inv.holdingID = id;
							Main.inv.holdingAmount = a;
							Main.inv.isHolding = true;
						}
					}
					if(Main.inv.isHolding)
					{
						if(id == Block.air.blockID)
						{
							if(Main.inv.holdingAmount > 0)
							{
								id = Main.inv.holdingID;
								itemAmount = 0;
								Main.inv.holdingAmount--;
							}
							else
							{
								id = Main.inv.holdingID;
								itemAmount = 0;
								Main.inv.holdingID = Block.air.blockID;
								Main.inv.holdingAmount = 0;
								Main.inv.isHolding = false;
							}
						}
						else
						{
							if(Main.inv.holdingID == id)
							{
								if(Main.inv.holdingAmount > 0)
								{				
									if(Main.inv.holdingID >= Block.IDs.length)
									{
										if(itemAmount < (Item.getItem(Main.inv.holdingID).getMaxStackSize() - 1))
										{
											itemAmount++;
											Main.inv.holdingAmount--;
										}
									}
									else
									{
										if(itemAmount < (Block.getBlock(Main.inv.holdingID).getMaxStackSize() - 1))
										{
											itemAmount++;
											Main.inv.holdingAmount--;
										}
									}
								}
								else
								{
									if(Main.inv.holdingID >= Block.IDs.length)
									{
										if(itemAmount < (Item.getItem(Main.inv.holdingID).getMaxStackSize() - 1))
										{
											itemAmount++;
											Main.inv.holdingID = Block.air.blockID;
											Main.inv.holdingAmount = 0;
											Main.inv.isHolding = false;
										}
									}
									else
									{
										if(itemAmount < (Block.getBlock(Main.inv.holdingID).getMaxStackSize() - 1))
										{
											itemAmount++;
											Main.inv.holdingID = Block.air.blockID;
											Main.inv.holdingAmount = 0;
											Main.inv.isHolding = false;
										}
									}
								}
							}
							else
							{
								int sid = Main.inv.holdingID;
								int samount = Main.inv.holdingAmount;
								Main.inv.holdingID = id;
								Main.inv.holdingAmount = itemAmount;
								id = sid;
								itemAmount = samount;
							}
						}
					}
				}
			}
		}
		else
		{
			if((Main.key.isMouseRight || Main.key.isMouseLeft) && clickDelay <= 0)
			{	
				if(Main.key.isMouseLeft) clickDelay = 12;
				if(contains((new Point(Main.mse.x / Main.SCALE, Main.mse.y / Main.SCALE))))
				{
					if(!Main.inv.isHolding && id != 0)
					{
						if(Key.transportKey.isPressed())
						{
							Main.inv.giveItem(new CraftableStack(id));
							return;
						}
						else
						{
							Main.inv.holdingID = id;
							Main.inv.holdingAmount = 0;
							Main.inv.isHolding = true;
							return;
						}
					}
					if(Main.inv.isHolding && id == Main.inv.holdingID && id != 0)
					{
						if(Key.transportKey.isPressed())
						{
							Main.inv.giveItem(new CraftableStack(id));
							return;
						}
						else
						{
							if((Main.inv.holdingAmount + 1) < BlockAndItem.getMaxItemOrBlockStackSize(id)) Main.inv.holdingAmount++;
							return;
						}
					}
				}
			}
			if(Main.key.isMouseMiddle && clickDelay <= 0 && id != 0)
			{
				if(contains((new Point(Main.mse.x / Main.SCALE, Main.mse.y / Main.SCALE))))
				{
					clickDelay = 12;
					Main.inv.giveItem(new CraftableStack(id, BlockAndItem.getMaxItemOrBlockStackSize(id)));
				}
			}
		}
	}

	public void render(boolean isSelected)
	{
		if(isColorDifferent)
		{
			Render.setColor(new Color(r, g, b, 100)); 
			Render.fillRect(x, y, width, height);
		}
		Render.drawImage(cell.getImage(), x, y, width, height);
		if(isSelected) Render.drawImage(selection.getImage(), x - 1, y - 1, width + 2, height + 2);
		if(icon != null && id == 0) Render.drawImage(SpriteSheet.GUI.getImage().getSubimage(icon[0] * Tile.tileSize, icon[1] * Tile.tileSize, Tile.tileSize, Tile.tileSize), x + Tile.invItemBorder - 2, y + Tile.invItemBorder - 2);
		for(int i = 0; i < Block.blocks.size(); i++)
			if(id != Block.air.blockID && Block.blocks.get(i).blockID == id && !isItem(id) && Block.blocks.get(id).getSpriteSheet() != null && Block.blocks.get(id).getSpriteSheet().getImage() != null) Render.drawImage(Block.blocks.get(id).getSpriteSheet().getImage(), x + Tile.invItemBorder - 1, y + Tile.invItemBorder - 1, x + width - Tile.invItemBorder + 1, y + height - Tile.invItemBorder + 1, Block.blocks.get(id).getTexture().x * Tile.tileSize, Block.blocks.get(id).getTexture().y * Tile.tileSize, Block.blocks.get(id).getTexture().x * Tile.tileSize + Tile.tileSize, Block.blocks.get(id).getTexture().y * Tile.tileSize + Tile.tileSize);				
		for(int i = 0; i < Item.items.size(); i++)
			if(id != Block.air.blockID)
				if(id == Item.items.get(i).itemID) Item.items.get(i).render(x + Tile.invItemBorder - 2, y + Tile.invItemBorder - 2);
		if(itemAmount > 0 && id != Block.air.blockID) Text.drawStringWithShadow(String.valueOf(itemAmount + 1), x + 5 - ((String.valueOf(itemAmount + 1).length() >= 3)? 3 : 0), y + 10, Color.WHITE, 8, Main.gameFont);

		if(contains(new Point(Main.mse.x / Main.SCALE, Main.mse.y / Main.SCALE)) && isAvailable)
		{
			Render.setColor(new Color(r, g, b, 100)); 
			Render.fillRect(x, y, width, height);
		}
		if(!isAvailable)
		{
			Render.setColor(new Color(40, 40, 40, 130)); 
			Render.fillRect(x, y, width, height);
		}

		if(isSelected && id >= Block.IDs.length) Item.getItem(id).tickOnSelected();

		if(contains(new Point(Main.mse.x / Main.SCALE, Main.mse.y / Main.SCALE)) && !label.equalsIgnoreCase("undefined"))
		{
			Render.setFont(Main.gameFont, 9);
			String name = label;
			Render.setColor(new Color(60, 60, 60, 255));
			Render.fillRect((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 4, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 10, Render.getFontMetrics().stringWidth(name) + 3, 12);
			Render.setColor(Color.black);
			Render.drawRect((Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 4, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 10, Render.getFontMetrics().stringWidth(name) + 3, 12);

			Render.setColor(new Color(20, 10, 50, 180));
			Text.drawStringWithShadow(name, (Main.mse.x / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder - 1, (Main.mse.y / Main.SCALE) - (Tile.invCellSize / 2) + Tile.invItemBorder, Color.WHITE, 9, Main.gameFont);
		}
	}

	public void render(boolean isSelected, boolean av)
	{
		if(isColorDifferent)
		{
			Render.setColor(new Color(r, g, b, 100)); 
			Render.fillRect(x, y, width, height);
		}
		Render.drawImage(cell.getImage(), x, y, width, height);
		if(isSelected) Render.drawImage(selection.getImage(), x - 1, y - 1, width + 2, height + 2);
		if(icon != null && id == 0) Render.drawImage(SpriteSheet.GUI.getImage().getSubimage(icon[0] * Tile.tileSize, icon[1] * Tile.tileSize, Tile.tileSize, Tile.tileSize), x + Tile.invItemBorder - 2, y + Tile.invItemBorder - 2);
		for(int i = 0; i < Block.blocks.size(); i++)
			if(id != Block.air.blockID && Block.blocks.get(i).blockID == id && !isItem(id)) Render.drawImage(Block.blocks.get(id).getSpriteSheet().getImage(), x + Tile.invItemBorder - 1, y + Tile.invItemBorder - 1, x + width - Tile.invItemBorder + 1, y + height - Tile.invItemBorder + 1, Block.blocks.get(id).getTexture().x * Tile.tileSize, Block.blocks.get(id).getTexture().y * Tile.tileSize, Block.blocks.get(id).getTexture().x * Tile.tileSize + Tile.tileSize, Block.blocks.get(id).getTexture().y * Tile.tileSize + Tile.tileSize);				
		for(int i = 0; i < Item.items.size(); i++)
			if(id != Block.air.blockID)
				if(id == Item.items.get(i).itemID) Item.items.get(i).render(x + Tile.invItemBorder - 2, y + Tile.invItemBorder - 2);
		if(itemAmount > 0 && id != Block.air.blockID) Text.drawStringWithShadow(String.valueOf(itemAmount + 1), x + 5 - ((String.valueOf(itemAmount + 1).length() >= 3)? 3 : 0), y + 10, Color.WHITE, 8, Main.gameFont);

		if(contains(new Point(Main.mse.x / Main.SCALE, Main.mse.y / Main.SCALE)) && av)
		{
			Render.setColor(new Color(r, g, b, 100)); 
			Render.fillRect(x, y, width, height);
		}
		if(!av)
		{
			Render.setColor(new Color(40, 40, 40, 130));
			Render.fillRect(x, y, width, height);
		}

		if(isSelected && id >= Block.IDs.length) Item.getItem(id).tickOnSelected();
	}

	public int getMaxItemsPerSlot()
	{
		return maxItem;
	}

	public boolean isSlotFull()
	{
		if(id == Block.air.blockID) return false;
		else
		{
			if(itemAmount < maxItem) return false;
			else return true;
		}
	}

	public void addItemToSlot(int id, int amount)
	{
		if(this.id == id || this.id == 0)
		{
			if(this.id != id)
			{
				this.id = id;
				itemAmount--;
			}
			if((itemAmount + (amount - 1)) <= BlockAndItem.getMaxItemOrBlockStackSize(id)) itemAmount += (amount);
			else itemAmount = BlockAndItem.getMaxItemOrBlockStackSize(id) - 1;
		}
	}

	public void removeItemFromSlot()
	{
		if(itemAmount <= 0) this.id = Block.air.blockID;
		else itemAmount--;
	}

	public boolean isItem(int id)
	{
		if(id > Block.IDs.length)
		{
			for(int i = 0; i < Item.items.size(); i++)
			{
				if(Item.items.get(i).getName().equalsIgnoreCase(Item.items.get(id / Block.IDs.length).getName())) return true;
			}
		}
		else
		{
			for(int i = 0; i < Block.blocks.size(); i++)
			{
				if(Block.blocks.get(i).getName().equalsIgnoreCase(Block.blocks.get(id).getName())) return false;
			}
		}
		return false;
	}

	public void empty()
	{
		id = 0;
		itemAmount = 0;
	}

	public boolean isAvailable() 
	{
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) 
	{
		this.isAvailable = isAvailable;
	}
	
	public BlockAndItem getContains()
	{
		if(id >= Block.IDs.length) return Item.items.get(id - Block.IDs.length);
		else return Block.blocks.get(id);
	}
}
