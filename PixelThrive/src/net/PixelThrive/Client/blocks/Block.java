package net.PixelThrive.Client.blocks;

import java.util.ArrayList;
import java.util.List;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.PixelException;
import net.PixelThrive.Client.GUI.CreativeMenu;
import net.PixelThrive.Client.GUI.CreativeTabs;
import net.PixelThrive.Client.GUI.Slot;
import net.PixelThrive.Client.entities.tileentities.CraftingBench;
import net.PixelThrive.Client.entities.tileentities.Furnace;
import net.PixelThrive.Client.entities.tileentities.MysticWorktable;
import net.PixelThrive.Client.items.CraftableStack;
import net.PixelThrive.Client.items.Item;
import net.PixelThrive.Client.items.ItemFunctions;
import net.PixelThrive.Client.materials.BlockMaterial;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.renders.Texture;
import net.PixelThrive.Client.world.Tile;
import net.PixelThrive.Client.world.World;

public class Block extends BlockAndItem
{
	public static int[] IDs = new int[4096];
	public int blockID;

	protected Texture texture;
	protected int resistance, quantityDropped = 0, toolPower, maxStackSize = 100, biome = -1;
	protected String name;
	protected BlockMaterial material;
	protected Item[] tool;
	protected int drop;
	protected String walkSound = "";
	protected CraftableStack[] drops;
	protected boolean hasOutline = true, shouldCrop = true, renderInWorld = true, isCollidable = true, middleMouse = true, dropRender = true, canTurn = true, isSolid = true, isTransparent = false, hasRandomDrop = false;
	public boolean isGettingDestroyed = false;
	public static List<OreBlock> ores = new ArrayList<OreBlock>();
	protected int rarity, size;
	public Slot specialSlot;
	private float[][] liquidQuantity;

