package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Catalog entries name the variations of a virtual device, as it can be bought
 * from a catalog or web-shop. Catalog entries of the same virtual device
 * usually differ in things like housing color or maximum switching power.
 */
@XmlType(name = "CatalogEntry", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdCatalogEntry
{
   @XmlAttribute(name = "catalog_entry_id", required = true)
   private int id;

   @XmlAttribute(name = "product_id", required = true)
   private int productId;

   @XmlAttribute(name = "manufacturer_id", required = true)
   private int manufacturerId;

   @XmlAttribute(name = "symbol_id")
   private Integer symbolId;

   @XmlAttribute(name = "order_number")
   private String orderNumber;

   @XmlAttribute(name = "entry_name", required = true)
   private String name;

   @XmlAttribute(name = "entry_colour")
   private String color;

   @XmlAttribute(name = "entry_width_in_modules")
   private Integer widthModules;

   @XmlAttribute(name = "entry_width_in_millimeters")
   private Integer widthMM;

   @XmlAttribute(name = "price")
   private Integer price;

   @XmlAttribute(name = "currency")
   private String currency;

   @XmlAttribute(name = "quantity_unit")
   private String quantityUnit;

   @XmlAttribute(name = "material_price")
   private Integer materialPrice;

   @XmlAttribute(name = "mounting_hours")
   private Integer mountingHours;

   @XmlAttribute(name = "mounting_minutes")
   private Integer mountingMinutes;

   @XmlAttribute(name = "mounting_seconds")
   private Integer mountingSeconds;

   @XmlAttribute(name = "din_flag", required = true)
   private boolean din;

   @XmlAttribute(name = "series")
   private String series;

   @XmlAttribute(name = "catalog_name")
   private String catalogName;

   @XmlAttribute(name = "page_number")
   private Integer pageNumber;

   @XmlAttribute(name = "entry_picture")
   private String entryPicture;

   @XmlAttribute(name = "designation_type")
   private String designationType;

   @XmlAttribute(name = "designation_function")
   private String designationFunction;

   @XmlAttribute(name = "help_file_name")
   private String helpFileName;

   @XmlAttribute(name = "context_id")
   private Integer contextId;

   @XmlAttribute(name = "ram_size")
   private Integer ramSize;

   @XmlAttribute(name = "registration_number")
   private Integer registrationNumber;

   @XmlAttribute(name = "registration_year")
   private Integer registrationYear;

   @XmlAttribute(name = "entry_status_code")
   private Integer entryStatusCode;

   @XmlAttribute(name = "registration_ts")
   private String registrationTS;


   /**
    * Create an empty catalog-entry object.
    */
   public VdCatalogEntry()
   {
   }

   /**
    * Create an empty catalog-entry object with an id.
    * 
    * @param id - the database ID of the object.
    */
   public VdCatalogEntry(int id)
   {
      this.id = id;
   }

   /**
    * Create a catalog-entry object.
    * 
    * @param id - the database ID of the object.
    * @param name - the name of the object.
    * @param manufacturerId - the manufacturer ID.
    * @param productId - the product ID.
    */
   public VdCatalogEntry(int id, String name, int manufacturerId, int productId)
   {
      this.id = id;
      this.name = name;
      this.manufacturerId = manufacturerId;
      this.productId = productId;
   }

   /**
    * Create a catalog-entry object.
    *
    * @param name - the name of the object.
    * @param manufacturerId - the manufacturer ID.
    */
   public VdCatalogEntry(String name, int manufacturerId)
   {
      this(0, name, manufacturerId, 0);
   }

   /**
    * @return the id of the catalog-entry.
    */
   public int getId()
   {
      return id;
   }

   /**
    * Set the id of the catalog-entry.
    * 
    * @param id - the ID to set.
    */
   public void setId(int id)
   {
      this.id = id;
   }

   /**
    * @return the name of the catalog-entry.
    */
   public String getName()
   {
      return name;
   }

   /**
    * Set the name of the catalog-entry.
    * 
    * @param name - the name to set.
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * @return the manufacturer ID.
    */
   public int getManufacturerId()
   {
      return manufacturerId;
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
    * @return the hardware product ID.
    */
   public int getProductId()
   {
      return productId;
   }

   /**
    * Set the hardware product ID.
    * 
    * @param product - the product to set.
    */
   public void setProductId(int productId)
   {
      this.productId = productId;
   }

   /**
    * @return the with in module-units.
    */
   public Integer getWidthModules()
   {
      return widthModules;
   }

   /**
    * Set the with in module-units.
    * 
    * @param widthModules - the width to set.
    */
   public void setWidthModules(Integer widthModules)
   {
      this.widthModules = widthModules;
   }

   /**
    * @return the width in millimeters.
    */
   public Integer getWidthMM()
   {
      return widthMM;
   }

   /**
    * Set the width in millimeters.
    * 
    * @param widthMM - the width to set.
    */
   public void setWidthMM(Integer widthMM)
   {
      this.widthMM = widthMM;
   }

   /**
    * Test if the device can be mounted on DIN rails.
    * 
    * @return True if the device is DIN mountable.
    */
   public boolean getDIN()
   {
      return din;
   }

   /**
    * Set if the device can be mounted on DIN rails.
    * 
    * @param din - true if the device is DIN mountable.
    */
   public void setDIN(boolean din)
   {
      this.din = din;
   }

   /**
    * @return the order-number.
    */
   public String getOrderNumber()
   {
      return orderNumber;
   }

   /**
    * Set the order-number.
    * 
    * @param orderNumber - the order number to set.
    */
   public void setOrderNumber(String orderNumber)
   {
      this.orderNumber = orderNumber;
   }

   /**
    * @return the color of the device.
    */
   public String getColor()
   {
      return color;
   }

   /**
    * Set the color of the device.
    * 
    * @param color - the color of the device.
    */
   public void setColor(String color)
   {
      this.color = color;
   }

   /**
    * @return the series.
    */
   public String getSeries()
   {
      return series;
   }

   /**
    * Set the series.
    * 
    * @param series - the series to set.
    */
   public void setSeries(String series)
   {
      this.series = series;
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
    * {@inheritDoc}
    */
   @Override
   public boolean equals(final Object o)
   {
      if (o == this)
         return true;

      if (!(o instanceof VdCatalogEntry))
         return false;

      final VdCatalogEntry oo = (VdCatalogEntry) o;
      return id == oo.id && manufacturerId == oo.manufacturerId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return name == null ? "#" + id : name;
   }
}
