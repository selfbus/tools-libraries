package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * The assembler type.
 */
@XmlType(name = "Assembler", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdAssembler
{
   @XmlAttribute(name = "assembler_id", required = true)
   private int id;

   @XmlAttribute(name = "assembler_name", required = true)
   private String name;

   @XmlAttribute(name = "assembler_type", required = true)
   private int type;

   /**
    * Create an assembler type object.
    */
   public VdAssembler()
   {
   }

   /**
    * Create an assembler type object.
    *
    * @param id - the id.
    * @param name - the name.
    * @param type - the type.
    */
   public VdAssembler(int id, String name, int type)
   {
      this.id = id;
      this.name = name;
      this.type = type;
   }

   /**
    * @return the ID.
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
    * @return the type
    */
   public int getType()
   {
      return type;
   }

   /**
    * @param type the type to set
    */
   public void setType(int type)
   {
      this.type = type;
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
      if (!(o instanceof VdAssembler))
         return false;
      final VdAssembler oo = (VdAssembler) o;
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
