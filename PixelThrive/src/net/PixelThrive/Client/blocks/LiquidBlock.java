package net.PixelThrive.Client.blocks;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.biomes.BiomeType;
import net.PixelThrive.Client.items.CraftableStack;
import net.PixelThrive.Client.items.Item;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.renders.Texture;
import net.PixelThrive.Client.world.Tile;
import net.PixelThrive.Client.world.World;
import net.PixelThrive.Client.blocks.LiquidBlock;

public class LiquidBlock extends Block
{
	private int[][] animDelay, animation, flowDelay, flow;
	protected int liquidID, spawned;
	private Texture[][] texture;

	public LiquidBlock(int id)
	{
		super(id);
	}

	public LiquidBlock(int id, int texture, int liquidID)
	{
		super(id, texture);
		this.liquidID = liquidID;
	}

	public boolean isSelectable()
	{
		return false;
	}
	
	public boolean isLiquid()
	{
		return true;
	}

	public void blockTick(World world, int x, int y)
	{
		if(animDelay == null) animDelay = new int[world.worldW][world.worldH];
		if(animation == null) animation = new int[world.worldW][world.worldH];
		if(texture == null) texture = new Texture[world.worldW][world.worldH];
		if(flow == null) flow = new int[world.worldW][world.worldH];
		if(flowDelay == null) flowDelay = new int[world.worldW][world.worldH];
		try
		{
			if(flowDelay[x][y] > 0) flowDelay[x][y]--;
			if(world.getBlock(x, y + 1) == Block.air && flowDelay[x][y] <= 0)
			{
				if(y + 1 > 0 && world.getBlock(x, y + 1) == Block.air) world.setBlock(x, y + 1, blockID);
				world.setBlock(x, y, Block.air.blockID);
				flowDelay[x][y] = 50;
			}
//			Main.consoleMessage(quantity);
			if(spawned == 0){
				spawned++;
				world.setLiquidQuantity(1600, x, y);
			}
			if(world.getBlock(x, y + 1) != Block.air && flowDelay[x][y] <= 0 && world.getLiquidQuantity(x, y) > 0)
			{
				if(x - 1 > 0 && world.getBlock(x - 1, y) == Block.air){
					world.setBlock(x - 1, y, blockID);
//					world.getBlock(x - 1, y).setQuantity(world.getBlock(x, y).getQuantity()/2);
					world.setLiquidQuantity(world.getLiquidQuantity(x, y)/2, x, y);
				}
				if(x + 1 < world.worldW && world.getBlock(x + 1, y) == Block.air){
					world.setBlock(x + 1, y, blockID);
//					world.getBlock(x + 1, y).setQuantity(world.getBlock(x, y).getQuantity()/2);
					world.setLiquidQuantity(world.getLiquidQuantity(x, y)/2, x, y);
				}
				flowDelay[x][y] = 50;
			}
			if(animDelay[x][y] > 0) animDelay[x][y]--;
			else animDelay[x][y] = 20;
			if(animDelay[x][y] > 0 && animDelay[x][y] < 5) animation[x][y] = 0;
			else if(animDelay[x][y] >= 5 && animDelay[x][y] < 10) animation[x][y] = 1;
			else if(animDelay[x][y] >= 10 && animDelay[x][y] < 15) animation[x][y] = 2;
			else animation[x][y] = 3;
			if(animation[x][y] < 2) texture[x][y] = new Texture(SpriteSheet.Terrain, animation[x][y], 14);
			else texture[x][y] = new Texture(SpriteSheet.Terrain, animation[x][y] - 2, 15);
		}
		catch(Exception e){}
		if((world.getBiomeByX(x).getType() == BiomeType.SNOWY || world.getBiomeByX(x).getType() == BiomeType.COLD) && world.getBlock(x, y - 1) == Block.air && world.rand.nextInt(60) == 0) world.setBlock(x, y, Block.ice.blockID);
	}

	public void renderOverlay(World world, int x, int y)
	{
		try
		{
			if(texture != null && texture[x][y] != null) 
				Render.drawImage(texture[x][y].getImageIcon(), (x * Tile.tileSize) - (int)Main.sX, (y * Tile.tileSize) - (int)Main.sY);
		//	Render.drawImage(texture[x][y].getImageIcon(), (x * Tile.tileSize) - (int)Main.sX, (y * Tile.tileSize) - (int)Main.sY + (Tile.tileSize - (int)(world.getLiquidQuantity(x, y)/100)), Tile.tileSize, Tile.tileSize - (Tile.tileSize - (int)(world.getLiquidQuantity(x, y)/100)));
		}
		catch(Exception e){}
	}

	public boolean isSolid()
	{
		return false;
	}

	public void onRightClick(int x, int y)
	{		
		if(Main.inv.invBar[Main.inv.selection].id == Item.bottle.itemID)
		{
			Main.inv.invBar[Main.inv.selection].itemAmount--;
			if(Main.inv.invBar[Main.inv.selection].itemAmount < 0) Main.inv.invBar[Main.inv.selection].id = 0;
			Main.inv.giveItem(new CraftableStack(Item.waterBottle, 1));		
			return;
		}
	}

	public double slowDownInBlock()
	{
		return 0.7D;
	}

	public int getLiquidID()
	{
		return liquidID;
	}
}
