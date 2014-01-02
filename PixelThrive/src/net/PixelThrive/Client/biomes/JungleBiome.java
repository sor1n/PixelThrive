package net.PixelThrive.Client.biomes;

import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.world.World;
import net.PixelThrive.Client.world.WorldGeneration;

public class JungleBiome extends Biome
{
	public JungleBiome(int id, Block top, Block second, String name, BiomeType type)
	{
		super(id, top, second, name, type);
	}

	public void structures(WorldGeneration w, World world, int x, int y)
	{
		try{
			if(world.getBlock(x, world.worldH / 2 + 1 - y) == Block.grass && world.seed.nextBoolean() && world.getBlock(x - 1, world.worldH / 2 - y) == Block.air) 
				w.tree(x, world.worldH/2 - y, 17 + world.seed.nextInt(5), 4, Block.log, Block.leaves);
		}
		catch(Exception e){}
	}

}
