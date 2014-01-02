package net.PixelThrive.Server.packets;

import net.PixelThrive.Client.net.Client;
import net.PixelThrive.Client.net.Server;

public class Packet00Login extends Packet
{
	private String username;
	private int x, y;
	
	public Packet00Login(byte[] data)
	{
		super(00);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
	}
	
	public Packet00Login(String name, int x, int y)
	{
		super(00);
		this.username = name;
		this.x = x;
		this.y = y;
	}

	@Override
	public void writeData(Client client)
	{
		client.sendData(getData());
	}

	@Override
	public void writeData(Server server)
	{
		//server.sendDataToAllClients(getData());
	}
	
	@Override
	public byte[] getData()
	{
		return ("00" + this.username + "," + x + "," + y).getBytes();
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
}
