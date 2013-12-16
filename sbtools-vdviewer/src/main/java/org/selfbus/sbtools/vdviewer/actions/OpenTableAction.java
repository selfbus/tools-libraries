package org.selfbus.sbtools.vdviewer.actions;

import java.awt.event.ActionEvent;

import org.selfbus.sbtools.common.gui.actions.BasicAction;
import org.selfbus.sbtools.common.gui.misc.ImageCache;
import org.selfbus.sbtools.vdviewer.VdViewer;
import org.selfbus.sbtools.vdviewer.internal.I18n;

/**
 * Open a tab and show the selected table in it.
 */
public final class OpenTableAction extends BasicAction
{
   private static final long serialVersionUID = -6218240381660607473L;

   /**
    * Create an action object.
    */
   public OpenTableAction()
   {
      super(I18n.getMessage("OpenTableAction.name"), I18n.getMessage("OpenTableAction.toolTip"),
         ImageCache.getIcon("icons/show-table"));
   }

   /**
    * Perform the action.
    */
   @Override
   public void actionEvent(ActionEvent ev)
   {
      VdViewer.getInstance().showSelectedTable();
   }
}
