package net.PixelThrive.Client.entities.projectiles;

import net.PixelThrive.Client.items.*;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.world.*;
import net.PixelThrive.Client.*;
import java.util.*;
import java.awt.Point;
import java.awt.image.BufferedImage;
import net.PixelThrive.Client.entities.*;

public class ProjectileArrow extends Projectile
{
	protected ItemArrow arrow;
	protected BufferedImage arrowIMG;

	public ProjectileArrow(int x, int y, int baseDMG, double dir, int id) 
	{
		super(x, y, dir, Tile.tileSize - 2, Tile.tileSize - 2, null);
		ItemArrow item = (ItemArrow)Item.getItem(id);
		arrow = item; //2 - 4
		damage = (new Random().nextInt(item.getMaxDamage() - item.getMinDamage()) + item.getMinDamage()) + baseDMG;
		speed = 4;
		nx = speed * Math.cos(dir);
		ny = speed * Math.sin(dir);
		arrowIMG = arrow.getTexture().getImageIcon();
	}	

	public void tick()
	{	
		x += nx;
		y += ny;

		isCollidingUp = (isCollidingWithBlock(new Point((int) (x + 2), (int) y), new Point((int) (x + width - 2), (int) y))) | noclip;
		isCollidingRight = (isCollidingWithBlock(new Point((int) (x + width), (int) y), new Point((int) (x + width), (int) (y + height - 2)))) | noclip;
		isCollidingLeft = (isCollidingWithBlock(new Point((int) x - 1, (int) y), new Point((int) x - 1, (int) (y + (height - 2))))) | noclip;
		isCollidingDown = (isCollidingWithBlock(new Point((int) x + 2, (int) (y + height)), new Point((int) (x + width - 2), (int) (y + height))));

		if(isCollidingDown || isCollidingRight || isCollidingLeft || isCollidingUp) drop();

		for(Entity e : Main.getEntities())
		{
			if(!(e instanceof Player) && e.canBeHurt() && isCollidingWithEntity(e) && !e.isInvincible())
			{
				e.hurt(damage, DeathCause.SHOT);
				drop();
			}
		}
	}

	public void render()
	{
		Render.drawImage(Render.rotate(arrowIMG, Math.toDegrees(angle) + 90), (int)x - (int)Main.sX, (int)y - (int)Main.sY);
	}
	
	public void renderOverWorld()
	{
	}
	
	public void drop()
	{
		if(!Main.player.isCreative) new Drop((int)x + 4, (int)y + 4, arrow.itemID, 0).spawnEntity();
		despawnEntity();
	}
}
