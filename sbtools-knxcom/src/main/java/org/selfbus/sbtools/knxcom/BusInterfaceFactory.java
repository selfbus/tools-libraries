package org.selfbus.sbtools.knxcom;

import java.io.IOException;

import org.selfbus.sbtools.common.SimpleConfig;
import org.selfbus.sbtools.common.exception.FtsRuntimeException;
import org.selfbus.sbtools.knxcom.internal.BusInterfaceImpl;
import org.selfbus.sbtools.knxcom.link.Link;
import org.selfbus.sbtools.knxcom.link.dummy.DummyLink;
import org.selfbus.sbtools.knxcom.link.netip.KNXnetLink;
import org.selfbus.sbtools.knxcom.link.serial.Ft12SerialLink;
import org.selfbus.sbtools.knxcom.types.LinkMode;
import org.selfbus.sbtools.knxcom.types.LinkType;
import org.slf4j.LoggerFactory;

/**
 * Factory class for KNX/EIB bus interfaces.
 */
public final class BusInterfaceFactory
{
   private static BusInterfaceImpl busInterface;
   private static LinkMode defaultLinkMode = LinkMode.LinkLayer;

   /**
    * Returns the default bus-interface. If no default bus-interface exists, one
    * is created. If the creation of the bus-interface fails, an error dialog is
    * shown and null is returned.
    *
    * @return The default {@link BusInterface} bus-interface, or null if no
    *         bus-interface could be created.
    */
   public synchronized static BusInterface getBusInterface()
   {
      if (busInterface == null)
      {
         try
         {
            createBusInterface();
         }
         catch (final Exception e)
         {
            LoggerFactory.getLogger(BusInterfaceFactory.class).error("Failed to create bus interface", e);
            throw new FtsRuntimeException(e);
         }
      }

      return busInterface;
   }

   /**
    * @return true if the bus interface exists, false if it would be created
    *         when calling {@link #getBusInterface}.
    */
   public synchronized static boolean busInterfaceOpened()
   {
      return busInterface != null;
   }

   /**
    * Create the default bus interface. Automatically called on demand by
    * {@link #getBusInterface()}. Uses the global {@link SimpleConfig configuration} to get
    * the configured bus interface.
    * 
    * @throws FtsRuntimeException if the bus interface cannot be opened
    */
   private static void createBusInterface()
   {
      BusInterfaceImpl newBusInterface = new BusInterfaceImpl();

      SimpleConfig cfg = SimpleConfig.getInstance();
      setLinkType(newBusInterface, LinkType.valueOf(cfg.getStringValue("knxConnectionType")));

      busInterface = newBusInterface;
   }

   /**
    * Close the default bus-interface. The bus-interface will be recreated upon
    * the next {@link #getBusInterface()} call.
    */
   public synchronized static void closeBusInterface()
   {
      if (busInterface != null)
      {
         busInterface.close();
         busInterface = null;
      }
   }

   /**
    * Reopen the bus interface.
    * 
    * @throws FtsRuntimeException if the bus interface cannot be opened
    */
   public synchronized static void reopenBusInterface()
   {
      if (busInterface != null)
      {
         SimpleConfig cfg = SimpleConfig.getInstance();
         setLinkType(busInterface, LinkType.valueOf(cfg.getStringValue("knxConnectionType")));
      }
   }

   /**
    * Set the link type. This closes and reopens the bus link.
    * 
    * @param busInterface - the bus interface to set the link type.
    * @param type - the link type to set.
    */
   public synchronized static void setLinkType(BusInterfaceImpl busInterface, LinkType type)
   {
      SimpleConfig cfg = SimpleConfig.getInstance();
      Link link;

      if (type == LinkType.KNXNET_IP)
      {
         link = new KNXnetLink(cfg.getStringValue("knxConnectionKNXnet.host"), cfg.getIntValue("knxConnectionKNXnet.port"));
      }
      else if (type == LinkType.SERIAL_FT12)
      {
         link = new Ft12SerialLink(cfg.getStringValue("knxConnectionSerial.port"));
      }
      else throw new FtsRuntimeException("No bus interface configured");

      try
      {
         busInterface.setLink(link);

         if (!busInterface.isConnected())
            busInterface.open(defaultLinkMode);
      }
      catch (IOException e)
      {
         throw new FtsRuntimeException(e);
      }
   }

   /**
    * Create a new KNX/EIB bus interface that connects to a serial port.
    *
    * @param portName - the name of the serial port, e.g. "COM1:" or "/dev/ttyS0".
    *
    * @return The created bus interface.
    */
   public static BusInterface newSerialInterface(String portName)
   {
      // SerialPortUtil.loadSerialPortLib();  // .. probably obsolete
      return new BusInterfaceImpl(new Ft12SerialLink(portName));
   }

   /**
    * Create a new KNXnet/IP bus interface that connects e.g. to an eibd.
    *
    * @param host - the name or IP address of the host that is running the KNXnet/IP server.
    * @param port - the UDP port of the KNXnet/IP server on the host. Default: 3671.
    *
    * @return The created bus interface.
    */
   public static BusInterface newKNXnetInterface(String host, int port)
   {
      return new BusInterfaceImpl(new KNXnetLink(host, port));
   }

   /**
    * Create a new KNXnet/IP bus interface with a {@link DummyLink dummy connection}.
    *
    * @return The created bus interface.
    */
   public static BusInterface newDummyInterface()
   {
      return new BusInterfaceImpl(new DummyLink());
   }

   /**
    * Set the default {@link LinkMode link mode} that is used when a bus
    * interface is created. Does not change existing bus interfaces.
    * 
    * @param mode - the link mode to set.
    */
   public static void setDefaultLinkMode(LinkMode mode)
   {
      defaultLinkMode = mode;
   }

   /**
    * @return the default {@link LinkMode link mode} that is used when a bus
    *         interface is created.
    */
   public static LinkMode getDefaultLinkMode()
   {
      return defaultLinkMode;
   }
}
