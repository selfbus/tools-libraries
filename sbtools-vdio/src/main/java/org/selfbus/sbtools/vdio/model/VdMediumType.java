package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * The medium type.
 */
@XmlType(name = "MediumType", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdMediumType
{
   @XmlAttribute(name = "medium_type_number", required = true)
   private int id;

   @XmlAttribute(name = "medium_type_name", required = true)
   private String name;

   @XmlAttribute(name = "medium_type_short_name", required = true)
   private String shortName;

   @XmlAttribute(name = "standard_system_id_level")
   private String standardSystemIdLevel;

   @XmlAttribute(name = "system_id_length")
   private Integer systemIdLength;

   @XmlAttribute(name = "dcy_f_individual")
   private Double dcyFindividual;

   @XmlAttribute(name = "dcy_g_individual")
   private Double dcyGindividual;

   @XmlAttribute(name = "dcy_f_group")
   private Double dcyFgroup;

   @XmlAttribute(name = "dcy_g_group")
   private Double dcyGgroup;

   @XmlAttribute(name = "duty_cycle_calculation")
   private Integer dutyCycleCalculation;

   /**
    * Create a medium-type object.
    */
   public VdMediumType()
   {
   }

   /**
    * Create a medium-type object.
    *
    * @param id - the id.
    * @param name - the name.
    * @param shortName - the short name.
    */
   public VdMediumType(int id, String name, String shortName)
   {
      this.id = id;
      this.name = name;
      this.shortName = shortName;
   }

   /**
    * @return the ID
    */
   public int getId()
   {
      return id;
   }

   /**
    * Set the ID.
    *
    * @param id - the id to set
    */
   public void setId(int id)
   {
      this.id = id;
   }

   /**
    * @return The name.
    */
   public String getName()
   {
      return name;
   }

   /**
    * Set the name.
    *
    * @param name - the name to set
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * @return The short name.
    */
   public String getShortName()
   {
      return shortName;
   }

   /**
    * Set the short name.
    *
    * @param shortName - the short name to set
    */
   public void setShortName(String shortName)
   {
      this.shortName = shortName;
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
   public boolean equals(Object o)
   {
      if (o == this)
         return true;
      if (!(o instanceof VdMediumType))
         return false;
      final VdMediumType oo = (VdMediumType) o;
      return id == oo.id;
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
