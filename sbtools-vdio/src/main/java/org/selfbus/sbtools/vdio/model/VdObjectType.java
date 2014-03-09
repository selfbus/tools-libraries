package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * An object type.
 */
@XmlType(name = "ObjectType", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdObjectType
{
   @XmlAttribute(name = "object_type_code", required = true)
   private int id;

   @XmlAttribute(name = "object_type_name", required = true)
   private String name;

   @XmlAttribute(name = "length_in_bit")
   private Integer lengthInBit;

   /**
    * Create an object type object.
    */
   public VdObjectType()
   {
   }

   /**
    * Create an object type object.
    *
    * @param id - the id.
    * @param name - the name.
    */
   public VdObjectType(int id, String name)
   {
      this.id = id;
      this.name = name;
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
      if (!(o instanceof VdObjectType))
         return false;
      final VdObjectType oo = (VdObjectType) o;
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
