package com.base.engine;

import java.util.ArrayList;

public class Game
{
	private ArrayList<GameObject> objects = new ArrayList<GameObject>();
	
	private String title;
	
	public Game(String title)
	{
		this.title = title;
	}
	
	public void init(){}
	
	public void input(float delta)
	{
		for(int i = 0; i < objects.size(); i++) objects.get(i).input(delta);
	}
	
	public void update(float delta)
	{
		for(int i = 0; i < objects.size(); i++) objects.get(i).update(delta);
	}
	
	public void render()
	{
		for(int i = 0; i < objects.size(); i++) objects.get(i).render();
	}
	
	public void addObject(GameObject object)
	{
		objects.add(object);
	}
	
	public String getTitle()
	{
		return title;
	}
}
