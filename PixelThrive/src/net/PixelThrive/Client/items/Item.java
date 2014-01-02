package net.PixelThrive.Client.items;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.PixelException;
import net.PixelThrive.Client.GUI.CreativeMenu;
import net.PixelThrive.Client.GUI.CreativeTabs;
import net.PixelThrive.Client.GUI.ItemInfo;
import net.PixelThrive.Client.GUI.Slot;
import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.blocks.BlockAndItem;
import net.PixelThrive.Client.buff.ArmorBuff;
import net.PixelThrive.Client.materials.ArmorMaterial;
import net.PixelThrive.Client.materials.ToolMaterial;
import net.PixelThrive.Client.renders.PlayerAnimation;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.renders.Texture;
import net.PixelThrive.Client.world.Tile;

public class Item extends BlockAndItem
{
	public static int[] IDs = new int[32000];
	public int strength = 100, power;
	ToolMaterial material;
	public int itemID;
	public Slot specialSlot;
	protected int holdingOffsX = 0, cropSize = 12;
	protected PlayerAnimation poke = new PlayerAnimation(new int[]{0, 3}, 10, itemID, MouseEvent.BUTTON1);
	protected PlayerAnimation slash = new PlayerAnimation(new int[]{0, 1, 2, 3}, 6, itemID, MouseEvent.BUTTON1);
	protected PlayerAnimation stab = new PlayerAnimation(new int[]{4, 5}, 15, itemID, MouseEvent.BUTTON1);
	protected boolean shouldCrop = true;

