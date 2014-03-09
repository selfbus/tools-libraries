package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A virtual device. This is a device as it could be added to a project.
 */
@XmlType(name = "VirtualDevice", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdVirtualDevice
{
   @XmlAttribute(name = "virtual_device_id", required = true)
   private int id;

   @XmlAttribute(name = "symbol_id")
   private Integer symbolId;

   @XmlAttribute(name = "catalog_entry_id", required = true)
   private int catalogEntryId;

   @XmlAttribute(name = "program_id", required = true)
   private int programId;

   @XmlAttribute(name = "functional_entity_id", required = true)
   private int functionalEntityId;

   @XmlAttribute(name = "virtual_device_name", required = true)
   private String name = "";

   @XmlAttribute(name = "virtual_device_description")
   private String description;

   @XmlAttribute(name = "virtual_device_number")
   private int number;

   @XmlAttribute(name = "product_type_id", required = true)
   private int productTypeId;

   @XmlAttribute(name = "medium_types")
   private String mediumTypes;

   /**
    * Create an empty virtual-device object.
    */
   public VdVirtualDevice()
   {
   }

   /**
    * Create a virtual-device object.
    * 
    * @param id - the database ID of the object.
    * @param name - the name of the object.
    * @param functionalEntityId - the functional entity ID.
    * @param catalogEntry - the catalog entry.
    */
   public VdVirtualDevice(int id, String name, int functionalEntityId, int catalogEntryId)
   {
      this.id = id;
      this.name = name;
      this.functionalEntityId = functionalEntityId;
      this.catalogEntryId = catalogEntryId;
   }

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
    * @return the catalogEntryId
    */
   public int getCatalogEntryId()
   {
      return catalogEntryId;
   }

   /**
    * @param catalogEntryId the catalogEntryId to set
    */
   public void setCatalogEntryId(int catalogEntryId)
   {
      this.catalogEntryId = catalogEntryId;
   }

   /**
    * @return the programId
    */
   public int getProgramId()
   {
      return programId;
   }

   /**
    * @param programId the programId to set
    */
   public void setProgramId(int programId)
   {
      this.programId = programId;
   }

   /**
    * @return the functionalEntityId
    */
   public int getFunctionalEntityId()
   {
      return functionalEntityId;
   }

   /**
    * @param functionalEntityId the functionalEntityId to set
    */
   public void setFunctionalEntityId(int functionalEntityId)
   {
      this.functionalEntityId = functionalEntityId;
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
    * @return the description
    */
   public String getDescription()
   {
      return description;
   }

   /**
    * @param description the description to set
    */
   public void setDescription(String description)
   {
      this.description = description;
   }

   /**
    * @return the number
    */
   public int getNumber()
   {
      return number;
   }

   /**
    * @param number the number to set
    */
   public void setNumber(int number)
   {
      this.number = number;
   }

   /**
    * @return the productTypeId
    */
   public int getProductTypeId()
   {
      return productTypeId;
   }

   /**
    * @param productTypeId the productTypeId to set
    */
   public void setProductTypeId(int productTypeId)
   {
      this.productTypeId = productTypeId;
   }

   /**
    * @return the mediumTypes
    */
   public String getMediumTypes()
   {
      return mediumTypes;
   }

   /**
    * @param mediumTypes the mediumTypes to set
    */
   public void setMediumTypes(String mediumTypes)
   {
      this.mediumTypes = mediumTypes;
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
      if (!(o instanceof VdVirtualDevice))
         return false;
      final VdVirtualDevice oo = (VdVirtualDevice) o;
      return id == oo.id;
   }
}
