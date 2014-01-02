package net.PixelThrive.Client;

import java.awt.event.*;

import net.PixelThrive.Client.GUI.*;
import net.PixelThrive.Client.world.Tile;

public class Input implements KeyListener, MouseListener, MouseWheelListener, MouseMotionListener
{
	private int screenDelay = 0, hideGUIDelay = 0;
	public boolean isMouseLeft = false, isMouseRight = false, isMouseMiddle = false;
	private boolean capture = false;

	public enum Key
	{
		backGroundKey("Background Edit", KeyEvent.VK_CONTROL), skillKey("Skill Menu", KeyEvent.VK_L), itemFuncButton("Item Menu", KeyEvent.VK_Q), hideKey("Hide GUI", KeyEvent.VK_F12),
		picKey("Screenshot", KeyEvent.VK_F5), transportKey("Inv Sorting", KeyEvent.VK_SHIFT), commandKey("Console", KeyEvent.VK_SLASH), chatKey("Chat/Accept", KeyEvent.VK_ENTER),
		menuKey("Menu/Pause", KeyEvent.VK_ESCAPE), jumpKey("Jump", KeyEvent.VK_SPACE), flyKey("Fly Toggle", KeyEvent.VK_P), invKey("Inventory", KeyEvent.VK_E),
		turnKey("Turned Block", KeyEvent.VK_SHIFT), walkLeftKey("Walk Left", KeyEvent.VK_A), walkRightKey("Walk Right", KeyEvent.VK_D), ascendKey("Ascend", KeyEvent.VK_W), descendKey("Descend", KeyEvent.VK_S),
		nextPage("Next Page", KeyEvent.VK_RIGHT), previousPage("Prev Page", KeyEvent.VK_LEFT), commandsList("Commands", KeyEvent.VK_C), debugMenu("Stats Menu", KeyEvent.VK_F3), shopMenu("Shop", KeyEvent.VK_G);

		Key(String keyName, int key)
		{
			this.keyName = keyName;
			this.key = key;
		}

		private String keyName;
		private int key;
		private boolean isPressed;
		public String getKeyName()
		{
			return keyName;
		}
		public int getKey()
		{
			return key;
		}
		public String getKeyLetter()
		{
			return KeyEvent.getKeyText(key);
		}
		public void setKey(int i)
		{
			key = i;
		}
		public void setPressed(boolean bool)
		{
			isPressed = bool;
		}
		public boolean isPressed()
		{
			return isPressed;
		}
	}

	public void tick()
	{
		if(screenDelay > 0) screenDelay--;
		if(hideGUIDelay > 0) hideGUIDelay--;
		if(screenDelay <= 0 && Key.picKey.isPressed && !capture)
		{
			capture = true;
			Main.captureScreenShot(Main.TITLE + " - " + Main.getTime());
			screenDelay = 8;
		}
		if(hideGUIDelay <= 0 && Key.hideKey.isPressed)
		{
			if(GUI.hideGUI) GUI.hideGUI = false;
			else GUI.hideGUI = true;
			hideGUIDelay = 8;
		}
	}

