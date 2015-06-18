package com.majoolwip.editor;

import java.awt.FileDialog;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.majoolwip.core.GameContainer;
import com.majoolwip.core.Renderer;
import com.majoolwip.core.fx.Image;
import com.majoolwip.core.gui.Button;
import com.majoolwip.core.gui.CollisionPanel;
import com.majoolwip.core.gui.GUIContainer;
import com.majoolwip.core.gui.TileSelector;

public class Level
{
	private int levelW, levelH;
	private int[] tiles;
	private int[] collision;
	private FileDialog fd = null;
	private Camera camera;
	private int tileW = 16, tileH = 16;
	private GUIContainer guiC = new GUIContainer();
	private TileSheet tileSheet;
	private TileSelector ts;
	private CollisionPanel cp;
	
	public Level(int levelW, int levelH)
	{
		this.levelW = levelW;
		this.levelH = levelH;
		tiles = new int[levelW * levelH];
		collision = new int[levelW * levelH];
		camera = new Camera();
		guiC.addGUIObject(new Button("Save", null, 0, 0, 32, 16));
		guiC.addGUIObject(new Button("Load", null, 32, 0, 32, 16));
		
		tileSheet = new TileSheet("/tileSheet.png", 16);
		ts = new TileSelector(tileSheet);
		guiC.addGUIObject(ts);
		
		cp = new CollisionPanel();
		guiC.addGUIObject(cp);
	}

	public void init(GameContainer gc)
	{
		fd = new FileDialog(gc.getWindow().getFrame());
		ts.init(gc);
	}

	public void update(GameContainer gc, float dt)
	{
		camera.update(gc, dt);
		guiC.update(gc, dt);

		if(cp.isVisible())
		{
			ts.setVisible(false);
		}
		
		if(ts.isVisible())
		{
			cp.setVisible(false);
		}
		
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
		else if (gc.getInput().isButtonPressed(MouseEvent.BUTTON1))
		{
			if(ts.isVisible())
			{
				setTile((gc.getInput().getMouseX() + (int) (camera.getCamX() + 0.5f)) / tileW, (gc.getInput().getMouseY() + (int) (camera.getCamY() + 0.5f)) / tileH, ts.getSelected());
			}
			else if(cp.isVisible())
			{
				setCollision((gc.getInput().getMouseX() + (int) (camera.getCamX() + 0.5f)) / tileW, (gc.getInput().getMouseY() + (int) (camera.getCamY() + 0.5f)) / tileH, cp.getValue());
			}
		}
	}

	public void render(GameContainer gc, Renderer r)
	{
		camera.render(gc, r);

		for (int x = (int) (camera.getCamX()) / tileW; x < ((int) (camera.getCamX() + gc.getWidth()) / tileW) + 1; x++)
		{
			for (int y = (int) (camera.getCamY()) / tileH; y < ((int) (camera.getCamY() + gc.getHeight()) / tileH) + 1; y++)
			{
				if(x < 0 || x >= levelW || y < 0 || y >= levelH)
					continue;
				Image image = tileSheet.getTileImage(tiles[x + y * levelW]);
				if(image != null)
				{
					r.drawImage(image , x * tileW, y * tileH);
				}
				
				if(cp.isVisible())
				{
					r.drawString("" + collision[x + y * levelW], 0xff111111, x * tileW + (tileW / 2), y * tileH + (tileH / 2), true, true);
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
				out.write(collision[i]);
			}

			out.close();

			System.out.println("worked");
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	private void loadMap(String path)
	{
		if (path == null)
		{
			System.out.println("failed");
			return;
		}

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
				collision[i] = in.read();
			}

			in.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public int getTile(int x, int y)
	{
		if (x < 0 || x >= levelW || y < 0 || y >= levelH)
			return -1;
		return tiles[x + y * levelW];
	}

	public void setTile(int x, int y, int value)
	{
		if (x < 0 || x >= levelW || y < 0 || y >= levelH)
			return;
		tiles[x + y * levelW] = value;
	}
	
	public int getCollision(int x, int y)
	{
		if (x < 0 || x >= levelW || y < 0 || y >= levelH)
			return -1;
		return collision[x + y * levelW];
	}

	public void setCollision(int x, int y, int value)
	{
		if (x < 0 || x >= levelW || y < 0 || y >= levelH)
			return;
		collision[x + y * levelW] = value;
	}
}
