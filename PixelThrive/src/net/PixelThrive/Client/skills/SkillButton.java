package net.PixelThrive.Client.skills;

import java.awt.Color;
import java.awt.Point;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.GUI.Button;
import net.PixelThrive.Client.GUI.Text;
import net.PixelThrive.Client.commands.Message;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.world.Tile;

public class SkillButton extends Button
{
	private int id, xp, x, y, clickDelay;
	private boolean hasBeenPurchased = false;

	public SkillButton(int id, int x, int y, int width, int height, int xp)
	{
		super(x, y, width, height);
		this.x = x;
		this.y = y;
		this.xp = xp;
		this.id = id;
	}

	public void tick()
	{
		super.tick();
		if(clickDelay != 0) clickDelay--;
		if(this.isClicked() && !hasBeenPurchased && clickDelay == 0)
		{
			clickDelay = 40;
			if(Main.player.exp >= xp){
				Main.player.skills.set(id, 1);
				Main.player.reduceExp(xp);	
				hasBeenPurchased = true;
			}
			else Message.newMessage("Not enough experience!");
		}
		if(this.isClicked() && hasBeenPurchased && clickDelay == 0){
			clickDelay = 40;
			Message.newMessage("Already purchased " + Skill.skills.get(id).getName() + "!");
		}
	}

	public void render()
	{
		if(!hasBeenPurchased){
			Render.drawImage(SpriteSheet.Skill.getImage(), x, y, x + Tile.tileSize, y + Tile.tileSize, Skill.skills.get(id).getIconTexture());
			if(button.contains((new Point(Main.mse.x / Main.SCALE, Main.mse.y / Main.SCALE))))
			{
				if(x - Render.stringWidth(Skill.skills.get(id).getName(), 12)/2 + 5 > 0)
				{
					Render.setColor(new Color(0, 0, 0, 210));
					Render.fillRect(x - Render.stringWidth(Skill.skills.get(id).getName(), 12)/2 + 4, y - 12*3, Render.stringWidth(Skill.skills.get(id).getName(), 12) + 1, 30);
					Render.setColor(new Color(100, 100, 100, 200));
					Render.fillRect(x - Render.stringWidth(Skill.skills.get(id).getName(), 12)/2 + 6, y - 12*3 + 2, Render.stringWidth(Skill.skills.get(id).getName(), 12) - 3, 26);
					Text.drawStringWithShadow(Skill.skills.get(id).getName(), x - Render.stringWidth(Skill.skills.get(id).getName(), 12)/2 + 8, y - 12*2, new Color(0xffffff), 12);
					Text.drawStringWithShadow(Integer.toString(xp) + "xp", x - Render.stringWidth(Integer.toString(xp), 12)/2 - Render.stringWidth("xp", 12)/2 + 8, y + 14 - 12*2, new Color(0x00e900), 12);
				}
				else
				{
					Render.setColor(new Color(0, 0, 0, 210));
					Render.fillRect(4, y - 12*3, Render.stringWidth(Skill.skills.get(id).getName(), 12) + 1, 30);
					Render.setColor(new Color(100, 100, 100, 200));
					Render.fillRect(6, y - 12*3 + 2, Render.stringWidth(Skill.skills.get(id).getName(), 12) - 3, 26);
					Text.drawStringWithShadow(Skill.skills.get(id).getName(), 8, y - 12*2, new Color(0xffffff), 12);
					Text.drawStringWithShadow(Integer.toString(xp) + "xp", Render.stringWidth(Integer.toString(xp), 12) - Render.stringWidth("xp", 12)/2 + 12, y + 14 - 12*2, new Color(0x00e900), 12);				
				}
			}
		}
		else Render.drawImage(recolorGray(SpriteSheet.Skill.getImage()), x, y, x + Tile.tileSize, y + Tile.tileSize, Skill.skills.get(id).getIconTexture());
	}
	
	public BufferedImage recolorGray(BufferedImage image)
	{
		ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
		ColorConvertOp op = new ColorConvertOp(cs, null);
		BufferedImage grayImage = op.filter(image, null);
		return grayImage;
	}

}
