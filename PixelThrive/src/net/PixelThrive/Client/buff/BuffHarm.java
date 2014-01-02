package net.PixelThrive.Client.buff;

import net.PixelThrive.Client.DeathCause;
import net.PixelThrive.Client.entities.Player;

public class BuffHarm extends Buffs
{		
	int delay;
	public BuffHarm(int id)
	{	
		super(id);
	}

	public void effect()
	{
		super.effect();
		if(duration > 0)
		{ 
			delay++;
			if(delay >= 4)
			{
				delay = 0;
				if(affected instanceof Player){
					((Player)affected).damageIgnoresArmor = true;
					((Player)affected).hurt((float)(strength*0.1), DeathCause.MAGIC);
				}
				else affected.hurt((float)(strength*0.1));
			}
			decreaseDuration();
		}
	}
}