	public static List<Item> items = new ArrayList<Item>();
	public static final Item woodenAxe = new ItemTool(0, ToolMaterial.WOOD).setTexture(0).setName("wooden Axe");
	public static final Item woodenPick = new ItemTool(1, ToolMaterial.WOOD).setTexture(1).setName("wooden Pickaxe");
	public static final Item woodenShovel = new ItemTool(2, ToolMaterial.WOOD).setTexture(2).setName("wooden Shovel");
	public static final Item woodenBow = new ItemBow(3, 0, 0).setClickDelay(3).setTexture(3).setName("wooden Bow");
	public static final Item woodenArrow = new ItemArrow(4, 1, 2, 0).setDescription("Ouch! A splinter D:", 0xE0AE0F).setTexture(19).setName("wooden Arrow");
	public static final Item regenerationPotion = new ItemPotion(5, 4, 0, 1, 30);
	public static final Item harmingPotion = new ItemPotion(6, 20, 1, 1, 30);
	public static final Item woodenSword = new ItemSword(7, 2, ToolMaterial.WOOD).setFunctions(new ItemFunctions[]{ItemFunctions.POKE, ItemFunctions.SLASH, ItemFunctions.STAB, ItemFunctions.THROW}).setClickDelay(3).setTexture(5).setName("wooden Sword");
	public static final Item stoneArrow = new ItemArrow(8, 2, 4, 1).setDescription("Only for stoners. #_#", 0xE0AE0F).setTexture(35).setName("stone Arrow");
	public static final Item dynamite = new ItemExplosive(9, 2.5F, false).setFunctions(new ItemFunctions[]{ItemFunctions.POKE, ItemFunctions.THROW}).setClickDelay(20).setTexture(6).setName("dynamite").setCreativeTab(CreativeTabs.EXPLOSIVE);
	public static final Item stick = new Item(10).setTexture(7).setName("wooden Stick").setFunctions(new ItemFunctions[]{ItemFunctions.DEFAULT, ItemFunctions.POKE, ItemFunctions.THROW}).setDescription("I see you got some wood.", 0xAFE0F1F).addStat("Weapon Damage: 999999").setCreativeTab(CreativeTabs.MISCELANIOUS);
	public static final Item string = new Item(11).setTexture(8).setName("string").setDescription("Good for tools :P", 0xE1AFF1F).setCreativeTab(CreativeTabs.MISCELANIOUS);
	public static final Item clay = new Item(12).setTexture(9).setName("clay Ball").setDescription("Not for human consumption.", 0x444444).setCreativeTab(CreativeTabs.MISCELANIOUS);
	public static final Item leaf = new Item(13).setTexture(11).setName("leaf").setDescription("99% Nature ", 0x228822).setCreativeTab(CreativeTabs.MISCELANIOUS);
	public static final Item branch = new Item(14).setTexture(10).setName("branch").setDescription("Branches off into more.", 0xAAFFFF).setCreativeTab(CreativeTabs.MISCELANIOUS);
	public static final Item brick = new Item(15).setTexture(12).setName("brick").setDescription("Now don't shit it.", 0xEA1F0F).setCreativeTab(CreativeTabs.MISCELANIOUS);
	public static final Item woodenShears = new ItemTool(16, ToolMaterial.WOOD).setTexture(13).setName("wooden Shears");
	public static final Item bottle = new Item(17).setTexture(14).setName("bottle").setDescription("For illegal chemicals 'n stuff.", 0xAAAEF1).setCreativeTab(CreativeTabs.POTIONS);
	public static final Item plank = new Item(18).setTexture(15).setFunctions(new ItemFunctions[]{ItemFunctions.DEFAULT, ItemFunctions.POKE, ItemFunctions.THROW}).setName("plank").setDescription("Planking is sooo old.", 0xC1DAFE).setCreativeTab(CreativeTabs.COMBAT);
	public static final Item nails = new Item(19).setTexture(16).setName("nails").setDescription("Don't step on these.", 0xAEF0F1A).setCreativeTab(CreativeTabs.MISCELANIOUS);
	public static final Item stoneAxe = new ItemTool(20, ToolMaterial.STONE).setTexture(17).setName("stone Axe");
	public static final Item stonePick = new ItemTool(21, ToolMaterial.STONE).setTexture(18).setName("stone Pickaxe");
	public static final Item stoneShovel = new ItemTool(22, ToolMaterial.STONE).setTexture(21).setName("stone Shovel");
	public static final Item stoneBow = new ItemBow(23, 1, 1).setClickDelay(3).setTexture(22).setName("stone Bow");
	public static final Item stoneSword = new ItemSword(24, 4, ToolMaterial.STONE).setFunctions(new ItemFunctions[]{ItemFunctions.POKE, ItemFunctions.SLASH, ItemFunctions.STAB, ItemFunctions.THROW}).setClickDelay(3).setTexture(23).setName("stone Sword");
	public static final Item stoneShears = new ItemTool(25, ToolMaterial.STONE).setTexture(24).setName("stone Shears");
	public static final Item waterBottle = new Item(26).setTexture(36).setName("water Bottle").setDescription("Sponsored by nature.", 0xAAAEF1).setCreativeTab(CreativeTabs.POTIONS);
	public static final Item woodenHelmet = new ItemWoodenArmor(27, 0).setTexture(32).setName("wooden Helmet");
	public static final Item woodenChest = new ItemWoodenArmor(28, 1).setTexture(48).setName("wooden Chestplate");
	public static final Item woodenBoots = new ItemWoodenArmor(29, 2).setTexture(64).setName("wooden Boots");
	public static final Item miningHelmet = new ItemMiningHelmet(30, 0, ArmorMaterial.VANITY, "Light").setTexture(33).setName("mining Helmet");
	public static final Item cursersCursor = new ItemSword(31, 54, ToolMaterial.CURSOR).setHoldingOffsetX(1).setClickDelay(3).setDescription("Cursed by Curse", 0xAEF201).setFunctions(new ItemFunctions[]{ItemFunctions.POKE, ItemFunctions.SLASH, ItemFunctions.STAB, ItemFunctions.THROW}).setTexture(25).setName("curser's Cursor");
	public static final Item vibratingViolator = new ItemSword(32, 80, ToolMaterial.CURSOR).setFunctions(new ItemFunctions[]{ItemFunctions.POKE, ItemFunctions.SLAP, ItemFunctions.THROW}).setHoldingOffsetX(1).setClickDelay(3).setTexture(26).setDescription("No Batteries Included", 0xCC3399).setName("vibrating Violator");
	public static final Item ironSword = new ItemSword(33, 7, ToolMaterial.IRON).setFunctions(new ItemFunctions[]{ItemFunctions.POKE, ItemFunctions.SLASH, ItemFunctions.STAB, ItemFunctions.THROW}).setHoldingOffsetX(1).setTexture(27).setClickDelay(3).setDescription("Thou art prepared for combat", 0xEFEFEF).setName("iron Sword");
	public static final Item stoneHelmet = new ItemStoneArmor(34, 0).setTexture(34).setName("stone Helmet");
	public static final Item stoneChest = new ItemStoneArmor(35, 1).setTexture(50).setName("stone Chestplate");
	public static final Item stoneBoots = new ItemStoneArmor(36, 2).setTexture(80).setName("stone Boots");
	public static final Item ironHelmet = new ItemIronArmor(37, 0).setTexture(37).setName("iron Helmet");
	public static final Item ironChest = new ItemIronArmor(38, 1).setTexture(53).setName("iron Chestplate");
	public static final Item ironBoots = new ItemIronArmor(39, 2).setTexture(96).setName("iron Boots");
	public static final Item ironBow = new ItemBow(40, 4, 2).setClickDelay(2).setTexture(28).setDescription("Almost like a gun", 0xAFAFAF).setName("iron Bow");
	public static final Item ironArrow = new ItemArrow(41, 4, 8, 2).setTexture(51).setDescription("A hitman. A nun. Lovers", 0xEAEAEA).setName("iron Arrow");
	public static final Item ironPick = new ItemTool(42, ToolMaterial.IRON).setTexture(29).setName("iron Pickaxe");
	public static final Item ironShovel = new ItemTool(43, ToolMaterial.IRON).setTexture(31).setName("iron Shovel");
	public static final Item ironAxe = new ItemTool(44, ToolMaterial.IRON).setTexture(30).setName("iron Axe");
	public static final Item bebopBoots = new ItemBebopBoots(45, 2, null, "Diamonds to you", 0x978797).setTexture(112).setName("bebopvox' Boots");
	public static final Item ironIngot = new Item(46).setTexture(38).setName("iron Ingot").setDescription("Oh... the irony", 0xee1Fa6).setCreativeTab(CreativeTabs.MISCELANIOUS);
	public static final Item goldIngot = new Item(47).setTexture(39).setName("gold Ingot").setDescription("It's fabulous", 0x65e10F).setCreativeTab(CreativeTabs.MISCELANIOUS);
	public static final Item charcoal = new Item(48).setTexture(40).setName("charcoal").setDescription("Filled with souls of trees", 0xdfdfdf).setCreativeTab(CreativeTabs.MISCELANIOUS);
	public static final Item coin = new Item(49).setTexture(52).setName("pixelcoin").setDescription("Don't ask.", 0xAAAEF1).setCreativeTab(CreativeTabs.MISCELANIOUS);
	public static final Item copperNeckalce = new ItemJewelry(50, 2).setTexture(49).setName("copper Necklace").setDescription("It's very rusty.", 0xeAAE12);
	public static final Item raggedClothes = new ItemVanity(51, 1).setTexture(54).setName("ragged Clothes").setDescription("I wouldn't wear it if I were you.", 0xe20E12);
	public static final Item goldHelmet = new ItemGoldArmor(52, 0).setTexture(55).setName("golden Helmet");
	public static final Item goldChest = new ItemGoldArmor(53, 1).setTexture(56).setName("golden Chestplate");
	public static final Item goldBoots = new ItemGoldArmor(54, 2).setTexture(128).setName("golden Boots");
	public static final Item copperHelmet = new ItemCopperArmor(55, 0).setTexture(69).setName("copper Helmet");
	public static final Item copperChest = new ItemCopperArmor(56, 1).setTexture(70).setName("copper Chestplate");
	public static final Item copperBoots = new ItemCopperArmor(57, 2).setTexture(144).setName("copper Boots");
	public static final Item diamondDrill = new ItemDrill(58, ToolMaterial.DIAMOND).setTexture(43).setDescription("Eco-Friendly", 0xEAEAEA).setName("diamond Drill");
	public static final Item steamRifle = new ItemGun(59, 40).setTexture(42).setName("steam Rifle").setDescription("Prototype Version 2.1", 0x1234EA);
	public static final Item steamPistol = new ItemGun(60, 20).setTexture(41).setName("steam Pistol").setDescription("Helmet sold seperately", 0xEEFAFE);
	
