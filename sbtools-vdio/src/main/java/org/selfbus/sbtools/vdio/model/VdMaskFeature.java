package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A mask feature.
 */
@XmlType(name = "MaskFeature", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdMaskFeature
{
   @XmlAttribute(name = "mask_feature_id", required = true)
   private int id;

   @XmlAttribute(name = "mask_feature_name", required = true)
   private String name;

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
      if (!(o instanceof VdMaskFeature))
         return false;
      final VdMaskFeature oo = (VdMaskFeature) o;
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
