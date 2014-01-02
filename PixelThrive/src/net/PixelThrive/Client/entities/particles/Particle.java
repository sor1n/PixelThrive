package net.PixelThrive.Client.entities.particles;

import java.awt.Point;
import java.awt.image.*;

import net.PixelThrive.Client.*;
import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.blocks.LiquidBlock;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.Tile;
import net.PixelThrive.Client.entities.*;

public abstract class Particle extends DoubleRectangle
{
	protected int[] id;
	protected int x, y, lifespan = 0, maxLifeSpan;
	
	public boolean renderAfterPlayer, isCollidingUp = false, isCollidingDown = false, isCollidingRight = false, isCollidingLeft = false;
	
	private BufferedImage icon;
	
	public Particle(int x, int y, int[] id, int maxLifeSpan, boolean renderAfterPlayer)
	{
		setBounds(x, y, Tile.tileSize, Tile.tileSize);
		this.id = id;
		this.x = x;
		this.y = y;
		this.renderAfterPlayer = renderAfterPlayer;
		this.maxLifeSpan = maxLifeSpan;
		spawn();
		if(id != null)
		{
			icon = SpriteSheet.Particle.getImage().getSubimage(id[0] * Tile.tileSize, id[1] * Tile.tileSize, Tile.tileSize, Tile.tileSize);
		}
	}
	
	public void tick()
	{
		isCollidingUp = (isCollidingWithBlock(new Point((int) (x + 2), (int) y), new Point((int) (x + width - 2), (int) y)));
		isCollidingRight = (isCollidingWithBlock(new Point((int) (x + width), (int) y), new Point((int) (x + width), (int) (y + height - 2))));
		isCollidingLeft = (isCollidingWithBlock(new Point((int) x - 1, (int) y), new Point((int) x - 1, (int) (y + (height - 2)))));
		isCollidingDown = (isCollidingWithBlock(new Point((int) x + 2, (int) (y + height)), new Point((int) (x + width - 2), (int) (y + height))));
		
		if(lifespan < maxLifeSpan) lifespan++;
		else despawn();
	}
	
	public void render()
	{
		Render.drawImage(icon, (int) x - (int) Main.sX, (int) y - (int) Main.sY);
	}
	
	public void spawn()
	{
		Main.getParticles().add(this);
	}
	
	public void despawn()
	{
		Main.getParticles().remove(this);
	}
	
	public boolean isCollidingWithBlock(Point pt1, Point pt2)
	{
		for(int x =(int) (this.x / Tile.tileSize); x < (int) (this.x / Tile.tileSize + 3); x++)
		{
			for(int y =(int) (this.y / Tile.tileSize); y < (int) (this.y / Tile.tileSize + 3); y++)
			{
				if(Main.world != null && x >= 0 && y >= 0 && x < Main.world.block.length && y < Main.world.block[0].length)
				{
					if(Main.world.block[x][y].id != Block.air.blockID && !(Main.world.block[x][y].getBlock() instanceof LiquidBlock))
					{
						if(Main.world.block[x][y].contains(pt1) || Main.world.block[x][y].contains(pt2))
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
