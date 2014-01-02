package net.PixelThrive.Client.renders;

import java.util.*;

import net.PixelThrive.Client.entities.Entity;
import net.PixelThrive.Client.world.Tile;

import java.awt.image.*;

public class EntityRenders
{
	private int[] id;
	private List<BufferedImage> frames = new ArrayList<BufferedImage>();
	private int animation = 0, animationFrame = 0, maxAnim, maxFrames = 0;
	private Entity entity;
	private int width, height;

	/**
	 * @param entity
	 * @param id
	 * @param frames
	 * @param maxFrames
	 */
	public EntityRenders(Entity entity, int id, int[] frames, int maxFrames)
	{
		this.entity = entity;
		this.id = getSheetCoord(id);
		this.maxFrames = maxFrames;
		this.width = Tile.tileSize;
		this.height = Tile.tileSize;
		for(int i = 0; i < frames.length; i++)
		{
			int[] fr = getSheetCoord(frames[i]);
			this.frames.add(getEntityFrame(fr, Tile.tileSize, Tile.tileSize));
		}
		maxAnim = this.frames.size();
	}
	
	/**
	 * @param entity
	 * @param width
	 * @param height
	 * @param id
	 * @param frames
	 * @param maxFrames
	 */
	public EntityRenders(Entity entity, int width, int height, int id, int[] frames, int maxFrames)
	{
		this.entity = entity;
		this.id = getSheetCoord(id);
		this.maxFrames = maxFrames;
		this.width = width;
		this.height = height;
		for(int i = 0; i < frames.length; i++)
		{
			int[] fr = getSheetCoord(frames[i]);
			this.frames.add(getEntityFrame(fr, width, height));
		}
		maxAnim = this.frames.size();
	}

	public void tick()
	{
		if(animationFrame < maxFrames) animationFrame++;
		else
		{
			animationFrame = 0;
			if(animation < (maxAnim - 1)) animation++;
			else animation = 0;
		}
	}

	public BufferedImage getEntityIcon()
	{
		if(!entity.isMoving) return getEntityFrame(id, width, height);
		else return frames.get(animation);
	}

	public static int[] getSheetCoord(int i)
	{
		int y = i / 16;
		return new int[]{i - 16 * y, y};
	}

	public static BufferedImage getEntityFrame(int[] coords, int width, int height)
	{
		return SpriteSheet.getIcon(SpriteSheet.Entity, coords[0], coords[1], width, height);
	}
	
	public int getAnimation()
	{
		return animation;
	}
	
	public int[] getDefaultFrame()
	{
		return id;
	}
}
