package org.selfbus.sbtools.knxcom.emi.types;

import org.selfbus.sbtools.knxcom.emi.PEI_Switch_req;

/**
 * Modes that can be switched to with a {@link PEI_Switch_req} frame.
 */
public enum PEISwitchMode
{
   /**
    * Normal mode.
    */
   NORMAL(0x00, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 }),

   /**
    * Application layer.
    */
   APP_LAYER(0x00, new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 8 }),

   /**
    * Transport layer.
    */
   TRANS_LAYER(0x00, new int[] { 1, 2, 3, 4, 4, 8, 8, 8, 0 }),

   /**
    * Data link layer.
    */
   LINK(0x00, new int[] { 1, 8, 3, 4, 5, 6, 7, 8, 0 }),

   /**
    * Data link layer in bus monitor mode.
    */
   BUSMON(0x90, new int[] { 1, 8, 3, 4, 5, 6, 7, 8, 0 });

   public final int systemStatus;
   public final int[] targets;

   private PEISwitchMode(int systemStatus, int[] targets)
   {
      this.systemStatus = systemStatus;
      this.targets = targets;
   }
}
