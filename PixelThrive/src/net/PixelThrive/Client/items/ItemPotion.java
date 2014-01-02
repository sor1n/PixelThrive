package net.PixelThrive.Client.items;

import net.PixelThrive.Client.*;
import net.PixelThrive.Client.GUI.CreativeTabs;
import net.PixelThrive.Client.buff.*;

public class ItemPotion extends Item
{
	protected int buffID, strength, duration;

	public ItemPotion(int id, int texture, int buff, int strength, int duration)
	{
		super(id);
		setMaxStackSize(16);
		buffID = buff;
		this.strength = strength;
		this.duration = duration;
		setTexture(texture);
		setName(Buffs.buffs.get(buffID).getName() + " Potion");
		switch(buff)
		{
		case 1:
			setDescription("This is not coke..", 0xFEA0FE);
			break;
		default:
			setDescription("It's a me! " + Buffs.buffs.get(buffID).getName(), 0xE0AE0F);
			break;
		}
		setCropInHand(false, 0);
		addStat("Strength: " + strength);
		addStat("Duration: " + duration);
		setClickDelay(10);
		setCreativeTab(CreativeTabs.POTIONS);
	}

	public int getBuffID()
	{
		return buffID;
	}

	public void onItemRightClick()
	{
		if(Main.player.activePotions.size() <= 0){
			Main.player.addBuff(buffID, strength, duration);
			if(!Main.player.isCreative) Main.inv.removeItemsFromInventory(this.itemID, 1);
		}		
		try{
			if(!Main.player.activePotions.contains(Buffs.buffs.get(buffID)))
			{
				Main.player.addBuff(buffID, strength, duration);
				if(!Main.player.isCreative) Main.inv.removeItemsFromInventory(this.itemID, 1);
			}
			else
			{
				if(Main.player.activePotions.get(buffID).getDuration() < duration && Main.player.activePotions.get(buffID).getStrength() == strength){
					Main.player.activePotions.get(buffID).setDuration(duration);
					if(!Main.player.isCreative) Main.inv.removeItemsFromInventory(this.itemID, 1);	
				}
				else if(Main.player.activePotions.get(buffID).getStrength() < strength){
					Main.player.activePotions.get(buffID).setStrength(strength);
					Main.player.activePotions.get(buffID).setDuration(duration);
					if(!Main.player.isCreative) Main.inv.removeItemsFromInventory(this.itemID, 1);	
				}			
			}
		}catch(Exception e){}
	}

	public int getDuration() {
		return duration;
	}

	public ItemPotion setDurationAndStrength(int dur, int str)
	{
		duration = dur;
		strength = str;
		updateStats();
		return this;
	}

	public int getStrength()
	{
		return strength;
	}

	public void updateStats()
	{
		removeAllStats();
		addStat("Strength: " + strength);
		addStat("Duration: " + duration / 100 + ":" + duration % 100);
	}
}
