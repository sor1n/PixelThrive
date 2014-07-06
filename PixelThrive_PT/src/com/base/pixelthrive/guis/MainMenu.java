package com.base.pixelthrive.guis;

import org.newdawn.slick.Color;

import com.base.engine.Button;
import com.base.engine.Font;
import com.base.engine.Render;
import com.base.engine.Vector2f;
import com.base.engine.Window;
import com.base.pixelthrive.GUI;
import com.base.pixelthrive.PTGame;

public class MainMenu extends GUI
{
	private Font title = Render.getFont(57f), version = Render.getFont(30f);
	
	private Button singlePlayer = new Button(Window.getAbsoluteCenter(new Vector2f(250, 40)), new Vector2f(250, 40), 1);
	
	public MainMenu()
	{
		singlePlayer.setText("Singleplayer", 26f, new Vector2f(2));
	}

	public void update(float delta)
	{
		if(active)
		{
			singlePlayer.update(delta);
		}
	}

	public void input(float delta)
	{
		if(active)
		{
			singlePlayer.input(delta);
		}
	}

	public void render()
	{
		if(active)
		{
			Render.pushMatrix();
			Render.drawRectangle(Vector2f.ZERO, Window.getSize(), new Color(60, 180, 220)); //background
			Render.drawCenteredStringFixedY(Vector2f.ZERO, new Vector2f(Window.getSize().getX(), 40), PTGame.TITLE, new Color(0xffffff), title, new Vector2f(3)); //title
			Render.drawString(Window.getSize().sub(Render.getStringSize(PTGame.VERSION, version)), PTGame.VERSION, new Color(0xffffff), version, new Vector2f(3)); //version text
			singlePlayer.render(new Vector2f(0));
			Render.popMatrix();
		}
	}
}