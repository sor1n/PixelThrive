package com.base.engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Util
{
	public static String toHex(int r, int g, int b) 
	{
		return "#" + toBrowserHexValue(r) + toBrowserHexValue(g) + toBrowserHexValue(b);
	}
	
	public String getHex(int r, int g, int b)
	{
	    return toHex(r, g, b);
	  }

	private static String toBrowserHexValue(int number)
	{
		StringBuilder builder = new StringBuilder(Integer.toHexString(number & 0xff));
		while (builder.length() < 2) builder.append("0");
		return builder.toString().toUpperCase();
	}
	
	public static void captureScreenshot()
	{
		GL11.glReadBuffer(GL11.GL_FRONT);
		int width = Display.getDisplayMode().getWidth();
		int height= Display.getDisplayMode().getHeight();
		int bpp = 4; // Assuming a 32-bit display with a byte each for red, green, blue, and alpha.
		ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bpp);
		GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
		new File("C:/Users/" + System.getProperty("user.name") + "/PixelBolt/").mkdir();	
		new File("C:/Users/" + System.getProperty("user.name") + "/PixelBolt/" + Window.getTitle() + "/").mkdir();	
		File file = new File("C:/Users/" + System.getProperty("user.name") + "/PixelBolt/" + Window.getTitle() + "/" + Window.getTitle() + " - " + Time.getDate() + ".png"); // The file to save to.
		try
		{
			file.createNewFile();
		}
		catch(IOException e1) {e1.printStackTrace();}
		String format = "PNG"; // Example: "PNG" or "JPG"
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++)
			{
				int i = (x + (width * y)) * bpp;
				int r = buffer.get(i) & 0xFF;
				int g = buffer.get(i + 1) & 0xFF;
				int b = buffer.get(i + 2) & 0xFF;
				image.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
			}
		try
		{
			ImageIO.write(image, format, file);
		} 
		catch(IOException e) {e.printStackTrace();}
	}
	
	public static int clamp(int a, int min, int max)
	{
		if(a > max) a = max;
		if(a < min) a = min;
		return a;
	}
	
	public static float clamp(float a, float min, float max)
	{
		if(a > max) a = max;
		if(a < min) a = min;
		return a;
	}

	public static float getDifference(float a, float b) 
	{
		if(a > b) return a - b;
		else if(a < b) return b - a;
		else return 0;
	}
}
