package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * The BCU type.
 */
@XmlType(name = "BcuType", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdBcuType
{
   @XmlAttribute(name = "bcu_type_number", required = true)
   private int id;

   @XmlAttribute(name = "bcu_type_name", required = true)
   private String name;

   @XmlAttribute(name = "bcu_type_cpu")
   private String cpuName;

   /**
    * Create a BCU-type object.
    */
   public VdBcuType()
   {
   }

   /**
    * Create a BCU-type object.
    *
    * @param id - the id.
    * @param name - the name of the BCU type.
    * @param cpuName - the name of the CPU.
    */
   public VdBcuType(int id, String name, String cpuName)
   {
      this.id = id;
      this.name = name;
      this.cpuName = cpuName;
   }

   /**
    * @return the ID of the BCU type
    */
   public int getId()
   {
      return id;
   }

   /**
    * Set the ID of the BCU type.
    *
    * @param id - the id to set
    */
   public void setId(int id)
   {
      this.id = id;
   }

   /**
    * @return The name of the BCU type.
    */
   public String getName()
   {
      return name;
   }

   /**
    * Set the name of the BCU type.
    *
    * @param name - the name to set
    */
   public void setName(String name)
   {
      this.name = name;
   }

   /**
    * @return The name of the CPU. May be null.
    */
   public String getCpuName()
   {
      return cpuName;
   }

   /**
    * Set the name of the CPU.
    *
    * @param cpuName - the CPU name to set (null allowed).
    */
   public void setCpuName(String cpuName)
   {
      this.cpuName = cpuName;
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
      if (!(o instanceof VdBcuType))
         return false;
      final VdBcuType oo = (VdBcuType) o;
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
