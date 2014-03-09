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
   @XmlAttribute(name = "manufacturer_id", required = true)
   private int id;

   @XmlAttribute(name = "manufacturer_name", required = true)
   private String name;

   @XmlAttribute(name = "address_id")
   private Integer addressId;

   /**
    * @return the id
    */
   public int getId()
   {
      return id;
   }

   /**
    * @param id the id to set
    */
   public void setId(int id)
   {
      this.id = id;
   }

   /**
    * @return the name
    */
   public String getName()
   {
      return name;
   }

   /**
    * @param name the name to set
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * @return the addressId
    */
   public Integer getAddressId()
   {
      return addressId;
   }

   /**
    * @param addressId the addressId to set
    */
   public void setAddressId(Integer addressId)
   {
      this.addressId = addressId;
   }
}
