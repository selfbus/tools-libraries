package org.selfbus.sbtools.sniffer.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TestSnifReader
{
   @Test
   public void read() throws IOException
   {
      String data = 
         "21:07:10.715 SEND  29 b0 11 ff 11 05 60 80\n" +
         "21:07:13.504 RECV  29 b0 11 ff 11 05 61 43 00\n" +
         "21:07:13.535 SEND  29 b0 11 05 11 ff 60 c2\n" +
         "\n" +
         "21:07:13.594 SEND  29 b0 11 05 11 ff 63 43 40 00 12\n" +
         "21:07:13.627 RECV  29 90 11 05 11 ff 63 43 40 00 12\n" +
         "21:07:13.683 SEND  29 b0 11 ff 11 05 60 c2\n" +
         "21:07:13.844 RECV  29 b0 11 ff 11 05 64 46 81 01 0d 00\n" +
         "\n";
      InputStream in = new ByteArrayInputStream(data.getBytes());

      SnifReader reader = new SnifReader();
      ArrayList<Record> records = reader.read(in);
      assertNotNull(records);
      assertEquals(7, records.size());
   }

   @Test(expected = IllegalArgumentException.class)
   public void readWrongDateFormat() throws IOException
   {
      String data = "21 SEND  29 b0 11 10 11 05 60 80\n";
      InputStream in = new ByteArrayInputStream(data.getBytes());
      SnifReader reader = new SnifReader();
      reader.read(in);
   }

   @Test(expected = IllegalArgumentException.class)
   public void readWrongDirection() throws IOException
   {
      String data = "21:07:10.715 xxx  29 b0 11 10 11 05 60 80\n";
      InputStream in = new ByteArrayInputStream(data.getBytes());
      SnifReader reader = new SnifReader();
      reader.read(in);
   }

   @Test(expected = IllegalArgumentException.class)
   public void readValueOutOfBounds() throws IOException
   {
      String data = "21:07:10.715 SEND  29 b0 11 100 11 05 60 80\n";
      InputStream in = new ByteArrayInputStream(data.getBytes());
      SnifReader reader = new SnifReader();
      reader.read(in);
   }
}
