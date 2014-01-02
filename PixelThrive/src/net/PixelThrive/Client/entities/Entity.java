package net.PixelThrive.Client.entities;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.PixelThrive.Client.DeathCause;
import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.GUI.DebugMenu;
import net.PixelThrive.Client.GUI.EntityGUI;
import net.PixelThrive.Client.GUI.GUI;
import net.PixelThrive.Client.GUI.Text;
import net.PixelThrive.Client.blocks.Block;
import net.PixelThrive.Client.blocks.LiquidBlock;
import net.PixelThrive.Client.buff.Buffs;
import net.PixelThrive.Client.entities.particles.DyingParticle;
import net.PixelThrive.Client.entities.projectiles.Projectile;
import net.PixelThrive.Client.entities.tileentities.TileEntity;
import net.PixelThrive.Client.items.CraftableStack;
import net.PixelThrive.Client.renders.EntityRenders;
import net.PixelThrive.Client.renders.Render;
import net.PixelThrive.Client.renders.SpriteSheet;
import net.PixelThrive.Client.renders.Texture;
import net.PixelThrive.Client.world.Tile;
import net.PixelThrive.Client.world.World;

public abstract class Entity extends DoubleRectangle
{
	protected EntityAttribute entityAttribute;
	protected BufferedImage hat = SpriteSheet.getIcon(SpriteSheet.Entity, 2, 4);
	protected BufferedImage wing = SpriteSheet.getIcon(SpriteSheet.Entity, 3, 4);
	protected int hatOffsX = 0, hatOffsY = 0, wingRotate = 0, wingDir = 0;

	public static Class<?>[] entityList = {Drop.class, GolemGuard.class, Shnail.class, FallingBlock.class, ShadowGhoul.class, Darkness.class, GlowFish.class};
	public static Class<?>[] livingEntityList = {GolemGuard.class, Shnail.class, ShadowGhoul.class, Darkness.class, GlowFish.class};
	protected CraftableStack[] drops;
	protected int expAmount = 0, life = 4000;
	
	public enum EntityAttribute
	{
		DEFAULT(null, 0, 0), SPEEDY("Speedy", 13, 0), TOUGH("Tough", 14, 0), CLASSY("Classy", 15, 0),
		WEAK("Weak", 14, 1), DERPY("Derpy", 15, 1), WINGED("Winged", 13, 1), HEALING("Healing", 0, 2);

		EntityAttribute(String rareName, int x, int y)
		{
			entityAttributeName = rareName;
			icon = new Texture(SpriteSheet.GUI, x, y);
		}

		private String entityAttributeName;
		private Texture icon;

		public String getRarityName()
		{
			return entityAttributeName;
		}

		public Texture getIcon()
		{
			return icon;
		}
	}

	protected EntityRenders entityRender;
	protected EntityGUI entityGUI;
	protected Random rand = new Random();
	protected String entityName;

	protected double dir = 0, movingSpeed = 0.4, fallingSpeed = 0.2, velocity = fallingSpeed, maxVelocity = 5.0;
	protected int[] id;

	public boolean isOnGround = false, isFalling = false, isMoving = false, isJumping = false;
	public boolean isCollidingRight = false, isCollidingDown = false, isCollidingLeft = false, isCollidingUp = false;
	public boolean noclip = false, isFlying = false;
	public boolean renderAfterWorld;
	protected boolean canBeHurt = true, hasSpawned = false;

	protected int animation = 0, damageDelay = 0;
	protected int textureWidth, textureHeight, spawnAnimFrame = 0, spawnAnimDelay = 0, spawnAnimLimit;

	protected float health, maxHealth, delay;

	public List<Buffs> activePotions = new ArrayList<Buffs>();
	public Rectangle boundingBox = new Rectangle();
	protected boolean canWander = false;
	protected int wanderDirection = 0, wanderDelay = 0, healDelay = 0;
	protected boolean wander = false, otherDir = false;

	protected DeathCause lastCause;
	protected int lastDMG = 0;
	protected int[] lightLevelSpawn = {0, 0}, spawningAnim;
	protected World world;

