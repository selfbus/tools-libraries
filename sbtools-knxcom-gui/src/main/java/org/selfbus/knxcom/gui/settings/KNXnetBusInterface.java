package org.selfbus.knxcom.gui.settings;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.selfbus.knxcom.gui.internal.I18n;
import org.selfbus.sbtools.common.Config;
import org.selfbus.sbtools.knxcom.link.netip.KNXnetLink;

/**
 * A widget for configuring a KNXnet/IP bus interface connection.
 */
public class KNXnetBusInterface extends AbstractBusInterface
{
   private static final long serialVersionUID = 1457255663125616282L;
   private final static String configKey = "knxConnectionKNXnet";
   final JTextField inpHost;
   final JTextField inpPort;
   /**
    * Create a new KNXnet/IP bus-interface configuration widget.
    */
   public KNXnetBusInterface()
   {
      super();

      GridBagLayout layout = new GridBagLayout();
      setLayout(layout);

      final GridBagConstraints c = new GridBagConstraints();
      c.insets = new Insets(2, 2, 2, 2);

      int gridY = -1;

      c.fill = GridBagConstraints.NONE;
      c.weightx = 1;
      c.gridx = 0;
      c.gridy = ++gridY;
      add(new JLabel(I18n.getMessage("KNXnetBusInterface.Host")), c);

      inpHost = new JTextField();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 10;
      c.gridx = 1;
      c.gridy = gridY;
      add(inpHost, c);

      c.fill = GridBagConstraints.NONE;
      c.weightx = 1;
      c.gridx = 0;
      c.gridy = ++gridY;
      add(new JLabel(I18n.formatMessage("KNXnetBusInterface.Port", new String[] { Integer
            .toString(KNXnetLink.defaultPortUDP) })), c);

      inpPort = new JTextField();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 10;
      c.gridx = 1;
      c.gridy = gridY;
      add(inpPort, c);

      c.gridx = 0;
      c.weightx = 1;
      c.gridy = ++gridY;
      add(Box.createVerticalStrut(8), c);

      final JButton btnTest = new JButton(I18n.getMessage("KNXnetBusInterface.Test"));
      c.fill = GridBagConstraints.NONE;
      c.weightx = 1;
      c.gridx = 0;
      c.gridy = ++gridY;
      c.gridwidth = 2;
      add(btnTest, c);
      c.gridwidth = 1;
      btnTest.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            testConnection();
         }
      });

      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 1;
      c.weighty = 1;
      c.gridx = 0;
      c.gridy = ++gridY;
      c.gridwidth = 2;
      c.insets = new Insets(8, 8, 40, 40);
      add(getTestOutputComponent(), c);
      c.gridwidth = 1;

      c.gridy = ++gridY;
      c.weighty = 2;
      c.fill = GridBagConstraints.VERTICAL;
      add(Box.createVerticalGlue(), c);

      final Config cfg = Config.getInstance();

      String host = cfg.getStringValue(configKey + ".host");
      if (host == null || host.isEmpty())
         host = "localhost";
      inpHost.setText(host);

      int port = cfg.getIntValue(configKey + ".port");
      if (port < 1)
         port = KNXnetLink.defaultPortUDP;
      inpPort.setText(Integer.toString(port));
   }

   /**
    * Apply the widget's contents to the configuration.
    */
   public void apply()
   {
      final Config cfg = Config.getInstance();
      cfg.put(configKey + ".host", inpHost.getText());
      cfg.put(configKey + ".port", inpPort.getText());
   }

   /**
    * Test the connection to the KNXnet/IP server using the values in the input
    * fields.
    */
   protected void testConnection()
   {
      testConnection(new KNXnetLink(inpHost.getText(), Integer.valueOf(inpPort.getText())));
   }
}
