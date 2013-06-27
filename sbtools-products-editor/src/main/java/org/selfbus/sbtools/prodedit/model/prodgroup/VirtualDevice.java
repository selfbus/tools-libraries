package org.selfbus.sbtools.prodedit.model.prodgroup;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.common.types.ObjectType;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.common.collect.ArrayListModel;

/**
 * A virtual device.
 */
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VirtualDevice extends Model
{
   private static final long serialVersionUID = 291511597283834248L;

   @XmlAttribute(name = "virtual_device_id", required = true)
   private int id;

   @XmlAttribute(name = "symbol_id")
   private int symbolId;

   @XmlAttribute(name = "catalog_entry_id", required = true)
   public int catalogEntryId;

   @XmlAttribute(name = "program_id")
   private int programId;

   @XmlAttribute(name = "virtual_device_name", required = true)
   private String name = "";

   @XmlAttribute(name = "virtual_device_description")
   private String description;

   @XmlAttribute(name = "functional_entity_id")
   public int functionalEntityId;

   @XmlAttribute(name = "product_type_id")
   private int productTypeId;

   @XmlAttribute(name = "help_file_name")
   private String helpFileName;

   @XmlAttribute(name = "context_id")
   private int contextId;

   @XmlAttribute(name = "pei_program_id")
   private int peiProgramId;

   @XmlAttribute(name = "virtual_device_number")
   private int number;

   @XmlAttribute(name = "medium_types")
   private String mediumTypes;

   @XmlElementWrapper(name = "parameter_types")
   @XmlElement(name = "parameter_type")
   private ArrayListModel<ObjectType> objectTypes = new ArrayListModel<ObjectType>();

   /**
    * Create an empty virtual-device object.
    */
   public VirtualDevice()
   {
   }

   /**
    * Create a virtual-device object.
    * 
    * @param id - the database ID of the object.
    * @param name - the name of the object.
    * @param description - the description.
    * @param functionalEntityId - the functional entity ID.
    * @param catalogEntryId - the catalog entry ID.
    */
   public VirtualDevice(int id, String name, String description, int functionalEntityId,
         int catalogEntryId)
   {
      this.id = id;
      this.name = name;
      this.description = description;
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
    * Set the id.
    * 
    * @param id - the id to set.
    */
   public void setId(int id)
   {
      this.id = id;
   }

   /**
    * @return the ID as string
    */
   public String getIdStr()
   {
      return Integer.toString(id);
   }

   /**
    * @return The functional entity ID.
    */
   public int getFunctionalEntityId()
   {
      return functionalEntityId;
   }

   /**
    * Set the functional entity ID.
    * 
    * @param functionalEntityID - the functional entity ID to set.
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
    * Set the name.
    * 
    * @param name - the name to set.
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
    * Set the description.
    * 
    * @param description - the description to set.
    */
   public void setDescription(String description)
   {
      this.description = description;
   }
   
   /**
    * Set the catalog entry ID.
    * 
    * @param catalogEntryId - the catalog entry ID to set.
    */
   public void setCatalogEntryId(int catalogEntryId)
   {
      this.catalogEntryId = catalogEntryId;
   }

   /**
    * @return the catalog entry ID.
    */
   public int getCatalogEntryId()
   {
      return catalogEntryId;
   }

   /**
    * Set the program ID.
    * 
    * @param program - the program ID to set.
    */
   public void setProgramId(int programId)
   {
      this.programId = programId;
   }

   /**
    * @return the program ID.
    */
   public int getProgramId()
   {
      return programId;
   }

   /**
    * Set the number of the virtual device. This seems to be a version number
    * for the device, starting with 1, and incrementing when the virtual device
    * is changed (by the manufacturer, in the VD_).
    * 
    * @param number - the number to set.
    */
   public void setNumber(int number)
   {
      this.number = number;
   }

   /**
    * Get the number of the virtual device. This seems to be a version number
    * for the device, starting with 1, and incrementing when the virtual device
    * is changed (by the manufacturer, in the VD_).
    * 
    * @return The number of the virtual device.
    */
   public int getNumber()
   {
      return number;
   }

   /**
    * Set the product type ID.
    *
    * @param productTypeId - the id to set.
    */
   public void setProductTypeId(int productTypeId)
   {
      this.productTypeId = productTypeId;
   }

   /**
    * @return The product type ID.
    */
   public int getProductTypeId()
   {
      return productTypeId;
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
      if (!(o instanceof VirtualDevice))
         return false;
      final VirtualDevice oo = (VirtualDevice) o;
      return id == oo.id && catalogEntryId == oo.catalogEntryId && programId == oo.programId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public String toString()
   {
      return name;
   }
}
