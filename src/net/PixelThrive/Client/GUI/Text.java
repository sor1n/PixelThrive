package net.PixelThrive.Client.GUI;

import java.awt.*;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.Render.Fonts;

public class Text 
{	
	public static void drawStringWithShadow(String s, int posX, int posY, int col, int size)
	{
		Render.setFont(Fonts.DIALOG_INPUT, size);
		Render.setColor(Color.black);
		Render.drawString(s, posX - 1, posY - 1);
        if((col & -67108864) == 0)  col |= -16777216;
        float red = (float)(col >> 16 & 255) / 255.0F;
        float green = (float)(col >> 8 & 255) / 255.0F;
        float blue = (float)(col & 255) / 255.0F;
        float alpha = (float)(col >> 24 & 255) / 255.0F;
        Render.setColor(new Color(red, green, blue, alpha));
		Render.drawString(s, posX, posY);		
	}
	
	public static void drawStringWithShadow(String s, int posX, int posY, Color col, int size)
	{
		Render.setFont(Fonts.DIALOG_INPUT, size);
		Render.setColor(Color.black);
		Render.drawString(s, posX - 1, posY - 1);
        Render.setColor(col);
		Render.drawString(s, posX, posY);		
	}
	
	public static void drawStringWithShadow(String s, int posX, int posY, Color col, int size, Fonts font)
	{
		Render.setFont(font, size);
		Render.setColor(Color.black);
		Render.drawString(s, posX - 1, posY - 1);
		Render.setColor(col);
		Render.drawString(s, posX, posY);		
	}
	
	public static void drawStringWithShadow(String s, int posX, int posY, Color col, int size, Font font)
	{
		Render.setFont(font, size);
		Render.setColor(Color.black);
		Render.drawString(s, posX - 1, posY - 1);
		Render.setColor(col);
		Render.drawString(s, posX, posY);		
	}
	
	public static void drawStringWithShadow(String s, int posX, int posY, int col, int size, Font font)
	{
		Render.setFont(font, size);
		Render.setColor(Color.black);
		Render.drawString(s, posX - 1, posY - 1);
		Render.setColor(col);
		Render.drawString(s, posX, posY);		
	}
	
	public static void drawStringWithShadow(Graphics g, String s, int posX, int posY, Color col, int size, Font font)
	{
		g.setFont(new Font(font.getFontName(), Font.PLAIN, size));
		g.setColor(Color.black);
		g.drawString(s, posX - 1, posY - 1);
		g.setColor(col);
		g.drawString(s, posX, posY);		
	}
	
	public static void drawStringWithShadowAndFade(String s, int posX, int posY, Color col, int size, Font font, int fade)
	{
		Render.setFont(font, size);
		Render.setColor(new Color(0, 0, 0, fade));
		Render.drawString(s, posX - 1, posY - 1);
		Render.setColor(col);
		Render.drawString(s, posX, posY);		
	}
	
	/** 
	 * @param s The text
	 * @param posX Where to draw the string on the X axis
	 * @param posY Where to draw the string on the Y axis
	 * @param col The color of the string, can be determined 2 ways, first by transforming a hex code into decimal or 0x then the hex code which can be 2 types: a) rgb (numbers can go up to ff and have to be one after another) and b) arbg (where a stands for alpha) 
	 * @param g The graphics
	 */
    public static void drawString(String s, int posX, int posY, int col, Graphics g)
    {
        if((col & -67108864) == 0)
        {
            col |= -16777216;
        }
        float red = (float)(col >> 16 & 255) / 255.0F;
        float green = (float)(col >> 8 & 255) / 255.0F;
        float blue = (float)(col & 255) / 255.0F;
        float alpha = (float)(col >> 24 & 255) / 255.0F;
        g.setColor(new Color(red, green, blue, alpha));
    	g.drawString(s, posX, posY);
    }

}
