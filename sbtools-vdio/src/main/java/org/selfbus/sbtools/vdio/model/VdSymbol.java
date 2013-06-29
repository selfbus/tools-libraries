package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * A symbol reference.
 */
@XmlType(name = "Symbol", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdSymbol
{
   @XmlAttribute(name = "symbol_id", required = true)
   private int id;

   @XmlAttribute(name = "symbol_filename")
   private String fileName;

   @XmlAttribute(name = "symbol_name")
   private String name;

   /**
    * Create a symbol object.
    */
   public VdSymbol()
   {
   }

   /**
    * @return the ID
    */
   public int getId()
   {
      return id;
   }

   /**
    * Set the ID
    *
    * @param id - the id to set
    */
   public void setId(int id)
   {
      this.id = id;
   }

   /**
    * @return The name
    */
   public String getName()
   {
      return name;
   }

   /**
    * Set the name.
    *
    * @param name - the name to set
    */
   public void setName(String name)
   {
      this.name = name;
   }
}
