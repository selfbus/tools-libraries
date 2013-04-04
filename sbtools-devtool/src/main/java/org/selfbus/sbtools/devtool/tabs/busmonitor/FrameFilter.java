package org.selfbus.sbtools.devtool.tabs.busmonitor;

import java.util.HashSet;
import java.util.Set;

import org.selfbus.sbtools.common.Config;
import org.selfbus.sbtools.common.address.Address;
import org.selfbus.sbtools.common.address.AddressFactory;
import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.common.gui.models.Filter;
import org.selfbus.sbtools.knxcom.emi.EmiTelegramFrame;
import org.selfbus.sbtools.knxcom.telegram.Telegram;
import org.selfbus.sbtools.knxcom.telegram.Transport;

/**
 * A configurable filter for filtering EMI frames.
 */
public class FrameFilter implements Filter
{
   private final Set<Transport> transportTypes = new HashSet<Transport>();
   private PhysicalAddress fromAddr;
   private Address destAddr;

   private boolean enabled = false;

   /**
    * Create a frame filter with default values.
    */
   public FrameFilter()
   {
      reset();
   }

   /**
    * Reset the filter to its default values.
    */
   public void reset()
   {
      enabled = false;

      fromAddr = null;
      destAddr = null;

      transportTypes.clear();
      for (final Transport type : Transport.values())
         transportTypes.add(type);
   }

   /**
    * {@inheritDoc}
    */
   public boolean matches(final Object o)
   {
      if (!enabled)
         return true;

      if (!(o instanceof EmiTelegramFrame))
         return false;

      final EmiTelegramFrame frame = (EmiTelegramFrame) o;
      final Telegram telegram = frame.getTelegram();

      if (fromAddr != null && !fromAddr.equals(telegram.getFrom()))
         return false;

      if (destAddr != null && !destAddr.equals(telegram.getDest()))
         return false;

      if (!transportTypes.contains(telegram.getTransport()))
         return false;

      return true;
   }

   /**
    * @return true if the filter is enabled. A disabled filter always returns
    *         true in {@link #matches(Object)}.
    */
   public boolean isEnabled()
   {
      return enabled;
   }

   /**
    * Enable / disable the filter. A disabled filter always returns true in
    * {@link #matches(Object)}.
    *
    * Per default, the filter is enabled.
    *
    * @param enabled - the enabled state.
    */
   public void setEnabled(boolean enabled)
   {
      this.enabled = enabled;
   }

   /**
    * @return the set of matching transport types.
    */
   public Set<Transport> getTransportTypes()
   {
      return transportTypes;
   }

   /**
    * @return the source address.
    */
   public PhysicalAddress getFrom()
   {
      return fromAddr;
   }

   /**
    * Set the source address filter.
    *
    * @param addr - the address to set
    */
   public void setFrom(PhysicalAddress addr)
   {
      this.fromAddr = addr;
   }

   /**
    * @return the destination address.
    */
   public Address getDest()
   {
      return destAddr;
   }

   /**
    * Set the destination address filter.
    *
    * @param addr - the address to set
    */
   public void setDest(Address addr)
   {
      this.destAddr = addr;
   }

   /**
    * Load the filter from a configuration object.
    *
    * @param config - the configuration object to load the filter from.
    * @param key - the base name for the configuration keys.
    */
   public void fromConfig(Config config, String key)
   {
      if (!config.containsKey(key + ".enabled"))
         return;

      enabled = config.getIntValue(key + ".enabled") != 0;

      fromAddr = (PhysicalAddress) AddressFactory.createAddress(config.getStringValue(key + ".from"));
      destAddr = AddressFactory.createAddress(config.getStringValue(key + ".dest"));;

      transportTypes.clear();
      for (final String val : config.getStringValue(key + ".trans").split(","))
         transportTypes.add(Transport.valueOf(val));
   }

   /**
    * Save the filter to a configuration object.
    *
    * @param config - the configuration object to save the filter to.
    * @param key - the base name for the configuration keys.
    */
   public void toConfig(Config config, String key)
   {
      StringBuilder sb;

      config.put(key + ".enabled", enabled ? 1 : 0);
      config.put(key + ".from", fromAddr == null ? "" : fromAddr.toString());
      config.put(key + ".dest", destAddr == null ? "" : destAddr.toString());

      sb = new StringBuilder();
      for (Transport trans : transportTypes)
         sb.append(',').append(trans.name());
      config.put(key + ".trans", sb.toString().substring(1));
   }
}
