package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.TexturePaint;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import GameState.LevelState;

public class Player 
{
	private double x;
	private double y;
	private int width;
	private int height;
	
	private static double jumpingSpeed;
	private static double js; //jumping speed
	private double fs; //falling speed
	private static double speed;
	private boolean jumping;
	private boolean falling;
	
	private int radiants;
	
	private TileMap map;
	private LevelState level;
	
	private boolean hide;
	private boolean hiding;
	private int colorAlfa;
	
	private static Texture texture;
	
	private int tick;
	
	private boolean right;
	private boolean left;
	
	public Player(double x, double y, TileMap map, LevelState level)
	{
		this.x = x;
		this.y = y;
		this.width = 40;
		this.height = 40;
		js = 10;//12
		jumpingSpeed = 10;
		this.fs = 0;
		speed = 8;
		this.map = map;
		this.colorAlfa = 255;

		texture = new Texture("/Player/player texture.png", 40, 40);

		this.level = level;
		
		this.right = true;
	}
	
	public void setMap(TileMap map)
	{
		this.map = map;
	}
	
	public static void setTexture(Texture t)
	{
		texture = t;
	}
	
	public static void setSpeed(double s)
	{
		speed = s;
	}
	
	public static void setJumpHeight(double jh)
	{
		jumpingSpeed = jh;
		js = jh;
	}
	
	public void jump()
	{
		if(!falling)
		{
			this.jumping = true;
		}
	}
	
	public void update()
	{
		ArrayList<Block> obstacles = map.getBlocks();
		
		if(jumping)
		{
			if(js > 0)
			{
				y -= js;
				js --;
			}
			else
			{
				js = jumpingSpeed;;
				jumping = false;
				falling = true;
			}
			
			//System.out.println(y);
		}
		
		
		if(y + height < 320 && !jumping)
		{
			for(int i = 0; i < obstacles.size(); i++)
			{
				if(y+height != obstacles.get(i).getY())
				{
					falling = true;
				}
			}
		}
		
		if(falling)
		{
			double tempy = y;
			double y2 = y;
			
			tempy += fs;
			if(fs < 15)
			{
				//fs++;
				fs+=2;
			}
			
			if(tempy + height > 320)
			{
				fs = 0;
				falling = false;
				y2 = 320-height;
			}
			else
			{
				y2 = tempy;
			}
		
			
			for(int i = 0; i < obstacles.size(); i++)
			{
				Block obstacle = obstacles.get(i);
				
				if(obstacle.isSquare())
				{
					if(x <= obstacle.getX()+obstacle.getWidth()-obstacle.getSpeed() && x+width >= obstacle.getX()-obstacle.getSpeed())
					{
						if(tempy <= obstacle.getY()+obstacle.getHeight() && tempy+height >= obstacle.getY())
						{
							if(y+height <= obstacle.getY()+2)
							{
								y2 = obstacle.getY()-height;
								falling = false;
								fs = 0;
							}
							else
							{
								kill();
							}
						}
					}
				}
				else if(obstacle.getTileID() == 4)
				{
					Rectangle player = new Rectangle((int)x, (int)y, width, height);
					Rectangle block = new Rectangle((int)obstacle.getX(), (int)obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
					
					if(player.intersects(block))
					{
						map.removeBlock(obstacle);
						map.addNewTile();
					}
				}
			}
			
			y = y2;
			
		}
		
		if(!falling)
		{
			for(int i = 0; i < obstacles.size(); i++)
			{
				Block obstacle = obstacles.get(i);
				
				if(obstacle.isSquare())
				{
					if(x <= obstacle.getX()+obstacle.getWidth()-obstacle.getSpeed() && x+width >= obstacle.getX()-obstacle.getSpeed())
					{
						if(y <= obstacle.getY()+obstacle.getHeight() && y+height >= obstacle.getY())
						{
							if(!(y+height <= obstacle.getY()+2))
							{
								kill();
							}
						}
					}
				}
				else if(obstacle.isTriangle())
				{
					Polygon triangle = new Polygon(obstacle.getPoints("x"), obstacle.getPoints("y"), 3);
					Rectangle player = new Rectangle((int)x, (int)y, width, height);
					
					if(triangle.intersects(player))
					{
						kill();
					}
					
				}
				else if(obstacle.isWinBlock())
				{
					Rectangle player = new Rectangle((int)x, (int)y, width, height);
					Rectangle winblock = new Rectangle((int)obstacle.getX(), (int)obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
					
					if(player.intersects(winblock))
					{
						win();
					}
				}
				else if(obstacle.getTileID() == 4)
				{
					Rectangle player = new Rectangle((int)x, (int)y, width, height);
					Rectangle block = new Rectangle((int)obstacle.getX(), (int)obstacle.getY(), obstacle.getWidth(), obstacle.getHeight());
					
					if(player.intersects(block))
					{
						map.addNewTile();
					}
				}
			}
		}
		
		map.move(speed);
		if(false)
		{
			if(right)map.move(speed);
			if(left)map.move(-speed);
		}
		
		
		if(hiding)
		{
			
			colorAlfa -= 6;
			
			if(colorAlfa <= 0)
			{
				colorAlfa = 255;
				hiding = false;
				hide = true;
			}
			
		}
	}
	
	public void draw(Graphics2D g)
	{
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		AffineTransform at = g.getTransform();
		
		if(!hide)
		{
			
			if((jumping || falling) && tick%5 == 0)
			{
				radiants += 45;
			}
			if(!jumping && !falling)
			{
				if(radiants%90 != 0)radiants+=45;
			}
			g.rotate(Math.toRadians(radiants), x + width/2, y +  height/2);
			g.setPaint(new TexturePaint(texture.getTexture(), new Rectangle((int)x, (int)y, width, height)));
			//g.setColor(new Color(255, 165, 79, colorAlfa));
			g.fillRect((int)x, (int)y, width, height);
			g.setColor(new Color(0, 0, 0, colorAlfa));
			g.drawRect((int)x, (int)y, width, height);
		}
		
		g.setTransform(at);
		
		tick++;
		
		if(tick > 1000)tick = 0;
	}
	
	public void show()
	{
		hide = false;
		hiding = false;
		colorAlfa = 255;
	}
	
	public void kill()
	{
		map.stop();
		map.hide();
		hiding = true;
		level.gameOver();
	}
	
	public void win()
	{
		//System.out.println("You WIN!!");
		//map.stop();
		//map.hide();
		//hiding = true;
		level.win();
	}
	
	public void left()
	{
		left = true;
		right = false;
	}
	
	public void right()
	{
		right = true;
		left = false;
	}
	
}
