package net.PixelThrive.Client.commands;

import net.PixelThrive.Client.Main;

public class CommandGamemode extends Command
{
	public CommandGamemode(String name, String[] comm)
	{
		super(name, comm);
		addDescription("[command] + 0/survival");
		addDescription("-> Sets the player in");
		addDescription("survival mode");
		addDescription("[command] + 1/creative");
		addDescription("-> Sets the player in");
		addDescription("creative mode");
	}

	public void effect(String message)
	{
		super.effect(message);
		for(int i = 0; i < command.length; i++)
		{
			if(message.equalsIgnoreCase(command[i] + "0") || message.equalsIgnoreCase(command[i] + "survival"))
				Main.player.isCreative = false;
			if(message.equalsIgnoreCase(command[i] + "1") || message.equalsIgnoreCase(command[i] + "creative"))
				Main.player.isCreative = true;
		}
	}
}
