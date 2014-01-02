package net.PixelThrive.Client.blocks;

import net.PixelThrive.Client.entities.FallingBlock;
import net.PixelThrive.Client.world.*;

public class GravityBlock extends Block
{
	public GravityBlock(int id)
	{
		super(id);
	}
	
	public GravityBlock(int id, int texture)
	{
		super(id, texture);
	}
	
	public void blockTick(World world, int x, int y)
	{
		if(world.getBlock(x, y + 1) == Block.air)
		{
			new FallingBlock(x, y, blockID).spawnEntity();
			world.setBlock(x, y, Block.air.blockID);
		}
	}
}