	public Entity(int x, int y, int width, int height, int textureWidth, int textureHeight, int[] id, boolean renderAfterPlayer)
	{
		setBounds(x, y, width, height);
		this.id = id;
		this.textureWidth = textureWidth;
		this.textureHeight = textureHeight;
		this.renderAfterWorld = renderAfterPlayer;
		health = maxHealth = 100;
		if(rand.nextInt(2) == 0) entityAttribute = EntityAttribute.values()[rand.nextInt(EntityAttribute.values().length - 1) + 1];
		else entityAttribute = EntityAttribute.DEFAULT;
	}

	public Entity(int x, int y, int width, int height)
	{
		this(x, y, width, height, width, height, null);
		boundingBox.setBounds(x, y, width, height);
		entityGUI = new EntityGUI(this, 20, 20);
	}

	public Entity(int x, int y, int width, int height, int textureWidth, int textureHeight, int[] id)
	{
		this(x, y, width, height, textureWidth, textureHeight, id, false);
	}

	public Entity(int x, int y, int width, int height, int[] id, boolean renderAfterPlayer)
	{
		this(x, y, width, height, width, height, id, renderAfterPlayer);
	}

	public void tick() 
	{
		if(spawningAnim != null)
		{
			if(spawnAnimDelay < spawnAnimLimit) spawnAnimDelay++;
			else
			{
				if(spawnAnimFrame < spawningAnim.length - 1)
				{
					spawnAnimFrame++;
					spawnAnimDelay = 0;
				}
				else hasSpawned = true;
			}
		}
		else hasSpawned = true;

		isCollidingUp = (isCollidingWithBlock(new Point((int) (x + 2), (int) y), new Point((int) (x + width - 2), (int) y))) | noclip;
		isCollidingRight = (isCollidingWithBlock(new Point((int) (x + width), (int) y), new Point((int) (x + width), (int) (y + height - 2)))) | noclip;
		isCollidingLeft = (isCollidingWithBlock(new Point((int) x, (int) y), new Point((int) x, (int) (y + (height - 2))))) | noclip;
		isCollidingDown = (isCollidingWithBlock(new Point((int) x + 2, (int) (y + height)), new Point((int) (x + width - 2), (int) (y + height))));
		boundingBox.setBounds((int)x - (int)Main.sX, (int)y - (int)Main.sY, (int)width, (int)height);

		if(damageDelay > 0) damageDelay--;
		if(wingDir == 0)
		{
			if(wingRotate < 26) wingRotate += 4;
			else wingDir = 1;
		}
		else
		{
			if(wingRotate > 0) wingRotate -= 4;
			else wingDir = 0;
		}

		if(hasSpawned)
		{
			if(entityAttribute == EntityAttribute.DERPY) dir = new Random().nextInt(2) - 1;
			if(entityAttribute == EntityAttribute.HEALING)
			{
				healDelay++;
				if(healDelay >= 100)
				{
					healDelay = 0;
					heal(2);
				}
			}
			else healDelay = 0;
		}

		if(entityGUI != null)
		{
			if(boundingBox.contains(Main.mouseX, Main.mouseY))
			{
				if(!entityGUI.isOpen && !GUI.isGUIOpen() && !Main.inv.isOpen && !Main.skl.isOpen && !Main.com.isOpen && !DebugMenu.isOpen && !Main.shop.isOpen) entityGUI.openGUI();
			}
			else if(!entityGUI.isCloseTriggered) entityGUI.triggerClose();
		}

		if(health <= 0 && !isInvincible())
		{
			for(int i = 0; i < 5; i++) new DyingParticle((int)x, (int)y, this).spawn();
			if(drops != null) for(int i = 0; i < drops.length; i++) new Drop((int)x, (int)y, drops[i].getID(), drops[i].getAmount() - 1).spawnEntity();
			if(expAmount > 0) new EXPOrb((int)x, (int)y, expAmount).spawnEntity();
			despawnEntity();
		}

		if(!noclip && !isFlying && entityAttribute != EntityAttribute.WINGED && !isJumping && (!isCollidingWithBlock(new Point((int) x + 2, (int) (y + height)), new Point((int) (x + width - 2), (int) (y + height)))))
		{
			y += fallingSpeed;
			isFalling = true;
			isOnGround = false;
		}	
		else 
		{
			isFalling = false;
			isOnGround = true;
		}
		for(int i = 0; i < activePotions.size(); i++) removeBuff(i);

		if(canWander && hasSpawned)
		{
			if(rand.nextInt(150) == 0)
			{
				wanderDirection = rand.nextInt(100);
				if(!wander) wander = true;
				else wander = false;
				otherDir = false;
			}
			if(wander)
			{
				isMoving = true;
				if(!otherDir)
				{
					if(wanderDirection <= 50) dir = 1;
					else dir = -1;
				}
			}
			else isMoving = false;

			if(isMoving)
			{
				if(isCollidingLeft)
				{
					dir = 1;
					otherDir = true;
				}
				if(isCollidingRight)
				{
					dir = -1;
					otherDir = true;
				}
				if(dir < 0) x -= movingSpeed;
				if(dir > 0) x += movingSpeed;
			}	
		}
		
		if(life > 0) life--;
		if(life <= 0 && canDespawn()) this.despawnEntity();
	}

