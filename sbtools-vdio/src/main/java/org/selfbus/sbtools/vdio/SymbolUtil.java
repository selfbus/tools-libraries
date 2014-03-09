package org.selfbus.sbtools.vdio;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageInputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;

/**
 * Utility methods for converting between BMP symbol byte arrays and images.
 */
public final class SymbolUtil
{
   /**
    * Create an {@link Image} from a BMP byte array.
    * 
    * @param data - the byte array to convert.
    * @return The symbol image.
    * 
    * @throws VdioException if image creation failed
    */
   public static BufferedImage toImage(byte[] data) throws VdioException
   {
      ImageReader reader = ImageIO.getImageReadersByFormatName("BMP").next();

      // Don't ask me why, but sometimes these bytes are >0 and then reading the
      // image fails. Might be as well a BMP feature that Java's image reader
      // does not understand.
      data[4] = 0;
      data[12] = 0;

      ByteArrayInputStream in = new ByteArrayInputStream(data);
      MemoryCacheImageInputStream imageIn = new MemoryCacheImageInputStream(in);
      reader.setInput(imageIn);

      try
      {
         return reader.read(0);
      }
      catch (IOException e)
      {
         throw new VdioException("Failed to create image from byte array", e);
      }
      finally
      {
         try
         {
            imageIn.close();
         }
         catch (IOException e)
         {
         }
      }
   }

   /**
    * Convert an {@link Image} to a BMP byte array.
    * 
    * @param image - the image to convert.
    * @return The byte array of the image in BMP format.
    * @throws VdioException if byte array creation failed
    */
   public static byte[] toByteArray(BufferedImage image) throws VdioException
   {
      ImageWriter writer = ImageIO.getImageWritersByFormatName("BMP").next();

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      MemoryCacheImageOutputStream imageOut = new MemoryCacheImageOutputStream(out); 
      writer.setOutput(imageOut);

      try
      {
         writer.write(image);
         imageOut.close();

         return out.toByteArray();
      }
      catch (IOException e)
      {
         try
         {
            imageOut.close();
         }
         catch (IOException ex)
         {
         }

         throw new VdioException("Failed to create byte array from image", e);
      }
   }
}
