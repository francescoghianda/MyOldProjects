package Main;
import javax.swing.JFrame;


public class Game 
{
	private static JFrame window;
	private static GamePanel game;
	
	public static void main(String[] args) 
	{
		window = new JFrame("Don't Tap The White Tile | ");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game = new GamePanel();
		window.setContentPane(game);
		window.setResizable(false);
		window.pack();
		window.setLocationRelativeTo(null);
		
		window.setVisible(true);
		
		Thread title = new Thread(new Runnable()
		{
			public void run()
			{
				while(true)
				{
					window.setTitle("Don't Tap The White Tile | "+game.getAverageFPS()+" fps");
					
					try
					{
						Thread.sleep(500);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		});
		
		title.run();
	}
}
