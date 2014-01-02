package net.PixelThrive.Client.entities.tileentities;

import java.awt.image.BufferedImage;
import java.util.Random;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.Tile;

public class Cloud extends TileEntity
{
	private BufferedImage[] clouds = {SpriteSheet.getIcon(SpriteSheet.TileEntity, 0, 2, Tile.tileSize * 4, Tile.tileSize * 2), SpriteSheet.getIcon(SpriteSheet.TileEntity, 4, 2, Tile.tileSize * 4, Tile.tileSize * 2), SpriteSheet.getIcon(SpriteSheet.TileEntity, 8, 2, Tile.tileSize * 4, Tile.tileSize * 2)};
	private BufferedImage cloud;
	private int rand = new Random().nextInt(clouds.length);
	private float speed;
	
	public Cloud(int x, int y)
	{
		super(x, y, Tile.tileSize * 4, Tile.tileSize * 2, null, false);
		cloud = clouds[rand];
		speed = new Random().nextFloat() - 0.7F;
		if(speed > 0) speed = -speed;
	}
	
	public void tick()
	{
		x += speed;
	}
	
	public void render()
	{
		Render.drawImage(cloud, (int)x - (int)Main.sX, (int)y - (int)Main.sY);
	}
}
