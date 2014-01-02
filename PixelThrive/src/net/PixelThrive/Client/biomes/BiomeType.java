package net.PixelThrive.Client.biomes;

public enum BiomeType 
{
	SNOWY(0),
	COLD(1),
	MEDIUM(2),
	WARM(3),
	DRY(4);
	
	BiomeType(int id)
	{
		this.id = id;
	}
	
	public int getID()
	{
		return id;
	}
	
	private int id;

}
