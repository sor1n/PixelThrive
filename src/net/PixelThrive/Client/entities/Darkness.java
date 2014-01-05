package net.PixelThrive.Client.entities;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.renders.EntityRenders;
import net.PixelThrive.Client.world.Tile;

public class Darkness extends Entity
{
	public Darkness(int x, int y)
	{
		super(x, y, Tile.tileSize, Tile.tileSize);
		fallingSpeed = 1.4;
		canWander = true;
		entityRender = new EntityRenders(this, Tile.tileSize, Tile.tileSize, 73, new int[]{73}, 10);
		entityName = "Darkness";
		setMovingSpeed(0.5);
		setLightLevel(205, 235);
		setSpawningAnim(new int[]{68, 69, 70, 71, 72, 73}, 10);
		disableEntityAttribute(EntityAttribute.WINGED);
	}
	
	public Darkness()
	{
		this(0, 0);
	}
	
	public boolean spawnsAtNight()
	{
		return false;
	}
	
	public boolean spawnsAnytime()
	{
		return true;
	}
	
	public boolean isInvincible()
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
		if(Main.world.light.getShadow((int)x/Tile.tileSize, (int)y/Tile.tileSize).getAlpha() < 150 || Main.world.light.getLight((int)x/Tile.tileSize, (int)y/Tile.tileSize).getAlpha() > 0) despawnEntity();
	}
	
	public void render()
	{
		super.renderEntity(entityRender);
	}
	
	public boolean spawnsOnScreen()
	{
		return true;
	}
	
	public boolean spawnsOnAllBlocks()
	{
		return true;
	}
	
	public boolean hasSpawningAnimation()
	{
		return true;
	}
}
