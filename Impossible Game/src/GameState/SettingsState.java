package GameState;

import java.awt.Color;
import java.awt.Font;

import main.Sound;


public class SettingsState extends MenuState
{
	public SettingsState(GameStateManager gsm)
	{
		super(gsm, "Sounds [ON]", "Players");
		addBackOption(GameStateManager.MAINMENUSTATE);
		setSpacing(50);
		setY(200);
		setTitle("Settings", this.getXforCenterString("Settings", new Font("Arial", Font.BOLD, 100)), 100, new Font("Arial", Font.BOLD, 100), Color.BLACK);
		setFont(new Font("Arial", Font.PLAIN, 40));
	}

	@Override
	public void select() 
	{
		if(selected == 0)
		{
			Sound.enableSounds(!Sound.isEnabled());
			if(Sound.isEnabled())options[0] = "Sounds [ON]";
			else options[0] = "Sounds [OFF]";
		}
		else if(selected == 1)
		{
			gsm.setGameState(GameStateManager.CHARACTERSMENUSTATE);
		}
		
	}
}
