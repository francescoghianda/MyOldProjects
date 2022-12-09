package GameState;

import java.awt.Color;
import java.awt.Font;

public class LevelCompletedState extends MenuState
{
	
	private String nextLevel;
	private boolean nextLevelExist;
	
	public LevelCompletedState(GameStateManager gsm, String nextLevel)
	{
		super(gsm);
		
		if(gsm.gameStateExist(nextLevel))
		{
			setOptions("Next level", "Back to level list");
			nextLevelExist = true;
		}
		else
		{
			setOptions("Back to level list");
			nextLevelExist = false;
		}
			
		setY(250);
		setSpacing(60);
		setFont(new Font("Chiller", Font.PLAIN, 50));
		setTitle("Level Completed", getXforCenterString("Level Completed", new Font("Chiller", Font.PLAIN, 150)), 130, new Font("Chiller", Font.PLAIN, 150), new Color(255, 153, 0));
	
		this.nextLevel = nextLevel;
	}
	
	public void select() 
	{
		if(selected == 0)
		{
			if(nextLevelExist)
			{
				gsm.setLevelCompletedState(nextLevel);
			}
			else
			{
				gsm.setGameState(GameStateManager.LEVELSLISTSTATE);
			}
		}
		else if(selected == 1)
		{
			gsm.setGameState(GameStateManager.LEVELSLISTSTATE);
		}
	}
}
