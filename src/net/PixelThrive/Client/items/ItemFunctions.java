package net.PixelThrive.Client.items;

public enum ItemFunctions 
{	
	DEFAULT(0, "None", 33),
	THROW(1, "Throw", 34),
	SLASH(2, "Slash", 35),
	STAB(3, "Stab", 36),
	POKE(4, "Poke", 37),
	CRUSH(5, "Crush", 38),
	SLAP(6, "Slap", 39);
	
	ItemFunctions(int id, String name, int i)
	{
		this.id = id;
		this.name = name;
		coord = i;
	}

	private int id, coord;
	private String name;
	
	public int getID()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getCoord()
	{
		return coord;
	}
}
