package net.PixelThrive.Client.audio;

import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;
import javax.sound.sampled.Mixer;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.PixelException;
import net.PixelThrive.Client.PixelException.ExceptionType;
import net.PixelThrive.Client.GUI.*;

import org.newdawn.easyogg.OggClip;

public class SoundSystem 
{	
	protected static List<Music> music = new ArrayList<Music>();
	protected static List<Sound> sound = new ArrayList<Sound>();

	public static enum Music
	{
		MainTheme("PixelThriveOST", "OST");

		Music(String path, String name)
		{
			this.name = name;
			this.path = "audio/music/" + path + ".ogg";
			try 
			{
				clip = new OggClip(this.path);
				music.add(this);
			}
			catch(Exception e)
			{
				throw new PixelException(e.getLocalizedMessage(), ExceptionType.AUDIOINIT, path, e.getCause());
			}
		}
		protected String path, name;
		protected OggClip clip;
		public String getName()
		{
			return name;
		}
		public String getPath()
		{
			return path;
		}
		public void play()
		{
			if(clip != null && Options.MUSIC_VOLUME > 0.0F) clip.play();
		}
		public void stop()
		{
			if(clip != null) clip.stop();
		}
		public void pause()
		{
			if(clip != null) clip.pause();
		}
		public void resume()
		{
			if(clip != null && Options.MUSIC_VOLUME > 0.0F) clip.resume();
			else clip.stop();
		}
		public static void setMainVolume(float f)
		{
			for(Music m : music) m.clip.setGain(f);
		}
		public void setVolume(float f)
		{
			clip.setGain(f);
		}
		public boolean isPlaying()
		{
			return !clip.isPaused();
		}
		public void loop()
		{
			if(clip != null && Options.MUSIC_VOLUME > 0.0F) clip.loop();
		}
		public void setBalance(float f)
		{
			clip.setBalance(f);
		}
	}

	public static enum Sound
	{
		Growl("growl", "growl"),
		Grass("grass", "grass"),
		Drill("drill", "drill"),
		Click("click", "click");

		Sound(String path, String name)
		{
			this.name = name;
			this.path = "audio/" + path + ".ogg";
			try 
			{
				clip = new OggClip(this.path);
				sound.add(this);
			}
			catch(Exception e)
			{
				throw new PixelException(e.getLocalizedMessage(), ExceptionType.AUDIOINIT, path, e.getCause());
			}
		}
		protected String path, name;
		protected OggClip clip;
		public String getName()
		{
			return name;
		}
		public String getPath()
		{
			return path;
		}
		public void play()
		{
			if(clip != null && Options.SOUND_VOLUME > 0.0F) clip.play();
		}
		public void loop()
		{
			if(clip != null && Options.SOUND_VOLUME > 0.0F) clip.loop();
		}
		public void stop()
		{
			if(clip != null) clip.stop();
		}
		public void pause()
		{
			if(clip != null) clip.pause();
		}
		public void resume()
		{
			if(clip != null && Options.SOUND_VOLUME > 0.0F) clip.resume();
			else clip.stop();
		}
		public boolean isPaused()
		{
			return clip.isPaused();
		}
		public boolean isPlaying()
		{
			return !clip.stopped() & !clip.isPaused();
		}
		public static void setMainVolume(float f)
		{
			for(Sound m : sound) m.clip.setGain(f);
		}
		public void setVolume(float f)
		{
			clip.setGain(f);
		}
		public void setBalance(float f)
		{
			clip.setBalance(f);
		}
	}

	/**
	 * Initializes all sounds and music
	 */
	public void init()
	{
		Music.values();
		Sound.values();
		Music.setMainVolume(1.0f);
		Sound.setMainVolume(1.0f);
	}

	/**
	 * Plays a music track once
	 * @param musicName The ID of the track
	 * @param loop true = loop, false = play once
	 */
	public static void playMusic(String musicName, boolean loop)
	{
		for(Music m : music)
			if(m.getName().equalsIgnoreCase(musicName))
			{
				if(!loop) m.play();
				else m.loop();
				break;
			}
	}

