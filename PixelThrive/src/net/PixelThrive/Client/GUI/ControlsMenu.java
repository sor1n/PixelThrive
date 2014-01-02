package net.PixelThrive.Client.GUI;

import java.util.ArrayList;
import java.util.List;

import net.PixelThrive.Client.Input;
import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.renders.Render;

public class ControlsMenu extends GUI
{
	private PauseMenu pause;
	private Button back = new Button(Main.WIDTH / 2 - 12, 10, 33, 20, "Back", 100, 200, 100);
	//private Button reset = new Button(10, 10, 42, 20, "Reset", 100, 200, 100);
	private int delay = 10;
	
	public static List<Control> controls = new ArrayList<Control>();
	
	public ControlsMenu(PauseMenu pause, Input key)
	{
		this.pause = pause;
		int x = 0, y = 0;
		for(int i = 0; i < Input.Key.values().length; i++)
		{
			controls.add(new Control((Main.WIDTH / 2 - ((Control.WIDTH + 95))) + (x * (Control.WIDTH + 35)), 50 + (y * (Control.HEIGHT + 12)), i, Input.Key.values()[i].getKeyName()));
			y++;
			
			if(y >= 5)
			{
				x++;
				y = 0;
			}
		}
	}

	public void tick()
	{
		if(delay > 0) delay--;
		back.tick();
		//reset.tick();
		if(back.isClicked() && delay <= 0){
			for(int i = 0; i < controls.size(); i++) controls.get(i).isListening = false;
			closeGUI();
		}
//		if(reset.isClicked() && delay <= 0)
//		{
//			for(Control control : controls) control.resetKey();
//			delay = 10;
//		}
		for(Control control : controls) control.tick();
	}

	public void render()
	{
		Render.setColor(60, 120, 200);
		Render.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		Render.setColor(0x000000);
		Render.drawRect(0, 0, Main.WIDTH - 1, Main.HEIGHT - 1);
		back.render();
		//reset.render();
		for(Control control : controls) control.render();
	}
	
	public void openGUI()
	{
		pause.isActive = false;
		super.openGUI();
	}
	
	public void closeGUI()
	{
		pause.isActive = true;
		super.closeGUI();
	}

	public static List<Control> getControls() 
	{
		return controls;
	}
}
