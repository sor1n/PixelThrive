package net.PixelThrive.Client.GUI;

import java.awt.Color;

import net.PixelThrive.Client.Input.Key;
import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.biomes.Biome;
import net.PixelThrive.Client.entities.Player;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.world.Tile;
import net.PixelThrive.Client.world.World;

public class DebugMenu extends GUI
{
	public static boolean isOpen = false;
	public int x, y, keyDelay;
	private World world;
	private Player player;
	private Biome currentBiome;

	public DebugMenu(World world, Player player)
	{	
		x = 5;
		y = Main.HEIGHT - 99;		
		this.world = world;
		this.player = player;
	}

	public void tick() 
	{
		try
		{
			if(world.getBiomeByX((int)player.getX() / Tile.tileSize).getName() != null) currentBiome = world.getBiomeByX((int)(player.getX() / Tile.tileSize));
		}
		catch(Exception e){}
	}

	public void triggerTick()
	{
		if(keyDelay > 0) keyDelay--;
		if(Main.key != null && Key.debugMenu.isPressed() && keyDelay <= 0 && !Main.console.showChat)
		{
			keyDelay = 20;
			if(isOpen) closeGUI();
			else openGUI();
		}
	}

	public void render() 
	{
		if(player != null && isOpen)
		{
			int m = 0;
			if(m < Render.stringWidth("Biome: " + currentBiome.getName(), 10)) m = Render.stringWidth("Biome: " + currentBiome.getName(), 10);
			if(m < Render.stringWidth("Seed: " + world.displaySeed, 10)) m = Render.stringWidth("Seed: " + world.displaySeed, 10);
			Render.setColor(new Color(20, 20, 20, 200));
			Render.fillRect(2, 15, m + 5, 65);
			Text.drawStringWithShadow("X: " + (int)(player.getX() / Tile.tileSize), 5, 25, Color.WHITE, 10, Main.gameFont);
			Text.drawStringWithShadow("Y: " + (Main.world.worldH - (int)(player.getY() / Tile.tileSize)), 5, 35, Color.WHITE, 10, Main.gameFont);
			Text.drawStringWithShadow("Biome: " + currentBiome.getName(), 5, 45, Color.WHITE, 10, Main.gameFont);
			Text.drawStringWithShadow("Climate: " + currentBiome.getType().toString().substring(0, 1) + currentBiome.getType().toString().substring(1).toLowerCase(), 5, 55, Color.WHITE, 10, Main.gameFont);
			Text.drawStringWithShadow("Time: " + (int)(Main.world.time*1000), 5, 65, Color.WHITE, 10, Main.gameFont);
			Text.drawStringWithShadow("Seed: " + world.displaySeed, 5, 75, Color.WHITE, 10, Main.gameFont);
		}		
	}

	public void openGUI()
	{
		isOpen = true;
		super.openGUI();
	}

	public void closeGUI()
	{
		isOpen = false;
		super.closeGUI();
	}
}