	/**
	 * Stops a music track
	 * @param musicName The name of the track ex. <code>OST</code>
	 */
	public static void stopMusic(String musicName)
	{
		for(Music m : music)
			if(m.getName().equalsIgnoreCase(musicName))
			{
				m.stop();
				break;
			}
	}

	public static void pauseMenu(String musicName)
	{
		for(Music m : music)
			if(m.getName().equalsIgnoreCase(musicName))
			{
				m.pause();
				break;
			}
	}

	public static void resumeMenu(String musicName)
	{
		for(Music m : music)
			if(m.getName().equalsIgnoreCase(musicName))
			{
				m.resume();
				break;
			}
	}

	public static Sound getSoundInstance(String soundName)
	{
		for(Sound m : sound)
			if(m.getName().equalsIgnoreCase(soundName))
			{
				Sound sound = m;
				return sound;
			}
		return null;
	}

	/**
	 * Quick play a music track, without saving it to the system.
	 * @param fileName The name of the file which should end with ".ogg"
	 */
	public static void quickPlayMusic(String fileName)
	{
		try 
		{
			new OggClip("audio/music/" + fileName).play();
		}
		catch(Exception e) {}
	}

	/**
	 * pauses all music and sounds
	 */
	public static void pauseAll()
	{
		for(int i = 0; i < music.size(); i++) if(music.get(i).isPlaying()) music.get(i).pause();
		for(int i = 0; i < sound.size(); i++) if(!sound.get(i).isPaused()) sound.get(i).pause();
	}

	/**
	 * resumes all music and sounds
	 */
	public static void resumeAll()
	{
		for(int i = 0; i < music.size(); i++) if(!music.get(i).isPlaying()) music.get(i).resume();
		for(int i = 0; i < sound.size(); i++) if(sound.get(i).isPaused()) sound.get(i).resume();
	}

	/**
	 * Quick play a sound, without saving it to the system.
	 * @param fileName The name of the file which should end with ".ogg"
	 */
	public static void quickPlaySound(String fileName)
	{
		try 
		{
			new OggClip("audio/" + fileName).play();
		}
		catch(Exception e) {}
	}

	public FloatControl getMasterVolumeControl()
	{	
		try
		{
			Mixer.Info mixers[] = AudioSystem.getMixerInfo();
			for (Mixer.Info mixerInfo : mixers)
			{
				Mixer mixer = AudioSystem.getMixer(mixerInfo);
				mixer.open();
				for(Line.Info info : mixer.getTargetLineInfo())
				{
					if(info.toString().contains("SPEAKER"))
					{
						Line line = mixer.getLine(info);
						try
						{
							line.open();
						} 
						catch(IllegalArgumentException iae){}
						return (FloatControl) line.getControl(FloatControl.Type.VOLUME);
					}
				}
			}
		} 
		catch(Exception ex)
		{
			Main.consoleError("Problem creating volume control object: " + ex);
		}
		return null;
	}

	/**
	 * Plays a sound
	 * @param SoundName The name of the sound in the library
	 * @param loop true = loop, false = play once
	 */
	public static void playSound(String soundName, boolean loop)
	{
		for(Sound s : sound)
		{
			if(s.getName().equalsIgnoreCase(soundName) && !s.isPlaying())
			{
				//if(s.isPlaying()) s.stop();
				if(!loop) s.play();
				else s.loop();
			}
		}
	}

	/**
	 * Stops a sound
	 * @param SoundName The name of the sound in the library
	 */
	public static void stopSound(String soundName)
	{
		for(Sound s : sound) if(s.getName().equalsIgnoreCase(soundName)) s.stop();
	}

	/**
	 * Adds a new sound instance to the library
	 * @param mus
	 */
	public static void addSound(Sound sounds)
	{
		sound.add(sounds);
	}

	/**
	 * Adds a new music instance to the library
	 * @param mus
	 */
	public static void addMusic(Music mus)
	{
		music.add(mus);
	}

	public static boolean isSoundPlaying(String soundName)
	{
		for(Sound s : sound)
		{
			if(s.getName().equalsIgnoreCase(soundName))
			{
				if(s.isPlaying()) return true;
				else break;
			}
		}
		return false;
	}
}
