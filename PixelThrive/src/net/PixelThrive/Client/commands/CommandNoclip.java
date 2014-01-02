package net.PixelThrive.Client.commands;

import net.PixelThrive.Client.Main;

public class CommandNoclip extends Command
{
	public CommandNoclip(String name, String[] comm)
	{
		super(name, comm);
		addDescription("[command]");
		addDescription("-> Sets the player");
		addDescription("into flying mode");
		addDescription("-> Entering the command");
		addDescription("again deactivates it");
	}

	public void effect(String message)
	{
		super.effect(message);
		for(int i = 0; i < command.length; i++)
			if(message.equalsIgnoreCase(command[i])) Main.player.noclip = !Main.player.noclip;
	}
}
