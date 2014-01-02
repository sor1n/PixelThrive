package net.PixelThrive.Client.GUI;

import java.awt.Rectangle;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.renders.Render;

public class Slider 
{
	private int x, y, width, offs = 0;
	private float max, current;
	private String text;
	private Rectangle pointer = new Rectangle();
	private boolean isHover = false, isClicked = false;

	public Slider(int x, int y, int width, float maxAmount, float start, String text)
	{
		this.x = x;
		this.y = y;
		this.max = maxAmount;
		this.width = width;
		current = start;
		this.text = text;
		offs = x + (int)((current / max) * width) - 4;
	}

	public void tick()
	{
		pointer.setBounds(offs + 1, y - 2, 4, 14);
		if(pointer.contains(Main.mouseX, Main.mouseY)) isHover = true;
		else isHover = false;
		if(isClicked)
		{
			offs = Main.mouseX;
			if(Main.mouseX > x + width - 4) offs = x + width - 4;
			if(Main.mouseX < x - 4) offs = x - 4;
		}
		if(Main.key != null && Main.key.isMouseLeft && isHover) isClicked = true;
		if(!Main.key.isMouseLeft) isClicked = false;
		current = (int)(offs / max) - (x - 4);
	}

	public void render()
	{
		Text.drawStringWithShadow(text, x + (width / 2) - (Render.stringWidth(text, 9) / 2), y - 4, 0xFFFFFF, 9, Main.gameFont);
		Render.setColor(90, 90, 90);
		Render.fillRect(x - 1, y, width + 2, 10);
		Text.drawStringWithShadow(String.valueOf(current).substring(0, String.valueOf(current).indexOf('.')), x + (width / 2) - (Render.stringWidth(String.valueOf(current).substring(0, String.valueOf(current).indexOf('.')), 9) / 2), y + 9, 0xFFFFFF, 9, Main.gameFont);
		Render.setColor(0x000000);
		Render.drawRect(x - 1, y, width + 2, 10);
		Render.setColor(0xFFFFFF);
		Render.fillRect(pointer.x, pointer.y, pointer.width, pointer.height);
		Render.setColor(0x000000);
		Render.drawRect(pointer.x, pointer.y, pointer.width, pointer.height);
		Render.setColor(140, 140, 140, 120);
		if(isHover) Render.fillRect(pointer.x, pointer.y, pointer.width + 1, pointer.height + 1);
	}

	public float getValue()
	{
		return current / 100;
	}
	
	public void setValue(int f)
	{
		offs = ((x - 4) - f);
		current = (int)(offs / max) - (x - 4);
		offs -= current * 2;
	}
	
	public boolean isClicked()
	{
		return isClicked;
	}
}
