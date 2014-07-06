package net.PixelThrive.Client.entities.particles;

import java.util.*;
import net.PixelThrive.Client.world.*;

public class JetFlamesParticle extends Particle
{
	protected int blockX, blockY;
	
	public JetFlamesParticle(int x, int y)
	{
		super(x, y, new Random().nextBoolean()? Tile.jet_flame1 : Tile.jet_flame2, 4, true);
		this.x = x;
		this.y = y;
	}
	
	public void tick()
	{
		blockX = (int)(x / Tile.tileSize);
		blockY = (int)(y / Tile.tileSize);
		super.tick();
		y+=1;
		x+=(new Random().nextInt(2) - new Random().nextInt(2));
	}
}
