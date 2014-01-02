package net.PixelThrive.Client.commands;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.PixelException;
import net.PixelThrive.Client.PixelException.ExceptionType;
import net.PixelThrive.Client.entities.Entity;

public class CommandSpawn extends Command
{
	public CommandSpawn(String name, String[] comm)
	{
		super(name, comm);
		addDescription("[command] + Entity name");
		addDescription("-> Spawns creature at");
		addDescription("the players location");
	}
	
	public void effect(String message)
	{
		super.effect(message);
		for(int i = 0; i < Entity.entityList.length; i++)
		{
			Entity e = null;
			try 
			{
				e = (Entity) Entity.entityList[i].newInstance();
			}
			catch(Exception ex)
			{
				throw new PixelException(ex.getMessage(), ExceptionType.OTHER, null, null);
			}
			if(message.equalsIgnoreCase("/spawn " + e.getEntityName()))
			{
				e.setPosition(Main.player.getX(), Main.player.getY());
				e.spawnEntity();
			}
		}
	}

}
