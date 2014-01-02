package net.PixelThrive.Client.world;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.PixelThrive.Client.Input.Key;
import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.PixelException;
import net.PixelThrive.Client.PixelException.ExceptionType;
import net.PixelThrive.Client.GUI.Options;
import net.PixelThrive.Client.biomes.Biome;
import net.PixelThrive.Client.biomes.BiomeBounds;
import net.PixelThrive.Client.blocks.*;
import net.PixelThrive.Client.blocks.BlockAndItem;
import net.PixelThrive.Client.buff.Buffs;
import net.PixelThrive.Client.entities.Drop;
import net.PixelThrive.Client.entities.Entity;
import net.PixelThrive.Client.entities.Player;
import net.PixelThrive.Client.entities.particles.BlockParticle;
import net.PixelThrive.Client.items.CraftableStack;
import net.PixelThrive.Client.items.Item;
import net.PixelThrive.Client.items.ItemFunctions;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.RenderBlock;

public class World 
{
	public int worldW, worldH = 600;
	public RenderBlock[][] block;
	public RenderBlock[][] bblock;
	private int posX;
	private int posY;
	public boolean restriction;
	public boolean isNight = false, isDay = true;
	public double resistance, bgResistance, bgQuantity, quantity;
	public Item[] toolNeeded, bgToolNeeded;
	public int power, bgPower, bgDrop, drop;
	public boolean isMining;
	public Random rand = new Random();
	public Random seed = new Random();
	public double time = 0, timeDelay = 0;
	public WorldGeneration worldGen;
	public Lighting light;
	public boolean hasGenerated = false;
	private int placeDelay = 0;
	public static int mobCount = 0;
	public static int mobCap = 20;
	public long displaySeed;
	public int camX, camY, renW, renH;
	private float[][] liquidQuantity;

	public List<BiomeBounds> biomes = new ArrayList<BiomeBounds>();
	private Biome previousBiome;

	public World(int size, boolean customSize, long sSeed)
	{
		seed.setSeed(sSeed);
		displaySeed = sSeed;
		if(customSize)
		{
			if(size > 1500) size = 1500;
			worldW = size;
		}
		else
		{
			if(size == 0) worldW = 400;
			else if(size == 1) worldW = 600;
			else if(size == 2) worldW = 1000;
		}
		block = new RenderBlock[worldW][worldH];
		bblock = new RenderBlock[worldW][worldH];
		for(int x = 0; x < block.length; x++)
		{
			for(int y = 0; y < block[0].length; y++)
			{
				block[x][y] = new RenderBlock(new Rectangle(x * Tile.tileSize, y * Tile.tileSize, Tile.tileSize, Tile.tileSize), Block.air.blockID);
				bblock[x][y] = new RenderBlock(new Rectangle(x * Tile.tileSize, y * Tile.tileSize, Tile.tileSize, Tile.tileSize), Block.air.blockID, true);
			}
		}
		light = new Lighting(this);
		worldGen = new WorldGeneration(this);
		generate();
	}

