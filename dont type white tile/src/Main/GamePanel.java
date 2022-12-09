package Main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;


public class GamePanel extends JPanel implements Runnable, MouseListener, KeyListener
{
	
	
	public static int WIDTH = 350;//350
	public static int HEIGHT = 525;//525
	
	private Thread thread;
	private boolean running;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private double FPS = 200;//80
	private double averageFPS;
	
	private Map map;
	private static int points;
	private static boolean gameover1;
	private static boolean gameover2;
	private static boolean newRecord;
	private static File bestscore;
	private static BufferedReader br;
	private static BufferedWriter bw;
	
	public GamePanel()
	{
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
		addMouseListener(this);
		addKeyListener(this);
	}
	
	public void addNotify()
	{
		super.addNotify();
		if(thread == null)
		{
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void run()
	{
		running = true;
		
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		
		map = new Map();
		try 
		{
			bestscore = new File("bestscore.txt");
			br = new BufferedReader(new FileReader(bestscore));
			int record = Integer.parseInt(br.readLine());
			bw = new BufferedWriter(new FileWriter(bestscore));
			bw.write(String.valueOf(record));
			bw.flush();
		} 
		catch (IOException e1)
		{
			e1.printStackTrace();
		}
		
		
		long startTime;
		long URDTimeMillis;
		long waitTime;
		long totalTime = 0;
		
		int frameCount = 0;
		int maxFrameCount = 30;
		
		long targetTime = (long) (1000 / FPS);
		
		while(running)
		{
			
			startTime = System.nanoTime();
			
			gameUpdate();
			gameRender();
			gameDraw();
			
			URDTimeMillis = (System.nanoTime() - startTime) / 1000000;
			waitTime = targetTime - URDTimeMillis;
			
			try
			{
				Thread.sleep(waitTime);
			}
			catch(Exception e){}
			
			totalTime += System.nanoTime() - startTime;
			
			frameCount++;
			if(frameCount == maxFrameCount)
			{
				averageFPS = 1000.0 / ((totalTime / frameCount) / 1000000);
				frameCount = 0;
				totalTime = 0;
			}
		}
	}
	
	public int getAverageFPS(){return (int)averageFPS;}
	public static void GameOver(int n)
	{
		if(n == 1)
		{
			gameover1 = true;
		}
		else if(n == 2)
		{
			gameover2 = true;
		}
		
		try 
		{
			br = new BufferedReader(new FileReader(bestscore));
			int record = Integer.parseInt(br.readLine());
			
			if(points > record)
			{
				newRecord = true;
				bw = new BufferedWriter(new FileWriter(bestscore));
				bw.write(String.valueOf(points));
				bw.flush();
			}
		} 
		catch (NumberFormatException | IOException e) 
		{
			e.printStackTrace();
		}
			
	}
	
	private void gameUpdate()
	{
		if(!gameover1 && !gameover2)
		{
			map.update();
		}
		
	}
	
	private void gameRender()
	{
		g.setColor(new Color(0, 120, 200));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if(gameover1)
		{
			g.setColor(new Color(0, 0, 0));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(new Color(255, 255, 255));
		}
		else if(gameover2)
		{
			g.setColor(new Color(255, 0, 0));
			g.fillRect(0, 0, WIDTH, HEIGHT);
			g.setColor(new Color(0, 0, 0));
		}
		else
		{
			map.draw(g);
			g.setFont(new Font("", Font.BOLD, 20));
			g.setColor(new Color(255, 0, 0));
			g.drawString(String.valueOf(points), (int)((WIDTH/2)-getStringSize(String.valueOf(points), g.getFont(), 'w')/2), 20);
		}
		
		if(gameover1 || gameover2)
		{
			g.setFont(new Font("", Font.BOLD, 25));
			g.drawString("Your score", (int)((WIDTH/2)-getStringSize("Your score", g.getFont(), 'w')/2), 100);
			g.drawString("is :", (int)((WIDTH/2)-getStringSize("is :", g.getFont(), 'w')/2), 125);
			
			int height;
			if(points < 100)
			{
				g.setFont(new Font("", Font.BOLD, 300));
				height = 370;
			}
			else
			{
				g.setFont(new Font("", Font.BOLD, 210));
				height = 340;
			}
			g.drawString(String.valueOf(points), (int)((WIDTH/2)-getStringSize(String.valueOf(points), g.getFont(), 'w')/2), height);
			
			if(newRecord)
			{
				g.setFont(new Font("", Font.BOLD, 30));
				g.drawString("NEW RECORD!", (int)((WIDTH/2)-getStringSize("NEW RECORD!", g.getFont(), 'w')/2), height+40);
			}
			
			g.setFont(new Font("", Font.BOLD, 25));
			g.drawString("Press [R] to restart", (int)((WIDTH/2)-getStringSize("Press [R] to restart", g.getFont(), 'w')/2), 450);
		}
		
	}
	
	private void gameDraw()
	{
		Graphics g2 = this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	public void reset()
	{
		points = 0;
		gameover1 = false;
		gameover2 = false;
		newRecord = false;
		map.reset();
	}
	
	public double getStringSize(String str, Font font, char wh)
	{
		AffineTransform affinetransform = new AffineTransform();     
		FontRenderContext frc = new FontRenderContext(affinetransform,true,true); 
		
		if(wh == 'w')
		{
			return font.getStringBounds(str, frc).getWidth();
		}
		else if(wh == 'h')
		{
			return font.getStringBounds(str, frc).getHeight();
		}
		else
		{
			return 0;
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		final ArrayList<Tile> tiles = map.getTiles();
		
		for(int i = 0; i < tiles.size(); i++)
		{
			Rectangle tile = tiles.get(i).getRectangleTile();
			
			if(tile.contains(e.getPoint()) && map.isRunning() && tiles.get(i).isBlackTile() && !tiles.get(i).isTapped())
			{
				tiles.get(i).setTyped(true);
				points++;
			}
			else if(tile.contains(e.getPoint()) && tiles.get(i).isStartTile() && !tiles.get(i).isTapped())
			{
				tiles.get(i).setTyped(true);
				points++;
				map.start();
			}
			else if(tile.contains(e.getPoint()) && map.isRunning() && !tiles.get(i).isBlackTile())
			{
				tiles.get(i).setTyped(true);
				final int o = i; 
				map.stop();
				Thread thread = new Thread(new Runnable()
				{
					public void run()
					{
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
						
						GameOver(2);
					}
				});
				
				thread.start();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_R)
		{
			if(gameover1 || gameover2)
			{
				reset();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}
}
