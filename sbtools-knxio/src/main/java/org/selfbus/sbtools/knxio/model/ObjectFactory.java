package org.selfbus.sbtools.knxio.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each Java content interface and Java
 * element interface generated in the org.freebus.fbhome.domain.project package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the
 * Java representation for XML content. The Java representation of XML content
 * can consist of schema derived interfaces and classes representing the binding
 * of schema type definitions, element declarations and model groups. Factory
 * methods for each of these are provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory
{
   private final static QName _KNX_DOCUMENT_QNAME = new QName(Namespaces.KNX, "KNX");

   /**
    * Create a new ObjectFactory that can be used to create new instances of
    * schema derived classes for package: org.freebus.fbhome.domain.project
    *
    */
   public ObjectFactory()
   {
   }

   /**
    * Create an instance of {@link KnxDocument}
    *
    */
   public KnxDocument createKnxDocument()
   {
      return new KnxDocument();
   }

   /**
    * Create an instance of {@link JAXBElement}{@code <}{@link KnxDocument}{@code >}
    *
    */
   @XmlElementDecl(namespace = Namespaces.KNX, name = "KNX")
   public JAXBElement<KnxDocument> createKnxDocument(KnxDocument value)
   {
      return new JAXBElement<KnxDocument>(_KNX_DOCUMENT_QNAME, KnxDocument.class, null, value);
   }
}
