package org.selfbus.sbtools.knxcom.application;

import org.apache.commons.lang3.text.WordUtils;
import org.selfbus.sbtools.knxcom.application.interfaceobject.InterfaceObjectType;
import org.selfbus.sbtools.knxcom.application.interfaceobject.PropertyId;

/**
 * Abstract base for property applications.
 */
public abstract class AbstractPropertyApplication extends AbstractApplication
{
   protected int objectId;
   protected int propertyId;

   /**
    * Get the property ID as label string.
    */
   protected String getPropertyIdLabel()
   {
      PropertyId ident = PropertyId.valueOf(propertyId);
      if (ident == null)
         return ".property#" + propertyId;

      String lbl = ident.toString();
      return "." + lbl.substring(0, 1).toLowerCase() + lbl.substring(1);
   }

   /**
    * Get the label of a property type that shall be printed before the object name.
    * The default is null.
    */
   protected String getPropertyTypeLabel()
   {
      return null;
   }

   @Override
   public String toString()
   {
      final StringBuffer sb = new StringBuffer();

      sb.append(super.toString()).append(": ");
      
      String typeLabel = getPropertyTypeLabel();
      if (typeLabel != null && !typeLabel.isEmpty())
         sb.append(typeLabel).append(" ");

      InterfaceObjectType objType = InterfaceObjectType.valueOf(objectId);
      if (objType == null)
         sb.append("Object#").append(objectId);
      else
      {
         sb.append(WordUtils.capitalizeFully(objType.toString().replace('_', ' ')).replaceAll(" ", ""));
      }

      return sb.toString();
   }
}