	public boolean isInvincible()
	{
		return false;
	}
	
	public boolean canDespawn()
	{
		return true;
	}

	public Block spawnBlock()
	{
		return null;
	}

	//Set the darkest light level it can spawn at
	public int[] lightLevelSpawn()
	{
		return lightLevelSpawn;
	}

	public void setLightLevel(int min, int max)
	{
		lightLevelSpawn[0] = min;
		lightLevelSpawn[1] = max;
	}

	public boolean spawnsAtNight()
	{
		return true;
	}

	public boolean spawnsAnytime()
	{
		return true;
	}

	public boolean spawnsOnAllBlocks()
	{
		return false;
	}

	public boolean spawnsOnScreen()
	{
		return false;
	}

	public boolean hasSpawningAnimation()
	{
		return false;
	}

	public void render()
	{			
		if(dir > 0) Render.drawImage(SpriteSheet.Entity.getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY - (int)height, (int) (x + textureWidth) - (int) Main.sX, (int) (y + textureHeight - height) - (int) Main.sY, /**/ (id[0] * Tile.tileSize) + (Tile.tileSize * animation), id[1] * Tile.tileSize, (id[0] * Tile.tileSize) + (Tile.tileSize * animation) + (int) textureWidth, id[1] * Tile.tileSize + (int)textureHeight);
		else Render.drawImage(SpriteSheet.Entity.getImage(), (int) x - (int) Main.sX, (int) y - (int) Main.sY - (int)height, (int) (x + textureWidth) - (int) Main.sX, (int) (y + textureHeight - height) - (int) Main.sY, /**/ (id[0] * Tile.tileSize) + (Tile.tileSize * animation) + (int) textureWidth, id[1] * Tile.tileSize, (id[0] * Tile.tileSize) + (Tile.tileSize * animation), id[1] * Tile.tileSize + (int)textureHeight);
	}

	public void renderEntity(EntityRenders entity)
	{
		renderEntity(entity, ((int)x - (int)Main.sX), (int)y - (int)Main.sY);
	}

	public void renderOverWorld()
	{
		if(damageDelay > 0) Text.drawStringWithShadowAndFade(String.valueOf(lastDMG), (int)x - (int)Main.sX, (int)y - (int)Main.sY, new Color(0xffffff), 10, Main.gameFont, damageDelay);
	}

	public void renderEntity(EntityRenders entity, int x, int y)
	{			
		if(spawningAnim != null && !hasSpawned)
		{
			int yy = spawningAnim[spawnAnimFrame] / 16;
			int xx = spawningAnim[spawnAnimFrame] - 16 * yy;
			Render.drawImage(SpriteSheet.Entity.getIconImage(xx * Tile.tileSize, yy * Tile.tileSize, (int)width, (int)height), x, y);
		}
		if(hasSpawned)
		{
			if(dir > 0) Render.drawImage(entity.getEntityIcon(), x, y);
			else Render.drawImage(entity.getEntityIcon(), x + (int)width, y, (x + (int)width) - (int)width, y + (int)height, 0, 0, entity.getEntityIcon().getWidth(), entity.getEntityIcon().getHeight());
		}
		if(entityAttribute == EntityAttribute.CLASSY) Render.drawImage(hat, x + hatOffsX, y + hatOffsY);
		if(entityAttribute == EntityAttribute.WINGED)
		{
			if(dir > 0) Render.drawImage(Render.rotate(wing, wingRotate), x + hatOffsX - 5, y + hatOffsY + 1 - (wingRotate / 10));
			else Render.drawImage(Render.rotate(wing, wingRotate), x + wing.getWidth() + hatOffsX + 5, y + hatOffsY + 1 - (wingRotate / 10), (x + hatOffsX + 5 + wing.getWidth()) - wing.getWidth(),  y + wing.getHeight() + hatOffsY + 1 - (wingRotate / 10), 0, 0, wing.getWidth(), wing.getHeight());
		}
	}

