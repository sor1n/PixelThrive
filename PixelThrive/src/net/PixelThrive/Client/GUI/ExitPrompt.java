package net.PixelThrive.Client.GUI;

import java.awt.Color;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.renders.Render;

public class ExitPrompt
{
	private Button no = new Button(Main.WIDTH / 2 - 40, Main.HEIGHT / 2, 80, 20, "No", 120, 40, 30);
	private Button yes = new Button(Main.WIDTH / 2 - 40, Main.HEIGHT / 2 - 30, 80, 20, "Yes", 90, 60, 10);
	private int delay = 10;
	
	private GUI gui;
	
	public ExitPrompt(GUI gui)
	{
		this.gui = gui;
	}
	
	public void tick()
	{
		if(delay > 0) delay--;
		yes.tick();
		no.tick();
		if(yes.isClicked() && delay <= 0) Main.quit();
		if(no.isClicked() && delay <= 0) ((MainMenu)gui).exitPrompt = null;
	}
	
	public void render()
	{
		Render.setColor(0, 0, 0, 170);
		Render.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		Render.setColor(60, 120, 200);
		Render.fillRect(Main.WIDTH / 2 - 60, Main.HEIGHT / 2 - 50, 120, 80);
		Render.setColor(Color.BLACK);
		Render.drawRect(Main.WIDTH / 2 - 60, Main.HEIGHT / 2 - 50, 120, 80);
		yes.render();
		no.render();
		Text.drawStringWithShadow("Are you sure?", Main.WIDTH / 2 - (Render.stringWidth("Are you sure?", 11) / 2), Main.HEIGHT / 2 - 38, 0xFFFFFF, 11, Main.gameFont);
	}
}
