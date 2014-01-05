package net.PixelThrive.Client.entities;

import java.awt.*;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.PixelThrive.Client.DeathCause;
import net.PixelThrive.Client.Input;
import net.PixelThrive.Client.Input.Key;
import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.GUI.*;
import net.PixelThrive.Client.audio.SoundSystem;
import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.blocks.BlockAndItem;
import net.PixelThrive.Client.buff.Buffs;
import net.PixelThrive.Client.commands.Message;
import net.PixelThrive.Client.entities.particles.Bubble;
import net.PixelThrive.Client.entities.particles.DyingParticle;
import net.PixelThrive.Client.entities.projectiles.ProjectileItem;
import net.PixelThrive.Client.items.Item;
import net.PixelThrive.Client.items.*;
import net.PixelThrive.Client.items.ItemFunctions;
import net.PixelThrive.Client.renders.PlayerAnimation;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.skills.Skill;
import net.PixelThrive.Client.world.Tile;

public class Player extends Entity
{	
	private Input key;
	private int animDelay = 0, jumpDelay = 0, clickDelay = 0, blockClickDelay = 0, jumpLimit = 20;
	private String name;
	public int nameColR, nameColG, nameColB;
	public boolean isCreative = false, isInLiquid = false, isStandingStill = false, isInSpace = false;
	private int legAnim = 0, animation = 0, mineDelay = 0, itemAnimOffsXLeft = 0, itemAnimOffsXRight = 0, itemAnimOffsY = 0, itemRotate = 0;
	private float maxHealth = 100F, health = maxHealth;
	public HealthBar healthBar;
	private int flyDelay = 0;
	public PlayerAnimation currentAnimation;
	protected int fallingDistance = 0;
	protected float fallingDamage = 0.0F;
	private boolean isGettingHurt = false;
	public boolean canMove = true, canMoveLeft = true, canMoveRight = true;
	private Rectangle boundingBox = new Rectangle();
	private int hurtDelay = 0, walkSoundDelay = 0, throwDelay = 0, deathTick = 0, drownTick = 0;
	public int spawnX, spawnY;
	public int drowning = 100;
	public int armor, specialDealTime;
	public boolean damageIgnoresArmor;
	public int exp, level;
	private int drownInt = 70;
	public List<Integer> skills = new ArrayList<Integer>(Skill.skills.size());

	private int prevEXP = 0;

	public Player(int x, int y, Input key, String name)
	{
		super(x, y, Tile.tileSize, Tile.tileSize, Tile.tileSize, Tile.tileSize * 2, Tile.player);
		spawnX = x / Tile.tileSize;
		spawnY = y / Tile.tileSize;
		this.key = key;
		fallingSpeed = 1.7;
		movingSpeed = 1.4;
		if(!name.trim().equalsIgnoreCase("") && name.trim().length() > 2) this.name = name;
		else this.name = "Man With No Name";
		healthBar = new HealthBar(Main.WIDTH - 108, 6, 0);
		nameColR = rand.nextInt(225) + 30;
		nameColG = rand.nextInt(225) + 30;
		nameColB = rand.nextInt(225) + 30;
		armor = 0;
		exp = 0;
		level = 0;
		entityAttribute = EntityAttribute.DEFAULT;
		currentAnimation = PlayerAnimation.DEFAULT;
		for(int i = 0; i < Skill.skills.size(); i++) skills.add(i, 0);
	}

