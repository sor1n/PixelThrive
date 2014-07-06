package com.base.engine;

import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

@SuppressWarnings("deprecation")
public class Font extends TrueTypeFont
{
	public Font(java.awt.Font font, boolean antiAlias)
	{
		super(font, antiAlias);
	}
	
	@Override
	public void drawString(float x, float y, String whatchars, Color color)
	{
		Render.setTextures(true);
		super.drawString(x, y, whatchars, color);
		Render.setTextures(false);
	}
	
	public void drawString(Vector2f pos, String txt, Color color)
	{
		drawString(pos.getX(), pos.getY(), txt, color);
	}
}