	public static List<Block> blocks = new ArrayList<Block>();
	public static final Block air = new Block(0, 0).setUnbreakable().setTransparent(true).setFunctions(null).setMaterial(BlockMaterial.AIR).setName("air");
	public static final Block dirt = new GrassBlock(1, 1, false).setResistance(20).setMaterial(BlockMaterial.GROUND).setTool(Item.shovel, 0).setQuantityDropped(1).setName("dirt").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("Don't touch, it's dirty.");
	public static final Block grass = new GrassBlock(2, 0, true).setWalkingSound("grass").setTurnable(false).setResistance(40).setMaterial(BlockMaterial.GROUND).setTool(Item.shovel, 0).setDrop(dirt.blockID).setQuantityDropped(1).setName("grass").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("Creepy crawlers...");	
	public static final Block stone = new Block(3, 2).setResistance(200).setMaterial(BlockMaterial.STONE).setTool(Item.pickaxe, 0).setName("stone").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("Pretty hard to mine without tools..");
	public static final Block bedrock = new Block(4, 3).setUnbreakable().setMaterial(BlockMaterial.STONE).setName("bedrock").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("Mine this. Challenge accepted?");
	public static final Block basalt = new Block(5, 4).setResistance(200).setMaterial(BlockMaterial.STONE).setTool(Item.pickaxe, 0).setName("basalt").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("No, not bathsalts.");
	public static final Block lavaStone = new Block(6, 5).setResistance(200).setMaterial(BlockMaterial.STONE).setTool(Item.pickaxe, 0).setName("lavastone").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("Is it hot here or is it me?");
	public static final Block obsidian = new Block(7, 6).setResistance(500).setMaterial(BlockMaterial.STONE).setTool(Item.pickaxe, 3).setName("obsidian").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("So purple :>");
	public static final Block mossyStone = new Block(8, 7).setResistance(200).setMaterial(BlockMaterial.STONE).setTool(Item.pickaxe, 0).setName("mossy Stone").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("Overgrown rock. Cool eh?");
	public static final Block glass = new Block(9, 8).setResistance(10).setMaterial(BlockMaterial.GLASS).setName("glass").setTransparent(true).setCreativeTab(CreativeTabs.DECORATION).setHelpDescription("See-through o_o");
	public static final Block zenGlass = new Block(10, 9).setResistance(10).setMaterial(BlockMaterial.GLASS).setTransparent(true).setName("zen Glass").setCreativeTab(CreativeTabs.DECORATION).setHelpDescription("Straight from the far east.");
	public static final Block stoneBricks = new Block(11, 10).setResistance(200).setMaterial(BlockMaterial.STONE).setTool(Item.pickaxe, 0).setName("stone Bricks").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("Cheap to make, looks rich. ;D");
	public static final Block ironFence = new Block(12, 11).setResistance(300).setMaterial(BlockMaterial.METAL).setTransparent(true).setTool(Item.pickaxe, 2).setName("iron Fence").setCreativeTab(CreativeTabs.DECORATION).setHelpDescription("Tresspassers will be shot twice.");
	public static final Block woodPlanks = new Block(13, 12).setResistance(25).setMaterial(BlockMaterial.WOOD).setTool(Item.axe, 0).setQuantityDropped(1).setName("planks").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("Majestic planks.");
	public static final Block log = new Block(14, 13).setResistance(150).setMaterial(BlockMaterial.WOOD).setTool(Item.axe, 0).setQuantityDropped(1).setName("log").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("Bark at the moon");
	public static final Block leaves = new LeafBlock(15, 14).setWalkingSound("grass").setResistance(10).setTool(Item.shears, 0).setHasRandomDrop(true).setDrops(new CraftableStack[]{new CraftableStack(Item.leaf, 3), new CraftableStack(Item.branch, 2)}).setMaterial(BlockMaterial.PLANTS).setName("leaves").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("Leaves 'n shit");
	public static final Block sand = new GravityBlock(16, 16).setResistance(19).setMaterial(BlockMaterial.GROUND).setTool(Item.shovel, 0).setQuantityDropped(1).setName("sand").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("Keep digging and you'll find pyramids");
	public static final Block grit = new GravityBlock(17, 18).setResistance(24).setMaterial(BlockMaterial.GROUND).setTool(Item.shovel, 0).setQuantityDropped(1).setName("grit").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("Gotta love grit");
	public static final Block clay = new Block(18, 17).setResistance(25).setDrop(Item.clay.itemID).setQuantityDropped(6).setTool(Item.shovel, 0).setMaterial(BlockMaterial.GROUND).setName("clay").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("Not for human consumption");
	public static final Block sandStone = new Block(19, 19).setResistance(200).setMaterial(BlockMaterial.STONE).setTool(Item.pickaxe, 0).setName("sand Stone").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("HARD SAND O_O");
	public static final Block water = new LiquidBlock(20, 240, 0).setTurnable(false).setSemiBlock(true).setUnbreakable().setMaterial(BlockMaterial.WATER).setRenderInWorld(false).setName("water").setCreativeTab(CreativeTabs.LIQUIDS).setHelpDescription("Don't feed the sharks");
	public static final Block chest = new ChestBlock(21).setTurnable(false).setSemiBlock(true).setMaterial(BlockMaterial.WOOD).setName("wooden Chest").setQuantityDropped(1).setTool(Item.axe, 0).setResistance(55).setRenderInWorld(false).setDifferentDroppedRender().setCreativeTab(CreativeTabs.STORAGE).setHelpDescription("Open me :D");
	public static final Block bricks = new Block(22, 22).setMaterial(BlockMaterial.STONE).setName("bricks").setQuantityDropped(1).setTool(Item.pickaxe, 1).setResistance(300).setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("Let's build the ghetto :3");
	public static final Block furnace = new TileEntityBlock(23, Furnace.class).setCropInHand(false).setSemiBlock(true).setTexture(24).setMaterial(BlockMaterial.STONE).setName("furnace").setQuantityDropped(1).setTool(Item.pickaxe, 0).setResistance(300).setCreativeTab(CreativeTabs.DECORATION).setHelpDescription("Burn baby! BURN!");
	public static final Block craftingBench = new TileEntityBlock(24, CraftingBench.class).setCropInHand(false).setSemiBlock(true).setTexture(23).setMaterial(BlockMaterial.WOOD).setName("crafting Bench").setQuantityDropped(1).setTool(Item.axe, 0).setResistance(100).setCreativeTab(CreativeTabs.DECORATION).setHelpDescription("I can create... PLANKS! :3");
	public static final Block marble = new Block(25).setMaterial(BlockMaterial.STONE).setName("marble").setTexture(25).setTool(Item.pickaxe, 1).setResistance(250).setHelpDescription("SHINY! :O").setCreativeTab(CreativeTabs.BUILDING);
	public static final Block jade = new Block(26).setMaterial(BlockMaterial.STONE).setName("jade").setTexture(26).setTool(Item.pickaxe, 1).setResistance(240).setHelpDescription("Smells like mint.").setCreativeTab(CreativeTabs.BUILDING);
	public static final Block dacite = new Block(27).setMaterial(BlockMaterial.STONE).setName("dacite").setTexture(27).setTool(Item.pickaxe, 1).setResistance(275).setHelpDescription("Not advised to stare for too long.").setCreativeTab(CreativeTabs.BUILDING);
	public static final Block mudstone = new Block(28).setMaterial(BlockMaterial.STONE).setName("mudstone").setTexture(28).setTool(Item.pickaxe, 0).setResistance(300).setHelpDescription("You should Play-Do").setCreativeTab(CreativeTabs.BUILDING);
	public static final Block swampLog = new Block(29).setMaterial(BlockMaterial.WOOD).setQuantityDropped(1).setName("swamp Log").setTexture(29).setTool(Item.axe, 0).setResistance(55).setHelpDescription("Looks cursed.").setCreativeTab(CreativeTabs.BUILDING);
	public static final Block swampPlanks = new Block(30).setMaterial(BlockMaterial.WOOD).setQuantityDropped(1).setName("swamp Planks").setTexture(30).setTool(Item.axe, 0).setResistance(30).setHelpDescription("Looks haunted.").setCreativeTab(CreativeTabs.BUILDING);
	/**/ public static final Block tnt = new ExplosiveBlock(31, 3F).setMaterial(BlockMaterial.GROUND).setQuantityDropped(1).setName("tnt").setTexture(31).setTool(Item.axe, 0).setResistance(20).setHelpDescription("Don't cut the wrong wire").setCreativeTab(CreativeTabs.EXPLOSIVE);
	public static final Block goldBricks = new Block(32).setMaterial(BlockMaterial.STONE).setName("gold Bricks").setTexture(32).setTool(Item.pickaxe, 2).setResistance(500).setHelpDescription("Only for rich people $_$").setCreativeTab(CreativeTabs.BUILDING);
	public static final Block jadeBricks = new Block(33).setMaterial(BlockMaterial.STONE).setName("jade Bricks").setTexture(33).setTool(Item.pickaxe, 2).setResistance(500).setHelpDescription("Jade walls FTW!").setCreativeTab(CreativeTabs.BUILDING);
	/**/ public static final Block sunRelic = new Block(34).setMaterial(BlockMaterial.STONE).setName("sun Relic").setTexture(34).setTool(Item.pickaxe, 3).setResistance(500).setHelpDescription("CLEAN UP THE GHETTO!").setCreativeTab(CreativeTabs.BUILDING);
	/**/ public static final Block moonRelic = new Block(35).setMaterial(BlockMaterial.STONE).setName("moon Relic").setTexture(35).setTool(Item.pickaxe, 3).setResistance(500).setHelpDescription("BIG DICK MYSTIC!").setCreativeTab(CreativeTabs.BUILDING);
	public static final Block barrier = new Block(36).setMiddleMouse(false).setTurnable(false).setMaterial(BlockMaterial.VOID).setName("barrier").setTool(Item.pickaxe, 100).setResistance(1).setRenderInWorld(false).setTexture(0).setHelpDescription("Invisibru");
	public static final Block woodenDoor = new TileEntityBlock(37, net.PixelThrive.Client.entities.tileentities.Door.class).setCropInHand(false).setMaterial(BlockMaterial.WOOD).setName("wooden Door").setTool(Item.axe, 0).setTexture(37).setCreativeTab(CreativeTabs.BUILDING);
	public static final Block mysticWorktable = new TileEntityBlock(38, MysticWorktable.class).setCropInHand(false).setTexture(36).setMaterial(BlockMaterial.WOOD).setName("mystic Worktable").setQuantityDropped(1).setTool(Item.axe, 0).setResistance(200).setCreativeTab(CreativeTabs.DECORATION).setHelpDescription("Black magic!");
	/**/ public static final Block ice = new Block(39).setMaterial(BlockMaterial.GLASS).setName("ice").setTool(Item.pickaxe, 0).setResistance(80).setHelpDescription("Caution! Slippery!").setCreativeTab(CreativeTabs.BUILDING).setTexture(38).setQuantityDropped(1);
	public static final Block snow = new GravityBlock(40).setMaterial(BlockMaterial.GROUND).setName("snow").setTool(Item.shovel, 0).setResistance(60).setHelpDescription("Better than clay.").setCreativeTab(CreativeTabs.BUILDING).setTexture(39).setQuantityDropped(1);
	public static final Block permaFrost = new Block(41).setMaterial(BlockMaterial.GLASS).setName("permafrost").setTool(Item.pickaxe, 3).setResistance(400).setHelpDescription("Hard to break.").setCreativeTab(CreativeTabs.BUILDING).setTexture(40).setQuantityDropped(1);
	public static final Block woodenTorch = new TorchBlock(42).setCropInHand(false).setSemiBlock(true).setTurnable(false).setCollidable(false).setMaterial(BlockMaterial.WOOD).setName("wooden Torch").setTool(Item.axe, 0).setResistance(20).setHelpDescription("Darkness Preventinator 2000 v0.1").setCreativeTab(CreativeTabs.DECORATION).setQuantityDropped(1).setTexture(41);
	public static final Block ironOre = new OreBlock(43, 50, 3).setTexture(42).setResistance(200).setMaterial(BlockMaterial.STONE).setTool(Item.pickaxe, 1).setName("iron Ore").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("It's shiny, so it must be valuable");
	public static final Block goldOre = new OreBlock(44, 200, 2).setTexture(43).setResistance(400).setMaterial(BlockMaterial.STONE).setTool(Item.pickaxe, 2).setName("gold Ore").setCreativeTab(CreativeTabs.BUILDING).setHelpDescription("This is true bling.");
	public static final Block cobweb = new CobwebBlock(45).setTransparent(true).setCollidable(false).setSemiBlock(true).setName("cobweb").setTool(Item.sword, 0).setResistance(100).setHelpDescription("Sticky situation.").setCreativeTab(CreativeTabs.BUILDING).setTexture(44).setDrops(new CraftableStack[]{new CraftableStack(Item.string, 2)});
	
