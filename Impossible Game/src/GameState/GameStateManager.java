package GameState;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

public class GameStateManager 
{
	private String currentGameState;
	
	private HashMap<String, GameState> gameStates;
	
	public static final String MAINMENUSTATE = "MAINMENUSTATE";
	public static final String PLAYMENUSTATE = "PLAYMENUSTATE";
	public static final String HELPSTATE = "HELPSTATE";
	public static final String LEVELSLISTSTATE = "LEVELSLISTSTATE";
	public static final String CHARACTERSMENUSTATE = "CHARACTERSMENUSTATE";
	public static final String GAMEOVERSTATE = "GAMEOVERSTATE";
	public static final String LEVELCOMPLETEDSTATE = "LEVELCOMPLETEDSTATE";
	public static final String SURVIVALSTATE = "SURVIVALSTATE";
	
	public GameStateManager()
	{
		gameStates = new HashMap<String, GameState>();
		
		gameStates.put(MAINMENUSTATE, new MainMenuState(this));
		gameStates.put(PLAYMENUSTATE, new PlayMenuState(this));
		gameStates.put(HELPSTATE, new SettingsState(this));
		gameStates.put(LEVELSLISTSTATE, new LevelsListState(this));
		gameStates.put(CHARACTERSMENUSTATE, new PlayersMenuState(this));
		gameStates.put(GAMEOVERSTATE, null);
		gameStates.put(LEVELCOMPLETEDSTATE, null);
		//gameStatesIDs.put("SURVIVALSTATE", 4);
		
		currentGameState = MAINMENUSTATE;
	}
	
	public void setGameState(String gameState)
	{
		currentGameState = gameState;
		gameStates.get(currentGameState).init();
	}
	
	public void setGameOverState(String level, boolean survival, int score, boolean newRecord)
	{
		currentGameState = GAMEOVERSTATE;
		gameStates.replace(GAMEOVERSTATE, new GameOverState(this, level, survival, score, newRecord));
		gameStates.get(GAMEOVERSTATE).init();
	}
	
	public void setLevelCompletedState(String nextLevel)
	{
		currentGameState = LEVELCOMPLETEDSTATE;
		gameStates.replace(LEVELCOMPLETEDSTATE, new LevelCompletedState(this, nextLevel));
		gameStates.get(LEVELCOMPLETEDSTATE).init();
	}
	
	public void addGameState(String name, GameState state)
	{
		gameStates.put(name, state);
	}
	
	public GameState getGameState(String name)
	{
		return gameStates.get(name);
	}
	
	public boolean gameStateExist(String gameState)
	{
		return gameStates.containsKey(gameState);
	}
	
	public void update()
	{
		gameStates.get(currentGameState).update();
	}
	
	public void draw(Graphics2D g)
	{
		gameStates.get(currentGameState).draw(g);
	}
	
	public void keyPressed(int k)
	{
		gameStates.get(currentGameState).keyPressed(k);
	}
	
	public void keyReleased(int k)
	{
		gameStates.get(currentGameState).keyReleased(k);
	}
}
