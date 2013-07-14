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
   private Double widthModules;

   @XmlAttribute(name = "entry_width_in_millimeters")
   private Double widthMM;

   @XmlAttribute(name = "price")
   private Double price;

   @XmlAttribute(name = "currency")
   private String currency;

   @XmlAttribute(name = "quantity_unit")
   private String quantityUnit;

   @XmlAttribute(name = "material_price")
   private Double materialPrice;

   @XmlAttribute(name = "mounting_hours")
   private Integer mountingHours;

   @XmlAttribute(name = "mounting_minutes")
   private Integer mountingMinutes;

   @XmlAttribute(name = "mounting_seconds")
   private Integer mountingSeconds;

   @XmlAttribute(name = "din_flag")
   private int din = 1;

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

   @XmlAttribute(name = "registration_date")
   private String registrationDate;

   @XmlAttribute(name = "registration_comment")
   private String registrationComment;


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
   public Double getWidthModules()
   {
      return widthModules;
   }

   /**
    * Set the with in module-units.
    * 
    * @param widthModules - the width to set.
    */
   public void setWidthModules(Double widthModules)
   {
      this.widthModules = widthModules;
   }

   /**
    * @return the width in millimeters.
    */
   public Double getWidthMM()
   {
      return widthMM;
   }

   /**
    * Set the width in millimeters.
    * 
    * @param widthMM - the width to set.
    */
   public void setWidthMM(Double widthMM)
   {
      this.widthMM = widthMM;
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
    * @return the price
    */
   public Double getPrice()
   {
      return price;
   }

   /**
    * @param price the price to set
    */
   public void setPrice(Double price)
   {
      this.price = price;
   }

   /**
    * @return the currency
    */
   public String getCurrency()
   {
      return currency;
   }

   /**
    * @param currency the currency to set
    */
   public void setCurrency(String currency)
   {
      this.currency = currency;
   }

   /**
    * @return the quantityUnit
    */
   public String getQuantityUnit()
   {
      return quantityUnit;
   }

   /**
    * @param quantityUnit the quantityUnit to set
    */
   public void setQuantityUnit(String quantityUnit)
   {
      this.quantityUnit = quantityUnit;
   }

   /**
    * @return the materialPrice
    */
   public Double getMaterialPrice()
   {
      return materialPrice;
   }

   /**
    * @param materialPrice the materialPrice to set
    */
   public void setMaterialPrice(Double materialPrice)
   {
      this.materialPrice = materialPrice;
   }

   /**
    * @return the mountingHours
    */
   public Integer getMountingHours()
   {
      return mountingHours;
   }

   /**
    * @param mountingHours the mountingHours to set
    */
   public void setMountingHours(Integer mountingHours)
   {
      this.mountingHours = mountingHours;
   }

   /**
    * @return the mountingMinutes
    */
   public Integer getMountingMinutes()
   {
      return mountingMinutes;
   }

   /**
    * @param mountingMinutes the mountingMinutes to set
    */
   public void setMountingMinutes(Integer mountingMinutes)
   {
      this.mountingMinutes = mountingMinutes;
   }

   /**
    * @return the mountingSeconds
    */
   public Integer getMountingSeconds()
   {
      return mountingSeconds;
   }

   /**
    * @param mountingSeconds the mountingSeconds to set
    */
   public void setMountingSeconds(Integer mountingSeconds)
   {
      this.mountingSeconds = mountingSeconds;
   }

   /**
    * @return the din
    */
   public boolean isDIN()
   {
      return 1 == din;
   }

   /**
    * @param din the din to set
    */
   public void setDIN(boolean din)
   {
      this.din = din ? 1 : 0;
   }

   /**
    * @return the catalogName
    */
   public String getCatalogName()
   {
      return catalogName;
   }

   /**
    * @param catalogName the catalogName to set
    */
   public void setCatalogName(String catalogName)
   {
      this.catalogName = catalogName;
   }

   /**
    * @return the pageNumber
    */
   public Integer getPageNumber()
   {
      return pageNumber;
   }

   /**
    * @param pageNumber the pageNumber to set
    */
   public void setPageNumber(Integer pageNumber)
   {
      this.pageNumber = pageNumber;
   }

   /**
    * @return the entryPicture
    */
   public String getEntryPicture()
   {
      return entryPicture;
   }

   /**
    * @param entryPicture the entryPicture to set
    */
   public void setEntryPicture(String entryPicture)
   {
      this.entryPicture = entryPicture;
   }

   /**
    * @return the designationType
    */
   public String getDesignationType()
   {
      return designationType;
   }

   /**
    * @param designationType the designationType to set
    */
   public void setDesignationType(String designationType)
   {
      this.designationType = designationType;
   }

   /**
    * @return the designationFunction
    */
   public String getDesignationFunction()
   {
      return designationFunction;
   }

   /**
    * @param designationFunction the designationFunction to set
    */
   public void setDesignationFunction(String designationFunction)
   {
      this.designationFunction = designationFunction;
   }

   /**
    * @return the helpFileName
    */
   public String getHelpFileName()
   {
      return helpFileName;
   }

   /**
    * @param helpFileName the helpFileName to set
    */
   public void setHelpFileName(String helpFileName)
   {
      this.helpFileName = helpFileName;
   }

   /**
    * @return the contextId
    */
   public Integer getContextId()
   {
      return contextId;
   }

   /**
    * @param contextId the contextId to set
    */
   public void setContextId(Integer contextId)
   {
      this.contextId = contextId;
   }

   /**
    * @return the ramSize
    */
   public Integer getRamSize()
   {
      return ramSize;
   }

   /**
    * @param ramSize the ramSize to set
    */
   public void setRamSize(Integer ramSize)
   {
      this.ramSize = ramSize;
   }

   /**
    * @return the registrationNumber
    */
   public Integer getRegistrationNumber()
   {
      return registrationNumber;
   }

   /**
    * @param registrationNumber the registrationNumber to set
    */
   public void setRegistrationNumber(Integer registrationNumber)
   {
      this.registrationNumber = registrationNumber;
   }

   /**
    * @return the registrationYear
    */
   public Integer getRegistrationYear()
   {
      return registrationYear;
   }

   /**
    * @param registrationYear the registrationYear to set
    */
   public void setRegistrationYear(Integer registrationYear)
   {
      this.registrationYear = registrationYear;
   }

   /**
    * @return the entryStatusCode
    */
   public Integer getEntryStatusCode()
   {
      return entryStatusCode;
   }

   /**
    * @param entryStatusCode the entryStatusCode to set
    */
   public void setEntryStatusCode(Integer entryStatusCode)
   {
      this.entryStatusCode = entryStatusCode;
   }

   /**
    * @return the registrationTS
    */
   public String getRegistrationTS()
   {
      return registrationTS;
   }

   /**
    * @param registrationTS the registrationTS to set
    */
   public void setRegistrationTS(String registrationTS)
   {
      this.registrationTS = registrationTS;
   }

   /**
    * @return the registrationDate
    */
   public String getRegistrationDate()
   {
      return registrationDate;
   }

   /**
    * @param registrationDate the registrationDate to set
    */
   public void setRegistrationDate(String registrationDate)
   {
      this.registrationDate = registrationDate;
   }

   /**
    * @return the registrationComment
    */
   public String getRegistrationComment()
   {
      return registrationComment;
   }

   /**
    * @param registrationComment the registrationComment to set
    */
   public void setRegistrationComment(String registrationComment)
   {
      this.registrationComment = registrationComment;
   }

   /**
    * @param manufacturerId the manufacturerId to set
    */
   public void setManufacturerId(int manufacturerId)
   {
      this.manufacturerId = manufacturerId;
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
