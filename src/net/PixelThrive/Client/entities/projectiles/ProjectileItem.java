package net.PixelThrive.Client.entities.projectiles;

import java.awt.Point;
import java.awt.image.BufferedImage;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.entities.Drop;
import net.PixelThrive.Client.items.Item;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.world.Tile;

public class ProjectileItem extends Projectile
{
	private BufferedImage icon;
	protected int slowDown = 0, bounceDelay = 0;
	private int id;
	protected double spin = 0;
	public boolean isBlock = false;

	public ProjectileItem(int x, int y, double dir, int id)
	{
		super(x, y, dir, Tile.tileSize, Tile.tileSize, null);
		if(id >= Block.IDs.length) icon = Item.items.get(id - Block.IDs.length).getTexture().getImageIcon();
		else
		{
			icon = Block.blocks.get(id).getTexture().getImageIcon();
			isBlock = true;
		}
		speed = 3;
		this.id = id;
		nx = speed * Math.cos(dir);
		ny = speed * Math.sin(dir);
	}	

	public void tick()
	{	
		if(bounceDelay <= 200) x += nx;
		if(bounceDelay <= 40) y += ny;
		bounceDelay++;
		isCollidingUp = (isCollidingWithBlock(new Point((int) (x + 2), (int) y), new Point((int) (x + width - 2), (int) y))) | noclip;
		isCollidingRight = (isCollidingWithBlock(new Point((int) (x + width), (int) y), new Point((int) (x + width), (int) (y + height - 2)))) | noclip;
		isCollidingLeft = (isCollidingWithBlock(new Point((int) x - 1, (int) y), new Point((int) x - 1, (int) (y + (height - 2))))) | noclip;
		isCollidingDown = (isCollidingWithBlock(new Point((int) x + 2, (int) (y + height)), new Point((int) (x + width - 2), (int) (y + height))));
		if(!isCollidingDown) y++;
		if(isCollidingDown && bounceDelay < 40) y-= (40 - bounceDelay)/10;
		if(bounceDelay <= 200 && (isCollidingLeft || isCollidingRight || isCollidingDown || isCollidingUp))
		{
			nx = -nx;
			if(isCollidingDown || isCollidingUp) ny = -ny;
			if(bounceDelay == 200 && angle != -1.5886515719344492) angle = 0;
			else if(bounceDelay == 200) angle = -1.5886515719344492;
		}
		else spin += 0.5;
		if(bounceDelay > 70)
		{
			new Drop((int)x, (int)y, id, 0).spawnEntity();
			despawnEntity();
		}
	}

	public void render()
	{
		if(!isBlock) Render.drawImage(Render.rotate(icon, Math.toDegrees(spin) + 90), (int)x - (int)Main.sX, (int)y - (int)Main.sY);
		else Render.drawImage(Render.rotate(icon, Math.toDegrees(spin) + 90).getScaledInstance(14, 14, 0), (int)x - (int)Main.sX, (int)y - (int)Main.sY);
	}
}
