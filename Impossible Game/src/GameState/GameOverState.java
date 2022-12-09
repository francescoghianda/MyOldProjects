package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.Random;

import main.GamePanel;

public class GameOverState extends MenuState
{
	
	private String level;
	private boolean survival;
	private int score;
	private boolean newRecord;
	
	private int[] strX;
	private int[] strY;
	private int[] strFontSize;
	
	public GameOverState(GameStateManager gsm, String level)
	{
		super(gsm, "Retry", "Back to levels list");
		setY(250);
		setSpacing(60);
		setFont(new Font("Chiller", Font.PLAIN, 50));
		this.titleFont = new Font("Chiller", Font.PLAIN, 200);
		setTitle("Game Over", this.getXforCenterString("Game Over", titleFont), 140, titleFont, new Color(255, 153, 0));
		
		this.level = level;
		this.survival = false;
	}
	
	public GameOverState(GameStateManager gsm, String level, boolean survival, int score, boolean newRecord)
	{
		super(gsm, "Retry", "Back to levels list");
		if(survival)
		{
			setY(260);
		}
		else
		{
			setY(250);
		}
		
		setSpacing(60);
		setFont(new Font("Chiller", Font.PLAIN, 50));
		this.titleFont = new Font("Chiller", Font.PLAIN, 200);
		setTitle("Game Over", this.getXforCenterString("Game Over", titleFont), 140, titleFont, new Color(255, 153, 0));
		
		this.level = level;
		this.survival = survival;
		this.score = score;
		this.newRecord = newRecord;
		
		if(newRecord)
		{
			int NSTR = 50;
			strX = new int[NSTR];
			strY = new int[NSTR];
			strFontSize = new int[NSTR];
			
			Random r = new Random();
			
			for(int i = 0; i < strX.length; i++)
			{
				strX[i] = r.nextInt(GamePanel.WIDTH);
				strY[i] = r.nextInt(GamePanel.HEIGHT);
				strFontSize[i] = r.nextInt(20);
			}
		}
	}
	
	protected void paintTitle(Graphics2D g)
	{
		if(survival)
		{
			if(newRecord)
			{
				g.setColor(new Color(80, 80, 80));
				for(int i = 0; i < strX.length; i++)
				{
					g.setFont(new Font("Arial", Font.PLAIN, strFontSize[i]));
					g.drawString("NEW RECORD!", strX[i], strY[i]);
				}
			}
			g.setFont(titleFont);
			g.setColor(titleColor);
			g.drawString(title, (int)titleX, (int)titleY);
			g.setFont(font);
			g.setColor(Color.BLACK);
			g.drawString("Your score is: "+score, (int)getXforCenterString("Your score is: "+score, font), (int)titleY+50);
		}
		else
		{
			g.setFont(titleFont);
			g.setColor(titleColor);
			g.drawString(title, (int)titleX, (int)titleY);
		}
	}
	
	public void update()
	{
		waitTick--;
		tick++;
		
		if(tick%10 == 0 && newRecord)
		{
			Random r = new Random();
			
			for(int i = 0; i < strX.length; i++)
			{
				strX[i] = r.nextInt(GamePanel.WIDTH);
				strY[i] = r.nextInt(GamePanel.HEIGHT);
				strFontSize[i] = r.nextInt(20);
			}
		}
	}
	
	public void select()
	{
		if(selected == 0)
		{
			gsm.setGameState(level);
		}
		else if(selected == 1)
		{
			gsm.setGameState(GameStateManager.LEVELSLISTSTATE);
		}
	}
	
}
