package net.PixelThrive.Client.renders;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.world.Tile;

public class PlayerAnimation
{
	private List<BufferedImage> frames = new ArrayList<BufferedImage>();
	public static List<PlayerAnimation> anims = new ArrayList<PlayerAnimation>();
	private int frameDelay, currentDelay = 0, currentFrame = 0;
	private int id, key;
	private boolean isMouse = false;
	public boolean usesLeftMouse = false, usesRightMouse = false;

	public static final PlayerAnimation DEFAULT = new PlayerAnimation(null, 0, 0, MouseEvent.BUTTON1);

	public PlayerAnimation(int[] list, int frameDelay, int id, int key)
	{
		this.frameDelay = frameDelay;
		this.id = id;
		if(key == MouseEvent.BUTTON1 || key == MouseEvent.BUTTON2 || key == MouseEvent.BUTTON3) isMouse = true;
		this.key = key;
		if(key == MouseEvent.BUTTON1) usesLeftMouse = true;
		if(key == MouseEvent.BUTTON3) usesRightMouse = true;
		if(list != null) for(int i = 0; i < list.length; i++) frames.add(SpriteSheet.getIcon(SpriteSheet.Entity, list[i], 0, Tile.tileSize, Tile.tileSize * 2));
		anims.add(this);
	}

	public void tick()
	{
		boolean mouseClicked = (key == MouseEvent.BUTTON1)? Main.key.isMouseLeft : (key == MouseEvent.BUTTON2)? Main.key.isMouseMiddle : Main.key.isMouseRight;
		if(isMouse && mouseClicked)
		{
			if(currentDelay < frameDelay) currentDelay++;
			else
			{
				currentDelay = 0;
				if(currentFrame < frames.size() - 1) currentFrame++;
				else currentFrame = 0;
			}
		}
		else currentDelay = currentFrame = 0;
	}

	public void render(int x, int y)
	{
		if(Main.player.getDir() > 0) Render.drawImage(frames.get(currentFrame), x, y);
		else Render.drawImage(frames.get(currentFrame), x + frames.get(currentFrame).getWidth(), y, (x + frames.get(currentFrame).getWidth()) - frames.get(currentFrame).getWidth(), y + frames.get(currentFrame).getHeight(), 0, 0, frames.get(currentFrame).getWidth(), frames.get(currentFrame).getHeight());
	}

	public int getID()
	{
		return id;
	}
	
	public int getCurrentFrame()
	{
		return currentFrame;
	}
	
	public void setCurrentFrame(int i)
	{
		currentFrame = i;
	}
}
