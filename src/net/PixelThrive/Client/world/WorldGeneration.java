package net.PixelThrive.Client.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.PixelThrive.Client.biomes.Biome;
import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.blocks.ChestBlock;
import net.PixelThrive.Client.items.CraftableStack;
import net.PixelThrive.Client.items.Item;

public class WorldGeneration 
{	
	public World world;
	public static List<CraftableStack> dungeonLoot = new ArrayList<CraftableStack>();
	public static Random loot = new Random();
	
	public WorldGeneration(World world)
	{
		this.world = world;
		loot = world.seed;
	}
	
	static
	{
		dungeonLoot.add(new CraftableStack(Item.vibratingViolator, 0));
		dungeonLoot.add(new CraftableStack(Item.woodenArrow, 10));
		dungeonLoot.add(new CraftableStack(Item.woodenChest, 0));
		dungeonLoot.add(new CraftableStack(Item.ironSword, 0));
		dungeonLoot.add(new CraftableStack(Item.string, 2));
		dungeonLoot.add(new CraftableStack(Block.dacite, 5));
		dungeonLoot.add(new CraftableStack(Item.regenerationPotion, 1));
		dungeonLoot.add(new CraftableStack(Item.harmingPotion, 3));
		dungeonLoot.add(new CraftableStack(Item.ironIngot, 1));
		dungeonLoot.add(new CraftableStack(Item.stonePick, 0));
		dungeonLoot.add(new CraftableStack(Item.woodenPick, 0));
		dungeonLoot.add(new CraftableStack(Block.woodenTorch, 3));
		dungeonLoot.add(new CraftableStack(Item.miningHelmet, 0));
		dungeonLoot.add(new CraftableStack(Block.basalt, 2));
		dungeonLoot.add(new CraftableStack(Block.marble, 5));
		dungeonLoot.add(new CraftableStack(Block.ironOre, 1));
		dungeonLoot.add(new CraftableStack(Block.ironFence, 4));
		dungeonLoot.add(new CraftableStack(Item.coin, 5));
	}
	