	public double getDir()
	{
		return dir;
	}

	public void spawnEntity()
	{
		if(!(this instanceof Projectile) && !(this instanceof TileEntity)) World.mobCount++;
		Main.getEntities().add(this);
	}

	public void despawnEntity()
	{
		if(!(this instanceof Projectile) && !(this instanceof TileEntity)) World.mobCount--;
		Main.getEntities().remove(this);
	}

	public float getHealth()
	{
		return health;
	}

	public void setHealth(float health)
	{
		this.health = health;
	}

	public float getMaxHealth()
	{
		return maxHealth;
	}

	public void setMaxHealth(float maxHealth)
	{
		this.maxHealth = maxHealth;
	}

	public void hurt(float damage)
	{
		this.health -= damage;
		lastCause = DeathCause.UNDEFINED;
		damageDelay = 100;
		lastDMG = (int)damage;
	}

	public void hurt(int damage, DeathCause death)
	{
		this.health -= damage;
		lastCause = death;
		damageDelay = 100;
		lastDMG = (int)damage;
	}

	public void heal(float heal)
	{
		this.health += heal;
		if(health > maxHealth) health = maxHealth;
	}

	public void addBuff(int id, int str, int dur)
	{
		if(activePotions.size() <= 0) activePotions.add(Buffs.buffs.get(id));
		Buffs.buffs.get(id).setStrength(str);
		Buffs.buffs.get(id).setDuration(dur);
		Buffs.buffs.get(id).setAffected(this);
		for(int i = 0; i < activePotions.size(); i++)
			if(Buffs.buffs.get(id) != activePotions.get(i)) activePotions.add(Buffs.buffs.get(id));
	}

	public void removeBuff(int id)
	{
		if(activePotions.get(id).getDuration() <= 0) activePotions.remove(id);
	}

	public boolean isCollidingWithBlock(Point pt1, Point pt2)
	{
		for(int x =(int) (this.x / Tile.tileSize); x < (int) (this.x / Tile.tileSize + width); x++)
		{
			for(int y =(int) (this.y / Tile.tileSize); y < (int) (this.y / Tile.tileSize + height); y++)
			{
				if(Main.world != null && x >= 0 && y >= 0 && x < Main.world.block.length && y < Main.world.block[0].length)
				{
					if(Main.world.block[x][y].id != Block.air.blockID && !(Main.world.block[x][y].getBlock() instanceof LiquidBlock))
					{
						if(Main.world.block[x][y].contains(pt1) || Main.world.block[x][y].contains(pt2))
						{
							if(Main.world.block[x][y].getBlock().isCollidable()) return true;
						}
					}
				}
			}
		}
		return false;
	}

	public Block getBlockCollidedWith(Point pt1, Point pt2)
	{
		for(int x =(int) (this.x / Tile.tileSize); x < (int) (this.x / Tile.tileSize + 3); x++)
		{
			for(int y =(int) (this.y / Tile.tileSize); y < (int) (this.y / Tile.tileSize + 3); y++)
			{
				if(Main.world != null && x >= 0 && y >= 0 && x < Main.world.block.length && y < Main.world.block[0].length)
				{
					if(Main.world.block[x][y].id != Block.air.blockID)
					{
						if(Main.world.block[x][y].contains(pt1) || Main.world.block[x][y].contains(pt2))
						{
							return Main.world.block[x][y].getBlock();
						}
					}
				}
			}
		}
		return Block.air;
	}

