package net.PixelThrive.Client.GUI;

import java.awt.Color;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.entities.Entity;
import net.PixelThrive.Client.renders.Render;

public class HealthBar
{
	private int x, y, min, width = 100, height = 10;
	public boolean isDead = false;
	public int health = 0;
	public boolean isPlayerHealth = true;
	private Entity ent;
	public EXPCircle expCircle;

	public HealthBar(int x, int y, int min)
	{
		this.x = x;
		this.y = y;
		this.min = min;
		isPlayerHealth = true;
		expCircle = new EXPCircle(x - (EXPCircle.getWidth() / 2), y + (height / 2) - (EXPCircle.getWidth() / 2));
	}

	public HealthBar(Entity entity, int x, int y, int width, int height)
	{
		this.x = x;
		this.y = y;
		isPlayerHealth = false;
		ent = entity;
		health = (int)entity.getHealth();
		this.width = width;
		this.height = height;
	}

	public void tick()
	{
		if(isPlayerHealth)
		{
			if(Main.player != null) health = (int)Main.player.getHealth();
			if(health < 0) health = 0;
			if(Main.player != null && min == 0 && Main.player.getHealth() <= min) isDead = true;
			else isDead = false;
			expCircle.tick();
		}
		else
		{
			health = (int)ent.getHealth();
			if(health < 0) health = 0;
		}
	}

	public void render()
	{
		if(!GUI.hideGUI)
		{
			if(isPlayerHealth)
			{
				for(int i = min; i < width; i++)
				{
					Render.setColor(new Color(i + 120, 10, 10));
					Render.fillRect(x + i, y, 1, height);
				}
				Render.setColor(Color.BLACK);
				Render.drawRect(x, y, width, height);
				Text.drawStringWithShadow(String.valueOf(health + "/" + (int)Main.player.getMaxHealth()), x + 32, y + (height - 1), Color.WHITE, 8, Main.gameFont);
				Render.setColor(new Color(140, 140, 140, 210));
				Render.fillRect(x + health, y + 1, 2, height - 1);
				if(isDead)
				{
					Render.setColor(new Color(80, 0, 0, 120));
					Render.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
				}
				expCircle.render();
			}
			else
			{
				int size = String.valueOf(health + "/" + (int)ent.getMaxHealth()).length();
				int offs = 0;
				if(size == 3) offs = 18;
				if(size == 4) offs = 16;
				if(size == 5) offs = 12;
				if(size == 6) offs = 9;
				if(size == 7) offs = 6;
				if(size == 8) offs = 3;
				for(int i = min; i < width; i++)
				{
					Render.setColor(new Color(i + 120, 10, 10));
					Render.fillRect(x + i, y, 1, height);
				}
				Render.setColor(Color.BLACK);
				Render.drawRect(x, y, width, height);
				Text.drawStringWithShadow(String.valueOf(health + "/" + (int)ent.getMaxHealth()), x + offs, y + (height - 1), Color.WHITE, 8, Main.gameFont);
				Render.setColor(new Color(140, 140, 140, 210));
				Render.fillRect(x + (int)((health / ent.getMaxHealth()) * width), y + 1, 2, height - 1);
			}
		}
	}
}
