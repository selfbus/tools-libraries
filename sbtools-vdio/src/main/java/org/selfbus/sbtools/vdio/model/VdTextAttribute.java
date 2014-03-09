package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A text attribute.
 */
@XmlType(name = "TextAttribute", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdTextAttribute
{
   @XmlAttribute(name = "text_attribute_id", required = true)
   private int id;

   @XmlAttribute(name = "language_id", required = true)
   private int languageId;

   @XmlAttribute(name = "column_id", required = true)
   private int columnId;

   @XmlAttribute(name = "entity_id", required = true)
   private int entityId;

   @XmlAttribute(name = "text_attribute_text")
   private String text;

   /**
    * Create an assembler type object.
    */
   public VdTextAttribute()
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
    * @return the columnId
    */
   public int getColumnId()
   {
      return columnId;
   }

   /**
    * @param columnId the columnId to set
    */
   public void setColumnId(int columnId)
   {
      this.columnId = columnId;
   }

   /**
    * @return the entityId
    */
   public int getEntityId()
   {
      return entityId;
   }

   /**
    * @param entityId the entityId to set
    */
   public void setEntityId(int entityId)
   {
      this.entityId = entityId;
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
      if (!(o instanceof VdTextAttribute))
         return false;
      final VdTextAttribute oo = (VdTextAttribute) o;
      return id == oo.id;
   }
}
