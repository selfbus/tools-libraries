package org.selfbus.sbtools.knxcom.application;

/**
 * A value is being sent to a {@link GroupAddress group address}.
 * 
 * Use {@link #getApciValue()} to access 1-6 bit data values.
 */
public final class GroupValueWrite extends GenericDataApplication
{
   /**
    * Create an empty group-value write application.
    */
   public GroupValueWrite()
   {
      super(ApplicationType.GroupValue_Write, null);
   }

   /**
    * Create a group-value write application. The data value is set via
    * {@link #setApciValue(int)}.
    * 
    * @param value - the data to write, up to 6 bit.
    */
   public GroupValueWrite(int value)
   {
      super(ApplicationType.GroupValue_Write, null);
      setApciValue(value);
   }

   /**
    * Create a group-value write application.
    * 
    * @param data - the data to write.
    */
   public GroupValueWrite(byte[] data)
   {
      super(ApplicationType.GroupValue_Write, data);
   }
}
