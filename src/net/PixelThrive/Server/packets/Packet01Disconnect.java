package net.PixelThrive.Server.packets;

import net.PixelThrive.Client.net.Client;
import net.PixelThrive.Client.net.Server;

public class Packet01Disconnect extends Packet
{
	private String username;

	public Packet01Disconnect(byte[] data)
	{
		super(01);
		this.username = readData(data);
	}

	public Packet01Disconnect(String name)
	{
		super(01);
		this.username = name;
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
		return ("01" + this.username).getBytes();
	}

	public String getUsername()
	{
		return username;
	}
}
