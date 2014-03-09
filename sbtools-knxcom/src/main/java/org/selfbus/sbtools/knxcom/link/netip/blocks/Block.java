package org.selfbus.sbtools.knxcom.link.netip.blocks;

import org.selfbus.sbtools.knxcom.telegram.InvalidDataException;

/**
 * Interface of structures in KNXnet/IP frame bodies that contain the data for a
 * specific service type.
 */
public interface Block
{
   /**
    * Initialize the object from the given data, beginning at start.
    * 
    * @return number of bytes that were used.
    * @throws InvalidDataException
    */
   public int fromData(int[] data, int start) throws InvalidDataException;

   /**
    * Fill the data of the object into the array <code>data</code>, starting at index
    * start. The bytes in rawData are in the range 0..255.
    * 
    * @return number of bytes that were used.
    */
   public int toData(int[] data, int start);
}