	public static Item[] axe = {woodenAxe, stoneAxe, ironAxe};
	public static Item[] pickaxe = {woodenPick, stonePick, ironPick, diamondDrill};
	public static Item[] shovel = {woodenShovel, stoneShovel, ironShovel};
	public static Item[] bow = {woodenBow, stoneBow, ironBow};
	public static Item[] sword = {woodenSword, stoneSword, ironSword, vibratingViolator, cursersCursor};
	public static Item[] arrow = {woodenArrow, stoneArrow, ironArrow};
	public static Item[] explosive = {dynamite};
	public static Item[] shears = {woodenShears, stoneShears};

	protected String itemName;
	protected Texture texture;
	protected int maxStackSize = 99, clickDelay, color;
	private boolean canBreakBlockWithItem = true, hasCustomOverlay = false;
	private String desc = "";
	private List<String> stats = new ArrayList<String>();
	private List<ArmorBuff> buff = new ArrayList<ArmorBuff>();
	private int descColor;
	protected boolean isSwinging = false;
	protected int value, yOffs, delay, holdingOffsXLeft, holdingOffsXRight;

	public Item(int id)
	{
		if(id < IDs.length)
		{
			itemID = ID = id + Block.IDs.length;
			items.add(this);
			maxStackSize = 99;
		}
		else throw new PixelException("Ran out of Item IDs!", PixelException.ExceptionType.NOTENOUGHIDS, null, null);
	}

