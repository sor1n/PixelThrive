package net.PixelThrive.Client;

import java.awt.*;

import net.PixelThrive.Client.GUI.Text;
import net.PixelThrive.Client.renders.Render;

public class LoadingBar implements Runnable
{
	public int percent;
	public String message = "";
	public boolean isLast = false;
	public volatile boolean isDone = false;
	private Thread thread = new Thread(this, Main.TITLE + " - Loading");
	public boolean isRunning = false;
	private Render render;

	public void open()
	{
		thread.start();
		isRunning = true;
	}

	public void close()
	{
		isRunning = false;
		try
		{
			thread.join();
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
		render = null;
	}

	public void render()
	{
		Graphics2D g = (Graphics2D)Main.getGameInstance().getScreen().getGraphics();
		if(render == null) render = new Render(g);
		g = (Graphics2D) Main.getGameInstance().getFrame().getGraphics();
		Render.setColor(new Color(60, 180, 220));
		Render.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		Text.drawStringWithShadow(Main.VERSION, Main.WIDTH - 40, Main.HEIGHT - 10, Color.WHITE, 12, Main.gameFont);
		String loading = "Loading...";
		Render.setFont(Main.gameFont, 22);
		int loadW = Render.getFontMetrics().stringWidth(loading);
		Text.drawStringWithShadow(loading, (Main.WIDTH / 2) - (loadW / 2), 40, Color.WHITE, 22, Main.gameFont);
		Render.setFont(Main.gameFont, 15);
		int messW = Render.getFontMetrics().stringWidth(message);
		Text.drawStringWithShadow(message, (Main.WIDTH / 2) - (messW / 2), 60, Color.WHITE, 15, Main.gameFont);
		Render.setFont(Main.gameFont, 15);
		String per = String.valueOf(percent) + "%";
		int percentW = Render.getFontMetrics().stringWidth(per);
		Text.drawStringWithShadow(per, (Main.WIDTH / 2) - (percentW / 2), 100, Color.WHITE, 19, Main.gameFont);
		g = (Graphics2D) Main.getGameInstance().getGraphics();
		g.drawImage(Main.getGameInstance().getScreen(), 0, 0, Main.size.width, Main.size.height, 0, 0, Main.pixel.width, Main.pixel.height, null);
		g.dispose();
	}

	public void load(int p, String m, boolean isL)
	{
		isLast = isL;
		percent = p;
		message = m;
	}

	public void run()
	{
		while(isRunning){
			render();
			try
			{
				Thread.sleep(5);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
