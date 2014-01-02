package net.PixelThrive.Client.commands;

import net.PixelThrive.Client.Main;

public class CommandTime extends Command
{
	public CommandTime(String name, String[] comm)
	{
		super(name, comm);
		addDescription("/time set day");
		addDescription("-> Sets the time");
		addDescription("to day");
		addDescription("/time set night");
		addDescription("-> Sets the time");
		addDescription("to night");
		addDescription("/time set + value");
		addDescription("-> Sets the time");
		addDescription("to the value given");
		addDescription("/time add + value");
		addDescription("-> Adds to the time");
		addDescription("the amount specified");
		addDescription("/time substract + value");
		addDescription("-> Removes from the");
		addDescription("time the amount specified");
	}
	
	public void effect(String message)
	{
		super.effect(message);
		String m[] = message.split(" ");
		if(message.equalsIgnoreCase("/time set day")) Main.world.time = 0;
		else if(message.equalsIgnoreCase("/time set night")) Main.world.time = 3.32;
		else if(message.startsWith("/time set")) Main.world.time = Integer.valueOf(m[2])*0.001;
		if(message.startsWith("/time add")) Main.world.time += Integer.valueOf(m[2])*0.001;
		if(message.startsWith("/time substract")) Main.world.time -= Integer.valueOf(m[2])*0.001;
	}

}
