package net.PixelThrive.Client.renders;

import java.awt.image.BufferedImage;

import net.PixelThrive.Client.LoadedImage;
import net.PixelThrive.Client.world.Tile;

public class SpriteSheet
{
	public static final SpriteSheet Terrain = new SpriteSheet("Terrain.png");
	public static final SpriteSheet Entity = new SpriteSheet("Entity.png");
	public static final SpriteSheet TileEntity = new SpriteSheet("TileEntity.png");
	public static final SpriteSheet Item = new SpriteSheet("Item.png");
	public static final SpriteSheet Buff = new SpriteSheet("Buff.png");
	public static final SpriteSheet Particle = new SpriteSheet("Particle.png");
	public static final SpriteSheet GUI = new SpriteSheet("Gui.png");
	public static final SpriteSheet Skill = new SpriteSheet("Skills.png");
	
	private LoadedImage img;
	
	public SpriteSheet(String path)
	{
		img = new LoadedImage(path);
	}
	
	public BufferedImage getImage()
	{
		return img.getImage();
	}
	
	public static BufferedImage getIcon(SpriteSheet sheet, int x, int y)
	{
		return sheet.getImage().getSubimage(x * Tile.tileSize, y * Tile.tileSize, Tile.tileSize, Tile.tileSize);
	}
	
	public BufferedImage getIconImage(int x, int y)
	{
		return getImage().getSubimage(x, y, Tile.tileSize, Tile.tileSize);
	}
	
	public BufferedImage getIconImage(int x, int y, int width, int height)
	{
		return getImage().getSubimage(x, y, width, height);
	}
	
	public static BufferedImage getIcon(SpriteSheet sheet, int x, int y, int width, int height)
	{
		return sheet.getImage().getSubimage(x * Tile.tileSize, y * Tile.tileSize, width, height);
	}
}
