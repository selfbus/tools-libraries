package org.selfbus.sbtools.sniffer.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * A writer for .snif recordings.
 */
public class SnifWriter
{
   private final DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.SSS");

   /**
    * Write a recording to the file.
    *
    * @param file - the file to write.
    * @param records - the records.
    * @throws IOException
    */
   public void write(File file, List<Record> records) throws IOException
   {
      write(new FileOutputStream(file), records);
   }

   /**
    * Write a recording to the file.
    *
    * @param out - the stream to write.
    * @param records - the records.
    * @throws IOException 
    */
   public void write(OutputStream out, List<Record> records) throws IOException
   {
      PrintWriter writer = new PrintWriter(out);

      for (Record record : records)
      {
         writer.print(dateFormat.format(new Date(record.time)));
         writer.print(" ");
         writer.print(record.direction.toString());
         writer.print(" ");

         for (int i = 0; i < record.length; ++i)
            writer.printf(" %02x", record.data[i] & 255);

         writer.println();
      }

      writer.close();
   }
}
