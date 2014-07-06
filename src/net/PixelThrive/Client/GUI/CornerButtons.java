package net.PixelThrive.Client.GUI;

import java.awt.Color;
import java.awt.Rectangle;

import net.PixelThrive.Client.*;
import net.PixelThrive.Client.Input.Key;
import net.PixelThrive.Client.commands.Message;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;

public class CornerButtons extends GUI
{
	private boolean isSelected = false, isClicked = false, isSelected2 = false, isClicked2 = false;
	public Rectangle help = new Rectangle(2, 2, 10, 10), pause = new Rectangle(14, 2, 10, 10);

	private int keyDelay = 0;
	public boolean isHelpMode = false;
	public PauseMenu pauseMenu = new PauseMenu();
	public boolean isPaused = false, escToggle = false;

	public void tick()
	{
		if(keyDelay > 0) keyDelay--;

		if(Main.inv != null && !Main.inv.isHolding)
		{
			if(help.contains(Main.mouseX, Main.mouseY)) isSelected = true;
			else isSelected = false;
			if(pause.contains(Main.mouseX, Main.mouseY)) isSelected2 = true;
			else isSelected2 = false;
			if(isSelected2 && Main.key.isMouseLeft && keyDelay <= 0)
			{
				if(!isClicked2) isClicked2 = true;
				else isClicked2 = false;
				keyDelay = 8;
			}
			if(Main.key != null && Key.menuKey.isPressed() && !pauseMenu.controlMenu.isOpen && keyDelay <= 0)
			{
				escToggle = !escToggle;
				keyDelay = 13;
			}
			if(isClicked2 || escToggle) isPaused = true;
			else isPaused = false;

			if(isSelected && !isPaused && Main.key.isMouseLeft && keyDelay <= 0)
			{
				if(!isClicked) isClicked = true;
				else isClicked = false;
				keyDelay = 8;
			}
		}
		if(isPaused) pauseMenu.tick();
		isHelpMode = isClicked;
		if(Main.key.isMouseLeft && isHelpMode && keyDelay == 0) isClicked = false;
	}

	public void render()
	{
		Render.setColor(Color.gray);
		Render.fillRoundRect(2, 2, 10, 10, 5, 5);
		Render.setColor(Color.gray.darker());
		Render.drawRoundRect(3, 3, 8, 8, 4, 4);
		Render.setColor(Color.darkGray);
		Render.drawRoundRect(2, 2, 10, 10, 5, 5);
		Render.setColor(Color.WHITE);
		Render.setFont(Main.gameFont, 10);
		Render.drawString("?", 5, 11);
		if(isSelected)
		{		
			Render.setColor(new Color(80, 80, 80, 120));
			Render.fillRoundRect(2, 2, 10, 10, 5, 5);
		}
		if(isClicked)
		{
			Render.setColor(new Color(150, 150, 150, 120));
			Render.fillRoundRect(2, 2, 10, 10, 5, 5);
		}
		Render.setColor(Color.gray);
		Render.fillRoundRect(14, 2, 10, 10, 5, 5);
		Render.setColor(Color.gray.darker());
		Render.drawRoundRect(15, 3, 8, 8, 4, 4);
		Render.setColor(Color.darkGray);
		Render.drawRoundRect(14, 2, 10, 10, 5, 5);
		Render.setColor(Color.WHITE);
		Render.setFont(Main.gameFont, 4);
		Render.drawString("II", 18, 9);
		if(isSelected2)
		{		
			Render.setColor(new Color(80, 80, 80, 120));
			Render.fillRoundRect(14, 2, 10, 10, 5, 5);
		}
		if(isClicked2)
		{
			Render.setColor(new Color(150, 150, 150, 120));
			Render.fillRoundRect(14, 2, 10, 10, 5, 5);
		}
		if(isClicked) Text.drawStringWithShadow("?", Main.mouseX + 9, Main.mouseY + 16, 0xff0000, 12, Main.gameFont);
		if(Main.key != null && Key.turnKey.isPressed()) Render.drawImage(SpriteSheet.getIcon(SpriteSheet.GUI, 11, 0), Main.WIDTH - 34, 15);
		if(Main.key != null && Key.backGroundKey.isPressed()) Render.drawImage(SpriteSheet.getIcon(SpriteSheet.GUI, 12, 0), Main.WIDTH - 49, 15);
	}

	public void newHelp(String mess, String desc)
	{
		Message.newMessage(mess.substring(0, 1).toUpperCase() + mess.substring(1) + " - " + desc);
		isClicked = false;
	}
}
