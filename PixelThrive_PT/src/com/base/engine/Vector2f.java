package com.base.engine;

public class Vector2f 
{
	public static final Vector2f ZERO = new Vector2f(0), ONE = new Vector2f(1);

	private float x, y;

	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(float a)
	{
		this(a, a);
	}

	public float cross(Vector2f other)
	{
		return x * other.getY() - y * other.getX();
	}

	public Vector2f lerp(Vector2f dest, float lerpFactor)
	{
		return dest.sub(this).mul(lerpFactor).add(this);
	}

	public float length()
	{
		return (float)Math.sqrt(x * x + y * y);
	}

	public float dot(Vector2f other)
	{
		return x * other.x + y * other.y;
	}

	public Vector2f normalized()
	{
		return new Vector2f(x / length(), y / length());
	}

	public Vector2f rotate(float angle, Vector2f center)
	{
		double radians = Math.toRadians(angle);
		float cos = (float) Math.cos(radians);
		float sin = (float) Math.sin(radians);
		float x = this.x - center.getX();
		float y = this.y - center.getY();
		return new Vector2f((float)(x * cos - y * sin) + center.getX(), (float)(x * sin + y * cos) + center.getY());
	}

	public Vector2f add(Vector2f other)
	{
		return new Vector2f(this.x + other.getX(), this.y + other.getY());
	}

	public Vector2f add(float other)
	{
		return new Vector2f(this.x + other, this.y + other);
	}

	public Vector2f sub(Vector2f other)
	{
		return new Vector2f(this.x - other.getX(), this.y - other.getY());
	}

	public Vector2f sub(float other)
	{
		return new Vector2f(this.x - other, this.y - other);
	}

	public Vector2f mul(Vector2f other)
	{
		return new Vector2f(this.x * other.getX(), this.y * other.getY());
	}

	public Vector2f mul(float other)
	{
		return new Vector2f(this.x * other, this.y * other);
	}

	public Vector2f div(Vector2f other)
	{
		return new Vector2f(this.x / other.getX(), this.y / other.getY());
	}

	public Vector2f div(float other)
	{
		return new Vector2f(this.x / other, this.y / other);
	}

	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public int getXInt()
	{
		return (int)x;
	}

	public int getYInt()
	{
		return (int)y;
	}

	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}

	public boolean equals(Vector2f other)
	{
		return x == other.getX() && y == other.getY();
	}

	public boolean equals(float x, float y)
	{
		return x == this.x && y == this.y;
	}

	public float max()
	{
		return Math.max(x, y);
	}

	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	public Vector2f add(float x, float y)
	{
		return new Vector2f(x + this.x, this.y + y);
	}

	public Vector2f sub(float x, float y)
	{
		return new Vector2f(this.x - x, this.y - y);
	}

	public Vector2f div(float x, float y)
	{
		return new Vector2f(this.x / x, this.y / y);
	}

	public Vector2f mul(float x, float y)
	{
		return new Vector2f(this.x * x, this.y * y);
	}

	public Vector2f clamp(Vector2f pos, Vector2f min, Vector2f max)
	{
		float x = pos.getX();
		float y = pos.getY();
		if(x < min.getX()) x = min.getX();
		else if(x > max.getX()) x = max.getX();
		if(y < min.getY()) y = min.getY();
		else if(y > max.getY()) y = max.getY();
		return new Vector2f(x, y);
	}
	
	public Vector2f clamp(float min, float max)
	{
		return clamp(this, new Vector2f(min), new Vector2f(max));
	}

	public Vector2f abs()
	{
		return new Vector2f(Math.abs(x), Math.abs(y));
	}

	public boolean bigger(Vector2f r)
	{
		if(this.x > r.getX() && this.y > r.getY()) return true;
		else return false;
	}

	public boolean smaller(Vector2f r)
	{
		if(this.x < r.getX() && this.y < r.getY()) return true;
		else return false;
	}

	public Vector2f round()
	{
		return new Vector2f((int)x, (int)y);
	}

	public boolean bigger(int i) 
	{
		return this.x > i && this.y > i;
	}

	public boolean smaller(int i) 
	{
		return this.x < i && this.y < i;
	}

	public Vector2f swap() 
	{
		return new Vector2f(y, x);
	}
}
