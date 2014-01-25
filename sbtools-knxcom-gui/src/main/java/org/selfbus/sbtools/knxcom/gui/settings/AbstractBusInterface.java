package org.selfbus.sbtools.knxcom.gui.settings;

import java.awt.Cursor;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.selfbus.sbtools.knxcom.gui.internal.I18n;
import org.selfbus.sbtools.knxcom.link.Link;
import org.selfbus.sbtools.knxcom.types.LinkMode;

public abstract class AbstractBusInterface extends JPanel
{
   private static final long serialVersionUID = 3860835244990516812L;
   private final JLabel lblTestOutput = new JLabel();

   protected AbstractBusInterface()
   {
      lblTestOutput.setAlignmentY(0.1f);
   }
   
   /**
    * @return The test output component.
    */
   protected JLabel getTestOutputComponent()
   {
      return lblTestOutput;
   }
   
   /**
    * Test the connection to the KNXnet/IP server using the values in the input
    * fields.
    * 
    * @param link - the link to test the connection with.
    */
   protected void testConnection(Link link)
   {
      try
      {
         lblTestOutput.setText(I18n.getMessage("KNXnetBusInterface.TestConnecting"));
         setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
   
         link.open(LinkMode.LinkLayer);
   
         final StringBuffer sb = new StringBuffer();
   
         sb.append("<html><body><h3>");
         sb.append(I18n.getMessage("KNXnetBusInterface.TestOk"));
         sb.append("</h3>");
         sb.append("</body></html>");
   
         lblTestOutput.setText(sb.toString());
   
         link.close();
      }
      catch (Exception e)
      {
         final StringBuffer sb = new StringBuffer();
   
         sb.append("<html><body><h3>");
         sb.append(I18n.getMessage("KNXnetBusInterface.TestFailed"));
         sb.append("</h3><br />");
         sb.append(e.toString());
         sb.append("</body></html>");
   
         lblTestOutput.setText(sb.toString());
      }
      finally
      {
         setCursor(Cursor.getDefaultCursor());
      }
   }

}