	public void building(int camX, int camY, int renW, int renH)
	{
		if(placeDelay > 0) placeDelay--;
		posX = ((Main.mse.x/Main.SCALE) / ((Main.WIDTH/2)/Tile.tileSize)) - 16;
		posY = ((Main.mse.y/Main.SCALE) / ((Main.HEIGHT/2)/Tile.tileSize)) - 16;
		if(Main.player != null && !Main.player.isCreative) restriction = (posY <= 11 && posY >= -9 && posX <= 5 && posX >= -4);
		else restriction = true;
		if(Main.key.isMouseLeft || Main.key.isMouseRight || Main.key.isMouseMiddle)
		{
			for(int x = (camX / Tile.tileSize); x < (camX / Tile.tileSize) + renW; x++)
			{
				for(int y = (camY / Tile.tileSize); y < (camY / Tile.tileSize) + renH; y++)
				{
					if(x >= 0 && y >= 0 && x < worldW && y < worldH)
					{
						if(block[x][y].contains(new Point((Main.mse.x / Main.SCALE) + (int) Main.sX, (Main.mse.y / Main.SCALE) + (int) Main.sY)) && restriction)
						{
							int sid = Main.inv.invBar[Main.inv.selection].id;				
							try
							{
								if(Main.player != null && Main.player.isCreative) resistance = 0;
								else resistance = Block.blocks.get(getBlockID(x, y)).getResistance();
								if(getBlock(x, y) instanceof LiquidBlock) liquidQuantity[x][y] = Block.blocks.get(getBlockID(x, y)).getLiquidQuantity(x, y);
								quantity = Block.blocks.get(getBlockID(x, y)).getQuantityDropped();
								drop = Block.blocks.get(getBlockID(x, y)).getDrop();
								toolNeeded = Block.blocks.get(getBlockID(x, y)).getTool();
								power = Block.blocks.get(getBlockID(x, y)).getToolPower();
								if(Main.player != null && Main.player.isCreative) bgResistance = 0;
								else bgResistance = Block.blocks.get(getBackgroundBlockID(x, y)).getResistance();
								bgQuantity = Block.blocks.get(getBackgroundBlockID(x, y)).getQuantityDropped();
								bgDrop = Block.blocks.get(getBackgroundBlockID(x, y)).getDrop();
								bgToolNeeded = Block.blocks.get(getBackgroundBlockID(x, y)).getTool();
								bgPower = Block.blocks.get(getBackgroundBlockID(x, y)).getToolPower();
							}
							catch(Exception e){}
							if(!Main.inv.buttons.isHelpMode)
							{
								if(Main.key.isMouseLeft && (resistance != -1 || bgResistance != -1) && Main.player.canMove)
								{
									if(!Key.backGroundKey.isPressed())
									{
										if(block[x][y].id != Block.air.blockID)
										{
											block[x][y].destroy();
											isMining = true;
											if(new Random().nextInt(7) == 0) new BlockParticle((x * Tile.tileSize) + new Random().nextInt(Tile.tileSize), (y * Tile.tileSize) + new Random().nextInt(Tile.tileSize), block[x][y].id).spawn();
										}
										else isMining = false;
									}
									else
									{
										if(block[x][y].id == 0 && bblock[x][y].id != 0)
										{
											bblock[x][y].bgDestroy();
											isMining = true;
											if(new Random().nextInt(7) == 0) new BlockParticle((x * Tile.tileSize) + new Random().nextInt(Tile.tileSize), (y * Tile.tileSize) + new Random().nextInt(Tile.tileSize), bblock[x][y].id).spawn();
										}
										else isMining = false;
									}
								}
								else if(Main.key.isMouseRight && placeDelay <= 0 && sid < Block.IDs.length)
								{
									if(Main.mouseX < Main.inv.invBar[0].x || Main.mouseY < Main.inv.invBar[0].y)
									{
										placeDelay = 15;
										if(!Key.backGroundKey.isPressed())
										{
											if(x >= 0 && x < worldW && y >= 0 && y < worldH && block[x][y].id == Block.air.blockID && ((block[x-1][y].id != Block.air.blockID || block[x+1][y].id != Block.air.blockID || block[x][y-1].id != Block.air.blockID || block[x][y+1].id != Block.air.blockID) || bblock[x][y].id != Block.air.blockID))
											{
												if(sid != Block.air.blockID)
												{
													if(Key.turnKey.isPressed()) block[x][y].isTurned = true;
													else block[x][y].isTurned = false;
													Block.getBlock(sid).onPlaced(this, x, y);
												}
											}
										}
										else
										{
											if(block[x][y].id == Block.air.blockID && ((bblock[x-1][y].id != Block.air.blockID || bblock[x+1][y].id != Block.air.blockID || bblock[x][y-1].id != Block.air.blockID || bblock[x][y+1].id != Block.air.blockID || block[x-1][y].id != Block.air.blockID || block[x+1][y].id != Block.air.blockID || block[x][y-1].id != Block.air.blockID || block[x][y+1].id != Block.air.blockID) && bblock[x][y].id == Block.air.blockID))
											{
												if(sid != Block.air.blockID)
												{
													if(Key.turnKey.isPressed()) bblock[x][y].isTurned = true;
													else bblock[x][y].isTurned = false;
													Block.getBlock(sid).onBackgroundPlaced(this, x, y);
												}
											}
										}
									}
								}
								else if(Main.key.isMouseMiddle && placeDelay <= 0 && Main.player.isCreative)
								{
									if(!Key.backGroundKey.isPressed())
									{
										if(Main.mouseX < Main.inv.invBar[0].x || Main.mouseY < Main.inv.invBar[0].y)
										{
											placeDelay = 15;
											if(Block.blocks.get(block[x][y].id).middleMouse() && block[x][y].id != Block.air.blockID) Main.inv.giveItem(new CraftableStack(block[x][y].id, 1));
										}
									}
									else
									{
										if(Main.mouseX < Main.inv.invBar[0].x || Main.mouseY < Main.inv.invBar[0].y)
										{
											placeDelay = 15;
											if(Block.blocks.get(bblock[x][y].id).middleMouse() && bblock[x][y].id != Block.air.blockID) Main.inv.giveItem(new CraftableStack(bblock[x][y].id, 1));
										}
									}
								}
								else isMining = false;
								break;
							}
							else if(Main.key.isMouseRight)
							{
								if(block[x][y].id != 0 && !Key.backGroundKey.isPressed()) Main.inv.buttons.newHelp(block[x][y].getBlock().getName(), block[x][y].getBlock().getHelpDescription());
								if(bblock[x][y].id != 0 && block[x][y].id == 0 && Key.backGroundKey.isPressed()) Main.inv.buttons.newHelp(bblock[x][y].getBlock().getName(), bblock[x][y].getBlock().getHelpDescription());
							}
						}
					}
				}
			}
		}
		else isMining = false;
		if(!Main.key.isMouseLeft) isMining = false;	
	}

