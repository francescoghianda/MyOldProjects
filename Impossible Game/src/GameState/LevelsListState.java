package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.InputStream;
import java.util.ArrayList;

import main.GamePanel;
import main.Texture;

public class LevelsListState extends MenuState
{
	private int Nlevels;
	private static int[] unlockedLevels;
	
	private Texture locked;
	
	private Texture right;
	private Texture left;
	
	private ArrayList<String[]> list;
	private int listIndex;
	
	public LevelsListState(GameStateManager gsm)
	{
		super(gsm, new Font("Arial", Font.BOLD, 40));
		optionsCentering (true);
		setY(150);
		titleFont = new Font("Century gothic", Font.BOLD, 80);
		setTitle("Impossible Game", getXforCenterString("Impossible Game", titleFont), 80, titleFont, Color.BLACK);
		setSpacing(50);
		
		list = new ArrayList<String[]>();
		
		loadLevels();
		
		options = list.get(0);
		
		backOption = true;
		backGameState = GameStateManager.PLAYMENUSTATE;
		
		locked = new Texture("/Menu/locked.png", 16, 16);
		right = new Texture("/Menu/right.png", 256, 256);
		left = new Texture("/Menu/left.png", 256, 256);
	}
	
	public void init()
	{
		selected = 0;
	}
	
	private void loadLevels()
	{
		InputStream level;
		while(true)
		{
			level = getClass().getResourceAsStream("/Levels/"+String.valueOf(Nlevels));
			if(level != null)
			{
				gsm.addGameState(String.valueOf(Nlevels), new LevelState(gsm, level, String.valueOf(Nlevels), false));
			}
			else
			{
				break;
			}
			Nlevels++;
		}
		
		unlockedLevels = new int[Nlevels];
		
		for(int i = 0; i < unlockedLevels.length; i++)
		{
			int index;
			if(Nlevels-i >= 4)
			{
				index = addEmptyList(5);
			}
			else
			{
				index = addEmptyList(Nlevels-i+1);
			}
			
			
			for(int i2 = 0; i2 < list.get(index).length-1; i2++)
			{
				LevelState ls = (LevelState) gsm.getGameState(String.valueOf(i));
				list.get(index)[i2] = ls.getLevelName();
				unlockedLevels[i] = -1;
				if(i2 != 3)i++;
			}
			list.get(index)[list.get(index).length-1] = "Back";
			
		}
	}
	
	private int addEmptyList(int l)
	{
		list.add(new String[l]);
		return list.size()-1;
	}
	
	protected void paintOption(Graphics2D g, int i)
	{
			int realLevel;
			if(listIndex == 0)realLevel = i;
			else realLevel = ((listIndex)*4)+i;
			
			g.setColor(Color.BLACK);
			if(i == selected){g.setColor(Color.RED);}
			
			if(optionsCentering)
			{
				if(!isUnlockedLevel(realLevel))
				{
					g.setColor(new Color(80, 80, 80));
					if(i == selected){g.setColor(new Color(186, 98, 98));}
					g.drawImage(locked.getTexture(), ((GamePanel.WIDTH/2)-(getStringWidth(options[i], g.getFont())/2))-25, (int)(y+(i*spacing))-20, null);
				}
				g.drawString(options[i], ((GamePanel.WIDTH/2)-(getStringWidth(options[i], g.getFont())/2)), (int)(y+(i*spacing)));
			}
		
		g.drawImage(right.getTexture(), 650, 200, 50, 50, null);
		g.drawImage(left.getTexture(), 100, 200, 50, 50, null);
	}

	public void select()
	{
		int selectedLevel;
		if(listIndex == 0)selectedLevel = selected;
		else selectedLevel = ((listIndex)*4)+selected;
		if(isUnlockedLevel(selectedLevel))
		{
			gsm.setGameState(String.valueOf(selectedLevel));
		}
	}
	
	public static String getUnlockedLevels()
	{
		String s = "";
		for(int i = 0; i < unlockedLevels.length; i++)
		{
			s += ","+unlockedLevels[i];
		}
		if(s.length() > 0)
		{
			s = s.substring(1);
		}
		return s;
	}
	
	public static void setUnlockedLevels(String s)
	{
		String[] splitted = s.split(",");
		for(int i = 0; i < unlockedLevels.length; i++)
		{
			unlockedLevels[i] = Integer.parseInt(splitted[i]);
		}
	}
	
	private boolean isUnlockedLevel(int level)
	{
		boolean b = false;
		for(int i = 0; i < unlockedLevels.length; i++)
		{
			if(unlockedLevels[i] == level)
			{
				b = true;
				break;
			}
		}
		return b;
	}
	
	
	public void keyPressed(int k) 
	{
		
		if(waitTick <= 0)
		{
			if(k == KeyEvent.VK_DOWN)
			{
				selected++;
				if(selected > options.length-1)
				{
					selected = 0;
				}
			}
			
			if(k == KeyEvent.VK_UP)
			{
				selected--;
				if(selected < 0)
				{
					selected = options.length-1;
				}
			}
			
			if(k == KeyEvent.VK_RIGHT)
			{
				listIndex++;
				if(listIndex > list.size()-1)
				{
					listIndex = 0;
				}
				
				options = list.get(listIndex);
			}
			
			if(k == KeyEvent.VK_LEFT)
			{
				listIndex--;
				if(listIndex < 0)
				{
					listIndex = list.size()-1;
				}
				
				options = list.get(listIndex);
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
			
		}
		
	}
	
	
	
	
	public Class<?>[] addToArray(Class<?>[] array, Class<?> obj)
	{
		Class<?>[] newArray = new Class<?>[array.length+1];
		for(int i = 0; i < array.length; i++)
		{
			newArray[i] = array[i];
		}
		newArray[newArray.length-1] = obj;
		return newArray;
	}
}
