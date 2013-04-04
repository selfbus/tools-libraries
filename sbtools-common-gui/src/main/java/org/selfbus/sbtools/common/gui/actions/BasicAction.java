package org.selfbus.sbtools.common.gui.actions;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JFrame;

import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.selfbus.sbtools.common.gui.components.Dialogs;
import org.selfbus.sbtools.common.gui.internal.I18n;

/**
 * An abstract action class that extracts the mnemonic from the action's name. E.g. "E&xit" will
 * result in the name "Exit" and the mnemonic "x".
 * 
 * @see BasicActionFactory
 */
public abstract class BasicAction extends AbstractAction
{
   private static final long serialVersionUID = -4196730218025617438L;

   /**
    * Create an action with the given name, icon, and tool-tip text.
    * 
    * @param name - The name of the action. A "&" marks the mnemonic.
    * @param toolTipText - The text for the tool-tip.
    * @param icon - The icon for the action.
    */
   public BasicAction(String name, String toolTipText, Icon icon)
   {
      super();

      putValue(Action.SHORT_DESCRIPTION, toolTipText);

      final int idx = name.indexOf('&');
      if (idx >= 0)
      {
         putValue(Action.MNEMONIC_KEY, (int) name.codePointAt(idx + 1));
         name = name.substring(0, idx) + name.substring(idx + 1);
      }

      putValue(Action.NAME, name);
      if (icon != null)
         putValue(Action.SMALL_ICON, icon);
   }

   /**
    * Create an action with the given name and icon.
    * 
    * @param name - The name of the action. A "&" marks the mnemonic.
    * @param icon - The icon for the action.
    */
   public BasicAction(String name, Icon icon)
   {
      this(name, null, icon);
   }

   /**
    * Create an action with the given name.
    * 
    * @param name - The name of the action. A "&" marks the mnemonic.
    */
   public BasicAction(String name)
   {
      this(name, null);
   }

   /**
    * Create an unnamed action.
    */
   public BasicAction()
   {
      super();
   }

   /**
    * @return The action's name.
    */
   public String getName()
   {
      return (String) getValue(Action.NAME);
   }
   
   /**
    * Set the action's name.
    * 
    * @param name - the name to set.
    */
   public void setName(String name)
   {
      putValue(Action.NAME, name);
   }

   /**
    * Set the action's icon.
    * 
    * @param icon - the icon to set.
    */
   public void setIcon(Icon icon)
   {
      putValue(Action.SMALL_ICON, icon);
   }

   /**
    * Set the tool-tip text.
    * 
    * @param toolTipText - The text for the tool-tip.
    */
   public void setToolTipText(String toolTipText)
   {
      putValue(Action.SHORT_DESCRIPTION, toolTipText);
   }

   /**
    * Perform the action.
    */
   public void perform()
   {
      actionPerformed(new ActionEvent(this, 1, ""));
   }

   /**
    * Invoked when an action occurs. If an exception occurs during the action,
    * an error dialog is displayed.
    * 
    * Calls {@link #actionEvent(ActionEvent)}.
    * 
    * @param event - the triggering action event.
    */
   @Override
   public final void actionPerformed(ActionEvent event)
   {
      try
      {
         actionEvent(event);
      }
      catch (RuntimeException e)
      {
         Dialogs.showExceptionDialog(e, I18n.formatMessage("BasicAction.errException", getName()));
      }
   }

   /**
    * Called by {@link #actionPerformed(ActionEvent)} when an action occurs.
    * 
    * @param event - the triggering action event.
    */
   public abstract void actionEvent(ActionEvent event);
   
   /**
    * Invoke a task. This is done in a worker thread and can be used to avoid blocking
    * the foreground task with long running work.
    *
    * @param task - the task to invoke.
    */
   public void invokeLater(Task<?,?> task)
   {
      Application.getInstance().getContext().getTaskService().execute(task);
   }

   /**
    * Get the parent frame of the event. Searches the event source and its parents for a
    * {@link JFrame}. Returns the first frame that is found.
    * 
    * @param event - the event to process
    * 
    * @return The parent {@link JFrame} of the event, or null if no parent frame was found.
    */
   public static JFrame getParentFrame(ActionEvent e)
   {
      for (Object src = e.getSource(); src instanceof Component; src = ((Component) src).getParent())
      {
         if (src instanceof JFrame)
         {
            return (JFrame) src;
         }
      }

      return null;
   }
}
