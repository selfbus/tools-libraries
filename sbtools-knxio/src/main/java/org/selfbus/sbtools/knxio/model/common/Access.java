package org.selfbus.sbtools.knxio.model.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.common.internal.I18n;

/**
 * Access restrictions.
 */
@XmlType
@XmlEnum
public enum Access
{
   /**
    * No access.
    */
   @XmlEnumValue("None")
   NONE,

   /**
    * Read only access.
    */
   @XmlEnumValue("Read")
   READ,

   /**
    * Read/write access.
    */
   @XmlEnumValue("ReadWrite")
   READ_WRITE;

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
   private Access()
   {
      this.name = I18n.getMessage("ParameterAccess." + name());
   }
}
