package net.PixelThrive.Client.buff;

import java.awt.Color;
import java.util.*;

import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.renders.Texture;
import net.PixelThrive.Client.world.Tile;
import net.PixelThrive.Client.LoadedImage;
import net.PixelThrive.Client.entities.*;
import net.PixelThrive.Client.GUI.*;

import net.PixelThrive.Client.Main;

public class Buffs 
{
	public static List<Buffs> buffs = new ArrayList<Buffs>();
	public static int[] IDs = new int[64];
	public int buffID;
	public int strength = 0;
	public int duration = 0, time = 0, color;
	protected String name;
	protected Texture icon;
	protected Entity affected;

	public static final Buffs regeneration = new BuffHealing(0).setIconTexture(0).setColor(0x70FF1000).setName("Regeneration");
	public static final Buffs harming = new BuffHarm(1).setIconTexture(1).setColor(0xAEFEE0).setName("Harming");

	public Buffs(int id)
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
	
	public Buffs(int id, int texture)
	{
		this(id);	
		setIconTexture(texture);
	}
	
	public Buffs(int id, int texture, SpriteSheet s)
	{
		this(id);	
		setIconTexture(texture, s);
	}

	public String getName() 
	{
		return name;
	}

	public Buffs setName(String name) 
	{
		this.name = name;
		return this;
	}

	public Texture getIconTexture()
	{
		return icon;
	}
	
	public Buffs setIconTexture(int id)
	{
		int y = id/16;
		this.icon = new Texture(SpriteSheet.Buff, id - 16*y, y);
		return this;
	}

	public Buffs setIconTexture(int texture, SpriteSheet s)
	{
		int y = texture/16;
		this.icon = new Texture(s, texture - 16*y, y);
		return this;
	}

	public int getStrength() {
		return strength;
	}

	public Buffs setStrength(int str) 
	{
		strength = str;
		return this;
	}

	public int getDuration() 
	{
		return duration;
	}

	public Buffs setDuration(int dur) 
	{
		duration = dur;
		return this;
	}

	public void render()
	{	
		LoadedImage cell = new LoadedImage("Slot.png");
		for(int i = 0; i < buffs.size(); i++){
			if(buffs.get(i).getDuration() > 0){
				try{
				for(int j = 0; j < getAffected().activePotions.size(); j++){
					Render.drawImage(cell.getImage(), 5, 15 + Tile.tileSize*j, getAffected().activePotions.get(j).getIconTexture().getTextureWidth(), getAffected().activePotions.get(j).getIconTexture().getTextureHeight());
					Render.setColor(new Color(0, 50, 200, 175)); 
					Render.fillRect(5, 15 + Tile.tileSize*j, getAffected().activePotions.get(j).getIconTexture().getTextureWidth(), getAffected().activePotions.get(j).getIconTexture().getTextureHeight());
					Render.drawImage(SpriteSheet.Buff.getImage().getSubimage(getAffected().activePotions.get(j).getIconTexture().x * Tile.tileSize, getAffected().activePotions.get(j).getIconTexture().y * Tile.tileSize, Tile.tileSize, Tile.tileSize), 5, 15 + Tile.tileSize*j);
					if(getAffected().activePotions.get(j).getDuration()%100 >= 10)Text.drawStringWithShadow(Integer.toString(getAffected().activePotions.get(j).strength) + " " + Integer.toString(getAffected().activePotions.get(j).getDuration() / 100) + ":" + Integer.toString(getAffected().activePotions.get(j).getDuration() % 100), 3*Tile.tileSize/2, 18 + Tile.tileSize/2 + Tile.tileSize*j, 0xffffff, 12);
					else Text.drawStringWithShadow(Integer.toString(getAffected().activePotions.get(j).strength) + " " + Integer.toString(getAffected().activePotions.get(j).getDuration() / 100) + ":0" + Integer.toString(getAffected().activePotions.get(j).getDuration() % 100), 3*Tile.tileSize/2, 18 + Tile.tileSize/2 + Tile.tileSize*j, 0xffffff, 12);
				}
				}catch(Exception e){}
			}			
		}

	}

	public void decreaseDuration()
	{
		time++;
		if(duration%100 >= 60)duration = duration - 40;
		if(time >= 40){
			duration--;
			time = 0;
		}
	}

	public int getBuffID() {
		return buffID;
	}

	public void setBuffID(int buffID) {
		this.buffID = buffID;
	}

	public Entity getAffected() {
		return affected;
	}

	public void setAffected(Entity aff) 
	{
		affected = aff;
	}

	public static void clearActiveBuffs()
	{
		for(int i = 0; i < buffs.size(); i++)
			buffs.get(i).duration = 0;
	}

	public int getColor() 
	{
		return color;
	}

	public Buffs setColor(int color) 
	{
		this.color = color;
		return this;
	}
	
	public SpriteSheet getSpriteSheet()
	{
		return this.icon.getSpriteSheet();
	}
	
	public boolean isEffectInstant()
	{
		return false;
	}

	public void effect()
	{
	}
}