	public void generateBiome(int x, int y, Biome biome)
	{
		Block b1 = biome.getTopLayerBlock(), b2 = biome.getSecondLayerBlock();
		worldGen.strip(x, y,biome.getBiomeSize(), 3, 3, b1.blockID);
		worldGen.strip(x, y + 4, biome.getBiomeSize(), 10, 3, b2.blockID);
		biome.landscape(worldGen, this, x, y);
		biomes.add(new BiomeBounds(x, biome.getBiomeID(), this));
		previousBiome = biome;
	}

	public void onScreenTick(int camX, int camY, int renW, int renH)
	{
		light.tick(camX, camY, renW, renH);
		this.camX = camX;
		this.camY = camY;
		this.renW = renW;
		this.renH = renH;
		if(Main.inv != null && BlockAndItem.getItemOrBlock(Main.inv.invBar[Main.inv.selection].id).getCurrentFunction() != ItemFunctions.THROW && !Main.inv.isOpen && Main.player != null && Main.player.healthBar != null && !Main.player.healthBar.isDead && ((Key.itemFuncButton.isPressed() && Main.inv.invBar[Main.inv.selection].id == 0) || !Key.itemFuncButton.isPressed()) && (Main.mouseX < Main.inv.invBar[0].x || Main.mouseY < Main.inv.invBar[0].y) && !Main.skl.isOpen && !Main.shop.isOpen  && !Main.com.isOpen && Main.inv.buttons != null && Main.inv.buttons.pause != null && (Main.mouseX > (Main.inv.buttons.pause.x + Main.inv.buttons.pause.width) || (Main.mouseY > (Main.inv.buttons.pause.y + Main.inv.buttons.pause.height)))) building(camX, camY, renW, renH);
		for(int x = (camX / Tile.tileSize); x < (camX / Tile.tileSize) + renW; x++)
		{
			for(int y = (camY / Tile.tileSize); y < (camY / Tile.tileSize) + renH; y++)
			{
				if(x >= 0 && y >= 0 && x < worldW && y < worldH)
				{	
					int i = -1;
					try
					{
						if(!isNight)
						{		
							light.setShadow(0, y, 235, this);
							light.setShadow(worldW - 1, y, 235, this);
							if(getBlock(x, y).isTransparent() && getBackgroundBlock(x, y).isTransparent()) light.setShadow(x, y, 0, this);
							else
							{
								i = 7 + light.getShadow(x, y - 1).getAlpha()/3 + light.getShadow(x - 1, y).getAlpha()/3 + light.getShadow(x + 1, y).getAlpha()/3;		
								if(i > 235) i = 235;					
								light.setShadow(x, y, i, this);
							}
						}
						else light.setShadow(x, y, 235, this);
//						if(time >= 3.3065 && time <= 3.33) light.setShadow(x, y, (int)((time - 3.3065)*10000), this);
						getBlock(x, y).blockTick(this, x, y);
					}
					catch(ArrayIndexOutOfBoundsException e) {}
				}
			}
		}
		worldGen.rectangleEdge(0, 0, worldW - 1, worldH - 1, Block.bedrock.blockID);
		worldGen.strip(worldH - 1, 1, 3, Block.bedrock.blockID);
		int m = rand.nextInt(Entity.livingEntityList.length);
		Entity e = null;
		try 
		{
			e = (Entity) Entity.livingEntityList[m].newInstance();
		}
		catch(Exception ex)
		{
			throw new PixelException(ex.getMessage(), ExceptionType.ENTITYCORRUPT, null, null);
		}
		if(!e.spawnsOnScreen())
		{
			if(mobCount < mobCap)
			{
				for(int x = (camX / Tile.tileSize); x > (camX / Tile.tileSize) - renW; x--)
				{
					for(int y = (camY / Tile.tileSize); y > (camY / Tile.tileSize) - renH; y--)
					{
						if(x >= 0 && y >= 0 && x < worldW && y < worldH)
						{
							if(((getBlock(x, y + 1) == e.spawnBlock() && e.spawnBlock() != null) || e.spawnsOnAllBlocks()) && getBlock(x, y + 1) != Block.air && rand.nextInt(1000) == 0 && getBlockID(x, y) == 0 && light.getShadow(x, y).getAlpha() >= e.lightLevelSpawn()[0] && light.getShadow(x, y).getAlpha() <= e.lightLevelSpawn()[1])
							{
								if(isNight == e.spawnsAtNight() && !e.spawnsAnytime())
								{
									e.setPosition(x*Tile.tileSize, y*Tile.tileSize);
									e.spawnEntity();
								}
								if(e.spawnsAnytime())
								{
									e.setPosition(x*Tile.tileSize, y*Tile.tileSize);
									e.spawnEntity();
								}
							}
						}
					}
				}
				for(int x = (camX / Tile.tileSize) + renW; x < (camX / Tile.tileSize) + renW*2; x++)
				{
					for(int y = (camY / Tile.tileSize) + renH; y < (camY / Tile.tileSize) + renH*2; y++)
					{
						if(x >= 0 && y >= 0 && x < worldW && y < worldH)
						{
							if(((getBlock(x, y + 1) == e.spawnBlock() && e.spawnBlock() != null) || e.spawnsOnAllBlocks()) && getBlock(x, y + 1) != Block.air && rand.nextInt(1000) == 0 && getBlockID(x, y) == 0 && light.getShadow(x, y).getAlpha() >= e.lightLevelSpawn()[0] && light.getShadow(x, y).getAlpha() <= e.lightLevelSpawn()[1])
							{
								if(isNight == e.spawnsAtNight() && !e.spawnsAnytime())
								{
									e.setPosition(x*Tile.tileSize, y*Tile.tileSize);
									e.spawnEntity();
								}
								if(e.spawnsAnytime() && e.spawnsOnScreen())
								{
									e.setPosition(x*Tile.tileSize, y*Tile.tileSize);
									e.spawnEntity();
								}
							}
						}
					}
				}
			}
		}
		else{

			if(mobCount < mobCap)
			{
				for(int x = (camX / Tile.tileSize); x < (camX / Tile.tileSize) + renW/2; x++)
				{
					for(int y = (camY / Tile.tileSize); y < (camY / Tile.tileSize) + renH/2; y++)
					{
						if(x >= 0 && y >= 0 && x < worldW && y < worldH)
						{
							if(((getBlock(x, y + 1) == e.spawnBlock() && e.spawnBlock() != null) || e.spawnsOnAllBlocks()) && getBlock(x, y + 1) != Block.air && rand.nextInt(1000) == 0 && getBlockID(x, y) == 0 && light.getShadow(x, y).getAlpha() >= e.lightLevelSpawn()[0] && light.getShadow(x, y).getAlpha() <= e.lightLevelSpawn()[1])
							{
								if(isNight == e.spawnsAtNight() && !e.spawnsAnytime())
								{
									e.setPosition(x*Tile.tileSize, y*Tile.tileSize);
									e.spawnEntity();
								}
								if(e.spawnsAnytime())
								{
									e.setPosition(x*Tile.tileSize, y*Tile.tileSize);
									e.spawnEntity();
								}
							}
						}
					}
				}
				for(int x = (camX / Tile.tileSize) + renW/2; x < (camX / Tile.tileSize) + renW; x++)
				{
					for(int y = (camY / Tile.tileSize) + renH/2; y < (camY / Tile.tileSize) + renH; y++)
					{
						if(x >= 0 && y >= 0 && x < worldW && y < worldH)
						{
							if(((getBlock(x, y + 1) == e.spawnBlock() && e.spawnBlock() != null) || e.spawnsOnAllBlocks()) && getBlock(x, y + 1) != Block.air && rand.nextInt(1000) == 0 && getBlockID(x, y) == 0 && light.getShadow(x, y).getAlpha() >= e.lightLevelSpawn()[0] && light.getShadow(x, y).getAlpha() <= e.lightLevelSpawn()[1])
							{
								if(isNight == e.spawnsAtNight() && !e.spawnsAnytime())
								{
									e.setPosition(x*Tile.tileSize, y*Tile.tileSize);
									e.spawnEntity();
								}
								if(e.spawnsAnytime() && e.spawnsOnScreen())
								{
									e.setPosition(x*Tile.tileSize, y*Tile.tileSize);
									e.spawnEntity();
								}
							}
						}
					}
				}
			}
		}

		//Time
		time += 0.0001;
		if(time >= 0 && time <= 3.32)
		{
			isNight = false;
			isDay = true;
		}
		if(time > 3.32 && time <= 6.5)
		{
			isNight = true;
			isDay = false;
		}
		if(time >= 6.5) time = 0;
	}

