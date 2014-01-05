package net.PixelThrive.Client.crafting;

import java.util.*;

import net.PixelThrive.Client.items.*;
import net.PixelThrive.Client.blocks.*;

public class Recipes 
{
	private static List<CraftableStack> results = new ArrayList<CraftableStack>();
	private static List<CraftableStack[]> recipes = new ArrayList<CraftableStack[]>();
	private static List<Integer> loc = new ArrayList<Integer>();
	
	//Normal Recipes		
	public static final Recipes planks = new Recipes(new CraftableStack(Block.woodPlanks, 4), new CraftableStack[]{new CraftableStack(Block.log, 1)}, 0);
	public static final Recipes stoneBricks = new Recipes(new CraftableStack(Block.stoneBricks, 2), new CraftableStack[]{new CraftableStack(Block.stone, 4)}, 0);
	public static final Recipes jadeBricks = new Recipes(new CraftableStack(Block.jadeBricks, 2), new CraftableStack[]{new CraftableStack(Block.jade, 4)}, 0);
	public static final Recipes goldBricks = new Recipes(new CraftableStack(Block.goldBricks, 2), new CraftableStack[]{new CraftableStack(Item.goldIngot, 6)}, 0);
	public static final Recipes stick = new Recipes(new CraftableStack(Item.stick, 4), new CraftableStack[]{new CraftableStack(Block.woodPlanks, 2)}, 0);
	public static final Recipes woodenPick = new Recipes(new CraftableStack(Item.woodenPick, 1), new CraftableStack[]{new CraftableStack(Block.woodPlanks, 3), new CraftableStack(Item.stick, 3)}, 0);
	public static final Recipes woodenSword = new Recipes(new CraftableStack(Item.woodenSword, 1), new CraftableStack[]{new CraftableStack(Block.woodPlanks, 2), new CraftableStack(Item.stick, 1)}, 0);
	public static final Recipes woodenAxe = new Recipes(new CraftableStack(Item.woodenAxe, 1), new CraftableStack[]{new CraftableStack(Block.woodPlanks, 3), new CraftableStack(Item.stick, 3)}, 0);
	public static final Recipes woodenShovel = new Recipes(new CraftableStack(Item.woodenShovel, 1), new CraftableStack[]{new CraftableStack(Block.woodPlanks, 1), new CraftableStack(Item.stick, 3)}, 0);
	public static final Recipes woodenBow = new Recipes(new CraftableStack(Item.woodenBow, 1), new CraftableStack[]{new CraftableStack(Item.string, 3), new CraftableStack(Item.stick, 3)}, 0);
	public static final Recipes woodenArrow = new Recipes(new CraftableStack(Item.woodenArrow, 2), new CraftableStack[]{new CraftableStack(Item.stick), new CraftableStack(Block.woodPlanks)}, 0);
	public static final Recipes woodenShears = new Recipes(new CraftableStack(Item.woodenShears, 1), new CraftableStack[]{new CraftableStack(Item.stick, 4), new CraftableStack(Block.woodPlanks, 2)}, 0);
	public static final Recipes chest = new Recipes(new CraftableStack(Block.chest, 1), new CraftableStack[]{new CraftableStack(Block.woodPlanks, 12)}, 0);
	public static final Recipes bottle = new Recipes(new CraftableStack(Item.bottle, 2), new CraftableStack[]{new CraftableStack(Block.glass, 3)}, 0);
	public static final Recipes sandStone = new Recipes(new CraftableStack(Block.sandStone, 1), new CraftableStack[]{new CraftableStack(Block.sand, 6)}, 0);
	public static final Recipes leaves = new Recipes(new CraftableStack(Block.leaves, 4), new CraftableStack[]{new CraftableStack(Item.leaf, 6)}, 0);
	public static final Recipes stick_2 = new Recipes(new CraftableStack(Item.stick, 6), new CraftableStack[]{new CraftableStack(Item.branch, 1)}, 0);
	public static final Recipes leaf = new Recipes(new CraftableStack(Item.leaf, 6), new CraftableStack[]{new CraftableStack(Block.leaves, 1)}, 0);
	public static final Recipes bricks = new Recipes(new CraftableStack(Block.bricks, 4), new CraftableStack[]{new CraftableStack(Item.brick, 6)}, 0);
	public static final Recipes dynamite = new Recipes(new CraftableStack(Item.dynamite, 1), new CraftableStack[]{new CraftableStack(Block.sand, 1), new CraftableStack(Item.string, 2), new CraftableStack(Item.clay, 2)}, 0);
	public static final Recipes clayBlock = new Recipes(new CraftableStack(Block.clay, 1), new CraftableStack[]{new CraftableStack(Item.clay, 6)}, 0);
	public static final Recipes clayBalls = new Recipes(new CraftableStack(Item.clay, 6), new CraftableStack[]{new CraftableStack(Block.clay, 1)}, 0);
	public static final Recipes plank = new Recipes(new CraftableStack(Item.plank, 6), new CraftableStack[]{new CraftableStack(Block.woodPlanks, 1)}, 0);
	public static final Recipes planks_2 = new Recipes(new CraftableStack(Block.woodPlanks, 1), new CraftableStack[]{new CraftableStack(Item.plank, 6)}, 0);
	public static final Recipes craftingTable = new Recipes(new CraftableStack(Block.craftingBench, 1), new CraftableStack[]{new CraftableStack(Block.woodPlanks, 20), new CraftableStack(Item.stick, 4)}, 0);
	public static final Recipes swampPlanks = new Recipes(new CraftableStack(Block.swampPlanks, 4), new CraftableStack[]{new CraftableStack(Block.swampLog, 1)}, 0);
	public static final Recipes nails = new Recipes(new CraftableStack(Item.nails, 3), new CraftableStack[]{new CraftableStack(Item.ironIngot, 1)}, 0);
	public static final Recipes torch = new Recipes(new CraftableStack(Block.woodenTorch, 2), new CraftableStack[]{new CraftableStack(Item.charcoal, 1), new CraftableStack(Item.stick, 2)}, 0);
	
