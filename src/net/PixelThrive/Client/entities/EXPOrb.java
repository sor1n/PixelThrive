package net.PixelThrive.Client.entities;

import java.awt.Color;
import java.awt.Point;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.GUI.Text;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.*;

public class EXPOrb extends Entity
{
	private int life = 0;
	private int expAmount;
	
	public EXPOrb(int x, int y, int expAmount)
	{
		super(x, y, Tile.tileSize, Tile.tileSize, Tile.tileSize, Tile.tileSize, new int[]{0, 11}, true);
		health = maxHealth = 0;
		this.expAmount = expAmount;
		fallingSpeed = 0.4;
		animation = 0;
	}
	
	public void tick()
	{
		isCollidingDown = (isCollidingWithBlock(new Point((int) x + 2, (int) (y + height)), new Point((int) (x + width - 2), (int) (y + height))));
		life++;
		if(!isCollidingDown) y += fallingSpeed;
		if(Main.player != null && isCollidingWithPlayer() && life >= 100)
		{
			Main.player.addExp(expAmount);
			despawnEntity();
		}
	}
	
	public void render()
	{
		Render.drawImage(SpriteSheet.Entity.getImage().getSubimage(0, 11 * Tile.tileSize, Tile.tileSize, Tile.tileSize), (int) x - (int) Main.sX, (int) y - (int) Main.sY);
		Text.drawStringWithShadow("EXP" + " [" + expAmount + "]", (int)x - (int)Main.sX, (int)y - (int)Main.sY - 22, Color.WHITE, 9, Main.gameFont);
	}
}
