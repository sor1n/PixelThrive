package net.PixelThrive.Client.biomes;

import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.world.World;
import net.PixelThrive.Client.world.WorldGeneration;

public class ForestBiome extends Biome
{
	public ForestBiome(int id, Block top, Block second, String name, BiomeType type)
	{
		super(id, top, second, name, type);
	}

	public void structures(WorldGeneration w, World world, int x, int y)
	{
		try{
			if(world.getBlock(x, world.worldH / 2 + 1 - y) == Block.grass && world.seed.nextInt(3) == 0 && world.getBlock(x - 1, world.worldH / 2 - y) == Block.air) 
				w.tree(x, world.worldH/2 - y, 5 + world.seed.nextInt(5), 2, Block.log, Block.leaves);
		}
		catch(Exception e){}

		try{
			if(world.seed.nextInt(500) == 0) w.lake(x, y + world.worldH/2, 3 + world.seed.nextInt(6), 3 + world.seed.nextInt(2), Block.water);
		}
		catch(Exception e){}

	}

}
