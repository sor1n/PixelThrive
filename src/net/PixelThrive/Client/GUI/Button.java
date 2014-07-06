package net.PixelThrive.Client.GUI;

import java.awt.*;
import java.awt.geom.AffineTransform;

import net.PixelThrive.Client.*;
import net.PixelThrive.Client.audio.SoundSystem;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.Tile;

public class Button
{
	public final Rectangle button = new Rectangle();

	private boolean isClicked, isHoverOver, rotate90, hasColor = true;
	private String text;
	private int fontSize, r, g, b, posX, posY, maxPosX, maxPosY; 
	private int[] icon, iconS;
	AffineTransform at;

	public Button(int x, int y, int width, int height)
	{
		this(x, y, width, height, "", 99, 99, 99, 12);
	}

	public Button(int x, int y, int width, int height, String text, int r, int g, int b)
	{
		this(x, y, width, height, text, r, g, b, 12);
	}

	public Button(int x, int y, int width, int height, String text, int r, int g, int b, int fontSize)
	{
		button.setBounds(x, y, width, height);
		this.text = text;
		this.r = r;
		this.g = g;
		this.b = b;
		this.fontSize = fontSize;
		if(text.equalsIgnoreCase("%infinite%"))
		{
			this.text = "8";
			rotate90 = true;
		}
		else rotate90 = false;
	}

	public Button(int x, int y, int width, int height, String text, int r, int g, int b, int fontSize, boolean h)
	{
		button.setBounds(x, y, width, height);
		this.text = text;
		this.r = r;
		this.g = g;
		this.b = b;
		this.fontSize = fontSize;
		if(text.equalsIgnoreCase("%infinite%"))
		{
			this.text = "8";
			rotate90 = true;
		}
		else rotate90 = false;
		this.hasColor = h;
	}

	public Button(int x, int y, int width, int height, int posX, int posY, int maxPosX, int maxPosY, int[] icon, int[] iconS)
	{
		button.setBounds(x, y, width, height);
		this.icon = icon;
		this.iconS = iconS;
		this.posX = posX;
		this.posY = posY;
		this.maxPosX = maxPosX;
		this.maxPosY = maxPosY;
	}

	public void setXY(int x, int y)
	{
		button.setBounds(x, y, button.width, button.height);
	}

	public void tick()
	{
		if(button.contains((new Point(Main.mse.x / Main.SCALE, Main.mse.y / Main.SCALE))))
		{
			isHoverOver = true;
			if(Main.key.isMouseLeft)
			{
				SoundSystem.playSound("click", false);
				isClicked = true;
			}
			else isClicked = false;
		}
		else isClicked = isHoverOver = false;
	}

	public void render()
	{
		if(icon == null && iconS == null)
		{
			if(hasColor){
				Render.setColor(new Color(r, g, b));
				Render.fillRect(button.x, button.y, button.width, button.height);
				if(isHoverOver)
				{
					Render.setColor(new Color(r + 50, g + 50, b + 50, 100));
					Render.fillRect(button.x, button.y, button.width, button.height);
				}
				Render.setColor(0x000000);
				Render.drawRect(button.x, button.y, button.width, button.height);
			}
		}
		else
		{
			if(!isHoverOver) Render.drawImage(SpriteSheet.GUI.getImage(), posX, posY, maxPosX, maxPosY, icon[0] * Tile.tileSize, icon[1] * Tile.tileSize, icon[0] * Tile.tileSize + Tile.tileSize, icon[1] * Tile.tileSize + Tile.tileSize);
			else Render.drawImage(SpriteSheet.GUI.getImage(), posX, posY, maxPosX, maxPosY, iconS[0] * Tile.tileSize, iconS[1] * Tile.tileSize, iconS[0] * Tile.tileSize + Tile.tileSize, iconS[1] * Tile.tileSize + Tile.tileSize);
		}
		if(text != null)
		{
			if(!rotate90) Text.drawStringWithShadow(text, (button.x + 1) + (button.width / 2) - (Render.stringWidth(text, fontSize) / 2), button.y + (button.height / 2 + 4), Color.WHITE, fontSize, Main.gameFont);
			else Render.drawRotatedString(text, 90, (button.x + 1) + (button.width / 2) - (Render.stringWidth(text, fontSize) / 2) - 2, button.y + (button.height / 2 + 4) - 8);
		}
	}

	public void setText(String str)
	{
		text = str;
	}

	public boolean isClicked()
	{
		return isClicked;
	}

	public void reset()
	{
		isClicked = false;
	}
}
