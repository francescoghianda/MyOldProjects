package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Texture
{
	
	private BufferedImage texture;
	private BufferedImage[] sprites;
	private int spriteWidth;
	private int spriteHeight;
	private int NSpritesForColumn;
	private boolean isSpriteSheet;
	
	private boolean loaded;
	
	public Texture(File textureFile, int errWidth, int errHeight)
	{
		try
		{
			this.texture = ImageIO.read(textureFile);
		}
		catch(IOException e)
		{
			this.texture =  getNoTexture(errWidth, errHeight);
		}
	}
	
	public Texture(String path, int errWidth, int errHeight)
	{
		try
		{
			this.texture = ImageIO.read(getClass().getResourceAsStream(path));
		}
		catch(IOException | IllegalArgumentException e)
		{
			this.texture =  getNoTexture(errWidth, errHeight);
		}
	}
	
	public Texture(BufferedImage texture)
	{
		this.texture = texture;
	}
	
	public Texture(Texture spriteSheet, int spriteWidth, int spriteHeight)
	{
		this.isSpriteSheet = true;
		this.texture = spriteSheet.getTexture();
		this.NSpritesForColumn = spriteSheet.getWidth()/spriteWidth;
		this.spriteWidth = spriteWidth;
		this.spriteHeight = spriteHeight;
		sprites = new BufferedImage[NSpritesForColumn*(spriteSheet.getHeight()/spriteHeight)];
		loadSprites();
	}
	
	private void loadSprites()
	{
		int row = 0;
		int col = 0;
		for(int i = 0; i < sprites.length; i++)
		{
			if(col == NSpritesForColumn)
			{
				row++;
				col = 0;
			}
			sprites[i] = this.texture.getSubimage(spriteWidth*col, spriteHeight*row, spriteWidth, spriteHeight);
			
			col++;
		}
	}
	
	public boolean isSpriteSheet()
	{
		return this.isSpriteSheet;
	}
	public int getSpriteWidth()
	{
		return this.spriteWidth;
	}
	public int getSpriteHeight()
	{
		return this.spriteHeight;
	}
	public int getNSprites()
	{
		return this.sprites.length;
	}
	
	public BufferedImage[] getSprites()
	{
		if(isSpriteSheet)
		{
			return sprites;
		}
		else
		{
			return null;
		}
		
	}
	
	public BufferedImage getSprite(int n)
	{
		if(isSpriteSheet)
		{
			return sprites[n];
		}
		else
		{
			return null;
		}
	}
	
	public BufferedImage getTexture()
	{
		return this.texture;
	}
	
	public boolean isLoaded()
	{
		return this.loaded;
	}
	
	public Dimension getTextureSize()
	{
		return new Dimension(this.texture.getWidth(), this.texture.getHeight());
	}
	
	public int getWidth()
	{
		return this.texture.getWidth();
	}
	
	public int getHeight()
	{
		return this.texture.getHeight();
	}
	
	private BufferedImage getNoTexture(int width, int height)
	{
		BufferedImage notx = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = notx.getGraphics();
		g.setColor(new Color(255, 0, 255));
		g.fillRect(0, 0, width, height);
		g.setColor(new Color(0, 0, 0));
		g.drawLine(width/2, 0, width/2, height);
		g.drawLine(0, height/2, width, height/2);
		g.dispose();
		return notx;
	}
}
