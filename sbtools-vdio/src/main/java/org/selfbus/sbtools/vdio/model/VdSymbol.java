package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.DatatypeConverter;
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

   private byte[] data;
   private byte[] iconData;

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

   /**
    * @return the fileName
    */
   public String getFileName()
   {
      return fileName;
   }

   /**
    * @param fileName the fileName to set
    */
   public void setFileName(String fileName)
   {
      this.fileName = fileName;
   }

   /**
    * @return the data
    */
   public byte[] getData()
   {
      return data;
   }

   /**
    * @param data the data to set
    */
   public void setData(byte[] data)
   {
      this.data = data;
   }

   /**
    * @return the iconData
    */
   public byte[] getIconData()
   {
      return iconData;
   }

   /**
    * @param iconData the iconData to set
    */
   public void setIconData(byte[] iconData)
   {
      this.iconData = iconData;
   }

   @XmlAttribute(name = "symbol_data")
   String getDataStr()
   {
      return data == null ? "" : DatatypeConverter.printHexBinary(data).toLowerCase();
   }

   void setDataStr(String str)
   {
      data = DatatypeConverter.parseHexBinary(str);
   }

   @XmlAttribute(name = "icondata")
   String getIconDataStr()
   {
      return iconData == null ? "" : DatatypeConverter.printHexBinary(iconData).toLowerCase();
   }

   void setIconDataStr(String str)
   {
      iconData = DatatypeConverter.parseHexBinary(str);
   }
}
