package net.PixelThrive.Client.GUI;

import java.awt.Color;
import java.util.Random;

import net.PixelThrive.Client.*;
import net.PixelThrive.Client.Input.Key;
import net.PixelThrive.Client.audio.SoundSystem;
import net.PixelThrive.Client.config.SaveOptions;
import net.PixelThrive.Client.renders.Render;

public class MainMenu extends GUI
{	
	private Button play = new Button(Main.WIDTH / 2 - (125 / 2), 70, 125, 20, "Play Singleplayer", 100, 200, 100);
	private Button playMP = new Button(Main.WIDTH / 2 - (125 / 2), 95, 125, 20, "Play Multiplayer", 50, 100, 200);
	private Button options = new Button(Main.WIDTH / 2 - (125 / 2), 120, 125, 20, "Options", 200, 70, 0);
	private Button exit = new Button(Main.WIDTH / 2 - (125 / 2), 145, 125, 20, "Quit", 0, 200, 200);
	private Button small = new Button(Main.WIDTH / 2 - 20, 45, 50, 20, "Small", 100, 200, 100);
	private Button medium = new Button(Main.WIDTH / 2 - 20, 67, 50, 20, "Medium", 100, 200, 100);
	private Button large = new Button(Main.WIDTH / 2 - 20, 89, 50, 20, "Large", 100, 200, 100);
	private Button custom = new Button(Main.WIDTH / 2 - 20, 111, 50, 20, "Custom", 100, 200, 100);
	private Button customBox = new Button(Main.WIDTH / 2 - 20, 111, 50, 20, "", 100, 200, 100);
	private Button infinite = new Button(Main.WIDTH / 2 - 20, 133, 50, 20, "%infinite%", 100, 200, 100, 15);
	private Button back = new Button(10, 10, 33, 20, "Back", 100, 200, 100);
	private Button accept = new Button(Main.WIDTH / 2 - 60, 95, 125, 20, "Accept", 2, 80, 190);
	private Button randSeed = new Button(Main.WIDTH / 2 + 69, 165, 52, 10, "random", 150, 120, 90);

	private Button createServer = new Button(Main.WIDTH / 2 - (125 / 2), 70, 125, 20, "Create Server", 120, 150, 50);
	private Button joinServer = new Button(Main.WIDTH / 2 - (125 / 2), 95, 125, 20, "Join Server", 2, 80, 190);

	private Button survival = new Button(Main.WIDTH / 2 + 40, 80, 60, 20, "Survival", 25, 27, 70);
	private Button creative = new Button(Main.WIDTH / 2 + 40, 100, 60, 20, "Creative", 25, 27, 70);

	private Options option = new Options(this);

	public ExitPrompt exitPrompt;

	private int menu = 0, clickDelay = 0, redDelay = 0;
	private boolean customSize = false, MP = false;

	public int gamemode = 0;

	public TextInput customS, IPInput, charName, seed;
	private String name = "", optionString;
	private String[] ops;

	public MainMenu()
	{
		Main.getGameInstance().setMP(false);
		clickDelay = 15;
		canBeHidden = false;
		Main.createOptionsFile();
		optionString = SaveOptions.loadOptions();
		if(optionString != null) applyLoadedOptions();
		SoundSystem.playMusic("OST", true);
	}
	
	public static int findOption(String[] ops, String op)
	{
		for(int i = 0; i < ops.length; i++) if(ops[i].contains(op)) return i;
		return 0;
	}
	
