package GameState;

import java.awt.Color;
import java.awt.Font;

public class PlayMenuState extends MenuState
{	
	
	private static String[] options = {"Levels", "Survival"};
	
	public PlayMenuState(GameStateManager gsm)
	{
		super(gsm, new Font("Arial", Font.BOLD, 40), options);
		optionsCentering (true);
		setY(200);
		titleFont = new Font("Century gothic", Font.BOLD, 80);
		setTitle("Impossible Game", getXforCenterString("Impossible Game", titleFont), 80, titleFont, Color.BLACK);
		setSpacing(50);
		addBackOption(GameStateManager.MAINMENUSTATE);
	}
	
	public void select()
	{
		if(selected == 0)
		{
			gsm.setGameState(GameStateManager.LEVELSLISTSTATE);
		}
		else if(selected == 1)
		{
			gsm.addGameState(GameStateManager.SURVIVALSTATE, new LevelState(gsm, null, GameStateManager.SURVIVALSTATE, true));
			gsm.setGameState(GameStateManager.SURVIVALSTATE);
		}
	}
}
