package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.Validate;

/**
 * A language.
 */
@XmlType(name = "Language", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdLanguage
{
   @XmlAttribute(name = "language_id")
   private int id;

   @XmlAttribute(name = "language_name")
   private String name;

   @XmlAttribute(name = "database_language")
   private int databaseLanguage;

   /**
    * Create a language.
    */
   public VdLanguage()
   {
   }

   /**
    * Create a language.
    * 
    * @param id - the ID
    * @param name - the name
    */
   public VdLanguage(int id, String name)
   {
      this.id = id;
      this.name = name;
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
      Validate.notNull(name);
      this.name = name;
   }

   /**
    * @return the databaseLanguage
    */
   public int getDatabaseLanguage()
   {
      return databaseLanguage;
   }

   /**
    * @param databaseLanguage the databaseLanguage to set
    */
   public void setDatabaseLanguage(int databaseLanguage)
   {
      this.databaseLanguage = databaseLanguage;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (!(o instanceof VdLanguage))
         return false;

      VdLanguage oo = (VdLanguage) o;
      return id == oo.id;
   }
}
