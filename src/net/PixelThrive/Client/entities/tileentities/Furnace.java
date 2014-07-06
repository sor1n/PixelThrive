package net.PixelThrive.Client.entities.tileentities;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.world.Tile;

public class Furnace extends TileEntity
{
	int[] texture = Tile.furnace_inactive;
	int j = 0;
	
	public Furnace(int x, int y)
	{
		super(x, y, Tile.tileSize * 2, Tile.tileSize * 2, Tile.furnace_inactive, false);
	}
	
	public Furnace()
	{
		this(0, 0);
	}
	
	public void tick()
	{
		super.tick();		
		texture = Tile.furnace_inactive;		
		if(isCollidingWithPlayer() && j == 0){
			j = 1;
			Main.inv.isNearFurnace = true;
		}
		if(!isCollidingWithPlayer() && j == 1){
			j = 0;
			Main.inv.isNearFurnace = false;
		}
		if(Main.inv != null && Main.inv.isOpen)
		{
			if(this.isCollidingWithPlayer()) texture = Tile.furnace_active;
			else texture = Tile.furnace_inactive;
		}
		id = texture;
	}
}
