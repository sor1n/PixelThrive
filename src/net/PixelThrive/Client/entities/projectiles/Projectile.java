package net.PixelThrive.Client.entities.projectiles;

import net.PixelThrive.Client.entities.*;
import java.awt.image.*;
import net.PixelThrive.Client.*;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.*;

public abstract class Projectile extends Entity
{
	protected double angle;
	protected double nx, ny;
	protected float speed;
	protected int damage;
	protected BufferedImage icon;
	
	public Projectile(int x, int y, double dir, int width, int height, int[] id)
	{
		super(x, y, width, height, Tile.tileSize, Tile.tileSize, id, true);
		angle = dir;
		canBeHurt = false;
	}
	
	public void tick()
	{
		
	}
	
	public void render()
	{
		if(dir > 0) Render.drawImage(SpriteSheet.Item.getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY - (int)height, (int) (x + textureWidth) - (int) Main.sX, (int) (y + textureHeight - height) - (int) Main.sY, /**/ (id[0] * Tile.tileSize) + (Tile.tileSize * animation), id[1] * Tile.tileSize, (id[0] * Tile.tileSize) + (Tile.tileSize * animation) + (int) textureWidth, id[1] * Tile.tileSize + (int)textureHeight);
		else Render.drawImage(SpriteSheet.Item.getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY - (int)height, (int) (x + textureWidth) - (int) Main.sX, (int) (y + textureHeight - height) - (int) Main.sY, /**/ (id[0] * Tile.tileSize) + (Tile.tileSize * animation) + (int) textureWidth, id[1] * Tile.tileSize, (id[0] * Tile.tileSize) + (Tile.tileSize * animation), id[1] * Tile.tileSize + (int)textureHeight);
	}
}
