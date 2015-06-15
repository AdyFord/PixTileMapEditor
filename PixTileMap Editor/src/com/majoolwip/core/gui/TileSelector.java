package com.majoolwip.core.gui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.majoolwip.core.GameContainer;
import com.majoolwip.core.Renderer;
import com.majoolwip.editor.TileSheet;

public class TileSelector extends AbstractGUI
{
	private TileSheet tileSheet;
	private int selected = 0;
	private boolean display = false;
	
	public TileSelector(TileSheet tileSheet)
	{
		this.tileSheet = tileSheet;
		this.name = "tileSelector";
	}
	
	@Override
	public void init(GameContainer gc)
	{

	}

	@Override
	public void update(GameContainer gc, float dt)
	{
		this.interacted = false;
		
		if(gc.getInput().isKeyPressed(KeyEvent.VK_M))
		{
			display = !display;
		}
		
		if(gc.getInput().isButtonPressed(MouseEvent.BUTTON1) && display)
		{
			if(gc.getInput().getMouseX() > (gc.getWidth() - tileSheet.getTileS() * 5) + 1)
			{
				int x = (gc.getInput().getMouseX() - (gc.getWidth() - tileSheet.getTileS() * 5)) / tileSheet.getTileS();
				selected = x + (gc.getInput().getMouseY() / tileSheet.getTileS()) * 5;
				
				this.interacted = true;
			}
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		if(display)
		{
			r.setTranslate(false);
			r.drawFillRect(gc.getWidth() - tileSheet.getTileS() * 5, 0, tileSheet.getTileS() * 5, gc.getHeight(), 0xff111111);
			
			for(int x = 0; x < 5; x++)
			{
				for(int y = 0; y < (gc.getHeight() / tileSheet.getTileS()) - 5; y++)
				{
					r.drawImage(tileSheet.getTileImage(x + y * 5), x * tileSheet.getTileS() + (gc.getWidth() - tileSheet.getTileS() * 5), y * tileSheet.getTileS());
				}
			}
			
			
			r.setTranslate(true);
		}
	}

	public int getSelected()
	{
		return selected;
	}

	public void setSelected(int selected)
	{
		this.selected = selected;
	}
}
