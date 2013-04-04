package org.selfbus.sbtools.devtool.tabs.busmonitor;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.selfbus.sbtools.common.HexString;
import org.selfbus.sbtools.common.gui.misc.ImageCache;
import org.selfbus.sbtools.devtool.internal.I18n;
import org.selfbus.sbtools.knxcom.application.Application;
import org.selfbus.sbtools.knxcom.emi.EmiFrame;
import org.selfbus.sbtools.knxcom.emi.EmiTelegramFrame;
import org.selfbus.sbtools.knxcom.emi.types.EmiFrameClass;
import org.selfbus.sbtools.knxcom.telegram.Telegram;

public final class BusMonitorListCellRenderer<E> implements ListCellRenderer<E>
{
   private static final Icon recvIcon = ImageCache.getIcon("icons/msg_receive");
   private static final Icon sendIcon = ImageCache.getIcon("icons/msg_send");
   private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss.SSS");

   private final JPanel renderer;
   private final JLabel lblWhen, lblId, lblDirection, lblAppName, lblFrom, lblDest, lblAppData, lblRaw;

   private final ListCellRenderer<Object> defaultRenderer = new DefaultListCellRenderer();

   /**
    * Create a bus-monitor cell renderer.
    */
   public BusMonitorListCellRenderer()
   {
      renderer = new JPanel(new GridBagLayout());

      GridBagConstraints c = new GridBagConstraints();
      c.anchor = GridBagConstraints.WEST;
      c.ipadx = 8;
      c.ipady = 2;

      int col = -1;

      lblWhen = new JLabel();
      c.gridx = ++col;
      c.gridy = 0;
      renderer.add(lblWhen, c);

      lblId = new JLabel();
      lblId.setForeground(Color.gray);
      c.gridx = col;
      c.gridy = 1;
      c.anchor = GridBagConstraints.EAST;
      renderer.add(lblId, c);

      lblDirection = new JLabel();
      c.gridx = ++col;
      c.gridy = 0;
      c.gridheight = 2;
      c.anchor = GridBagConstraints.NORTH;
      renderer.add(lblDirection, c);

      c.gridheight = 1;
      c.anchor = GridBagConstraints.WEST;

      lblAppName = new JLabel();
      c.gridx = ++col;
      c.gridy = 0;
      renderer.add(lblAppName, c);

      lblFrom = new JLabel();
      c.gridx = ++col;
      c.gridy = 0;
      renderer.add(lblFrom, c);

      lblDest = new JLabel();
      c.gridx = ++col;
      c.gridy = 0;
      renderer.add(lblDest, c);

      lblAppData = new JLabel();
      c.gridx = ++col;
      c.gridy = 0;
      renderer.add(lblAppData, c);

      lblRaw = new JLabel();
      lblRaw.setForeground(Color.gray);
      c.gridx = 2;
      c.gridwidth = col - 1;
      c.gridy = 1;
      renderer.add(lblRaw, c);
      c.gridwidth = 1;

      c.gridx = ++col;
      c.gridy = 0;
      c.weightx = 100;
      c.fill = GridBagConstraints.HORIZONTAL;
      renderer.add(Box.createHorizontalGlue(), c);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, boolean isSelected,
         boolean cellHasFocus)
   {
      Component returnValue = null;
      if (value != null && (value instanceof BusMonitorItem))
      {
         final BusMonitorItem busMonitorItem = (BusMonitorItem) value;
         final EmiFrame frame = busMonitorItem.getFrame();
         final EmiFrameClass frameClass = frame.getType().frameClass;

         lblWhen.setText(dateFormatter.format(busMonitorItem.getWhen()));
         lblId.setText("#" + busMonitorItem.getId());

         if (frameClass == EmiFrameClass.SEND || frameClass == EmiFrameClass.CONFIRM)
            lblDirection.setIcon(sendIcon);
         else if (frameClass == EmiFrameClass.RECEIVE)
            lblDirection.setIcon(recvIcon);
         else lblDirection.setIcon(null);

         lblAppName.setText(frame.getType().getLabel());
         lblRaw.setText(HexString.toString(frame.toByteArray()));

         if (frame instanceof EmiTelegramFrame)
         {
            final Telegram telegram = ((EmiTelegramFrame) frame).getTelegram();

            lblFrom.setText(I18n.formatMessage("BusMonitorCellRenderer.From", telegram.getFrom().toString(), busMonitorItem.getFromName()));
            lblDest.setText(I18n.formatMessage("BusMonitorCellRenderer.Dest", telegram.getDest().toString(), busMonitorItem.getDestName()));

            final Application app = telegram.getApplication();
            if (app == null)
               lblAppData.setText(telegram.getTransport().name());
            else lblAppData.setText(app.toString());
         }
         else
         {
            lblFrom.setText("");
            lblDest.setText("");
            lblAppData.setText(frame.toString());
         }

         renderer.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
         renderer.setEnabled(list.isEnabled());

         returnValue = renderer;
      }

      if (returnValue == null)
         return defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      return returnValue;
   }
}
