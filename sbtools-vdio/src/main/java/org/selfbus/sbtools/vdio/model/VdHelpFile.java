package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A help file.
 */
@XmlType(name = "HelpFile", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdHelpFile
{
   @XmlAttribute(name = "help_file_id", required = true)
   private int id;

   @XmlAttribute(name = "language_id", required = true)
   private int languageId;

   @XmlAttribute(name = "entity_id", required = true)
   private int catalogEntryId;

   @XmlAttribute(name = "help_file_name")
   private String text;

   /**
    * Create a help file.
    */
   public VdHelpFile()
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
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;
      if (!(o instanceof VdHelpFile))
         return false;
      final VdHelpFile oo = (VdHelpFile) o;
      return id == oo.id;
   }
}
