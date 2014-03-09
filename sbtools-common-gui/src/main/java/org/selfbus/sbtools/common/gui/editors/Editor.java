package org.selfbus.sbtools.common.gui.editors;

/**
 * Interface for editors.
 */
public interface Editor
{
   /**
    * Set the object that is edited.
    *
    * @param obj - the object to be edited.
    */
   public void setObject(Object obj);

   /**
    * @return The object that is edited.
    */
   public Object getObject();
}
