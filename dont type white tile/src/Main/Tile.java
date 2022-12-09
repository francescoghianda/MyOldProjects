package Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

public class Tile 
{
	
	public enum TileColor
	{
		WHITE, BLACK;
	}
	
	private double x;
	private double y;
	private int width;
	private int height;
	private TileColor color;
	private boolean typed;
	private boolean startTile;
	
	public Tile(double x, double y, TileColor color)
	{
		this.x = x;
		this.y = y;
		this.color = color;
		this.width = 50;
		this.height = 75;
	}
	
	public boolean isTapped(){return this.typed;}
	public boolean isBlackTile(){return color == TileColor.BLACK;}
	public void setStartTile(){this.startTile = true;}
	public boolean isStartTile(){return this.startTile;}
	public void setTyped(boolean b){this.typed = b;}
	public void setX(double x){this.x = x;}
	public void setY(double y){this.y = y;}
	public double getX(){return this.x;}
	public double getY(){return this.y;}
	public Rectangle getRectangleTile(){return new Rectangle((int)x, (int)y, width, height);}
	public void changeColor()
	{
		if(color.equals(TileColor.WHITE))
		{
			color = TileColor.BLACK;
		}
		else
		{
			color = TileColor.WHITE;
		}
	}
	public void changeColor(TileColor color)
	{
		this.color = color;
	}
	
	public void update()
	{
		
	}
	
	public void draw(Graphics2D g)
	{
		if(color.equals(TileColor.WHITE))
		{
			if(typed)
			{
				g.setColor(new Color(255, 0, 0));
			}
			else
			{
				g.setColor(new Color(255, 255, 255));
			}
		}
		else if(color.equals(TileColor.BLACK))
		{
			if(typed)
			{
				g.setColor(new Color(180, 180, 180));
			}
			else
			{
				g.setColor(new Color(0, 0, 0));
			}
		}
		g.fillRect((int)x, (int)y, width, height);
		g.setColor(new Color(0, 0, 0));
		g.drawRect((int)x, (int)y, width, height);
		
		if(startTile)
		{
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g.setColor(new Color(255, 255, 255));
			g.setFont(new Font("", Font.BOLD, 20));
			g.drawString("Start", (int)x, (int)y+(height/2)+7);
		}
	}
	
}
