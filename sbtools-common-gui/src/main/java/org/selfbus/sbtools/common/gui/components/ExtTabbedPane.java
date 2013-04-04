package org.selfbus.sbtools.common.gui.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;

/**
 * An extended tabbed pane widget, that adds icons and/or buttons to the tabs.
 */
public class ExtTabbedPane extends JTabbedPane
{
   private static final long serialVersionUID = 6376413536236202913L;

   /**
    * Creates an empty <code>ExtTabbedPane</code> with a default tab placement
    * of <code>JTabbedPane.TOP</code>.
    *
    * @see #addTab
    */
   public ExtTabbedPane()
   {
      this(JTabbedPane.TOP);
   }

   /**
    * Creates an empty <code>TabbedPane</code> with the specified tab placement
    * of either: <code>JTabbedPane.TOP</code>, <code>JTabbedPane.BOTTOM</code>,
    * <code>JTabbedPane.LEFT</code>, or <code>JTabbedPane.RIGHT</code>.
    *
    * @param tabPlacement the placement for the tabs relative to the content
    * @see #addTab
    */
   public ExtTabbedPane(int tabPlacement)
   {
      this(tabPlacement, JTabbedPane.WRAP_TAB_LAYOUT);
   }

   /**
    * Creates an empty <code>TabbedPane</code> with the specified tab placement
    * and tab layout policy. Tab placement may be either:
    * <code>JTabbedPane.TOP</code>, <code>JTabbedPane.BOTTOM</code>,
    * <code>JTabbedPane.LEFT</code>, or <code>JTabbedPane.RIGHT</code>. Tab
    * layout policy may be either: <code>JTabbedPane.WRAP_TAB_LAYOUT</code> or
    * <code>JTabbedPane.SCROLL_TAB_LAYOUT</code>.
    *
    * @param tabPlacement the placement for the tabs relative to the content
    * @param tabLayoutPolicy the policy for laying out tabs when all tabs will
    *           not fit on one run
    * @exception IllegalArgumentException if tab placement or tab layout policy
    *               are not one of the above supported values
    * @see #addTab
    * @since 1.4
    */
   public ExtTabbedPane(int tabPlacement, int tabLayoutPolicy)
   {
      super(tabPlacement, tabLayoutPolicy);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void addTab(String title, Component component)
   {
      super.addTab(title, component);

      final ExtTab tab = new ExtTab(title, true);
      tab.addCloseListener(closeListener);

      setTabComponentAt(getTabCount() - 1, tab);
   }

   /**
    * Action listener that gets called when the close button of a tab is
    * clicked.
    */
   private final ActionListener closeListener = new ActionListener()
   {
      @Override
      public void actionPerformed(ActionEvent e)
      {
         if (!(e.getSource() instanceof ExtTab))
            return;

         final ExtTab tab = (ExtTab) e.getSource();

         final int idx = indexOfTabComponent(tab);
         if (idx < 0)
            return;

         final Component c = getComponentAt(idx);
         if (c instanceof CloseableComponent)
            ((CloseableComponent) c).close();
         else remove(idx);
      }
   };
}
