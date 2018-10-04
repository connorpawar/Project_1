import java.io.*;
import javax.sound.sampled.*;
public class Sound
{
  Sound()
  {
  }
  static void play()
  {
    try
    {
    Clip clip = AudioSystem.getClip();
    clip.open(AudioSystem.getAudioInputStream(new File("Resources/bomb.wav")));
    clip.start();
    }
    catch (Exception exc)
    {
    exc.printStackTrace(System.out);
    }
  }
}
