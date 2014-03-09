package org.selfbus.sbtools.knxcom.gui.settings;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.selfbus.sbtools.common.Config;
import org.selfbus.sbtools.knxcom.gui.internal.I18n;
import org.selfbus.sbtools.knxcom.link.serial.Ft12SerialLink;
import org.selfbus.sbtools.knxcom.link.serial.SerialPortUtil;

/**
 * A widget for configuring a serial bus interface connection.
 */
public class SerialBusInterface extends AbstractBusInterface
{
   private static final long serialVersionUID = 1457255663125616282L;
   private final static String configKey = "knxConnectionSerial";
   private final JComboBox<String> cboPort;

   /**
    * Create a new serial bus-interface configuration widget.
    */
   public SerialBusInterface()
   {
      super();

      GridBagLayout layout = new GridBagLayout();
      setLayout(layout);

      final GridBagConstraints c = new GridBagConstraints();
      int gridY = -1;

      c.fill = GridBagConstraints.NONE;
      c.weightx = 1;
      c.gridx = 0;
      c.gridy = ++gridY;
      add(new JLabel(I18n.getMessage("SerialBusInterface.Port")), c);

      cboPort = new JComboBox<String>();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 10;
      c.gridx = 1;
      c.gridy = gridY;
      add(cboPort, c);

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
      final String cfgPortName = cfg.get(configKey + ".port");

      for (final String portName: SerialPortUtil.getPortNames())
      {
         cboPort.addItem(portName);
         if (portName.equals(cfgPortName)) cboPort.setSelectedIndex(cboPort.getItemCount() - 1);
      }
   }

   /**
    * Apply the widget's contents to the configuration.
    */
   public void apply()
   {
      final Config cfg = Config.getInstance();

      Object sel = cboPort.getSelectedItem();
      cfg.put(configKey + ".port", sel == null ? "" : sel.toString());
   }

   /**
    * Test the connection to the KNXnet/IP server using the values in the input
    * fields.
    */
   protected void testConnection()
   {
      Object sel = cboPort.getSelectedItem();
      if (sel != null)
         testConnection(new Ft12SerialLink(sel.toString()));
   }
}
