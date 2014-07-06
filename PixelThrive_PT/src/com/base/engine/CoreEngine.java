package com.base.engine;

public class CoreEngine 
{
	private boolean isRunning;
	private Game game;
	private int width, height;
	private double frameTime;
	private String gameTitle;

	public CoreEngine(Vector2f size, double frameRate, Game game)
	{
		this.isRunning = false;
		this.game = game;
		this.width = (int)size.getX();
		this.height = (int)size.getY();
		this.frameTime = 1.0 / frameRate;
	}
	
	private void createWindow(String title)
	{
		gameTitle = title;
		Window.createWindow(width, height, title);
		Render.init(width, height);
	}

	public void start()
	{
		createWindow(game.getTitle());
		if(isRunning) return;
		run();
	}

	public void stop()
	{
		if(!isRunning) return;
		isRunning = false;
	}

	private void run()
	{
		isRunning = true;
		int frames = 0;
		double frameCounter = 0; //long
		game.init();
		double lastTime = Time.getTime();
		double unprocessedTime = 0.0;
		while(isRunning)
		{
			boolean render = false;
			double startTime = Time.getTime();
			double pastTime = startTime - lastTime;
			lastTime = startTime;
			unprocessedTime += pastTime;
			frameCounter += pastTime;
			while(unprocessedTime > frameTime)
			{
				render = true;
				unprocessedTime -= frameTime;
				if(Window.isCloseRequested()) stop();
				game.input((float)frameTime);
				Input.update();
				game.update((float)frameTime);
				if(frameCounter >= 1.0)
				{
//					consoleMessage(frames + " FPS");
					Window.setTitle(gameTitle + " - FPS: " + frames);
					frames = 0;
					frameCounter = 0;
				}
			}
			if(render)
			{
				Render.render(game);
				Window.render();
				frames++;
			}
			else Window.sleep(1);
		}
		cleanUp();
	}

	private void cleanUp()
	{
		Window.dispose();
	}
	
	public static <T> void consoleMessage(T t)
	{
		System.out.println("[ENGINE]: " + String.valueOf(t));
	}
	
	public static <T> void consoleError(T t)
	{
		System.err.println("[ENGINE]: " + String.valueOf(t));
	}
}
