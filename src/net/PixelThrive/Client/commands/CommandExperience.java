package net.PixelThrive.Client.commands;

import net.PixelThrive.Client.Main;

public class CommandExperience extends Command
{
	public CommandExperience(String name, String[] comm)
	{
		super(name, comm);
		addDescription("/add xp + value");
		addDescription("-> Adds the amount");
		addDescription("specified to the");
		addDescription("players experience");
		addDescription("/remove xp + value");
		addDescription("-> Removes the amount");
		addDescription("specified from the");
		addDescription("players experience");
		addDescription("/set xp + value");
		addDescription("-> Sets the amount");
		addDescription("specified as the");
		addDescription("players experience");
		addDescription("/add level + value");
		addDescription("-> Adds the amount");
		addDescription("specified to the");
		addDescription("players levels");
		addDescription("/remove level + value");
		addDescription("-> Removes the amount");
		addDescription("specified from the players");
		addDescription("levels");
		addDescription("/set level + value");
		addDescription("-> Sets the amount");
		addDescription("specified as the");
		addDescription("players level");
		addDescription("/clear xp");
		addDescription("-> Removes all exp");
		addDescription("from the player");
	}

	public void effect(String message)
	{
		super.effect(message);
		String m[] = message.split(" ");
		if(message.startsWith("/add xp")) Main.player.addExp(Integer.valueOf(m[2]));
		if(message.startsWith("/remove xp")) Main.player.reduceExp(Integer.valueOf(m[2]));
		if(message.startsWith("/set xp")) Main.player.setExp(Integer.valueOf(m[2]));
		if(message.startsWith("/add level")) Main.player.addExp(Integer.valueOf(m[2])*1000);
		if(message.startsWith("/remove level")) Main.player.reduceExp(Integer.valueOf(m[2])*1000);
		if(message.startsWith("/set level")) Main.player.setExp(Integer.valueOf(m[2])*1000);
		if(message.startsWith("/clear xp")) Main.player.setExp(0);
	}
}
