package org.selfbus.sbtools.sniffer.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A reader for .snif recordings.
 */
public class SnifReader
{
   private static final Logger LOGGER = LoggerFactory.getLogger(SnifReader.class);

   /**
    * Read a recording from the file.
    *
    * @param file - the file to read.
    * @return The recording.
    * @throws IOException
    */
   public ArrayList<Record> read(File file) throws IOException
   {
      return read(new FileInputStream(file));
   }

   /**
    * Read a recording from the stream.
    *
    * @param in - the stream to read.
    * @return The recording.
    * @throws IOException 
    */
   public ArrayList<Record> read(InputStream in) throws IOException
   {
      return read(new BufferedReader(new InputStreamReader(in)));
   }

   /**
    * Read a recording from the reader.
    *
    * @param reader - the reader to read.
    * @return The recording.
    * @throws IOException 
    */
   public ArrayList<Record> read(BufferedReader reader) throws IOException
   {
      final ArrayList<Record> records = new ArrayList<Record>(1000);

      int lineNo = 0;
      while (true)
      {
         ++lineNo;
         String line = reader.readLine();
         if (line == null) break;

         line = line.trim();
         if (line.isEmpty()) continue;

         String parts[] = line.split("  *");
         if (parts.length < 3)
         {
            LOGGER.warn("line {}: invalid record format", lineNo);
            continue;
         }

         Date when = parseDate(parts[0]);
         Validate.isTrue(when != null, "line " + lineNo + ": invalid date format");

         Direction dir = Direction.valueOf(parts[1].toUpperCase());
         Validate.isTrue(dir != null, "line " + lineNo + ": invalid direction \"" + parts[1] + "\"");

         byte[] data = new byte[parts.length - 2];
         for (int idx = 2; idx < parts.length; ++idx)
         {
            int val = Integer.parseInt(parts[idx], 16);
            Validate.isTrue(val >= 0 && val <= 255, "line " + lineNo + ": value out of bounds: " + val);
            data[idx - 2] = (byte) val;
         }

         records.add(new Record(when.getTime(), dir, data, data.length));
      }

      reader.close();
      return records;
   }

   /**
    * Parse a date string in the format HH:MM:SS.mmm
    * 
    * @param str - the date string to parse.
    * @return The parsed date, or null if the date could not be parsed
    */
   Date parseDate(String str)
   {
      String parts[] = str.split("[.:]");
      if (parts.length != 4) return null;

      int sec = Integer.parseInt(parts[0]) * 3600;
      sec += Integer.parseInt(parts[1]) * 60;
      sec += Integer.parseInt(parts[2]);

      return new Date(sec * 1000 + Integer.parseInt(parts[3]));
   }
}
