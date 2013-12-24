package org.selfbus.sbtools.knxcom.application;

/**
 * A response to a PropertyValueRead request.
 */
public class PropertyValueResponse extends AbstractPropertyValueData
{
   public PropertyValueResponse()
   {
      super();
   }

   public PropertyValueResponse(int objectId, int propertyId, int startIndex, int count)
   {
      super(objectId, propertyId, startIndex, count);
   }

   @Override
   public ApplicationType getType()
   {
      return ApplicationType.PropertyValue_Response;
   }
}