	public void applyLoadedOptions()
	{
		ops = optionString.split("\n");
		//SoundVol
		int soundVol = 100;
		try
		{
			soundVol = Integer.valueOf(ops[findOption(ops, "[SoundVol]")].substring(ops[findOption(ops, "[SoundVol]")].indexOf(':') + 1).trim());
		}
		catch(NumberFormatException e){soundVol = 0;}
		soundVol = Main.correctInt(soundVol, 0, 100);
		option.setSoundVolume(soundVol);
		//MusicVol
		int musicVol = 100;
		try
		{
			musicVol = Integer.valueOf(ops[findOption(ops, "[MusicVol]")].substring(ops[findOption(ops, "[MusicVol]")].indexOf(':') + 1).trim());
		}
		catch(NumberFormatException e){musicVol = 0;}
		musicVol = Main.correctInt(musicVol, 0, 100);
		option.setMusicVolume(musicVol);
		//BlockBorders
		int blockBorders = 0;
		try
		{
			blockBorders = Integer.valueOf(ops[findOption(ops, "[BlockBorders]")].substring(ops[findOption(ops, "[BlockBorders]")].indexOf(':') + 1).trim());
		}
		catch(NumberFormatException e){blockBorders = 0;}	
		blockBorders = Main.correctInt(blockBorders, 0, 1);
		option.setBlockBorders(blockBorders);
		option.updateOptions();
	}

	public void tick()
	{	
		if(clickDelay > 0) clickDelay--;
		if(redDelay > 0) redDelay--;

		if(isActive)
		{
			if(menu == 0)
			{
				if(exitPrompt != null) exitPrompt.tick();
				else
				{
					play.tick();
					playMP.tick();
					options.tick();
					exit.tick();

					if(play.isClicked() && clickDelay <= 0)
					{
						exitPrompt = null;
						clickDelay = 20;
						menu = 3;
						MP = false;
					}
					if(options.isClicked() && clickDelay <= 0)
					{
						exitPrompt = null;
						option.openGUI();
					}
					//			if(playMP.isClicked() && clickDelay <= 0)
					//			{
					//				clickDelay = 20;
					//				menu = 3;
					//				MP = true;
					//			}
					if(exit.isClicked() && clickDelay <= 0) exitPrompt = new ExitPrompt(this);
				}
			}
			if(menu == 1)
			{
				if(Main.key.isMouseLeft)
				{
					if(seed != null && seed.bounds.contains(Main.mouseX, Main.mouseY)) seed.isActive = true;
					else if(seed != null) seed.isActive = false;
					if(customS != null && customS.bounds.contains(Main.mouseX, Main.mouseY)) customS.isActive = true;
					else if(customS != null) customS.isActive = false;
				}
				if(creative.isClicked()) gamemode = 1;
				if(survival.isClicked()) gamemode = 0;
				survival.tick();
				creative.tick();
				small.tick();
				medium.tick();
				large.tick();
				randSeed.tick();
				back.tick();
				infinite.tick();
				if(!customSize) custom.tick();
				else customBox.tick();

				if(randSeed.isClicked() && clickDelay <= 0)
				{
					clickDelay = 8;
					seed.setTextWithNoLimit(getRandomSeed(seed.getMaxLength()));
				}
				if(back.isClicked() && clickDelay <= 0) backToMenu();
				if(small.isClicked() && clickDelay <= 0)
				{
					String finalSeed = "";
					if(seed.getText() == null || seed.getText().trim().equalsIgnoreCase("")) finalSeed = getRandomSeed(seed.getMaxLength());
					else finalSeed = seed.getText();
					Main.getGameInstance().startGame(name, 0, false, gamemode, finalSeed);
					closeGUI();
				}
				else if(medium.isClicked() && clickDelay <= 0)
				{
					String finalSeed = "";
					if(seed.getText() == null || seed.getText().trim().equalsIgnoreCase("")) finalSeed = getRandomSeed(seed.getMaxLength());
					else finalSeed = seed.getText();
					Main.getGameInstance().startGame(name, 1, false, gamemode, finalSeed);
					closeGUI();
				}
				else if(large.isClicked() && clickDelay <= 0)
				{
					String finalSeed = "";
					if(seed.getText() == null || seed.getText().trim().equalsIgnoreCase("")) finalSeed = getRandomSeed(seed.getMaxLength());
					else finalSeed = seed.getText();
					Main.getGameInstance().startGame(name, 2, false, gamemode, finalSeed);
					closeGUI();
				}
				else if(custom.isClicked() && clickDelay <= 0 && !customSize)
				{
					customSize = true;
					clickDelay = 20;
				}
				else if((customBox.isClicked() || (customS != null && customS.isDone)) && clickDelay <= 0)
				{
					String finalSeed = "";
					if(seed.getText() == null || seed.getText().trim().equalsIgnoreCase("")) finalSeed = getRandomSeed(seed.getMaxLength());
					else finalSeed = seed.getText();
					customS.setText(customS.getText().trim());
					if(!customS.getText().equalsIgnoreCase(""))
					{
						Main.getGameInstance().startGame(name, Integer.parseInt(customS.getText()), true, gamemode, finalSeed);
						closeGUI();
					}
				}
			}
			else if(menu == 2)
			{
				back.tick();
				createServer.tick();
				joinServer.tick();
				if(back.isClicked() && clickDelay <= 0) backToMenu();

				if(clickDelay <= 0)
				{
					if(createServer.isClicked())
					{
						clickDelay = 20;
						if(isValidIP(IPInput.getText()))
						{
							String IP = getValidIP(IPInput.getText());
							Main.getGameInstance().startMP(name, true, IP, gamemode);
							IPInput.closeText();
							closeGUI();
						}
					}
					else if(joinServer.isClicked())
					{
						clickDelay = 20;
						if(isValidIP(IPInput.getText()))
						{
							String IP = getValidIP(IPInput.getText());
							Main.getGameInstance().startMP(name, false, IP, gamemode);
							IPInput.closeText();
							closeGUI();
						}
					}
				}
			}
			else if(menu == 3)
			{
				back.tick();
				accept.tick();
				if(back.isClicked() && clickDelay <= 0) backToMenu();
				if((accept.isClicked() || Key.chatKey.isPressed()) && clickDelay <= 0 && charName.getText().trim().length() > 2)
				{
					clickDelay = 10;
					name = charName.getText();
					charName.setTextNull();
					charName.closeText();
					if(MP) menu = 2;
					else menu = 1;
				}
				if((accept.isClicked() || Key.chatKey.isPressed()) && clickDelay <= 0 && charName.getText().trim().length() <= 2) redDelay = 50;
			}
		}
	}

