package net.PixelThrive.Client;

import java.awt.Canvas;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.PixelThrive.Client.PixelException.ExceptionType;
import net.PixelThrive.Client.GUI.CommandsList;
import net.PixelThrive.Client.GUI.Console;
import net.PixelThrive.Client.GUI.GUI;
import net.PixelThrive.Client.GUI.IGUIBlock;
import net.PixelThrive.Client.GUI.Inventory;
import net.PixelThrive.Client.GUI.MainMenu;
import net.PixelThrive.Client.GUI.Shop;
import net.PixelThrive.Client.GUI.SplashScreen;
import net.PixelThrive.Client.GUI.DebugMenu;
import net.PixelThrive.Client.GUI.TextInput;
import net.PixelThrive.Client.audio.SoundSystem;
import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.buff.Buffs;
import net.PixelThrive.Client.entities.Entity;
import net.PixelThrive.Client.entities.Player;
import net.PixelThrive.Client.entities.particles.Particle;
import net.PixelThrive.Client.entities.tileentities.Chest;
import net.PixelThrive.Client.entities.tileentities.TileEntity;
import net.PixelThrive.Client.net.Client;
import net.PixelThrive.Client.net.Server;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.skills.SkillsGUI;
import net.PixelThrive.Client.world.Sky;
import net.PixelThrive.Client.world.Tile;
import net.PixelThrive.Client.world.World;
import net.PixelThrive.Server.packets.Packet00Login;

public class Main extends Canvas implements Runnable
{
	private static final long serialVersionUID = 1L;

	//Dimensions and frame
	public static int WIDTH;
	public static int HEIGHT;
	public static int SCALE = 3;
	public static Dimension size;
	public static Dimension pixel;
	private JFrame frame;
	public static final String TITLE = "PixelThrive", VERSION = "v0.1";
	public static boolean isFullScreen = true;
	public static int cameraOffsX, cameraOffsY;
	public static final Image icon = new LoadedImage("icon.png").getImage();

	//Render
	private Image screen;
	public static Render render;
	public static double sX = 0, sY = 0;

	//Thread
	public boolean isRunning = false;
	private Thread thread;
	private static Main game;
	public static Font gameFont;

	//Input
	public static Point mse = new Point(0, 0);
	public static int mouseX = 0, mouseY = 0;
	public static Input key;

	//Arrays
	private static List<GUI> guis = new CopyOnWriteArrayList<GUI>(), activeGUI = new CopyOnWriteArrayList<GUI>();
	private static List<IGUIBlock> blockGUIs = new CopyOnWriteArrayList<IGUIBlock>();
	private static List<TileEntity> tileEntityGUIs = new CopyOnWriteArrayList<TileEntity>();
	private static List<Entity> entities = new CopyOnWriteArrayList<Entity>();
	private static List<Player> players = new CopyOnWriteArrayList<Player>();
	private static List<Particle> particles = new CopyOnWriteArrayList<Particle>();
	public static List<TextInput> textInputs = new CopyOnWriteArrayList<TextInput>();

	//Objects
	public static LoadingBar load;
	public static Sky sky;
	public static World world;
	public static Player player;
	public static Inventory inv;
	public static SkillsGUI skl;
	public static CommandsList com;
	public static DebugMenu debug;
	public static Shop shop;
	public static Console console;
	public static SoundSystem soundSystem = new SoundSystem();

	public Client socketClient;
	public Server socketServer;
	public WindowHandler windowHandler;
	private static boolean isMP = true;
	private static boolean isRunningServer = false;

	public Main()
	{
		setPreferredSize(size);
		frame = new JFrame();
		key = new Input();
		initFont();
	}

	public void init()
	{
		DeathCause.init();
		soundSystem.init();
	}

