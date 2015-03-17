package org.selfbus.sbtools.knxio.model.masterdata;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.knxio.internal.I18n;

/**
 * Types of management models.
 */
@XmlType
@XmlEnum
public enum ManagementModel
{
   /**
    * None
    */
   @XmlEnumValue("None")
   NONE,

   /**
    * BCU 1
    */
   @XmlEnumValue("Bcu1")
   BCU1,

   /**
    * BCU 2
    */
   @XmlEnumValue("Bcu2")
   BCU2,

   /**
    * Property based
    */
   @XmlEnumValue("PropertyBased")
   PROPERTY_BASED,

   /**
    * Bim M112
    */
   @XmlEnumValue("BimM112")
   BIM_M112,

   /**
    * System B
    */
   @XmlEnumValue("SystemB")
   SYSTEM_B;

   private final String label;

   /**
    * @return The Id
    */
   public int getId()
   {
      return ordinal();
   }

   /**
    * @return The label
    */
   public String getLabel()
   {
      return label;
   }

   /*
    * Constructor.
    */
   private ManagementModel()
   {
      this.label = I18n.getMessage("ManagementModel." + name());
   }
}