	//Crafting Table Recipes
	public static final Recipes zenGlass = new Recipes(new CraftableStack(Block.zenGlass, 1), new CraftableStack[]{new CraftableStack(Block.glass, 6)}, 1);
	public static final Recipes furnace = new Recipes(new CraftableStack(Block.furnace, 1), new CraftableStack[]{new CraftableStack(Block.stone, 20)}, 1);
	public static final Recipes woodenDoor = new Recipes(new CraftableStack(Block.woodenDoor, 1), new CraftableStack[]{new CraftableStack(Block.woodPlanks, 10)}, 1);
	public static final Recipes alchemyTable = new Recipes(new CraftableStack(Block.mysticWorktable, 1), new CraftableStack[]{new CraftableStack(Block.swampPlanks, 20), new CraftableStack(Item.stick, 6), new CraftableStack(Block.swampLog, 2)}, 1);
	public static final Recipes woodHelmet = new Recipes(new CraftableStack(Item.woodenHelmet, 1), new CraftableStack[]{new CraftableStack(Block.woodPlanks, 8)}, 1);
	public static final Recipes woodChest = new Recipes(new CraftableStack(Item.woodenChest, 1), new CraftableStack[]{new CraftableStack(Block.woodPlanks, 15)}, 1);
	public static final Recipes woodBoots = new Recipes(new CraftableStack(Item.woodenBoots, 1), new CraftableStack[]{new CraftableStack(Block.woodPlanks, 4)}, 1);
	public static final Recipes stoneHelmet = new Recipes(new CraftableStack(Item.stoneHelmet, 1), new CraftableStack[]{new CraftableStack(Block.stone, 8)}, 1);
	public static final Recipes stoneChest = new Recipes(new CraftableStack(Item.stoneChest, 1), new CraftableStack[]{new CraftableStack(Block.stone, 15)}, 1);
	public static final Recipes stoneBoots = new Recipes(new CraftableStack(Item.stoneBoots, 1), new CraftableStack[]{new CraftableStack(Block.stone, 4)}, 1);
	public static final Recipes ironHelmet = new Recipes(new CraftableStack(Item.ironHelmet, 1), new CraftableStack[]{new CraftableStack(Item.ironIngot, 8)}, 1);
	public static final Recipes ironChest = new Recipes(new CraftableStack(Item.ironChest, 1), new CraftableStack[]{new CraftableStack(Item.ironIngot, 15)}, 1);
	public static final Recipes ironBoots = new Recipes(new CraftableStack(Item.ironBoots, 1), new CraftableStack[]{new CraftableStack(Item.ironIngot, 4)}, 1);
	public static final Recipes stoneArrow = new Recipes(new CraftableStack(Item.stoneArrow, 2), new CraftableStack[]{new CraftableStack(Item.stick), new CraftableStack(Block.stone)}, 1);
	public static final Recipes stonePick = new Recipes(new CraftableStack(Item.stonePick, 1), new CraftableStack[]{new CraftableStack(Block.stone, 3), new CraftableStack(Item.stick, 3)}, 1);
	public static final Recipes stoneSword = new Recipes(new CraftableStack(Item.stoneSword, 1), new CraftableStack[]{new CraftableStack(Block.stone, 2), new CraftableStack(Item.stick, 1)}, 1);
	public static final Recipes stoneAxe = new Recipes(new CraftableStack(Item.stoneAxe, 1), new CraftableStack[]{new CraftableStack(Block.stone, 3), new CraftableStack(Item.stick, 3)}, 1);
	public static final Recipes stoneShovel = new Recipes(new CraftableStack(Item.stoneShovel, 1), new CraftableStack[]{new CraftableStack(Block.stone, 1), new CraftableStack(Item.stick, 3)}, 1);
	public static final Recipes stoneBow = new Recipes(new CraftableStack(Item.stoneBow, 1), new CraftableStack[]{new CraftableStack(Item.string, 3), new CraftableStack(Block.stone, 2)}, 1);
	public static final Recipes stoneShears = new Recipes(new CraftableStack(Item.stoneShears, 1), new CraftableStack[]{new CraftableStack(Item.stick, 4), new CraftableStack(Block.stone, 2)}, 1);	
	public static final Recipes ironPick = new Recipes(new CraftableStack(Item.ironPick, 1), new CraftableStack[]{new CraftableStack(Item.ironIngot, 3), new CraftableStack(Item.stick, 3)}, 1);
	public static final Recipes ironSword = new Recipes(new CraftableStack(Item.ironSword, 1), new CraftableStack[]{new CraftableStack(Item.ironIngot, 2), new CraftableStack(Item.stick, 1)}, 1);
	public static final Recipes ironAxe = new Recipes(new CraftableStack(Item.ironAxe, 1), new CraftableStack[]{new CraftableStack(Item.ironIngot, 3), new CraftableStack(Item.stick, 3)}, 1);
	public static final Recipes ironShovel = new Recipes(new CraftableStack(Item.ironShovel, 1), new CraftableStack[]{new CraftableStack(Item.ironIngot, 1), new CraftableStack(Item.stick, 3)}, 1);
	public static final Recipes ironBow = new Recipes(new CraftableStack(Item.ironBow, 1), new CraftableStack[]{new CraftableStack(Item.string, 3), new CraftableStack(Item.ironIngot, 3)}, 1);	
	public static final Recipes ironArrow = new Recipes(new CraftableStack(Item.ironArrow, 2), new CraftableStack[]{new CraftableStack(Item.stick), new CraftableStack(Item.ironIngot)}, 1);
	public static final Recipes miningHelm = new Recipes(new CraftableStack(Item.miningHelmet, 1), new CraftableStack[]{new CraftableStack(Item.ironIngot, 6), new CraftableStack(Block.woodenTorch, 1)}, 1);
	public static final Recipes bebopBoots = new Recipes(new CraftableStack(Item.bebopBoots, 1), new CraftableStack[]{new CraftableStack(Item.ironIngot, 25)}, 1);
	
