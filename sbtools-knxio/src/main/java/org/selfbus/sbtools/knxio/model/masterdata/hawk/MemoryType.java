package org.selfbus.sbtools.knxio.model.masterdata.hawk;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.knxio.internal.I18n;

/**
 * Memory type.
 */
@XmlType
@XmlEnum
public enum MemoryType
{
   /**
    * EEPROM memory.
    */
   @XmlEnumValue("EEPROM")
   EEPROM;

   private final String name;

   /**
    * @return the name
    */
   public String getName()
   {
      return name;
   }

   /**
    * Constructor.
    */
   private MemoryType()
   {
      this.name = I18n.getMessage("MemoryType." + name());
   }
}
