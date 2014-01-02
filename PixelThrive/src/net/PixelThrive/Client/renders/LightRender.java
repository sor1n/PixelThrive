package net.PixelThrive.Client.renders;

import java.awt.*;
import net.PixelThrive.Client.*;
import net.PixelThrive.Client.world.*;

public class LightRender extends Rectangle
{
	private static final long serialVersionUID = 1L;

	private int brightness = 0;
	private int alpha = 0;
	private int r = -1, g = -1, b = -1;
	private World lightWorld;
	public int id;
	private Object source;
	private boolean isShadow = false;

	public LightRender(Rectangle size, World world, int id)
	{
		setBounds(size);
		lightWorld = world;
		this.id = id;
	}

	public void setShadow(boolean bool)
	{
		isShadow = bool;
	}

	public boolean isShadow()
	{
		return isShadow;
	}

	public void setSource(Object obj)
	{
		source = obj;
	}

	public void setBrightness(int light)
	{
		brightness = light;
	}

	public void setAlpha(int alpha)
	{
		this.alpha = alpha;
	}

	public void setRGB(int r, int g, int b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public int getR()
	{
		return r;
	}

	public int getG()
	{
		return g;
	}

	public int getB()
	{
		return b;
	}

	public int getBrightness()
	{
		return brightness;
	}

	public int getAlpha()
	{
		return alpha;
	}

	public void render()
	{
		if(isShadow)
		{
			if(r != -1 && b != -1 && g != -1) Render.setColor(r, g, b, alpha);
			else Render.setColor(brightness, brightness, brightness, alpha);
			if(source != null && id != 0)
			{
				for(int x = 0; x < Tile.tileSize; x++)
				{
					for(int y = 0; y < Tile.tileSize; y++)
					{
						if(lightWorld.getBlock(this.x, this.y).getTexture().getImageIcon().getRGB(x, y) != 0x00000000) Render.fillRect((this.x - (int)Main.sX) + (x), (this.y - (int)Main.sY) + (y), 1, 1);
					}
				}
			}
		}
		else
		{
			Render.setColor(new Color(0, 0, 0, Main.sky.darkness));
			if(source == null || id == 0 || (lightWorld.getBackgroundBlockID(x / Tile.tileSize, y / Tile.tileSize) == 0 && lightWorld.getBlockID(x / Tile.tileSize, y / Tile.tileSize) == 0)) Render.fillRect(x - (int)Main.sX, y - (int)Main.sY, Tile.tileSize, Tile.tileSize);
			if(lightWorld.isDay)
			{
				if(alpha > 0) alpha--;
			}
			else 
			{
				if(alpha < 10) alpha++;
				else alpha = 0;
			}
			if(r != -1 && b != -1 && g != -1) Render.setColor(r, g, b, alpha);
			else Render.setColor(brightness, brightness, brightness, alpha);
			if(source != null && id != 0 && lightWorld.getBackgroundBlockID(x / Tile.tileSize, y / Tile.tileSize) != 0) Render.fillRect(x - (int)Main.sX, y - (int)Main.sY, Tile.tileSize, Tile.tileSize);
		}
	}

	public World getWorld()
	{
		return lightWorld;
	}

	public Object getSource()
	{
		return source;
	}
}
