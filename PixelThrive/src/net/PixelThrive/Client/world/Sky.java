package net.PixelThrive.Client.world;

import java.awt.Rectangle;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import net.PixelThrive.Client.LoadedImage;
import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.entities.particles.Star;
import net.PixelThrive.Client.entities.tileentities.Cloud;
import net.PixelThrive.Client.renders.Render;

public class Sky
{
	private static List<Star> stars = new CopyOnWriteArrayList<Star>();
	private static List<Cloud> clouds = new CopyOnWriteArrayList<Cloud>();

	public int r1 = 60, g1 = 180, b1 = 220;  //day
	public int r2 = 0, g2 = 0, b2 = 80;  //night
	public int r3 = 0, g3 = 0, b3 = 30; //Space
	public int darknessDay = 0, darknessNight = 150;
	public int r = r1, g = g1, b = b1, darkness = darknessDay;
	private Rectangle[] sky = new Rectangle[Main.HEIGHT / 2];
	private int[] rr = new int[Main.HEIGHT], gg = new int[Main.HEIGHT];
	private int alpha = 0;
	public static int CLOUD_INT = 0, CLOUD_LIMIT = 15;
	public int cloudInt = 0;
	
	private LoadedImage sun = new LoadedImage("Sun.png"), moon = new LoadedImage("Moon.png");
	
	public Sky()
	{
		for(int i = 0; i < sky.length; i++)
		{
			rr[i] = 235;
			gg[i] = 249;
			sky[i] = new Rectangle(0, i * 2, Main.WIDTH, 2);
		}
		int reds = 7;
		for(int red = 0; red < rr.length; red++)
		{
			rr[red] = reds;
			if(reds < 235) reds += 2;
		}
		int greens = 184;
		for(int green = 0; green < gg.length; green++)
		{
			gg[green] = greens;
			if(greens < 249) greens++;
		}
		
		if(Main.world.isDay)
		{
			alpha = 0;
			r = r1;
			g = g1;
			b = b1;
			darkness = darknessDay;
		}
		else if(Main.world.isNight)
		{
			alpha = 255;
			r = r2;
			g = g2;
			b = b2;
			darkness = darknessNight;
		}
	}

	public void tick()
	{	
		if(Main.world != null && Main.player != null && (Main.world.isNight || Main.player.isInSpace) && new Random().nextInt(60) == 0) Star.deployRandomStars(Main.WIDTH, Main.HEIGHT);

		if(Main.world != null && Main.world.isDay && new Random().nextInt(110) == 0 && CLOUD_INT <= CLOUD_LIMIT)
		{
			Cloud cloud = new Cloud(new Random().nextInt(Main.world.worldW) * Tile.tileSize, ((Main.world.worldH / 2) - (new Random().nextInt(25) + 8)) * Tile.tileSize);
			getClouds().add(cloud);
			cloud.spawnEntity();
			CLOUD_INT++;
			cloudInt++;
		}
		
		if(cloudInt > CLOUD_LIMIT)
		{
			for(int i = 0; i < getClouds().size(); i++)
			{
				Cloud cloud = getClouds().get(i);
				if((cloud.getX() + cloud.getWidth() + 2) < Main.sX || (cloud.getX() + 2) > (Main.sX + Main.WIDTH))
				{
					getClouds().get(i).despawnEntity();
					getClouds().remove(i);
					CLOUD_INT--;
				}
			}
			cloudInt = 0;
		}
		
		if(Main.player != null && !Main.player.isInSpace)
		{
			if(Main.world.time >= 0 && Main.world.time <= 1)
			{
				if(r < r1) r++;
				if(g < g1) g++;
				if(b < b1) b++;
				if(darkness > darknessDay) darkness--;
				if(alpha > 0) alpha--;
			}
			if(Main.world.time > 1 && Main.world.time < 3.32)
			{
				r = r1;
				g = g1;
				b = b1;
				darkness = darknessDay;
				if(alpha > 0) alpha = 0;
			}
			if(Main.world.time > 3.32 && Main.world.time <= 4)
			{
				if(r > r2) r--;
				if(g > g2) g--;
				if(b > b2) b--;
				if(darkness < darknessNight) darkness++;
				if(alpha < 255) alpha++;
			}
			if(Main.world.time > 4 && Main.world.time < 6.5)
			{
				r = r2;
				g = g2;
				b = b2;
				darkness = darknessNight;
				if(alpha < 255) alpha = 255;
			}
		}
		else
		{
			if(r > r3) r--;
			if(b > b3) b--;
			if(g > g3) g--;
			darkness = darknessDay;
			alpha = 0;
		}
		for(Star s : getStars()) s.tick();
	}

	public void render()
	{
		for(int i = 0; i < sky.length; i++)
		{
			Render.setColor(rr[i], gg[i], 255);
			Render.fillRect(sky[i].x, sky[i].y, sky[i].width, sky[i].height);
		}
		Render.setColor(20, 20, 60, alpha);
		Render.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		Render.drawImage(sun.getImage(), Main.WIDTH/2 - sun.getImageWidth()/2 - (int)(Math.cos(Main.world.time)*(Main.WIDTH/2 - sun.getImageWidth()/2)), Main.HEIGHT/2 + sun.getImageWidth() + (int)(Math.sin(-Main.world.time)*(Main.HEIGHT/2 + sun.getImageWidth()/2)));
		Render.drawImage(moon.getImage(), Main.WIDTH/2 - moon.getImageWidth()/2 + (int)(Math.cos(-Main.world.time)*(Main.WIDTH/2 - moon.getImageWidth()/2)), Main.HEIGHT/2 + moon.getImageWidth() + (int)(Math.sin(Main.world.time)*(Main.HEIGHT/2 + moon.getImageWidth()/2)));
		for(Star s : getStars()) s.render();
	}

	public static synchronized List<Star> getStars()
	{
		return stars;
	}
	
	public static synchronized List<Cloud> getClouds()
	{
		return clouds;
	}
}
