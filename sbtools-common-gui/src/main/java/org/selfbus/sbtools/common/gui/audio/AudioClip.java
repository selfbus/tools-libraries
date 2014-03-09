package org.selfbus.sbtools.common.gui.audio;

import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for playing an audio clip.
 */
public class AudioClip
{
   private final static Logger LOGGER = LoggerFactory.getLogger(AudioClip.class);
   private final static int EXTERNAL_BUFFER_SIZE = 8192;

   private final java.net.URL clipUrl;
   private SourceDataLine line;
   private Thread playbackThread;

   /**
    * Create an audio clip object.
    * 
    * @param clipName - the name of the audio clip, without path or file
    *           extension.
    */
   public AudioClip(final String clipName)
   {
      final String clipFileName = "sounds/" + clipName + ".wav";
      LOGGER.debug("Playing " + clipFileName);

      final ClassLoader classLoader = getClass().getClassLoader();
      clipUrl = classLoader.getResource(clipFileName);
   }

   /**
    * Play the audio clip. The method returns immediately and the audio clip is
    * played in the background by an extra thread.
    */
   public void play()
   {
      try
      {
         final AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(clipUrl);
         final AudioFormat format = audioInputStream.getFormat();

         final DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
         line = (SourceDataLine) AudioSystem.getLine(info);

         line.open();
         line.start();

         playbackThread = new Thread()
         {
            @Override
            public void run()
            {
               try
               {
                  int rlen = 0;
                  final byte[] buffer = new byte[EXTERNAL_BUFFER_SIZE];
                  while (rlen != -1)
                  {
                     rlen = audioInputStream.read(buffer, 0, buffer.length);
                     if (rlen >= 0)
                        line.write(buffer, 0, rlen);
                  }
               }
               catch (IOException e)
               {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
               }
               finally
               {
                  if (line != null && line.isOpen())
                  {
                     line.drain();
                     line.close();
                  }
               }
            }
         };

         playbackThread.start();
      }
      catch (Exception e)
      {
         LOGGER.warn("Failed to play audio clip " + clipUrl, e);
      }
   }
}
