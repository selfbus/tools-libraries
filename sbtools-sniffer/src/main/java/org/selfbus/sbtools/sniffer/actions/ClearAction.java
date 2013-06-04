package org.selfbus.sbtools.sniffer.actions;

import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

import javax.swing.KeyStroke;

import org.selfbus.sbtools.common.gui.actions.BasicAction;
import org.selfbus.sbtools.common.gui.misc.ImageCache;
import org.selfbus.sbtools.sniffer.Sniffer;
import org.selfbus.sbtools.sniffer.internal.I18n;

/**
 * Clear the log.
 */
public final class ClearAction extends BasicAction
{
   private static final long serialVersionUID = 2724691573897193687L;

   /**
    * Create an action object.
    */
   public ClearAction()
   {
      super(I18n.getMessage("ClearAction.name"), I18n.getMessage("ClearAction.toolTip"),
            ImageCache.getIcon("icons/eraser"));

      putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke('L', InputEvent.CTRL_DOWN_MASK));
   }

   /**
    * Perform the action.
    */
   @Override
   public void actionEvent(ActionEvent e)
   {
      Sniffer.getInstance().clear();
   }
}
