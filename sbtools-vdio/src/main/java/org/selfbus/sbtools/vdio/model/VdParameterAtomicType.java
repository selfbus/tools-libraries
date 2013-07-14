package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * The atomic type of a parameter.
 */
@XmlType(name = "ParameterAtomicType", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdParameterAtomicType
{
   @XmlAttribute(name = "atomic_type_number", required = true)
   private int id;

   @XmlAttribute(name = "atomic_type_name", required = true)
   private String name;

   @XmlAttribute(name = "dispattr", required = true)
   private String display;

   /**
    * Create an atomic type of a parameter.
    */
   public VdParameterAtomicType()
   {
   }

   /**
    * Create an atomic type of a parameter.
    *
    * @param id - the id.
    * @param name - the name.
    * @param display - the display attribute.
    */
   public VdParameterAtomicType(int id, String name, char display)
   {
      this.id = id;
      this.name = name;
      this.display = Character.toString(display);
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
    * @return the display
    */
   public String getDisplay()
   {
      return display;
   }

   /**
    * @param display the display to set
    */
   public void setDisplay(String display)
   {
      this.display = display;
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
      if (!(o instanceof VdParameterAtomicType))
         return false;
      final VdParameterAtomicType oo = (VdParameterAtomicType) o;
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
