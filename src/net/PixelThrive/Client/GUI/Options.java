package net.PixelThrive.Client.GUI;

import java.awt.Color;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.audio.*;
import net.PixelThrive.Client.config.SaveOptions;

public class Options extends GUI
{
	/**
	 * ReadOnly; Modifying this doesn't affect anything.
	 */
	public static float SOUND_VOLUME = 1.0F, MUSIC_VOLUME = 1.0F;
	/**
	 * ReadOnly; Modifying this doesn't affect anything.
	 */
	public static boolean BLOCK_BORDERS = true;
	
	private GUI gui;
	private int delay = 10;
	
	private Button back = new Button(10, 10, 33, 20, "Back", 100, 200, 100);
	private Slider soundVolume = new Slider(10, 50, 100, 1.0F, 1.0F, "Sound Volume");
	private Slider musicVolume = new Slider(Main.WIDTH - 111, 50, 100, 1.0F, 1.0F, "Music Volume");
	private Toggle blockBorders = new Toggle(35, 80, BLOCK_BORDERS, "Block Borders");
	
	public Options(GUI gui)
	{
		this.gui = gui;
	}

	public void tick()
	{
		if(delay > 0) delay--;
		back.tick();
		if(back.isClicked() && delay <= 0) closeGUI();
		updateOptions();
	}

	public void render()
	{
		Render.setColor(60, 120, 200);
		Render.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		back.render();
		soundVolume.render();
		musicVolume.render();
		blockBorders.render();
		Text.drawStringWithShadow("Options", (Main.WIDTH / 2) - (Render.stringWidth("Options", 22) / 2), 28, Color.WHITE, 22, Main.gameFont);
	}

	public void openGUI()
	{
		if(gui != null) gui.isActive = false;
		super.openGUI();
	}

	public void closeGUI()
	{
		SaveOptions.saveOptions();
		if(gui != null) gui.isActive = true;
		super.closeGUI();
	}
	
	public void setSoundVolume(int f)
	{
		soundVolume.setValue(f);
	}
	
	public void setMusicVolume(int f)
	{
		musicVolume.setValue(f);
	}
	
	public void setBlockBorders(boolean b)
	{
		blockBorders.setValue(b);
	}
	
	public void setBlockBorders(int i)
	{
		blockBorders.setValue(i);
	}
	
	public void updateOptions()
	{
		soundVolume.tick();
		musicVolume.tick();
		blockBorders.tick();
		SOUND_VOLUME = soundVolume.getValue();
		MUSIC_VOLUME = musicVolume.getValue();
		BLOCK_BORDERS = blockBorders.getValue();
		SoundSystem.Music.setMainVolume(musicVolume.getValue());
		SoundSystem.Sound.setMainVolume(soundVolume.getValue());
	}
}
