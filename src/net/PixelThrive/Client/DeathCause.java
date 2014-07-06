package net.PixelThrive.Client;

import java.util.Random;

import net.PixelThrive.Client.entities.Entity;

public enum DeathCause
{
	MAGIC(new String[]{"got killed by magic."}), GRAVITY(new String[]{"got crushed.", "got splattered.", "got screwed."}), DROWNING(new String[]{"wanted to be a fish.", "is sleeping with the fish."}), BYENTITY(new String[]{"got slayed by "}),
	FALLING(new String[]{"didn't have a parachute."}), CACTUS(new String[]{"got stuck in a cactus."}), SHOT(new String[]{"got blasted away."}), UNDEFINED(new String[]{"got killed.", "got blasted away."});
	
	DeathCause(String[] mess)
	{
		messages = mess;
	}
	
	private String[] messages;
	private String message;
	
	public String getMessage()
	{
		int rand = new Random().nextInt(messages.length);
		message = messages[rand];
		return message;
	}
	
	public String getMessages()
	{
		return message;
	}
	
	public static void init()
	{
		values();
	}
	
	public static void causeEntityDeath(Entity e, String mess)
	{
		
	}
}
