package com.majoolwip.editor;

import java.awt.event.KeyEvent;

import com.majoolwip.core.GameContainer;
import com.majoolwip.core.Renderer;

public class Camera
{
	private float camX, camY;
	
	public Camera()
	{
		camX = 0;
		camY = 0;
	}
	
	public void init(GameContainer gc)
	{

	}

	public void update(GameContainer gc, float dt)
	{
		if(gc.getInput().isKey(KeyEvent.VK_D))
		{
			camX += dt * 50;
		}
		
		if(gc.getInput().isKey(KeyEvent.VK_A))
		{
			camX -= dt * 50;
		}
		
		if(gc.getInput().isKey(KeyEvent.VK_W))
		{
			camY -= dt * 50;
		}
		
		if(gc.getInput().isKey(KeyEvent.VK_S))
		{
			camY += dt * 50;
		}
	}

	public void render(GameContainer gc, Renderer r)
	{
		r.setTransX((int)(camX + 0.5f));
		r.setTransY((int)(camY + 0.5f));
	}

	public float getCamX()
	{
		return camX;
	}

	public void setCamX(float camX)
	{
		this.camX = camX;
	}

	public float getCamY()
	{
		return camY;
	}

	public void setCamY(float camY)
	{
		this.camY = camY;
	}
}
