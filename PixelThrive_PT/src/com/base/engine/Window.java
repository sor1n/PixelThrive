package com.base.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Window 
{
	public static void createWindow(int width, int height, String title)
	{
		setTitle(title);
		try 
		{
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
			Keyboard.create();
			Mouse.create();
		} 
		catch(LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void setTitle(String title)
	{
		Display.setTitle(title);
	}
	
	public static void render()
	{
		Display.update();
	}
	
	public static void dispose()
	{
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}
	
	public static boolean isCloseRequested()
	{
		return Display.isCloseRequested();
	}
	
	public static Vector2f getSize()
	{
		return new Vector2f(Display.getWidth(), Display.getHeight());
	}
	
	public static int getWidth()
	{
		return Display.getWidth();
	}
	
	public static int getHeight()
	{
		return Display.getHeight();
	}
	
	public static String getTitle()
	{
		return Display.getTitle().substring(0, Display.getTitle().indexOf('-'));
	}
	
	public static void sleep(long millis)
	{
		try
		{
			Thread.sleep(millis);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
	}

	public static float getAspectRatio() 
	{
		return (float)getWidth() / (float)getHeight();
	}
	
	public static Vector2f getCenter()
	{
		return new Vector2f(getWidth() / 2, getHeight() / 2);
	}
	
	public static Vector2f getAbsoluteCenter(Vector2f size)
	{
		return getCenter().sub(size.div(2));
	}
}