	public void worldBlockTick(int x, int y)
	{
		for(int i = 0; i < x; i++)
			for(int j = 0; j < y; j++)
				getBlock(i, j).blockTick(this, i, j);
	}

	public boolean canBlockSeeSky(int x, int y)
	{
		int block = 0;
		for(int m = y - 1; m > 1; m--)
		{
			if(getBlock(x, m) == Block.air) block++;
		}
		if(block >= y - 2) return true;
		return false;
	}

	public int getMobcap() 
	{
		return mobCap;
	}

	public void setMobcap(int m) 
	{
		mobCap = m;
	}

	public int getBlockDistanceToSky(int x, int y)
	{
		int i = 0;
		for(int m = y; m > 0; m--)
		{
			if(canBlockSeeSky(x, m)) return i;
			else i++;
		}
		return -1;
	}

	public void generate()
	{
		//Background
		Main.load.load(0, "Setting The Background...", false);
		for(int x = 0; x < worldW; x++)
			for(int y = worldH/2 + 30; y < worldH; y++)
			{
				Main.load.load((int)(x / 10), "Setting The Background...", false);
				try{setBackgroundBlock(x, y, Block.dirt.blockID);}
				catch(Exception e){}
			}

		//Blocks
		for(int x = 1; x < worldW - 1; x++)
			for(int y = 1; y < worldH / 2; y++)
			{
				Main.load.load((int)(x / 10), "Spawning Oxygen...", false);
				setBlock(x, y, Block.air.blockID);
			}

		for(int x = 0; x < worldW; x++)
			for(int y = worldH / 2 + 24; y < worldH; y++)
			{
				Main.load.load((int)(x / 10), "Creating Stonehenge...", false);
				setBlock(x, y, Block.stone.blockID);
			}

		Main.load.load(2, "Going To Hell...", false);
		worldGen.strip(worldH / 2 + 20, 4, 5, Block.stone.blockID);
		worldGen.strip(worldH - 1, 3, 4, Block.lavaStone.blockID);

		for(int y = worldH / 2 + 30; y < worldH; y++)
			for(int x = 0; x < worldW; x++)
				if(seed.nextInt(100) == 0)
				{
					Main.load.load((int)(x / 10), "Gritting The Grit...", false);
					if(getBlock(x, y) == Block.stone) worldGen.circleWithRandom(x, y, 1 + seed.nextInt(2), Block.grit.blockID);
				}

		for(int y = worldH / 2 + 30; y < worldH; y++)
			for(int x = 0; x < worldW; x++)
				if(seed.nextInt(100) == 0)
				{
					Main.load.load((int)(x / 10), "Getting Dirty...", false);
					if(getBlock(x, y) == Block.stone) worldGen.circleWithRandom(x, y, seed.nextInt(2), Block.dirt.blockID);
				}
		//Biomes
		for(int x = 0; x <= worldW; x += Biome.biomes.get(0).getBiomeSize())
			try
		{
				Main.load.load((int)(x / 4), "Creating biomes...", false);
				int m = seed.nextInt(Biome.biomes.size());
				if(previousBiome != null) 
					while(Biome.biomes.get(m).getType().getID() > previousBiome.getType().getID() + 1 || Biome.biomes.get(m).getType().getID() < previousBiome.getType().getID() - 1)
						m = seed.nextInt(Biome.biomes.size());
				generateBiome(x, worldH / 2, Biome.biomes.get(m));
				for(int y = worldH / 2; y < worldH / 2 + 5; y++)
					try
				{
						worldGen.circleBackgroundWithRandom(x, y, 2, Biome.biomes.get(m).getSecondLayerBlock().blockID);
				}
				catch(Exception e){}
		}
		catch(Exception e){}

		for(int x = 0; x < worldW; x++)
			for(int y = worldH/2 + 2; y < worldH; y++)
				try
		{
					Main.load.load((int)(x / 10), "Running Out Of Air...", false);
					if(seed.nextInt(200) == 0)
						worldGen.circleWithRandom(x, y, 3, Block.air.blockID);
		}
		catch(Exception e){}

		for(int x = 0; x < worldW; x++)
			for(int y = worldH/2 + 2; y < worldH; y++)
				try
		{
					Main.load.load((int)(x / 10), "Running Out Of Imaginary Air...", false);
					if(seed.nextInt(75) == 0)
						worldGen.circleBackgroundWithRandom(x, y, 4, Block.air.blockID);
		}
		catch(Exception e){}

		for(int x = 0; x < worldW; x++)
		{
			for(int y = 0; y < worldH; y++)
			{
				try
				{
					Main.load.load((int)((x % worldW) / 10), "Asking Farmers To Plant Grass...", false);
					if(getBlock(x, y) == Block.air && getBlock(x, y-1) != Block.air && getBlock(x + 1, y) != Block.air && getBlock(x-1, y) != Block.air && getBlock(x, y+1) != Block.air)
						setBlock(x, y, getBlock(x, y + 1).blockID);
					for(int m = 1; m < (worldH - y); m++)
						if(getBlock(x, y) == Block.dirt && canBlockSeeSky(x, y)) setBlock(x, y, Block.grass.blockID);
				}
				catch(ArrayIndexOutOfBoundsException e) {}
			}
		}

		for(int x = 0; x < worldW; x++)
			for(int y = 0; y < worldH; y++)
				try
				{
					Main.load.load(x / 4, "Generating Structures...", false);
					worldGen.structures(x, y);
				}
				catch(Exception e){}

		for(int x = 0; x < worldW; x++)
			for(int y = 0; y < worldH; y++)
				try
		{
					Main.load.load(x / 4, "Placing Ores...", false);
					int m = seed.nextInt(Block.ores.size());
					if(seed.nextInt(Block.ores.get(m).getRarity()) == 0) 
						for(int i = 0; i < Block.ores.get(m).getMaxVeinSize(); i++)
							for(int j = 0; j < Block.ores.get(m).getMaxVeinSize(); j++)
								if(getBlock(x + i, y + j) == Block.stone && seed.nextInt(2) == 0){
									this.setBlock(x + i, y + j, Block.ores.get(m).blockID);
									if(seed.nextInt(5) == 0){
										this.setBlock(x + i + seed.nextInt(2), y + j + seed.nextInt(2), Block.ores.get(m).blockID);
										this.setBlock(x - seed.nextInt(2), y - seed.nextInt(2), Block.ores.get(m).blockID);
									}
								}
		}
		catch(Exception e){}
		
		for(int i = 1; i <= 50; i++)
		{
			Main.load.load(i*2, "Tick-Tok-Tick-Tok...", false);
			worldBlockTick(worldW, worldH);
		}

		for(int x = 0; x < worldW; x++)
			for(int y = 0; y < worldH; y++)
				try
		{
					Main.load.load((int)(x / 4), "Builders are making biome structures...", false);
					worldGen.generateBiomeSpecificStructure(x, y, getBiomeByX(x));
		}
		catch(Exception e){}
		
		for(int x = 0; x < worldW; x++)
			for(int y = 0; y < worldH; y++)
			{
				int i = -1;
				Main.load.load(x / 4, "Creating Shadows...", false);
				light.setShadow(0, y, 205, this);
				light.setShadow(worldW - 1, y, 205, this);
				if(getBlock(x, y).isTransparent() && getBackgroundBlock(x, y).isTransparent()) light.setShadow(x, y, 0, this);
				else
				{
					i = 10 + light.getShadow(x, y - 1).getAlpha();		
					if(i > 235) i = 235;					
					light.setShadow(x, y, i, this);
				}
			}
		hasGenerated = true;		
		Main.load.load(100, "Done!", true);
	}

