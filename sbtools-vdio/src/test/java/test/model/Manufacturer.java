package test.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A manufacturer.
 */
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class Manufacturer
{
   @XmlAttribute(name = "manufacturer_name")
   public String name;

   @XmlAttribute(name = "manufacturer_id")
   public int id;
}
