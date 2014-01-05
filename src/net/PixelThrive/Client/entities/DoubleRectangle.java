package net.PixelThrive.Client.entities;

public class DoubleRectangle 
{
    /**
     * @param height Gives the height of entity, 20 is 1 block, 40 is 2 blocks
     * @param width Gives the width of the block
     */
	protected double x, y, width, height;
	
	public DoubleRectangle()
	{
		setBounds(0, 0, 0, 0);
	}
	
	public DoubleRectangle(double x, double y, double width, double height)
	{
		setBounds(x, y, width, height);
	}
	
	public void setBounds(double x, double y, double width, double height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public double getX()
	{
		return x;
	}
	
	public double getY()
	{
		return y;
	}
	
	public double getWidth()
	{
		return width;
	}
	
	public double getHeight()
	{
		return height;
	}
}

