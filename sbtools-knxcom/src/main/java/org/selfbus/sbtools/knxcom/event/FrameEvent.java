package org.selfbus.sbtools.knxcom.event;

import java.util.EventObject;

import org.selfbus.sbtools.knxcom.emi.EmiFrame;

/**
 * A {@link EmiFrame} frame was received.
 */
public class FrameEvent extends EventObject
{
   private static final long serialVersionUID = 2669026678425552600L;

   private final byte[] data;
   private EmiFrame frame;

   /**
    * Create a new frame event.
    * 
    * @param source - the source of the event.
    * @param data - the raw data of the frame.
    */
   public FrameEvent(Object source, byte[] data)
   {
      super(source);
      this.data = data;
   }

   /**
    * Create a new frame event.
    * 
    * @param source - the source of the event.
    * @param frame - the frame object.
    */
   public FrameEvent(Object source, EmiFrame frame)
   {
      super(source);
      this.data = null;
      this.frame = frame;
   }

   /**
    * Create a new frame event.
    * 
    * @param source - the source of the event.
    * @param data - the raw data of the frame.
    * @param frame - the frame object.
    */
   public FrameEvent(Object source, byte[] data, EmiFrame frame)
   {
      super(source);
      this.data = data;
      this.frame = frame;
   }

   /**
    * @return The raw data of the received frame, or null if the event contains
    *         no raw data.
    */
   public byte[] getData()
   {
      return data;
   }

   /**
    * @return The received frame, or null if the event contains no frame object.
    */
   public EmiFrame getFrame()
   {
      return frame;
   }

   /**
    * Set the frame object.
    * 
    * @param frame - the frame to set.
    */
   public void setFrame(EmiFrame frame)
   {
      this.frame = frame;
   }
}
