package net.PixelThrive.Client.renders;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import net.PixelThrive.Client.Main;
import net.PixelThrive.Client.world.Tile;

public class Render
{	
	private static Graphics2D g;

	public Render(Graphics2D gr)
	{
		g = gr;
	}
	
	public static void setPaint(Paint paint)
	{
		g.setPaint(paint);
	}

	public static void gRotate(double theta)
	{
		g.rotate(theta);
	}

	public static void drawRotatedString(String label, double theta, int x, int y)
	{
		Graphics2D g2D = (Graphics2D)g;
		AffineTransform fontAT = new AffineTransform();
		Font theFont = g2D.getFont();
		fontAT.rotate(Math.toRadians(theta));
		Font theDerivedFont = theFont.deriveFont(fontAT);
		g2D.setFont(theDerivedFont);
		setColor(0x000000);
		g2D.drawString(label, x, y);
		setColor(0xFFFFFF);
		g2D.drawString(label, x + 1, y + 1);
		g2D.setFont(theFont);
	}

	public static void fill(Shape s)
	{
		g.fill(s);
	}

	public static void draw(Shape s)
	{
		g.draw(s);
	}

	public static FontRenderContext getFontRenderContext()
	{
		return g.getFontRenderContext();
	}

	public static void translate(int x, int y)
	{
		g.translate(x, y);
	}

	public static Font getFont()
	{
		return g.getFont();
	}

	public static void setTransform(AffineTransform Tx)
	{
		g.setTransform(Tx);
	}

	public static AffineTransform getTransform()
	{
		return g.getTransform();
	}

	public static void transform(AffineTransform arg0)
	{
		g.transform(arg0);
	}

	public static int stringWidth(String txt, int fontsize)
	{
		setFont(Main.gameFont, fontsize);
		return getFontMetrics().stringWidth(txt);
	}

	public static void clearRect(int x, int y, int width, int height)
	{
		g.clearRect(x, y, width, height);
	}

	public static void setColor(int i)
	{
		g.setColor(new Color(i));
	}

	public static FontMetrics getFontMetrics()
	{
		return g.getFontMetrics();
	}

	public static void setComposite(AlphaComposite i)
	{
		g.setComposite(i);
	}

	public static void setColor(int r, int gg, int b, int a)
	{
		g.setColor(new Color(r, gg, b, a));
	}

	public static void setColor(int r, int gg, int b)
	{
		g.setColor(new Color(r, gg, b));
	}

	public static void setColor(Color col)
	{
		g.setColor(col);
	}

	public static void drawRect(int x, int y, int width, int height)
	{
		g.drawRect(x, y, width, height);
	}

	public static void fillRect(int x, int y, int width, int height)
	{
		g.fillRect(x, y, width, height);
	}

	public static void drawOval(int x, int y, int width, int height)
	{
		g.drawOval(x, y, width, height);
	}

	public static void fillOval(int x, int y, int width, int height)
	{
		g.fillOval(x, y, width, height);
	}

	public static void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight)
	{
		g.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
	}

	public static void setFont(String font, int size)
	{
		g.setFont(new Font(font, Font.PLAIN, size));
	}

	public static void setFont(Font font, int size)
	{
		g.setFont(new Font(font.getName(), Font.PLAIN, size));
	}

	public static void setFont(Font font)
	{
		g.setFont(font);
	}

	public static void setFont(Fonts font, int size)
	{
		g.setFont(new Font(font.getFontName(), Font.PLAIN, size));
	}

	public static void drawString(String s, int x, int y)
	{
		g.drawString(s, x, y);
	}

	public static void addAntiAliasing(boolean b)
	{
		if(b) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(!b) g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}

	public static void drawImage(Image img, int x, int y)
	{
		g.drawImage(img, x, y, null);
	}

	public static void drawImage(Image img, int x, int y, int dx, int dy, int sx, int sy, int sx1, int sy1)
	{
		g.drawImage(img, x, y, dx, dy, sx, sy, sx1, sy1, null);
	}

	public static void drawImage(Image img, int x, int y, int dx, int dy, int[] icon)
	{
		g.drawImage(img, x, y, dx, dy, icon[0] * Tile.tileSize, icon[1] * Tile.tileSize, icon[0] * Tile.tileSize + Tile.tileSize, icon[1] * Tile.tileSize + Tile.tileSize, null);
	}

	public static void drawImage(Image img, int x, int y, int dx, int dy, Texture icon)
	{
		g.drawImage(img, x, y, dx, dy, icon.x * Tile.tileSize, icon.y * Tile.tileSize, icon.x * Tile.tileSize + Tile.tileSize, icon.y * Tile.tileSize + Tile.tileSize, null);
	}

	public static void drawImage(Image img, int x, int y, int width, int height)
	{
		g.drawImage(img, x, y, width, height, null);
	}

	public static void drawLine(int x, int y, int dx, int dy)
	{
		g.drawLine(x, y, dx, dy);
	}

	public static void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle)
	{
		g.drawArc(x, y, width, height, startAngle, arcAngle);
	}

	public static void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight)
	{
		g.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
	}

	public enum Fonts
	{
		SERIF(Font.SERIF), SANS_SERIF(Font.SANS_SERIF), DIALOG(Font.DIALOG), DIALOG_INPUT(Font.DIALOG_INPUT), MONOSPACED(Font.MONOSPACED); //COMIC_ANDY(Main.comicAndy.getName());

		private String name;

		Fonts(String i)
		{
			this.name = i;
			getFont();
		}

		public Font getFont()
		{
			return Font.decode(name);
		}

		public String getFontName()
		{
			return name;
		}
	}

	public static BufferedImage rotate(BufferedImage image, double angle)
	{
		double sin = Math.abs(Math.sin(Math.toRadians(angle))), cos = Math.abs(Math.cos(Math.toRadians(angle)));
		int w = image.getWidth(), h = image.getHeight();
		int neww = (int)Math.floor(w * cos + h * sin), newh = (int)Math.floor(h * cos + w * sin);
		GraphicsConfiguration gc = Main.getGameInstance().getGraphicsConfiguration();
		BufferedImage result = gc.createCompatibleImage(neww, newh, Transparency.TRANSLUCENT);
		Graphics2D g = result.createGraphics();
		g.translate((neww - w) / 2, (newh - h) / 2);
		g.rotate(Math.toRadians(angle), w / 2, h / 2);
		g.drawRenderedImage(image, null);
		g.dispose();
		return result;
	}

	/**
	 * This method flips the image horizontally
	 * @param img --> BufferedImage Object to be flipped horizontally
	 * @return
	 */
	public static BufferedImage flipHorizontal(BufferedImage img)
	{
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
		g.dispose();
		return dimg;
	}

	/**
	 * This method flips the image vertically
	 * @param img --> BufferedImage object to be flipped
	 * @return
	 */
	public static BufferedImage flipVertical(BufferedImage img)
	{
		int w = img.getWidth();
		int h = img.getHeight();
		BufferedImage dimg = new BufferedImage(w, h, img.getColorModel().getTransparency());
		Graphics2D g = dimg.createGraphics();
		g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
		g.dispose();
		return dimg;
	}

	public static Image addGrayScale(Image image)
	{
		Image myImage = new ImageIcon(image).getImage();
		BufferedImage bufferedImage = new BufferedImage(myImage.getWidth(null), myImage.getHeight(null), BufferedImage.TYPE_BYTE_GRAY);
		Graphics gi = bufferedImage.getGraphics();
		gi.drawImage(myImage, 0, 0, null);
		gi.dispose();

		return bufferedImage;
	}
}
