package net.PixelThrive.Client.entities.tileentities;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.world.Tile;

public class CraftingBench extends TileEntity
{
	int j = 0;
	public CraftingBench(int x, int y)
	{
		super(x, y, Tile.tileSize * 2, Tile.tileSize * 2, Tile.crafting_bench, false);
	}
	
	public CraftingBench()
	{
		this(0, 0);
	}
	
	public void tick()
	{
		super.tick();
		if(isCollidingWithPlayer() && j == 0){
			j = 1;
			Main.inv.isNearCraftingBench = true;
		}
		if(!isCollidingWithPlayer() && j == 1){
			j = 0;
			Main.inv.isNearCraftingBench = false;
		}
	}
}