	public Block(int id)
	{
		if(blocks.size() <= 0) blocks.add(this);
		else
		{
			if(id < IDs.length)
			{
				maxStackSize = 100;
				blockID = ID = id;
				blocks.add(this);
				drop = blockID;
				if(id != 0) setFunctions(new ItemFunctions[]{ItemFunctions.DEFAULT, ItemFunctions.THROW});
			}
			else throw new PixelException("Ran out of Block IDs!", PixelException.ExceptionType.NOTENOUGHIDS, null, null);
		}
	}
	
	public Block setCropInHand(boolean b)
	{
		shouldCrop = b;
		return this;
	}
	
	public Block setSemiBlock(boolean b)
	{
		hasOutline = !b;
		return this;
	}

	public Block setFunctions(ItemFunctions[] i)
	{
		functions = i;
		return this;
	}

	public Block setWalkingSound(String soundName)
	{
		walkSound = soundName;
		return this;
	}

	public String getWalkingSound()
	{
		return walkSound;
	}
	
	public boolean shouldCropInHand()
	{
		return shouldCrop;
	}

	public boolean isAttachedToBlock(int x, int y)
	{
		return (Main.world.getBackgroundBlock(x, y) != Block.air || Main.world.getBlock(x + 1, y) != Block.air || Main.world.getBlock(x - 1, y) != Block.air || Main.world.getBlock(x, y + 1) != Block.air);
	}

