package org.selfbus.sbtools.vdio.internal;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 * Abstract base class for ProductsReader and ProductsWriter.
 */
public abstract class AbstractZipPasswordHandler
{
   protected final JFrame parentFrame;
   protected boolean interactive = true;
   protected String zipPasswd;

   /**
    * Create a password handler.
    */
   public AbstractZipPasswordHandler()
   {
      this(null);
   }

   /**
    * Create a password handler. The parent frame is used for the password dialog.
    * 
    * @param parentFrame - the frame of the window invoking the reader
    */
   public AbstractZipPasswordHandler(JFrame parentFrame)
   {
      this.parentFrame = parentFrame;
   }

   /**
    * @return the ZIP password.
    */
   public String getZipPassword()
   {
      return zipPasswd;
   }

   /**
    * @return True if a ZIP password is present and not empty.
    */
   public boolean hasZipPassword()
   {
      return zipPasswd != null && !zipPasswd.isEmpty();
   }

   /**
    * Set the ZIP password.
    * 
    * @param passwd - the ZIP password to set
    */
   public void setZipPassword(String passwd)
   {
      this.zipPasswd = passwd;
   }

   /**
    * @return True if the reader may ask the user for the ZIP password.
    */
   public boolean isInteractive()
   {
      return interactive;
   }

   /**
    * If interactive is enabled, the reader may ask the user for the ZIP password.
    * 
    * @param enable - the interactive flag to set
    */
   public void setInteractive(boolean enable)
   {
      this.interactive = enable;
   }

   /**
    * Ask the user to enter the ZIP password. The password can be retrieved afterwards with
    * {@link #getZipPassword()}.
    * 
    * @param wrongPassword - if the current password is wrong
    * 
    * @return The entered password, or null if the user canceled the operation.
    */
   protected String askZipPassword(boolean wrongPassword)
   {
      JPasswordField passwordField = new JPasswordField();
      passwordField.setEchoChar('*');
      String msgId = wrongPassword ? "ProductsReader.askZipPasswordWrong" : "ProductsReader.askZipPassword";
      Object[] obj = { I18n.getMessage(msgId), passwordField };
   
      if (JOptionPane.showOptionDialog(parentFrame, obj, I18n.getMessage("ProductsReader.askZipPasswordTitle"),
         JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, obj) == JOptionPane.YES_OPTION)
      {
         return new String(passwordField.getPassword());
      }
   
      return null;
   }

}
