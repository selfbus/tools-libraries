package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * A functional entity.
 */
@XmlRootElement
@XmlType(name = "FunctionalEntity", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdFunctionalEntity
{
   @XmlAttribute(name = "functional_entity_id", required = true)
   public int id;

   @XmlAttribute(name = "manufacturer_id", required = true)
   public int manufacturerId;

   @XmlAttribute(name = "functional_entity_name", required = true)
   public String name;

   @XmlAttribute(name = "functional_entity_numb", required = true)
   public String shortName;

   @XmlAttribute(name = "functional_entity_description")
   public String description;

   @XmlAttribute(name = "fun_functional_entity_id")
   public Integer parentId;

   @XmlAttribute(name = "help_file_name")
   public String helpFileName;

   @XmlAttribute(name = "context_id")
   public Integer contextId;

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
    * @return the shortName
    */
   public String getShortName()
   {
      return shortName;
   }

   /**
    * @param shortName the shortName to set
    */
   public void setShortName(String shortName)
   {
      this.shortName = shortName;
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
    * @return the parentId
    */
   public Integer getParentId()
   {
      return parentId;
   }

   /**
    * @param parentId the parentId to set
    */
   public void setParentId(Integer parentId)
   {
      this.parentId = parentId;
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
}
