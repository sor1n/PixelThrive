package net.PixelThrive.Client.GUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.PixelThrive.Client.Input;
import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.Input.Key;
import net.PixelThrive.Client.renders.Render;

public class Control implements KeyListener
{
	private int x, y, clickDelay = 10, keyID;
	private String name, keyName;
	private Button button;
	public static final int HEIGHT = 12, WIDTH = 46;
	public boolean isListening = false;

	public Control(int x, int y, int key, String keyName)
	{
		this.keyID = key;
		this.x = x;
		this.y = y;
		name = keyName;
		this.keyName = KeyEvent.getKeyText(Input.Key.values()[key].getKey());
		button = new Button(x, y, WIDTH, HEIGHT, this.keyName, 100, 200, 100, 10);
		Main.getGameInstance().addKeyListener(this);
	}

	public void tick()
	{
		if(clickDelay > 0) clickDelay--;
		button.tick();
		if(button.isClicked() && clickDelay <= 0)
		{
			for(int i = 0; i < ControlsMenu.getControls().size(); i++)
				if(i != keyID) ControlsMenu.getControls().get(i).isListening = false;
			isListening = !isListening;
			clickDelay = 8;
		}
	}

	public void render()
	{
		Text.drawStringWithShadow(name, x + ((WIDTH / 2) - (Render.stringWidth(name, 9) / 2)), y - 2, 0xFFFFFF, 9, Main.gameFont);
		button.render();
		Render.setColor(255, 255, 255, 170);
		if(isListening) Render.fillRect(button.button.x, button.button.y, button.button.width, button.button.height);
	}
	
	public void keyPressed(KeyEvent e)
	{
		if(isListening)
		{
			//key.useKeys[id] = key.keys[e.getKeyCode()];
			Key.values()[keyID].setKey(e.getKeyCode());
			keyName = KeyEvent.getKeyText(e.getKeyCode());
			button.setText(keyName);
			isListening = false;
		}
	}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}
}
