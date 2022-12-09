package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class Block 
{
	private double xi;
	private double yi;
	private double x;
	private double y;
	private int width;
	private int height;
	private double speed;
	private int colorAlfa;
	private int tileID;
	private TileMap map;
	
	private static Texture spriteSheet = new Texture(new Texture("/Blocks/blocksSpriteSheet.png", 128, 40), 40, 40);
	
	public Block(double x, double y, int tileID, TileMap map)
	{
		this.x = x;
		this.y = y;
		this.xi = x;
		this.yi = y;
		this.width = 40;
		this.height = 40;
		this.speed = 8;
		this.colorAlfa = 255;
		
		this.tileID = tileID;
		
		this.map = map;
	}
	
	public void setX(double x){this.x = x;}
	public double getX(){return this.x;}
	public double getY(){return this.y;}
	public int getWidth(){return this.width;}
	public int getHeight(){return this.height;}
	public double getSpeed(){return this.speed;}
	public int getTileID(){return this.tileID;}
	
	public boolean isSquare()
	{
		if(tileID == 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean isTriangle()
	{
		if(tileID == 2)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public boolean isWinBlock()
	{
		if(tileID == 3)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int[] getPoints(String xy)
	{
		if(tileID == 2)
		{
			if(xy.equalsIgnoreCase("x"))
			{
				int[] xPoints = {(int)x, (int)(x+(width/2)), (int)(x+width)};
				return xPoints;
			}
			else if(xy.equalsIgnoreCase("y"))
			{
				int yPoints[] = {(int)(y+height), (int)y, (int)(y+height)};
				return yPoints;
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	public void setColorAlfa(int alfa)
	{
		this.colorAlfa = alfa;
	}
	
	public void reset()
	{
		this.x = xi;
		this.y = yi;
	}
	
	public void update()
	{
		//x -= speed;
	}
	
	public void draw(Graphics2D g)
	{
		if(!(this.x+this.width <= 60) && !(this.x >= GamePanel.WIDTH-60))
		{
			if(tileID == 1)
			{	
				g.setPaint(new TexturePaint(spriteSheet.getSprite(0), new Rectangle((int)x, (int)y, 40, 40)));
				g.fillRect((int)x, (int)y, width, height);
				
				//g.setColor(new Color(0, 0, 255, colorAlfa));
				//g.fillRect((int)x, (int)y, width, height);
				//g.setColor(new Color(0, 0, 0, colorAlfa));
				//g.drawRect((int)x, (int)y, width, height);
			}
			else if(tileID == 2)
			{
				int xPoints[] = {(int)x, (int)(x+(width/2)), (int)(x+width)};
				int yPoints[] = {(int)(y+height), (int)y, (int)(y+height)};
				
				g.setPaint(new TexturePaint(spriteSheet.getSprite(1), new Rectangle((int)x, (int)y, 40, 40)));
				g.fillPolygon(xPoints, yPoints, 3);
				
				//g.setColor(new Color(160, 0, 255, colorAlfa));
				//g.fillPolygon(xPoints, yPoints, 3);
				g.setColor(new Color(0, 0, 0, colorAlfa));
				g.drawPolygon(xPoints, yPoints, 3);
			}
			else if(tileID == 3)
			{
				g.setPaint(new TexturePaint(spriteSheet.getSprite(2), new Rectangle((int)x, (int)y, 40, 40)));
				g.fillRect((int)x, (int)y, width, height);
			}
			else if(tileID == 4)
			{
				//g.setColor(Color.BLACK);
				//g.fillRect((int)x, (int)y, 40, 40);
			}
		}
		
	}
}
