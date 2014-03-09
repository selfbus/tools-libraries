package org.selfbus.sbtools.common.types;

/**
 * Priority of communication objects.
 * 
 * The {@link #ordinal() ordinal} gives the numeric value of the priority as it
 * is used in communication and the KNX devices.
 */
public enum ObjectPriority
{
   /**
    * System priority. This is the highest priority.
    */
   SYSTEM,

   /**
    * High priority.
    */
   HIGH,

   /**
    * Alarm / urgent priority.
    */
   ALARM,

   /**
    * Low / normal priority. This is the lowest priority.
    */
   LOW;

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