	public Block setMiddleMouse(boolean bool)
	{
		middleMouse = bool;
		return this;
	}

	public Block setTurnable(boolean bool)
	{
		canTurn = bool;
		return this;
	}

	public Block setCollidable(boolean bool)
	{
		isCollidable = bool;
		return this;
	}

	public int getRarity()
	{
		return rarity;
	}

	public int getMaxVeinSize()
	{
		return size;
	}

	public Block setRarity(int i)
	{
		rarity = i;
		return this;
	}

	public Block setMaxVeinSize(int i)
	{
		size = i;
		return this;
	}

	public Block setCreativeTab(CreativeTabs tab)
	{
		if(tab == CreativeTabs.BUILDING) CreativeMenu.buildBlocks.add(this);
		if(tab == CreativeTabs.EXPLOSIVE) CreativeMenu.explosives.add(this);
		if(tab == CreativeTabs.DECORATION) CreativeMenu.decoBlocks.add(this);
		if(tab == CreativeTabs.LIQUIDS) CreativeMenu.liquids.add(this);
		if(tab == CreativeTabs.STORAGE) CreativeMenu.storageBlocks.add(this);
		if(tab == CreativeTabs.MISCELANIOUS) CreativeMenu.misc.add(this);
		return this;
	}

	public Block(int id, int texture)
	{
		this(id);
		setTexture(texture);
	}

