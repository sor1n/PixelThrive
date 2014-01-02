package net.PixelThrive.Client;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import net.PixelThrive.Server.packets.Packet01Disconnect;

public class WindowHandler implements WindowListener
{
	private final Main game;
	
	public WindowHandler(Main game)
	{
		this.game = game;
		this.game.getFrame().addWindowListener(this);
	}
	
	public void windowActivated(WindowEvent e)
	{
		
	}

	public void windowClosed(WindowEvent e)
	{
		
	}

	public void windowClosing(WindowEvent e)
	{
		Packet01Disconnect packet = new Packet01Disconnect(Main.player.getName());
		packet.writeData(this.game.socketClient);
	}

	public void windowDeactivated(WindowEvent e)
	{
		
	}

	public void windowDeiconified(WindowEvent e)
	{
	
	}

	public void windowIconified(WindowEvent e)
	{
	
	}

	public void windowOpened(WindowEvent e)
	{
		
	}
}
