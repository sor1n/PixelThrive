package net.PixelThrive.Client.blocks;

import net.PixelThrive.Client.materials.BlockMaterial;

public class CobwebBlock extends Block
{	
	public CobwebBlock(int id)
	{
		super(id);
		setMaterial(BlockMaterial.WEB);		
	}

	public double slowDownInBlock()
	{
		return 0.7D;
	}

	public boolean isSolid()
	{
		return false;
	}
}
