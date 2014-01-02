package net.PixelThrive.Server.packets;

import net.PixelThrive.Client.net.Client;
import net.PixelThrive.Client.net.Server;

public class Packet02Move extends Packet
{
	private String username;
	private int x, y;
	
	private boolean isMoving = false;
	private int dir = 0;
	private int animation = 0;

	public Packet02Move(byte[] data)
	{
		super(02);
		String[] dataArray = readData(data).split(",");
		this.username = dataArray[0];
		this.x = Integer.parseInt(dataArray[1]);
		this.y = Integer.parseInt(dataArray[2]);
		this.animation = Integer.parseInt(dataArray[3]);
		this.dir = Integer.parseInt(dataArray[4]);
		this.isMoving = Integer.parseInt(dataArray[5]) == 0;
	}

	public Packet02Move(String name, int x, int y, int animation, int dir, boolean isMoving)
	{
		super(02);
		this.username = name;
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.animation = animation;
		this.isMoving = isMoving;
	}

	@Override
	public void writeData(Client client)
	{
		client.sendData(getData());
	}

	@Override
	public void writeData(Server server)
	{
	//	server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData()
	{
		return ("02" + this.username + "," + this.x + "," + this.y + "," + this.animation + "," + this.dir + "," + (this.isMoving ? 1 : 0)).getBytes();
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
	
	public int getAnimation()
	{
		return this.animation;
	}
	
	public boolean isMoving()
	{
		return this.isMoving;
	}
	
	public int getDir()
	{
		return this.dir;
	}
}
