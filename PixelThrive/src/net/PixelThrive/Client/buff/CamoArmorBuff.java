package net.PixelThrive.Client.buff;

import net.PixelThrive.Client.Main;

public class CamoArmorBuff extends ArmorBuff
{		
	int delay;
	public CamoArmorBuff(int id)
	{	
		super(id);
	}

	public void effect()
	{
		super.effect();
		delay++;
		if(delay>=4)
		{
			delay = 0;
			Main.player.heal((float)(3*0.1));
		}
	}
}
