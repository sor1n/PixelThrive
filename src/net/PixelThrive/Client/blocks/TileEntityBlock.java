package net.PixelThrive.Client.blocks;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.entities.tileentities.TileEntity;
import net.PixelThrive.Client.world.Tile;
import net.PixelThrive.Client.world.World;

public class TileEntityBlock extends Block
{
	private Class<?> tileEntity;
	
	public TileEntityBlock(int id, Class<? extends TileEntity> cl)
	{
		super(id);
		tileEntity = cl;
		setTurnable(false);
	}
	
	public void onPlaced(World world, int x, int y)
	{
		if(!Main.player.isCreative) Main.inv.invBar[Main.inv.selection].removeItemFromSlot();
		try
		{
			TileEntity e = (TileEntity) tileEntity.newInstance();
			e.placeTileEntity(x * Tile.tileSize, y * Tile.tileSize);
			e.spawnEntity();
		}
		catch(InstantiationException e)
		{
			e.printStackTrace();
		} 
		catch(IllegalAccessException e)
		{
			e.printStackTrace();
		}
	}
}
