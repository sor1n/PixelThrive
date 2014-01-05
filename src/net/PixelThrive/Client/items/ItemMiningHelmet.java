package net.PixelThrive.Client.items;

import net.PixelThrive.Client.entities.Player;
import net.PixelThrive.Client.materials.ArmorMaterial;
import net.PixelThrive.Client.world.Tile;
import net.PixelThrive.Client.world.World;

public class ItemMiningHelmet extends ItemArmor
{
	public ItemMiningHelmet(int id, int type, ArmorMaterial mat, String name)
	{
		super(id, type, mat, name);
	}
	
	public void tickWhileWearing(Player player, World world)
	{
		world.light.lightCircle((int)(player.getX() / Tile.tileSize), (int)(player.getY() / Tile.tileSize), 4, this);
		world.light.lightCircle((int)(player.getX() / Tile.tileSize), (int)(player.getY() / Tile.tileSize), 4, null);
	}
}
