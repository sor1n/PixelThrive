package net.PixelThrive.Client.commands;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.world.Tile;

public class CommandTeleport extends Command
{
	public CommandTeleport(String name, String[] comm)
	{
		super(name, comm);
		addDescription("[command] + (x)");
		addDescription("-> Teleports the player");
		addDescription("to the x coordonate");
		addDescription("specified");
		addDescription("[command] + (x) + (y)");//
		addDescription("-> Teleports the player");
		addDescription("to the x and y");
		addDescription("coordonates specified");
	}

	public void effect(String message)
	{
		super.effect(message);
		String m[] = message.split(" ");
		if(m.length >= 3) Main.player.setPosInWorld(Integer.valueOf(m[1]), (Main.world.worldH - Integer.valueOf(m[2])));
		else Main.player.setPosInWorld(Integer.valueOf(m[1]), (int)Main.player.getY() / Tile.tileSize);
	}
}
