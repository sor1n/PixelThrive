package net.PixelThrive.Client.blocks;

import java.awt.image.BufferedImage;
import java.util.Random;

import net.PixelThrive.Client.*;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.*;

public class GrassBlock extends Block
{
	protected boolean canSpread;
	Random rand = new Random();
	private BufferedImage img = SpriteSheet.Terrain.getImage();
	public boolean[][] grassLeft, grassRight;

	public GrassBlock(int id)
	{
		super(id);
	}

	public GrassBlock(int id, int texture, boolean canSpread)
	{
		super(id, texture);
		this.canSpread = canSpread;
		if(canSpread) img = img.getSubimage(getSheetCoordinates()[0], getSheetCoordinates()[1], Tile.tileSize, 4);
	}

	public void renderOverlay(int x, int y)
	{
		try
		{
			if(canSpread)
			{
				if(grassLeft[x][y]) Render.drawImage(Render.rotate(img, -90), (x * Tile.tileSize) - (int)Main.sX, (y * Tile.tileSize) - (int)Main.sY);
				if(grassRight[x][y]) Render.drawImage(Render.rotate(img, 90), (x * Tile.tileSize) + 12 - (int)Main.sX, (y * Tile.tileSize) - (int)Main.sY);
				Render.drawImage(texture.getImageIcon().getSubimage(0, 0, Tile.tileSize, 1), (x * Tile.tileSize) - (int)Main.sX, (y * Tile.tileSize) - (int)Main.sY);
			}
		}
		catch(Exception e){}
	}

	public void blockTick(World world, int x, int y)
	{	
		if(grassLeft == null) grassLeft = new boolean[world.worldW][world.worldH];
		if(grassRight == null) grassRight = new boolean[world.worldW][world.worldH];
		if(x >= 0 && y >= 0 && x < world.worldW && y < world.worldH)
		{
			if(world.getBlock(x, y) == Block.dirt && world.canBlockSeeSky(x, y) && rand.nextInt(700) == 0 && world.isDay) world.setBlock(x, y, Block.grass.blockID);
			for(int i = -1; i <= 1; i++)
				for(int j = -1; j <= 1; j++)
					if(world.getBlock(x, y) == Block.dirt && world.getBlock(x, y - 1) == Block.air && world.getBlock(x + i, y + j) == Block.grass && rand.nextInt(700) == 0 && world.isDay)
						world.setBlock(x, y, Block.grass.blockID);
			for(int i = -1; i <= 1; i++)
				for(int j = -1; j <= 1; j++)
					if(world.getBlock(x, y) == Block.grass && world.getBlock(x, y - 1) != Block.air && world.getBlock(x + i, y + j) != Block.grass && rand.nextInt(700) == 0)
						world.setBlock(x, y, Block.dirt.blockID);
			try
			{
				if(world.getBlock(x, y) == Block.grass)
				{
					if(world.getBlock(x - 1, y + 1) == Block.grass) grassLeft[x][y] = true;
					else grassLeft[x][y] = false;
					if(world.getBlock(x + 1, y + 1) == Block.grass) grassRight[x][y] = true;
					else grassRight[x][y] = false;
				}
			}
			catch(Exception e){}
		}
	}
}
