package com.base.engine;

import java.util.ArrayList;

public abstract class GameObject
{
	protected ArrayList<GameObject> children = new ArrayList<GameObject>();
	
	public void input(float delta)
	{
		for(int i = 0; i < children.size(); i++) children.get(i).input(delta);
	}
	
	public void update(float delta)
	{
		for(int i = 0; i < children.size(); i++) children.get(i).update(delta);
	}
	
	public void render()
	{
		for(int i = 0; i < children.size(); i++) children.get(i).render();
	}
	
	public ArrayList<GameObject> getChildren()
	{
		return children;
	}
	
	public GameObject addChild(GameObject object)
	{
		children.add(object);
		return this;
	}
}
