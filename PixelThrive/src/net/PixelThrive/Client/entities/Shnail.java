package net.PixelThrive.Client.entities;

import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.items.CraftableStack;
import net.PixelThrive.Client.items.Item;
import net.PixelThrive.Client.renders.EntityRenders;
import net.PixelThrive.Client.world.Tile;

public class Shnail extends Entity
{	
	public Shnail(int x, int y)
	{
		super(x, y, Tile.tileSize, Tile.tileSize);
		fallingSpeed = 1.4;
		canWander = true;
		entityRender = new EntityRenders(this, 64, new int[]{64, 64, 65, 65}, 10);
		entityName = "Shnail";
		setStartHealth(5);
		setMovingSpeed(0.4);
		setLightLevel(0, 20);
		setDrops(new CraftableStack[]{new CraftableStack(Item.vibratingViolator, 1)});
	}
	
	public Shnail()
	{
		this(0, 0);
	}

	public void tick()
	{
		entityRender.tick();
		super.tick();

		if(dir > 0)
		{
			hatOffsY = 1;
			if(isMoving)
			{
				if(entityRender.getAnimation() == 0 || entityRender.getAnimation() == 1) hatOffsX = 2;
				if(entityRender.getAnimation() == 2 || entityRender.getAnimation() == 3) hatOffsX = 3;
			}
			else hatOffsX = 2;
		}
		else
		{
			hatOffsY = 1;
			if(isMoving)
			{
				if(entityRender.getAnimation() == 0 || entityRender.getAnimation() == 1) hatOffsX = -3;
				if(entityRender.getAnimation() == 2 || entityRender.getAnimation() == 3) hatOffsX = -4;
			}
			else hatOffsX = -3;
		}
	}
	
	public Block spawnBlock()
	{
		return Block.grass;
	}
	
	public boolean spawnsAtNight()
	{
		return false;
	}
	
	public boolean spawnsAnytime()
	{
		return false;
	}

	public void render()
	{
		super.renderEntity(entityRender);
	}
}
