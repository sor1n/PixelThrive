package net.PixelThrive.Client.items;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.Input.Key;
import net.PixelThrive.Client.buff.ArmorBuff;
import net.PixelThrive.Client.entities.Player;
import net.PixelThrive.Client.entities.particles.JetFlamesParticle;
import net.PixelThrive.Client.materials.ArmorMaterial;
import net.PixelThrive.Client.world.World;

public class ItemBebopBoots extends ItemArmor
{
	private ArmorBuff buff;
	public ItemBebopBoots(int id, int type, ArmorBuff buff, String text, int color)
	{
		super(id, type, ArmorMaterial.BEBOP, "Special");
		setDescription(text, color);
		this.buff = buff;
	}
	
	public void tickWhileWearing(Player player, World world) 
	{
		if(buff!= null) buff.effect();
		if(Key.jumpKey.isPressed() && !Main.console.showChat) player.isFlying = true;
		else player.isFlying = false;
		if(player.isFlying) 
			for(int i = 0; i < world.rand.nextInt(1) + 1; i++) 
				new JetFlamesParticle((int)player.getX() + (world.rand.nextInt(2) + 2) - (world.rand.nextInt(2) + 2), (int)player.getY() + (world.rand.nextInt(2) + 11) + (world.rand.nextInt(2) + 2) + i).spawn();
	}   
	   
}
