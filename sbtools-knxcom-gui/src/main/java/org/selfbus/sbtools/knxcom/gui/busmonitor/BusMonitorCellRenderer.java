package org.selfbus.sbtools.knxcom.gui.busmonitor;

import java.awt.Color;
import java.awt.Component;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.selfbus.sbtools.common.HexString;
import org.selfbus.sbtools.common.gui.misc.ImageCache;
import org.selfbus.sbtools.knxcom.application.Application;
import org.selfbus.sbtools.knxcom.emi.EmiFrame;
import org.selfbus.sbtools.knxcom.emi.EmiTelegramFrame;
import org.selfbus.sbtools.knxcom.emi.types.EmiFrameClass;
import org.selfbus.sbtools.knxcom.gui.internal.I18n;
import org.selfbus.sbtools.knxcom.telegram.Telegram;

/**
 * An alternative cell renderer for the bus monitor.
 */
public final class BusMonitorCellRenderer<E> implements ListCellRenderer<E>
{
   private static final Icon recvIcon = ImageCache.getIcon("icons/msg_receive");
   private static final Icon sendIcon = ImageCache.getIcon("icons/msg_send");
   private static final SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm:ss.SSS");

   private final ListCellRenderer<Object> defaultRenderer = new DefaultListCellRenderer();
   private final JLabel lblWhen, lblId, lblDirection, lblFrom, lblDest, lblAppData, lblRaw;
   private final JPanel renderer;

   private boolean colorize = false;
   private Color bgcolor;

   /**
    * Create a bus-monitor cell renderer.
    */
   public BusMonitorCellRenderer()
   {
      renderer = new JPanel();
      GroupLayout layout = new GroupLayout(renderer); // see http://docs.oracle.com/javase/tutorial/uiswing/layout/group.html
      renderer.setLayout(layout);
      renderer.setBorder(BorderFactory.createEmptyBorder(2, 1, 2, 1));

      layout.setAutoCreateGaps(false);
      layout.setAutoCreateContainerGaps(false);

      lblWhen = new JLabel();
      renderer.add(lblWhen);

      lblId = new JLabel();
      lblId.setForeground(Color.gray);
      renderer.add(lblId);

      lblDirection = new JLabel();
      lblDirection.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 2));
      renderer.add(lblDirection);

      lblFrom = new JLabel(I18n.formatMessage("BusMonitorCellRenderer.From", "0.0.00"));
      int lblFromWidth = lblFrom.getMinimumSize().width;
      renderer.add(lblFrom);

      lblDest = new JLabel(org.selfbus.sbtools.knxcom.gui.internal.I18n.formatMessage("BusMonitorCellRenderer.Dest", "0.0.00"));
      int lblDestWidth = lblDest.getMinimumSize().width;
      renderer.add(lblDest);

      lblAppData = new JLabel();
      renderer.add(lblAppData);

      lblRaw = new JLabel();
      lblRaw.setForeground(Color.gray);
      renderer.add(lblRaw);

      layout.setHorizontalGroup(layout.createSequentialGroup()
         .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
            .addComponent(lblWhen)
            .addComponent(lblId))
         .addComponent(lblDirection)
         .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
               .addComponent(lblFrom, lblFromWidth, lblFromWidth, GroupLayout.DEFAULT_SIZE)
               .addComponent(lblDest, lblDestWidth, lblDestWidth, GroupLayout.DEFAULT_SIZE)
               .addComponent(lblAppData))
            .addComponent(lblRaw))
         );

      layout.setVerticalGroup(layout.createSequentialGroup()
         .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(lblWhen)
            .addComponent(lblDirection)
            .addComponent(lblFrom)
            .addComponent(lblDest)
            .addComponent(lblAppData))
         .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addComponent(lblId)
            .addComponent(lblRaw))
         );
   }

   /**
    * Toggle colorizing of the background color by the sender address of the telegram.
    * 
    * @param colorize - true to enable, false to disable
    */
   public void setColorize(boolean colorize)
   {
      this.colorize = colorize;
   }

   /**
    * @return True if colorizing of the background color is enabled.
    */
   public boolean isColorize()
   {
      return colorize;
   }
   
   /**
    * Get a color for an address.
    *
    * @param addr - the address.
    * @param bgcolor - the default background color.
    * @return The color for the address.
    */
   protected Color getAddrColor(int addr, Color bgcolor)
   {
      int r = (addr>>0)&1 | (addr>>2)&2 | (addr>>4)&4 | (addr>>6)&8 | (addr>>8)&16 | (addr>>10)&32;
      int g = (addr>>1)&1 | (addr>>3)&2 | (addr>>5)&4 | (addr>>7)&8 | (addr>>9)&16;
      int b = (addr>>2)&1 | (addr>>4)&2 | (addr>>6)&4 | (addr>>8)&8 | (addr>>10)&16;
      float f = 3.0f;

      r = (int) (bgcolor.getRed() - r * f);
      if (r < 0) r = 0;
   
      g = (int) (bgcolor.getGreen() - g * f);
      if (g < 0) g = 0;
   
      b = (int) (bgcolor.getBlue() - b * f);
      if (b < 0) b = 0;

      return new Color(r, g, b);
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
         Color c = list.getBackground();

         lblWhen.setText(dateFormatter.format(busMonitorItem.getWhen()));
         lblId.setText("#" + busMonitorItem.getId());

         if (frameClass == EmiFrameClass.SEND || frameClass == EmiFrameClass.CONFIRM)
            lblDirection.setIcon(sendIcon);
         else if (frameClass == EmiFrameClass.RECEIVE)
            lblDirection.setIcon(recvIcon);
         else lblDirection.setIcon(null);

         lblRaw.setText(HexString.toString(frame.toByteArray()));

         if (frame instanceof EmiTelegramFrame)
         {
            final Telegram telegram = ((EmiTelegramFrame) frame).getTelegram();

            lblFrom.setText(I18n.formatMessage("BusMonitorCellRenderer.From", telegram.getFrom().toString(), busMonitorItem.getFromName()));
            lblDest.setText(I18n.formatMessage("BusMonitorCellRenderer.Dest", telegram.getDest().toString(), busMonitorItem.getDestName()));

            final Application app = telegram.getApplication();
            String appData;
            if (app == null)
               appData = telegram.getTransport().name();
            else appData = app.toString();

            if (telegram.getTransport().hasSequence)
            {
               appData += " (";
               appData += I18n.getMessage("BusMonitorCellRenderer.Sequence");
               appData += ' ';
               appData += Integer.toString(telegram.getSequence());
               appData += ')';
            }
            lblAppData.setText(appData);

            if (bgcolor == null)
               bgcolor = list.getBackground();

            if (colorize)
               c = getAddrColor(telegram.getFrom().getAddr(), bgcolor);
            else c = bgcolor;
         }
         else
         {
            lblFrom.setText("");
            lblDest.setText("");
            lblAppData.setText(frame.toString());
         }

         renderer.setBackground(isSelected ? list.getSelectionBackground() : c);
         renderer.setEnabled(list.isEnabled());

         returnValue = renderer;
      }

      if (returnValue == null)
         return defaultRenderer.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
      return returnValue;
   }
}
