package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Mapping from mask to mask feature.
 */
@XmlType(name = "MaskToMaskFeature", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdMaskToMaskFeature
{
   @XmlAttribute(name = "mask_to_mask_feature_id", required = true)
   private int id;

   @XmlAttribute(name = "mask_id", required = true)
   private int maskId;

   @XmlAttribute(name = "mask_feature_id", required = true)
   private int maskFeatureId;

   @XmlAttribute(name = "mask_feature_value")
   private Integer value;

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
      if (!(o instanceof VdMaskToMaskFeature))
         return false;
      final VdMaskToMaskFeature oo = (VdMaskToMaskFeature) o;
      return id == oo.id;
   }
}
