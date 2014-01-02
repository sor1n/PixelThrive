package net.PixelThrive.Client.blocks;

import net.PixelThrive.Client.PixelException;

public class OreBlock extends Block
{	
	public OreBlock(int id, int r, int s)
	{
		super(id);
		if(ores.size() <= 0) ores.add(this);
		else
		{
			if(id < IDs.length)
			{
				ores.add(this);
			}
			else throw new PixelException("Ran out of Block IDs!", PixelException.ExceptionType.NOTENOUGHIDS, null, null);
		}
		setRarity(r);
		setMaxVeinSize(s);
	}
}
