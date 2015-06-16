package com.majoolwip.core.gui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.majoolwip.core.GameContainer;
import com.majoolwip.core.Renderer;
import com.majoolwip.core.fx.Pixel;
import com.majoolwip.core.fx.ShadowType;
import com.majoolwip.editor.TileSheet;

public class TileSelector extends AbstractGUI
{
	private TileSheet tileSheet;
	private int selected = 0;
	private boolean visible = false;

	private int page, pageTotal;

	public TileSelector(TileSheet tileSheet)
	{
		this.tileSheet = tileSheet;
		this.name = "tileSelector";
	}

	@Override
	public void init(GameContainer gc)
	{
		page = 1;
		pageTotal = tileSheet.getTilesLength() / (5 * ((gc.getHeight() / tileSheet.getTileS()) - 5));

		if (pageTotal * (5 * ((gc.getHeight() / tileSheet.getTileS()) - 5)) > tileSheet.getTilesLength())
		{
			pageTotal++;
		}
	}

	@Override
	public void update(GameContainer gc, float dt)
	{
		this.interacted = false;

		if (gc.getInput().isKeyPressed(KeyEvent.VK_M))
		{
			visible = !visible;
		}

		if (gc.getInput().isButtonPressed(MouseEvent.BUTTON1) && visible)
		{
			if (gc.getInput().getMouseX() > (gc.getWidth() - tileSheet.getTileS() * 5) + 1)
			{
				int x = (gc.getInput().getMouseX() - (gc.getWidth() - tileSheet.getTileS() * 5)) / tileSheet.getTileS();
				selected = (x + (gc.getInput().getMouseY() / tileSheet.getTileS()) * 5) + ((page - 1) * (5 * ((gc.getHeight() / tileSheet.getTileS()) - 5)));

				this.interacted = true;
			}
		}

		if (visible)
		{
			if (gc.getInput().isKeyPressed(KeyEvent.VK_PERIOD))
			{
				page++;

				if (page > pageTotal)
				{
					page = 1;
				}
			}

			if (gc.getInput().isKeyPressed(KeyEvent.VK_COMMA))
			{
				page--;

				if (page < 1)
				{
					page = pageTotal;
				}
			}
		}
	}

	@Override
	public void render(GameContainer gc, Renderer r)
	{
		if (visible)
		{
			r.setTranslate(false);
			r.drawFillRect(gc.getWidth() - tileSheet.getTileS() * 5, 0, tileSheet.getTileS() * 5, gc.getHeight(), 0xff111111);

			int tempPage = page - 1;

			for (int x = 0; x < 5; x++)
			{
				for (int y = 0; y < (gc.getHeight() / tileSheet.getTileS()) - 5; y++)
				{
					r.drawImage(tileSheet.getTileImage((x + y * 5) + (tempPage * (5 * ((gc.getHeight() / tileSheet.getTileS()) - 5)))), x * tileSheet.getTileS() + (gc.getWidth() - tileSheet.getTileS() * 5), y * tileSheet.getTileS());

					if ((x + y * 5) + (tempPage * (5 * ((gc.getHeight() / tileSheet.getTileS()) - 5))) == selected)
					{
						r.drawRect(x * tileSheet.getTileS() + (gc.getWidth() - tileSheet.getTileS() * 5), y * tileSheet.getTileS(), tileSheet.getTileS() - 1, tileSheet.getTileS() - 1, 0xfff82e4a, ShadowType.NONE);
					}
				}
			}

			r.drawString("Page - " + page + " of " + pageTotal, Pixel.WHITE, (gc.getWidth() - tileSheet.getTileS() * 5) + (tileSheet.getTileS() * 5 / 2), ((gc.getHeight() / tileSheet.getTileS()) - 5) * tileSheet.getTileS() + tileSheet.getTileS(), true, true);

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

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}
}
