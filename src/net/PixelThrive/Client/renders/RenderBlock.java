package net.PixelThrive.Client.renders;

import java.awt.Rectangle;
import java.util.Random;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.entities.Drop;
import net.PixelThrive.Client.items.*;
import net.PixelThrive.Client.world.Tile;

public class RenderBlock extends Rectangle
{
	private static final long serialVersionUID = 1L;
	public int id = -1;
	public int breakDelay = 0, strength = 1;
	private int[] crumble = {11, 15};
	private int afterTouch = 0;
	private boolean isBackgroundBlock;
	public boolean isTurned = false;
	private Random rand = new Random();
	
	public RenderBlock(Rectangle size, int id)
	{
		setBounds(size);
		this.id = id;
		isBackgroundBlock = false;
	}

	public RenderBlock(Rectangle size, int id, boolean backgroundblock)
	{
		setBounds(size);
		this.id = id;
		isBackgroundBlock = backgroundblock;
	}

	public void render()
	{
		if(afterTouch <= 0) breakDelay = 0;
		if(afterTouch > 0) afterTouch--;
		if(id != 0)
		{
			if(isBackgroundBlock)
			{
				if(Block.blocks.get(id).getTexture() != null)
				{
					if(!isTurned || !Block.blocks.get(id).isTurnable()) Render.drawImage(Block.blocks.get(id).getTexture().getImageIcon(), x - (int) Main.sX, y - (int) Main.sY, x + Tile.tileSize - (int) Main.sX, y + Tile.tileSize - (int) Main.sY, Block.blocks.get(id).getTexture().getTextureX() * Tile.tileSize, Block.blocks.get(id).getTexture().getTextureY() * Tile.tileSize, Block.blocks.get(id).getTexture().getTextureX() * Tile.tileSize + Tile.tileSize, Block.blocks.get(id).getTexture().getTextureY() * Tile.tileSize + Tile.tileSize);
					if(isTurned && Block.blocks.get(id).isTurnable()) Render.drawImage(Render.rotate(Block.blocks.get(id).getTexture().getImageIcon(), 90), x - (int) Main.sX, y - (int) Main.sY, x + Tile.tileSize - (int) Main.sX, y + Tile.tileSize - (int) Main.sY, Block.blocks.get(id).getTexture().getTextureX() * Tile.tileSize, Block.blocks.get(id).getTexture().getTextureY() * Tile.tileSize, Block.blocks.get(id).getTexture().getTextureX() * Tile.tileSize + Tile.tileSize, Block.blocks.get(id).getTexture().getTextureY() * Tile.tileSize + Tile.tileSize);
				}
				if(breakDelay > 0 && breakDelay < Main.world.bgResistance / 2) crumble = new int[]{11, 15};
				else if(breakDelay >= Main.world.bgResistance / 2 && breakDelay < Main.world.bgResistance) crumble = new int[]{12, 15};
				else if(breakDelay >= Main.world.bgResistance && breakDelay < Main.world.bgResistance + Main.world.bgResistance/2) crumble = new int[]{13, 15};
				else if(breakDelay >= Main.world.bgResistance + Main.world.bgResistance / 2 && breakDelay < Main.world.bgResistance*2) crumble = new int[]{14, 15};
				else if(breakDelay >= Main.world.bgResistance * 2 && breakDelay < Main.world.bgResistance*2 + Main.world.bgResistance/2) crumble = new int[]{15, 15};
				Render.setColor(0, 0, 0, 125);
				Render.fillRect(x - (int)Main.sX, y - (int)Main.sY, width, height);
				if(breakDelay > 0) Render.drawImage(SpriteSheet.Terrain.getImage(), x - (int) Main.sX, y - (int) Main.sY, x + width - (int) Main.sX, y + height - (int) Main.sY, crumble[0] * Tile.tileSize, crumble[1] * Tile.tileSize, crumble[0] * Tile.tileSize + Tile.tileSize, crumble[1] * Tile.tileSize + Tile.tileSize);
			}
			else
			{
				if(Block.blocks.get(id).renderInWorld() && Block.blocks.get(id).getTexture() != null)
				{
					if(!isTurned || !Block.blocks.get(id).isTurnable()) Render.drawImage(Block.blocks.get(id).getTexture().getImageIcon(), x - (int) Main.sX, y - (int) Main.sY, x + Tile.tileSize - (int) Main.sX, y + Tile.tileSize - (int) Main.sY, Block.blocks.get(id).getTexture().getTextureX() * Tile.tileSize, Block.blocks.get(id).getTexture().getTextureY() * Tile.tileSize, Block.blocks.get(id).getTexture().getTextureX() * Tile.tileSize + Tile.tileSize, Block.blocks.get(id).getTexture().getTextureY() * Tile.tileSize + Tile.tileSize);
					if(isTurned && Block.blocks.get(id).isTurnable()) Render.drawImage(Render.rotate(Block.blocks.get(id).getTexture().getImageIcon(), 90), x - (int) Main.sX, y - (int) Main.sY, x + Tile.tileSize - (int) Main.sX, y + Tile.tileSize - (int) Main.sY, Block.blocks.get(id).getTexture().getTextureX() * Tile.tileSize, Block.blocks.get(id).getTexture().getTextureY() * Tile.tileSize, Block.blocks.get(id).getTexture().getTextureX() * Tile.tileSize + Tile.tileSize, Block.blocks.get(id).getTexture().getTextureY() * Tile.tileSize + Tile.tileSize);
				}
				if(breakDelay > 0 && breakDelay < Main.world.resistance / 2) crumble = new int[]{11, 15};
				else if(breakDelay >= Main.world.resistance / 2 && breakDelay < Main.world.resistance) crumble = new int[]{12, 15};
				else if(breakDelay >= Main.world.resistance && breakDelay < Main.world.resistance + Main.world.resistance/2) crumble = new int[]{13, 15};
				else if(breakDelay >= Main.world.resistance + Main.world.resistance / 2 && breakDelay < Main.world.resistance*2) crumble = new int[]{14, 15};
				else if(breakDelay >= Main.world.resistance * 2 && breakDelay < Main.world.resistance*2 + Main.world.resistance/2) crumble = new int[]{15, 15};
				if(breakDelay > 0) Render.drawImage(SpriteSheet.Terrain.getImage(), x - (int) Main.sX, y - (int) Main.sY, x + width - (int) Main.sX, y + height - (int) Main.sY, crumble[0] * Tile.tileSize, crumble[1] * Tile.tileSize, crumble[0] * Tile.tileSize + Tile.tileSize, crumble[1] * Tile.tileSize + Tile.tileSize);
			}
		}
	}

