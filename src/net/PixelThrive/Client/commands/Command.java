package net.PixelThrive.Client.commands;

import java.util.ArrayList;
import java.util.List;

public class Command 
{
	public static List<Command> commands = new ArrayList<Command>();
	public String[] command = new String[320000];
	public String name;
	public List<String> description = new ArrayList<String>();
	
	public static final Command spawn = new CommandSpawn("Spawn", new String[]{"/spawn"});
	public static final Command gamemode = new CommandGamemode("Gamemode", new String[]{"/gamemode ", "/gm ", "/g "});
	public static final Command healthManagement = new CommandHealth("Health", new String[]{"/health", "/heal", "/damage", "/kill"});
	public static final Command noclip = new CommandNoclip("Noclip", new String[]{"/noclip", "/fly"});
	public static final Command experienceManagement = new CommandExperience("Experience", new String[]{"/add xp", "/remove xp", "/set xp", "/clear xp", "/add level", "/remove level", "/set level"});
	public static final Command time = new CommandTime("Time", new String[]{"/time set", "/time add", "/time substract"});
	public static final Command give = new CommandGive("Give", new String[]{"/give "});
	public static final Command teleport = new CommandTeleport("Teleport", new String[]{"/tp", "/teleport", "/transport"});
	
	public Command(String name, String[] comm)
	{
		commands.add(this);
		command = comm;
		this.name = name;
	}
	
	public Command addDescription(String descr)
	{
		description.add(descr);
		return this;
	}
	
	public List<String> getDescription()
	{
		return description;
	}
	
	public void effect(String message)
	{
		message = message.trim();		
	}

}
