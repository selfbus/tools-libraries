package org.selfbus.sbtools.sniffer.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.selfbus.sbtools.common.gui.misc.ImageCache;
import org.selfbus.sbtools.sniffer.model.Direction;
import org.selfbus.sbtools.sniffer.model.Record;

import com.jgoodies.forms.layout.FormLayout;

/**
 * A list cell renderer for {@link Record}s.
 */
public class RecordsListCellRenderer implements ListCellRenderer<Record>
{
//   private final FormLayout layout = new FormLayout("6dlu,l:p,4dlu,f:p:g,6dlu", 
//      "8dlu,p,8dlu,p,4dlu,p,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,p,4dlu,f:p:g,p,4dlu");

   private final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0)); //new GridBagLayout());
   private final JLabel dateLabel = new JLabel();
   private final JLabel dirLabel = new JLabel();
   private final JLabel dataLabel = new JLabel();
   private final DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.SSS");
   private final Icon sendIcon = ImageCache.getIcon("icons/direction-send");
   private final Icon recvIcon = ImageCache.getIcon("icons/direction-recv");

   private Color sendColor, recvColor;
   private boolean showAscii = false;
   private boolean showHex = true;

   
   /**
    * Create a list cell renderer for {@link Record}s.
    */
   public RecordsListCellRenderer()
   {
      dateLabel.setOpaque(false);
      dateLabel.setHorizontalTextPosition(JLabel.LEFT);
      panel.add(dateLabel);

      dirLabel.setOpaque(false);
      dirLabel.setHorizontalTextPosition(JLabel.CENTER);
      dirLabel.setMinimumSize(new Dimension(sendIcon.getIconWidth(), 1));
      dirLabel.setMaximumSize(new Dimension(sendIcon.getIconWidth(), 256));
      panel.add(dirLabel);

      dataLabel.setOpaque(false);
      dataLabel.setHorizontalTextPosition(JLabel.LEFT);
      panel.add(dataLabel);
   }

   /**
    * Set the font.
    *
    * @param font - the font to set
    */
   public void setFont(Font font)
   {
      dateLabel.setFont(font);
      dataLabel.setFont(font);
   }

   /**
    * Set the foreground color for the date field.
    * 
    * @param c - the color to set.
    */
   public void setDateColor(Color c)
   {
      dateLabel.setForeground(c);
   }

   /**
    * Set the foreground color for the data field.
    * 
    * @param c - the color to set.
    */
   public void setDataColor(Color c)
   {
      dataLabel.setForeground(c);
   }

   /**
    * Set the background color for {@link Direction#SEND sending direction}.
    * 
    * @param c - the color to set.
    */
   public void setSendColor(Color c)
   {
      sendColor = c;
   }

   /**
    * Set the background color for {@link Direction#RECV receiving direction}.
    * 
    * @param c - the color to set.
    */
   public void setRecvColor(Color c)
   {
      recvColor = c;
   }

   /**
    * Set the view mode.
    *
    * @param ascii - true to show data as ASCII characters
    * @param hex - true to show data as hex bytes
    */
   public void setViewMode(boolean ascii, boolean hex)
   {
      this.showAscii = ascii;
      this.showHex = hex;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public Component getListCellRendererComponent(JList<? extends Record> list, Record value, int index,
      boolean isSelected, boolean cellHasFocus)
   {
      if (isSelected) panel.setBackground(list.getSelectionBackground());
      else if (value.direction == Direction.SEND && sendColor != null)
         panel.setBackground(sendColor);
      else if (value.direction == Direction.RECV && recvColor != null)
         panel.setBackground(recvColor);
      else panel.setBackground(list.getBackground());

      if (value.direction == Direction.SEND)
         dirLabel.setIcon(sendIcon);
      else if (value.direction == Direction.RECV)
         dirLabel.setIcon(recvIcon);
      else dirLabel.setIcon(null);
      
      dateLabel.setText(dateFormat.format(value.date));

      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < value.data.length; ++i)
      {
         stringBuilder.append(String.format("%02x", value.data[i]));
         stringBuilder.append(' ');
      }
      dataLabel.setText(stringBuilder.toString().toUpperCase());

      return panel;
   }
}
