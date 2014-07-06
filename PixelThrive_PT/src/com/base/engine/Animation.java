package com.base.engine;

public class Animation
{
	private Texture[] frames;
	private int curFrame = 0, frameInt = 0;
	private float frameDelay;

	public Animation(String sheet, Vector2f spriteSize, float frameDelay, Vector2f[] frameCoords)
	{
		this.frameDelay = frameDelay;
		frames = new Texture[frameCoords.length];
		for(int i = 0; i < frameCoords.length; i++) frames[i] = new Texture(sheet, frameCoords[i].mul(spriteSize), spriteSize, false);
	}

	public void update(float delta)
	{
		if(frameInt < frameDelay) frameInt++;
		else
		{
			frameInt = 0;
			nextFrame();
		}
	}
	
	public void render(Vector2f pos, Vector2f scale, int dir)
	{
		Render.unBlur();
		if(dir > 0) Render.drawScaledTexturedRectangle(pos, scale, frames[curFrame]);
		else Render.drawXFlipScaledTexturedRectangle(pos, scale, frames[curFrame]);
	}

	private void nextFrame()
	{
		if(curFrame < frames.length - 1) curFrame++;
		else curFrame = 0;
	}

	public void resetAnimation()
	{
		curFrame = frameInt = 0;
	}
}
