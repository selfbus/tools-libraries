package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A hardware product contains the hardware details about a device, taken from a .VD_
 * specification file (HW_PRODUCTS section). This is not a device that gets
 * installed, it is the technical description of the hardware.
 */
@XmlType(name = "HwProduct", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdHwProduct
{
   @XmlAttribute(name = "product_id", required = true)
   private int id;

   @XmlAttribute(name = "product_name", required = true)
   private String name;

   @XmlAttribute(name = "manufacturer_id")
   private int manufacturerId;

   @XmlAttribute(name = "product_version_number")
   private int version;

   @XmlAttribute(name = "product_serial_number")
   private String serial;

   @XmlAttribute(name = "bus_current")
   private int busCurrent;

   @XmlAttribute(name = "bcu_type_number")
   private int bcuTypeId;

   /**
    * Create an empty product object.
    */
   public VdHwProduct()
   {
   }

   /**
    * Create a product.
    * 
    * @param id - the database ID of the product.
    * @param name - the name of the product.
    * @param manufacturerId - the manufacturer.
    */
   public VdHwProduct(int id, String name, int manufacturerId)
   {
      this.id = id;
      this.name = name;
      this.manufacturerId = manufacturerId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(final Object o)
   {
      if (o == this)
         return true;

      if (!(o instanceof VdHwProduct))
         return false;

      final VdHwProduct oo = (VdHwProduct) o;
      return id == oo.id && manufacturerId == oo.manufacturerId && name.equals(oo.name);
   }

   /**
    * @return The current that the product requires, in milliampere.
    */
   public int getBusCurrent()
   {
      return busCurrent;
   }

   /**
    * @return the product ID.
    */
   public int getId()
   {
      return id;
   }

   /**
    * @return the manufacturer ID.
    */
   public int getManufacturer()
   {
      return manufacturerId;
   }

   /**
    * @return the name of the product.
    */
   public String getName()
   {
      return name;
   }

   /**
    * @return the product-id that the manufacturer assigned.
    */
   public String getSerial()
   {
      return serial;
   }

   /**
    * @return The version.
    */
   public int getVersion()
   {
      return version;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return id;
   }

   /**
    * Set the current that the product requires, in milli-ampere.
    * 
    * @param busCurrent - the bus current to set.
    */
   public void setBusCurrent(int busCurrent)
   {
      this.busCurrent = busCurrent;
   }

   /**
    * Set the product id.
    * 
    * @param id - the id to set.
    */
   public void setId(int id)
   {
      this.id = id;
   }

   /**
    * Set the manufacturer ID.
    * 
    * @param manufacturerId - the manufacturer ID to set.
    */
   public void setManufacturer(int manufacturerId)
   {
      this.manufacturerId = manufacturerId;
   }

   /**
    * Set the serial-number that the manufacturer assigned.
    * 
    * @param serial - the serial to set.
    */
   public void setSerial(String serial)
   {
      this.serial = serial;
   }

   /**
    * Set the version.
    *
    * @param version - the version to set.
    */
   public void setVersion(int version)
   {
      this.version = version;
   }

   /**
    * @param bcuTypeId the bcuType ID to set
    */
   public void setBcuType(int bcuTypeId)
   {
      this.bcuTypeId = bcuTypeId;
   }

   /**
    * @return the bcuType
    */
   public int getBcuType()
   {
      return bcuTypeId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return getClass().getSimpleName() + " #" + id + " \"" + name + "\"";
   }
}
