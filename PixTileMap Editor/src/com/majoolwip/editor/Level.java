package com.majoolwip.editor;

import java.awt.FileDialog;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.majoolwip.core.GameContainer;
import com.majoolwip.core.Renderer;
import com.majoolwip.core.fx.Pixel;
import com.majoolwip.core.fx.ShadowType;
import com.majoolwip.core.gui.Button;
import com.majoolwip.core.gui.GUIContainer;

public class Level
{
	private int levelW, levelH;
	private int[] tiles;
	private FileDialog fd = null;
	private Camera camera;
	private int tileW = 16, tileH = 16;
	private GUIContainer guiC = new GUIContainer();

	public Level(int levelW, int levelH)
	{
		this.levelW = levelW;
		this.levelH = levelH;
		tiles = new int[levelW * levelH];
		camera = new Camera();
		guiC.addGUIObject(new Button("Save", null, 0, 0, 32, 16));
		guiC.addGUIObject(new Button("Load", null, 32, 0, 32, 16));
	}

	public void init(GameContainer gc)
	{
		fd = new FileDialog(gc.getWindow().getFrame());
	}

	public void update(GameContainer gc, float dt)
	{
		camera.update(gc, dt);
		guiC.update(gc, dt);

		if (guiC.getOuputName() != null)
		{
			if (guiC.getOuputName().equals("Save"))
			{
				fd.setMode(FileDialog.SAVE);
				fd.setVisible(true);
				saveMap(fd.getFile());
			}

			if (guiC.getOuputName().equals("Load"))
			{
				fd.setMode(FileDialog.LOAD);
				fd.setVisible(true);
				loadMap(fd.getFile());
			}
		}

		if (gc.getInput().isButtonPressed(MouseEvent.BUTTON1))
		{
			setTile((gc.getInput().getMouseX() + (int) (camera.getCamX() + 0.5f)) / tileW, (gc.getInput().getMouseY() + (int) (camera.getCamY() + 0.5f)) / tileH, 1);
		}
	}

	public void render(GameContainer gc, Renderer r)
	{
		camera.render(gc, r);

		for (int x = (int) (camera.getCamX()) / tileW; x < ((int) (camera.getCamX() + gc.getWidth()) / tileW) + 1; x++)
		{
			for (int y = (int) (camera.getCamY()) / tileH; y < ((int) (camera.getCamY() + gc.getHeight()) / tileH) + 1; y++)
			{
				if (getTile(x, y) == 0)
				{
					r.drawRect(x * tileW, y * tileH, tileW, tileH, Pixel.WHITE, ShadowType.NONE);
				} else
				{
					r.drawFillRect(x * tileW, y * tileH, tileW, tileH, Pixel.RED, ShadowType.NONE);
				}
			}
		}

		guiC.render(gc, r);
	}

	private void saveMap(String path)
	{
		if (path == null)
			return;

		try
		{
			File file = new File(path);
			BufferedWriter out = new BufferedWriter(new FileWriter(file));

			out.write(levelW);
			out.write(levelH);

			for (int i = 0; i < tiles.length; i++)
			{
				out.write(tiles[i]);
			}

			out.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void loadMap(String path)
	{
		if (path == null)
			return;

		try
		{
			File file = new File(path);
			BufferedReader in = new BufferedReader(new FileReader(file));

			levelW = in.read();
			levelH = in.read();

			tiles = new int[levelW * levelH];

			for (int i = 0; i < tiles.length; i++)
			{
				tiles[i] = in.read();
			}

			in.close();

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private int getTile(int x, int y)
	{
		if (x < 0 || x >= levelW || y < 0 || y >= levelH)
			return -1;
		return tiles[x + y * levelW];
	}

	private void setTile(int x, int y, int value)
	{
		if (x < 0 || x >= levelW || y < 0 || y >= levelH)
			return;
		tiles[x + y * levelW] = value;
	}
}
