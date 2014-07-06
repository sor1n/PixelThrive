package net.PixelThrive.Client;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;

import javax.imageio.ImageIO;

public class LoadedImage
{
	private BufferedImage image;

	public LoadedImage(String path)
	{
		try
		{
			image = ImageIO.read(this.getClass().getClassLoader().getResource(path));
		}
		catch(IOException e) {}
	}

	public LoadedImage(BufferedImage img, int x, int y, int width, int height)
	{
		image = img.getSubimage(x, y, width, height);
	}
	
	public static Image copyBufferedImage(BufferedImage img)
	{
		Image image = new BufferedImage(img.getWidth(null), img.getHeight(null), 0);
		return image;
	}

	public BufferedImage getImage()
	{
		return image;
	}

	public int getImageWidth()
	{
		return image.getWidth();
	}

	public int getImageHeight()
	{
		return image.getHeight();
	}

	public BufferedImage bufferImage(Image image, int type)
	{
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		Graphics2D g = bufferedImage.createGraphics();
		g.drawImage(image, null, null);
		return bufferedImage;
	}

	public static BufferedImage deepCopy(BufferedImage bi)
	{
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}
