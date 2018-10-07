package minesweeper;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
public class Sound
{
	Sound()
	{
	}
	void playBomb()
	{
		URL url = getClass().getResource("bomb.wav");
		try
	    {
	    Clip clip = AudioSystem.getClip();
	    clip.open(AudioSystem.getAudioInputStream(new File( url.toURI())));
	    clip.start();
	    }
	    catch (Exception exc)
	    {
	    exc.printStackTrace(System.out);
	    }
	}
}
