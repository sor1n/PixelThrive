package net.PixelThrive.Client.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import net.PixelThrive.Client.*;
import net.PixelThrive.Server.packets.*;
import net.PixelThrive.Server.packets.Packet.PacketTypes;

public class Client extends Thread
{
	private InetAddress ipAddress;
	private DatagramSocket socket;
	private Main game;

	public Client(Main main, String IP)
	{
		this.game = main;

		try
		{
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(IP);
		}
		catch(SocketException e)
		{
			e.printStackTrace();
		}
		catch(UnknownHostException e)
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		while(true)
		{
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);

			try
			{
				socket.receive(packet);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			
			parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
			
//			String message = new String(packet.getData());
//			Main.consoleMessage("SERVER > " + message);
		}
	}
	
	public void sendData(byte[] data)
	{
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, 1331);
		try
		{
			socket.send(packet);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public void parsePacket(byte[] data, InetAddress address, int port)
	{
		String message = new String(data).trim();
		PacketTypes type = Packet.lookUpPacket(message.substring(0, 2));
		Packet packet = null;
		
		switch(type)
		{
		default:
		case INVALID: 
			break;
		case LOGIN:
			packet = new Packet00Login(data);
			handleLogin((Packet00Login)packet, address, port);
			break;
		case DISCONNECT:
			packet = new Packet01Disconnect(data);
			Main.consoleMessage("[" + address.getHostAddress() + ":" + port + "] " + ((Packet01Disconnect)packet).getUsername() + " has left the game.");
			//Main.player.removePlayerMP(((Packet01Disconnect)packet).getUsername());
			break;
		case MOVE:
			packet = new Packet02Move(data);
			handleMove((Packet02Move)packet);
			break;
		}
	}
	
	private void handleLogin(Packet00Login packet, InetAddress address, int port)
	{
		Main.consoleMessage("[" + address.getHostAddress() + ":" + port + "] " + packet.getUsername() + " has joined the game.");
		//PlayerMP player = new PlayerMP(packet.getX(), packet.getY(), packet.getUsername(), address, port, null);
		//Main.getPlayers().add(player);
	}
	
	private void handleMove(Packet02Move packet)
	{
		//Main.player.movePlayer(packet.getUsername(), packet.getX(), packet.getY(), packet.getAnimation(), packet.isMoving(), packet.getDir());
	}

	public Main getGame()
	{
		return game;
	}
}
