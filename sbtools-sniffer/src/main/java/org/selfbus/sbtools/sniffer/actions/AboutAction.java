package org.selfbus.sbtools.sniffer.actions;

import java.awt.event.ActionEvent;

import org.selfbus.sbtools.common.gui.actions.BasicAction;
import org.selfbus.sbtools.common.gui.misc.ImageCache;
import org.selfbus.sbtools.sniffer.Sniffer;
import org.selfbus.sbtools.sniffer.components.About;
import org.selfbus.sbtools.sniffer.misc.I18n;

/**
 * Show the "about" dialog.
 */
public final class AboutAction extends BasicAction
{
   private static final long serialVersionUID = 7954396766386352325L;

   /**
    * Create an action object.
    */
   public AboutAction()
   {
      super(I18n.getMessage("AboutAction.name"), I18n.getMessage("AboutAction.toolTip"),
            ImageCache.getIcon("icons/help"));
   }

   /**
    * Perform the action.
    */
   @Override
   public void actionEvent(ActionEvent e)
   {
      final About dlg = new About(Sniffer.getInstance().getMainFrame());
      dlg.center();
      dlg.setVisible(true);
   }
}
