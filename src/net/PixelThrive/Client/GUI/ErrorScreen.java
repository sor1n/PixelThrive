package net.PixelThrive.Client.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;

import javax.swing.JTextField;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.PixelException.ExceptionType;
import net.PixelThrive.Client.renders.Render;

public class ErrorScreen implements Runnable
{
	private Thread thread = new Thread(this, Main.TITLE + " - Error");
	public boolean isRunning = false;
	private Render render;
	protected String errorLine, message, errorDetails, line;
	protected ExceptionType ex;
	private int errorID;

	public ErrorScreen(ExceptionType e, String message, String errorLine, String errorDetails, int errorID, String line)
	{
		this.errorLine = errorLine;
		this.line = line;
		this.message = message;
		this.errorDetails = errorDetails;
		ex = e;
		this.errorID = errorID;
		JTextField label = new JTextField();
		label.setText(message);
		label.setVisible(true);
		label.setHorizontalAlignment(JTextField.CENTER);
		Main.getGameInstance().getFrame().add(label, BorderLayout.SOUTH);
		Main.getGameInstance().getFrame().pack();
		open();
		Main.getGameInstance().stop();
	}

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
		Text.drawStringWithShadow(ex.toString() + " ERROR", Main.WIDTH / 2 - (Render.stringWidth(ex.toString() + " ERROR", 11) / 2), Main.HEIGHT / 2 - 32, 0xFFFFFF, 11, Main.gameFont);
		if(line != null) Text.drawStringWithShadow(line, Main.WIDTH / 2 - (Render.stringWidth(line, 11) / 2), Main.HEIGHT / 2 - 18, 0xFFFFFF, 11, Main.gameFont);
		Text.drawStringWithShadow("An error has occured.", Main.WIDTH / 2 - (Render.stringWidth("An error has occured.", 13) / 2), 20, 0xFFFFFF, 13, Main.gameFont);
		//if(message != null) Text.drawStringWithShadow(message, Main.WIDTH / 2 - (Render.stringWidth(message, 13) / 2), 30, 0xFFFFFF, 13, Main.gameFont);
		if(errorLine != null) Text.drawStringWithShadow(errorLine, Main.WIDTH / 2 - (Render.stringWidth(errorLine, 11) / 2), Main.HEIGHT / 2 - 2, 0xFFFFFF, 11, Main.gameFont);
		Text.drawStringWithShadow("Error ID: " + String.valueOf(errorID), Main.WIDTH / 2 - (Render.stringWidth("Error ID: " + String.valueOf(errorID), 14) / 2), Main.HEIGHT / 2 + 14, 0xFFFFFF, 14, Main.gameFont);
		if(errorDetails != null) Text.drawStringWithShadow(errorDetails, Main.WIDTH / 2 - (Render.stringWidth(errorDetails, 12) / 2), Main.HEIGHT / 2 + 26, 0xFFFFFF, 12, Main.gameFont);
		g = (Graphics2D) Main.getGameInstance().getGraphics();
		g.drawImage(Main.getGameInstance().getScreen(), 0, 0, Main.size.width, Main.size.height, 0, 0, Main.pixel.width, Main.pixel.height, null);
		g.dispose();
	}

	public void run()
	{
		while(isRunning) render();
	}
}
