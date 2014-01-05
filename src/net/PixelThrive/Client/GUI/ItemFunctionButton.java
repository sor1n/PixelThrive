package net.PixelThrive.Client.GUI;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.items.ItemFunctions;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.Tile;

public class ItemFunctionButton
{
	private int x, y;
	private ItemFunctions itemf;
	private BufferedImage slot, select, icon;
	public boolean isSelected, isClicked;
	private String name;
	private Rectangle bounds = new Rectangle();

	public ItemFunctionButton(int x, int y, ItemFunctions itemf, boolean isSelected)
	{
		this.itemf = itemf;
		this.x = x;
		this.y = y;
		slot = Slot.cell.getImage();
		select = Slot.selection.getImage();
		this.isSelected = isSelected;
		int yy = itemf.getCoord() / Tile.tileSize;
		icon = SpriteSheet.getIcon(SpriteSheet.GUI, itemf.getCoord() - Tile.tileSize * yy, yy);
		name = itemf.getName();
		bounds.setBounds(x, y, getSize(), getSize());
	}

	public void render()
	{
		if(isSelected) Render.drawImage(select.getScaledInstance(26, 26, Image.SCALE_SMOOTH), x, y);
		Render.drawImage(slot.getScaledInstance(26, 26, Image.SCALE_SMOOTH), x, y);
		Render.drawImage(icon, x + 5, y + 4);
		Text.drawStringWithShadow(name, x + (getSize() / 2) - (Render.stringWidth(name, 7) / 2), y + 24, 0xFFFFFF, 7, Main.gameFont);
		Render.setColor(180, 180, 180, 180);
		if(bounds.contains(Main.mouseX, Main.mouseY))
		{
			Render.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
			if(Main.key.isMouseLeft) isClicked = true;
			else isClicked = false;
		}
	}

	public ItemFunctions getItemFunction()
	{
		return itemf;
	}

	public static int getSize()
	{
		return 26;
	}
}
