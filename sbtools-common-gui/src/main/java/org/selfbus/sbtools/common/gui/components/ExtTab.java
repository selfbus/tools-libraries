package org.selfbus.sbtools.common.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.basic.BasicButtonUI;

import org.selfbus.sbtools.common.gui.misc.ImageCache;

/**
 * A tab of a tab-page that can contain an icon and a close button.
 */
class ExtTab extends JPanel
{
   private static final long serialVersionUID = 600126632738870853L;
   private static final Icon CLOSE_BUTTON_HIGHLIGHT_ICON = ImageCache.getIcon("tab-close-highlite");
   private static final Icon CLOSE_BUTTON_DIMMED_ICON = ImageCache.getIcon("tab-close-dimmed");

   private final CloseButton closeButton;
   private final JLabel lblTitle;
   private final EventListenerList listenerList = new EventListenerList();

   /**
    * Create a closable tab object.
    *
    * @param title - the title of the tab.
    * @param icon - an optional icon.
    * @param closable - true if the tab shall have a close button.
    */
   public ExtTab(String title, Icon icon, boolean closable)
   {
      super(new BorderLayout(4, 0));
      setOpaque(false);

      lblTitle = new JLabel(title);
      add(lblTitle, BorderLayout.CENTER);

      if (icon != null)
         add(new JLabel(icon), BorderLayout.WEST);

      if (closable)
      {
         closeButton = new CloseButton();
         add(closeButton, BorderLayout.EAST);

         closeButton.addActionListener(new ActionListener()
         {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               fireCloseButtonClicked(e);
            }
         });
      }
      else
      {
         closeButton = null;
      }
   }

   /**
    * Create a closable tab object.
    *
    * @param title - the title of the tab.
     * @param closable - true if the tab shall have a close button.
    */
   public ExtTab(String title, boolean closable)
   {
      this(title, null, closable);
   }

   /**
    * Add a close button {@link ActionListener action listener} that gets called
    * when the close button is clicked.
    * 
    * @param l - the action listener.
    */
   public void addCloseListener(ActionListener l)
   {
      listenerList.add(ActionListener.class, l);
   }

   /**
    * Remove a close button {@link ActionListener action listener} from the tab.
    * 
    * @param l - the action listener.
    */
   public void removeCloseListener(ActionListener l)
   {
      listenerList.remove(ActionListener.class, l);
   }

   /**
    * Notifies all listeners that have registered interest for notification on
    * when the close button is clicked.
    * 
    * @param e - the action event.
    */
   protected void fireCloseButtonClicked(ActionEvent e)
   {
      e.setSource(ExtTab.this);

      // Process the listeners last to first, notifying
      // those that are interested in this event
      final Object[] listeners = listenerList.getListenerList();
      for (int i = listeners.length - 2; i >= 0; i -= 2)
      {
         if (listeners[i] == ActionListener.class)
            ((ActionListener) listeners[i + 1]).actionPerformed(e);
      }
   }

   /**
    * Set the name of the tab. This is also the displayed label.
    * 
    * @param name - the name of the tab.
    */
   @Override
   public void setName(String name)
   {
      super.setName(name);
      lblTitle.setText(name);
   }

   /**
    * Internal class of {@link ExtTab} for the close button.
    */
   private class CloseButton extends JButton
   {
      private static final long serialVersionUID = -1335643238071282239L;

      /**
       * Create a close button.
       */
      CloseButton()
      {
         int size = 17;
         setPreferredSize(new Dimension(size, size));
         // setToolTipText(I18n.getMessage("ExtTab.CloseTip"));
         setUI(new BasicButtonUI());
         setContentAreaFilled(false);
         setFocusable(false);
         setBorderPainted(false);
         setIcon(CLOSE_BUTTON_DIMMED_ICON);
         setRolloverEnabled(true);

         addMouseListener(new MouseAdapter()
         {
            @Override
            public void mouseEntered(MouseEvent e)
            {
               closeButton.setIcon(CLOSE_BUTTON_HIGHLIGHT_ICON);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
               closeButton.setIcon(CLOSE_BUTTON_DIMMED_ICON);
            }
         });
      }
   }
}
