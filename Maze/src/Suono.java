import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


public class Suono 
{
	private File suono;
	private AudioFileFormat fileFormat;
	private AudioInputStream audioInStream;
	private AudioFormat audioFormat;
	private Clip clip;
	
	public Suono(File suono) throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		this.suono = suono;
		fileFormat = AudioSystem.getAudioFileFormat(this.suono);
		audioInStream = AudioSystem.getAudioInputStream(this.suono);
		audioFormat = fileFormat.getFormat();
		
		DataLine.Info info = new DataLine.Info(Clip.class, audioInStream.getFormat(), ((int) audioInStream.getFrameLength() * audioFormat.getFrameSize()));
		clip = (Clip) AudioSystem.getLine(info);
	}

	public Suono(InputStream suono) throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		fileFormat = AudioSystem.getAudioFileFormat(suono);
		audioInStream = AudioSystem.getAudioInputStream(suono);
		audioFormat = fileFormat.getFormat();

		DataLine.Info info = new DataLine.Info(Clip.class, audioInStream.getFormat(), ((int) audioInStream.getFrameLength() * audioFormat.getFrameSize()));
		clip = (Clip) AudioSystem.getLine(info);
	}
	
	public void play() throws LineUnavailableException, IOException
	{
		clip.open(audioInStream);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public void play(int count) throws LineUnavailableException, IOException
	{
		clip.open(audioInStream);
		clip.loop(count);
	}
	public void playOnce() throws LineUnavailableException, IOException
	{
		clip.open(audioInStream);
		clip.start();
	}
	
	public void stop()
	{
		clip.stop();
		clip.close();
	}
	
}
