package net.PixelThrive.Client.blocks;

import net.PixelThrive.Client.items.*;
import net.PixelThrive.Client.renders.PlayerAnimation;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.renders.Texture;

public class BlockAndItem 
{	
	protected String helpDescription;
	public int ID;
	protected PlayerAnimation playerAnim = PlayerAnimation.DEFAULT;
	protected ItemFunctions[] functions = {ItemFunctions.DEFAULT, ItemFunctions.THROW};
	protected ItemFunctions currentFunc = functions[0];
	
	public static Texture getItemOrBlockTexture(int id)
	{
		if(id >= Block.IDs.length) return Item.getItem(id).getTexture();
		else return Block.getBlock(id).getTexture();
	}
	
	public static String getItemOrBlockName(int id)
	{
		if(id >= Block.IDs.length) return Item.getItem(id).getName();
		else return Block.getBlock(id).getName();
	}
	
	public static int getMaxItemOrBlockStackSize(int id)
	{
		if(id >= Block.IDs.length) return Item.getItem(id).getMaxStackSize();
		else return Block.getBlock(id).getMaxStackSize();
	}
	
	public static BlockAndItem getItemOrBlock(int id)
	{
		if(id >= Block.IDs.length) return Item.getItem(id);
		else return Block.getBlock(id);
	}
	
	public String getHelpDescription()
	{
		return helpDescription;
	}
		
	public BlockAndItem setAnimation(PlayerAnimation anim)
	{
		playerAnim = anim;
		return this;
	}
	
	public PlayerAnimation getAnimation()
	{
		return playerAnim;
	}
	
	public ItemFunctions getCurrentFunction()
	{
		return currentFunc;
	}
	
	public ItemFunctions[] getFunctions()
	{
		return functions;
	}
	
	public static SpriteSheet getSpriteSheet(int i)
	{
		if(i >= Block.IDs.length) return Item.getItem(i).getSpriteSheet();
		else return Block.getBlock(i).getSpriteSheet();
	}
}
