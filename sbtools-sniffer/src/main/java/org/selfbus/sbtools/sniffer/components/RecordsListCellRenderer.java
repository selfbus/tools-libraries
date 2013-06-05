package org.selfbus.sbtools.sniffer.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import org.selfbus.sbtools.common.gui.misc.ImageCache;
import org.selfbus.sbtools.sniffer.model.Direction;
import org.selfbus.sbtools.sniffer.model.Record;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * A list cell renderer for {@link Record}s.
 */
public class RecordsListCellRenderer implements ListCellRenderer<Record>
{
   private final FormLayout layout = new FormLayout("4dlu, l:p,4dlu, l:p,4dlu, f:p:g, 4dlu", 
      "1dlu, p, p, 1dlu");

   private final JPanel panel = new JPanel(layout);
   private final JLabel dateLabel = new JLabel();
   private final JLabel dirLabel = new JLabel();
   private final JLabel asciiLabel = new JLabel();
   private final JLabel hexLabel = new JLabel();
   private final DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss.SSS");
   private final Icon sendIcon = ImageCache.getIcon("icons/direction-send");
   private final Icon recvIcon = ImageCache.getIcon("icons/direction-recv");

   private Color sendColor, recvColor;
   private String ctrlColorStr;
   private boolean showAscii;
   private boolean showHex;
   
   /**
    * Create a list cell renderer for {@link Record}s.
    */
   public RecordsListCellRenderer()
   {
      this(false, true);
   }
   
   /**
    * Create a list cell renderer for {@link Record}s.
    *
    * @param ascii - true to show data as ASCII characters
    * @param hex - true to show data as hex bytes
    */
   public RecordsListCellRenderer(boolean showAscii, boolean showHex)
   {
      super();
      this.showAscii = showAscii;
      this.showHex = showHex;

      CellConstraints cc = new CellConstraints();

      dateLabel.setOpaque(false);
      dateLabel.setHorizontalTextPosition(JLabel.LEFT);

      dirLabel.setOpaque(false);
      dirLabel.setHorizontalTextPosition(JLabel.CENTER);
      dirLabel.setMinimumSize(new Dimension(sendIcon.getIconWidth(), 1));
      dirLabel.setMaximumSize(new Dimension(sendIcon.getIconWidth(), 256));

      hexLabel.setOpaque(false);
      hexLabel.setHorizontalTextPosition(JLabel.LEFT);

      if (showAscii && showHex)
      {
         panel.add(dateLabel, cc.xywh(2, 2, 1, 3));
         panel.add(dirLabel, cc.xywh(4, 2, 1, 3));
         panel.add(hexLabel, cc.xy(6, 2));
         panel.add(asciiLabel, cc.xy(6, 3));

         
      }
      else
      {
         panel.add(dateLabel, cc.xy(2, 2));
         panel.add(dirLabel, cc.xy(4, 2));

         if (showAscii)
            panel.add(asciiLabel, cc.xy(6, 2));
         else if (showHex)
            panel.add(hexLabel, cc.xy(6, 2));
      }
   }

   /**
    * Set the font.
    *
    * @param font - the font to set
    */
   public void setFont(Font font)
   {
      dateLabel.setFont(font);

      if (showAscii && showHex)
         font = font.deriveFont(font.getSize() * 0.9f);

      asciiLabel.setFont(font);
      hexLabel.setFont(font);
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
    * Set the background color for non printable characters.
    * 
    * @param c - the color to set.
    */
   public void setCtrlColor(Color c)
   {
      ctrlColorStr = String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
   }

   /**
    * Set the foreground color for hex characters.
    * 
    * @param c - the color to set.
    */
   public void setHexColor(Color c)
   {
      hexLabel.setForeground(c);
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
      
      dateLabel.setText(dateFormat.format(new Date(value.time)));

      if (showHex)
      {
         StringBuilder stringBuilder = new StringBuilder();
         for (int i = 0; i < value.length; ++i)
         {
            stringBuilder.append(String.format("%02x", value.data[i] & 255));
            stringBuilder.append(' ');
         }
         hexLabel.setText(stringBuilder.toString().toUpperCase());
      }

      if (showAscii)
      {
         StringBuilder stringBuilder = new StringBuilder("<html>");

         for (int i = 0; i < value.length; ++i)
         {
            char ch = (char) (value.data[i] & 255);

            if (Character.isISOControl(ch))
            {
               if (isSelected) stringBuilder.append("<i>");
               else stringBuilder.append("<i bgcolor=\"").append(ctrlColorStr).append("\">");

               stringBuilder.append(String.format("%02X", value.data[i] & 255));
               stringBuilder.append("</i>");

               if (showHex) stringBuilder.append("&nbsp;");
            }
            else
            {
               stringBuilder.append(ch);
               if (showHex) stringBuilder.append("&nbsp;&nbsp;");
            }
         }

         stringBuilder.append("</html>");
         asciiLabel.setText(stringBuilder.toString().toUpperCase());
      }

      return panel;
   }
}
