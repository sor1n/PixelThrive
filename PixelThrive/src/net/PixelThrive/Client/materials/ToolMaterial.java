package net.PixelThrive.Client.materials;

public enum ToolMaterial 
{
	WOOD(93, 0),
	STONE(77, 1),
	CURSOR(10, 10),
	IRON(60, 2),
	DIAMOND(12, 10);
	
	ToolMaterial(int str, int pow)
	{
		this.setStrength(str);
		this.setPower(pow);
	}
	
	public int getStrength() 
	{
		return strength;
	}

	public void setStrength(int strength) 
	{
		this.strength = strength;
	}

	public int getPower() 
	{
		return power;
	}

	public void setPower(int power) 
	{
		this.power = power;
	}

	private int strength, power;
}
