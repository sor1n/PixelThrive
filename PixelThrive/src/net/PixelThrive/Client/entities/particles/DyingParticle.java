package net.PixelThrive.Client.entities.particles;

import java.awt.image.*;
import net.PixelThrive.Client.*;
import net.PixelThrive.Client.entities.Entity;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.Tile;

import java.util.*;

public class DyingParticle extends Particle
{
	protected BufferedImage img, part;
	private int rand = new Random().nextInt(4);
	private boolean up = true;

	protected double x, y, grav = 0.0;
	protected double rotate = 0.0;
	protected boolean direction = new Random().nextBoolean();

	public DyingParticle(int x, int y, Entity entity)
	{
		super(x, y, null, 100, false);
		this.x = x;
		this.y = y;
		img = SpriteSheet.Entity.getImage();
		int[] id = entity.getID();
		if(id == Tile.player)
		{
			switch(rand)
			{
			case 0:
				part = img.getSubimage(6, 48, 11, 16);
				break;
			case 1:
				part = img.getSubimage(0, 18, 10, 7);
				break;
			case 2:
				part = img.getSubimage(5, 9, 10, 10);
				break;
			default:
				part = img.getSubimage(3, Tile.tileSize + 5, 10, 15);
				break;
			}
		}
		else
		{
			img = img.getSubimage(entity.getEntityRender().getDefaultFrame()[0] * Tile.tileSize, entity.getEntityRender().getDefaultFrame()[1] * Tile.tileSize, Tile.tileSize, Tile.tileSize);
			switch(rand)
			{
			case 0:
				part = img.getSubimage(5, 5, 9, 9);
				break;
			case 1:
				part = img.getSubimage(1, 9, 10, 6);
				break;
			case 2:
				part = img.getSubimage(12, 8, 2, 2);
				break;
			default:
				part = img.getSubimage(7, 6, 5, 6);
				break;
			}
		}
	}

	public void tick()
	{
		super.tick();
		if(lifespan >= 20) up = false;

		if(!up && grav < 1.5) grav += .05;
		if(rand == 0)
		{
			x += .1;
			if(!up) y += grav;
			else y -= .2;
		}
		else
		{
			x -= .1;
			if(!up) y += grav;
			else y -= .2;
		}
		if(direction) rotate += 2;
		else rotate -= 2;
	}

	public void render()
	{
		Render.drawImage(Render.rotate(part, rotate), (int)x - (int)Main.sX, (int)y - (int)Main.sY);
	}
}
