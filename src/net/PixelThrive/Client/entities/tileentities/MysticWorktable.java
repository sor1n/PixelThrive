package net.PixelThrive.Client.entities.tileentities;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.world.Tile;

public class MysticWorktable extends TileEntity
{
	int j = 0;
	public MysticWorktable(int x, int y)
	{
		super(x, y, Tile.tileSize * 2, Tile.tileSize * 2, Tile.alchemyTable, false);
	}
	
	public MysticWorktable()
	{
		this(0, 0);
	}
	
	public void tick()
	{
		super.tick();		
		if(isCollidingWithPlayer() && j == 0){
			j = 1;
			Main.inv.isNearMysticWorktable = true;
		}
		if(!isCollidingWithPlayer() && j == 1){
			j = 0;
			Main.inv.isNearMysticWorktable = false;
		}
	}
}