	public void initFont()
	{
		try
		{
			gameFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Pixel.ttf"));
			gameFont = gameFont.deriveFont(10.0f);
			GraphicsEnvironment vc = GraphicsEnvironment.getLocalGraphicsEnvironment();
			vc.registerFont(gameFont);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		} 
		catch(FontFormatException e)
		{
			e.printStackTrace();
		}
	}

	public void backToMainMenu()
	{
		clearWorld();
		new MainMenu().openGUI();
	}

	public void clearWorld()
	{
		load = null;
		GUI.clearCurrentGUI();
		Chest.clearChests();
		getGUIs().clear();
		getEntities().clear();
		getPlayers().clear();
		getParticles().clear();
		getBlockGUIs().clear();
		Buffs.clearActiveBuffs();
		textInputs.clear();
		console.clearConsole();
		console.closeGUI();
		world = null;
		player = null;
		sky = null;
		inv.closeGUI();
		inv = null;
		skl.closeGUI();
		skl = null;
		com.closeGUI();
		com = null;
		debug.closeGUI();
		debug = null;
		shop.closeGUI();
		shop = null;
	}

	public static synchronized List<Entity> getEntities()
	{
		return entities;
	}

	public static synchronized List<TileEntity> getTileEntitiesGUIs()
	{
		return tileEntityGUIs;
	}

	public static synchronized List<GUI> getGUIs()
	{
		return guis;
	}

	public static synchronized List<GUI> getActiveGUIs()
	{
		return activeGUI;
	}

	public static synchronized List<IGUIBlock> getBlockGUIs()
	{
		return blockGUIs;
	}

	public static synchronized List<Player> getPlayers()
	{
		return players;
	}

	public static synchronized List<Particle> getParticles()
	{
		return particles;
	}

	public static void main(String[] args)
	{
		int option = JOptionPane.showConfirmDialog(game, "Run Fullscreen?", TITLE, 0, 0, new ImageIcon(icon));
		if(option == 0)
		{
			WIDTH = getFullScreenWidth() / SCALE;
			HEIGHT = getFullScreenHeight() / SCALE;
			size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
			pixel = new Dimension(size.width / SCALE, size.height / SCALE);
			game = new Main();
			cameraOffsY = getFullScreenHeight() / 6;
			isFullScreen = true;
			game.frame.setExtendedState(Frame.MAXIMIZED_BOTH);
			game.frame.setResizable(false);
			game.frame.setUndecorated(true);
		}
		else if(option == 1)
		{
			isFullScreen = false;
			WIDTH = 330;
			HEIGHT = WIDTH / 16 * 9;
			size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
			pixel = new Dimension(size.width / SCALE, size.height / SCALE);
			game = new Main();
			cameraOffsY = 80;
			game.frame.setResizable(false);
			game.frame.setUndecorated(false);
		}
		else System.exit(0);
		cameraOffsX = WIDTH / 2 - 5;
		game.frame.setTitle(TITLE);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setIconImage(icon);
		game.frame.setVisible(true);
		game.start();
	}

	public synchronized void start()
	{
		requestFocus();
		isRunning = true;
		thread = new Thread(this, TITLE);
		thread.start();
		initGame();
		setCustomCursor("Cursor.png");
		init();
	}

	public void stop()
	{
		try
		{
			thread.join();
		}
		catch(Exception e){}
	}

	public static int getFullScreenWidth()
	{
		return Toolkit.getDefaultToolkit().getScreenSize().width;
	}

	public static int getFullScreenHeight()
	{
		return Toolkit.getDefaultToolkit().getScreenSize().height;
	}

	public void initGame()
	{
		new SplashScreen();
		addKeyListener(key);
		addMouseListener(key);
		addMouseMotionListener(key);
		addMouseWheelListener(key);
	}

