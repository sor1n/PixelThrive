package net.PixelThrive.Client.entities;

import java.awt.image.BufferedImage;
import java.util.Random;

import net.PixelThrive.Client.renders.EntityRenders;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.Tile;

public class GlowFish extends WaterEntity
{
	private BufferedImage render;
	private int rand = new Random().nextInt(6);

	public GlowFish(int x, int y)
	{
		super(x, y, Tile.tileSize, Tile.tileSize);
		entityName = "Glow Fish";
		setStartHealth(5);
		setMovingSpeed(0.5);
		setLightLevel(5, 15);
		disableEntityAttribute(EntityAttribute.WINGED);
		disableEntityAttribute(EntityAttribute.CLASSY);
		render = SpriteSheet.getIcon(SpriteSheet.Entity, rand, 10);
	}

	public GlowFish()
	{
		this(0, 0);
	}

	public void render()
	{
		renderEntity(null);
	}

	public void renderEntity(EntityRenders entity, int x, int y)
	{			
		if(dir > 0) Render.drawImage(render, x, y);
		else Render.drawImage(render, x + (int)width, y, (x + (int)width) - (int)width, y + (int)height, 0, 0, render.getWidth(), render.getHeight());
	}
}
