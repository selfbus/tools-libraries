package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Application program attributes.
 */
@XmlType(name = "ApplicationProgramAttributes", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdApplicationProgramAttributes
{
   @XmlAttribute(name = "id", required = true)
   private int id;

   @XmlAttribute(name = "applicationprogramid", required = true)
   private int appProgramId;

   @XmlAttribute(name = "attributename")
   private String name;

   @XmlAttribute(name = "attributevalue")
   private String value;

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
      if (!(o instanceof VdApplicationProgramAttributes))
         return false;
      final VdApplicationProgramAttributes oo = (VdApplicationProgramAttributes) o;
      return id == oo.id;
   }
}
