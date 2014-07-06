package net.PixelThrive.Client.entities;

import java.awt.Point;

import net.PixelThrive.Client.DeathCause;
import net.PixelThrive.Client.LoadedImage;
import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.blocks.*;
import net.PixelThrive.Client.items.CraftableStack;
import net.PixelThrive.Client.renders.LightRender;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.Tile;
import net.PixelThrive.Client.GUI.*;

public class FallingBlock extends Entity
{
	private LoadedImage block;
	private int blockid;
	private double velocity = 0.1, maxVelocity = 4.0;
	private CraftableStack[] items;
	public int life = 0;
	private LightRender light;

	public FallingBlock(int x, int y, int blockID)
	{
		super(x * Tile.tileSize + 3, y * Tile.tileSize + 2, Tile.tileSize - 3, Tile.tileSize - 2, null, true);
		block = new LoadedImage(SpriteSheet.Terrain.getImage(), Block.getBlock(blockID).getSheetCoordinates()[0] * Tile.tileSize, Block.getBlock(blockID).getSheetCoordinates()[1] * Tile.tileSize, Tile.tileSize, Tile.tileSize);
		blockid = blockID;
	}	

	/**
	 * Chest FallingBlock
	 */
	public FallingBlock(int x, int y, int blockID, CraftableStack[] list)
	{
		super(x * Tile.tileSize + 2, y * Tile.tileSize + 2, Tile.tileSize - 2, Tile.tileSize - 2, null, true);
		block = new LoadedImage(SpriteSheet.Terrain.getImage(), Block.getBlock(blockID).getSheetCoordinates()[0] * Tile.tileSize, Block.getBlock(blockID).getSheetCoordinates()[1] * Tile.tileSize, Tile.tileSize, Tile.tileSize);
		blockid = blockID;
		items = new CraftableStack[16];
		items = list;
	}

	public FallingBlock()
	{
		this(0, 0, 0);
	}

	public void tick()
	{
		light = Main.world.light.getShadow((int)x / Tile.tileSize, (int)y / Tile.tileSize);

		life++;
		if(isCollidingWithPlayer()) Main.player.hurt(0.3F, DeathCause.GRAVITY);
		if(!isCollidingWithBlock(new Point((int) x + 2, (int) (y + height)), new Point((int) (x + width - 2), (int) (y + height))))
		{
			y += velocity;
			if(velocity < maxVelocity) velocity += 0.1;
			isFalling = true;
			isOnGround = false;
		}
		else
		{
			isFalling = false;
			isOnGround = true;
			velocity = 0.1;
		}

		if(isOnGround || !isFalling)
		{
			Main.world.setBlock((int)(x / Tile.tileSize), (int)(y / Tile.tileSize), blockid);
			if(blockid == Block.chest.blockID)
			{
				((ChestBlock)Main.world.getBlock((int)(x / Tile.tileSize), (int)(y / Tile.tileSize))).newChest(((ChestBlock)Main.world.getBlock((int)(x / Tile.tileSize), (int)(y / Tile.tileSize))).getUniqueChest(), (int)(x / Tile.tileSize), (int)(y / Tile.tileSize));
				if(items != null)
				{
					for(int i = 0; i < items.length; i++)
					{
						if(items[i] != null) ChestBlock.setChestContains((int)(x / Tile.tileSize), (int)(y / Tile.tileSize), i, items[i]);
					}
				}
			}
			despawnEntity();
		}
	}

	public void render()
	{
		Render.drawImage(block.getImage(), (int) x - (int) Main.sX - 3, (int) y - (int) Main.sY - 2);
		if(light != null)
		{
			if(light.getR() != -1 && light.getB() != -1 && light.getG() != -1) Render.setColor(light.getR(), light.getG(), light.getB(), light.getAlpha());
			else Render.setColor(light.getBrightness(), light.getBrightness(), light.getBrightness(), light.getAlpha());
		}
		Render.fillRect((int) x - (int) Main.sX - 3, (int) y - (int) Main.sY - 2, Tile.tileSize, Tile.tileSize);
		if(Options.BLOCK_BORDERS)
		{
			Render.setColor(0x000000);
			if(Main.world.getBlockID((int)(x / Tile.tileSize), (int)(y / Tile.tileSize) - 1) == 0) Render.drawLine((int)x - (int)Main.sX - 3, (int)y - (int)Main.sY - 3, (int)x - (int)Main.sX + (Tile.tileSize - 1) - 3, (int)y - (int)Main.sY - 3);
			if(Main.world.getBlockID((int)(x / Tile.tileSize) + 1, (int)(y / Tile.tileSize)) == 0) Render.drawLine((int)x - (int)Main.sX + (Tile.tileSize - 1) - 2, (int)y - (int)Main.sY - 2, (int)x - (int)Main.sX + (Tile.tileSize - 1) - 2, (int)y - (int)Main.sY + (Tile.tileSize - 1) - 2);
			if(Main.world.getBlockID((int)(x / Tile.tileSize) - 1, (int)(y / Tile.tileSize)) == 0) Render.drawLine((int)x - (int)Main.sX - 4, (int)y - (int)Main.sY - 2, (int)x - (int)Main.sX - 4, (int)y - (int)Main.sY + (Tile.tileSize - 1) - 2);
			if(Main.world.getBlockID((int)(x / Tile.tileSize), (int)(y / Tile.tileSize) + 1) == 0) Render.drawLine((int)x - (int)Main.sX - 3, (int)y - (int)Main.sY + (Tile.tileSize - 1) - 1, (int)x - (int)Main.sX + (Tile.tileSize - 1) - 3, (int)y - (int)Main.sY + (Tile.tileSize - 1) - 1);
		}
	}
}
