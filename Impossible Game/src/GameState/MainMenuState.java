package GameState;

import java.awt.Color;
import java.awt.Font;

import main.GamePanel;

public class MainMenuState extends MenuState
{
	private Font titleFont;
	private Font font;
	
	public MainMenuState(GameStateManager gsm)
	{
		super(gsm, "Play","Settings","Quit");
		
		font = new Font("Chiller", Font.BOLD, 70);
		titleFont = new Font("Century gothic", Font.BOLD, 80);
		optionsCentering = true;
		setY(200);
		setFont(font);
		setTitle("Impossible Game", getXforCenterString("Impossible Game", titleFont), 80, titleFont, Color.BLACK);
	}
	
	public void select()
	{
		if(selected == 0)
		{
			gsm.setGameState(GameStateManager.PLAYMENUSTATE);
		}
		else if(selected == 1)
		{
			gsm.setGameState(GameStateManager.HELPSTATE);
		}
		else if(selected == 2)
		{
			GamePanel.saveGame();
			System.exit(0);
		}
	}
	
	
	
}