	public void setBlock(int x, int y, int tile)
	{
		if(x >= 0 && x < worldW && y >= 0 && y < worldH) block[x][y].id = tile;
	}

	public void destroyBlock(int x, int y)
	{
		if(x >= 0 && x < worldW && y >= 0 && y < worldH) block[x][y].id = 0;
		new Drop((int)x*Tile.tileSize + 4, (int)y*Tile.tileSize + 4, getBlock(x, y).getDrop(), getBlock(x, y).getQuantityDropped()).spawnEntity();
		new Drop((int)x*Tile.tileSize + 4, (int)y*Tile.tileSize + 4, getBackgroundBlock(x, y).getDrop(), getBackgroundBlock(x, y).getQuantityDropped()).spawnEntity();
	}

	public void explosionBlockDestroy(int x, int y, int r, float power)
	{
		if(x >= 0 && x < worldW && y >= 0 && y < worldH)
		{
			if(rand.nextInt(r) == 0)
			{
				if(power >= getBlock(x, y).getResistance() && getBlock(x, y) != Block.chest && !getBlock(x, y).isUnbreakable()) new Drop((int)x*Tile.tileSize + 4, (int)y*Tile.tileSize + 4, getBlock(x, y).getDrop(), getBlock(x, y).getQuantityDropped()).spawnEntity();
				if(power >= getBackgroundBlock(x, y).getResistance() && !getBackgroundBlock(x, y).isUnbreakable()) new Drop((int)x*Tile.tileSize + 4, (int)y*Tile.tileSize + 4, getBackgroundBlock(x, y).getDrop(), getBackgroundBlock(x, y).getQuantityDropped()).spawnEntity();
			}
			if(power >= getBlock(x, y).getResistance() && getBlock(x, y) != Block.chest && !getBlock(x, y).isUnbreakable()) setBlock(x, y, 0);
			else Block.chest.onDestroyed(x, y);
			if(power >= getBackgroundBlock(x, y).getResistance() && !getBackgroundBlock(x, y).isUnbreakable()) setBackgroundBlock(x, y, 0);
		}
	}

