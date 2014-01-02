package net.PixelThrive.Client.world;

public abstract class Tile
{
	public static int tileSize = 16;
	public static int invLength = 7;
	public static int invHeight = 3;
	public static int invCellSize = 18;
	public static int invCellSpace = 2;
	public static int invBorderSpace = 3;
	public static int invItemBorder = 3;

	//Entities
	public static int[] player = {0, 0};
	
	//TileEntities
	public static int[] crafting_bench = {0, 0};
	public static int[] small_chest = {2, 0};
	public static int[] large_chest = {3, 0};
	public static int[] furnace_inactive = {2, 0};
	public static int[] furnace_active = {4, 0};
	public static int[] wooden_door_closed = {6, 0};
	public static int[] wooden_door_open = {7, 0};
	public static int[] alchemyTable = {8, 0};
	
	//Particles
	public static int[] bubble_big = {0, 0};
	public static int[] bubble_small = {1, 0};
	public static int[] jet_flame1 = {2, 0};
	public static int[] jet_flame2 = {3, 0};
}
