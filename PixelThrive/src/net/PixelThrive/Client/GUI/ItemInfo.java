package net.PixelThrive.Client.GUI;

import java.awt.Color;
import java.util.List;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.buff.ArmorBuff;
import net.PixelThrive.Client.renders.Render;

public class ItemInfo 
{
	private String name, desc;
	private List<String> stat;
	private List<ArmorBuff> armorBuff;
	private int col, offs;

	public ItemInfo(String name, String desc, int col, List<String> stat)
	{
		this.name = name;
		this.desc = desc;
		this.stat = stat;
		this.col = col;
		if(stat.size() > 1) offs = stat.size() * 5;
		else offs = 1;
	}

	public ItemInfo(String name, String desc, int col, List<String> stat, List<ArmorBuff> buff)
	{
		this.name = name;
		this.desc = desc;
		this.stat = stat;
		this.col = col;
		if(stat.size() > 1) offs = stat.size() * 5;
		else offs = 1;
		this.armorBuff = buff;
	}

	public void render(int x, int y)
	{
		Render.setFont(Main.gameFont, 9);
		String str1 = name.toUpperCase().substring(0, 1) + name.substring(1);
		String str2 = desc;
		String str3 = "";
		String str4 = "";
		if(armorBuff == null) 
			if(stat.size() > 1) offs = stat.size() * 5;
			else offs = 1;
		else 
			if(stat.size() > 1) offs = stat.size() * 10 + armorBuff.size() * 10;
			else offs = armorBuff.size() * 10;
		if(Render.getFontMetrics().stringWidth(str1) > Render.getFontMetrics().stringWidth(str2)) str3 = str1;
		else str3 = str2;
		if(!stat.isEmpty()) for(String s : stat) if(Render.getFontMetrics().stringWidth(s) > Render.getFontMetrics().stringWidth(str3)) str3 = s;
		if(x + Render.getFontMetrics().stringWidth(str3)/2 + 5> Main.WIDTH) x = Main.WIDTH - Render.getFontMetrics().stringWidth(str3)/2 - 5;
		if(armorBuff == null){
			Render.setColor(new Color(30, 30, 30));
			Render.fillRect(x - Render.getFontMetrics().stringWidth(str3)/2, y - 30 - offs, Render.getFontMetrics().stringWidth(str3) + 4, 25 + stat.size() * 10);
			Render.setColor(0x000000);
			Render.drawRect(x - Render.getFontMetrics().stringWidth(str3)/2, y - 30 - offs, Render.getFontMetrics().stringWidth(str3) + 4, 25 + stat.size() * 10);
		}
		else{
			Render.setColor(new Color(30, 30, 30));
			Render.fillRect(x - Render.getFontMetrics().stringWidth(str3)/2, y - 30 - offs, Render.getFontMetrics().stringWidth(str3) + 4, 35 + stat.size() * 10 + armorBuff.size() * 10);
			Render.setColor(0x000000);
			Render.drawRect(x - Render.getFontMetrics().stringWidth(str3)/2, y - 30 - offs, Render.getFontMetrics().stringWidth(str3) + 4, 35 + stat.size() * 10 + armorBuff.size() * 10);
		}
		Text.drawStringWithShadow(str1, x - Render.getFontMetrics().stringWidth(str3)/2 + 3, y - 20 - offs, Color.WHITE, 9, Main.gameFont);
		Text.drawStringWithShadow(str2, x - Render.getFontMetrics().stringWidth(str3)/2 + 3, y - 10 - offs, col, 9, Main.gameFont);
		if(!stat.isEmpty())
		{
			for(int a = 0; a < stat.size(); a++)
				Text.drawStringWithShadow(stat.get(a), x - Render.getFontMetrics().stringWidth(str3)/2 + 3, y + (a * 10) - offs, new Color(0x0ffff0), 9, Main.gameFont);
		}

		if(armorBuff != null)
		{
			if(!armorBuff.isEmpty()){
				Text.drawStringWithShadow("Whole set buffs:", x - Render.getFontMetrics().stringWidth(str3)/2 + 3, y + stat.size()*10 - offs, new Color(0x980050), 9, Main.gameFont);			
				for(ArmorBuff s : armorBuff) if(Render.getFontMetrics().stringWidth(s.getName()) > Render.getFontMetrics().stringWidth(str4)) str4 = s.getName();
				for(int a = 0; a < armorBuff.size(); a++)
					Text.drawStringWithShadow(armorBuff.get(a).getName(), x - Render.getFontMetrics().stringWidth(str3)/2 + 3, y + stat.size()*10 + (a * 10) - offs + 10, new Color(0x980050), 9, Main.gameFont);
			}
		}
	}
}