	public void tick()
	{	
		//BoundingBox
		boundingBox.setBounds((int)(x / Tile.tileSize), (int)(y / Tile.tileSize), (int)width, (int)height);

		//Camera
		Main.sX = x - Main.cameraOffsX;
		Main.sY = y - Main.cameraOffsY;

		super.tick();

		//animTick
		currentAnimation = Main.inv.invBar[Main.inv.selection].getContains().getAnimation();
		currentAnimation.tick();

		//Walking Sound
		if(Main.world != null && Main.world.getBlockID((int)(x / Tile.tileSize), (int)(y / Tile.tileSize) + 1) != 0 && isMoving && !isJumping && walkSoundDelay <= 0) playWalkingSound();
		if(walkSoundDelay > 0) walkSoundDelay--;

		//fallingDamage
		if(isFalling) fallingDistance++;
		else if(!isFlying)
		{
			fallingDistance = 0;
			if(fallingDamage > 0.0F)
			{
				if(!isInLiquid) hurt(fallingDamage, DeathCause.FALLING);
				fallingDamage = 0.0F;
			}
		}
		if(isFlying && Key.jumpKey.isPressed()) fallingDamage = fallingDistance = 0;
		if(fallingDistance > 60) fallingDamage += 0.3F;

		//Throwing
		if(Main.inv != null && !Main.skl.isOpen && !Main.shop.isOpen && !Main.com.isOpen && throwDelay <= 0 && BlockAndItem.getItemOrBlock(Main.inv.invBar[Main.inv.selection].id).getCurrentFunction() == ItemFunctions.THROW && !Main.inv.isOpen && !Main.player.healthBar.isDead && !Key.itemFuncButton.isPressed() && !Main.inv.buttons.isPaused && !Main.inv.buttons.isHelpMode && Main.key.isMouseLeft)
		{
			throwDelay = 10;
			double dx = Main.mouseX - Main.WIDTH / 2;
			double dy = Main.mouseY - Main.HEIGHT / 2;
			double bleg = Math.atan2(dy, dx);
			new ProjectileItem((int)x, (int)y, bleg, Main.inv.invBar[Main.inv.selection].id).spawnEntity();
			Main.inv.removeItemFromInvBar(Main.inv.selection);
			Main.inv.scroll();
		}
		if(throwDelay > 0) throwDelay--;

		//Level and EXP
		level = exp / (10 + 18 * level);
		if(exp / (10 + 18 * level) > exp / (10 + 18 * (level + 1))) level = exp / (10 + 18 * level);

		//Armor
		armor = 0;
		for(int i = 0; i < Main.inv.invArmor.length; i++)
			if(Main.inv != null && Main.inv.invArmor[i].id != 0) 
			{
				armor += ((ItemArmor)Item.getItem(Main.inv.invArmor[i].id)).defense;
				((ItemArmor) Main.inv.invArmor[i].getContains()).tickWhileWearing(this, Main.world);
			}
		for(int i = 0; i < Main.inv.invVanity.length; i++)
			if(Main.inv != null && Main.inv.invVanity[i].id != 0 && Main.inv.invVanity[i].getContains().ID >= Block.IDs.length)
				((ItemVanity) Main.inv.invVanity[i].getContains()).tickWhileWearing(this, Main.world);

		for(int i = 0; i < Main.inv.invJewelry.length; i++)
			if(Main.inv != null && Main.inv.invJewelry[i].id != 0) 
				((ItemJewelry) Main.inv.invJewelry[i].getContains()).tickWhileWearing(this, Main.world);

		for(int i = 0; i < Main.inv.invTrinkets.length; i++)
			if(Main.inv != null && Main.inv.invTrinkets[i].id != 0) 
				((ItemTrinket) Main.inv.invTrinkets[i].getContains()).tickWhileWearing(this, Main.world);

		if(deathTick >= 300) respawn();
		if(healthBar.isDead)
		{
			if(deathTick == 0) Buffs.clearActiveBuffs();
			deathTick++;
			if(deathTick < 22 && new Random().nextInt(4) == 0) new DyingParticle((int)(x) + new Random().nextInt(5), (int)(y) + new Random().nextInt(5), this).spawn();
		}
		else deathTick = 0;

		if(flyDelay > 0) flyDelay--;
		if(hurtDelay < 40 && hurtDelay > 30) isGettingHurt = true;
		else if(hurtDelay <= 30 && hurtDelay > 20) isGettingHurt = false;
		else if(hurtDelay <= 20 && hurtDelay > 10) isGettingHurt = true;
		else isGettingHurt = false;

		//Drowning and Breath meter
		if(!isInLiquid)
		{
			drownTick = 0;
			drowning = 100;
			fallingSpeed = 1.7;
			movingSpeed = 1.4;
		}
		else drownTick++;

		//Space Gravity
		if(isInSpace) fallingSpeed = 0.9;
		else fallingSpeed = 1.7;

		//Slowing down in blocks (ex. water)
		if(!Main.world.getBlock((int)((x + 2) / Tile.tileSize), (int)(y / Tile.tileSize)).isSolid())
		{
			Block block = Main.world.getBlock((int)((x + 2) / Tile.tileSize), (int)(y / Tile.tileSize));//getBlockCollidedWith(new Point((int) x + 2, (int) (y + height)), new Point((int) (x + width - 2), (int) (y + height)));
			fallingSpeed = block.slowDownInBlock();
			movingSpeed = block.slowDownInBlock();
			if(block.isLiquid()) isInLiquid = true;
		}
		else isInLiquid = false;

		if(drownTick >= drownInt)
		{
			for(int i = 0; i < new Random().nextInt(5) + 1; i++) new Bubble((int)x + (new Random().nextInt(8) + 2) - (new Random().nextInt(8) + 2), (int)y + (new Random().nextInt(2) + 2) - (new Random().nextInt(2) + 2) + i).spawn();
			drownTick = 0;
			if(!isCreative)
			{
				if(drowning > 0) drowning -= 10;
				else hurt(10F, DeathCause.DROWNING);
			}
		}

		if((int)(y / Tile.tileSize) <= 60) isInSpace = true;
		else isInSpace = false;

		//onWalking in Block
		if(isMoving) getBlockCollidedWith(new Point((int) x + 2, (int) (y + height)), new Point((int) (x + width - 2), (int) (y + height))).onWalking();

		//Mining Anim
		if(Main.world != null && Main.world.isMining)
		{
			mineDelay++;
			if(mineDelay > 0 && mineDelay < 10)
			{
				animation = 1;
				itemAnimOffsY = 13;
				itemRotate = 20;
				itemAnimOffsXLeft = 2;
				itemAnimOffsXRight = -2;
			}
			else if(mineDelay >= 10 && mineDelay < 20)
			{
				animation = 2;
				itemAnimOffsY = 5;
				itemRotate = 0;
				itemAnimOffsXLeft = 2;
				itemAnimOffsXRight = 2;
			}
			else
			{
				animation = 3;
				itemAnimOffsY = 1;
				itemRotate = -15;
				itemAnimOffsXLeft = 3;
				itemAnimOffsXRight = 0;
			}
		}
		else animation = itemAnimOffsXLeft = itemAnimOffsXRight = itemAnimOffsY = itemRotate = 0;
		if(mineDelay >= 30) mineDelay = 0;

		//Collision sth
		if(!isCollidingLeft && !isCollidingRight) isStandingStill = true;
		else isStandingStill = false;

		//Flying
		if(noclip && Key.descendKey.isPressed() && !Main.console.showChat) y += fallingSpeed;
		if(noclip && Key.ascendKey.isPressed() && !Main.console.showChat) y -= fallingSpeed;
		if(noclip && Key.flyKey.isPressed() && flyDelay <= 0 && !Main.console.showChat) 
		{
			noclip = false;
			flyDelay = 8;
			isJumping = false;
			Message.newMessage("Flying deactived");
		}
		if(!noclip && isCreative && Key.flyKey.isPressed() && flyDelay <= 0 && !Main.console.showChat) 
		{
			noclip = true;
			flyDelay = 8;
			isJumping = false;
			Message.newMessage("Flying actived - Ascend: " + Key.ascendKey.getKeyLetter() + ", Descend: " + Key.descendKey.getKeyLetter());
		}

		//Jumping
		if(((isOnGround && !isJumping && !noclip && !isFlying) || (isInLiquid)) && Key.jumpKey.isPressed() && !Main.console.showChat && !healthBar.isDead && canMove) isJumping = true;
		if(isJumping && !isFlying)
		{
			jumpDelay++;
			if((!isCollidingUp) || noclip) y -= fallingSpeed;
		}
		if(jumpDelay >= jumpLimit && !noclip && !isFlying)
		{
			jumpDelay = 0;
			isJumping = false;
		}
		if(Key.walkRightKey.isPressed() && !Main.console.showChat && !healthBar.isDead && canMove && canMoveRight)
		{
			dir = movingSpeed;
			isMoving = true;
		}
		else if(Key.walkLeftKey.isPressed() && !Main.console.showChat && !healthBar.isDead && canMove && canMoveLeft)
		{
			dir = -movingSpeed;
			isMoving = true;
		}
		else isMoving = false;

		if(isMoving)
		{
			boolean canMove = false;
			if(dir > 0) canMove = isCollidingWithBlock(new Point((int) (x + width), (int) y), new Point((int) (x + width), (int) (y + height - 2)));
			else if(dir < 0) canMove = isCollidingWithBlock(new Point((int) x - 1, (int) y), new Point((int) x - 1, (int) (y + (height - 2))));
			if((!canMove && !noclip) || (noclip))
			{	
				if(animDelay <= 64) animDelay++;
				else animDelay = 0;
				if(animDelay > 0 && animDelay < 8) legAnim = 1;
				else if(animDelay >= 8 && animDelay < 16) legAnim = 2;
				else if(animDelay >= 16 && animDelay < 24) legAnim = 1;
				else if(animDelay >= 24 && animDelay < 32) legAnim = 0;	
				else if(animDelay >= 32 && animDelay < 40) legAnim = 3;
				else if(animDelay >= 40 && animDelay < 48) legAnim = 4;
				else if(animDelay >= 48 && animDelay < 56) legAnim = 3;
				else legAnim = 0;
				x += dir;
			}
		}
		else legAnim = 0;
		if(clickDelay > 0) clickDelay--;
		if(blockClickDelay > 0) blockClickDelay--;
		for(int i = 0; i < Item.items.size(); i++)
		{
			if(Item.items.get(i).itemID == Main.inv.invBar[Main.inv.selection].id && !Main.inv.isOpen && clickDelay == 0)
			{
				if(key.isMouseRight)
				{
					Item.items.get(i).onItemRightClick();
					clickDelay = Item.items.get(i).getClickDelay();
				}
				if(key.isMouseLeft)
				{
					Item.items.get(i).onItemLeftClick();
					clickDelay = Item.items.get(i).getClickDelay();
				}
				break;
			}
		}
		for(int x = ((int)Main.sX / Tile.tileSize); x < ((int)Main.sX / Tile.tileSize) + (Main.pixel.width / Tile.tileSize) + 2; x++)
		{
			for(int y = ((int)Main.sY / Tile.tileSize); y < ((int)Main.sY / Tile.tileSize) + (Main.pixel.height / Tile.tileSize) + 2; y++)
			{
				if(x >= 0 && y >= 0 && x < Main.world.worldW && y < Main.world.worldH)
				{
					if(Main.world.block[x][y].id != Block.air.blockID && !Main.inv.isOpen)
					{
						if(Main.world.block[x][y].contains(new Point((Main.mse.x / Main.SCALE) + (int) Main.sX, (Main.mse.y / Main.SCALE) + (int) Main.sY)))
						{
							if(blockClickDelay == 0)
							{
								if(key.isMouseRight && Main.world != null && Main.world.restriction)
								{
									Main.world.block[x][y].getBlock().onRightClick(x, y);
									blockClickDelay = 10;
								}
								if(key.isMouseLeft)
								{
									Main.world.block[x][y].getBlock().onLeftClick(x, y);
									blockClickDelay = 10;
								}
							}
						}
					}
				}
			}
		}
		if(Main.inv != null && Main.inv.invArmor[0].id != 0 && Main.inv.invArmor[1].id != 0 && Main.inv.invArmor[2].id != 0 && ((ItemArmor)(Item.items.get(Main.inv.invArmor[0].id - Block.IDs.length))).getSuitName() == ((ItemArmor)(Item.items.get(Main.inv.invArmor[1].id - Block.IDs.length))).getSuitName()  && ((ItemArmor)(Item.items.get(Main.inv.invArmor[1].id - Block.IDs.length))).getSuitName() == ((ItemArmor)(Item.items.get(Main.inv.invArmor[2].id - Block.IDs.length))).getSuitName())
			for(int m = 0; m < ((ItemArmor)(Item.items.get(Main.inv.invArmor[0].id - Block.IDs.length))).getBuffs().size(); m++)
				((ItemArmor)(Item.items.get(Main.inv.invArmor[0].id - Block.IDs.length))).getBuffs().get(m).effect();
		healthBar.tick();
		if(hurtDelay > 0) hurtDelay--;		
		for(int i = 0; i < skills.size(); i++) if(skills.get(i) == 1) Skill.skills.get(i).effect();
		if(isFlying && !this.isCollidingUp) y -= 2;
		if(specialDealTime > 0) specialDealTime--;
		if(rand.nextInt(1000) == 0 && specialDealTime == 0)
		{
			int m = rand.nextInt(Shop.specialPrice.size());
			Shop.specialDealItem = m;
			Shop.specialDeal = new ShopButton(Shop.specialPrice.get(m), Shop.specialObject.get(m), Shop.x + 1, Shop.y - 45, true);
			specialDealTime = 3000 + rand.nextInt(10000);
		}
	}

