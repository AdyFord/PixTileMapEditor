package com.majoolwip.editor;

import com.majoolwip.core.fx.Image;

public class TileSheet
{
	private int tileS;
	private Image[] tiles;
	
	public TileSheet(String path, int tileS)
	{
		this.tileS = tileS;
		Image image = new Image(path);
		
		tiles = new Image[(image.width / tileS) * (image.height / tileS)];
		
		for(int i = 0; i < image.width / tileS; i++)
		{
			for(int j = 0; j < image.height / tileS; j++)
			{
				int[] p = new int[tileS * tileS];
				
				for(int x = 0; x < 16; x++)
				{
					for(int y = 0; y < 16; y++)
					{
						p[x + y * tileS] = image.pixels[(x + i * tileS) + (y + j * tileS) * image.width];
					}
				}
				
				tiles[i + j * (image.width / tileS)] = new Image(tileS, tileS, p);
			}
		}
	}
	
	public Image getTileImage(int id)
	{
		if(id < 0)
		{
			return null;
		}
		return tiles[id];
	}

	public int getTileS()
	{
		return tileS;
	}

	public void setTileS(int tileS)
	{
		this.tileS = tileS;
	}
	
	public int getTilesLength()
	{
		return tiles.length;
	}
	
}
