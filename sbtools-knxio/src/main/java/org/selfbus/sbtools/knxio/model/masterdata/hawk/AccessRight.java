package org.selfbus.sbtools.knxio.model.masterdata.hawk;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.knxio.internal.I18n;

/**
 * Access right for a resource.
 */
@XmlType
@XmlEnum
public enum AccessRight
{
   /**
    * No access.
    */
   @XmlEnumValue("None")
   NONE,

   /**
    * Configuration time access.
    */
   @XmlEnumValue("Configuration")
   CONFIG,

   /**
    * Manufacturer only access.
    */
   @XmlEnumValue("Manufacturer")
   MANUFACTURER,

   /**
    * Runtime access.
    */
   @XmlEnumValue("Runtime")
   RUNTIME;

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
   private AccessRight()
   {
      this.name = I18n.getMessage("ResourceAccessRight." + name());
   }
}
