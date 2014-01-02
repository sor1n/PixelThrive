package net.PixelThrive.Client.GUI;

import java.awt.*;
import java.awt.event.*;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.renders.Render;

public class TextInput
{
	public int x, y;
	public String text = "";
	private int maxLength, fontSize;
	public KeyEvent e;
	public String command = "";

	public boolean isDone = false, isReset = false, isActive = true;
	public boolean numOnly, shadowedText, withDots, letterOnly = false;
	public Rectangle bounds = new Rectangle();
	private int delay = 0;
	
	public TextInput(int maxL, int x, int y, int fontSize, boolean numOnly, boolean isIP)
	{
		this(maxL, x, y, fontSize, numOnly, isIP, true);
	}
	
	public TextInput(int maxL, int x, int y, int fontSize, boolean numOnly, boolean isIP, boolean shadowedText)
	{
		this.shadowedText = shadowedText;
		this.numOnly = numOnly;
		this.x = x;
		this.y = y;
		this.fontSize = fontSize;
		maxLength = maxL;
		withDots = isIP;
		Main.textInputs.add(this);
		isReset = false;
		String txt = "";
		isActive = true;
		for(int i = 0; i < maxL; i++) txt += "O";
		bounds.setBounds(x, y, Render.stringWidth(txt, fontSize), 8);
	}
	
	public void closeText()
	{
		Main.textInputs.remove(this);
	}
	
	public void resetAndClose()
	{
		setTextNull();
		isReset = true;
		closeText();
		isActive = false;
	}

	public void addText(String txt)
	{
		if(text.length() < maxLength) text += txt;
	}

	public void setText(String txt)
	{
		if(txt.length() < maxLength) text = txt;
	}
	
	public void setTextWithNoLimit(String txt)
	{
		text = txt;
	}

	public void setCommand(String ctxt)
	{
		command = ctxt;
	}

	public String getCommand()
	{
		return command;
	}

	public void setTextNull()
	{
		text = "";
	}

	public void removeKey()
	{
		if(text.length() > 0) text = text.substring(0, text.length() - 1);
	}

	public String getText()
	{
		return text;
	}
	
	public void tick()
	{
		if(delay <= 0) delay = 50;
		else delay--;
	}
	
	public void render()
	{
		Render.setFont(Main.gameFont, fontSize);
		Render.setColor(Color.white);
		if(!shadowedText) Render.drawString(getText(), x, y + 9);
		else Text.drawStringWithShadow(getText(), x, y + 9, Color.WHITE, fontSize, Main.gameFont);
		if(isActive && delay > 25) Text.drawStringWithShadow("|", x + Render.stringWidth(getText(), fontSize), y + 8, 0xFFFFFF, fontSize, Main.gameFont);
	}

	public int getMaxLength()
	{
		return maxLength;
	}
}