package net.PixelThrive.Client.entities;

import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.renders.EntityRenders;
import net.PixelThrive.Client.world.Tile;

public class ShadowGhoul extends Entity
{
	public ShadowGhoul(int x, int y)
	{
		super(x, y, Tile.tileSize*2, Tile.tileSize * 3);
		fallingSpeed = 1.4;
		canWander = true;
		entityRender = new EntityRenders(this, Tile.tileSize*2, Tile.tileSize * 3, 112, new int[]{114, 116, 114, 112}, 10);
		entityName = "Shadow Ghoul";
		setStartHealth(25);
		setMovingSpeed(0.5);
		setLightLevel(105, 205);
	}
	
	public ShadowGhoul()
	{
		this(0, 0);
	}
	
	public Block spawnBlock()
	{
		return Block.stoneBricks;
	}
	
	public boolean spawnsAtNight()
	{
		return false;
	}
	
	public void tick()
	{
		super.tick();
		entityRender.tick();
		
		if(dir > 0)
		{
			hatOffsY = -7;
			hatOffsX = 0;
		}
		else
		{
			hatOffsY = -7;
			hatOffsX = -1;
		}
	}
	
	public void render()
	{
		super.renderEntity(entityRender);
	}
}
