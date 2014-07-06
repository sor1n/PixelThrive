package net.PixelThrive.Client.entities;

import java.util.Random;

import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.renders.EntityRenders;
import net.PixelThrive.Client.world.Tile;

public class GolemGuard extends Entity
{
	public GolemGuard(int x, int y)
	{
		super(x, y, Tile.tileSize, Tile.tileSize * 2);
		fallingSpeed = 1.4;
		canWander = true;
		entityRender = new EntityRenders(this, Tile.tileSize, Tile.tileSize * 2, 80, new int[]{81, 80, 82, 80}, 10);
		entityName = "Golem Guard";
		setStartHealth(25);
		setMovingSpeed(0.5);
		setLightLevel(105, 235);
		setEXPAmount(100 + new Random().nextInt(900));
	}
	
	public GolemGuard()
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
	
	public boolean spawnsAnytime()
	{
		return true;
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
