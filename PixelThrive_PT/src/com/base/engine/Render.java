package com.base.engine;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

import org.newdawn.slick.Color;

@SuppressWarnings("deprecation")
public class Render
{
	public static void init(int WIDTH, int HEIGHT)
	{
		CoreEngine.consoleMessage("OpenGL - " + getOpenGLVersion());
		glEnable(GL_TEXTURE_2D);               
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);    
		// enable alpha blending
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glViewport(0, 0, WIDTH, HEIGHT);
		glMatrixMode(GL_MODELVIEW);
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, WIDTH, HEIGHT, 0, 1, -1);
		glMatrixMode(GL_MODELVIEW);
	}

	public static void render(Game game)
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		game.render();

		//		glEnable(GL_BLEND);
		//		glBlendFunc(GL_ONE, GL_ONE);
		//		glDepthMask(false);
		//		glDepthFunc(GL_EQUAL);

		//		for(BaseLight light : lights)
		//		{
		//			activeLight = light;
		//			object.renderAll(light.getShader(), this);
		//		}

		//		glDepthFunc(GL_LESS);
		//		glDepthMask(true);
		//		glDisable(GL_BLEND);
	}

	public static String getOpenGLVersion()
	{
		return glGetString(GL_VERSION);
	}

	public static void drawTriangle(Vector2f pos, Vector2f size, int r, int g, int b, float angle, float rX, float rY, float rZ)
	{
		float x = pos.getX();
		float y = pos.getY();
		float sizeX = size.getX();
		float sizeY = size.getY();
		//		setTextures(false);
		glTranslatef(x + sizeX / 2, y + sizeY / 2, 0);
		glRotatef(angle, rX, rY, rZ);
		glTranslatef(-(x + sizeX / 2), -(y + sizeY / 2), -0);
		new Color(r, g, b).bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); // top left
		glVertex2f(x + (sizeX / 2), y);
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(x, sizeY + y);
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(sizeX + x, sizeY + y);
		glTexCoord2f(1, 0); // top right
		glVertex2f(x + (sizeX / 2), y);
		glEnd();
	}

	public static void drawLine(Vector2f pos1, Vector2f pos2, float r, float g, float b, float lineWidth)
	{
		glColor3f(r, g, b);
		glBegin(GL_LINES);
		glLineWidth(lineWidth);
		glVertex2f(pos1.getX(), pos1.getY());
		glVertex2f(pos2.getX(), pos2.getY());
		glEnd();
	}

	public static void drawRectangle(Vector2f pos, Vector2f size, int red, int green, int blue)
	{
		float x = pos.getX();
		float y = pos.getY();
		new Color(red, green, blue).bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); // top left
		glVertex2f(x, y);
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(x, size.getY() + y);
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(size.getX() + x, size.getY() + y);
		glTexCoord2f(1, 0); // top right
		glVertex2f(size.getX() + x, y);
		glEnd();
	}
	
	public static void drawRectangle(Vector2f pos, Vector2f size, int col)
	{
		float x = pos.getX();
		float y = pos.getY();
		new Color(col).bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); // top left
		glVertex2f(x, y);
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(x, size.getY() + y);
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(size.getX() + x, size.getY() + y);
		glTexCoord2f(1, 0); // top right
		glVertex2f(size.getX() + x, y);
		glEnd();
	}
	
	public static void drawRectangle(Vector2f pos, Vector2f size, org.newdawn.slick.Color color)
	{
		float x = pos.getX();
		float y = pos.getY();
		color.bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); // top left
		glVertex2f(x, y);
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(x, size.getY() + y);
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(size.getX() + x, size.getY() + y);
		glTexCoord2f(1, 0); // top right
		glVertex2f(size.getX() + x, y);
		glEnd();
	}

	public static void drawRotatedRectangle(Vector2f pos, Vector2f size, int red, int green, int blue, float angle, float rX, float rY, float rZ)
	{
		float x = pos.getX();
		float y = pos.getY();
		float sizeX = size.getX();
		float sizeY = size.getY();
		glTranslatef(x + sizeX / 2, y + sizeY / 2, 0);
		glRotatef(angle, rX, rY, rZ);
		glTranslatef(-(x + sizeX / 2), -(y + sizeY / 2), -0);
		new Color(red, green, blue).bind();
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); // top left
		glVertex2f(x, y);
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(x, size.getY() + y);
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(size.getX() + x, size.getY() + y);
		glTexCoord2f(1, 0); // top right
		glVertex2f(size.getX() + x, y);
		glEnd();
	}

	public static void drawRectangle(Vector2f pos, Vector2f size, float red, float green, float blue)
	{
		float x = pos.getX();
		float y = pos.getY();
		glColor3f(red, green, blue);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); // top left
		glVertex2f(x, y);
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(x, size.getY() + y);
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(size.getX() + x, size.getY() + y);
		glTexCoord2f(1, 0); // top right
		glVertex2f(size.getX() + x, y);
		glEnd();
	}

	public static void drawRectangle(Vector2f pos, Vector2f size, float red, float green, float blue, float alpha)
	{
		float x = pos.getX();
		float y = pos.getY();
		glColor4f(red, green, blue, alpha);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); // top left
		glVertex2f(x, y);
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(x, size.getY() + y);
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(size.getX() + x, size.getY() + y);
		glTexCoord2f(1, 0); // top right
		glVertex2f(size.getX() + x, y);
		glEnd();
	}
	
	public static void drawRotatedRectangle(Vector2f pos, Vector2f size, float red, float green, float blue, float alpha, float angle, float rZ)
	{
		float x = pos.getX();
		float y = pos.getY();
		float sizeX = size.getX();
		float sizeY = size.getY();
		glTranslatef(x + sizeX / 2, y + sizeY / 2, 0);
		glRotatef(angle, 0f, 0f, rZ);
		glTranslatef(-(x + sizeX / 2), -(y + sizeY / 2), -0);
		glColor4f(red, green, blue, alpha);
		glBegin(GL_QUADS);
		glTexCoord2f(0, 0); // top left
		glVertex2f(x, y);
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(x, size.getY() + y);
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(size.getX() + x, size.getY() + y);
		glTexCoord2f(1, 0); // top right
		glVertex2f(size.getX() + x, y);
		glEnd();
	}
	
	public static void rotate(Vector2f pos, Vector2f size, float angle, float rX, float rY, float rZ)
	{
		float x = pos.getX();
		float y = pos.getY();
		float sizeX = size.getX();
		float sizeY = size.getY();
		glTranslatef(x + sizeX / 2, y + sizeY / 2, 0);
		glRotatef(angle, rX, rY, rZ);
		glTranslatef(-(x + sizeX / 2), -(y + sizeY / 2), -0);
	}
	
	public static void loadIdentity()
	{
		glLoadIdentity();
	}

	public static void setTextures(boolean b)
	{
		if(b) glEnable(GL_TEXTURE_2D);
		else glDisable(GL_TEXTURE_2D);
	}

	public static void drawTexturedRectangle(Vector2f pos, Texture tex)
	{
		drawScaledTexturedRectangle(pos, Vector2f.ONE, tex);
	}
	
	public static void drawScaledTexturedRectangle(Vector2f pos, Vector2f scale, Texture tex)
	{
		float x = pos.getX();
		float y = pos.getY();
		float width = scale.getX();
		float height = scale.getY();
		tex.bind();
		glColor3f(1f, 1f, 1f);
		glScalef(width, height, 0f);
		glBegin(GL_QUADS);
		glEnable(GL_TEXTURE_2D);
		glTexCoord2f(0, 0); // top left
		glVertex2f(x, y);
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(x, y + tex.getImageHeight());
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(x + tex.getImageWidth(), y + tex.getImageHeight());
		glTexCoord2f(1, 0); // top right
		glVertex2f(x + tex.getImageWidth(), y);
		glEnd();
	}
	
	public static void drawXFlipTexturedRectangle(Vector2f pos, Texture tex)
	{
		drawXFlipScaledTexturedRectangle(pos, Vector2f.ONE, tex);
	}
	
	public static void drawXFlipScaledTexturedRectangle(Vector2f pos, Vector2f scale, Texture tex)
	{
		float x = pos.getX();
		float y = pos.getY();
		float width = scale.getX();
		float height = scale.getY();
		tex.bind();
		glColor3f(1f, 1f, 1f);
		glScalef(width, height, 0f);
		glBegin(GL_QUADS);
		glEnable(GL_TEXTURE_2D);
		glTexCoord2f(0, 0); // top left
		glVertex2f(x + tex.getImageWidth(), y);
		glTexCoord2f(0, 1); // bottom left 
		glVertex2f(x + tex.getImageWidth(), y + tex.getImageHeight());
		glTexCoord2f(1, 1); // bottom right
		glVertex2f(x, y + tex.getImageHeight());
		glTexCoord2f(1, 0); // top right
		glVertex2f(x, y);
		glEnd();
	}

	public static void pushMatrix()
	{
		glPushMatrix();
	}

	public static void popMatrix()
	{
		glPopMatrix();
	}

	public static void drawCircle(Vector2f pos, Vector2f scale, float rad, int r, int g, int b)
	{
		double i, angle, x1, y1;
		for(i = 0; i < 360; i += 0.1)
		{
			angle = i;
			x1 = rad * Math.cos(angle * Math.PI / 180);
			y1 = rad * Math.sin(angle * Math.PI / 180);
			drawRectangle(pos.add((int)x1, (int)y1), scale, r, g, b);
		}
	}

	public static com.base.engine.Font getFont(float size)
	{
		// load font from a .ttf file
		try
		{
			Font awtFont3 = Font.createFont(Font.TRUETYPE_FONT, Game.class.getClass().getResourceAsStream("/Pixel.ttf"));
			awtFont3 = awtFont3.deriveFont(size);
			GraphicsEnvironment vc = GraphicsEnvironment.getLocalGraphicsEnvironment();
			vc.registerFont(awtFont3);
			com.base.engine.Font font = new com.base.engine.Font(awtFont3, true);
			Render.setTextures(false);
			return font;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static void drawString(Vector2f pos, String txt, Color col, com.base.engine.Font font)
	{
		drawString(pos, txt, col, font, Vector2f.ZERO);
	}
	
	public static void drawString(float x, float y, String txt, Color col, com.base.engine.Font font)
	{
		drawString(x, y, txt, col, font, Vector2f.ZERO);
	}
	
	public static void drawString(float x, float y, String txt, Color col, com.base.engine.Font font, Vector2f shadowOffs)
	{
		unBlur();
		font.drawString(x - shadowOffs.getX(), y - shadowOffs.getY(), txt, new Color(0x000000));
		font.drawString(x, y, txt, col);
	}
	
	public static void drawString(Vector2f pos, String txt, Color col, com.base.engine.Font font, Vector2f shadowOffs)
	{
		drawString(pos.getX(), pos.getY(), txt, col, font, shadowOffs);
	}
	
	public static Vector2f getStringSize(String txt, com.base.engine.Font font)
	{
		return new Vector2f(font.getWidth(txt), font.getHeight(txt));
	}
	
	public static void drawCenteredString(Vector2f min, Vector2f max, String txt, Color color, com.base.engine.Font font, Vector2f shadow)
	{
		Vector2f fontSize = new Vector2f(font.getWidth(txt), font.getHeight(txt));
		Vector2f pos = min.add(max.div(2)).sub(fontSize.div(2));
		drawString(pos, txt, color, font, shadow);
	}
	
	public static void drawCenteredStringFixedY(Vector2f min, Vector2f max, String txt, Color color, com.base.engine.Font font)
	{
		drawCenteredStringFixedY(min, max, txt, color, font, Vector2f.ZERO);
	}
	
	public static void drawCenteredStringFixedY(Vector2f min, Vector2f max, String txt, Color color, com.base.engine.Font font, Vector2f shadowOffs)
	{
		Vector2f fontSize = new Vector2f(font.getWidth(txt), font.getHeight(txt));
		Vector2f pos = min.add(max.div(2)).sub(fontSize.div(2));
		drawString(pos.getX() - shadowOffs.getX(), max.getY() - shadowOffs.getY(), txt, new Color(0x000000), font);
		drawString(pos.getX(), max.getY(), txt, color, font);
	}
	
	public static void unBlur()
	{
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
	}

	public static void drawCross(Vector2f pos, Vector2f size, float lineSize, int r, int g, int b) 
	{
		Vector2f pos2 = pos.add(size);
		Vector2f rectSize = new Vector2f(lineSize, lineSize);
		Vector2f center = pos.add(size.div(2));
		for(float i = 0; i < center.sub(pos).getX(); i++)
		{
			drawRectangle(pos.add(i), rectSize, r, g, b);
			drawRectangle(pos2.sub(i), rectSize, r, g, b);
			drawRectangle(pos.add(size.getX(), 0).add(-i, i), rectSize, r, g, b);
			drawRectangle(pos.add(0, size.getY()).add(i, -i), rectSize, r, g, b);
		}
	}

	public static void drawArrow(Vector2f pos, Vector2f size, int r, int g, int b, float angle, float rX, float rY, float rZ) 
	{
		pushMatrix();
		float x = pos.getX(), y = pos.getY();
		float sizeX = size.getX(), sizeY = size.getY();
		glTranslatef(x + sizeX / 2, y + sizeY / 2, 0);
		glRotatef(angle, rX, rY, rZ);
		glTranslatef(-(x + sizeX / 2), -(y + sizeY / 2), -0);
		drawRectangle(pos.add(size.div(4).getX(), 10), size.sub(size.div(2).getX(), 10), r, g, b);
		drawTriangle(pos.sub(0, size.div(2).getY() - 10), size.sub(0, size.div(2).getY()), r, g, b, 0, 0, 0, 1);
		popMatrix();
	}
}
