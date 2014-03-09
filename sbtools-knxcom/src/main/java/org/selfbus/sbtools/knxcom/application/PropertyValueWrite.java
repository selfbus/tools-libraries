package org.selfbus.sbtools.knxcom.application;

/**
 * A property value write request.
 */
public class PropertyValueWrite extends AbstractPropertyValueData
{
   public PropertyValueWrite()
   {
      super();
   }

   public PropertyValueWrite(int objectId, int propertyId, int startIndex, int count)
   {
      super(objectId, propertyId, startIndex, count);
   }

   @Override
   public ApplicationType getType()
   {
      return ApplicationType.PropertyValue_Write;
   }
}
