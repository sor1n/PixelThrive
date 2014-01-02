package net.PixelThrive.Client.biomes;

import java.util.*;

import net.PixelThrive.Client.PixelException;
import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.world.World;
import net.PixelThrive.Client.world.WorldGeneration;

public class Biome 
{
	private static int[] IDs = new int[32000];
	private int size = 30, biomeID;
	private Block topLayer, secondLayer, background;
	public static List<Biome> biomes = new ArrayList<Biome>();
	private String name;
	private BiomeType type;
	
	public static final Biome forest = new ForestBiome(0, Block.dirt, Block.dirt, "Forest", BiomeType.MEDIUM);
	public static final Biome swamp = new SwampBiome(1, Block.dirt, Block.dirt, "Swamp", BiomeType.COLD);
	public static final Biome desert = new Biome(2, Block.sand, Block.sandStone, "Desert", BiomeType.DRY);
	public static final Biome glacier = new Biome(3, Block.snow, Block.ice, "Glacier", BiomeType.SNOWY);
	public static final Biome permaFrostGlacier = new Biome(4, Block.snow, Block.permaFrost, "Permafrosted Glacier", BiomeType.SNOWY);
	public static final Biome jungle = new JungleBiome(5, Block.dirt, Block.dirt, "Jungle", BiomeType.WARM);
//	public static final Biome ocean = new Biome(5, Block.water, Block.water, Block.stone, "Ocean").setTemperature(20D);
	public static final Biome mountains = new MountainBiome(6, Block.dirt, Block.dirt, "Mountain", BiomeType.MEDIUM);
	
	public Biome(int id, Block top, Block second, String name, BiomeType type)
	{
		this(id, top, second, second, name, type);
	}
	
	public Biome(int id, Block top, Block second, Block background, String name, BiomeType type)
	{
		if(id < IDs.length)
		{
			this.biomeID = id;
			this.topLayer = top;
			this.secondLayer = second;
			this.background = background;
			this.name = name;
			this.type = type;
			if(id >= 0) biomes.add(this);
		}
		else throw new PixelException("Ran out of Biome IDs!", PixelException.ExceptionType.NOTENOUGHIDS, null, null);
	}

	public int getBiomeSize()
	{
		return size;
	}

	public void setBiomeSize(int size)
	{
		this.size = size;
	}

	public int getBiomeID()
	{
		return biomeID;
	}

	public void setBiomeID(int biomeID)
	{
		this.biomeID = biomeID;
	}

	public Block getTopLayerBlock()
	{
		return topLayer;
	}

	public void setTopLayerBlock(Block topLayer)
	{
		this.topLayer = topLayer;
	}

	public Block getSecondLayerBlock()
	{
		return secondLayer;
	}
	
	public Block getBackgroundBlock()
	{
		return background;
	}

	public void setSecondLayerBlock(Block secondLayer)
	{
		this.secondLayer = secondLayer;
	}

	public String getName()
	{
		return name;
	}
	
	public void structures(WorldGeneration w, World world, int x, int y)
	{
		
	}
	
	public void landscape(WorldGeneration worldGen, World world, int x, int y)
	{
		for(int j = world.worldH / 2 - 4; j < world.worldH / 2 + 4; j++)
			try
		{
				if(world.seed.nextInt(5) == 0)
					worldGen.circleWithRandomAndBackground(x, j, 3, getTopLayerBlock().blockID, getSecondLayerBlock().blockID);
		}
		catch(Exception e){}

		for(int i = x; i<= x + getBiomeSize(); i++)
			for(int j = world.worldH/2; j < world.worldH/2 + 30; j++)
			{
				try
				{
					world.setBackgroundBlock(i, j, getBackgroundBlock().blockID);
					//Background sides
					if(world.seed.nextInt(75)== 0)worldGen.circleBackgroundWithRandom(x, j, 3, getSecondLayerBlock().blockID);
					if(world.seed.nextInt(75)== 0)worldGen.circleBackgroundWithRandom(x + Biome.biomes.get(0).getBiomeSize(), j, 3, getSecondLayerBlock().blockID);

					//Background placing between layers
					if(world.seed.nextInt(75)== 0)worldGen.circleBackgroundWithRandom(i, j, 2, getSecondLayerBlock().blockID);
					if(world.seed.nextInt(75)== 0)worldGen.circleBackgroundWithRandom(i, world.worldH/2 + 30, 2, getSecondLayerBlock().blockID);

					//Block line placement at bottom and top					
					if(world.seed.nextInt(75)== 0)worldGen.circleWithRandom(i, y + 14, 3, getSecondLayerBlock().blockID);
					if(world.seed.nextInt(75)== 0)worldGen.circleWithRandom(i, y + 4, 3, getTopLayerBlock().blockID);
				}
				catch(Exception e){}
			}
	}

	public BiomeType getType() {
		return type;
	}

	public void setType(BiomeType type) {
		this.type = type;
	}
}