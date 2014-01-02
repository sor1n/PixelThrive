package net.PixelThrive.Client.skills;

import net.PixelThrive.Client.Main;

public class WaterBreathing extends Skill
{
	public WaterBreathing(int id)
	{	
		super(id);
	}

	public void effect()
	{
		super.effect();
		Main.player.setDrownLimit(140);
	}	
}
