package net.PixelThrive.Client.items;

import net.PixelThrive.Client.blocks.Block;

public class CraftableStack
{
	private int amount, metadata;
	private int id;
	
	public CraftableStack(Item item)
	{
		this(item, 1);
	}
	
	public CraftableStack(Block block)
	{
		this(block, 1);
	}
	
	public CraftableStack(Item item, int amount)
	{
		this(item, amount, 0);
	}
	
	public CraftableStack(Block block, int amount)
	{
		this(block, amount, 0);
	}
	
	public CraftableStack(Item item, int amount, int meta)
	{
		this.amount = amount;
		id = item.itemID;
		metadata = meta;
	}
	
	public CraftableStack(Block block, int amount, int meta)
	{
		this.amount = amount;
		id = block.blockID;
		metadata = meta;
	}
	
	public CraftableStack(int id, int amount, int meta)
	{
		this.amount = amount;
		this.id = id;
		metadata = meta;
	}
	
	public CraftableStack(int id, int amount)
	{
		this.amount = amount;
		this.id = id;
		metadata = 0;
	}
	
	public CraftableStack(int id)
	{
		this.id = id;
		this.amount = 1;
		metadata = 0;
	}
	
	public int getAmount()
	{
		return amount;
	}
	
	public int getID()
	{
		return id;
	}
	
	public int getMetadata()
	{
		return metadata;
	}
	
	public void emptyStack()
	{
		id = 0;
		amount = 0;
		metadata = 0;
	}
}
