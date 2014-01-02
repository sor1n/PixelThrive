package net.PixelThrive.Client.entities;

import net.PixelThrive.Client.blocks.Block;

public class WaterEntity extends Entity
{
	public WaterEntity(int x, int y, int width, int height)
	{
		super(x, y, width, height);
		canWander = false;
		fallingSpeed = 0;
	}
	
	public WaterEntity(int x, int y, int width, int height, int textureWidth, int textureHeight, int[] id, boolean renderAfterPlayer)
	{
		super(x, y, width, height, textureWidth, textureHeight, id, renderAfterPlayer);
		canWander = false;
		fallingSpeed = 0;
	}
	
	public Block spawnBlock()
	{
		return Block.water;
	}
}
