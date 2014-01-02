package net.PixelThrive.Client.entities.tileentities;

import java.awt.Color;
import java.awt.Rectangle;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.entities.Drop;
import net.PixelThrive.Client.items.CraftableStack;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.Tile;

public class Door extends TileEntity
{
	public Rectangle trigger = new Rectangle(), bounds = new Rectangle();
	public boolean isSelected = false, isOpen = false;
	private int keyDelay = 0, initialX, initialY, openedXRight, openedXLeft;
	private boolean isPlaced = false;

	public Door(int x, int y)
	{
		super(x, y, Tile.tileSize, Tile.tileSize * 2, Tile.wooden_door_closed, false);
	}

	public Door()
	{
		this(0, 0);
	}

	public void tick()
	{	
		obeysGravity = false;

		if(keyDelay > 0) keyDelay--;
		trigger.setBounds((int)x - (int)Main.sX, (int)y - (int)Main.sY, Tile.tileSize, Tile.tileSize * 2);
		bounds.setBounds((int)x / Tile.tileSize, (int)x / Tile.tileSize, 1, 2);
		super.tick();

		if(isPlaced)
		{
			y = initialY;
			if(trigger.contains(Main.mouseX, Main.mouseY)) isSelected = true;
			else isSelected = false;

			if(isOpen) id = Tile.wooden_door_open;
			else id = Tile.wooden_door_closed;

			if(isSelected && Main.key.isMouseRight && keyDelay <= 0)
			{
				keyDelay = 15;
				if(isOpen) isOpen = false;
				else isOpen = true;
				if(Main.player.getX() > x)
				{
					dir = -1;
					x = openedXLeft;
				}
				else
				{
					dir = 1;
					x = openedXRight;
				}
			}
			if(!isOpen) x = initialX;
		}

		if(isPlaced && isSelected && Main.key.isMouseLeft)
		{
			isOpen = false;
			new Drop(new CraftableStack(Block.woodenDoor, 0), initialX, initialY).spawnEntity();
			despawnEntity();
		}
		if(bounds.x == Main.player.getBoundingBox().x && bounds.y * Tile.tileSize - 5 == Main.player.getBoundingBox().y)
		{
			//>_> stupid stuff
		}
	}

	public void render()
	{
		if(dir > 0) Render.drawImage(SpriteSheet.TileEntity.getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY, (int) (x + width) - (int) Main.sX, (int) (y + height) - (int) Main.sY, /**/ (id[0] * Tile.tileSize) + (Tile.tileSize * animation), id[1] * Tile.tileSize, (id[0] * Tile.tileSize) + (Tile.tileSize * animation) + (int) textureWidth, id[1] * Tile.tileSize + (int)textureHeight);
		else Render.drawImage(SpriteSheet.TileEntity.getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY, (int) (x + width) - (int) Main.sX, (int) (y + height) - (int) Main.sY, /**/ (id[0] * Tile.tileSize) + (Tile.tileSize * animation) + (int) textureWidth, id[1] * Tile.tileSize, (id[0] * Tile.tileSize) + (Tile.tileSize * animation), id[1] * Tile.tileSize + (int)textureHeight);

		if(isSelected)
		{
			Render.setColor(new Color(200, 200, 200, 110));
			Render.drawRect((int)(x) - (int)Main.sX, (int)y - (int)Main.sY, Tile.tileSize - 1, Tile.tileSize * 2 - 1);
		}
	}

	public void placeTileEntity(int x, int y)
	{
		this.x = initialX = x;
		this.y = initialY = y - Tile.tileSize;
		openedXRight = initialX + 5;
		openedXLeft = initialX - 5;
		isOpen = false;
		keyDelay = 20;
		isPlaced = true;
	}
}
