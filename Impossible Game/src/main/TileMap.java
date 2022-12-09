package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class TileMap 
{
	private InputStream map;
	private ArrayList<Block> old_blocks;
	private boolean running;
	private boolean hide;
	private boolean hiding;
	private int colorAlfa;
	private int score;
	private int dscore;
	private static int scoreRecord;
	
	private int rows;
	private int col;
	private int tileSize;
	
	private String levelName;
	
	private double x;
	
	private int mapWidth;
	private boolean survival;
	private InputStream[] mapTiles;
	
	private int ticks;
	
	private ArrayList<Integer> usedTiles;
	
	public TileMap(InputStream levelFile)
	{
		map = levelFile;
		
		old_blocks = new ArrayList<Block>();
		
		try 
		{
			BufferedReader br = new BufferedReader(new InputStreamReader(map));
			
			this.levelName = br.readLine();
			
			this.col = Integer.parseInt(br.readLine());
			this.rows = Integer.parseInt(br.readLine());
			this.tileSize = 40;
			
			br.readLine();
			
			for(int r = 0; r < rows; r++)
			{
				String[] line = br.readLine().split(" ");
				
				for(int c = 0; c < col; c++)
				{
					old_blocks.add(new Block(c*tileSize, r*tileSize, Integer.parseInt(line[c]), this));
				}
			}
			this.mapWidth += this.col*this.tileSize;
			br.close();
			
			running = true;
			hide = false;
			hiding = false;
			colorAlfa = 255;
			
			dscore = 1;
			
			usedTiles = new ArrayList<Integer>();
		} 
		catch (NumberFormatException | IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public TileMap(boolean survival, InputStream... mapTiles)
	{
		if(survival)
		{
			usedTiles = new ArrayList<Integer>();
			
			this.survival = survival;
			this.mapTiles = mapTiles;
			old_blocks = new ArrayList<Block>();
			
			try 
			{
				this.levelName = "SURVIVAL";
				
				this.col = 20;
				this.rows = 10;
				this.tileSize = 40;
				
				for(int r = 0; r < rows; r++)
				{
					for(int c = 0; c < col; c++)
					{
						old_blocks.add(new Block(c*tileSize+mapWidth-x, r*tileSize, 0, this));
					}
				}
				this.mapWidth += this.col*this.tileSize;
				
				addNewTile();
				addNewTile();
				
				running = true;
				hide = false;
				hiding = false;
				colorAlfa = 255;
				
				dscore = 1;
			} 
			catch (NumberFormatException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void removeBlock(Block block)
	{
		this.old_blocks.remove(block);
	}
	
	public void addNewTile()
	{
		//System.out.println("ADD NEW TILE");
		try 
		{
			if(usedTiles.size() == 2)usedTiles.remove(0);
			int rand = new Random().nextInt(mapTiles.length);
			if(!usedTiles.contains(rand))
			{
				System.out.println(rand);
				usedTiles.add(rand);
				InputStream newTile = mapTiles[rand];
				newTile.mark(Integer.MAX_VALUE);
				BufferedReader br = new BufferedReader(new InputStreamReader(newTile));
				this.levelName = br.readLine();
				
				this.col = Integer.parseInt(br.readLine());
				this.rows = Integer.parseInt(br.readLine());
				this.tileSize = 40;
				
				br.readLine();
				
				for(int r = 0; r < rows; r++)
				{
					String[] line = br.readLine().split(" ");
					
					for(int c = 0; c < col; c++)
					{
						old_blocks.add(new Block((c*tileSize)+mapWidth-x, r*tileSize, Integer.parseInt(line[c]), this));
					}
				}
				
				this.mapWidth += this.col*this.tileSize;
				
				newTile.reset();
			}
			else
			{
				addNewTile();
			}
			
		} 
		catch (NumberFormatException | IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public String getLevelName()
	{
		return this.levelName;
	}
	
	public int calculateColumns(double x)
	{
		return (int)(x/tileSize);
	}
	
	public int calculateRows(double y)
	{
		return (int)(y/tileSize);
	}
	
	public ArrayList<Block> getBlocks()
	{
		return this.old_blocks;
	}
	
	public void stop()
	{
		running = false;
	}
	public void start()
	{
		running = true;
	}
	public void show()
	{
		hide = false;
		hiding = false;
		colorAlfa = 255;
		for(int i = 0; i < old_blocks.size(); i++)
		{
			old_blocks.get(i).setColorAlfa(colorAlfa);
		}
	}
	public void hide()
	{
		hiding = true;
	}
	public void reset()
	{
		for(int i = 0; i < old_blocks.size(); i++)
		{
				old_blocks.get(i).reset();
		}
		score = 0;
		x = 0;
		
		show();
		start();
	}
	
	public double getX()
	{
		return this.x;
	}
	
	public void move(double dx)
	{
		this.x += dx;
		for(int i = 0; i < old_blocks.size(); i++)
		{
			old_blocks.get(i).setX(old_blocks.get(i).getX()-dx);
		}
		
		if(dx > 0)dscore = 1;
		if(dx == 0)dscore = 0;
		if(dx < 0)dscore = -1;
	}
	
	public int getScore()
	{
		return this.score;
	}
	
	public static int getScoreRecord()
	{
		return scoreRecord;
	}
	
	public static void setScoreRecord(int n)
	{
		scoreRecord = n;
	}
	
	public void update()
	{
		//x += 8;
		if(running)
		{
			for(int i = 0; i < old_blocks.size(); i++)
			{
				old_blocks.get(i).update();
			}
			if(survival && ticks%10 == 0)
			{
				score += dscore;
			}
			
		}
		
		if(hiding)
		{
			
			colorAlfa -= 6;
			
			if(colorAlfa < 0)
			{
				colorAlfa = 0;
			}
			
			for(int i = 0; i < old_blocks.size(); i++)
			{
				old_blocks.get(i).setColorAlfa(colorAlfa);
			}
			
			if(colorAlfa == 0)
			{
				colorAlfa = 255;
				hiding = false;
				hide = true;
			}
		}
	
		ticks++;
		
	}
	
	public void draw(Graphics2D g)
	{
		if(!hide)
		{
			for(int i = 0; i < old_blocks.size(); i++)
			{
				old_blocks.get(i).draw(g);
			}
			
			if(survival)
			{
				g.setFont(new Font("", Font.BOLD, 40));
				g.setColor(Color.CYAN);
				g.drawString(String.valueOf(score), 50, 40);
			}
			
		}
	}
}