	public Item setCreativeTab(CreativeTabs tab)
	{
		if(tab == CreativeTabs.EXPLOSIVE) CreativeMenu.explosives.add(this);
		if(tab == CreativeTabs.TOOLS) CreativeMenu.tools.add(this);
		if(tab == CreativeTabs.COMBAT) CreativeMenu.combat.add(this);
		if(tab == CreativeTabs.POTIONS) CreativeMenu.potions.add(this);
		if(tab == CreativeTabs.MISCELANIOUS) CreativeMenu.misc.add(this);
		return this;
	}

	public Item canItemBePlacedInSlot(Slot invArmor)
	{
		specialSlot = invArmor;
		return this;
	}

	public Item setHoldingOffsetX(int i)
	{
		holdingOffsX = i;
		return this;
	}

	public Item(int id, int texture)
	{
		this(id);
		setTexture(texture);
	}

	public Item(int id, int texture, SpriteSheet s)
	{
		this(id);
		setTexture(texture, s);
	}

	public void getSubItems(int id, List<CraftableStack> items)
	{
		items.add(new CraftableStack(id, 1, 0));
	}

	public boolean canBreakBlockWithItem()
	{
		return canBreakBlockWithItem;
	}

	public Item setCanBreakBlockWithItem(boolean item)
	{
		canBreakBlockWithItem = item;
		return this;
	}

	public void hasCustomColorOverlay(boolean b)
	{
		hasCustomOverlay = b;
	}

	public boolean hasCustomColorOverlay()
	{
		return hasCustomOverlay;
	}

	public Item setName(String name)
	{
		itemName = name;
		return this;
	}

	public String getName()
	{
		return itemName;
	}

	public Item setClickDelay(int delay)
	{
		clickDelay = delay;
		return this;
	}

	public int getClickDelay()
	{
		return clickDelay;
	}

	public Texture getTexture()
	{
		return texture;
	}

	public Item setTexture(int id)
	{
		int y = id / Tile.tileSize;
		this.texture = new Texture(SpriteSheet.Item, id - Tile.tileSize * y, y);
		return this;
	}

