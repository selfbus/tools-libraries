package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A manufacturer.
 */
@XmlType(name = "Manufacturer", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdManufacturer
{
   @XmlAttribute(name = "manufacturer_id")
   public int id;

   @XmlAttribute(name = "manufacturer_name")
   public String name;

   @XmlAttribute(name = "address_id")
   public int addressId;
}
