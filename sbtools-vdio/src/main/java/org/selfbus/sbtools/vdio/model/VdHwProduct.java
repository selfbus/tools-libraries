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

   @XmlAttribute(name = "manufacturer_id")
   private int manufacturerId;

   @XmlAttribute(name = "symbol_id")
   private Integer symbolId;

   @XmlAttribute(name = "product_name", required = true)
   private String name;

   @XmlAttribute(name = "product_version_number")
   private int version;

   @XmlAttribute(name = "component_type")
   private Integer componentType;

   @XmlAttribute(name = "component_attributes")
   private Integer componentAttributes;

   @XmlAttribute(name = "bus_current")
   private Integer busCurrent;

   @XmlAttribute(name = "product_serial_number")
   private String serial;

   @XmlAttribute(name = "component_type_number")
   private Integer componentTypeNumber;

   @XmlAttribute(name = "product_picture")
   private String picture;

   @XmlAttribute(name = "bcu_type_number")
   private int bcuTypeId;

   @XmlAttribute(name = "product_handling")
   private Integer handling;

   @XmlAttribute(name = "product_dll")
   private String dll;

   @XmlAttribute(name = "original_manufacturer_id")
   private Integer originalManufacturerId;

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
   public Integer getBusCurrent()
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
    * Set the current that the product requires, in milliampere.
    * 
    * @param busCurrent - the bus current to set.
    */
   public void setBusCurrent(Integer busCurrent)
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
    * @return the manufacturerId
    */
   public int getManufacturerId()
   {
      return manufacturerId;
   }

   /**
    * @param manufacturerId the manufacturerId to set
    */
   public void setManufacturerId(int manufacturerId)
   {
      this.manufacturerId = manufacturerId;
   }

   /**
    * @return the symbolId
    */
   public Integer getSymbolId()
   {
      return symbolId;
   }

   /**
    * @param symbolId the symbolId to set
    */
   public void setSymbolId(Integer symbolId)
   {
      this.symbolId = symbolId;
   }

   /**
    * @return the componentType
    */
   public Integer getComponentType()
   {
      return componentType;
   }

   /**
    * @param componentType the componentType to set
    */
   public void setComponentType(Integer componentType)
   {
      this.componentType = componentType;
   }

   /**
    * @return the componentAttributes
    */
   public Integer getComponentAttributes()
   {
      return componentAttributes;
   }

   /**
    * @param componentAttributes the componentAttributes to set
    */
   public void setComponentAttributes(Integer componentAttributes)
   {
      this.componentAttributes = componentAttributes;
   }

   /**
    * @return the componentTypeNumber
    */
   public Integer getComponentTypeNumber()
   {
      return componentTypeNumber;
   }

   /**
    * @param componentTypeNumber the componentTypeNumber to set
    */
   public void setComponentTypeNumber(Integer componentTypeNumber)
   {
      this.componentTypeNumber = componentTypeNumber;
   }

   /**
    * @return the picture
    */
   public String getPicture()
   {
      return picture;
   }

   /**
    * @param picture the picture to set
    */
   public void setPicture(String picture)
   {
      this.picture = picture;
   }

   /**
    * @return the bcuTypeId
    */
   public int getBcuTypeId()
   {
      return bcuTypeId;
   }

   /**
    * @param bcuTypeId the bcuTypeId to set
    */
   public void setBcuTypeId(int bcuTypeId)
   {
      this.bcuTypeId = bcuTypeId;
   }

   /**
    * @return the handling
    */
   public Integer getHandling()
   {
      return handling;
   }

   /**
    * @param handling the handling to set
    */
   public void setHandling(Integer handling)
   {
      this.handling = handling;
   }

   /**
    * @return the dll
    */
   public String getDll()
   {
      return dll;
   }

   /**
    * @param dll the dll to set
    */
   public void setDll(String dll)
   {
      this.dll = dll;
   }

   /**
    * @return the originalManufacturerId
    */
   public Integer getOriginalManufacturerId()
   {
      return originalManufacturerId;
   }

   /**
    * @param originalManufacturerId the originalManufacturerId to set
    */
   public void setOriginalManufacturerId(Integer originalManufacturerId)
   {
      this.originalManufacturerId = originalManufacturerId;
   }

   /**
    * @param name the name to set
    */
   public void setName(String name)
   {
      this.name = name;
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
