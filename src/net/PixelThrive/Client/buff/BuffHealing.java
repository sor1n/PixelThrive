package net.PixelThrive.Client.buff;

public class BuffHealing extends Buffs
{		
	int delay;
	public BuffHealing(int id)
	{	
		super(id);
	}

	public void effect()
	{
		super.effect();
		if(duration > 0)
		{ 
			delay++;
			if(delay>=4){
				delay = 0;
				affected.heal((float)(strength*0.1));
			}
			decreaseDuration();
		}
	}
}
