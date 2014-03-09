package org.selfbus.sbtools.knxcom.gui.settings;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import org.selfbus.sbtools.common.Config;
import org.selfbus.sbtools.knxcom.BusInterfaceFactory;
import org.selfbus.sbtools.knxcom.gui.internal.I18n;
import org.selfbus.sbtools.knxcom.types.KNXConnectionType;

/**
 * Settings panel for the bus-interface settings.
 */
public final class BusInterfacePanel extends JPanel
{
   private static final long serialVersionUID = 88245350928577623L;
   private final JComboBox<String> cboConnectionType;
   private final SerialBusInterface cfgSerial;
   private final KNXnetBusInterface cfgKNXnet;
   private final JPanel cfgNone;

   /**
    * Create a bus-interface settings page.
    */
   public BusInterfacePanel()
   {
      GridBagLayout layout = new GridBagLayout();
      setLayout(layout);

      final GridBagConstraints c = new GridBagConstraints();
      int gridY = -1;

      final JLabel lblCaption = new JLabel(I18n.getMessage("BusInterfacePanel.Caption"));
      lblCaption.setFont(getFont().deriveFont(Font.BOLD).deriveFont(getFont().getSize() * (float) 1.2));
      c.fill = GridBagConstraints.NONE;
      c.gridx = 0;
      c.gridy = ++gridY;
      c.gridwidth = 2;
      c.ipady = 20;
      add(lblCaption, c);
      c.gridwidth = 1;
      c.ipady = 2;

      c.fill = GridBagConstraints.NONE;
      c.weightx = 1;
      c.gridx = 0;
      c.gridy = ++gridY;
      add(new JLabel(I18n.getMessage("BusInterfacePanel.Type")), c);

      cboConnectionType = new JComboBox<String>();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.weightx = 5;
      c.gridx = 1;
      c.gridy = gridY;
      add(cboConnectionType, c);

      c.fill = GridBagConstraints.HORIZONTAL;
      c.gridwidth = 2;
      c.gridx = 0;
      c.gridy = ++gridY;
      c.insets = new Insets(8, 4, 8, 4);
      add(new JSeparator(), c);
      c.gridwidth = 1;

      cfgSerial = new SerialBusInterface();
      cfgKNXnet = new KNXnetBusInterface();
      cfgNone = new JPanel();

      c.fill = GridBagConstraints.BOTH;
      c.weightx = 1;
      c.weighty = 10;
      c.gridwidth = 2;
      c.gridx = 0;
      c.gridy = ++gridY;
      c.insets = new Insets(0, 4, 0, 4);
      add(cfgNone, c);
      add(cfgSerial, c);
      add(cfgKNXnet, c);

      cboConnectionType.addActionListener(new ActionListener()
      {
         @Override
         public void actionPerformed(ActionEvent e)
         {
            showConnectionDetails();
         }
      });

      final Config cfg = Config.getInstance();

      final String connTypeStr = cfg.get("knxConnectionType");
      for (KNXConnectionType type : KNXConnectionType.values())
      {
         final String typeStr = type.toString();
         final String key = "BusInterfacePanel." + typeStr;
         String label = I18n.getMessage(key);
         if (label.equals(key) || label.isEmpty()) label = type.label;

         cboConnectionType.addItem(label);
         if (typeStr.equals(connTypeStr))
            cboConnectionType.setSelectedIndex(cboConnectionType.getItemCount() - 1);
      }
      showConnectionDetails();
   }

   /**
    * Called when a connection type is selected. Show the connection-details widget.
    */
   private void showConnectionDetails()
   {
      final KNXConnectionType type = getSelectedConnectionType();

      cfgNone.setVisible(type == KNXConnectionType.NONE);
      cfgSerial.setVisible(type == KNXConnectionType.SERIAL_FT12);
      cfgKNXnet.setVisible(type == KNXConnectionType.KNXNET_IP);
   }

   /**
    * @return the connection type of the selected item in the connection-type combobox.
    */
   private KNXConnectionType getSelectedConnectionType()
   {
      return KNXConnectionType.values()[cboConnectionType.getSelectedIndex()];
   }

   /**
    * Apply the widget's contents to the running application and the configuration.
    */
   public void apply()
   {
      final Config cfg = Config.getInstance();
      cfg.put("knxConnectionType", getSelectedConnectionType().toString());

      cfgSerial.apply();
      cfgKNXnet.apply();

      BusInterfaceFactory.reopenBusInterface();
   }

   /**
    * Revert the widget's contents changes, if any.
    */
   public void revert()
   {
   }
}
