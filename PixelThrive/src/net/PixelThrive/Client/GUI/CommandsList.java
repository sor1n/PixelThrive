package net.PixelThrive.Client.GUI;

import java.awt.Color;

import net.PixelThrive.Client.Input.Key;
import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.GUI.Button;
import net.PixelThrive.Client.GUI.GUI;
import net.PixelThrive.Client.GUI.Text;
import net.PixelThrive.Client.commands.Command;
import net.PixelThrive.Client.renders.GuiIcons;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.world.Tile;

public class CommandsList extends GUI
{
	public boolean isOpen = false;
	public int x, y, page, maxPages, page1, maxPages1;
	private static int keyDelay = 0, keyDelay1 = 0;
	public Button next, back, next1, back1;
	public Button[] coms = new Button[Command.commands.size()];
	public int[] click = new int[Command.commands.size()];

	public CommandsList()
	{	
		x = 5;
		y = Main.HEIGHT - 99;		
		next = new Button(x + 80, y - y/2 - 10, 16, 16, x + 80, (int)y - y/2 - 10, x + Tile.tileSize + 80, y - y/2 - 10 + Tile.tileSize, GuiIcons.next, GuiIcons.nextS);
		back = new Button(x + 80, y - y/2 + 90, 16, 16, x + 80, y - y/2 + 90, x + Tile.tileSize + 80, y - y/2 + 90 + Tile.tileSize, GuiIcons.back, GuiIcons.backS);
		next1 = new Button(x + 250, y - y/2 - 10, 16, 16, x + 250, (int)y - y/2 - 10, x + Tile.tileSize + 250, y - y/2 - 10 + Tile.tileSize, GuiIcons.next, GuiIcons.nextS);
		back1 = new Button(x + 250, y - y/2 + 90, 16, 16, x + 250, y - y/2 + 90, x + Tile.tileSize + 250, y - y/2 + 90 + Tile.tileSize, GuiIcons.back, GuiIcons.backS);
	}

	public void tick() 
	{
		for(int i = 0; i < 9; i++)
			if(i + page*9 < Command.commands.size()) 
				coms[i + 9*page] = new Button(x + 5, y - y/2 + i*12 - 5, 70, 11, Command.commands.get(i + 9*page).name, 0, 0, 0, 9, false);

		for(int i = 0; i < 9; i++)
			if(i + page*9 < Command.commands.size())  
				coms[i + 9*page].tick();

		next.tick();
		back.tick();
		next1.tick();
		back1.tick();
		maxPages = Command.commands.size()/11;
		for(int i = 0; i < Command.commands.size(); i++)
			if(click[i] == 1)
				maxPages1 = 1 + (Command.commands.get(i).description.size() - 1)/9;
		if((next.isClicked() || Key.nextPage.isPressed()) && page < maxPages && keyDelay == 0){
			page++;
			keyDelay = 20;
		}
		if((back.isClicked() || Key.previousPage.isPressed()) && page > 0 && keyDelay == 0){
			page--;
			keyDelay = 20;
		}
		if(next1.isClicked() && page1 < maxPages1 && keyDelay1 == 0){
			page1++;
			keyDelay1 = 20;
		}
		if(back1.isClicked() && page1 > 0 && keyDelay1 == 0){
			page1--;
			keyDelay1 = 20;
		}
		if(page > maxPages) page = maxPages;
		if(page1 > maxPages1) page1 = maxPages1;

		for(int i = 0; i < Command.commands.size(); i++) 
			if(coms[i].isClicked() && keyDelay == 0)
			{ 
				keyDelay = 20;
				if(click[i] == 0) click[i] = 1;
				else click[i] = 0;
				page1 = 0;
				for(int j = 0; j < Command.commands.size(); j++) if(i != j) click[j] = 0;
			}

	}

	public void triggerTick()
	{
		if(keyDelay > 0) keyDelay--;
		if(keyDelay1 > 0) keyDelay1--;
		if(Main.key != null && Key.commandsList.isPressed() && keyDelay <= 0 && !Main.console.showChat)
		{
			Main.inv.isOpen = false;
			Main.skl.closeGUI();
			Main.shop.closeGUI();
			keyDelay = 20;
			if(isOpen) closeGUI();
			else openGUI();
		}
	}

	public static void delay()
	{
		keyDelay = 10;
	}

	public void render() 
	{
		if(Main.player != null && isOpen)
		{
			Render.setColor(new Color(20, 20, 20, 200));
			Render.fillRect(x + 3, y - y/2 - 10, 100, 120);		
			Render.fillRect(x + 100, y - y/2 - 10, 175, 120);
			Render.setColor(new Color(200, 20, 20, 250));
			Render.fillRect(x + 100, y - y/2 - 10, 3, 120);	
			for(int i = 0; i < Command.commands.size(); i++)
				if(click[i] == 1){
					Render.setColor(new Color(150, 250, 50, 250));
					Render.drawRect(x + 5, y - y/2 + i*12 - 5, 70, 11);
					Text.drawStringWithShadow(Command.commands.get(i).name, (int)x - (Render.stringWidth(Command.commands.get(i).name, 10) / 2) + 190, (int)y - y/2, new Color(0x209920), 10, Main.gameFont);
					if(page1 == 0) 
					{
						Text.drawStringWithShadow("Commands:", (int)x + 107, (int)y - y/2 + 10, new Color(0xff0030), 10, Main.gameFont);
						for(int m = 0; m < Command.commands.get(i).command.length; m++)
							Text.drawStringWithShadow(Command.commands.get(i).command[m] + " ", (int)x + 107, (int)y - y/2 + 20 + 10*m, new Color(0xffffff), 10, Main.gameFont);
					}					
					else
					{
						Text.drawStringWithShadow("Description:", (int)x + 107, (int)y - y/2 + 10, new Color(0xff0030), 10, Main.gameFont);
						if(Command.commands.get(i).description != null) 
							for(int m = 0; m < 9; m++)
								if(m + 9*(page1 - 1) < Command.commands.get(i).description.size()){
									if(!Command.commands.get(i).description.get(m + 9*(page1 - 1)).contains("[command]") && !Command.commands.get(i).description.get(m + 9*(page1 - 1)).contains("/"))
										Text.drawStringWithShadow(Command.commands.get(i).description.get(m + 9*(page1 - 1)), (int)x + 107, (int)y - y/2 + 20 + m*10, new Color(0xffffff), 10, Main.gameFont);
									else Text.drawStringWithShadow(Command.commands.get(i).description.get(m + 9*(page1 - 1)), (int)x + 107, (int)y - y/2 + 20 + m*10, new Color(0x00ff00), 10, Main.gameFont);
								}

					}
//					Text.drawStringWithShadow(String.valueOf(page1) + "/" + String.valueOf(maxPages1), (int)x + 250, (int)y - y/2 + 50, Color.WHITE, 10, Main.gameFont);
					next1.render();
					back1.render();
				}
			Text.drawStringWithShadow(String.valueOf(page) + "/" + String.valueOf(maxPages), (int)x + 80, (int)y - y/2 + 50, Color.WHITE, 8, Main.gameFont);
			next.render();
			back.render();
			for(int i = 0; i < 9; i++)
				if(i + page*9 < Command.commands.size())  
					coms[i + 9*page].render();
		}		
	}

	public void openGUI()
	{
		super.openGUI();
		isOpen = true;
	}

	public void closeGUI()
	{
		super.closeGUI();
		isOpen = false;
	}
}
