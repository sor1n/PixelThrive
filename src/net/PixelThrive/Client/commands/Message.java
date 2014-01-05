package net.PixelThrive.Client.commands;

import net.PixelThrive.Client.*;
import net.PixelThrive.Client.GUI.GUI;
import net.PixelThrive.Client.GUI.Text;
import net.PixelThrive.Client.entities.*;
import net.PixelThrive.Client.renders.Render;
import java.awt.*;
import java.util.List;
import java.util.concurrent.*;

public class Message extends GUI
{
	private String mess;
	private int x, y, startingY, messDelay = 0;
	private Player sender;
	private int fade = 255;

	private static List<Message> messages = new CopyOnWriteArrayList<Message>();

	public Message(String mess, int x, int y, Player sender)
	{
		if(!isCommand(mess))
		{
			this.mess = mess;
			this.x = x;
			this.y = y;
			this.startingY = y;
			this.sender = sender;
			if(getMessages().size() > 0) moveEverythingElseUp();
			getMessages().add(this);
			openGUI();
		}
		else returnCommand(mess).effect(mess);
	}

	public Message(String mess, int x, int y)
	{
		if(!isCommand(mess))
		{
			this.mess = mess;
			this.x = x;
			this.y = y;
			this.startingY = y;
			if(getMessages().size() > 0) moveEverythingElseUp();
			getMessages().add(this);
			openGUI();
		}
		else returnCommand(mess).effect(mess);
	}

	public Message(String mess)
	{
		this(mess, 12, Main.HEIGHT - 33);
	}

	public Message(String mess, Player sender)
	{
		this(mess, 12, Main.HEIGHT - 33, sender);
	}

	public void tick()
	{
		messDelay++;
		if(messDelay > 200 && messDelay < 210) y--;
		if(messDelay >= 220) messDelay = 0;
		if(y < (startingY - 20) && fade > 0) fade--;
		if(fade <= 0)
		{
			getMessages().remove(this);
			closeGUI();
		}
	}

	public static synchronized List<Message> getMessages()
	{
		return messages;
	}

	public void moveEverythingElseUp()
	{
		for(int i = 0; i < getMessages().size(); i++) getMessages().get(i).messDelay = 200;
	}

	public void render()
	{
		if(!Main.inv.isOpen)
		{
			if(sender != null)
			{
				Text.drawStringWithShadowAndFade(sender.getName(), x, y, new Color(sender.nameColR, sender.nameColG, sender.nameColB, fade), 10, Main.gameFont, fade);
				Text.drawStringWithShadowAndFade(" : " + mess, x + Render.getFontMetrics().stringWidth(sender.getName()), y, new Color(255, 255, 255, fade), 10, Main.gameFont, fade);
			}
			else Text.drawStringWithShadowAndFade(mess, x, y, new Color(210, 210, 210, fade), 10, Main.gameFont, fade);	
		}
	}

	public boolean isCommand(String s)
	{
		s = s.trim();
		for(int i = 0; i < Command.commands.size(); i++)
			for(int m = 0; m < Command.commands.get(i).command.length; m++)
			if(s.contains(Command.commands.get(i).command[m])) return true;
		return false;
	}
	
	public Command returnCommand(String s)
	{
		s = s.trim();
		for(int i = 0; i < Command.commands.size(); i++)
			for(int m = 0; m < Command.commands.get(i).command.length; m++)
				if(s.contains(Command.commands.get(i).command[m]))
					return Command.commands.get(i);
		return null;		
	}
	
	public static void newMessage(Player sender, String mess)
	{
		new Message(mess, 12, Main.HEIGHT - 33, sender);
	}

	public static void newMessage(String mess)
	{
		new Message(mess, 12, Main.HEIGHT - 33);
	}

	public static void newDeathMessage(Player player, DeathCause deathCause)
	{
		new Message(player.getName() + " " + deathCause.getMessage(), 12, Main.HEIGHT - 33);
	}
}