	public void startGame(String charName, int worldSize, boolean customSize, int gamemode, String seed)
	{
		load = new LoadingBar();
		load.open();
		if(world != null) world.hasGenerated = false;
		try
		{
			world = new World(worldSize, customSize, convertSeed(seed));
		}
		catch(PixelException e)
		{
			throw new PixelException(e.getLocalizedMessage(), ExceptionType.WORLDGEN, null, e.getCause());
		}
		if(world.hasGenerated)
		{
			load.close();
			sky = new Sky();
			inv = new Inventory();
			skl = new SkillsGUI();
			skl.activate();
			com = new CommandsList();
			com.activate();
			console = new Console();
			try
			{
				int x = viableSpawnX();
				int y = viableSpawnY();
				player = new Player(x, y, key, charName);
				player.spawnEntity();
				if(gamemode == 1) player.isCreative = true;
				else player.isCreative = false;
				world.setBlock(x/Tile.tileSize, y/Tile.tileSize - 2, Block.stone.blockID);
				world.setBlock(x/Tile.tileSize, y/Tile.tileSize - 1, Block.air.blockID);
				world.setBlock(x/Tile.tileSize, y/Tile.tileSize, Block.air.blockID);
				world.setBlock(x/Tile.tileSize, y/Tile.tileSize + 1, Block.air.blockID);
				debug = new DebugMenu(world, player);
				debug.activate();
				shop = new Shop(player);
				shop.activate();
			}
			catch(Exception e){}
		}
	}

	public int viableSpawnX()
	{
		for(int x = world.worldW / 3; x < world.worldW / 2; x++)
			for(int y = world.worldH / 2 - 10; y < world.worldH / 2 + 10; y++)
				if(world.getBlock(x, y) == Block.air && world.getBlock(x, y - 1) == Block.air && world.getBlock(x, y + 1) != Block.air && world.getBlock(x, y + 1) != Block.sandStone)
					return x * Tile.tileSize;
		return 0;
	}

	public int viableSpawnY()
	{
		for(int x = world.worldW / 3; x < world.worldW / 2; x++)
			for(int y = world.worldH / 2 - 10; y < world.worldH / 2 + 10; y++)
				if(world.getBlock(x, y) == Block.air && world.getBlock(x, y - 1) == Block.air && world.getBlock(x, y + 1) != Block.air && world.getBlock(x, y + 1) != Block.sandStone)
					return y * Tile.tileSize;
		return 0;
	}

	public static void quit()
	{
		System.exit(0);
	}

