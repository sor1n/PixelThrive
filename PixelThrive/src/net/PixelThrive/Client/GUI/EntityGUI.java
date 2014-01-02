package net.PixelThrive.Client.GUI;

import java.awt.Color;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.entities.Entity;
import net.PixelThrive.Client.entities.Entity.EntityAttribute;
import net.PixelThrive.Client.renders.Render;

public class EntityGUI extends GUI
{	
	public static EntityGUI currentEntityGUI = null;

	private Entity entity;
	private int x, y;
	public HealthBar healthBar;
	private int rareOffs = 0;
	private int delay = -1;
	public boolean isCloseTriggered = false;

	public EntityGUI(Entity entity, int x, int y)
	{
		this.entity = entity;
		this.x = x;
		this.y = y;
		healthBar = new HealthBar(entity, x + 4, y + 60, 52, 10);
	}

	public void tick()
	{
		if(delay > 0) delay--;
		else if(delay == 0) closeGUI();
		healthBar.tick();
		if(entity.getEntityRarity() == EntityAttribute.DEFAULT) rareOffs = 0;
		else rareOffs = 10;
	}

	public void render()
	{
		if(currentEntityGUI == this)
		{
			Render.setColor(new Color(20, 20, 20, 150));
			Render.fillRect(x, y, 60, 80 + rareOffs);
			Render.setColor(new Color(10, 10, 10, 150));
			Render.fillRect(x + 2, y + 2, 56, 40);
			if(entity.getEntityRender() != null) entity.renderEntity(entity.getEntityRender(), x + 2 + 28 - (int)(entity.getWidth() / 2), y + 2 + 20 - (int)(entity.getHeight() / 2));
			else entity.renderEntity(null, x + 2 + 28 - (int)(entity.getWidth() / 2), y + 2 + 20 - (int)(entity.getHeight() / 2));
			if(entity.getEntityName() != null)
			{
				int size = 14;
				for(int i = 14; i > 0; i--)
				{
					Render.setFont(Main.gameFont, i);
					if(Render.getFontMetrics().stringWidth(entity.getEntityName()) < 56)
					{
						size = i;
						break;
					}
				}
				Text.drawStringWithShadow(entity.getEntityName(), x + 5, y + 55, 0xffffff, size, Main.gameFont);
			}
			if(!entity.isInvincible()) healthBar.render();
			if(entity.getEntityRarity() != EntityAttribute.DEFAULT)
			{
				Render.drawImage(entity.getEntityRarity().getIcon().getImageIcon(), x + 1, y + 73);
				Text.drawStringWithShadow(entity.getEntityRarity().getRarityName(), x + 17, y + 84, 0xffffff, 9, Main.gameFont);
			}
		}
	}
	
	public void openGUI()
	{
		super.openGUI();
		currentEntityGUI = this;
		isCloseTriggered = false;
	}
	
	public void triggerClose()
	{
		delay = 190;
		isCloseTriggered = true;
	}
	
	public void closeGUI()
	{
		super.closeGUI();
		currentEntityGUI = null;
	}

	public Entity getEntity()
	{
		return entity;
	}
}
