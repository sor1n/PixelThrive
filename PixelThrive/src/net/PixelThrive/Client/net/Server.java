package net.PixelThrive.Client.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
//import java.util.ArrayList;
//import java.util.List;
import net.PixelThrive.Client.*;
//import net.PhuckYuToo.PixelThrive.net.packets.Packet;
//import net.PhuckYuToo.PixelThrive.net.packets.Packet.PacketTypes;
//import net.PhuckYuToo.PixelThrive.net.packets.*;

public class Server extends Thread
{
	private DatagramSocket socket;
	private Main game;
	//private List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();

	public Server(Main main)
	{
		this.game = main;

		try
		{
			this.socket = new DatagramSocket(1331);
		}
		catch(SocketException e)
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
			//			Main.consoleMessage("CLIENT ["+ packet.getAddress().getHostAddress()+":"+ packet.getPort() +"] > " + message);
			//			if(message.trim().equalsIgnoreCase("ping"))
			//			{
			//				sendData("pong".getBytes(), packet.getAddress(), packet.getPort());
			//			}
		}
	}

	public void parsePacket(byte[] data, InetAddress address, int port)
	{
//		String message = new String(data).trim();
//		PacketTypes type = Packet.lookUpPacket(message.substring(0, 2));
//		Packet packet = null;

//		String IP = "[" + address.getHostAddress() + ":" + port + "] ";

//		switch(type)
//		{
//		default:
//		case INVALID: 
//			break;
//		case LOGIN:
//			packet = new Packet00Login(data);
//			Main.consoleMessage(IP + ((Packet00Login)packet).getUsername() + " has connected...");
//			//PlayerMP player = new PlayerMP(2, 90, ((Packet00Login)packet).getUsername(), address, port, null);
//			addConnection(player, ((Packet00Login)packet));
//			break;
//		case DISCONNECT:
//			packet = new Packet01Disconnect(data);
//			Main.consoleMessage(IP + ((Packet01Disconnect)packet).getUsername() + " has left...");
//			removeConnection(((Packet01Disconnect)packet));
//			break;
//		case MOVE:
//			packet = new Packet02Move(data);
//			handleMove(((Packet02Move)packet));
//			break;
//		}
	}

//	public void addConnection(PlayerMP player, Packet00Login packet)
//	{
//		boolean alreadyConnected = false;
//		for(PlayerMP p : connectedPlayers)
//		{
//			if(player.getUsername().equalsIgnoreCase(p.getUsername()))
//			{
//				if(p.ipAddress == null)
//				{
//					p.ipAddress = player.ipAddress;
//				}
//				if(p.port == -1)
//				{
//					p.port = player.port;
//				}
//				alreadyConnected = true;
//			}
//			else
//			{
//				sendData(packet.getData(), p.ipAddress, p.port);
//				packet = new Packet00Login(p.getUsername(), (int)p.x, (int)p.y);
//				sendData(packet.getData(), player.ipAddress, player.port);
//			}
//		}
//
//		if(!alreadyConnected)
//		{
//			connectedPlayers.add(player);
//		}
//	}
//
//	public void removeConnection(Packet01Disconnect packet)
//	{
//		connectedPlayers.remove(getPlayerMPIndex(packet.getUsername()));
//		packet.writeData(this);
//	}
//
//	public PlayerMP getPlayerMP(String username)
//	{
//		for(PlayerMP player : connectedPlayers)
//		{
//			if(player.getUsername().equals(username))
//			{
//				return player;
//			}
//		}
//		return null;
//	}
//
//	public int getPlayerMPIndex(String username)
//	{
//		int index = 0;
//		for(PlayerMP player : connectedPlayers)
//		{
//			if(player.getUsername().equals(username))
//			{
//				break;
//			}
//			index++;
//		}
//		return index;
//	}

	public void sendData(byte[] data, InetAddress ipAddress, int port)
	{
		DatagramPacket packet = new DatagramPacket(data, data.length, ipAddress, port);
		try
		{
			this.socket.send(packet);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}

//	public void sendDataToAllClients(byte[] data)
//	{
//		for(PlayerMP p : connectedPlayers)
//		{
//			sendData(data, p.ipAddress, p.port);
//		}
//	}
//
//	private void handleMove(Packet02Move packet)
//	{
//		if(getPlayerMP(packet.getUsername()) != null)
//		{
//			int index = getPlayerMPIndex(packet.getUsername());
//			PlayerMP player = connectedPlayers.get(index);
//
//			player.x = packet.getX();
//			player.y = packet.getY();
//			player.setDir(packet.getDir());
//			player.setMoving(packet.isMoving());
//			player.setAnim(packet.getAnimation());
//			packet.writeData(this);
//		}
//	}

	public Main getGame()
	{
		return game;
	}
}
