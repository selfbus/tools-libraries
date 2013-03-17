package org.selfbus.sbtools.knxcom.application;

/**
 * A response to a {@link GroupValueRead} request.
 * 
 * Use {@link #getApciValue()} to access 1-6 bit data values.
 */
public final class GroupValueResponse extends GenericDataApplication
{
   /**
    * Create an empty group-value response application.
    */
   public GroupValueResponse()
   {
      super(ApplicationType.GroupValue_Response, null);
   }

   /**
    * Create a group-value response application. The data value is set via
    * {@link #setApciValue(int)}.
    * 
    * @param value - the data, up to 6 bit.
    */
   public GroupValueResponse(int value)
   {
      super(ApplicationType.GroupValue_Response, null);
      setApciValue(value);
   }

   /**
    * Create a group-value response application.
    * 
    * @param data - the data.
    */
   public GroupValueResponse(byte[] data)
   {
      super(ApplicationType.GroupValue_Response, data);
   }
}
