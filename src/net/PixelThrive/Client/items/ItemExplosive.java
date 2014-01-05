package net.PixelThrive.Client.items;

import net.PixelThrive.Client.*;
import net.PixelThrive.Client.world.*;
import net.PixelThrive.Client.entities.projectiles.*;

public class ItemExplosive extends Item
{
	protected float explosionSize;
	protected boolean explodeOnCollide; 
	protected double dir = 0;

	public ItemExplosive(int id, float exp, boolean expOnCollide)
	{
		super(id);
		explosionSize = exp;
		explodeOnCollide = expOnCollide;
		setCropInHand(false, 0);
		setDescription("Keep Away From Children. ", 0x3EAF01);
		addStat("Explosion Size: " + exp);
	}

	public void onItemRightClick()
	{
		double dx = Main.mouseX - Main.WIDTH / 2;
		double dy = Main.mouseY - Main.HEIGHT / 2;
		dir = Math.atan2(dy, dx);
		shoot((int)Main.player.getX(), (int)Main.player.getY(), dir, itemID);
		if(!Main.player.isCreative) decrementItem();
	}

	public void shoot(int x, int y, double dir, int item)
	{
		new ProjectileExplosive(x, y, dir, explosionSize, Tile.tileSize, Tile.tileSize, item).spawnEntity();
	}
}