	public void closeGUI()
	{
		exitPrompt = null;
		if(customS != null) customS.setTextNull();
		if(customS != null) customS.closeText();
		if(seed != null) seed.resetAndClose();
		customS = null;
		SoundSystem.stopMusic("OST");
		Main.getGUIs().remove(this);
	}

	public void render()
	{		
		if(isActive)
		{
			//Main Menu
			if(menu == 0)
			{	
				Render.setColor(60, 180, 220);
				Render.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
				Text.drawStringWithShadow(Main.TITLE, (Main.WIDTH / 2) - 120, 50, Color.WHITE, 40, Main.gameFont);
				Text.drawStringWithShadow(Main.VERSION, Main.WIDTH - 40, Main.HEIGHT - 10, Color.WHITE, 12, Main.gameFont);
				play.render();
				playMP.render();
				options.render();
				exit.render();
				if(exitPrompt != null) exitPrompt.render();
			}
			//SP
			if(menu == 1)
			{
				Render.setColor(60, 180, 220);
				Render.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
				Text.drawStringWithShadow(Main.VERSION, Main.WIDTH - 40, Main.HEIGHT - 10, Color.WHITE, 12, Main.gameFont);
				Text.drawStringWithShadow("World Settings", (Main.WIDTH / 2) - 80, 30, Color.WHITE, 22, Main.gameFont);
				small.render();
				medium.render();
				large.render();
				back.render();
				survival.render();
				creative.render();
				infinite.render();
				randSeed.render();
				Render.setColor(90, 90, 90, 120);
				if(gamemode == 0) Render.fillRect(survival.button.x, survival.button.y, survival.button.width, survival.button.height);
				else Render.fillRect(creative.button.x, creative.button.y, creative.button.width, creative.button.height);
				if(!customSize) custom.render();
				else customBox.render();
				if(customSize) 
				{
					Render.setColor(Color.black);
					Render.drawRect(Main.WIDTH / 2 - 9, 114, 29, 13);
					Render.setColor(new Color(0, 0, 0, 100));
					Render.fillRect(Main.WIDTH / 2 - 9, 114, 29, 13);
					if(customS == null || customS.isReset) customS = new TextInput(4, Main.WIDTH / 2 - 5, 116, 10, true, false);
				}
				Render.setColor(Color.black);
				Render.drawRect(Main.WIDTH / 2 - ((18 * 7) / 2) + 4, 164, 18 * 7, 13);
				Render.setColor(new Color(0, 0, 0, 100));
				Render.fillRect(Main.WIDTH / 2 - ((18 * 7) / 2) + 4, 164, 18 * 7, 13);
				if(seed == null || seed.isReset) seed = new TextInput(18, Main.WIDTH / 2 - ((18 * 7) / 2) + 6, 166, 10, false, false);
				seed.letterOnly = true;
				Text.drawStringWithShadow("Seed", Main.WIDTH / 2 - (Render.stringWidth("Seed", 10) / 2) + 6, 163, 0xFFFFFF, 10, Main.gameFont);
			}
			//MP
			if(menu == 2)
			{
				Render.setColor(60, 180, 220);
				Render.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
				Text.drawStringWithShadow(Main.VERSION, Main.WIDTH - 40, Main.HEIGHT - 10, Color.WHITE, 12, Main.gameFont);
				Text.drawStringWithShadow("Enter an IP", (Main.WIDTH / 2) - 70, 40, Color.WHITE, 22, Main.gameFont);
				Render.setColor(new Color(0, 0, 0, 100));
				Render.fillRect(Main.WIDTH / 2 - 57, 123, 114, 13);
				if(IPInput == null || IPInput.isReset) IPInput = new TextInput(16, Main.WIDTH / 2 - 53, 124, 10, true, true);
				IPInput.render();
				back.render();
				createServer.render();
				joinServer.render();
			}
			//Name
			if(menu == 3)
			{
				Render.setColor(60, 180, 220);
				Render.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
				Text.drawStringWithShadow(Main.VERSION, Main.WIDTH - 40, Main.HEIGHT - 10, Color.WHITE, 12, Main.gameFont);
				Text.drawStringWithShadow("Character Name", (Main.WIDTH / 2) - 90, 40, Color.WHITE, 22, Main.gameFont);
				back.render();
				Render.setColor(new Color(redDelay * 5, 0, 0, 100));
				Render.fillRect(Main.WIDTH / 2 - 57, 123, 120, 13);
				Render.setColor(240, 240, 240, redDelay * 3);
				Render.setFont(Main.gameFont, 15);
				Render.drawString("Invalid Name", (Main.WIDTH / 2 - 57) + 62 - (Render.stringWidth("Invalid Name", 15) / 2), 135);
				if(charName == null || charName.isReset) charName = new TextInput(18, Main.WIDTH / 2 - 55, 124, 10, false, false);
				charName.render();
				accept.render();
			}
		}
	}

	public void backToMenu()
	{
		menu = 0;
		exitPrompt = null;
		if(IPInput != null) IPInput.resetAndClose();
		if(charName != null) charName.resetAndClose();
		if(customS != null) customS.resetAndClose();
		if(seed != null) seed.resetAndClose();
		if(custom != null) custom.reset();
		customSize = false;
	}

	public boolean isValidIP(String IP)
	{
		IP = IP.trim();
		if(IP.equalsIgnoreCase("localhost")) return true;
		if(IP.length() < 7 || IP.isEmpty() || IP == null) return false;
		int dotCount = 0;
		char dot;
		for(int i = 0; i < IP.length(); i++)
		{
			dot = IP.charAt(i);
			if(dot == '.') dotCount++;
		}
		if(dotCount != 3) return false;
		else return true;
	}

	public String getValidIP(String IP)
	{
		return IP.trim();
	}

	public static String getRandomSeed(int length)
	{
		String end = "";
		for(int i = 0; i < length; i++)
		{
			if(new Random().nextBoolean())
			{
				String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				int character = (int)(Math.random() * 26);
				String s = alphabet.substring(character, character + 1);
				end += s;
			}
			else end += new Random().nextInt(10);
		}
		return end;
	}
}
