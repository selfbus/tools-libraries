package org.selfbus.sbtools.vdviewer.vdx;


import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * A file reader class that works on a {@link RandomAccessFile} and is optimized
 * for mostly sequential reading of the file.
 */
public final class FileBlockReader
{
   private final RandomAccessFile in;
   private final int bufferMax;
   private final byte[] buffer;
   private int rlen = 1, bufferSize = 0, bufferPos = 0;
   private long bufferOffset;
   
   private final Charset charset;
   private final CharsetDecoder decoder;
   private final ByteBuffer bbuf = ByteBuffer.allocate(64);
   private final CharBuffer cbuf = CharBuffer.allocate(64);


   /**
    * Create a new reader that works on the {@link RandomAccessFile} file in
    * and has a default internal buffer size of 32KB.
    * 
    * @param in - the file to read.
    * 
    * @throws IOException in case of I/O problems.
    */
   public FileBlockReader(RandomAccessFile in) throws IOException
   {
      this(in, 32768);
   }

   /**
    * Create a new reader that works on the {@link RandomAccessFile} file in.
    *
    * @param in - the {@link RandomAccessFile} that will be used
    * @param bufferSize - the size of the internal read buffer
    * 
    * @throws IOException in case of I/O problems.
    */
   public FileBlockReader(RandomAccessFile in, int bufferSize) throws IOException
   {
      this.in = in;
      this.bufferMax = bufferSize;
      this.buffer = new byte[bufferMax];
      bufferOffset = in.getFilePointer();
      
      charset = Charset.forName("ISO-8859-15");
      decoder = charset.newDecoder();
   }

   /**
    * @return true if the end of the file is reached.
    */
   public boolean atEnd()
   {
      return rlen <= 0 && bufferPos >= bufferSize;
   }

   /**
    * @return the file-pointer's offset in the file.
    */
   public long getFilePointer()
   {
      return bufferOffset + bufferPos;
   }

   /**
    * @return the size of the file in bytes
    * @throws IOException in case of I/O problems.
    */
   public long getFileSize() throws IOException
   {
      return in.length();
   }

   /**
    * Position the reader at the offset within the file.
    * 
    * @param offset - the offset.
    * 
    * @throws IOException in case of I/O problems.
    */
   public void seek(long offset) throws IOException
   {
      if (offset >= bufferOffset && offset < bufferOffset - bufferSize)
      {
         bufferPos = (int) (offset - bufferOffset);
      }
      else
      {
         in.seek(offset);
         bufferOffset = offset;
         bufferSize = 0;
         bufferPos = 0;
      }
   }

   /**
    * Read the next character.
    * 
    * @return the read character, or '\0' if the end-of-file is reached.
    * @throws IOException in case of I/O problems.
    * @see #atEnd()
    */
   public char read() throws IOException
   {
      if (bufferPos >= bufferSize)
      {
         underrun();
         if (atEnd()) return '\0';
      }
      
      byte b = buffer[bufferPos++];
      if (b < 0)
      {
         bbuf.clear();
         cbuf.clear();

         bbuf.put(0, b);
         decoder.decode(bbuf, cbuf, true);
         return cbuf.get(0);
      }
      return (char) b;
   }

   /**
    * Read the next word. Initial white-spaces are skipped.
    * The end of the word is a white-space, or the end of the file.
    * 
    * @return the read word, or null if the end of the file is reached.
    * @throws IOException in case of I/O problems.
    */
   public String readWord() throws IOException
   {
      final StringBuilder stringBuilder = new StringBuilder(256);
      char ch;

      // Skip initial white-spaces.
      while (true)
      {
         ch = read();
         if (!Character.isWhitespace(ch) || (ch=='\0' && atEnd())) break;
      }

      while (true)
      {
         if (Character.isWhitespace(ch) || (ch=='\0' && atEnd())) break;
         stringBuilder.append(ch);
         ch = read();
      }

      if (stringBuilder.length() <= 0 && atEnd()) return null;
      return stringBuilder.toString();
   }

   /**
    * Read the next line. The end of the line is a line feed LF.
    * CR and/or LF at the end of the line are discarded.
    * 
    * @return the read line, or null if the end of the file is reached.
    * @throws IOException in case of I/O problems.
    */
   public String readLine() throws IOException
   {
      final StringBuilder stringBuilder = new StringBuilder(256);
      char ch;

      while (true)
      {
         ch = read();
         if (ch == '\n' || (ch=='\0' && atEnd())) break;
         if (ch != '\r') stringBuilder.append(ch);
      }

      if (stringBuilder.length() <= 0 && atEnd()) return null;
      return stringBuilder.toString();
   }

   /**
    * Read until the character 'find' is read or end-of-file is reached.
    * 
    * @param find - the character to read until.
    *
    * @return true if the character is found, false if end-of-file was reached.
    * @throws IOException in case of I/O problems.
    */
   public boolean readUntil(char find) throws IOException
   {
      char ch;

      while (true)
      {
         ch = read();
         if (ch == find) return true;
         if (ch == '\0' && atEnd()) break;
      }

      return false;
   }

   /**
    * Read the next block from the file into the internal buffer.
    * 
    * @throws IOException in case of I/O problems.
    */
   protected void underrun() throws IOException
   {
      if (bufferPos < bufferSize)
      {
         System.arraycopy(buffer, bufferPos, buffer, 0, bufferSize - bufferPos);
         bufferSize -= bufferPos;
      }
      else bufferSize = 0;
      bufferOffset += bufferPos;
      bufferPos = 0;
      rlen = in.read(buffer, bufferSize, bufferMax - bufferSize);
      if (rlen > 0) bufferSize += rlen;
   }
}
