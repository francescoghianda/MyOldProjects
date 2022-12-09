package main;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;


public class ImpossibleGame implements WindowListener 
{
	
	private GamePanel panel;
	
	public static void main(String[] args) 
	{
		new ImpossibleGame();
	}
	
	public ImpossibleGame()
	{
		JFrame window = new JFrame("Impossible Game");
		window.addWindowListener(this);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new GamePanel();
		window.setContentPane(panel);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		
		window.setVisible(true);
	}

	@Override
	public void windowClosing(WindowEvent e) 
	{
		GamePanel.saveGame();
	}
	
	
	@Override
	public void windowActivated(WindowEvent e) 
	{
		
	}

	@Override
	public void windowClosed(WindowEvent e) 
	{
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) 
	{
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) 
	{
		
	}

	@Override
	public void windowIconified(WindowEvent e) 
	{
		
	}

	@Override
	public void windowOpened(WindowEvent e) 
	{
		
	}
}
