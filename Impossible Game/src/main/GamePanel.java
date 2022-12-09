package main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JPanel;

import GameState.GameStateManager;
import GameState.LevelsListState;
import GameState.MenuState;
import SASS.SASS;


public class GamePanel extends JPanel implements Runnable, KeyListener
{
	public static int WIDTH = 800;
	public static int HEIGHT = 400;
	
	private Thread thread;
	private boolean running;
	
	private BufferedImage image;
	private Graphics2D g;
	
	private double FPS = 70;//80
	private double averageFPS;
	
	private Background bg;
	
	//private static boolean playing;
	//private MenuState menu;
	//private static boolean gameOver = false;
	//public static int times;
	
	/*private int colorHeight = 400;
	private boolean colorUp = true;
	private boolean colorDown;
	private Color topColor;
	private Color bottomColor;*/
	
	private GameStateManager gsm;
	
	public GamePanel()
	{
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();
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
		
		gsm = new GameStateManager();
		
		bg = new Background();
		bg.start();
		
		loadSave();
		
		long startTime;
		long URDTimeMillis;
		long waitTime;
		long totalTime = 0;
		
		int frameCount = 0;
		int maxFrameCount = 30;
		
		long targetTime = (long) (1000 / FPS);
		
		int tick = 0;
		
		
		while(running)
		{
			
			startTime = System.nanoTime();
			
			if(tick % 1 == 0)
			{
				gameUpdate();
			}
			
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
			
			tick++;
		}
	}
	
	private void gameUpdate()
	{
		gsm.update();
	}
	
	private void gameRender()
	{	
		AffineTransform at = g.getTransform();
		
		bg.draw(g);
		
		gsm.draw(g);
		
		g.setTransform(at);
		
		g.setFont(new Font("", Font.ITALIC, 10));
		g.setColor(Color.BLACK);
		g.drawString("FPS :" + (int)averageFPS, 0, 10);
	}
	
	private void gameDraw()
	{
		Graphics g2 = (Graphics2D) this.getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}
	
	public void loadSave()
	{
		try
		{
			File saveFile = new File("save");
			BufferedReader br = new BufferedReader(new FileReader(saveFile));
			String fileStr = br.readLine();
			br.close();
			SASS sass = new SASS();
			fileStr = sass.Decrypt(fileStr);
			fileStr = sass.Decrypt(fileStr);
			fileStr = fileStr.replaceAll("%n", "\n");
			//System.out.println(fileStr);
			File tmpLoadSave = File.createTempFile("tmpLoadSave", null);
			tmpLoadSave.deleteOnExit();
			BufferedWriter bw = new BufferedWriter(new FileWriter(tmpLoadSave));
			bw.write(fileStr);
			bw.flush();
			bw.close();
			Properties prop = new Properties();
			prop.load(new FileReader(tmpLoadSave));
			
			//GET PROP
			
			TileMap.setScoreRecord(Integer.parseInt(prop.getProperty("survivalRecord")));
			LevelsListState.setUnlockedLevels(prop.getProperty("unlockedLevels"));
			
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void saveGame()
	{
		try 
		{
			File tmpSave = File.createTempFile("tmpSave", null);
			tmpSave.deleteOnExit();
			//System.out.println(tmpSave.getPath());
			Properties prop = new Properties();
			prop.load(new FileReader(tmpSave));
			prop.put("unlockedLevels", LevelsListState.getUnlockedLevels());
			prop.put("unlockedLevels", "0"); //unlock tutorial
			prop.put("unlockedPlayers", "");
			prop.put("survivalRecord", String.valueOf(TileMap.getScoreRecord()));
			
			prop.store(new FileWriter(tmpSave), null);
			
			BufferedReader br = new BufferedReader(new FileReader(tmpSave));
			String line;
			String fileStr = "";
			while((line = br.readLine()) != null)
			{
				fileStr += line+"%n";
			}
			br.close();
			//System.out.println(fileStr);
			SASS sass = new SASS();
			fileStr = sass.Crypt(fileStr);
			fileStr = sass.Crypt(fileStr);
			//System.out.println(fileStr);
			File saveFile = new File("save");
			if(saveFile.exists())saveFile.delete();
			if(saveFile.createNewFile())
			{
				BufferedWriter bw = new BufferedWriter(new FileWriter(saveFile));
				bw.write(fileStr);
				bw.flush();
				bw.close();
			}
			else
			{
				System.err.println("Errore!");
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		gsm.keyPressed(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) 
	{
		gsm.keyReleased(e.getKeyCode());
	}

	@Override
	public void keyTyped(KeyEvent e) 
	{
		
	}
}