	public void turnBlock(int x, int y, boolean turn)
	{
		if(Block.blocks.get(block[x][y].id).isTurnable()) block[x][y].isTurned = turn;
	}

	public void turnBackgroundBlock(int x, int y, boolean turn)
	{
		if(Block.blocks.get(block[x][y].id).isTurnable()) bblock[x][y].isTurned = turn;
	}

	public void setBlockWithBackground(int x, int y, int tile)
	{
		setBlock(x, y, tile);
		setBackgroundBlock(x, y, tile);
	}

	public void setBlockWithBackground(int x, int y, int tile, int btile)
	{
		setBlock(x, y, tile);
		setBackgroundBlock(x, y, btile);
	}

	public Block getBlock(int x, int y)
	{
		if(x >= 0 && x < worldW && y >= 0 && y < worldH) return block[x][y].getBlock();
		else return Block.air;
	}
	
	public void setBackgroundBlock(int x, int y, int tile)
	{
		if(x >= 0 && x < worldW && y >= 0 && y < worldH) bblock[x][y].id = tile;
	}

	public int getBackgroundBlockID(int x, int y)
	{
		if(x >= 0 && x < worldW && y >= 0 && y < worldH) return bblock[x][y].id;
		else return 0;
	}

	public Block getBackgroundBlock(int x, int y)
	{
		if(x >= 0 && x < worldW && y >= 0 && y < worldH) return bblock[x][y].getBlock();
		else return Block.air;
	}

