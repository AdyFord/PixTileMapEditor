package com.majoolwip.core.gui;

import java.awt.event.KeyEvent;

import com.majoolwip.core.GameContainer;
import com.majoolwip.core.Renderer;
import com.majoolwip.core.fx.Pixel;

public class CollisionPanel extends AbstractGUI
{
	private boolean visible = false;
	
	private int value = 0;
	
	public CollisionPanel()
	{

	}

	@Override
	public void init(GameContainer gc)
	{

	}

	@Override
	public void update(GameContainer gc, float dt)
	{
		if(gc.getInput().isKeyPressed(KeyEvent.VK_C))
		{
			visible = !visible;
		}
		
		if(visible)
		{
			if(gc.getInput().isKeyPressed(KeyEvent.VK_UP))
			{
				value++;
			}
			else if(gc.getInput().isKeyPressed(KeyEvent.VK_DOWN))
			{
				value--;
			}
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		if (visible)
		{
			r.setTranslate(false);

			r.drawFillRect(gc.getWidth() - 16 * 5, gc.getHeight() - 16 * 5, 16 * 5, 16 * 5, 0xff111111);

			r.drawString("Collison - " + value, Pixel.WHITE, (int)(gc.getWidth() - 16 * 2.5), (int)(gc.getHeight() - 16 * 2.5), true, true);
			
			r.setTranslate(true);
		}
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		this.value = value;
	}

}