	public Item setTexture(int texture, SpriteSheet s)
	{
		int y = texture / Tile.tileSize;
		this.texture = new Texture(s, texture - Tile.tileSize * y, y);
		return this;
	}

	public SpriteSheet getSpriteSheet()
	{
		return this.texture.getSpriteSheet();
	}

	public Item setMaxStackSize(int i)
	{
		maxStackSize = i;
		return this;
	}

	public int getMaxStackSize()
	{
		return maxStackSize;
	}

	public void onItemRightClick()
	{
	}

	public void onItemLeftClick()
	{
		isSwinging = true;
		if(value >= 50) value = yOffs = 0;
	}

	public void tickMouseHolding()
	{
	}

	public void render(int x, int y)
	{
		Render.drawImage(texture.getImageIcon(), x, y);
	}

	public void renderInPlayerHand(int x, int y, double angle, boolean flipped, boolean small)
	{
		if(!small)
		{
			if(Main.key.isMouseLeft)
			{
				switch(currentFunc)
				{
				default:
				case DEFAULT:
					if(!flipped) Render.drawImage(Render.rotate(texture.getImageIcon(), angle), x - holdingOffsX, y);
					else Render.drawImage(Render.flipHorizontal(Render.rotate(texture.getImageIcon(), angle)), x + holdingOffsX, y);
					break;
				case POKE:
					if(!flipped) Render.drawImage(Render.rotate(texture.getImageIcon(), -90), x - 4 - (getAnimation().getCurrentFrame() * 3) - holdingOffsX, y + 5);
					else Render.drawImage(Render.rotate(Render.flipHorizontal(texture.getImageIcon()), 90), x + 4 + (getAnimation().getCurrentFrame() * 3) + holdingOffsX, y + 5);
					break;
				case SLASH:
				case SLAP:
					if(!flipped) Render.drawImage(Render.rotate(texture.getImageIcon(), -value), x - holdingOffsXLeft, y - (20 - yOffs / 3));
					else Render.drawImage(Render.flipHorizontal(Render.rotate(texture.getImageIcon(), -value)), x + holdingOffsXRight, y - (20 - yOffs / 3));
					break;
				case STAB:
					if(!flipped) Render.drawImage(Render.rotate(texture.getImageIcon(), -90), x - 5 - (getAnimation().getCurrentFrame() * 4) - holdingOffsX, y + 2);
					else Render.drawImage(Render.rotate(Render.flipHorizontal(texture.getImageIcon()), 90), x + 4 + (getAnimation().getCurrentFrame() * 4) + holdingOffsX, y + 2);
					break;
				}
			}
			else
			{
				if(!flipped) Render.drawImage(Render.rotate(texture.getImageIcon(), angle), x - holdingOffsX, y);
				else Render.drawImage(Render.flipHorizontal(Render.rotate(texture.getImageIcon(), angle)), x + holdingOffsX, y);
			}
		}
		else
		{
			if(Main.key.isMouseLeft && currentFunc == ItemFunctions.POKE)
			{
				if(!flipped) Render.drawImage(Render.rotate(texture.getImageIcon(), -90).getScaledInstance(cropSize, cropSize, Image.SCALE_FAST), x - 4 - (getAnimation().getCurrentFrame() * 3) - holdingOffsX, y + 5 + cropSize/3);
				else Render.drawImage(Render.rotate(Render.flipHorizontal(texture.getImageIcon()), 90).getScaledInstance(cropSize, cropSize, Image.SCALE_FAST), x + 4 + (getAnimation().getCurrentFrame() * 3) + holdingOffsX + cropSize/3, y + 5 + cropSize/3);
			}
			else
			{
				if(!flipped) Render.drawImage(Render.rotate(texture.getImageIcon(), angle).getScaledInstance(cropSize, cropSize, Image.SCALE_FAST), x - holdingOffsX, y + cropSize/3);
				else Render.drawImage(Render.flipHorizontal(Render.rotate(texture.getImageIcon(), angle)).getScaledInstance(cropSize, cropSize, Image.SCALE_FAST), x + holdingOffsX + cropSize/3, y + cropSize/3);
			}
		}
	}

