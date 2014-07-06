package net.PixelThrive.Client.GUI;

import net.PixelThrive.Client.*;
import net.PixelThrive.Client.renders.*;

public class EXPCircle
{
	private int x, y, fontSize = 10, glow = 0;
	private static int width = 20;
	public int fill = 50, level = 0, progress = 0;
	private boolean next = false;

	public EXPCircle(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public void tick()
	{
		if(glow > 0) glow -= 10;
		if(glow < 0) glow = 0;
		if(Main.player != null)
		{
			level = Main.player.getExp() / 1000;
			progress = Main.player.getExp() % 1000;
		}
		if(next)
		{
			glow = 255;
			next = false;
		}
		if(level >= 0 && level <= 9) fontSize = 10;
		if(level > 10 && level <= 99) fontSize = 9;
		if(level > 99 && level <= 999) fontSize = 8;
		if(level > 999 && level <= 9999) fontSize = 7;
		if(level > 9000) level = -1;
	}

	public void render()
	{
		Render.setColor(0xAFAFAF);
		Render.fillRect(x, y, width, width);
		Render.setColor(0x00AA00);
		Render.fillRect(x, y, progress / 50, width);
		if(glow == 0) Render.setColor(0x000000);
		else if(glow > 0 && glow < 255) Render.setColor(glow, glow, glow);
		Render.drawRect(x, y, width, width);
		Render.drawLine(x + (width / 2), y, x + (width / 2), y + width);
		Render.setColor(0xAAAAFF);
		Render.fillRect(x + 2, y + 2, width - 4, width - 4);
		Render.setColor(0x000000);
		Render.drawRect(x + 2, y + 2, width - 4, width - 4);
		if(level >= 0) Text.drawStringWithShadow(String.valueOf(level), x + (getWidth() / 2) - (Render.stringWidth(String.valueOf(level), fontSize) / 2) + 1, y + 14, 0xFFFFFF, fontSize, Main.gameFont);
		else
		{
			Text.drawStringWithShadow("Over", x + (getWidth() / 2) - (Render.stringWidth("Over", 7) / 2) + 1, y + 9, 0xFFFFFF, 7, Main.gameFont);
			Text.drawStringWithShadow("9000", x + (getWidth() / 2) - (Render.stringWidth("9000", 7) / 2) + 1, y + 16, 0xFFFFFF, 7, Main.gameFont);
		}
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	public static int getWidth()
	{
		return width;
	}
	
	public void glow()
	{
		next = true;
	}
}