	//Furnace Recipes
	public static final Recipes bricks_2 = new Recipes(new CraftableStack(Item.brick, 6), new CraftableStack[]{new CraftableStack(Block.bricks, 4)}, 2);
	public static final Recipes glass = new Recipes(new CraftableStack(Block.glass, 1), new CraftableStack[]{new CraftableStack(Block.sand, 1)}, 2);
	public static final Recipes ironIngot = new Recipes(new CraftableStack(Item.ironIngot, 1), new CraftableStack[]{new CraftableStack(Block.ironOre, 1)}, 2);
	public static final Recipes goldIngot = new Recipes(new CraftableStack(Item.goldIngot, 1), new CraftableStack[]{new CraftableStack(Block.goldOre, 1)}, 2);
	public static final Recipes charcoal_1 = new Recipes(new CraftableStack(Item.charcoal, 2), new CraftableStack[]{new CraftableStack(Block.log, 1)}, 2);
	public static final Recipes charcoal_2 = new Recipes(new CraftableStack(Item.charcoal, 2), new CraftableStack[]{new CraftableStack(Block.swampLog, 1)}, 2);
	
	//Alchemy Table Recipes
	public static final Recipes harmingPotion = new Recipes(new CraftableStack(Item.harmingPotion, 1), new CraftableStack[]{new CraftableStack(Item.waterBottle, 1), new CraftableStack(Item.clay, 4)}, 3);
	
	/** 
	 * @param obj The resulting Item/Block
	 * @param obj2 The Materials needed
	 * @param location The tile entity it needs to be next to (0 = nothing, 1 = crafting bench, 2 = furnace, 3 = mystic worktable) 
	 */
	public Recipes(CraftableStack obj, CraftableStack[] obj2, int location)
	{
		addRecipe(obj, obj2, location);
	}
	
	public static List<CraftableStack> getResults()
	{
		return results;
	}
	
	public static List<CraftableStack[]> getRecipes()
	{
		return recipes;
	}
	
	public static List<Integer> getTileEntities()
	{
		return loc;
	}
	
	public void addRecipe(CraftableStack object, CraftableStack[] recipe, int location)
	{
		results.add(object);
		recipes.add(recipe);
		loc.add(location);
	}
}
