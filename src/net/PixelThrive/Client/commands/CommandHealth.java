package net.PixelThrive.Client.commands;

import net.PixelThrive.Client.Main;

public class CommandHealth extends Command
{
	public CommandHealth(String name, String[] comm)
	{
		super(name, comm);
		addDescription("/health + value");
		addDescription("-> Sets the health");
		addDescription("of the player to the");
		addDescription("given value");
		addDescription("/heal + value");
		addDescription("-> Restores the player");
		addDescription("the amount of health");
		addDescription("specified");
		addDescription("/damage + value");
		addDescription("-> Damages the palyer");
		addDescription("equal to the amount");
		addDescription("of health specified");
		addDescription("/kill");
		addDescription("-> Kills the player");
	}

	public void effect(String message)
	{
		super.effect(message);
		String m[] = message.split(" ");
		if(message.startsWith("/health")) Main.player.setHealth(Float.valueOf(m[1]));
		if(message.startsWith("/heal "))
			if(Integer.valueOf(m[1]) + Main.player.getHealth() <= Main.player.getMaxHealth()) Main.player.heal(Integer.valueOf(m[1]));
				else Main.player.setHealth(100);
		if(message.startsWith("/damage")) Main.player.heal(-Integer.valueOf(m[1]));
		if(message.startsWith("/kill")) Main.player.setHealth(0);
	}
}
