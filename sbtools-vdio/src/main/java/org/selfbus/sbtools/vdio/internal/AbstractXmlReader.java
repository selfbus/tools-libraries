package org.selfbus.sbtools.vdio.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;

/**
 * A {@link XMLReader XML reader} for VD files.
 */
public abstract class AbstractXmlReader implements XMLReader
{
   private static final Logger LOGGER = LoggerFactory.getLogger(AbstractXmlReader.class);

   protected final Map<String, Boolean> features = new HashMap<String, Boolean>();
   protected final Map<String, Object> properties = new HashMap<String, Object>();

   protected InputSource input;
   protected int lineNo;

   protected DTDHandler dtdHandler;
   protected EntityResolver resolver;
   protected ContentHandler contentHandler;
   protected ErrorHandler errorHandler = new ErrorHandler()
   {
      @Override
      public void warning(SAXParseException exception) throws SAXException
      {
         LOGGER.info(exception.getMessage());
      }

      @Override
      public void fatalError(SAXParseException exception) throws SAXException
      {
         LOGGER.error(exception.getMessage());
      }

      @Override
      public void error(SAXParseException exception) throws SAXException
      {
         LOGGER.warn(exception.getMessage());
      }
   };

   /**
    * Parses the input source specified by the given system identifier.
    * <p>
    * This method is equivalent to the following:
    * 
    * <pre>
    * parse(new InputSource(systemId));
    * </pre>
    * 
    * @param systemId - the system identifier (URI).
    * 
    * @exception org.xml.sax.SAXException Throws exception on SAX error.
    * @exception java.io.IOException Throws exception on i/o error.
    */
   @Override
   public final void parse(String systemId) throws IOException, SAXException
   {
      parse(new InputSource(systemId));
   }

   /**
    * Parse a VD document.
    * 
    * @param inputSource - the input source to parse
    * 
    * @exception org.xml.sax.SAXException
    * @exception java.io.IOException
    */
   @Override
   public void parse(InputSource input) throws IOException, SAXException
   {
      this.input = input;
      BufferedReader reader = new BufferedReader(getCharacterStream(input));

      try
      {
         contentHandler.setDocumentLocator(locator);
         parseDocument(reader);
      }
      finally
      {
         reader.close();
      }
   }

   /**
    * Parse the document.
    * 
    * @param reader - the reader for the document.
    * 
    * @throws IOException
    * @throws SAXException
    */
   protected abstract void parseDocument(BufferedReader reader) throws IOException, SAXException;

   /**
    * Get the character stream for the input source. A character stream is created if the input
    * source does not contain one.
    * 
    * @param input - the input source.
    * 
    * @return The character stream
    */
   protected Reader getCharacterStream(InputSource input)
   {
      Reader reader = input.getCharacterStream();
      if (reader == null)
         reader = new InputStreamReader(input.getByteStream());

      return reader;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException
   {
      return features.containsKey(name) ? features.get(name) : false;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException
   {
      features.put(name, value);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException
   {
      return properties.get(name);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException
   {
      properties.put(name, value);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setEntityResolver(EntityResolver resolver)
   {
      this.resolver = resolver;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public EntityResolver getEntityResolver()
   {
      return resolver;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setDTDHandler(DTDHandler handler)
   {
      this.dtdHandler = handler;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public DTDHandler getDTDHandler()
   {
      return dtdHandler;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setContentHandler(ContentHandler handler)
   {
      this.contentHandler = handler;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ContentHandler getContentHandler()
   {
      return contentHandler;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setErrorHandler(ErrorHandler handler)
   {
      this.errorHandler = handler;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ErrorHandler getErrorHandler()
   {
      return errorHandler;
   }

   /**
    * The {@link Locator document locator}.
    */
   protected final Locator locator = new Locator()
   {
      @Override
      public String getPublicId()
      {
         return input.getPublicId();
      }

      @Override
      public String getSystemId()
      {
         return input.getSystemId();
      }

      @Override
      public int getLineNumber()
      {
         return lineNo;
      }

      @Override
      public int getColumnNumber()
      {
         return 0;
      }
   };
}
