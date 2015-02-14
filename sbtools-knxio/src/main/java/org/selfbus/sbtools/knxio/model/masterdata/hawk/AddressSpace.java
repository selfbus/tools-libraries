package org.selfbus.sbtools.knxio.model.masterdata.hawk;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.knxio.internal.I18n;

/**
 * Resource address space type.
 */
@XmlType
@XmlEnum
public enum AddressSpace
{
   /**
    * No address space.
    */
   @XmlEnumValue("None")
   NONE,

   /**
    * Standard memory address.
    */
   @XmlEnumValue("StandardMemory")
   STANDARD_MEMORY,

   /**
    * LC filter address.
    */
   @XmlEnumValue("LcFilter")
   LC_FILTER,

   /**
    * LC filter memory address.
    */
   @XmlEnumValue("LcFilterMemory")
   LC_FILTER_MEMORY,

   /**
    * LC slave address.
    */
   @XmlEnumValue("LcSlave")
   LC_SLAVE,

   /**
    * LC slave memory address.
    */
   @XmlEnumValue("LcSlaveMemory")
   LC_SLAVE_MEMORY,

   /**
    * Relative memory address.
    */
   @XmlEnumValue("RelativeMemory")
   RELATIVE_MEMORY,

   /**
    * Constant address.
    */
   @XmlEnumValue("Constant")
   CONSTANT,

   /**
    * A pointer.
    */
   @XmlEnumValue("Pointer")
   POINTER,

   /**
    * A property.
    */
   @XmlEnumValue("Property")
   PROPERTY,

   /**
    * A system property.
    */
   @XmlEnumValue("SystemProperty")
   SYSTEM_PROPERTY,

   /**
    * ADC address.
    */
   @XmlEnumValue("ADC")
   ADC;

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
   private AddressSpace()
   {
      this.name = I18n.getMessage("AddressSpace." + name());
   }
}
