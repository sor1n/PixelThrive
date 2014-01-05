package net.PixelThrive.Client.items;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.audio.SoundSystem;
import net.PixelThrive.Client.materials.ToolMaterial;
import net.PixelThrive.Client.renders.PlayerAnimation;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;

public class ItemDrill extends ItemTool
{
	protected double dir;
	private int frame = 0, frameDelay = 0;
	private BufferedImage[] frames = new BufferedImage[3];
	private int soundDelay = 0;

	public ItemDrill(int id, ToolMaterial mat)
	{
		super(id, mat);
		setAnimation(new PlayerAnimation(new int[]{0}, 1, itemID, MouseEvent.BUTTON1));
	}

	public void tickInInventory()
	{
		if(soundDelay > 0) soundDelay--;
		if(frames[frame] == null) for(int i = 0; i < frames.length; i++) frames[i] = SpriteSheet.getIcon(getSpriteSheet(), (getTexture().x + i), getTexture().y);
		if(Main.key.isMouseLeft && Main.inv.invBar[Main.inv.selection].id == itemID)
		{
			double dx = Main.mouseX - Main.WIDTH / 2;
			double dy = Main.mouseY - Main.HEIGHT / 2;
			dir = Math.atan2(dy, dx);
			frameDelay++;
			if(frameDelay >= 5)
			{
				frameDelay = 0;
				frame++;
				if(frame > frames.length - 1) frame = 0;
			}
			if(soundDelay <= 0)
			{
				SoundSystem.playSound("drill", false);
				soundDelay = 8;
			}
		}
		else
		{
			frame = frameDelay = 0;
			if(SoundSystem.isSoundPlaying("drill")) SoundSystem.stopSound("drill");
		}
	}

	public void renderInPlayerHand(int x, int y, double angle, boolean flipped, boolean s)
	{
		if(Main.key.isMouseLeft)
		{
			if(!flipped) Render.drawImage(Render.rotate(Render.flipHorizontal(frames[frame]), Math.toDegrees(dir) + 180), x, y);
			else Render.drawImage(Render.rotate(frames[frame], Math.toDegrees(dir)), x, y);
		}
		else
		{
			if(!flipped) Render.drawImage(Render.flipHorizontal(texture.getImageIcon()), x - holdingOffsX, y);
			else Render.drawImage(texture.getImageIcon(), x + holdingOffsX, y);
		}
	}
}