	public void destroy()
	{
		if(Block.blocks.get(id).getResistance() != -1)
		{
			if(Main.world.toolNeeded != null)
			{
				for(int i = 0; i < Main.world.toolNeeded.length; i++)
				{
					if(Main.inv.invBar[Main.inv.selection].id >= 4096 && (Main.world.toolNeeded[i] != null && Main.inv.invBar[Main.inv.selection].id == Main.world.toolNeeded[i].itemID || (Main.player.isCreative && Main.world.quantity == 0)))
					{
						strength = Item.items.get(Main.player.getHoldingItemID()).getStrength();
						Main.world.resistance *= (float)(strength * 0.01);
						if(Main.world.quantity == 0)Main.world.quantity++;
					}
				}
			}
			if(Main.world.toolNeeded != null)
				for(int i = 0; i < Main.world.toolNeeded.length; i++)
					if(breakDelay < Main.world.resistance * 2 + Main.world.resistance / 2)
					{
						afterTouch = 150;
						breakDelay++;
					}
					else
					{
						breakDelay = 0;
						if(Main.inv.invBar[Main.inv.selection].id >= 4096 && Item.items.get(Main.player.getHoldingItemID()).getPower() >= Main.world.power)
							for(int m = 0; m < Main.world.quantity; m++)
							{
								if(!Main.player.isCreative) new Drop((int)x + 4, (int)y + 4, Main.world.drop, 0).spawnEntity();
								if(getBlock().getDrops() != null)
								{
									for(int k = 0; k < getBlock().getDrops().length; k++)
										if(!Main.player.isCreative) 
											if(!getBlock().hasRandomDrop()) new Drop((int)x + 4, (int)y + 4, getBlock().getDrops()[k].getID(), getBlock().getDrops()[k].getAmount()).spawnEntity();
											else new Drop((int)x + 4, (int)y + 4, getBlock().getDrops()[k].getID(), rand.nextInt(getBlock().getDrops()[k].getAmount())).spawnEntity();
								}
							}
						else if(Main.inv.invBar[Main.inv.selection].id >= 0 && Main.inv.invBar[Main.inv.selection].id < 4096 && Main.world.power == 0)
							for(int m = 0; m < Main.world.quantity; m++)
							{
								if(!Main.player.isCreative) new Drop((int)x + 4, (int)y + 4, Main.world.drop, 0).spawnEntity();
								if(getBlock().getDrops() != null)
								{
									for(int k = 0; k < getBlock().getDrops().length; k++)
										if(!Main.player.isCreative) 
											if(!getBlock().hasRandomDrop()) new Drop((int)x + 4, (int)y + 4, getBlock().getDrops()[k].getID(), getBlock().getDrops()[k].getAmount()).spawnEntity();
											else new Drop((int)x + 4, (int)y + 4, getBlock().getDrops()[k].getID(), rand.nextInt(getBlock().getDrops()[k].getAmount())).spawnEntity();
								}
							}						
						Block.getBlock(id).onDestroyed(x / Tile.tileSize, y / Tile.tileSize);
						id = Block.air.blockID;
					}
			else
			{
				if(breakDelay < Main.world.resistance * 2 + Main.world.resistance / 2)
				{
					afterTouch = 150;
					breakDelay++;
				}
				else
				{
					breakDelay = 0;
					if(Main.player.getHoldingItemID() >= 4096 && Item.items.get(Main.player.getHoldingItemID()).getPower() >= Main.world.power)for(int m = 0; m < Main.world.quantity; m++)
					{
						if(!Main.player.isCreative) new Drop((int)x + 4, (int)y + 4, Main.world.drop, 0).spawnEntity();
						if(getBlock().getDrops() != null)
						{
							for(int k = 0; k < getBlock().getDrops().length; k++)
								if(!Main.player.isCreative) 
									if(!getBlock().hasRandomDrop()) new Drop((int)x + 4, (int)y + 4, getBlock().getDrops()[k].getID(), getBlock().getDrops()[k].getAmount()).spawnEntity();
									else new Drop((int)x + 4, (int)y + 4, getBlock().getDrops()[k].getID(), rand.nextInt(getBlock().getDrops()[k].getAmount())).spawnEntity();
						}
					}
					Block.getBlock(id).onDestroyed(x / Tile.tileSize, y / Tile.tileSize);
					id = Block.air.blockID;
				}
			}
		}
		else if(Main.player.isCreative) id = Block.air.blockID;
	}
	
