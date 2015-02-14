package org.selfbus.sbtools.knxio.reader;

import org.selfbus.sbtools.knxio.model.Namespaces;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;

/**
 * A namespace filter that adds the namespace {@link Namespaces#KNX} to the XML elements.
 */
@Deprecated
public class KnxNamespaceFilter extends XMLFilterImpl
{
   public KnxNamespaceFilter(XMLReader reader)
   {
      super(reader);
   }

   @Override
   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
   {
      super.startElement(Namespaces.KNX, localName, qName, attributes);
   }
}
