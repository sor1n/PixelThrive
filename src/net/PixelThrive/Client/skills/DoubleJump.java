package net.PixelThrive.Client.skills;
import net.PixelThrive.Client.*;

public class DoubleJump extends Skill
{
	public DoubleJump(int id)
	{	
		super(id);
	}

	public void effect()
	{
		super.effect();
		Main.player.setJumpLimit(40);
	}
}
