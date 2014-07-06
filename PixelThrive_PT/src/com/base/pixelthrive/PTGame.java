package com.base.pixelthrive;

import com.base.engine.CoreEngine;
import com.base.engine.Game;
import com.base.engine.Vector2f;
import com.base.pixelthrive.guis.MainMenu;

public class PTGame extends Game
{
	public static final String TITLE = "PixelThrive", VERSION = "v0.1";
	
	public PTGame()
	{
		super(TITLE);
	}
	
	@Override
	public void init()
	{
		super.init();
		addObject(new MainMenu());
	}
	
	public static class PTMain
	{
		public static void main(String[] args)
		{
			CoreEngine engine = new CoreEngine(new Vector2f(990, 990 / 16 * 9), 60D, new PTGame());
			engine.start();
		}
	}
}
