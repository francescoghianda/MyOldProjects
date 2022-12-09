import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class Maze extends JComponent implements MouseMotionListener , MouseListener
{
	private static final long serialVersionUID = -422047537317621421L;

	static Maze maze = new Maze();
	
	static JFrame window;
	

	BufferedImage intro;
	BufferedImage gameover;
	BufferedImage Vis; // livello da visualizzare
	BufferedImage gameover2; //se si cerca di bypassare cliccando con il mouse
	
	BufferedImage livello1;
	BufferedImage livello2;
	BufferedImage livello3;
	
	static Suono musica;
	
	public Maze()
	{
		
		
		try 
		{

			//getClass().getResourceAsStream(path)
			
			intro = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Intro.png")));
			gameover = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Game over.png")));
			gameover2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Game over 2.png")));
			
			livello1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Livello1.png")));
			livello2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Livello2.png")));
			livello3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("Livello3.png")));
			
			
			Vis = intro;
			
		} 
		catch (IOException e) 
		{
			
		}
	}

	
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException 
	{
		window = new JFrame("Maze");
		window.setResizable(false);
        window.add(maze);
        window.pack();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        
        window.addMouseMotionListener(maze);
        window.addMouseListener(maze);
        
        
		musica = new Suono(new BufferedInputStream(Objects.requireNonNull(Maze.class.getClassLoader().getResourceAsStream("musica.wav"))));
		musica.play();
		
	}
	
	public Dimension getPreferredSize() 
	{
        return new Dimension(800, 600);
    }
	

	@Override
	protected void paintComponent(Graphics g) 
	{
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawImage(Vis, 0, 0, null);
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		int x = e.getX();
		int y = e.getY() - 25; // -25 perch� il cursore � sballato
		int colore = Vis.getRGB(x, y);
		
		//System.out.println(Vis.getRGB(x, y));
		int winColor = -16733524;
		
		int wallColor1 = -949760;
		int wallColor2 = -65518;
		int wallColor3 = -16711930;
		
		if(colore == winColor) //colore avanzamento livello
		{
			if(Vis == intro)
			{
				Vis = livello1;
			}
			else if(Vis == livello1)
			{
				Vis = livello2;
			}
			else if(Vis == livello2)
			{
				Vis = livello3;
			}
		}
		/*if(colore == -949760 || colore == -65518 || colore == -16711930)
		{
			Vis = gameover;
		}*/
		if(colore == wallColor1 && Vis == livello1)
		{
			Vis = gameover;
		}
		if(colore == wallColor2 && Vis == livello2)
		{
			Vis = gameover;
		}
		if(colore == wallColor3 && Vis == livello3)
		{
			Vis = gameover;
		}
		
		
		
		
		repaint();
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if(Vis != gameover && Vis != intro)
		{
			Vis = gameover2;
		}
		
	}



	@Override
	public void mouseEntered(MouseEvent e) 
	{
		if(Vis == gameover || Vis == gameover2)
		{
			Vis = intro;
		}
		
	}


	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
