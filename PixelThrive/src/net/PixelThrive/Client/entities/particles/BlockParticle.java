package net.PixelThrive.Client.entities.particles;

import net.PixelThrive.Client.*;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.world.*;
import net.PixelThrive.Client.blocks.*;
import java.awt.image.BufferedImage;
import java.util.*;

public class BlockParticle extends Particle
{
	protected int id;
	protected BufferedImage part;
	private int rand = new Random().nextInt(2);
	private boolean up = true;

	protected double x, y, grav = 0.0;
	
	public BlockParticle(int x, int y, int id)
	{
		super(x, y, null, 100, false);
		this.id = id;
		this.x = x;
		this.y = y;
		part = Block.getBlock(id).getTexture().getIcon().getImage();
		int corner = new Random().nextInt(4);
		int resX, resY;
		switch(corner)
		{
		default:
		case 0: resX = 0;
		resY = 0;
		break;
		case 1: resX = 0;
		resY = Tile.tileSize - 4;
		break;
		case 2: resX = Tile.tileSize - 4;
		resY = 0;
		break;
		case 3: resX = Tile.tileSize - 4;
		resY = Tile.tileSize - 4;
		break;
		}
		part = part.getSubimage(resX, resY, 4, 4);
	}

	public void tick()
	{
		super.tick();
		
		if(lifespan >= 20) up = false;
		
		if(!up && grav < 1.5) grav += .1;
		
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
	}

	public void render()
	{
		if(id != Block.air.blockID) Render.drawImage(part, (int)x - (int)Main.sX, (int)y - (int)Main.sY);
	}
}
