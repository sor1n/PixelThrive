package net.PixelThrive.Client.GUI;

import net.PixelThrive.Client.*;

public abstract class GUI 
{
	public static boolean hideGUI = false;
	public static final int guiX = 27, guiY = 2;
	
	public boolean isActive = true;
	
	private static boolean isGUIOpen = false;
	private static IGUIBlock currentGUI;
	public static int GUIBounds = 0;
	public boolean isOpen = false;
	public boolean canBeHidden = true;
	
	public abstract void tick();
	public abstract void render();
	
	public void openGUI()
	{
		Main.getGUIs().add(this);
		isOpen = true;
	}
	
	public void activate()
	{
		Main.getActiveGUIs().add(this);
	}
	
	public void deactivate()
	{
		Main.getActiveGUIs().remove(this);
	}
	
	public void closeGUI()
	{
		Main.getGUIs().remove(this);
		isOpen = false;
	}
	
	public static void setCurrentGUI(IGUIBlock g, int bounds)
	{
		currentGUI = g;
		isGUIOpen = true;
		GUIBounds = bounds;
	}
	
	public static boolean isGUIOpen()
	{
		return isGUIOpen;
	}
	
	public static IGUIBlock getCurrentGUI()
	{
		if(isGUIOpen) return currentGUI;
		else return null;
	}
	
	public static void clearCurrentGUI()
	{
		isGUIOpen = false;
		currentGUI = null;
		GUIBounds = 0;
	}
	
	public void triggerTick()
	{
	}
}
