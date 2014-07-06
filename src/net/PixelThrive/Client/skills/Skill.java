package net.PixelThrive.Client.skills;

import java.util.ArrayList;
import java.util.List;

import net.PixelThrive.Client.*;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.renders.Texture;

public class Skill 
{
	public static List<Skill> skills = new ArrayList<Skill>();
	public static int[] IDs = new int[256];
	public int skillID, experienceCost;
	protected String name;
	protected Texture icon;
	protected Skill previousBuffRequired;

	public static final Skill doubleJump = new DoubleJump(0).setPreviousSkill(null).setExperienceCost(200).setIconTexture(0).setName("Bunny Hop");
	public static final Skill waterBreathing = new WaterBreathing(1).setPreviousSkill(null).setExperienceCost(300).setIconTexture(1).setName("Human Submarine");
	
	public Skill(int id)
	{
		if(id < IDs.length)
		{
			skillID = id;
			skills.add(this);
		}
		else throw new PixelException("Ran out of Skill IDs!", PixelException.ExceptionType.NOTENOUGHIDS, null, null);
	}

	public Skill(int id, int texture)
	{
		this(id);	
		setIconTexture(texture);
	}

	public Skill(int id, int texture, SpriteSheet s)
	{
		this(id);	
		setIconTexture(texture, s);
	}

	public String getName() 
	{
		return name;
	}

	public Skill setName(String name) 
	{
		this.name = name;
		return this;
	}

	public Skill getPreviousSkill() 
	{
		return previousBuffRequired;
	}

	public Skill setPreviousSkill(Skill skill) 
	{
		this.previousBuffRequired = skill;
		return this;
	}

	public int getExperienceCost() 
	{
		return experienceCost;
	}

	public Skill setExperienceCost(int cost) 
	{
		this.experienceCost = cost;
		return this;
	}

	public Texture getIconTexture()
	{
		return icon;
	}

	public Skill setIconTexture(int id)
	{
		int y = id/16;
		this.icon = new Texture(SpriteSheet.Skill, id - 16*y, y);
		return this;
	}

	public Skill setIconTexture(int texture, SpriteSheet s)
	{
		int y = texture/16;
		this.icon = new Texture(s, texture - 16*y, y);
		return this;
	}

	public void render()
	{	
	}

	public int getSkillID() 
	{
		return skillID;
	}

	public void setSkillID(int buffID) 
	{
		this.skillID = buffID;
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