	public boolean isCollidingWithPlayer()
	{
		if(Main.player != null)
		{
			for(int x =(int) (this.x / Tile.tileSize - this.width); x < (int) (this.x / Tile.tileSize + 3); x++)
			{
				for(int y =(int) (this.y / Tile.tileSize - this.height); y < (int) (this.y / Tile.tileSize + 3); y++)
				{
					if(x >= 0 && y >= 0 && x < Main.world.block.length && y < Main.world.block[0].length)
					{
						if(this.x + width > Main.player.x && this.x < Main.player.x + Main.player.width && (this.y) + (height) > Main.player.y && (this.y) < (Main.player.y + Main.player.height))
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean isCollidingWithEntity(Entity e)
	{
		for(int x =(int) (this.x / Tile.tileSize - this.width); x < (int) (this.x / Tile.tileSize + 3); x++)
		{
			for(int y =(int) (this.y / Tile.tileSize - this.height); y < (int) (this.y / Tile.tileSize + 3); y++)
			{
				if(x >= 0 && y >= 0 && x < Main.world.block.length && y < Main.world.block[0].length)
				{
					if(this.x + width > e.x && this.x < e.x + e.width && (this.y) + (height) > e.y && (this.y) < (e.y + e.height))
					{
						return true;
					}
				}
			}
		}
		return false;
	}

	public int getDistanceToEntity(Entity e)
	{
		return (int)(this.x - e.x);
	}

	public int getDistanceToEntityWithoutNegative(Entity e)
	{
		int dis = (int)(this.x - e.x);
		if(dis < 0) dis = -dis;
		return dis;
	}

	public int getDistanceToEntityInBlocks(Entity e)
	{
		return (int)((this.x / Tile.tileSize) - (e.x / Tile.tileSize));
	}

	public int getDistanceToEntityWithoutNegativeInBlocks(Entity e)
	{
		int dis = (int)((this.x / Tile.tileSize) - (e.x / Tile.tileSize));
		if(dis < 0) dis = -dis;
		return dis;
	}

	public static int getEntityAmount(Class<? extends Entity> ent)
	{
		int i = 0;
		for(Entity e : Main.getEntities()) if(e.getClass().isAssignableFrom(ent)) i++;
		return i;
	}

	public EntityRenders getEntityRender()
	{
		return entityRender;
	}

	public EntityAttribute getEntityRarity()
	{
		return entityAttribute;
	}

	public void setEntityRarity(EntityAttribute ent)
	{
		entityAttribute = ent;
	}

	public EntityGUI getEntityGUI()
	{
		return entityGUI;
	}

	public String getEntityName()
	{
		return entityName;
	}

	public void setPosition(double x, double y)
	{
		this.x = x;
		this.y = (y + Tile.tileSize) - (int)height;
	}

	public void setStartHealth(int hp)
	{
		int extraHP = 0;
		if(entityAttribute == EntityAttribute.TOUGH) extraHP = hp;
		if(entityAttribute == EntityAttribute.WEAK) extraHP = -(hp / 3);
		health = maxHealth = hp + extraHP;
	}

	public void setMovingSpeed(double speed)
	{
		double extraSpeed = 0.0;
		if(entityAttribute == EntityAttribute.SPEEDY) extraSpeed = speed * .5;
		movingSpeed = speed + extraSpeed;
	}

	public boolean canBeHurt()
	{
		return canBeHurt;
	}

	public int[] getID()
	{
		return id;
	}

	public void setSpawningAnim(int[] spawningAnim, int frameLimit) 
	{
		this.spawningAnim = spawningAnim;
		this.spawnAnimLimit = frameLimit;
	}

	public int[] getSpawningAnim()
	{
		return spawningAnim;
	}
	
	public void disableEntityAttribute(EntityAttribute ent)
	{
		if(entityAttribute == ent) entityAttribute = EntityAttribute.DEFAULT;
	}
	
	public void setDrops(CraftableStack[] i)
	{
		drops = i;
	}
	
	public CraftableStack[] getDrops()
	{
		return drops;
	}
	
	public void setEXPAmount(int i)
	{
		expAmount = i;
	}
	
	public int getEXPAmount()
	{
		return expAmount;
	}
}
