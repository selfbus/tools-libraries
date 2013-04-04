package org.selfbus.sbtools.common.gui.components;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * A tool bar that styles its buttons for a nicer look.
 */
public class ToolBar extends JToolBar
{
   private static final long serialVersionUID = -7200515392906890824L;

   /**
    * Create an unnamed horizontal tool bar.
    */
   public ToolBar()
   {
      this(HORIZONTAL);
   }

   /**
    * Create an unnamed tool bar.
    *
    * @param orientation - the orientation of the tool bar.
    */
   public ToolBar(int orientation)
   {
      this(null, orientation);
   }

   /**
    * Create a named horizontal tool bar.
    *
    * @param name - the name of the tool bar.
    */
   public ToolBar(String name)
   {
      this(name, HORIZONTAL);
   }

   /**
    * Create a named tool bar.
    *
    * @param name - the name of the tool bar.
    * @param orientation - the orientation of the tool bar.
    */
   public ToolBar(String name, int orientation)
   {
      super(name, orientation);

      setOpaque(false);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   protected JButton createActionComponent(Action a)
   {
      final JButton btn = super.createActionComponent(a);
      ToolBarButton.useToolBarStyle(btn);
      return btn;
   }
}
