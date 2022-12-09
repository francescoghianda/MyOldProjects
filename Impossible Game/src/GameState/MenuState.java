package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.Sound;

public abstract class MenuState extends GameState
{
	protected String[] options;
	protected int selected;
	
	protected Font font;
	
	protected double x;
	protected double y;
	protected int spacing;
	
	protected boolean optionsCentering;
	protected boolean backOption;
	protected String backGameState;
	
	protected String title;
	protected boolean titlePaint;
	protected double titleY;
	protected double titleX;
	protected Font titleFont;
	protected Color titleColor;
	
	protected int waitTick;
	protected int tick;
	
	protected Sound sound1;
	protected Sound sound2;
	protected Sound sound3;
	
	public MenuState(GameStateManager gsm, Font font, String... options)
	{
		this.options = options;
		this.gsm = gsm;
		this.font = font;
		
		this.x = (GamePanel.WIDTH/2);
		this.y = 100;
		this.spacing = 80;
		
		optionsCentering = true;
		titlePaint = false;
		
		sound1 = new Sound("/Menu/menu1.wav");
		sound2 = new Sound("/Menu/menu2.wav");
		sound3 = new Sound("/Menu/menu3.wav");
	}
	
	public MenuState(GameStateManager gsm, String... options)
	{
		this.options = options;
		this.gsm = gsm;
		this.font = new Font("Arial", Font.PLAIN, 20);
		this.x = (GamePanel.WIDTH/2);
		this.y = 100;
		this.spacing = 80;
		
		optionsCentering = true;
		
		sound1 = new Sound("/Menu/menu1.wav");
		sound2 = new Sound("/Menu/menu2.wav");
		sound3 = new Sound("/Menu/menu3.wav");
	}
	
	public void setTitle(String s, double x, double y, Font font, Color color)
	{
		this.title = s;
		this.titlePaint = true;
		this.titleY = y;
		this.titleFont = font;
		this.titleColor = color;
		this.titleX = x;
	}
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public void setSpacing(int spacing)
	{
		this.spacing = spacing;
	}
	
	public void setFont(Font font)
	{
		this.font = font;
	}
	
	public void addBackOption(String gameState)
	{
		this.backGameState = gameState;
		this.backOption = true;
		String[] old = options;
		options = new String[options.length+1];
		for(int i = 0; i < options.length-1; i++)
		{
			options[i] = old[i];
		}
		options[options.length-1] = "Back";
	}
	
	public void optionsCentering(boolean b)
	{
		this.optionsCentering = b;
	}
	
	public void init()
	{
		selected = 0;
	}
	
	public abstract void select();
	
	public void update()
	{
		waitTick--;
		tick++;
	}
	
	public void setOptions(String... options)
	{
		this.options = options;
	}
	
	protected void paintTitle(Graphics2D g)
	{
		if(titlePaint)
		{
			g.setFont(titleFont);
			g.setColor(titleColor);
			g.drawString(title, (int)titleX, (int)titleY);
		}
	}
	
	protected void paintOption(Graphics2D g, int i)
	{
			g.setColor(Color.BLACK);
			if(i == selected){g.setColor(Color.RED);}
			
			if(optionsCentering)
			{
				g.drawString(options[i], ((GamePanel.WIDTH/2)-(getStringWidth(options[i], g.getFont())/2)), (int)(y+(i*spacing)));
			}
			else
			{
				g.drawString(options[i], (int)x, (int)(y+(i*spacing)));
			}
	}
	
	public void paintBackOption(Graphics2D g)
	{
		g.setColor(Color.BLACK);
		if(options.length-1 == selected)g.setColor(Color.RED);
		g.drawString(options[options.length-1], 50, 350);
	}
	
	public void draw(Graphics2D g)
	{
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
		
		paintTitle(g);
		
		g.setFont(font);
		
		int endfor = options.length;
		if(backOption)endfor--;
		
		for(int i = 0; i < endfor; i++)
		{
			paintOption(g, i);
		}
		
		if(backOption)paintBackOption(g);
		
		
		//g.setFont(new Font("Chiller", Font.BOLD, 80));
		
	}
	
	
	protected int getStringWidth(String str, Font font)
	{
		int stringWidth = 0;
		
		BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		FontMetrics fm = img.getGraphics().getFontMetrics(font);
		stringWidth = fm.stringWidth(str);
		
		return stringWidth;
	}
	
	protected double getXforCenterString(String s, Font font)
	{
		return ((GamePanel.WIDTH/2)-(getStringWidth(s, font)/2));
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
				sound1.playOnce();
			}
			
			if(k == KeyEvent.VK_UP)
			{
				selected--;
				if(selected < 0)
				{
					selected = options.length-1;
				}
				sound1.playOnce();
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

	public void keyReleased(int k) 
	{
		
	}
}
