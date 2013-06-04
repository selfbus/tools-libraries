package test.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the org.freebus.fbhome.domain.project package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory
{
   private final static QName _VD_QNAME = new QName("", "virtual_device");

   /**
    * Create a new ObjectFactory that can be used to create new instances of schema derived classes
    * for package: org.freebus.fbhome.domain.project
    * 
    */
   public ObjectFactory()
   {
   }

   /**
    * Create an instance of {@link VD}.
    * 
    */
   public VD createVD()
   {
      return new VD();
   }

   /**
    * Create an instance of {@link JAXBElement }{@code <}{@link VD }{@code >}
    * 
    */
   @XmlElementDecl(namespace = "", name = "virtual_device")
   public JAXBElement<VD> createVD(VD value)
   {
      return new JAXBElement<VD>(_VD_QNAME, VD.class, null, value);
   }
}