	//Normal
	public void circle(int x, int y, int size, int tile)
	{
		int var9;
		int var10;
		int var11;
		for (var9 = x - size; var9 <= x + size; ++var9)
		{
			for (var10 = y - size; var10 <= y + size; ++var10)
			{
				var11 = var9 - x;
				int var12 = var10 - y;
				if (var11 * var11 + var12 * var12 <= size * size + 1)
				{
					try
					{
						world.setBlock(var9, var10, tile);
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
	}
	
	public void elipse(int x, int y, int sizeX, int sizeY, int tile)
	{
		int var9;
		int var10;
		int var11;
		for (var9 = x - sizeX; var9 <= x + sizeX; ++var9)
		{
			for (var10 = y - sizeY; var10 <= y + sizeY; ++var10)
			{
				var11 = var9 - x;
				int var12 = var10 - y;
				if (var11 * var11 + var12 * var12 <= sizeX * sizeY + 1)
				{
					try
					{
						world.setBlock(var9, var10, tile);
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
	}

	public void halfCircle(int x, int y, int size, int tile)
	{
		int var9;
		int var10;
		int var11;
		for (var9 = x - size; var9 <= x + size; ++var9)
		{
			for (var10 = y; var10 <= y + size; ++var10)
			{
				var11 = var9 - x;
				int var12 = var10 - y;
				if (var11 * var11 + var12 * var12 <= size * size + 1)
				{
					try
					{
						world.setBlock(var9, var10, tile);
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
	}

	public void halfElipse(int x, int y, int sizeX, int sizeY, int tile)
	{
		int var9;
		int var10;
		int var11;
		for (var9 = x - sizeX; var9 <= x + sizeX; ++var9)
		{
			for (var10 = y; var10 <= y + sizeY; ++var10)
			{
				var11 = var9 - x;
				int var12 = var10 - y;
				if (var11 * var11 + var12 * var12 <= sizeX * sizeY + 1)
				{
					try
					{
						world.setBlock(var9, var10, tile);
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
	}

	public void tree(int x, int y, int size, int leafSize, Block log, Block leaves)
	{
		for(int m = 0; m < size; m++)
			world.setBlock(x, y - m, log.blockID);		
		for(int m = size/2; m < size; m++){
			if(world.seed.nextInt(5) == 0){
				world.setBlock(x + 1, y - m, log.blockID);
				world.turnBlock(x + 1, y - m, true);
			}
			if(world.seed.nextInt(5) == 0){
				world.setBlock(x - 1, y - m, log.blockID);
				world.turnBlock(x - 1, y - m, true);
			}
		}		
		circle(x, y - size, leafSize, leaves.blockID);
	}

	public void circleWithRandom(int x, int y, int siye, int tile)
	{
		int var9;
		int var10;
		int var11;
		for (var9 = x - siye; var9 <= x + siye; ++var9)
		{
			for (var10 = y - siye; var10 <= y + siye; ++var10)
			{
				var11 = var9 - x;
				int var12 = var10 - y;
				if (var11 * var11 + var12 * var12 <= siye * siye + 1)
				{
					try
					{
							world.setBlock(var9, var10, tile);
							world.setBlock(var9 + world.seed.nextInt(2), var10 + world.seed.nextInt(2), tile);
							world.setBlock(var9 - world.seed.nextInt(2), var10 - world.seed.nextInt(2), tile);
							world.setBlock(var9 - world.seed.nextInt(2), var10 + world.seed.nextInt(2), tile);
							world.setBlock(var9 + world.seed.nextInt(2), var10 - world.seed.nextInt(2), tile);
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
	}
	
	public void lake(int x, int y, int sizeX, int sizeY, Block b)
	{
		halfElipse(x, y, sizeX + 1, sizeY + 1, Block.stone.blockID);
		halfElipse(x, y - 2, sizeX, sizeY, Block.air.blockID);
		halfElipse(x, y, sizeX, sizeY, b.blockID);	
	}

	public void ellipseWithRandom(int x, int y, int sizeX, int sizeY, int tile)
	{
		int var9;
		int var10;
		int var11;
		for (var9 = x - sizeX; var9 <= x + sizeX; ++var9)
		{
			for (var10 = y - sizeY; var10 <= y + sizeY; ++var10)
			{
				var11 = var9 - x;
				int var12 = var10 - y;
				if (var11 * var11 + var12 * var12 <= sizeX * sizeY + 1)
				{
					try
					{
							world.setBlock(var9, var10, tile);
							world.setBlock(var9 + world.seed.nextInt(2), var10 + world.seed.nextInt(2), tile);
							world.setBlock(var9 - world.seed.nextInt(2), var10 - world.seed.nextInt(2), tile);
							world.setBlock(var9 - world.seed.nextInt(2), var10 + world.seed.nextInt(2), tile);
							world.setBlock(var9 + world.seed.nextInt(2), var10 - world.seed.nextInt(2), tile);
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
	}
	
	public void circleWithRandomAndBackground(int x, int y, int siye, int tile, int bg)
	{
		int var9;
		int var10;
		int var11;
		for (var9 = x - siye; var9 <= x + siye; ++var9)
		{
			for (var10 = y - siye; var10 <= y + siye; ++var10)
			{
				var11 = var9 - x;
				int var12 = var10 - y;
				if (var11 * var11 + var12 * var12 <= siye * siye + 1)
				{
					try
					{
							world.setBlockWithBackground(var9, var10, tile, bg);
							world.setBlockWithBackground(var9 + world.seed.nextInt(2), var10 + world.seed.nextInt(2), tile, bg);
							world.setBlockWithBackground(var9 - world.seed.nextInt(2), var10 - world.seed.nextInt(2), tile, bg);
							world.setBlockWithBackground(var9 - world.seed.nextInt(2), var10 + world.seed.nextInt(2), tile, bg);
							world.setBlockWithBackground(var9 + world.seed.nextInt(2), var10 - world.seed.nextInt(2), tile, bg);
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
	}

	public void structures(int x, int y)
	{
		if(y > (world.worldH/2 + 20) && world.seed.nextInt(1000) == 0)
		{
			for(int i = 0; i <= 15; i++)
				for(int j = 0; j <= 7; j++)
					world.setBlockWithBackground(x + i, y + j, Block.stoneBricks.blockID);
			for(int i = 1; i <= 14; i++)
				for(int j = 1; j <= 6; j++)
					world.setBlock(x + i, y + j, Block.air.blockID);	
			for(int i = 1; i <= 14; i++)
				for(int j = 1; j <= 6; j++)
					if(world.seed.nextInt(30) == 0)
						world.setBlock(x + i, y + j, Block.cobweb.blockID);
			Block.chest.onPlaced(world, x + 5, y + 6);
			int m = 0;
			for(int i = 0; i < loot.nextInt(16); i++){
				m = loot.nextInt(dungeonLoot.size());
				if(dungeonLoot.get(m).getAmount() == 0) ChestBlock.setChestContains(x + 5, y + 6, loot.nextInt(16), dungeonLoot.get(m));
				else ChestBlock.setChestContains(x + 5, y + 6, loot.nextInt(16), new CraftableStack(dungeonLoot.get(m).getID(), dungeonLoot.get(m).getAmount() + loot.nextInt(dungeonLoot.get(m).getAmount())));
			}				
		}
	}

	public void generateBiomeSpecificStructure(int x, int y, Biome biome) 
	{
		for(int i = 0; i < Biome.biomes.size(); i++)
			if(biome == Biome.biomes.get(i)) Biome.biomes.get(i).structures(this, world, x, y);
	}

	public void strip(int y, int size, int randomness, int tile)
	{
		for(int x = 0; x < world.worldW; x++)
			for(int m = 0; m < size; m++)
			{
				try{
						world.setBlock(x, y - m, tile);
						if(world.seed.nextInt(5)==0)circleWithRandom(x, y - size, 1, tile);
						if(world.seed.nextInt(5)==0)circleWithRandom(x, y + 1, 1, tile);
				}catch(Exception e){}
			}
	}

	public void strip(int x, int y, int sizeX, int sizeY, int randomness, int block)
	{
		for(int n = 0; n < sizeX; n++)
			for(int m = 0; m < sizeY; m++)
			{
				try{
						world.setBlock(x + n - world.seed.nextInt(7), y + m, block);
						world.setBlock(x + n - world.seed.nextInt(7), y + sizeY - world.seed.nextInt(randomness), block);
						world.setBlock(x + n - world.seed.nextInt(7), y + 1 + world.seed.nextInt(randomness), block);
				}catch(Exception e){}
			}
	}

	public void squareEdge(int x, int y, int size, int tile)
	{
		for(int i=0; i<size; i++){
			world.setBlock(x + i, y, tile);
			world.setBlock(x, y-i, tile);
			world.setBlock(x + i, y - size, tile);
			world.setBlock(x + size, y-i, tile);
		}
	}

	public void rectangleEdge(int x, int y, int sizeX, int sizeY, int tile)
	{
		for(int i=x; i<sizeX; i++){
			world.setBlock(x + i, y, tile);
			world.setBlock(x + i, y - sizeY, tile);
		}
		for(int i=y; i<sizeY; i++){
			world.setBlock(x, y+i, tile);
			world.setBlock(x + sizeX, y+i, tile);
		}
	}
	
	//Background
	public void circleBackground(int x, int y, int size, int tile)
	{
		int var9;
		int var10;
		int var11;
		for (var9 = x - size; var9 <= x + size; ++var9)
		{
			for (var10 = y - size; var10 <= y + size; ++var10)
			{
				var11 = var9 - x;
				int var12 = var10 - y;
				if (var11 * var11 + var12 * var12 <= size * size + 1)
				{
					try
					{
						world.setBackgroundBlock(var9, var10, tile);
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
	}

	public void treeBackground(int x, int y, int size)
	{
		for(int m = 0; m < size; m++)
			world.setBackgroundBlock(x, y - m, Block.log.blockID);		
		for(int m = size/2; m < size; m++){
			if(world.seed.nextInt(5) == 0){
				world.setBackgroundBlock(x + 1, y - m, Block.log.blockID);
				world.turnBackgroundBlock(x + 1, y - m, true);
			}
			if(world.seed.nextInt(5) == 0){
				world.setBackgroundBlock(x - 1, y - m, Block.log.blockID);
				world.turnBackgroundBlock(x - 1, y - m, true);
			}
		}
		circle(x, y - size, 3, Block.leaves.blockID);
	}

	public void circleBackgroundWithRandom(int x, int y, int siye, int tile)
	{
		int var9;
		int var10;
		int var11;
		for (var9 = x - siye; var9 <= x + siye; ++var9)
		{
			for (var10 = y - siye; var10 <= y + siye; ++var10)
			{
				var11 = var9 - x;
				int var12 = var10 - y;
				if (var11 * var11 + var12 * var12 <= siye * siye + 1)
				{
					try
					{
							world.setBackgroundBlock(var9, var10, tile);
							world.setBackgroundBlock(var9 + world.seed.nextInt(2), var10 + world.seed.nextInt(2), tile);
							world.setBackgroundBlock(var9 - world.seed.nextInt(2), var10 - world.seed.nextInt(2), tile);
							world.setBackgroundBlock(var9 - world.seed.nextInt(2), var10 + world.seed.nextInt(2), tile);
							world.setBackgroundBlock(var9 + world.seed.nextInt(2), var10 - world.seed.nextInt(2), tile);
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
	}

	public void stripBackground(int y, int size, int randomness, int tile)
	{
		for(int x = 0; x < world.worldW; x++)
			for(int m = 0; m < size; m++)
			{
				try{
						world.setBackgroundBlock(x, y - m, tile);
						world.setBackgroundBlock(x, y - size - world.seed.nextInt(randomness), tile);
						world.setBackgroundBlock(x, y + 1 + world.seed.nextInt(randomness), tile);
				}catch(Exception e){}
			}
	}

	public void squareBackgroundEdge(int x, int y, int size, int tile)
	{
		for(int i=0; i<size; i++){
			try{
					world.setBackgroundBlock(x + i, y, tile);
					world.setBackgroundBlock(x, y-i, tile);
					world.setBackgroundBlock(x + i, y - size, tile);
					world.setBackgroundBlock(x + size, y-i, tile);
			}catch(ArrayIndexOutOfBoundsException e){}
		}
	}

	public void rectangleBackgroundEdge(int x, int y, int sizeX, int sizeY, int tile)
	{
		for(int i=x; i<sizeX; i++){
			try{
					world.setBackgroundBlock(x + i, y, tile);
					world.setBackgroundBlock(x + i, y - sizeY, tile);
			}catch(ArrayIndexOutOfBoundsException e){}
		}
		for(int i=y; i<sizeY; i++){
			try{
					world.setBackgroundBlock(x, y+i, tile);
					world.setBackgroundBlock(x + sizeX, y+i, tile);
			}catch(ArrayIndexOutOfBoundsException e){}
		}
	}
}
