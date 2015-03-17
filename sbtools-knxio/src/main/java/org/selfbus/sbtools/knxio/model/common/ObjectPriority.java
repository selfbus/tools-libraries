package org.selfbus.sbtools.knxio.model.common;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.knxio.internal.I18n;

/**
 * Priority of communication objects.
 *
 * The {@link #ordinal() ordinal} gives the numeric value of the priority as it
 * is used in communication and the KNX devices.
 */
@XmlType
@XmlEnum
public enum ObjectPriority implements Labeled
{
   /**
    * System priority. This is the highest priority. It is reserved for system management
    * applications like the ETS.
    */
   @XmlEnumValue("System")
   SYSTEM,

   /**
    * Alarm / urgent priority.
    */
   @XmlEnumValue("Alert")
   ALERT,

   /**
    * High priority.
    */
   @XmlEnumValue("High")
   HIGH,

   /**
    * Low / normal priority. This is the lowest priority.
    */
   @XmlEnumValue("Low")
   LOW;

   private final String label;

   /**
    * @return the id
    */
   public int getId()
   {
      return ordinal();
   }

   /**
    * @return the name
    */
   @Override
   public String getLabel()
   {
      return label;
   }

   /**
    * Constructor.
    */
   private ObjectPriority()
   {
      this.label = I18n.getMessage("ObjectPriority." + name());
   }

   /**
    * @param ordinal - the ordinal to lookup.
    * @return The object for the given ordinal, or null if not found.
    */
   public static ObjectPriority valueOf(int ordinal)
   {
      for (ObjectPriority o : values())
      {
         if (o.ordinal() == ordinal)
            return o;
      }

      return null;
   }
}