	public void run()
	{
		screen = createVolatileImage(WIDTH, HEIGHT);
		long lastTick = System.nanoTime();
		double ns = 1000000000.0 / 60.0; //Updates 60 times p sec.
		double delta = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while(isRunning)
		{
			long now = System.nanoTime();
			delta += (now - lastTick) / ns;
			lastTick = now;
			while(delta >= 1)
			{
				GameLoop.tick();	
				delta--;
			}
			render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000)
			{
				timer += 1000;
				if(frame != null) frame.setTitle(TITLE + " | " + frames + " FPS");
				frames = 0;
			}
			try
			{
				Thread.sleep(40);
			}
			catch(Exception e){}
		}
	}

	public static Main getGameInstance()
	{
		return game;
	}

	public void render()
	{
		Graphics2D g = (Graphics2D)screen.getGraphics();
		if(render == null) render = new Render(g);
		g = (Graphics2D) frame.getGraphics();
		GameLoop.render();
		g = (Graphics2D) getGraphics();
		g.drawImage(screen, 0, 0, size.width, size.height, 0, 0, pixel.width, pixel.height, null);
		g.dispose();
	}

	public static <T> void consoleMessage(T t)
	{
		System.out.println("[PixelThrive] " + String.valueOf(t));
	}

	public static <T> void consoleError(T t)
	{
		System.err.println("[PixelThrive] " + String.valueOf(t));
	}

	public void resetCursor()
	{
		frame.getRootPane().setCursor(Cursor.getDefaultCursor());
	}

	public void setCustomCursor(String path)
	{
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(this.getClass().getClassLoader().getResource(path));
		Cursor c = toolkit.createCustomCursor(image, new Point(getX(), getY()), "PixelThriveCursor");
		frame.getRootPane().setCursor(c);	
	}

	public void startMP(String charName, boolean host, String IP, int gamemode)
	{
		setMP(true);
		if(host)
		{
			socketServer = new Server(this);
			socketServer.start();
			isRunningServer = true;
		}
		socketClient = new Client(this, IP);
		socketClient.start();
		windowHandler = new WindowHandler(this);
		startGame(charName, 0, false, gamemode, MainMenu.getRandomSeed(18));
		Packet00Login loginPacket = new Packet00Login(player.getName(), (int)player.getX(), (int)player.getY());
		if(socketServer != null)
		{	
			consoleMessage("Launching server...");
			//socketServer.addConnection((PlayerMP)player, loginPacket);
			consoleMessage("Server successfully booted.");
		}
		loginPacket.writeData(socketClient);
	}

	public boolean isMP()
	{
		return isMP;
	}

	public boolean isRunningServer()
	{
		return isRunningServer;
	}

	public void setMP(boolean MP)
	{
		isMP = MP;
	}

	public JFrame getFrame()
	{
		return frame;
	}

	public static void captureScreenShot(String fileName)
	{
		try
		{
			Image imgData = game.screen.getScaledInstance(WIDTH * SCALE, HEIGHT * SCALE, Image.SCALE_SMOOTH);     
			BufferedImage g = new BufferedImage(imgData.getWidth(null), imgData.getHeight(null), BufferedImage.TYPE_INT_RGB);
			g.getGraphics().drawImage(imgData, 0, 0, null);
			new File("C:/Users/" + System.getProperty("user.name") + "/PixelBolt/").mkdir();	
			new File("C:/Users/" + System.getProperty("user.name") + "/PixelBolt/" + Main.TITLE + "/").mkdir();	
			File file = new File("C:/Users/" + System.getProperty("user.name") + "/PixelBolt/" + Main.TITLE + "/" + fileName + ".png");
			file.createNewFile();
			ImageIO.write(g, "png", file);
			GameLoop.noticeCapture(g);
		}
		catch(Exception e){};
	}

	public static void createOptionsFile()
	{
		try
		{
			new File("C:/Users/" + System.getProperty("user.name") + "/PixelBolt/").mkdir();	
			new File("C:/Users/" + System.getProperty("user.name") + "/PixelBolt/" + Main.TITLE + "/").mkdir();	
			File file = new File("C:/Users/" + System.getProperty("user.name") + "/PixelBolt/" + Main.TITLE + "/Options.txt");
			file.createNewFile();
		}
		catch(Exception e){};
	}

	public static String getTime()
	{
		Calendar cal = Calendar.getInstance();
		cal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("y-MMM-d-HH.mm.s.S");
		return String.valueOf(sdf.format(cal.getTime()));
	}

	public Image getScreen()
	{
		return screen;
	}

	public static long convertSeed(String seed)
	{
		long finalSeed = -1;
		for(int i = 0; i < seed.length(); i++)
		{
			if(Character.isLetter(seed.charAt(i)))
			{
				int num = 0;
				Character c = seed.charAt(i);
				num = c.charValue() % 10;
				String s1 = seed.substring(0, i);
				String s2 = seed.substring(i + 1);
				seed = s1 + num + s2;
				seed = seed.replaceAll(" ", "");
			}
		}
		finalSeed = Long.parseLong(seed.trim());
		return finalSeed;
	}

	public static boolean intToBoolean(int i)
	{
		if(i <= 0) return false;
		else return true;
	}

	public static int booleanToInt(boolean i)
	{
		if(!i) return 0;
		else return 1;
	}

	public static int correctInt(int i, int min, int max)
	{
		if(i > max) i = max;
		if(i < min) i = min;
		return i;
	}

	public static float correctFloat(float i, float min, float max)
	{
		if(i > max) i = max;
		if(i < min) i = min;
		return i;
	}
}
