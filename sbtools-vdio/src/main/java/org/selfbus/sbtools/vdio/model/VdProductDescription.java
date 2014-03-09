package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A product description.
 */
@XmlType(name = "ProductDescription", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdProductDescription
{
   @XmlAttribute(name = "product_description_id", required = true)
   private int id;

   @XmlAttribute(name = "catalog_entry_id", required = true)
   private int catalogEntryId;

   @XmlAttribute(name = "product_description_text")
   private String text;

   @XmlAttribute(name = "display_order", required = true)
   private int order;

   @XmlAttribute(name = "language_id", required = true)
   private int languageId;

   /**
    * Create a product description.
    */
   public VdProductDescription()
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
    * @return the catalogEntryId
    */
   public int getCatalogEntryId()
   {
      return catalogEntryId;
   }

   /**
    * @param catalogEntryId the catalogEntryId to set
    */
   public void setCatalogEntryId(int catalogEntryId)
   {
      this.catalogEntryId = catalogEntryId;
   }

   /**
    * @return the text
    */
   public String getText()
   {
      return text;
   }

   /**
    * @param text the text to set
    */
   public void setText(String text)
   {
      this.text = text;
   }

   /**
    * @return the order
    */
   public int getOrder()
   {
      return order;
   }

   /**
    * @param order the order to set
    */
   public void setOrder(int order)
   {
      this.order = order;
   }

   /**
    * @return the languageId
    */
   public int getLanguageId()
   {
      return languageId;
   }

   /**
    * @param languageId the languageId to set
    */
   public void setLanguageId(int languageId)
   {
      this.languageId = languageId;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;
      if (!(o instanceof VdProductDescription))
         return false;
      final VdProductDescription oo = (VdProductDescription) o;
      return id == oo.id;
   }
}
