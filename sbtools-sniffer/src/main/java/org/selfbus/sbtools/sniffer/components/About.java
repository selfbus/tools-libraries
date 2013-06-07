package org.selfbus.sbtools.sniffer.components;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Window;
import java.io.IOException;
import java.net.URL;
import java.text.MessageFormat;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import org.selfbus.sbtools.common.gui.components.Dialog;
import org.selfbus.sbtools.common.gui.components.Dialogs;
import org.selfbus.sbtools.sniffer.misc.I18n;

/**
 * An "about" dialog.
 */
public class About extends Dialog
{
   private static final long serialVersionUID = -2792850060628403138L;

   /**
    * Create the "about" dialog.
    */
   public About(Window owner)
   {
      super(owner);

      setTitle(I18n.getMessage("About.title"));
      setSize(600, 600);

      final Container body = getBodyPane();
      body.setLayout(new BorderLayout());

      final String licenseUrl = "http://www.gnu.org/licenses";
      final String projectUrl = "http://www.selfbus.org";

      final StringBuilder sb = new StringBuilder();

      sb.append("<html><body style=\"background-color:transparent;\">");
      sb.append("<h1>").append(htmlEncode(I18n.getMessage("About.productName"))).append("</h1>");

      final Properties props = getManifestProperties();
      final String version = props.getProperty("Version");
      if (version != null)
      {
         sb.append(MessageFormat.format(htmlEncode(I18n.getMessage("About.version")), version, null));
         sb.append("<br /><br />");
      }

      sb.append("<i>").append(htmlEncode(I18n.getMessage("About.copyright"))).append("</i><br /><br />");

      sb.append(I18n.getMessage("About.details")).append("<br /><br />");

      sb.append(MessageFormat.format(htmlEncode(I18n.getMessage("About.website")), "<a href=\"" + projectUrl + "\">"
            + projectUrl + "</a>", null));
      sb.append("<br /><br />");

      sb.append(htmlEncode(I18n.getMessage("About.license"))).append("<br /><br />");
      sb.append(htmlEncode(I18n.getMessage("About.warranty"))).append("<br /><br />");

      sb.append(MessageFormat.format(htmlEncode(I18n.getMessage("About.obtainLicense")), "<a href=\"" + licenseUrl
            + "\">" + licenseUrl + "</a>", null));

      sb.append("</body></html>");

      final JEditorPane jep = new JEditorPane("text/html", sb.toString());
      jep.setOpaque(false);
      jep.setEditable(false);
      jep.setBorder(BorderFactory.createEmptyBorder(10, 20, 4, 20));
      body.add(new JScrollPane(jep), BorderLayout.CENTER);

      addButton(new JButton(I18n.getMessage("Button.close")), Dialog.ACCEPT);

      jep.addHyperlinkListener(new HyperlinkListener()
      {
         @Override
         public void hyperlinkUpdate(HyperlinkEvent e)
         {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
            {
               try
               {
                  java.awt.Desktop.getDesktop().browse(e.getURL().toURI());
               }
               catch (Exception ex)
               {
                  Dialogs.showExceptionDialog(ex, I18n.getMessage("About.errStartBrowser"));
               }
            }
         }
      });
   }

   /**
    * Encode special characters in str for HTML
    */
   private String htmlEncode(final String str)
   {
      return str.replace("&", "&amp;").replace("'", "&rsquo;").replace("\"", "&quot;").replace("<", "&lt;")
         .replace(">", "&gt;").replace("\n", "<br />");
   }


   /**
    * @return the manifest's properties of the main JAR of FTS.
    */
   public Properties getManifestProperties()
   {
      Properties manifestProps = new Properties();

      try
      {
         final String classContainer = getClass().getProtectionDomain().getCodeSource().getLocation().toString();

         if (classContainer.toLowerCase().endsWith(".jar"))
         {
            final URL manifestUrl = new URL("jar:" + classContainer + "!/META-INF/MANIFEST.MF");
            manifestProps.load(manifestUrl.openStream());
         }
      }
      catch (IOException e)
      {
         Dialogs.formatExceptionMessage(e, "Failed to load the manifest from the application's jar");
      }

      return manifestProps;
   }
}
