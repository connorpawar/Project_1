package minesweeper;

import java.io.*;
import java.net.URL;
import javax.sound.sampled.*;

/**
 * 
 * Creates sound objects to play game sound effects depending on user action
 * 
 *
 */
public class Sound
{
	/**
	 * Constructor creates sound object to play sound effects
	 * @ms.Pre-condition Input fields from {@link #Menu()} have been verified and the user has pressed the "Start" button 
     * @ms.Post-condition Calls the function that creates the sound object
	 */
	Sound()
	{
	}
	/**
	 * Method to play explosion sound upon user's clicking a mine
	 * @ms.Pre-condition The board isn't disposed and the user hasn't lost yet
     * @ms.Post-condition User has lost. Replay pop-up will appear
	 */
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