	public void keyPressed(KeyEvent e)
	{
		for(int i = 0; i < Key.values().length; i++) if(Key.values()[i].getKey() == e.getKeyCode()) Key.values()[i].setPressed(true);
		int key = e.getKeyCode();
		for(TextInput t : Main.textInputs)
		{
			if(t.isActive)
			{
				if(!t.numOnly)
				{
					if(key != KeyEvent.VK_CAPS_LOCK && key != KeyEvent.VK_TAB && key != KeyEvent.VK_SHIFT && key != KeyEvent.VK_SCROLL_LOCK && key != KeyEvent.VK_NUM_LOCK && key != KeyEvent.VK_UP && key != KeyEvent.VK_DOWN && key != KeyEvent.VK_LEFT && key != KeyEvent.VK_RIGHT && key != KeyEvent.VK_WINDOWS && key != KeyEvent.VK_HOME && key != KeyEvent.VK_END && key != KeyEvent.VK_PAGE_DOWN && key != KeyEvent.VK_PAGE_UP && key != KeyEvent.VK_INSERT && key != KeyEvent.VK_ALT && key != KeyEvent.VK_CONTROL)
					{
						if(!t.letterOnly || (t.letterOnly && key != KeyEvent.VK_PERIOD && key != KeyEvent.VK_COMMA && key != KeyEvent.VK_COLON && key != KeyEvent.VK_QUOTE && key != KeyEvent.VK_SLASH && key != KeyEvent.VK_SEMICOLON && key != KeyEvent.VK_ASTERISK && key != KeyEvent.VK_AT && key != KeyEvent.VK_BRACELEFT && key != KeyEvent.VK_BRACERIGHT && key != KeyEvent.VK_WINDOWS && key != KeyEvent.VK_PLUS && key != KeyEvent.VK_SEPARATER && key != KeyEvent.VK_SEPARATOR && key != KeyEvent.VK_AMPERSAND && key != KeyEvent.VK_BACK_SLASH && key != KeyEvent.VK_EXCLAMATION_MARK))
						{
							if(key != KeyEvent.VK_BACK_SPACE && key != KeyEvent.VK_ENTER && key != KeyEvent.VK_ESCAPE) t.addText(String.valueOf(e.getKeyChar()));
							else if(key == KeyEvent.VK_BACK_SPACE) t.removeKey();
							else if(key == KeyEvent.VK_ENTER) t.isDone = true;
							else if(key == KeyEvent.VK_ESCAPE) Main.textInputs.remove(t);
						}
					}
				}
				else
				{
					if(!t.withDots)
					{
						if(key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_ENTER || key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_0 || key == KeyEvent.VK_1 || key == KeyEvent.VK_2 || key == KeyEvent.VK_3 || key == KeyEvent.VK_4 || key == KeyEvent.VK_5 || key == KeyEvent.VK_6 || key == KeyEvent.VK_7 || key == KeyEvent.VK_8 || key == KeyEvent.VK_9)
						{
							if(key != KeyEvent.VK_BACK_SPACE && key != KeyEvent.VK_ENTER && key != KeyEvent.VK_ESCAPE) t.addText(String.valueOf(e.getKeyChar()));
							else if(key == KeyEvent.VK_BACK_SPACE) t.removeKey();
							else if(key == KeyEvent.VK_ENTER) t.isDone = true;
							else if(key == KeyEvent.VK_ESCAPE) Main.textInputs.remove(t);
						}
					}
					else
					{
						if(key == KeyEvent.VK_BACK_SPACE || key == KeyEvent.VK_PERIOD || key == KeyEvent.VK_ENTER || key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_0 || key == KeyEvent.VK_1 || key == KeyEvent.VK_2 || key == KeyEvent.VK_3 || key == KeyEvent.VK_4 || key == KeyEvent.VK_5 || key == KeyEvent.VK_6 || key == KeyEvent.VK_7 || key == KeyEvent.VK_8 || key == KeyEvent.VK_9)
						{
							if(key != KeyEvent.VK_BACK_SPACE && key != KeyEvent.VK_ENTER && key != KeyEvent.VK_ESCAPE) t.addText(String.valueOf(e.getKeyChar()));
							else if(key == KeyEvent.VK_BACK_SPACE) t.removeKey();
							else if(key == KeyEvent.VK_ENTER) t.isDone = true;
							else if(key == KeyEvent.VK_ESCAPE) Main.textInputs.remove(t);
						}
					}
				}
			}
		}
	}

	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() == KeyEvent.VK_F5) capture = false;
		for(int i = 0; i < Key.values().length; i++) if(Key.values()[i].getKey() == e.getKeyCode()) Key.values()[i].setPressed(false);
	}

	public void keyTyped(KeyEvent e) 
	{
	}

	public void mouseDragged(MouseEvent e) 
	{
		Main.mse.setLocation(e.getX(), e.getY());
	}

	public void mouseMoved(MouseEvent e)
	{
		Main.mse.setLocation(e.getX(), e.getY());
	}

	public void mouseClicked(MouseEvent e)
	{

	}

	public void mouseEntered(MouseEvent e)
	{

	}

	public void mouseExited(MouseEvent e)
	{

	}

	public void mousePressed(MouseEvent e) 
	{
		if(e.getButton() == MouseEvent.BUTTON1) isMouseLeft = true;
		else if(e.getButton() == MouseEvent.BUTTON2) isMouseMiddle = true;
		else if(e.getButton() == MouseEvent.BUTTON3) isMouseRight = true;
		if(Main.inv != null) Main.inv.click(e);
	}

	public void mouseReleased(MouseEvent e)
	{
		if(e.getButton() == MouseEvent.BUTTON1) isMouseLeft = false;
		else if(e.getButton() == MouseEvent.BUTTON2) isMouseMiddle = false;
		else if(e.getButton() == MouseEvent.BUTTON3) isMouseRight = false;
	}

	public void mouseWheelMoved(MouseWheelEvent e)
	{
		if(Main.inv != null && Main.player != null && !Main.player.healthBar.isDead)
		{
			if(e.getWheelRotation() > 0)
			{
				if(Main.inv.selection < Tile.invLength - 1) Main.inv.selection++;
				else Main.inv.selection = Tile.invLength - 1;
				Main.inv.scroll();
			}
			else if(e.getWheelRotation() < 0)
			{
				if(Main.inv.selection > 0) Main.inv.selection--;
				else Main.inv.selection = 0;
				Main.inv.scroll();
			}
		}
	}
}
