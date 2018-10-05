package minesweeper;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;
public class Sound
{
	Sound()
	{
	}
	static void playBomb()
	{
		URL url = Sound.class.getResource("bomb.wav");
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
	void placingFlag()
	{
		//class will implement a sound or slap type of noise 
	}
}
