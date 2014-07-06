package net.PixelThrive.Client.entities.tileentities;

import java.awt.*;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.entities.Entity;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.Tile;

public abstract class TileEntity extends Entity
{
	protected final boolean customBounds;
	protected boolean obeysGravity = true;
	protected double velocity, maxVelocity = 4.0;
	protected int distanceToPlayer = 0;
	protected int life = 0;

	public TileEntity(int x, int y, int width, int height, int[] id, boolean renderAfterPlayer)
	{
		super(x, y, width, height, id, renderAfterPlayer);
		health = maxHealth = 0;
		customBounds = false;
		fallingSpeed = 1.4;
		velocity = fallingSpeed;
		canBeHurt = false;
	}
	
	public TileEntity(int x, int y, int width, int height, int[] id, boolean renderAfterPlayer, boolean gravity)
	{
		super(x, y, width, height, id, renderAfterPlayer);
		health = maxHealth = 0;
		customBounds = false;
		fallingSpeed = 1.4;
		velocity = fallingSpeed;
		obeysGravity = gravity;
	}

	public TileEntity(int x, int y, int width, int height, int textureWidth, int textureHeight, int[] id, boolean renderAfterPlayer, boolean gravity)
	{
		super(x, y, width, height, textureWidth, textureHeight, id, renderAfterPlayer);
		health = maxHealth = 0;
		customBounds = true;
		fallingSpeed = 0.6;
		obeysGravity = gravity;
		velocity = fallingSpeed;
	}
	
	public TileEntity()
	{
		super(0, 0, 0, 0, null, false);
		customBounds = false;
	}

	public void tick()
	{
		life++;
		distanceToPlayer = (int)(x / Tile.tileSize) - (int)(Main.player.getX() / Tile.tileSize);
		if(distanceToPlayer < 0) distanceToPlayer = -distanceToPlayer;
		
		if(obeysGravity)
		{
			if(!isCollidingWithBlock(new Point((int) x + 2, (int) (y + height)), new Point((int) (x + width - 2), (int) (y + height))))
			{
				y += velocity;
				if(velocity < maxVelocity) velocity += 0.1;
				isFalling = true;
				isOnGround = false;
			}	
			else 
			{
				velocity = fallingSpeed;
				isFalling = false;
				isOnGround = true;
			}
		}
	}

	public void render()
	{
		if(!customBounds) Render.drawImage(SpriteSheet.TileEntity.getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY, (int) (x + width) - (int) Main.sX, (int) (y + height) - (int) Main.sY, /**/ (id[0] * Tile.tileSize) + (Tile.tileSize * 0), id[1] * Tile.tileSize, (id[0] * Tile.tileSize) + (Tile.tileSize * 0) + (int) textureWidth, id[1] * Tile.tileSize + (int)height);
		else Render.drawImage(SpriteSheet.TileEntity.getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY - (int)height, (int) (x + textureWidth) - (int) Main.sX, (int) (y + textureHeight - height) - (int) Main.sY, /**/ (id[0] * Tile.tileSize) + (Tile.tileSize * animation), id[1] * Tile.tileSize, (id[0] * Tile.tileSize) + (Tile.tileSize * animation) + (int) textureWidth, id[1] * Tile.tileSize + (int)textureHeight);
	}
	
	public void renderGUI() {}
	
	public void placeTileEntity(int x, int y)
	{
		this.x = x;
		this.y = y - (height / 2);
	}
}