	protected void playWalkingSound()
	{
		SoundSystem.playSound(Main.world.getBlock((int)(x / Tile.tileSize), (int)(y / Tile.tileSize) + 1).getWalkingSound(), false);
		walkSoundDelay = 5;
	}

	public boolean canDespawn()
	{
		return false;
	}

	public void render()
	{
		if(!healthBar.isDead)
		{
			if(isInLiquid) //Drowning Bar
			{
				Render.setColor(Color.BLUE);
				Render.fillRect((int)(x - 15) - (int)Main.sX, (int)(y - 20) - (int)Main.sY, (drowning / 2), 5);
				Render.setColor(Color.BLACK);
				Render.drawRect((int)(x - 16) - (int)Main.sX, (int)(y - 20) - (int)Main.sY, 51, 5);
			}
			if(!isGettingHurt) //Normal Render or Animations
			{
				if(currentAnimation == PlayerAnimation.DEFAULT)
				{
					if(dir > 0) Render.drawImage(SpriteSheet.Entity.getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY - (int)height, (int) (x + textureWidth) - (int) Main.sX, (int) (y + textureHeight - height) - (int) Main.sY, /**/ (id[0] * Tile.tileSize) + (Tile.tileSize * animation), id[1] * Tile.tileSize, (id[0] * Tile.tileSize) + (Tile.tileSize * animation) + (int) textureWidth, id[1] * Tile.tileSize + textureHeight);
					else Render.drawImage(SpriteSheet.Entity.getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY - (int)height, (int) (x + textureWidth) - (int) Main.sX, (int) (y + textureHeight - height) - (int) Main.sY, /**/ (id[0] * Tile.tileSize) + (Tile.tileSize * animation) + (int) textureWidth, id[1] * Tile.tileSize, (id[0] * Tile.tileSize) + (Tile.tileSize * animation), id[1] * Tile.tileSize + textureHeight);
				}
				else
				{
					if(Main.key.isMouseLeft && currentAnimation.usesLeftMouse) currentAnimation.render((int) x - (int) Main.sX, (int) y - (int) Main.sY - (int)height);
					else if(Main.key.isMouseRight && currentAnimation.usesRightMouse) currentAnimation.render((int) x - (int) Main.sX, (int) y - (int) Main.sY - (int)height);
					else
					{
						if(dir > 0) Render.drawImage(SpriteSheet.Entity.getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY - (int)height, (int) (x + textureWidth) - (int) Main.sX, (int) (y + textureHeight - height) - (int) Main.sY, /**/ (id[0] * Tile.tileSize) + (Tile.tileSize * animation), id[1] * Tile.tileSize, (id[0] * Tile.tileSize) + (Tile.tileSize * animation) + (int) textureWidth, id[1] * Tile.tileSize + textureHeight);
						else Render.drawImage(SpriteSheet.Entity.getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY - (int)height, (int) (x + textureWidth) - (int) Main.sX, (int) (y + textureHeight - height) - (int) Main.sY, /**/ (id[0] * Tile.tileSize) + (Tile.tileSize * animation) + (int) textureWidth, id[1] * Tile.tileSize, (id[0] * Tile.tileSize) + (Tile.tileSize * animation), id[1] * Tile.tileSize + textureHeight);
					}
				}
			}
			//Name
			Text.drawStringWithShadow(name, ((int)x - (int)Main.sX) + ((int)width / 2) - (Render.stringWidth(name, 9) / 2), (int)y - (int)Main.sY - 6, Color.WHITE, 9, Main.gameFont);

			if(!isGettingHurt) //Legs
			{
				if(dir > 0) Render.drawImage(SpriteSheet.Entity.getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY - (int)height, (int) (x + textureWidth) - (int) Main.sX, (int) (y + textureHeight - height) - (int) Main.sY, /**/ (Tile.tileSize * legAnim), Tile.tileSize * 2, (Tile.tileSize * legAnim) + (int) textureWidth, (Tile.tileSize * 2) + textureHeight);
				else Render.drawImage(SpriteSheet.Entity.getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY - (int)height, (int) (x + textureWidth) - (int) Main.sX, (int) (y + textureHeight - height) - (int) Main.sY, /**/ (Tile.tileSize * legAnim) + (int) textureWidth, Tile.tileSize * 2, (Tile.tileSize * legAnim), (Tile.tileSize * 2) + textureHeight);
			}

			if(getDir() > 0) //Armor
			{
				Image vanityHead = BlockAndItem.getSpriteSheet(Main.inv.invVanity[0].id).getImage().getSubimage(BlockAndItem.getItemOrBlockTexture(Main.inv.invVanity[0].id).x * Tile.tileSize, BlockAndItem.getItemOrBlockTexture(Main.inv.invVanity[0].id).y * Tile.tileSize, Tile.tileSize, Tile.tileSize);
				if(Main.inv != null && Main.inv.invArmor[2].id != 0 && Main.inv.invVanity[2].id == 0) 
					Render.drawImage(BlockAndItem.getSpriteSheet(Main.inv.invArmor[2].id).getIconImage((BlockAndItem.getItemOrBlockTexture(Main.inv.invArmor[2].id).x * Tile.tileSize) + (Tile.tileSize * legAnim), BlockAndItem.getItemOrBlockTexture(Main.inv.invArmor[2].id).y * Tile.tileSize), (int) x - (int) Main.sX, (int) y - (int) Main.sY + 3);
				else if(Main.inv.invVanity[2].id != 0) 
					Render.drawImage(BlockAndItem.getSpriteSheet(Main.inv.invVanity[2].id).getIconImage(BlockAndItem.getItemOrBlockTexture(Main.inv.invVanity[2].id).x * Tile.tileSize + (Tile.tileSize * legAnim), BlockAndItem.getItemOrBlockTexture(Main.inv.invVanity[2].id).y * Tile.tileSize), (int) x - (int) Main.sX, (int) y - (int) Main.sY + 3);

				if(Main.inv != null && Main.inv.invArmor[1].id != 0 && Main.inv.invVanity[1].id == 0) 
					Render.drawImage(BlockAndItem.getSpriteSheet(Main.inv.invArmor[1].id).getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY - 3, (int) (x + Tile.tileSize) - (int) Main.sX, (int) (y + Tile.tileSize) - (int) Main.sY - 3, BlockAndItem.getItemOrBlockTexture(Main.inv.invArmor[1].id));
				else if(Main.inv.invVanity[1].id != 0) 
					Render.drawImage(BlockAndItem.getSpriteSheet(Main.inv.invVanity[1].id).getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY - 3, (int) (x + Tile.tileSize) - (int) Main.sX, (int) (y + Tile.tileSize) - (int) Main.sY - 3, BlockAndItem.getItemOrBlockTexture(Main.inv.invVanity[1].id));

				if(Main.inv != null && Main.inv.invArmor[0].id != 0 && Main.inv.invVanity[0].id == 0) 
					Render.drawImage(BlockAndItem.getSpriteSheet(Main.inv.invArmor[0].id).getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY - 7, (int) (x + Tile.tileSize) - (int) Main.sX, (int) (y + Tile.tileSize) - (int) Main.sY - 7, BlockAndItem.getItemOrBlockTexture(Main.inv.invArmor[0].id));
				else if(Main.inv.invVanity[0].id != 0 && Main.inv.invVanity[0].id < Block.IDs.length) 
					Render.drawImage(vanityHead.getScaledInstance(10, 10, Image.SCALE_FAST), (int) x - (int) Main.sX + 3, (int) y - (int) Main.sY - 5);
				else if(Main.inv.invVanity[0].id != 0 && Main.inv.invVanity[0].id >= Block.IDs.length)
					Render.drawImage(BlockAndItem.getSpriteSheet(Main.inv.invVanity[0].id).getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY - 7, (int) (x + Tile.tileSize) - (int) Main.sX, (int) (y + Tile.tileSize) - (int) Main.sY - 7, BlockAndItem.getItemOrBlockTexture(Main.inv.invVanity[0].id));
			}
			else
			{
				Image vanityHead = BlockAndItem.getSpriteSheet(Main.inv.invVanity[0].id).getImage().getSubimage(BlockAndItem.getItemOrBlockTexture(Main.inv.invVanity[0].id).x * Tile.tileSize, BlockAndItem.getItemOrBlockTexture(Main.inv.invVanity[0].id).y * Tile.tileSize, Tile.tileSize, Tile.tileSize);
				if(Main.inv != null && Main.inv.invArmor[2].id != 0 && Main.inv.invVanity[2].id == 0) 
					Render.drawImage(Render.flipHorizontal(BlockAndItem.getSpriteSheet(Main.inv.invArmor[2].id).getIconImage((BlockAndItem.getItemOrBlockTexture(Main.inv.invArmor[2].id).x * Tile.tileSize) + (Tile.tileSize * legAnim), BlockAndItem.getItemOrBlockTexture(Main.inv.invArmor[2].id).y * Tile.tileSize)), (int) x - (int) Main.sX, (int) y - (int) Main.sY + 3);
				else if(Main.inv.invVanity[2].id != 0) 
					Render.drawImage(Render.flipHorizontal(BlockAndItem.getSpriteSheet(Main.inv.invVanity[2].id).getIconImage((BlockAndItem.getItemOrBlockTexture(Main.inv.invVanity[2].id).x * Tile.tileSize) + (Tile.tileSize * legAnim), BlockAndItem.getItemOrBlockTexture(Main.inv.invVanity[2].id).y * Tile.tileSize)), (int) x - (int) Main.sX, (int) y - (int) Main.sY + 3);

				if(Main.inv != null && Main.inv.invArmor[1].id != 0 && Main.inv.invVanity[1].id == 0) 
					Render.drawImage(BlockAndItem.getSpriteSheet(Main.inv.invArmor[1].id).getImage(), (int) x - (int) Main.sX + Tile.tileSize, (int) y - (int) Main.sY - 3, (int) (x - Tile.tileSize) - (int) Main.sX + Tile.tileSize, (int) (y + Tile.tileSize) - (int) Main.sY - 3, BlockAndItem.getItemOrBlockTexture(Main.inv.invArmor[1].id));
				else if(Main.inv.invVanity[1].id != 0) 
					Render.drawImage(BlockAndItem.getSpriteSheet(Main.inv.invVanity[1].id).getImage(), (int) x - (int) Main.sX + Tile.tileSize, (int) y - (int) Main.sY - 3, (int) (x - Tile.tileSize) - (int) Main.sX + Tile.tileSize, (int) (y + Tile.tileSize) - (int) Main.sY - 3, BlockAndItem.getItemOrBlockTexture(Main.inv.invVanity[1].id));

				if(Main.inv != null && Main.inv.invArmor[0].id != 0 && Main.inv.invVanity[0].id == 0) 
					Render.drawImage(BlockAndItem.getSpriteSheet(Main.inv.invArmor[0].id).getImage(), (int) x - (int) Main.sX + Tile.tileSize, (int) y - (int) Main.sY - 7, (int) (x - Tile.tileSize) - (int) Main.sX + Tile.tileSize, (int) (y + Tile.tileSize) - (int) Main.sY - 7, BlockAndItem.getItemOrBlockTexture(Main.inv.invArmor[0].id));
				else if(Main.inv.invVanity[0].id != 0 && Main.inv.invVanity[0].id < Block.IDs.length) 
					Render.drawImage(vanityHead.getScaledInstance(10, 10, Image.SCALE_FAST), (int) x - (int) Main.sX + 3, (int) y - (int) Main.sY - 5);
				else if(Main.inv.invVanity[0].id != 0 && Main.inv.invVanity[0].id >= Block.IDs.length)
					Render.drawImage(BlockAndItem.getSpriteSheet(Main.inv.invVanity[0].id).getImage(), (int) x - (int) Main.sX + Tile.tileSize, (int) y - (int) Main.sY - 7, (int) (x - Tile.tileSize) - (int) Main.sX + Tile.tileSize, (int) (y + Tile.tileSize) - (int) Main.sY - 7, BlockAndItem.getItemOrBlockTexture(Main.inv.invVanity[0].id));
			}

			//Items
			if(Main.inv.invBar[Main.inv.selection].id != Block.air.blockID)
			{
				if(getDir() < 0 || getDir() == 0)
				{
					for(int i = 0; i < Block.blocks.size(); i++)
					{
						if(Main.inv.invBar[Main.inv.selection].id == Block.blocks.get(i).blockID && Block.blocks.get(Main.inv.invBar[Main.inv.selection].id).getTexture() != null)
						{
							if(Block.blocks.get(Main.inv.invBar[Main.inv.selection].id).shouldCropInHand()) Render.drawImage(Block.blocks.get(Main.inv.invBar[Main.inv.selection].id).getSpriteSheet().getImage().getSubimage(Block.blocks.get(Main.inv.invBar[Main.inv.selection].id).getTexture().x * Tile.tileSize, Block.blocks.get(Main.inv.invBar[Main.inv.selection].id).getTexture().y * Tile.tileSize, Tile.tileSize, Tile.tileSize).getScaledInstance(5, 5, 0), (int)((Main.player.x - 1) - Main.sX), (int)((Main.player.y + 8) - Main.sY - itemAnimOffsY));
							else Render.drawImage(Block.blocks.get(Main.inv.invBar[Main.inv.selection].id).getSpriteSheet().getImage().getSubimage(Block.blocks.get(Main.inv.invBar[Main.inv.selection].id).getTexture().x * Tile.tileSize, Block.blocks.get(Main.inv.invBar[Main.inv.selection].id).getTexture().y * Tile.tileSize, Tile.tileSize, Tile.tileSize), (int)((Main.player.x - 8) - Main.sX), (int)((Main.player.y - 2) - Main.sY - itemAnimOffsY));
						}
					}
					for(int i = 0; i < Item.items.size(); i++)
						if(Item.items.get(i).itemID == (Main.inv.invBar[Main.inv.selection].id))
						{
							Item ids = Item.items.get(i);
							if(currentAnimation == PlayerAnimation.DEFAULT) ids.renderInPlayerHand((int)((x - 6) - Main.sX - itemAnimOffsXLeft), (int)((y - 2) - Main.sY - itemAnimOffsY), itemRotate, false, ids.shouldCropInHand());
							else ids.renderInPlayerHand((int)((x - 6) - Main.sX), (int)((y - 2) - Main.sY), itemRotate, false, ids.shouldCropInHand());
							break;
						}
				}
				else
				{
					for(int i = 0; i < Block.blocks.size(); i++)
					{
						if(Main.inv.invBar[Main.inv.selection].id == Block.blocks.get(i).blockID && Block.blocks.get(Main.inv.invBar[Main.inv.selection].id).getTexture() != null)
						{
							if(Block.blocks.get(Main.inv.invBar[Main.inv.selection].id).shouldCropInHand()) Render.drawImage(Block.blocks.get(Main.inv.invBar[Main.inv.selection].id).getSpriteSheet().getImage().getSubimage(Block.blocks.get(Main.inv.invBar[Main.inv.selection].id).getTexture().x * Tile.tileSize, Block.blocks.get(Main.inv.invBar[Main.inv.selection].id).getTexture().y * Tile.tileSize, Tile.tileSize, Tile.tileSize).getScaledInstance(5, 5, 0), (int)((Main.player.x + 12) - Main.sX), (int)((Main.player.y + 8) - Main.sY - itemAnimOffsY));
							else Render.drawImage(Block.blocks.get(Main.inv.invBar[Main.inv.selection].id).getSpriteSheet().getImage().getSubimage(Block.blocks.get(Main.inv.invBar[Main.inv.selection].id).getTexture().x * Tile.tileSize, Block.blocks.get(Main.inv.invBar[Main.inv.selection].id).getTexture().y * Tile.tileSize, Tile.tileSize, Tile.tileSize), (int)((Main.player.x + 7) - Main.sX), (int)((Main.player.y - 2) - Main.sY - itemAnimOffsY));
						}
					}
					for(int i = 0; i < Item.items.size(); i++)
					{
						if(Item.items.get(i).itemID == (Main.inv.invBar[Main.inv.selection].id))
						{
							Item ids = Item.items.get(i);
							if(currentAnimation == PlayerAnimation.DEFAULT) ids.renderInPlayerHand((int) (x + 6) - (int) Main.sX + itemAnimOffsXRight, (int) (y - 2) - (int)Main.sY - itemAnimOffsY, itemRotate, true, ids.shouldCropInHand());
							else ids.renderInPlayerHand((int) (x + 6) - (int) Main.sX, (int) (y - 2) - (int)Main.sY, itemRotate, true, ids.shouldCropInHand());
							break;
						}
					}
				}
			}
		}
	}

