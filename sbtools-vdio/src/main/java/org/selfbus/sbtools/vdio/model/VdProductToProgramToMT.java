package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Product to Program to MaskType.
 */
@XmlType(name = "ProductToProgramToMT", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdProductToProgramToMT
{
   @XmlAttribute(name = "prod2prog2mt_id", required = true)
   private int id;

   @XmlAttribute(name = "prod2prog_id", required = true)
   private int prod2progId;

   @XmlAttribute(name = "medium_type_number", required = true)
   private int mediumTypeId;

   @XmlAttribute(name = "channel_list_id")
   private Integer channelListId;

   /**
    * Create an object type object.
    */
   public VdProductToProgramToMT()
   {
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
    * @return the prod2progId
    */
   public int getProd2progId()
   {
      return prod2progId;
   }

   /**
    * @param prod2progId the prod2progId to set
    */
   public void setProd2progId(int prod2progId)
   {
      this.prod2progId = prod2progId;
   }

   /**
    * @return the mediumTypeId
    */
   public int getMediumTypeId()
   {
      return mediumTypeId;
   }

   /**
    * @param mediumTypeId the mediumTypeId to set
    */
   public void setMediumTypeId(int mediumTypeId)
   {
      this.mediumTypeId = mediumTypeId;
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
      if (!(o instanceof VdProductToProgramToMT))
         return false;
      final VdProductToProgramToMT oo = (VdProductToProgramToMT) o;
      return id == oo.id;
   }
}
