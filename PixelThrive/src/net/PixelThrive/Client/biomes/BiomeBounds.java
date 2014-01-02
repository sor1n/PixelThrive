package net.PixelThrive.Client.biomes;

import java.awt.Point;
import java.awt.Rectangle;

import net.PixelThrive.Client.world.Tile;
import net.PixelThrive.Client.world.World;

public class BiomeBounds
{
	private Rectangle bounds = new Rectangle();
	private int x, id;
	
	public BiomeBounds(int x, int id, World world)
	{	
		this.x = x;
		this.id = id;
		bounds.setBounds(x - 5, 0, Biome.biomes.get(id).getBiomeSize(), world.worldH * Tile.tileSize);
	}
	
	public boolean contains(Point point)
	{
		return bounds.contains(point);
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getID()
	{
		return id;
	}
}