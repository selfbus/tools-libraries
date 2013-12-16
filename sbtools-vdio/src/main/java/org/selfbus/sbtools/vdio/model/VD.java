package org.selfbus.sbtools.vdio.model;

import java.io.File;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

/**
 * A products file.
 */
@XmlType(propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VD
{
   @XmlAttribute
   public String name;

   @XmlAttribute
   public String comment;

   @XmlAttribute
   public String created;

   @XmlAttribute
   public String version;

   @XmlElementWrapper(name = "manufacturer")
   @XmlElement(name = "manufacturer")
   public List<VdManufacturer> manufacturers;

   @XmlElementWrapper(name = "functional_entity")
   @XmlElement(name = "functional_entity")
   public List<VdFunctionalEntity> functionalEntities;

   @XmlElementWrapper(name = "bcu_type")
   @XmlElement(name = "bcu_type")
   public List<VdBcuType> bcuTypes;

   @XmlElementWrapper(name = "symbol")
   @XmlElement(name = "symbol")
   public List<VdSymbol> symbols;

   @XmlElementWrapper(name = "hw_product")
   @XmlElement(name = "hw_product")
   public List<VdHwProduct> products;

   @XmlElementWrapper(name = "catalog_entry")
   @XmlElement(name = "catalog_entry")
   public List<VdCatalogEntry> catalogEntries;

   @XmlElementWrapper(name = "medium_type")
   @XmlElement(name = "medium_type")
   public List<VdMediumType> mediumTypes;

   @XmlElementWrapper(name = "mask")
   @XmlElement(name = "mask")
   public List<VdMask> masks;

   @XmlElementWrapper(name = "assembler")
   @XmlElement(name = "assembler")
   public List<VdAssembler> assemblers;

   @XmlElementWrapper(name = "application_program")
   @XmlElement(name = "application_program")
   public List<VdApplicationProgram> programs;

   @XmlElementWrapper(name = "virtual_device")
   @XmlElement(name = "virtual_device")
   public List<VdVirtualDevice> virtualDevices;

   @XmlElementWrapper(name = "mask_entry")
   @XmlElement(name = "mask_entry")
   public List<VdMaskEntry> maskEntries;

   @XmlElementWrapper(name = "device_info")
   @XmlElement(name = "device_info")
   public List<VdDeviceInfo> deviceInfos;

   @XmlElementWrapper(name = "ete_language")
   @XmlElement(name = "ete_language")
   public List<VdLanguage> languages;

   @XmlElementWrapper(name = "device_info_value")
   @XmlElement(name = "device_info_value")
   public List<VdDeviceInfoValue> deviceInfoValues;

   @XmlElementWrapper(name = "parameter_atomic_type")
   @XmlElement(name = "parameter_atomic_type")
   public List<VdParameterAtomicType> parameterAtomicTypes;

   @XmlElementWrapper(name = "parameter_type")
   @XmlElement(name = "parameter_type")
   public List<VdParameterType> parameterTypes;

   @XmlElementWrapper(name = "parameter_list_of_values")
   @XmlElement(name = "parameter_list_of_values")
   public List<VdParameterValue> parameterValues;

   @XmlElementWrapper(name = "parameter")
   @XmlElement(name = "parameter")
   public List<VdParameter> parameters;

   @XmlElementWrapper(name = "object_type")
   @XmlElement(name = "object_type")
   public List<VdObjectType> objectTypes;

   @XmlElementWrapper(name = "object_priority")
   @XmlElement(name = "object_priority")
   public List<VdObjectPriority> objectPriorities;

   @XmlElementWrapper(name = "communication_object")
   @XmlElement(name = "communication_object")
   public List<VdCommunicationObject> communicationObjects;

   @XmlElementWrapper(name = "text_attribute")
   @XmlElement(name = "text_attribute")
   public List<VdTextAttribute> textAttributes;

   @XmlElementWrapper(name = "product_description")
   @XmlElement(name = "product_description")
   public List<VdProductDescription> productDescriptions;

   @XmlElementWrapper(name = "help_file")
   @XmlElement(name = "help_file")
   public List<VdHelpFile> helpFiles;

   @XmlElementWrapper(name = "product_to_program")
   @XmlElement(name = "product_to_program")
   public List<VdProductToProgram> product2programs;

   @XmlElementWrapper(name = "product_to_program_to_mt")
   @XmlElement(name = "product_to_program_to_mt")
   public List<VdProductToProgramToMT> prod2prog2mts;

   @XmlElementWrapper(name = "program_description")
   @XmlElement(name = "program_description")
   public List<VdProgramDescription> programDescriptions;

   @XmlElementWrapper(name = "device_object")
   @XmlElement(name = "device_object")
   public List<VdDeviceObject> deviceObjects;

   @XmlElementWrapper(name = "device_parameter")
   @XmlElement(name = "device_parameter")
   public List<VdDeviceParameter> deviceParameters;

   private File file;

   /**
    * @return the file of the VD.
    */
   public File getFile()
   {
      return file;
   }

   /**
    * Set the file of the VD.
    *
    * @param file - the file to set
    */
   public void setFile(File file)
   {
      this.file = file;
   }

   /**
    * Find a catalog entry by ID.
    * 
    * @param id - the ID to find
    * @return The found object or null if not found.
    */
   public VdCatalogEntry findCatalogEntry(int id)
   {
      for (VdCatalogEntry e : catalogEntries)
      {
         if (e.getId() == id)
            return e;
      }
      
      return null;
   }

   /**
    * Find an application program by ID.
    * 
    * @param id - the ID to find
    * @return The found object or null if not found.
    */
   public VdApplicationProgram findProgram(int id)
   {
      if (programs == null)
         return null;

      for (VdApplicationProgram e : programs)
      {
         if (e.getId() == id)
            return e;
      }
      
      return null;
   }

   /**
    * Find a symbol by ID.
    * 
    * @param id - the ID to find
    * @return The found object or null if not found.
    */
   public VdSymbol findSymbol(int id)
   {
      if (symbols == null)
         return null;

      for (VdSymbol e : symbols)
      {
         if (e.getId() == id)
            return e;
      }
      
      return null;
   }
}
