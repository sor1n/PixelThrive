package net.PixelThrive.Client.buff;

import java.util.*;
import net.PixelThrive.Client.Main;

public class ArmorBuff 
{
	public static List<ArmorBuff> buffs = new ArrayList<ArmorBuff>();
	public static int[] IDs = new int[64];
	public int buffID;
	protected String name;
	
	public static final ArmorBuff camo = new CamoArmorBuff(0).setName("Camouflage");

	public ArmorBuff(int id)
	{
		if(id < IDs.length)
		{
			buffID = id;
			buffs.add(this);
		}
		else
		{
			try 
			{
				Main.consoleMessage("Ran out of Buffs IDs!");
				throw new IndexOutOfBoundsException();
			}
			catch(Exception j)
			{
				j.printStackTrace();
			}
		}	
	}

	public String getName() 
	{
		return name;
	}

	public ArmorBuff setName(String name) 
	{
		this.name = name;
		return this;
	}
	
	public int getBuffID() {
		return buffID;
	}

	public void setBuffID(int buffID) {
		this.buffID = buffID;
	}

	public void effect()
	{
	}
}
