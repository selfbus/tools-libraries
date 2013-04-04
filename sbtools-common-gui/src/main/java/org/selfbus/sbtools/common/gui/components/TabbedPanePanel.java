package org.selfbus.sbtools.common.gui.components;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.selfbus.sbtools.common.gui.internal.I18n;

/**
 * A panel that is meant to be added to a tabbed pane.
 */
public abstract class TabbedPanePanel extends JPanel implements CloseableComponent
{
   private static final long serialVersionUID = 1021452407687543962L;

   private boolean modified;

   /**
    * Create a page-frame. Sets the name of the page to the name of the class.
    */
   public TabbedPanePanel()
   {
      setName(getClass().getSimpleName());
   }

   /**
    * Close the panel.
    */
   @Override
   public void close()
   {
      setVisible(false);

      if (!(getParent() instanceof JTabbedPane))
         return;

      final JTabbedPane parent = (JTabbedPane) getParent();
      final int tabIndex = parent.indexOfComponent(this);
      if (tabIndex >= 0)
      {
         parent.remove(tabIndex);
      }
   }

   /**
    * Set the name of the panel.
    */
   @Override
   public void setName(String name)
   {
      super.setName(name);

      if (!(getParent() instanceof JTabbedPane))
         return;

      if (isModified())
         name = '*' + name;

      final JTabbedPane parent = (JTabbedPane) getParent();
      final int tabIndex = parent.indexOfComponent(this);
      if (tabIndex >= 0)
      {
         parent.setTitleAt(tabIndex, name);
         parent.getTabComponentAt(tabIndex).setName(name);
      }
   }

   /**
    * Set the modified flag.
    */
   public void setModified(boolean modified)
   {
      if (this.modified == modified)
         return;

      this.modified = modified;
      setName(getName());
   }

   /**
    * @return true if the page's contents is modified.
    */
   public boolean isModified()
   {
      return modified;
   }

   /**
    * Show a confirmation dialog that asks the user if the changes in the page
    * shall be applied before closing.
    * 
    * @return {@link JOptionPane#YES_OPTION}, {@link JOptionPane#NO_OPTION}, or
    *         {@link JOptionPane#CANCEL_OPTION}.
    */
   protected int confirmClose()
   {
      final String title = I18n.getMessage("CloseablePanel.confirmCloseTitle");
      final String msg = "<html><body width=\"300\">"
            + I18n.getMessage("CloseablePanel.confirmCloseText").replace("\n", "<br />") + "</body></html>";

      return JOptionPane.showConfirmDialog(this, msg, title, JOptionPane.YES_NO_CANCEL_OPTION);
   }
}
