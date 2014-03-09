package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A specification text.
 */
@XmlType(name = "SpecificationText", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdSpecificationText
{
   @XmlAttribute(name = "spec_text_id", required = true)
   private int id;

   @XmlAttribute(name = "manufacturer_id")
   private int manufacturerId;

   @XmlAttribute(name = "spec_text_name")
   private String name;

   @XmlAttribute(name = "spec_text_numb")
   private String number;

   @XmlAttribute(name = "spec_text_filename")
   private String fileName;

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
      if (!(o instanceof VdSpecificationText))
         return false;
      final VdSpecificationText oo = (VdSpecificationText) o;
      return id == oo.id;
   }
}