	public void renderRotated(int x, int y, double angle)
	{
		Render.drawImage(Render.rotate(texture.getImageIcon(), angle), x, y);
	}

	public static Item getItem(int id)
	{
		for(int i = 0; i < items.size(); i++) if(items.get(i).itemID == id) return items.get(i);
		return null;
	}

	public void tickInInventory()
	{
		if(getCurrentFunction() == ItemFunctions.SLASH && getAnimation() != slash) setAnimation(slash);
		if(getCurrentFunction() == ItemFunctions.POKE && getAnimation() != poke) setAnimation(poke);
		if(getCurrentFunction() == ItemFunctions.STAB && getAnimation() != stab) setAnimation(stab);
		if(getCurrentFunction() == ItemFunctions.SLAP && getAnimation() != slash) setAnimation(slash);
		if(isSwinging)
		{
			delay = 0;
			if(getAnimation() != slash) value += 2;
			else
			{
				if(slash.getCurrentFrame() == 1)
				{
					yOffs = 15;
					value = 12;
					holdingOffsXRight = 2;
					holdingOffsXLeft = 4;
				}
				else if(slash.getCurrentFrame() == 2)
				{
					yOffs = 36;
					value = 48;
					holdingOffsXRight = 3;
					holdingOffsXLeft = 9;
				}
				else if(slash.getCurrentFrame() == 3)
				{
					yOffs = 77;
					value = 113;
					holdingOffsXRight = 4;
					holdingOffsXLeft = 8;
				}
				else if(slash.getCurrentFrame() == 0)
				{
					yOffs = 65;
					value = 0;
					holdingOffsXRight = holdingOffsXLeft = 0;
				}
			}
			if(value >= 290)
			{
				value = 0;
				yOffs = 0;
				isSwinging = false;
			}
		}
	}

	public void tickOnSelected()
	{
	}

	public Item setDescription(String messg, int color)
	{
		descColor = color;
		desc = messg;
		return this;
	}

	public Item addStat(String messg)
	{
		stats.add(messg);
		return this;
	}

	public void removeStat(String messg)
	{
		stats.remove(messg);
	}

	public void removeAllStats()
	{
		for(int i = 0; i < stats.size(); i++)
			stats.remove(i);
	}

	public String getDescription()
	{
		return desc;
	}

	public int getDescColor()
	{
		return descColor;
	}

	public List<String> getStats()
	{
		return stats;
	}

	public void decrementItem()
	{
		Main.inv.removeItemsFromInventory(itemID, 1);
	}

	public ItemInfo getItemInfo()
	{
		return new ItemInfo(itemName, desc, descColor, stats);
	}

	public ItemInfo getItemArmorInfo()
	{
		return new ItemInfo(itemName, desc, descColor, stats, buff);
	}

	public Item addCompleteSetBuff(ArmorBuff b)
	{
		buff.add(b);
		return this;
	}

	public void removeCompleteSetBuff(ArmorBuff b)
	{
		buff.remove(b);
	}

	public void removeAllBuffs()
	{
		for(int i = 0; i < buff.size(); i++)
			buff.remove(i);
	}

	public List<ArmorBuff> getBuffs()
	{
		return buff;
	}

	public int getStrength()
	{
		return strength;
	}

	public int getPower()
	{
		return power;
	}

	public Item setMaterial(ToolMaterial mat)
	{
		material = mat;
		return this;
	}

	public ToolMaterial getMaterial()
	{
		return material;
	}

	public Slot getSpecial() 
	{
		return specialSlot;
	}

	public Item setFunctions(ItemFunctions[] i)
	{
		functions = i;
		return this;
	}

	public Item setCurrentFunction(ItemFunctions i)
	{
		currentFunc = i;
		return this;
	}

	public boolean shouldCropInHand()
	{
		return shouldCrop;
	}

	public int cropSize()
	{
		return cropSize;
	}

	public Item setCropInHand(boolean b, int s)
	{
		shouldCrop = b;
		cropSize = s;		
		return this;
	}
}
