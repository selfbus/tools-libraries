package org.selfbus.sbtools.common.gui.actions;

import javax.swing.Action;

/**
 * Interface for action factories.
 */
public interface ActionFactory
{

   /**
    * Get an action object. The actions are singleton objects.
    * 
    * @param id - the id of the action.
    * @return The action object.
    * 
    * @throws ActionCreationException if the creation of the action object fails.
    */
   public abstract Action getAction(String id);
}