	public int getBlockID(int x, int y)
	{
		try
		{
			for(int m = 0; m < Block.blocks.size(); m++) if(getBlock(x, y) == Block.blocks.get(m))return m;
			return -1;
		}
		catch(Exception e){}
		return -1;
	}

	public void render(int camX, int camY, int renW, int renH)
	{
		posX = ((Main.mse.x / Main.SCALE) / ((Main.WIDTH / 2) / Tile.tileSize)) - 16;
		posY = ((Main.mse.y / Main.SCALE) / ((Main.HEIGHT / 2) / Tile.tileSize)) - 16;
		if(Main.player != null && !Main.player.isCreative) restriction = (posY <= 11 && posY >= -9 && posX <= 5 && posX >= -4);
		else restriction = true;
		for(int x = (camX / Tile.tileSize); x < (camX / Tile.tileSize) + renW; x++)
		{
			for(int y = (camY / Tile.tileSize); y < (camY / Tile.tileSize) + renH; y++)
			{
				if(x >= 0 && y >= 0 && x < worldW && y < worldH) bblock[x][y].render();
			}
		}
		for(Entity e : Main.getEntities()) if(!e.renderAfterWorld) e.render();
		for(Player p : Main.getPlayers()) p.render();
		for(int x = (camX / Tile.tileSize); x < (camX / Tile.tileSize) + renW; x++)
		{
			for(int y = (camY / Tile.tileSize); y < (camY / Tile.tileSize) + renH; y++)
			{
				if(x >= 0 && y >= 0 && x < worldW && y < worldH)
				{
					block[x][y].render();
					if(Options.BLOCK_BORDERS) block[x][y].getBlock().renderOutline(x, y);
					block[x][y].getBlock().renderOverlay(this, x, y);
					if(block[x][y].id != Block.air.blockID && !Main.inv.isOpen)
					{
						if(block[x][y].contains(new Point((Main.mse.x / Main.SCALE) + (int) Main.sX, (Main.mse.y / Main.SCALE) + (int) Main.sY)) && restriction)
						{
							if(!Main.inv.isOpen && !Main.skl.isOpen && !Main.com.isOpen && !Main.shop.isOpen && block[x][y].getBlock().isSelectable())
							{
								Render.setColor(new Color(200, 200, 200, 120));
								Render.drawRect(block[x][y].x - camX, block[x][y].y - camY, block[x][y].width - 1, block[x][y].height - 1);
							}
						}
					}
				}
			}
		}
		light.render(camX, camY, renW, renH);
		for(Entity e : Main.getEntities())
		{
			if(e.renderAfterWorld) e.render();
			e.renderOverWorld();
		}
		for(int i = 0; i < Buffs.buffs.size(); i++) Buffs.buffs.get(i).render();
	}

	public Biome getBiomeByX(int x)
	{
		for(BiomeBounds biome : biomes) if(biome.contains(new Point(x, 0))) return Biome.biomes.get(biome.getID());
		return null;
	}

	public float getLiquidQuantity(int x, int y) 
	{
		return liquidQuantity[x][y];
	}

	public void setLiquidQuantity(float quantity, int x, int y) 
	{
		this.liquidQuantity[x][y] = quantity;
	}
}