	public void renderGUI()
	{
		healthBar.render();
		if(healthBar.isDead)
		{
			Text.drawStringWithShadow("You have died.", (Main.WIDTH / 2) - (Render.stringWidth("You have died.", 18) / 2), 40, Color.WHITE, 18, Main.gameFont);
			Text.drawStringWithShadow(":[", (Main.WIDTH / 2) - (Render.stringWidth(":[", 17) / 2), 60, Color.WHITE, 17, Main.gameFont);
		}
	}

	public String getName()
	{
		return name;
	}

	public void spawnEntity()
	{
		Main.getPlayers().add(this);
	}

	public void despawnEntity()
	{
		Main.getPlayers().remove(this);
	}

	public void setPosInWorld(int x, int y) 
	{
		this.x = x * Tile.tileSize;
		this.y = y * Tile.tileSize;
	}

	public void hurt(float amount, DeathCause death)
	{
		if(!isCreative)
		{
			if(hurtDelay <= 0) hurtDelay = 40;
			if(health > 0 && !damageIgnoresArmor) health -= ((100 - armor)*0.01)*amount;
			else if(health > 0) health -= amount;
			if(health <= 0 && health != -999)
			{
				deathMessage(death);
				health = -999;
			}
		}
	}

	public void heal(float amount)
	{
		health += amount;
		if(health > maxHealth) health = maxHealth;
	}

