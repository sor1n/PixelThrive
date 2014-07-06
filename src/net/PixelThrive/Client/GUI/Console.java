package net.PixelThrive.Client.GUI;

import java.awt.Color;
import net.PixelThrive.Client.*;
import net.PixelThrive.Client.Input.Key;
import net.PixelThrive.Client.commands.Message;
import net.PixelThrive.Client.skills.SkillsGUI;

public class Console extends GUI 
{
	private TextInput chat;
	public boolean showChat = false;
	private int chatDelay = 20, messageDelay = 0;

	public Console()
	{
		openGUI();
	}

	public void tick()
	{
		if(messageDelay > 0) messageDelay--;
		if(chatDelay > 0) chatDelay--;

		if(Main.key != null && chatDelay <= 0 && Main.player != null && Key.commandKey.isPressed() && !showChat && !Main.inv.isOpen && !Main.skl.isOpen && !Main.inv.buttons.isPaused)
		{
			showChat = true;
			chatDelay = 20;
			chat = new TextInput(35, 12, Main.HEIGHT - 29, 10, false, true, true);
			chat.addText("/");
		}
		
		if(Main.key != null && (Key.chatKey.isPressed() || Key.menuKey.isPressed()) && Main.player != null && !Main.inv.isOpen && !Main.skl.isOpen && !Main.inv.buttons.isPaused)
		{
			if(chatDelay <= 0)
			{
				if(showChat)
				{
					chatDelay = 20;
					SkillsGUI.delay();
					if(Key.chatKey.isPressed() && chat != null && !chat.getText().trim().equalsIgnoreCase("")) new Message(chat.getText(), 12, Main.HEIGHT - 33, Main.player);
					closeChat();
				}
				else if(Key.chatKey.isPressed())
				{
					chatDelay = 20;
					showChat = true;
				}
			}
		}
	}

	public void closeChat()
	{
		showChat = false;
		clearConsole();
		if(chat != null) chat.closeText();
		chat = null;
	}

	public void clearConsole()
	{
		if(chat != null) chat.setTextNull();
	}

	public void render()
	{
		if(showChat)
		{
			Text.drawStringWithShadow(">", 6, Main.HEIGHT - 20, Color.WHITE, 10, Main.gameFont);
			if(chat == null) chat = new TextInput(35, 12, Main.HEIGHT - 29, 10, false, true, true);
		}
	}
}
