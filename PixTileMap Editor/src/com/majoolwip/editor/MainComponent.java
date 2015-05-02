package com.majoolwip.editor;

import com.majoolwip.core.AbstractGame;
import com.majoolwip.core.GameContainer;
import com.majoolwip.core.Renderer;

public class MainComponent extends AbstractGame
{
	private Level level;

	public MainComponent()
	{
		level = new Level(100, 100);
	}

	@Override
	public void init(GameContainer gc)
	{
		level.init(gc);
	}

	@Override
	public void update(GameContainer gc, float dt)
	{
		level.update(gc, dt);
	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		level.render(gc, r);
	}

	public static void main(String args[])
	{
		GameContainer gc = new GameContainer(new MainComponent());
		gc.setWidth(320);
		gc.setHeight(240);
		gc.setScale(2.5f);
		gc.setClearScreen(true);
		gc.setLockFrameRate(true);
		gc.start();
	}

}
