package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A program description.
 */
@XmlType(name = "ProgramDescription", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdProgramDescription
{
   @XmlAttribute(name = "program_description_id", required = true)
   private int id;

   @XmlAttribute(name = "program_id", required = true)
   private int programId;

   @XmlAttribute(name = "text")
   private String text;

   @XmlAttribute(name = "display_order", required = true)
   private int order;

   @XmlAttribute(name = "language_id", required = true)
   private int languageId;

   /**
    * Create a product description.
    */
   public VdProgramDescription()
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
    * @return the programId
    */
   public int getProgramId()
   {
      return programId;
   }

   /**
    * @param programId the programId to set
    */
   public void setProgramId(int programId)
   {
      this.programId = programId;
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
      if (!(o instanceof VdProgramDescription))
         return false;
      final VdProgramDescription oo = (VdProgramDescription) o;
      return id == oo.id;
   }
}
