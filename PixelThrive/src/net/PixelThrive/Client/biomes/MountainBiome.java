package net.PixelThrive.Client.biomes;

import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.world.World;
import net.PixelThrive.Client.world.WorldGeneration;

public class MountainBiome extends Biome
{
	public MountainBiome(int id, Block top, Block second, String name, BiomeType type)
	{
		super(id, top, second, name, type);
		setBiomeSize(30);
	}
		
	public void landscape(WorldGeneration worldGen, World world, int x, int y)
	{		
		for(int i = -getBiomeSize()/2; i<= getBiomeSize()/2; i++)
			for(int j = 0; j<= (int)Math.abs(i*i - (1 + world.rand.nextInt(5))*i + 4)/50; j++)
				world.setBlock(x + i + getBiomeSize()/2, world.worldH/2 - j, Block.dirt.blockID);
	}

}
