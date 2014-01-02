package net.PixelThrive.Client.entities.particles;

import java.util.*;
import net.PixelThrive.Client.world.*;
import net.PixelThrive.Client.*;

public class Bubble extends Particle
{
	protected int blockX, blockY;
	
	public Bubble(int x, int y)
	{
		super(x, y, new Random().nextBoolean()? Tile.bubble_big : Tile.bubble_small, 1000, false);
		this.x = x;
		this.y = y;
	}
	
	public void tick()
	{
		blockX = (int)(x / Tile.tileSize);
		blockY = (int)(y / Tile.tileSize);
		super.tick();
		if(Main.world.getBlock(blockX, blockY).blockID == 0) despawn();
		else y -= 0.1;
	}
}
