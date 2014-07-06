package net.PixelThrive.Client.blocks;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.GUI.GUI;
import net.PixelThrive.Client.entities.Drop;
import net.PixelThrive.Client.entities.FallingBlock;
import net.PixelThrive.Client.entities.tileentities.Chest;
import net.PixelThrive.Client.items.CraftableStack;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.Tile;
import net.PixelThrive.Client.world.World;

public class ChestBlock extends Block
{
	public int x, y;

	public ChestBlock(int id)
	{
		super(id, 2, SpriteSheet.TileEntity);
		setTexture(20);
	}	

	public void newChest(int id, int x, int y)
	{
		new Chest(id, x, y);
	}

	public void onRightClick(int x, int y)
	{
		if(Chest.getChest(x, y) != null)
		{
			if(Chest.getChest(x, y).isOpen) close(x, y);
			else open(x, y);
		}
	}

	public void blockTick(World world, int x, int y)
	{
		this.x = x;
		this.y = y;
		if(Chest.getChest(x, y) != null)
		{
			if(world.getBlock(x, y + 1) == Block.air && !Chest.getChest(x, y).isOpen)
			{
				new FallingBlock(x, y, blockID, Chest.getChest(x, y).contains).spawnEntity();
				Chest.getChest(x, y).setChestNull(x, y);
				world.setBlock(x, y, Block.air.blockID);
			}
			else if(Chest.getChest(x, y).isOpen && world.getBlock(x, y + 1) == Block.air) close(x, y);
		}
	}

	public void open(int x, int y)
	{
		if(!GUI.isGUIOpen())
		{
			Main.player.canMove = false;
			Chest.getChest(x, y).getRandomBlocks();
			Chest.getChest(x, y).isOpen = true;
			Main.getBlockGUIs().add(Chest.getChest(x, y));
			GUI.setCurrentGUI(Chest.getChest(x, y), Chest.getChest(x, y).chest[15].x + Tile.invCellSize + Tile.invCellSpace);
		}
	}

	public void onDestroyed(int x, int y)
	{
		close(this.x, this.y);
		disposeItems();
		super.onDestroyed(x, y);
	}

	public void disposeItems()
	{
		if(Chest.getChest(x, y) != null)
		{
			for(int i = 0; i < Chest.getChest(x, y).contains.length; i++)
			{
				CraftableStack stack = Chest.getChest(x, y).contains[i];
				if(stack != null && stack.getID() != 0) new Drop(x * Tile.tileSize, y * Tile.tileSize, stack.getID(), stack.getAmount()).spawnEntity();
			}
			Chest.getChest(x, y).setChestNull(x, y);
		}
	}

	public void close(int x, int y)
	{
		if(Chest.getChest(x, y) != null)
		{
			Main.player.canMove = true;
			Chest.getChest(x, y).isOpen = false;
			Main.getBlockGUIs().remove(Chest.getChest(x, y));
			GUI.clearCurrentGUI();
		}
	}

	public boolean isSelectable()
	{
		return false;
	}

	public void renderOverlay(int x, int y)
	{
		if(Chest.getChest(x, y) != null) Chest.getChest(x, y).render(x, y);
	}

	public static void setChestContains(int x, int y, int slot, CraftableStack stack)
	{
		Chest.getChest(x, y).chest[slot].id = stack.getID();
		Chest.getChest(x, y).chest[slot].itemAmount = stack.getAmount();
	}

	public int getUniqueChest()
	{
		for(int i = 0; i < Chest.chests.length; i++)
		{
			if(Chest.chests[i] == null) return i;
		}
		return -1;
	}

	public void renderDrop(int x, int y)
	{
		Render.drawImage(getTexture().getImageIcon().getScaledInstance(5, 5, 0), x, y);
	}

	public void onPlaced(World world, int x, int y)
	{
		newChest(getUniqueChest(), x, y);
		super.onPlaced(world, x, y);
	}
}