	public Block(int id, int texture, SpriteSheet s)
	{
		this(id);
		setTexture(texture, s);
	}

	public boolean isSelectable()
	{
		return true;
	}

	public boolean middleMouse()
	{
		return middleMouse;
	}

	public boolean isCollidable()
	{
		return isCollidable;
	}

	public boolean isTurnable()
	{
		return canTurn;
	}

	public boolean renderInWorld()
	{
		return renderInWorld;
	}

	public boolean isGrassNearby(int x, int y)
	{
		for(int i = -1; i <= 1; i++)
			for(int j = -1; j <= 1; j++)
				if(Main.world.getBlock(x + i, y + j) == Block.grass) return true;
		return false;					
	}

	public Block setTool(Item[] t, int i)
	{
		tool = t;
		toolPower = i;
		return this;
	}

	public Block setRenderInWorld(boolean b)
	{
		renderInWorld = b;
		return this;
	}

	public Item[] getTool()
	{
		return tool;
	}

	public int getToolPower()
	{
		return toolPower;
	}

	public void renderOutline(int x, int y)
	{
		if(hasOutline)
		{
			try
			{
				Render.setColor(0x000000);
				if(blockID != 0 && (Main.world.getBlockID(x, y - 1) == 0 || Main.world.getBlock(x, y - 1).isSemiBlock())) Render.drawLine((x * Tile.tileSize) - (int)Main.sX, (y * Tile.tileSize) - (int)Main.sY - 1, (x * Tile.tileSize) - (int)Main.sX + (Tile.tileSize - 1), (y * Tile.tileSize) - (int)Main.sY - 1);
				if(blockID != 0 && (Main.world.getBlockID(x + 1, y) == 0 || Main.world.getBlock(x + 1, y).isSemiBlock())) Render.drawLine((x * Tile.tileSize) - (int)Main.sX + Tile.tileSize, (y * Tile.tileSize) - (int)Main.sY, (x * Tile.tileSize) - (int)Main.sX + Tile.tileSize, (y * Tile.tileSize) - (int)Main.sY + (Tile.tileSize - 1));
				if(blockID != 0 && (Main.world.getBlockID(x - 1, y) == 0 || Main.world.getBlock(x - 1, y).isSemiBlock())) Render.drawLine((x * Tile.tileSize) - (int)Main.sX - 1, (y * Tile.tileSize) - (int)Main.sY, (x * Tile.tileSize) - (int)Main.sX - 1, (y * Tile.tileSize) - (int)Main.sY + (Tile.tileSize - 1));
				if(blockID != 0 && (Main.world.getBlockID(x, y + 1) == 0 || Main.world.getBlock(x, y + 1).isSemiBlock())) Render.drawLine((x * Tile.tileSize) - (int)Main.sX, (y * Tile.tileSize) - (int)Main.sY + Tile.tileSize, (x * Tile.tileSize) - (int)Main.sX + (Tile.tileSize - 1), (y * Tile.tileSize) - (int)Main.sY + Tile.tileSize);
			}
			catch(Exception e){}
		}
	}

	public void renderOverlay(World world, int x, int y)
	{
	}

	public void renderDrop(int x, int y)
	{
	}

	public Block setDifferentDroppedRender()
	{
		dropRender = false;
		return this;
	}

	public boolean dropRender()
	{
		return dropRender;
	}

	public Block setName(String name)
	{
		this.name = name;
		return this;
	}

	public String getName()
	{
		return name;
	}

	public Block setDrop(int drop)
	{
		this.drop = drop;
		return this;
	}

	public Block setDrops(CraftableStack[] drops)
	{
		setDrop(0);
		this.drops = drops;
		return this;
	}