	public void bgDestroy()
	{
		if(Block.blocks.get(id).getResistance() != -1)
		{
			if(Main.world.bgToolNeeded != null)
			{
				for(int i = 0; i < Main.world.bgToolNeeded.length; i++)
				{
					if(Main.inv.invBar[Main.inv.selection].id >= 4096 && (Main.world.bgToolNeeded[i] != null && Main.inv.invBar[Main.inv.selection].id == Main.world.bgToolNeeded[i].itemID || (Main.player.isCreative && Main.world.bgQuantity == 0)))
					{
						strength = Item.items.get(Main.player.getHoldingItemID()).getStrength();
						Main.world.bgResistance *= (float)(strength * 0.01);
						if(Main.world.bgQuantity == 0) Main.world.bgQuantity++;
					}
				}
			}
			if(Main.world.bgToolNeeded != null)
				for(int i = 0; i < Main.world.bgToolNeeded.length; i++)
					if(breakDelay < Main.world.bgResistance * 2 + Main.world.bgResistance / 2)
					{
						afterTouch = 150;
						breakDelay++;
					}
					else
					{
						breakDelay = 0;
						if(Main.inv.invBar[Main.inv.selection].id >= 4096 && Item.items.get(Main.player.getHoldingItemID()).getPower() >= Main.world.bgPower)
							for(int m = 0; m < Main.world.bgQuantity; m++)
							{
								if(!Main.player.isCreative) new Drop((int)x + 4, (int)y + 4, Main.world.bgDrop, 0).spawnEntity();
								if(getBlock().getDrops() != null)
								{
									for(int k = 0; k < getBlock().getDrops().length; k++)
										if(!Main.player.isCreative) 
											if(!getBlock().hasRandomDrop()) new Drop((int)x + 4, (int)y + 4, getBlock().getDrops()[k].getID(), getBlock().getDrops()[k].getAmount()).spawnEntity();
											else new Drop((int)x + 4, (int)y + 4, getBlock().getDrops()[k].getID(), rand.nextInt(getBlock().getDrops()[k].getAmount())).spawnEntity();
								}
							}
						else if(Main.inv.invBar[Main.inv.selection].id >= 0 && Main.inv.invBar[Main.inv.selection].id < 4096 && Main.world.bgPower == 0)
							for(int m = 0; m < Main.world.bgQuantity; m++)
							{
								if(!Main.player.isCreative) new Drop((int)x + 4, (int)y + 4, Main.world.bgDrop, 0).spawnEntity();
								if(getBlock().getDrops() != null)
								{
									for(int k = 0; k < getBlock().getDrops().length; k++)
										if(!Main.player.isCreative) 
											if(!getBlock().hasRandomDrop()) new Drop((int)x + 4, (int)y + 4, getBlock().getDrops()[k].getID(), getBlock().getDrops()[k].getAmount()).spawnEntity();
											else new Drop((int)x + 4, (int)y + 4, getBlock().getDrops()[k].getID(), rand.nextInt(getBlock().getDrops()[k].getAmount())).spawnEntity();
								}
							}						
						Block.getBlock(id).onDestroyed(x / Tile.tileSize, y / Tile.tileSize);
						id = Block.air.blockID;
					}
			else
			{
				if(breakDelay < Main.world.bgResistance * 2 + Main.world.bgResistance / 2)
				{
					afterTouch = 150;
					breakDelay++;
				}
				else
				{
					breakDelay = 0;
					if(Main.player.getHoldingItemID() >= 4096 && Item.items.get(Main.player.getHoldingItemID()).getPower() >= Main.world.bgPower) for(int m = 0; m < Main.world.bgQuantity; m++)
					{
						if(!Main.player.isCreative) new Drop((int)x + 4, (int)y + 4, Main.world.bgDrop, 0).spawnEntity();
						if(getBlock().getDrops() != null)
						{
							for(int k = 0; k < getBlock().getDrops().length; k++)
								if(!Main.player.isCreative) 
									if(!getBlock().hasRandomDrop()) new Drop((int)x + 4, (int)y + 4, getBlock().getDrops()[k].getID(), getBlock().getDrops()[k].getAmount()).spawnEntity();
									else new Drop((int)x + 4, (int)y + 4, getBlock().getDrops()[k].getID(), rand.nextInt(getBlock().getDrops()[k].getAmount())).spawnEntity();
						}
					}
					Block.getBlock(id).onDestroyed(x / Tile.tileSize, y / Tile.tileSize);
					id = Block.air.blockID;
				}
			}
		}
		else if(Main.player.isCreative) id = Block.air.blockID;
	}

	public Block getBlock()
	{
		return Block.blocks.get(id);
	}
}
