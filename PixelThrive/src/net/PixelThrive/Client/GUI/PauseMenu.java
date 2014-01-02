package net.PixelThrive.Client.GUI;

import java.awt.Color;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.renders.Render;

public class PauseMenu extends GUI
{
	private Button menu = new Button(Main.WIDTH / 2 - 40, Main.HEIGHT / 2 + 20, 80, 20, "Main Menu", 50, 50, 50);
	private Button option = new Button(Main.WIDTH / 2 - 40, Main.HEIGHT / 2 - 10, 80, 20, "Options", 60, 60, 60);
	private Button controls = new Button(Main.WIDTH / 2 - 40, Main.HEIGHT / 2 - 40, 80, 20, "Controls", 70, 70, 70);
	
	public ControlsMenu controlMenu = new ControlsMenu(this, Main.key);
	public Options options = new Options(this);

	public void tick()
	{
		if(isActive)
		{
			menu.tick();
			option.tick();
			controls.tick();
			if(menu.isClicked()) Main.getGameInstance().backToMainMenu();
			if(controls.isClicked()) controlMenu.openGUI();
			if(option.isClicked()) options.openGUI();
		}
	}

	public void render()
	{
		if(isActive)
		{
			Render.setColor(60, 120, 200);
			Render.fillRect(Main.WIDTH / 2 - 60, Main.HEIGHT / 2 - 50, 120, 100);
			Render.setColor(Color.BLACK);
			Render.drawRect(Main.WIDTH / 2 - 60, Main.HEIGHT / 2 - 50, 120, 100);
			menu.render();
			option.render();
			controls.render();
		}
	}
}
