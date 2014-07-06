package com.base.engine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;

public class Texture 
{
	private static HashMap<String, TextureResource> loadedTextures = new HashMap<String, TextureResource>();
	private TextureResource resource;
	private String fileName;
	private Vector2f size;

	public Texture(String path, Vector2f texCoord, Vector2f size)
	{
		this.size = size;
		this.fileName = path;
		TextureResource oldResource = loadedTextures.get(fileName);
		if(oldResource != null)
		{
			resource = oldResource;
			resource.addReference();
		}
		else
		{
			resource = loadTexture(path, texCoord, size);
			loadedTextures.put(fileName, resource);
		}
	}
	
	public Texture(String path, Vector2f texCoord, Vector2f size, boolean checkIfExists)
	{
		this.size = size;
		this.fileName = path;
		TextureResource oldResource = loadedTextures.get(fileName);
		if(oldResource != null && checkIfExists)
		{
			resource = oldResource;
			resource.addReference();
		}
		else
		{
			resource = loadTexture(path, texCoord, size);
			loadedTextures.put(fileName, resource);
		}
	}

	public Texture(String path)
	{
		this(path, Vector2f.ZERO, Vector2f.ZERO);
	}

	public Texture(int width, int height)
	{
		this.size = new Vector2f(width, height);
		this.fileName = "";
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		resource = loadTexture(bi, false);
	}

	@Override
	protected void finalize()
	{
		if(resource.removeReference() && !fileName.isEmpty()) loadedTextures.remove(fileName);
	}

	public void bind()
	{
		bind(0);
	}

	public void bind(int slot)
	{
		assert(slot >= 0 && slot <= 31);
		glActiveTexture(GL_TEXTURE0 + slot);
		glBindTexture(GL_TEXTURE_2D, resource.getID());
	}

	public void unbind()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	public int getID()
	{
		return resource.getID();
	}

	public int getImageWidth()
	{
		return (int)size.getX();
	}	

	public int getImageHeight()
	{
		return (int)size.getY();
	}

	private TextureResource loadTexture(String fileName, Vector2f texCoord, Vector2f size)
	{
		try
		{
			BufferedImage image = ImageIO.read(new File("./res/" + fileName));
			if(size != Vector2f.ZERO) image = image.getSubimage((int)texCoord.getX(), (int)texCoord.getY(), (int)size.getX(), (int)size.getY());
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
			boolean hasAlpha = image.getColorModel().hasAlpha();

			for(int y = 0; y < image.getHeight(); y++)
			{
				for(int x = 0; x < image.getWidth(); x++)
				{
					int pixel = pixels[y * image.getWidth() + x];
					buffer.put((byte)((pixel >> 16) & 0xFF));
					buffer.put((byte)((pixel >> 8) & 0xFF));
					buffer.put((byte)((pixel) & 0xFF));
					if(hasAlpha) buffer.put((byte)((pixel >> 24) & 0xFF));
					else buffer.put((byte)(0xFF));
				}
			}

			buffer.flip();

			TextureResource resource = new TextureResource();
			glBindTexture(GL_TEXTURE_2D, resource.getID());

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
										   //GL_RGB
			return resource;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
	private TextureResource loadTexture(BufferedImage image, boolean init)
	{
		try
		{
			int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
			ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
			boolean hasAlpha = image.getColorModel().hasAlpha();

			for(int y = 0; y < image.getHeight(); y++)
			{
				for(int x = 0; x < image.getWidth(); x++)
				{
					int pixel = pixels[y * image.getWidth() + x];
					buffer.put((byte)((pixel >> 16) & 0xFF));
					buffer.put((byte)((pixel >> 8) & 0xFF));
					buffer.put((byte)((pixel) & 0xFF));
					if(hasAlpha) buffer.put((byte)((pixel >> 24) & 0xFF));
					else buffer.put((byte)(0xFF));
				}
			}

			buffer.flip();

			TextureResource resource = new TextureResource();
			glBindTexture(GL_TEXTURE_2D, resource.getID());

			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
			glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
			glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

			if(init) glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);
			return resource;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	public void createImage(int[][] pixels)
	{
		int width = pixels.length;
		int height = pixels[0].length;
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for(int x = 0; x < width; x++)
			for(int y = 0; y < height; y++) img.setRGB(x, y, pixels[x][y]);
		resource = loadTexture(img, true);
	}
}
