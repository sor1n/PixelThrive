package net.PixelThrive.Client.skills;

import java.awt.Color;

import net.PixelThrive.Client.Input.Key;
import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.GUI.Button;
import net.PixelThrive.Client.GUI.GUI;
import net.PixelThrive.Client.GUI.Text;
import net.PixelThrive.Client.renders.GuiIcons;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.world.Tile;

public class SkillsGUI extends GUI
{
	public boolean isOpen = false;
	public int x, y, page, maxPages;
	private static int keyDelay = 0;
	public Button next, back;
	public SkillButton[] skills = new SkillButton[Skill.skills.size()];

	public SkillsGUI()
	{	
		x = 5;
		y = Main.HEIGHT - 99;
		
		next = new Button(x + 159, y + 1, 16, 16, x + 159, (int)y, x + Tile.tileSize + 159, y + Tile.tileSize, GuiIcons.next, GuiIcons.nextS);
		back = new Button(x + 159, y + Tile.tileSize*4 + 1, 16, 16, x + 159, y + Tile.tileSize*3 + 20, x + Tile.tileSize + 159, y + Tile.tileSize*4 + 20, GuiIcons.back, GuiIcons.backS);
	}

	public void tick() 
	{
		next.tick();
		back.tick();
		maxPages = skills.length/40;
		if((next.isClicked() || Key.nextPage.isPressed()) && page < maxPages) page++;
		if((back.isClicked() || Key.previousPage.isPressed()) && page > 0) page--;
		if(page > maxPages) page = maxPages;
		for(int i = 0; i < 40; i++)
			if(i < (Skill.skills.size() - 40*page) && Main.player.skills.get(i + 40*page) == 0 && skills[i + 40*page] == null)
				skills[i + 40*page] = new SkillButton(Skill.skills.get(i + 40*page).getSkillID(), x + i*(Tile.tileSize + 3) - 8*(i/8)*(Tile.tileSize + 3), y + (i/8)*(Tile.tileSize + 3), Tile.tileSize, Tile.tileSize, Skill.skills.get(i + 40*page).getExperienceCost());
		for(int i = 0; i < 40; i++)
			if(i < (Skill.skills.size() - 40*page))
				skills[i + 40*page].tick();
	}

	public void triggerTick()
	{
		if(keyDelay > 0) keyDelay--;
		if(Main.key != null && Key.skillKey.isPressed() && keyDelay <= 0 && !Main.console.showChat)
		{
			Main.inv.isOpen = false;
			Main.com.closeGUI();
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
			Render.setColor(new Color(20, 20, 20, 150));
			Render.fillRect(x - 3, y - 3, 180, 100);
			for(int i = 0; i < 40; i++) 
				if(i < (Skill.skills.size() - 40*page))
					skills[i + 40*page].render();	
			Text.drawStringWithShadow(String.valueOf(page) + "/" + String.valueOf(maxPages), (int)x + 159, (int)y + Tile.tileSize*3, Color.WHITE, 8, Main.gameFont);
			next.render();
			back.render();		
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
