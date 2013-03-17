package org.selfbus.sbtools.knxcom.telegram;


/**
 * The priority of an EIB telegram.
 */
public enum Priority
{
   /**
    * System priority. This is the highest priority level.
    */
   SYSTEM(0),

   /**
    * Urgent priority.
    */
   URGENT(1),

   /**
    * Normal priority.
    */
   NORMAL(2),

   /**
    * Low priority. This is the lowest priority level.
    */
   LOW(3);

   /**
    * The numerical id of the priority.
    */
   public final int id;

   /**
    * @return the priority with the given id.
    * @throws InvalidDataException 
    */
   static public Priority valueOf(int id) throws InvalidDataException
   {
      for (Priority e: values())
         if (e.id==id) return e;

      throw new InvalidDataException("None telegram-priority", id);
   }

   // Internal constructor
   private Priority(int id)
   {
      this.id = id;
   }
}
