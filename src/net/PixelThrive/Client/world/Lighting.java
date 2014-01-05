package net.PixelThrive.Client.world;

import java.awt.*;
import net.PixelThrive.Client.renders.*;
import net.PixelThrive.Client.*;
import net.PixelThrive.Client.blocks.*;

public class Lighting
{
	public int worldW, worldH;
	public World w;
	public LightRender[][] shadow, light;

	public Lighting(World world)
	{
		worldW = world.worldW;
		worldH = world.worldH;
		w = world;
		shadow = new LightRender[worldW][worldH];
		light = new LightRender[worldW][worldH];

		for(int x = 0; x < worldW; x++)
		{
			for(int y = 0; y < worldH; y++)
			{
				shadow[x][y] = new LightRender(new Rectangle(x * Tile.tileSize, y * Tile.tileSize, Tile.tileSize, Tile.tileSize), world, 0);
				light[x][y] = new LightRender(new Rectangle(x * Tile.tileSize, y * Tile.tileSize, Tile.tileSize, Tile.tileSize), world, 0);
			}
		}
	}

	public void tick(int camX, int camY, int renW, int renH)
	{	
		for(int x = (camX / Tile.tileSize); x < (camX / Tile.tileSize) + renW; x++)
		{
			for(int y = (camY / Tile.tileSize); y < (camY / Tile.tileSize) + renH; y++)
			{
				try{
					if(shadow[x][y].getSource() == null || Main.world.getBlockID(x, y) == 0 || Main.world.getBlock(x, y).isTransparent() || Main.world.getBackgroundBlock(x, y).isTransparent()) shadow[x][y].id = 0;
					if(light[x][y].getSource() == null) light[x][y].id = 0;
				}catch(Exception e){}
			}
		}
	}

	public void render(int camX, int camY, int renW, int renH)
	{
		for(int x = (camX / Tile.tileSize); x < (camX / Tile.tileSize) + renW; x++)
		{
			for(int y = (camY / Tile.tileSize); y < (camY / Tile.tileSize) + renH; y++)
			{
				if(x >= 0 && y >= 0 && x < worldW && y < worldH && (w.getBackgroundBlockID(x, y) != 0 || w.getBlock(x, y) != Block.air || !w.getBlock(x, y).isTransparent() || !w.getBackgroundBlock(x, y).isTransparent()) && light[x][y].id == 0 && !w.isNight) shadow[x][y].render();
				else if(x >= 0 && y >= 0 && x < worldW && y < worldH && light[x][y].id == 0 && w.isNight) shadow[x][y].render();
				if(x >= 0 && y >= 0 && x < worldW && y < worldH && shadow[x][y].id == 0) light[x][y].render();
			}
		}
	}

	public void lightCircle(int x, int y, int size, Object source)
	{
		int var9;
		int var10;
		int var11;
		for(var9 = x - size; var9 <= x + size; ++var9)
		{
			for(var10 = y - size; var10 <= y + size; ++var10)
			{
				var11 = var9 - x;
				int var12 = var10 - y;
				if (var11 * var11 + var12 * var12 <= size * size + 1)
				{
					try
					{
						setLight(var9, var10, 2, source);
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
	}

	public void removeLightCircle(int x, int y, int size, Object source)
	{
		int var9;
		int var10;
		int var11;
		for(var9 = x - size; var9 <= x + size; ++var9)
		{
			for(var10 = y - size; var10 <= y + size; ++var10)
			{
				var11 = var9 - x;
				int var12 = var10 - y;
				if (var11 * var11 + var12 * var12 <= size * size + 1)
				{
					try
					{
						removeLight(var9, var10);
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
	}
	
	public void lightCircle(int x, int y, int size, int alpha, Object source)
	{
		int var9;
		int var10;
		int var11;
		for(var9 = x - size; var9 <= x + size; ++var9)
		{
			for(var10 = y - size; var10 <= y + size; ++var10)
			{
				var11 = var9 - x;
				int var12 = var10 - y;
				if (var11 * var11 + var12 * var12 <= size * size + 1)
				{
					try
					{
						setLight(var9, var10, alpha, source);
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
	}
	
	public void shadowCircle(int x, int y, int size, int alpha, Object source)
	{
		int var9;
		int var10;
		int var11;
		for(var9 = x - size; var9 <= x + size; ++var9)
		{
			for(var10 = y - size; var10 <= y + size; ++var10)
			{
				var11 = var9 - x;
				int var12 = var10 - y;
				if (var11 * var11 + var12 * var12 <= size * size + 1)
				{
					try
					{
						setShadow(var9, var10, alpha, source);
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
	}

	public void setShadow(int x, int y, int alpha, Object source)
	{
		shadow[x][y].setShadow(true);
		shadow[x][y].setAlpha(alpha);
		shadow[x][y].setBrightness(0);
		shadow[x][y].setSource(source);
		shadow[x][y].id = 1;
	}

	public void removeShadow(int x, int y)
	{
		shadow[x][y].setShadow(false);
		shadow[x][y].setAlpha(0);
		shadow[x][y].setBrightness(0);
		shadow[x][y].setSource(null);
		shadow[x][y].id = 0;
	}
	
	public LightRender getShadow(int x, int y)
	{
		if(x >= 0 && x < w.worldW && y >= 0 && y < w.worldH) return shadow[x][y];
		else return shadow[0][0];
	}

	public void setLight(int x, int y, int alpha, Object source)
	{
		light[x][y].setShadow(false);
		light[x][y].setAlpha(alpha);
		light[x][y].setBrightness(0);
		light[x][y].setSource(source);
		light[x][y].id = 1;
	}

	public void removeLight(int x, int y)
	{
		light[x][y].setShadow(true);
		light[x][y].setAlpha(0);
		light[x][y].setBrightness(0);
		light[x][y].setSource(null);
		light[x][y].id = 0;
	}
	
	public LightRender getLight(int x, int y)
	{
		if(x >= 0 && x < w.worldW && y >= 0 && y < w.worldH) return light[x][y];
		else return light[0][0];
	}
}
