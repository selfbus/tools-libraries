package test.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * A project.
 */
@XmlRootElement
@XmlType(name = "VD", namespace = Namespaces.VD, propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VD
{
   @XmlAttribute
   public String name;

   @XmlAttribute
   public String created;

   @XmlAttribute
   public String version;
}
