package net.PixelThrive.Client.renders;

import java.awt.image.BufferedImage;

import net.PixelThrive.Client.LoadedImage;
import net.PixelThrive.Client.world.Tile;

public class Texture 
{
	private final SpriteSheet mainSheet;
	private int textureX, textureY, width, height;
	public int x, y;
	private LoadedImage icon;
	
	public Texture(SpriteSheet sheet, int x, int y)
	{
		this.mainSheet = sheet;
		setTextureWidth(Tile.tileSize);
		setTextureHeight(Tile.tileSize);
		this.x = x;
		this.y = y;
		icon = new LoadedImage(sheet.getImage(), x * Tile.tileSize, y * Tile.tileSize, Tile.tileSize, Tile.tileSize);
	}
	
	public Texture(BufferedImage img, int x, int y)
	{
		mainSheet = null;
		icon = new LoadedImage(img, x, y, img.getWidth(), img.getHeight());
	}
	
	public Texture(LoadedImage img)
	{
		mainSheet = null;
		setTextureWidth(Tile.tileSize);
		setTextureHeight(Tile.tileSize);
		icon = img;
	}
	
	public LoadedImage getIcon()
	{
		return icon;
	}
	
	public BufferedImage getImageIcon()
	{
		return icon.getImage();
	}
	
	public SpriteSheet getSpriteSheet()
	{
		return mainSheet;
	}
	
	public int getTextureX()
	{
		return textureX;
	}
	
	public int getTextureY()
	{
		return textureY;
	}
	
	public int getTextureWidth()
	{
		return width;
	}

	public int getTextureHeight()
	{
		return height;
	}
	
	public void setTextureWidth(int width)
	{
		this.width = width;
	}

	public void setTextureHeight(int height)
	{
		this.height = height;
	}
}
