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
		try
	    {
	    Clip clip = AudioSystem.getClip();
	    clip.open(AudioSystem.getAudioInputStream(new File("src/minesweeper/235968__tommccann__explosion-01.wav")));
	    clip.start();
	    }
	    catch (Exception exc)
	    {
	    exc.printStackTrace(System.out);
	    }
	}
}
