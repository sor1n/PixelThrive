package net.PixelThrive.Client.entities.particles;

import java.util.Random;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.world.Sky;

public class Star 
{
	private double x, y;
	private int starColor = 255;
	
	public Star(int x, int y)
	{
		this.x = x;
		this.y = y;
		Sky.getStars().add(this);
	}
	
	public void tick()
	{
		if(starColor > 0) starColor--;
		else if(starColor <= 0) Sky.getStars().remove(this);
		
		if(Main.player.isMoving)
		{
			if(Main.player.getDir() > 0) x -= 0.02;
			else x += 0.02;
		}
	}
	
	public void render()
	{
		Render.setColor(255, 245, 107, starColor);
		Render.fillRect((int)x - (int)Main.sX, (int)y - (int)Main.sY, 2, 2);
	}
	
	public static void deployRandomStars(int xLimit, int yLimit)
	{
		for(int i = 0; i < new Random().nextInt(18); i++) new Star(new Random().nextInt(xLimit) + (int)Main.sX, new Random().nextInt(yLimit) + (int)Main.sY);
	}
	
	public int getX()
	{
		return (int)x;
	}
	
	public int getY()
	{
		return (int)y;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
}
