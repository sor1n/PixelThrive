package net.PixelThrive.Client.blocks;

public class BarrierBlock extends Block
{
	public BarrierBlock(int id)
	{
		super(id);
	}

	public BarrierBlock(int id, int texture)
	{
		super(id, texture);
	}
	
	public boolean isSelectable()
	{
		return false;
	}
}
