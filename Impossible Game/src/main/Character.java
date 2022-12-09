package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class Character 
{
	
	private Texture texture;
	
	private double x;
	private double y;
	
	private int size;
	
	private double dif;
	
	private double dx;
	private double dy;
	
	private boolean selected;
	
	private boolean showInfo;
	
	private BufferedImage info;
	
	private double r;
	
	public Character(Texture texture, double x, double y)
	{
		this.texture = texture;
		this.x = x;
		this.y = y;
		
		this.dx = x;
		this.dy = y;
		
		this.size = 40;
		
		info = new BufferedImage(360, 250, BufferedImage.TYPE_INT_ARGB);
		info.getGraphics().setColor(Color.WHITE);
		info.getGraphics().fillRect(0, 0, 360, 250);
	}
	
	public void showInfo(boolean b)
	{
		this.showInfo = b;
	}
	
	public double getX()
	{
		return this.dx;
	}
	
	public double getY()
	{
		return this.dy;
	}
	
	public void setX(double x)
	{
		this.dx = x;
	}
	
	public void setY(double y)
	{
		this.dy = y;
	}
	
	public void draw(Graphics2D g)
	{
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if(dx > x)x+=5;
		if(dx < x)x-=5;
		if(dy > y)y+=5;
		if(dy < y)y-=5;
		
		if(x  <= 360)
		{
			if(x == 120)
			{
				if(size != 65)
				{
					size++;
					dif += 0.5;
				}
			}
			else if(x != 120)
			{
				if(size != 40)
				{
					size--;
					dif -= 0.5;
				}
			}
			
			r+=0.01;;
			
			AffineTransform at = g.getTransform();
			
			if(selected)g.rotate(r, (int)(x-dif)+size/2, (int)(y-dif)+size/2);
			else r = 0;
			g.drawImage(texture.getTexture(), (int)(x-dif), (int)(y-dif), size, size, null);
			g.setColor(Color.BLACK);
			g.drawRect((int)(x-dif), (int)(y-dif), size, size);
			
			g.setTransform(at);
			
			if(showInfo)
			{
				Graphics2D g2 = (Graphics2D) info.getGraphics();
				g2.setColor(new Color(255, 255, 255, 10));
				g2.fillRect(0, 0, 360, 250);
				g2.drawImage(texture.getTexture(), 10, 10, 80, 80, null);
				g2.setColor(Color.BLACK);
				//g2.drawString("CIAO", (int)(Math.random()*360), (int)(Math.random()*250));
				g.drawImage(info, 420, 130, null);
			}
		}
		else
		{
			dif = 0;
			size = 40;
		}
		
	}
	
	public void deselect()
	{
		selected = false;
	}
	
	public void select()
	{
		selected = true;
		
		Player.setTexture(texture);
	}
}
