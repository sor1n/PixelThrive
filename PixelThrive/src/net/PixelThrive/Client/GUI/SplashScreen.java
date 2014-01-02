package net.PixelThrive.Client.GUI;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

import net.PixelThrive.Client.*;
import net.PixelThrive.Client.renders.Render;

public class SplashScreen extends GUI
{
	//To trigger the splash screen, add "new SplashScreen();" in your startGame/initGame method
	
	private int tick = 0;
	private BufferedImage banner;

	public SplashScreen()
	{	
		try
		{
			banner = ImageIO.read(this.getClass().getClassLoader().getResource("Banner.png"));	
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		openGUI(); //Adds it to the GUI list in Main, making the tick and render methods loop.
		canBeHidden = false;
	}

	public void tick()
	{
		tick++;
		if(tick >= 250) //change the duration of the screen if you want.
		{
			new MainMenu().openGUI(); //This line opens the Main Menu
			closeGUI(); //Removes it from the GUI list to stop ticking/rendering
		}
	}

	public void render()
	{
		Render.setColor(0xE7E7E7);
		Render.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		Render.drawImage(banner.getScaledInstance(Main.WIDTH, Main.HEIGHT - (banner.getHeight()), BufferedImage.SCALE_SMOOTH), 0, 0);
		Render.setColor(255, 255, 255, 255 - ((tick < 255)? tick : 255));
		Render.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
	}
}
