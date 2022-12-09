package main;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sound 
{
	
	private File audioFile;
	private AudioInputStream audioInStream;
	private Clip clip;
	
	private boolean loaded;
	
	private static boolean SOUNDS_ON = true;
	
	public Sound(String audioFilePath)
	{
		try 
		{
			clip = AudioSystem.getClip();
	        audioInStream = AudioSystem.getAudioInputStream(this.getClass().getResource(audioFilePath));
	        audioInStream.mark(Integer.MAX_VALUE);
	        loaded = true;
	    } 
		catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) 
		{
	        e.printStackTrace();
	    }
	}
	
	
	public static boolean isEnabled()
	{
		return SOUNDS_ON;
	}
	public static void enableSounds(boolean b)
	{
		SOUNDS_ON = b;
	}
	
	public void playOnce()
	{
		if(loaded && SOUNDS_ON)
		{
			try 
			{
				clip = AudioSystem.getClip();
				audioInStream.reset();
				clip.open(audioInStream);
		        clip.loop(0);
			} 
			catch (LineUnavailableException | IOException e) 
			{
				System.err.println("Errore riproduzione file: " + audioFile);
			}
		}
		
	}
	
	public void stop()
	{
		if(loaded && SOUNDS_ON)
		{
			clip.stop();
			clip.close();
		}
	}
	
	public boolean isRunning()
	{
		if(loaded)
		{
			try
			{
				return clip.isRunning();
			}
			catch(Exception ex)
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}
