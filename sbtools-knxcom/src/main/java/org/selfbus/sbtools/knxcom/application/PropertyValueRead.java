package org.selfbus.sbtools.knxcom.application;

/**
 * A property value read request.
 */
public class PropertyValueRead extends AbstractPropertyValue
{
   public PropertyValueRead()
   {
      super();
   }

   public PropertyValueRead(int objectId, int propertyId, int startIndex, int count)
   {
      super(objectId, propertyId, startIndex, count);
   }

   @Override
   public ApplicationType getType()
   {
      return ApplicationType.PropertyValue_Read;
   }
}
