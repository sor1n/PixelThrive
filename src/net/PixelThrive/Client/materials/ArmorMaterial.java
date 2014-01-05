package net.PixelThrive.Client.materials;

public enum ArmorMaterial 
{
	WOOD(1, 2, 1),
	STONE(3, 5, 2),
	COPPER(4, 7, 4),
	IRON(5, 10, 5),
	BEBOP(0, 0, 10),
	VANITY(0, 1, 0),
	GOLD(8, 14, 8);
	
	ArmorMaterial(int defHelmet, int defChest, int defBoots)
	{
		this.setDefHelmet(defHelmet);
		this.setDefChest(defChest);
		this.setDefBoots(defBoots);
	}
	
	public int getDefenceHelmet() 
	{
		return defHelmet;
	}
	
	public int getDefenceChest() 
	{
		return defChest;
	}
	
	public int getDefenceBoots() 
	{
		return defBoots;
	}

	public void setDefHelmet(int def) 
	{
		this.defHelmet = def;
	}
	
	public void setDefChest(int def) 
	{
		this.defChest = def;
	}
	
	public void setDefBoots(int def) 
	{
		this.defBoots = def;
	}
	
	private int defHelmet, defChest, defBoots;

}
