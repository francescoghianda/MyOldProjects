package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.InputStream;

import main.GamePanel;
import main.Player;
import main.TileMap;

public class LevelState extends GameState
{
	
	private InputStream levelFile;
	private TileMap map;
	private Player player;
	
	private String stateName;
	private String levelName;
	
	private boolean survival;
	
	private InputStream[] mapTiles;
	
	public LevelState(GameStateManager gsm, InputStream levelFile, String stateName, boolean survival)
	{
		this.gsm = gsm;
		this.levelFile = levelFile;
		
		this.stateName = stateName;
		
		if(survival)
		{
			InputStream[] mapTiles = new InputStream[0];
			int i = 0;
			while(true)
			{
				if(getClass().getResourceAsStream("/MapTiles/"+String.valueOf(i)) != null)
				{
					mapTiles = addToArray(mapTiles, getClass().getResourceAsStream("/MapTiles/"+String.valueOf(i)));
				}
				else
				{
					break;
				}
				i++;
			}
			this.map = new TileMap(true, mapTiles);
			this.mapTiles = mapTiles;
		}
		else
		{
			this.map = new TileMap(levelFile);
		}
		
		this.player = new Player(100, 280, map, this);
		
		this.levelName = map.getLevelName();

		this.survival = survival;
	}
	
	private InputStream[] addToArray(InputStream[] array, InputStream obj)
	{
		InputStream[] newArray = new InputStream[array.length+1];
		for(int i = 0; i < array.length; i++)
		{
			newArray[i] = array[i];
		}
		newArray[newArray.length-1] = obj;
		return newArray;
	}
	
	public String getLevelName()
	{
		return this.levelName;
	}
	
	public void init()
	{
		if(survival)
		{
			map = new TileMap(true, mapTiles);
			player.setMap(map);
		}
		else
		{
			map.reset();
		}
		player.show();
	}
	
	public void update()
	{
		map.update();
		player.update();
	}
	
	public void draw(Graphics2D g)
	{
		g.rotate(Math.toRadians(0), 0 + GamePanel.WIDTH/2, 0 +  GamePanel.HEIGHT/2);
			
		map.draw(g);
		player.draw(g);
			
		g.setColor(Color.WHITE);
		g.drawLine(50, 320, 750, 320);
	}
	
	public void win()
	{
		gsm.setLevelCompletedState(String.valueOf(Integer.parseInt(stateName)+1));
	}
	
	public void gameOver()
	{
		gsm.setGameOverState(stateName, survival, map.getScore(), (map.getScore() > TileMap.getScoreRecord()));
		if(map.getScore() > TileMap.getScoreRecord())
		{
			System.out.println(map.getScore()+"    "+TileMap.getScoreRecord());
			TileMap.setScoreRecord(map.getScore());
		}
	}
	
	public int getScore()
	{
		return map.getScore();
	}
	
	public void keyPressed(int k)
	{
		if(k == KeyEvent.VK_SPACE)
		{
			player.jump();
		}
		
		if(k == KeyEvent.VK_LEFT)
		{
			player.left();
		}
		
		if(k == KeyEvent.VK_RIGHT)
		{
			player.right();
		}
	}
	
	public void keyReleased(int k)
	{
		
	}
}
