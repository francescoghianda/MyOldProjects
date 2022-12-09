package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import main.Texture;
import main.Character;

public class PlayersMenuState extends MenuState
{
	
	private ArrayList<Character> characters;
	
	private boolean backSelected;
	private int characterSelected;
	
	private int characterChoosed;
	
	public PlayersMenuState(GameStateManager gsm)
	{
		super(gsm);
		
		this.titleFont = new Font("Arial", Font.BOLD, 100);
		setFont(new Font("Arial", Font.PLAIN, 40));
		setSpacing(50);
		setTitle("Select character", this.getXforCenterString("Select character", titleFont), 100, titleFont, Color.BLACK);
		
		characters = new ArrayList<Character>();
		loadCharacters();
	}
	
	public void init()
	{
		selected = characterSelected;
		backSelected = false;
	}
	
	private void loadCharacters()
	{
		InputStream is;
		Properties p = new Properties();
		for(int i = 0; (is = getClass().getResourceAsStream("/Characters/characters/"+String.valueOf(i))) != null; i++)
		{
			try 
			{
				p.load(is);
				characters.add(new Character(new Texture("/Characters/Textures/"+p.getProperty("TEXTURE"), 40, 40), 120+i*60, 200));
				options = addToArray(options, String.valueOf(i));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
		}
		addBackOption(GameStateManager.HELPSTATE);
	}
	
	public void paintOption(Graphics2D g, int i)
	{
		characters.get(i).draw(g);
	}
	
	public void select()
	{
		characters.get(characterChoosed).deselect();
		characterChoosed = selected;
		characters.get(characterChoosed).select();
	}
	
	public void keyPressed(int k) 
	{
		if(waitTick <= 0)
		{
			if(k == KeyEvent.VK_RIGHT && !backSelected)
			{
				selected++;
				characterSelected++;
				if(selected > options.length-2)
				{
					selected = options.length-2;
					characterSelected = options.length-2;;
				}
				else
				{
					for(int i = 0; i < characters.size(); i++)
					{
						characters.get(i).setX(characters.get(i).getX()-60);
					}
					sound3.playOnce();
				}
				for(int i = 0; i < characters.size(); i++)
				{
					characters.get(i).showInfo(false);
				}
				characters.get(selected).showInfo(true);
			}
			
			if(k == KeyEvent.VK_LEFT && !backSelected)
			{
				selected--;
				characterSelected--;
				if(selected < 0)
				{
					selected = 0;
					characterSelected = 0;
				}
				else
				{
					for(int i = 0; i < characters.size(); i++)
					{
						characters.get(i).setX(characters.get(i).getX()+60);
					}
					sound3.playOnce();
				}
				for(int i = 0; i < characters.size(); i++)
				{
					characters.get(i).showInfo(false);
				}
				characters.get(selected).showInfo(true);
			}
			
			if(k == KeyEvent.VK_DOWN)
			{
				selected = options.length-1;
				backSelected = true;
			}
			
			if(k == KeyEvent.VK_UP)
			{
				selected = characterSelected;
				backSelected = false;
			}
			
			waitTick = 10;
		}
		
		if(k == KeyEvent.VK_ENTER)
		{
			if(backOption && selected == options.length-1)
			{
				gsm.setGameState(backGameState);
			}
			else
			{
				select();
			}
			
			sound2.playOnce();
			
		}
		
	}
	
	private String[] addToArray(String[] array, String str)
	{
		String[] newArray = new String[array.length+1];
		for(int i = 0; i < array.length; i++)
		{
			newArray[i] = array[i];
		}
		newArray[newArray.length-1] = str;
		return newArray;
	}
}