	public CraftableStack[] getDrops()
	{
		return drops;
	}


	public int getDrop()
	{
		return drop;
	}

	public Texture getTexture()
	{
		return texture;
	}

	public Block setTexture(int id)
	{
		int y = id / Tile.tileSize;
		this.texture = new Texture(SpriteSheet.Terrain, id - Tile.tileSize * y, y);
		return this;
	}

	public Block setTexture(int texture, SpriteSheet s)
	{
		int y = texture / Tile.tileSize;
		this.texture = new Texture(s, texture - Tile.tileSize * y, y);
		return this;
	}

	public SpriteSheet getSpriteSheet()
	{
		if(texture != null) return texture.getSpriteSheet();
		else return null;
	}

	public int getResistance()
	{
		return resistance;
	}

	public Block setResistance(int i)
	{
		resistance = i;
		return this;
	}

	public int getBiome()
	{
		return biome;
	}

	public Block setBiome(int i)
	{
		biome = i;
		return this;
	}

	public Block setUnbreakable()
	{
		resistance = -1;
		return this;
	}
	
	public boolean isUnbreakable()
	{
		return resistance == -1;
	}

	public BlockMaterial getMaterial()
	{
		return material;
	}

	public Block setMaterial(BlockMaterial i)
	{
		material = i;
		return this;
	}

	public int getQuantityDropped()
	{
		return quantityDropped;
	}

	public Block setQuantityDropped(int quantity)
	{
		quantityDropped = quantity;
		return this;
	}

	public void blockTick(World world, int x, int y)
	{
	}

	public int[] getSheetCoordinates()
	{
		return new int[]{getTexture().x, getTexture().y};
	}

	public Block setHelpDescription(String desc)
	{
		helpDescription = desc;
		return this;
	}

	public Block setMaxStackSize(int i)
	{
		maxStackSize = i;
		return this;
	}

	public int getMaxStackSize()
	{
		return maxStackSize;
	}

	public static Block getBlock(int id)
	{
		for(int i = 0; i < blocks.size(); i++)
		{
			if(blocks.get(i).blockID == id) return blocks.get(i);
		}
		return air;
	}
	
	public void tickMouseHolding()
	{
		if(Main.inv != null && this.isCollidable && this.isSolid && this != chest && !(this instanceof LiquidBlock) && !(this instanceof TileEntityBlock)) 
			canItemBePlacedInSlot(Main.inv.invVanity[0]);
	}

	public Block canItemBePlacedInSlot(Slot slot)
	{
		specialSlot = slot;
		return this;
	}

	public Slot getSpecial() 
	{
		return specialSlot;
	}

	public void onWalking()
	{
	}

	public void onDestroyed(int x, int y)
	{
		isGettingDestroyed = true;
	}

	public boolean isTransparent()
	{
		return isTransparent;
	}

	public Block setTransparent(boolean u)
	{
		isTransparent = u;
		return this;
	}

	public void onRightClick(int x, int y)
	{		
	}

	public void onLeftClick(int x, int y)
	{		
	}

	public void onPlaced(World world, int x, int y)
	{
		world.block[x][y].id = blockID;
		if(Main.player != null && !Main.player.isCreative) Main.inv.invBar[Main.inv.selection].removeItemFromSlot();
	}

	public void onBackgroundPlaced(World world, int x, int y)
	{
		world.bblock[x][y].id = blockID;
		if(Main.player != null && !Main.player.isCreative) Main.inv.invBar[Main.inv.selection].removeItemFromSlot();
	}

	public boolean isSolid()
	{
		return true;
	}
	
	public boolean isLiquid()
	{
		return false;
	}
	
	public boolean isSemiBlock()
	{
		return !hasOutline;
	}

	public double slowDownInBlock()
	{
		return 0D;
	}

	public Block setCurrentFunction(ItemFunctions i)
	{
		currentFunc = i;
		return this;
	}

	public boolean hasRandomDrop() 
	{
		return hasRandomDrop;
	}

	public Block setHasRandomDrop(boolean hasRandomDrop) 
	{
		this.hasRandomDrop = hasRandomDrop;
		return this;
	}

	public float getLiquidQuantity(int x, int y) 
	{
		return liquidQuantity[x][y];
	}
}
