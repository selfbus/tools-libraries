package org.selfbus.sbtools.common.gui.components;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * Base class for custom dialog windows.
 */
public class Dialog extends JDialog
{
   private static final long serialVersionUID = 2554156128804430881L;
   private final JPanel bodyPane;
   private final Box buttonBox;
   private boolean accepted = false;
   private ActionListener acceptListener, rejectListener;

   /**
    * Button role for accept/ok buttons. Buttons with this role call
    * {@link #accept} when clicked.
    */
   public static final int ACCEPT = 1;

   /**
    * Button role for reject/cancel buttons. Buttons with this role call
    * {@link #reject} when clicked.
    */
   public static final int REJECT = 0;

   /**
    * Create a dialog window.
    *
    * @param owner the <code>Window</code> from which the dialog is displayed or
    *           <code>null</code> if this dialog has no owner
    * @param modalityType specifies whether dialog blocks input to other windows
    *           when shown. <code>null</code> value and unsupported modality
    *           types are equivalent to <code>MODELESS</code>
    * @exception HeadlessException when
    *               <code>GraphicsEnvironment.isHeadless()</code> returns
    *               <code>true</code>
    * @see java.awt.Dialog.ModalityType
    * @see java.awt.Dialog#setModal
    * @see java.awt.Dialog#setModalityType
    * @see java.awt.GraphicsEnvironment#isHeadless
    * @see JComponent#getDefaultLocale
    */
   public Dialog(Window owner, ModalityType modalityType)
   {
      super(owner, modalityType);

      setMinimumSize(new Dimension(200, 150));
      setLayout(new BorderLayout());

      bodyPane = new JPanel();
      bodyPane.setOpaque(true);
      bodyPane.setLayout(new BorderLayout());
      bodyPane.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
      add(bodyPane, BorderLayout.CENTER);

      buttonBox = Box.createHorizontalBox();
      buttonBox.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
      buttonBox.add(Box.createHorizontalGlue());
      add(buttonBox, BorderLayout.SOUTH);
   }

   /**
    * Create a modeless dialog window.
    *
    * @param owner the <code>Window</code> from which the dialog is displayed or
    *           <code>null</code> if this dialog has no owner
    * @exception HeadlessException when
    *               <code>GraphicsEnvironment.isHeadless()</code> returns
    *               <code>true</code>
    * @see java.awt.GraphicsEnvironment#isHeadless
    * @see JComponent#getDefaultLocale
    */
   public Dialog(Window owner)
   {
      this(owner, ModalityType.MODELESS);
   }

   /**
    * @return The body pane of this dialog.
    */
   public Container getBodyPane()
   {
      return bodyPane;
   }

   /**
    * Add a button to the dialog's buttons.
    * 
    * @param button - the button to add.
    */
   public void addButton(JButton button)
   {
      button.setPreferredSize(new Dimension(100, button.getPreferredSize().height));

      if (buttonBox.getComponentCount() > 0)
         buttonBox.add(Box.createRigidArea(new Dimension(8, 0)));

      buttonBox.add(button);
   }

   /**
    * Add a button to the dialog's buttons. When clicked, the button will call
    * the dialog's standard action, depending on the button's role.
    *
    * If the button's role is {@link #ACCEPT}, the {@link #accept} method is
    * called when the button is clicked. If the button's role is {@link #REJECT}
    * then the {@link #reject} method is called when the button is clicked.
    *
    * @param button - The button to add.
    * @param role - The button's role: {@link #ACCEPT}, {@link #REJECT}.
    */
   public void addButton(JButton button, int role)
   {
      addButton(button);

      if (role == ACCEPT)
      {
         button.addActionListener(new ActionListener()
         {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               if (acceptListener != null)
                  acceptListener.actionPerformed(e);

               accepted = true;
               accept();
               dispose();
            }
         });
      }
      else if (role == REJECT)
      {
         button.addActionListener(new ActionListener()
         {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               if (rejectListener != null)
                  rejectListener.actionPerformed(e);

               accepted = false;
               reject();
               dispose();
            }
         });
      }
      else
      {
         throw new RuntimeException("Invalid button role \"" + role + "\"");
      }
   }

   /**
    * Center the dialog in the owning window or on the screen, if the dialog
    * is not owned by a window.
    */
   public void center()
   {
      final Window owner = getOwner();
      Dimension sz;
      Point pos;

      if (owner == null)
      {
         sz = Toolkit.getDefaultToolkit().getScreenSize();
         pos = new Point();
      }
      else
      {
         sz = owner.getSize();
         pos = owner.getLocation();
      }

      setLocation(pos.x + ((sz.width - getWidth()) >> 1), pos.y + ((sz.height - getHeight()) >> 1));
   }

   /**
    * Set the accepted flag. Called automatically.
    * 
    * @param accepted - true if accepted
    *
    * @See isAccepted
    */
   public final void setAccepted(boolean accepted)
   {
      this.accepted = accepted;
   }

   /**
    * @return True if the dialog was accepted.
    */
   public final boolean isAccepted()
   {
      return accepted;
   }

   /**
    * Accept the dialog. The default implementation is empty.
    */
   public void accept()
   {
   }

   /**
    * Reject the dialog. The default implementation is empty.
    */
   public void reject()
   {
   }

   /**
    * Set the accept listener that gets called when the user clicks
    * the OK button.
    *
    * @param acceptListener - the accept listener to set
    */
   public void setAcceptListener(ActionListener acceptListener)
   {
      this.acceptListener = acceptListener;
   }

   /**
    * Set the reject listener that gets called when the user clicks
    * the CANCEL button.
    *
    * @param rejectListener - the reject listener to set
    */
   public void setRejectListener(ActionListener rejectListener)
   {
      this.rejectListener = rejectListener;
   }
}
