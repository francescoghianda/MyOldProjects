package main;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;

public class Background extends Thread
{

	private Color topColor;
	private Color bottomColor;
	
	private int colorHeight;
	
	public Background()
	{
		topColor = new Color(77, 77, 255); //new Color(145, 77, 255);
		bottomColor = new Color(151, 255, 255); //new Color(200, 0, 255);
		colorHeight = 400;
	}
	
	public void run()
	{
		int dy = 0;
		while(true)
		{
			if(colorHeight <= 400)dy = 1;
			if(colorHeight >= 800)dy = -1;
			
			colorHeight += dy;
			
			try 
			{
				Thread.sleep(30);
			} 
			catch (InterruptedException e) 
			{
				e.printStackTrace();
			}
			
			
		}
	}
	
	public void draw(Graphics2D g)
	{
		GradientPaint color = new GradientPaint(400, colorHeight, bottomColor, 400, 0, topColor);
		g.setPaint(color);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
	}
}