	public float getMaxHealth()
	{
		return maxHealth;
	}

	public float getHealth()
	{
		return health;
	}

	public void setHealth(float h)
	{
		health = h;
	}

	public void respawn()
	{
		drowning = 100;
		Buffs.clearActiveBuffs();
		setHealth(getMaxHealth());
		healthBar.isDead = false;
		deathTick = 0;
		setPosInWorld(spawnX, spawnY);
	}

	public void deathMessage(DeathCause d)
	{
		Message.newDeathMessage(this, d);
	}

	public int getHoldingItemID()
	{
		return (Main.inv.invBar[Main.inv.selection].id - Block.IDs.length);
	}

	public int getExp() 
	{
		return exp;
	}

	public void addExp(int m)
	{
		prevEXP = this.exp;
		exp+=m;
		if((prevEXP / 1000) < (exp / 1000)) healthBar.expCircle.glow();
	}

	public void reduceExp(int m)
	{
		prevEXP = this.exp;
		if(exp - m >= 0)exp-=m;
		else exp = 0;
		if((prevEXP / 1000) > (exp / 1000)) healthBar.expCircle.glow();
	}

	public void setExp(int exp)
	{
		prevEXP = this.exp;
		this.exp = exp;
		if((prevEXP / 1000) > (exp / 1000) || (prevEXP / 1000) < (exp / 1000)) healthBar.expCircle.glow();
	}

	public int getLevel() 
	{
		return level;
	}

	public int getJumpLimit() 
	{
		return jumpLimit;
	}

	public void setJumpLimit(int jumpLimit) 
	{
		this.jumpLimit = jumpLimit;
	}

	public void setDrownLimit(int drownLimit)
	{
		this.drownInt = drownLimit;
	}

	public Rectangle getBoundingBox()
	{
		return boundingBox;
	}
}
