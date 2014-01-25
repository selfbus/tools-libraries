package org.selfbus.sbtools.knxcom.gui.busmonitor;

import org.selfbus.sbtools.common.gui.models.Filter;

/**
 * A proxy filter class that filters bus monitor items by filtering the
 * contained frame, using the supplied frame filter.
 */
public class BusMonitorItemFilter implements Filter
{
   private final FrameFilter filter;

   /**
    * Create a bus monitor item filter. The supplied frame filter is
    * used for filtering the bus monitor items.
    *
    * @param filter - the filter to use for filtering.
    */
   public BusMonitorItemFilter(FrameFilter filter)
   {
      this.filter = filter;
   }

   /**
    * {@inheritDoc}
    */
   public boolean matches(final Object o)
   {
      if (!(o instanceof BusMonitorItem))
         return false;

      return filter.matches(((BusMonitorItem) o).getFrame());
   }
}
