package com.base.engine;

import java.awt.Rectangle;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;

public class Slider
{
	public Vector2f pos;
	private int width;
	private int max, current;
	private Rectangle slider = new Rectangle();
	private boolean isHover = false, isClicked = false;
	private Color specialColor;
	private float overlay;

	private Font font;
	private String text = "";
	private Vector2f textOffs;

	private Font font2;
	private int numR, numG, numB;

	public Slider(Vector2f pos, int maxAmount, int start, String text)
	{
		this(pos, maxAmount, start, text, maxAmount);
	}
	
	public Slider(Vector2f pos, int maxAmount, int start, String text, int maxWidth)
	{
		this.pos = pos;
		max = maxAmount;
		current = start;
		width = maxWidth + 4;
		this.text = text;
	}

	public void setText(String text, float size)
	{
		this.text = text;
		font = Render.getFont(size);
		textOffs = Vector2f.ZERO;
	}
	
	public void setText(String text, float size, Vector2f offset)
	{
		this.text = text;
		font = Render.getFont(size);
		textOffs = offset;
	}

	public void setNumberShow(float size, int r, int g, int b)
	{
		font2 = Render.getFont(size);
		numR = r;
		numG = g;
		numB = b;
	}
	
	public void setNumberColor(Color color)
	{
		numR = color.getRed();
		numG = color.getGreen();
		numB = color.getBlue();
	}

	public void setSpecialColor(boolean red, boolean green, boolean blue)
	{
		specialColor = new Color(0, 0, 0);
		if(red) specialColor.r = 255;
		if(green) specialColor.g = 255;
		if(blue) specialColor.b = 255;
	}

	public void input(float delta)
	{
		slider.setBounds((int)pos.getX() + current - 23, (int)pos.getY() - 3, 46, 46);
		if(slider.contains(Mouse.getX(), Window.getHeight() - Mouse.getY())) isHover = true;
		else isHover = false;
		if(isHover && Mouse.isButtonDown(0)) isClicked = true;
		if(isClicked)
		{
			int mouseX = Mouse.getX();
			mouseX = Util.clamp(mouseX, (int)pos.getX(), (int)pos.getX() + width);
			current = (mouseX - (int)pos.getX());
			current = Util.clamp(current, 0, max);
			slider.x = mouseX - 23;
			if(!Mouse.isButtonDown(0)) isClicked = false;
		}
		if(specialColor != null) overlay = current * 255 / 100;
	}

	public void render(Vector2f offset)
	{
		Render.pushMatrix();
		Render.drawRectangle(pos.sub(offset), new Vector2f(width, 40), 255, 255, 255);
		Render.drawRectangle(pos.sub(offset).add(2), new Vector2f(width - 4, 36), 0, 0, 0);
		
		if(font != null)
		{
			Vector2f slideBar = new Vector2f(width, 40);
			Vector2f textSize = new Vector2f(font.getWidth(text), font.getHeight(text));
			Vector2f finPos = slideBar.div(2).sub(textSize.div(2));
			Render.drawString(font, text, pos.add(finPos).add(textOffs).sub(offset), 255, 255, 255);
		}
		
		Render.drawRectangle(new Vector2f(slider.x, slider.y).sub(offset), new Vector2f(slider.width, slider.height), 255, 255, 255);
		if(isHover || isClicked) Render.drawRectangle(new Vector2f(slider.x, slider.y).sub(offset), new Vector2f(slider.width, slider.height), 105, 105, 105);
		if(isClicked) Render.drawRectangle(new Vector2f(slider.x, slider.y).sub(offset), new Vector2f(slider.width, slider.height), 0, 0, 0);
		if(specialColor == null) Render.drawRectangle(new Vector2f(slider.x + 2, slider.y + 2).sub(offset), new Vector2f(slider.width - 4, slider.height - 4), 1.0f - (float)(current / max), (float)current / 100, 0f);
		else 
		{
			if(specialColor.r > 0)
			{
				Render.drawRectangle(new Vector2f(slider.x + 2, slider.y + 2).sub(offset), new Vector2f(slider.width - 4, slider.height - 4), 255, 0, 0);
				Render.drawRectangle(new Vector2f(slider.x + 2, slider.y + 2).sub(offset), new Vector2f(slider.width - 4, slider.height - 4), 0, 0, 0, (255 - overlay) / 255);
			}
			if(specialColor.g > 0)
			{
				Render.drawRectangle(new Vector2f(slider.x + 2, slider.y + 2).sub(offset), new Vector2f(slider.width - 4, slider.height - 4), 0, 255, 0);
				Render.drawRectangle(new Vector2f(slider.x + 2, slider.y + 2).sub(offset), new Vector2f(slider.width - 4, slider.height - 4), 0, 0, 0, (255 - overlay) / 255);
			}
			if(specialColor.b > 0)
			{
				Render.drawRectangle(new Vector2f(slider.x + 2, slider.y + 2).sub(offset), new Vector2f(slider.width - 4, slider.height - 4), 0, 0, 255);
				Render.drawRectangle(new Vector2f(slider.x + 2, slider.y + 2).sub(offset), new Vector2f(slider.width - 4, slider.height - 4), 0, 0, 0, (255 - overlay) / 255);
			}
		}
		if(font2 != null)
		{
			String num = String.valueOf((int)getValue());
			Vector2f sliderVec = new Vector2f((slider.x + (slider.width / 2) - (font2.getWidth(num) / 2)) - offset.getX(), (pos.getY() + 6) - offset.getY());
			Render.drawString(font2, num, sliderVec, numR, numG, numB);
		}
		Render.popMatrix();
	}

	public boolean isClicked()
	{
		return isClicked;
	}

	public float getValue()
	{
		return Util.clamp(((current / 100f) * max), 0, max);//(current / (max / 100f));
	}

	public String getText()
	{
		return text;
	}

	public Vector2f getButton()
	{
		return new Vector2f(slider.x, slider.y);
	}

	public Vector2f getButtonSize()
	{
		return new Vector2f(slider.width, slider.height);
	}

	public Vector2f getCenter(Vector2f size)
	{
		return new Vector2f(slider.x, slider.y).add(slider.width / 2).sub(size.div(2));
	}
}
