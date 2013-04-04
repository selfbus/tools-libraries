package org.selfbus.sbtools.devtool.tabs.busmonitor;

import java.util.Calendar;
import java.util.Date;

import org.selfbus.sbtools.knxcom.emi.EmiFrame;

/**
 * An item of the {@link BusMonitor bus-monitor}.
 */
public final class BusMonitorItem
{
   private final int id;
   private final EmiFrame frame;
   private final Date when;
   private final String fromName, destName;

   /**
    * Create a bus-monitor item.
    *
    * @param id - the numerical id of the frame.
    * @param when - the time when the frame was received.
    * @param frame - the EMI frame that the item represents.
    *
    * @see Calendar#getTimeInMillis()
    */
   public BusMonitorItem(int id, Date when, EmiFrame frame)
   {
      this(id, when, frame, null, null);
   }

   /**
    * Create a bus-monitor item.
    *
    * @param id - the numerical id of the frame.
    * @param when - the time when the frame was received.
    * @param frame - the EMI frame that the item represents.
    * @param fromName - the name of the sender
    * @param destName - the name of the destination
    *
    * @see Calendar#getTimeInMillis()
    */
   public BusMonitorItem(int id, Date when, EmiFrame frame, String fromName, String destName)
   {
      this.id = id;
      this.when = when;
      this.frame = frame;
      this.fromName = fromName;
      this.destName = destName;
   }

   /**
    * @return the numerical id of the frame.
    */
   public int getId()
   {
      return id;
   }

   /**
    * @return the date when the frame was received.
    */
   public Date getWhen()
   {
      return when;
   }

   /**
    * @return The EMI frame.
    */
   public EmiFrame getFrame()
   {
      return frame;
   }

   /**
    * @return The name of the sender.
    */
   public String getFromName()
   {
      return fromName == null ? "" : fromName;
   }

   /**
    * @return The name of the destination.
    */
   public String getDestName()
   {
      return destName == null ? "" : destName;
   }
}
