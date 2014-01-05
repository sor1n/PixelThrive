package net.PixelThrive.Client.commands;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.items.CraftableStack;
import net.PixelThrive.Client.items.Item;

public class CommandGive extends Command
{
	public CommandGive(String name, String[] comm)
	{
		super(name, comm);
		addDescription("[command] + object");
		addDescription("-> Gives the player the");
		addDescription("requested block or item");
		addDescription("[command] + object + value");
		addDescription("-> Gives the player the");
		addDescription("requested amount of");
		addDescription("the block or item");
	}

	public void effect(String message)
	{
		super.effect(message);
		String m[] = message.split(" ");
		for(int i = 0; i < Block.blocks.size(); i++)
		{
			if(message.equalsIgnoreCase("/give " + Block.blocks.get(i).getName())) Main.inv.giveItem(new CraftableStack(Block.blocks.get(i).blockID, 1));
			if(message.startsWith("/give " + Block.blocks.get(i).getName() + " ")) Main.inv.giveItem(new CraftableStack(Block.blocks.get(i).blockID, Integer.valueOf(m[2])));
		}
		for(int i = 0; i < Item.items.size(); i++)
		{
			if(message.equalsIgnoreCase("/give " + Item.items.get(i).getName())) Main.inv.giveItem(new CraftableStack(Item.items.get(i).itemID, 1));
			if(message.startsWith("/give " + Item.items.get(i).getName() + " ")) Main.inv.giveItem(new CraftableStack(Item.items.get(i).itemID, Integer.valueOf(m[2])));
		}
	}
}
