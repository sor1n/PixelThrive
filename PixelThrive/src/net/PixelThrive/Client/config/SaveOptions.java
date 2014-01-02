package net.PixelThrive.Client.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.GUI.Options;

public class SaveOptions
{	
	public static void saveOptions()
	{
		try 
		{
			int soundVol = (int)(Options.SOUND_VOLUME * 100);
			soundVol = Main.correctInt(soundVol, 0, 100);
			int musicVol = (int)(Options.MUSIC_VOLUME * 100);
			musicVol = Main.correctInt(musicVol, 0, 100);
			int blockBor = Main.booleanToInt(Options.BLOCK_BORDERS);
			PrintWriter writer = new PrintWriter(new File("C:/Users/" + System.getProperty("user.name") + "/PixelBolt/" + Main.TITLE + "/Options.txt"));
			writer.println("[MusicVol]:" + String.valueOf(musicVol));
			writer.println("[SoundVol]:" + String.valueOf(soundVol));
			writer.println("[BlockBorders]:" + String.valueOf(blockBor));
			writer.close();
		} 
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	public static String loadOptions()
	{
		InputStream is = null;
		byte[] b = null;
		String text = null;
		try 
		{
			File file = new File("C:/Users/" + System.getProperty("user.name") + "/PixelBolt/" + Main.TITLE + "/Options.txt");
			is = new FileInputStream(file);
			b = new byte[is.available()];
			is.read(b);
			text = new String(b);
			is.close();
		} 
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		if(text != null) return text;
		else return null;
	}
}
