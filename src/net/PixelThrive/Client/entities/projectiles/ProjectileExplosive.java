package net.PixelThrive.Client.entities.projectiles;

import java.awt.Point;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.items.*;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.world.*;

public class ProjectileExplosive extends Projectile
{
	protected float explosionSize;
	protected int slowDown = 0, bounceDelay = 0;

	public ProjectileExplosive(int x, int y, double dir, float explosionSize, int width, int height, int id)
	{
		super(x, y, dir, width, height, null);
		this.explosionSize = explosionSize;
		speed = 3;
		nx = speed * Math.cos(dir);
		ny = speed * Math.sin(dir);
		icon = Item.getItem(id).getTexture().getImageIcon();
	}

	public void tick()
	{	
		if(bounceDelay <= 200) x += nx;
		if(bounceDelay <= 40) y += ny;
		bounceDelay++;
		isCollidingUp = (isCollidingWithBlock(new Point((int) (x + 2), (int) y), new Point((int) (x + width - 2), (int) y))) | noclip;
		isCollidingRight = (isCollidingWithBlock(new Point((int) (x + width), (int) y), new Point((int) (x + width), (int) (y + height - 2)))) | noclip;
		isCollidingLeft = (isCollidingWithBlock(new Point((int) x - 1, (int) y), new Point((int) x - 1, (int) (y + (height - 2))))) | noclip;
		isCollidingDown = (isCollidingWithBlock(new Point((int) x + 2, (int) (y + height)), new Point((int) (x + width - 2), (int) (y + height))));
		if(!isCollidingDown) y++;
		if(isCollidingDown && bounceDelay < 40) y-= (40 - bounceDelay)/10;
		if(bounceDelay <= 200 && (isCollidingLeft || isCollidingRight || isCollidingDown || isCollidingUp))
		{
			nx = -nx;
			if(isCollidingDown || isCollidingUp) ny = -ny;
			if(bounceDelay == 200 && angle != -1.5886515719344492) angle = 0;
			else if(bounceDelay == 200) angle = -1.5886515719344492;
		}
		if(bounceDelay > 300) explode(explosionSize);
	}

	public void explode(float size)
	{
		int var9;
		int var10;
		int var11;
		for (var9 = (int) (x/Tile.tileSize - size); var9 <= x/Tile.tileSize + size; ++var9)
		{
			for (var10 = (int) (y/Tile.tileSize - size); var10 <= y/Tile.tileSize + size; ++var10)
			{
				var11 = (int) (var9 - x/Tile.tileSize);
				int var12 = (int) (var10 - y/Tile.tileSize);
				if (var11 * var11 + var12 * var12 <= size * size + 1)
				{
					try
					{
						Main.world.explosionBlockDestroy(var9, var10, 7, explosionSize*80);
						Main.world.explosionBlockDestroy(var9 + rand.nextInt(2), var10 + rand.nextInt(2), 7, explosionSize*80);
						Main.world.explosionBlockDestroy(var9 - rand.nextInt(2), var10 - rand.nextInt(2), 7, explosionSize*80);
						Main.world.explosionBlockDestroy(var9 - rand.nextInt(2), var10 + rand.nextInt(2), 7, explosionSize*80);
						Main.world.explosionBlockDestroy(var9 + rand.nextInt(2), var10 - rand.nextInt(2), 7, explosionSize*80);
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
		despawnEntity();
	}

	public void render()
	{
		Render.drawImage(Render.rotate(icon, Math.toDegrees(angle) + 90), (int)x - (int)Main.sX, (int)y - (int)Main.sY);
	}
}
