package Main;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Random;

import Main.Tile.TileColor;

public class Map 
{
	private ArrayList<Tile> tiles;
	private boolean running;
	
	public Map()
	{
		tiles = new ArrayList<Tile>();
		initMap();
	}
	
	public void initMap()
	{
		double tilex = 0;
		double tiley = -75;
		boolean a = false;
		boolean b = false;
		Random random = new Random();
		
		for(int i = 0; i < 56; i++)
		{
			if(random.nextDouble()*10 > 2)
			{
				tiles.add(new Tile(tilex, tiley, TileColor.WHITE));
			}
			else
			{
				if(!a)
				{
					a = true;
					b = true;
					tiles.add(new Tile(tilex, tiley, TileColor.BLACK));
				}
				else
				{
					tiles.add(new Tile(tilex, tiley, TileColor.WHITE));
				}
				
			}
			
			
			
			if(tilex+50 == 350)
			{
				if(!b)
				{
					tiles.get(i-random.nextInt(7)).changeColor();
				}
				tiley += 75;
				tilex = 0;
				a = false;
				b = false;
			}
			else
			{
				tilex += 50;
			}
			
			
		}
		
		for(int i = 0; i < 7; i++)
		{
			tiles.get(tiles.size()-1-i).changeColor(TileColor.WHITE);
		}
		
		tiles.get(tiles.size()-4).changeColor();
		tiles.get(tiles.size()-4).setStartTile();
		
	}
	
	public void start()
	{
		this.running = true;
	}
	public void stop()
	{
		this.running = false;
	}
	public boolean isRunning()
	{
		return this.running;
	}
	
	public void generateNewLine()
	{
		double tilex = 0;
		double tiley = -75;
		Random random = new Random();
		
		for(int i = 0; i < 7; i++)
		{
			tiles.add(new Tile(tilex, tiley, TileColor.WHITE));
			tilex += 50;
		}
		tiles.get(tiles.size()-1-random.nextInt(7)).changeColor();
	}
	
	public ArrayList<Tile> getTiles()
	{
		return this.tiles;
	}
	
	public void update()
	{
		//System.out.println(tiles.size());
		if(running)
		{
			boolean deletedLine = false;
			
			for(int i = 0; i < tiles.size(); i++)
			{
				tiles.get(i).setY(tiles.get(i).getY()+1);//3 / 2.5
				
				if(tiles.get(i).getY() >= 525)
				{
					if(tiles.get(i).isBlackTile() && !tiles.get(i).isTapped())
					{
						final int o = i;
						
						//System.out.println(tiles.size()+" | "+i);
						//System.out.println(7-(tiles.size()-49));
						int cond = 7-(tiles.size()-49);
						for(int c = 0; c < cond; c++)
						{
							tiles.add(new Tile(c*50, 525, TileColor.WHITE));
						}
						
						Thread thread = new Thread(new Runnable()
						{
							public void run()
							{
								boolean done = true;
								while(done)
								{
									for(int i = 0; i < tiles.size(); i++)
									{
										tiles.get(i).setY(tiles.get(i).getY()-1);
									}
									if(tiles.get(o).getY()+75 == GamePanel.HEIGHT){done = false;}
									try
									{
										Thread.sleep(7);
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
								}
								
								for(int y = 0; y < 6; y++)
								{
									if(tiles.get(o).isTapped())
									{
										tiles.get(o).setTyped(false);
									}
									else
									{
										tiles.get(o).setTyped(true);
									}
									
									try
									{
										Thread.sleep(200);
									}
									catch(Exception e)
									{
										e.printStackTrace();
									}
								}
								GamePanel.GameOver(1);
							}
						});
						stop();
						thread.start();
						break;
					}
					else
					{
						deletedLine = true;
						tiles.remove(i);
					}
					
					i--;
				}
			}
			
			if(deletedLine)
			{
				generateNewLine();
			}
		}
	}
	
	public void draw(Graphics2D g)
	{
		for(int i = 0; i < tiles.size(); i++)
		{
			tiles.get(i).draw(g);
		}
	}
	
	public void reset()
	{
		tiles.clear();
		initMap();
	}
}
