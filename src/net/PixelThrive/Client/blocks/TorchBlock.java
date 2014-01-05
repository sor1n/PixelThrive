package net.PixelThrive.Client.blocks;

import net.PixelThrive.Client.entities.Drop;
import net.PixelThrive.Client.world.*;
import net.PixelThrive.Client.*;

public class TorchBlock extends Block
{
	public TorchBlock(int id)
	{
		super(id);
	}
	
	public void blockTick(World world, int x, int y)
	{
		world.light.lightCircle(x, y, 4, this);
		if(!isAttachedToBlock(x, y)){
			Main.world.light.removeLightCircle(x, y, 4, this);
			Main.world.setBlock(x, y, 0);
			new Drop((int)x*Tile.tileSize + 4, (int)y*Tile.tileSize + 4, this.blockID, 0).spawnEntity();
		}
	}
	
	public void onDestroyed(int x, int y)
	{
		Main.world.light.removeLightCircle(x, y, 4, this);
		super.onDestroyed(x, y);
	}
}
