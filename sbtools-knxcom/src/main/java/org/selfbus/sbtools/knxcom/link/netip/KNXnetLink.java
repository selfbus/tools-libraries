package org.selfbus.sbtools.knxcom.link.netip;

import java.io.IOException;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.Validate;
import org.selfbus.sbtools.common.HexString;
import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.knxcom.emi.EmiFrame;
import org.selfbus.sbtools.knxcom.event.CloseEvent;
import org.selfbus.sbtools.knxcom.event.FrameEvent;
import org.selfbus.sbtools.knxcom.internal.AbstractListenableLink;
import org.selfbus.sbtools.knxcom.link.Link;
import org.selfbus.sbtools.knxcom.link.netip.frames.ConnectRequest;
import org.selfbus.sbtools.knxcom.link.netip.frames.ConnectResponse;
import org.selfbus.sbtools.knxcom.link.netip.frames.DescriptionRequest;
import org.selfbus.sbtools.knxcom.link.netip.frames.DescriptionResponse;
import org.selfbus.sbtools.knxcom.link.netip.frames.DisconnectRequest;
import org.selfbus.sbtools.knxcom.link.netip.frames.DisconnectResponse;
import org.selfbus.sbtools.knxcom.link.netip.frames.Frame;
import org.selfbus.sbtools.knxcom.link.netip.frames.FrameFactory;
import org.selfbus.sbtools.knxcom.link.netip.frames.SearchRequest;
import org.selfbus.sbtools.knxcom.link.netip.frames.SearchResponse;
import org.selfbus.sbtools.knxcom.link.netip.frames.TunnelingAck;
import org.selfbus.sbtools.knxcom.link.netip.frames.TunnelingRequest;
import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;
import org.selfbus.sbtools.knxcom.link.netip.types.StatusCode;
import org.selfbus.sbtools.knxcom.link.netip.types.TransportType;
import org.selfbus.sbtools.knxcom.types.LinkMode;
import org.selfbus.sbtools.knxcom.types.LinkType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A KNXnet/IP connection.
 */
public final class KNXnetLink extends AbstractListenableLink implements Link
{
   private final static Logger LOGGER = LoggerFactory.getLogger(KNXnetLink.class);

   /**
    * The default KNXnet/IP UDP port
    */
   public static final int defaultPortUDP = 3671;

   /**
    * The default KNXnet/IP TCP port
    */
   public static final int defaultPortTCP = 6720;

   // Enable to get debug output of the KNXnet/IP communication data
   private final boolean debugData = true;

   // Enable to get debug output of the KNXnet/IP communication protocol
   private final boolean debugProto = false;

   private final InetSocketAddress addr;
   private final InetAddress localAddr;
   private InetSocketAddress dataAddr;
   private PhysicalAddress busAddr = PhysicalAddress.NULL;

   private final Semaphore receiveSemaphore = new Semaphore(0);
   private Frame receivedFrame;

   private int channelId = -1;
   private int sequence = -1;
   private boolean busMonitorModeSupported = true;
   private LinkMode mode;

   private final DatagramSocket socket;
   private final Thread listenerThread;

   private final int sendBufferSize = 1024;
   private final int recvBufferSize = 1024;

   /**
    * Create a new connection to a KNXnet/IP server listening on a custom port.
    * 
    * @param host - the name or IP address of the host that is running the
    *           KNXnet/IP server.
    * @param port - the UDP port of the KNXnet/IP server on the host. Usually
    *           3671.
    */
   public KNXnetLink(String host, int port)
   {
      addr = new InetSocketAddress(host, port);
      Validate.notNull(addr.getAddress(), "Host not found: " + host);
      localAddr = getLocalIpAddressFor(addr.getAddress());

      try
      {
         socket = new DatagramSocket(0, localAddr);
         socket.setSendBufferSize(sendBufferSize << 1);
         socket.setReceiveBufferSize(recvBufferSize << 1);
         LOGGER.info("Opening UDP socket " + localAddr + " port " + socket.getLocalPort());
      }
      catch (SocketException e)
      {
         throw new RuntimeException("Cannot create a datagram socket", e);
      }

      listenerThread = createListenerThread();
      listenerThread.start();
   }

   /**
    * Create a new connection to a KNXnet/IP server listening on the default UDP
    * port (3671).
    * 
    * @param host - the name or IP address of the host that is running the
    *           KNXnet/IP server.
    */
   public KNXnetLink(String host)
   {
      this(host, defaultPortUDP);
   }

   /**
    * @return the host where the KNXnet/IP server is running.
    */
   public String getHost()
   {
      return addr.getHostName();
   }

   /**
    * @return the port on which the KNXnet/IP server is listening.
    */
   public int getPort()
   {
      return addr.getPort();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void open(LinkMode mode) throws IOException
   {
      receiveSemaphore.drainPermits();

      Frame frame;

      LOGGER.info("Local address is " + localAddr.toString() + ", port " + socket.getLocalPort());

      send(addr, new SearchRequest(TransportType.UDP, localAddr, socket.getLocalPort()), false);
      frame = receive(1000);
      if (!(frame instanceof SearchResponse))
         throw new ConnectException("no response to KNXnet/IP search request");
      final SearchResponse searchResponse = (SearchResponse) frame;
      LOGGER.info("Found KNXnet/IP server: " + searchResponse.getHardwareInfo().getName());

      StatusCode status = connectRequest(mode);
      if (status != StatusCode.OK)
      {
         if (status == StatusCode.E_CONNECTION_TYPE && mode == LinkMode.BusMonitor)
         {
            // Fall back to LinkLayer mode if BusMonitor mode is not supported
            mode = LinkMode.LinkLayer;
            status = connectRequest(mode);
            if (status == StatusCode.OK)
            {
               LOGGER.warn("KNXnet/IP server does not support bus monitor mode, falling back to link layer mode");
               busMonitorModeSupported = false;
            }
         }

         if (status != StatusCode.OK)
            throw new ConnectException("KNXnet/IP connect to " + addr + " failed: " + status);
      }

      send(addr, new DescriptionRequest(TransportType.UDP, localAddr, socket.getLocalPort()), false);
      frame = receive(3500);

      if (!(frame instanceof DescriptionResponse))
         throw new ConnectException("no response to KNXnet/IP describe request");

      final DescriptionResponse descResp = (DescriptionResponse) frame;

      this.mode = mode;
      busAddr = descResp.getHardwareInfo().getBusAddress();
      LOGGER.info("Connection to KNXnet/IP server " + busAddr + " established");

      if (PhysicalAddress.NULL.equals(busAddr))
         busAddr = new PhysicalAddress(0, 0, 254);
   }

   /**
    * Send a connection request and wait for the answer. On success, the channel
    * ID and the data endpoint information is stored and {@link StatusCode#OK}
    * is returned. On failure, the {@link StatusCode status code} of the
    * response is returned.
    * 
    * @param mode - the link mode to request
    * 
    * @return the {@link StatusCode status} of the the connection response.
    * 
    * @throws IOException
    */
   private StatusCode connectRequest(LinkMode mode) throws IOException
   {

      final ConnectRequest conReq = new ConnectRequest(TransportType.UDP, localAddr,
            socket.getLocalPort(), TransportType.UDP, localAddr, socket.getLocalPort());
      conReq.setLayer(mode);

      Frame frame = null;
      for (int tries = 3; tries > 0 && !(frame instanceof ConnectResponse); --tries)
      {
         send(addr, conReq, false);
         frame = receive(3500);
      }

      if (!(frame instanceof ConnectResponse))
         throw new ConnectException("no response to KNXnet/IP connect request");

      final ConnectResponse resp = (ConnectResponse) frame;
      final StatusCode status = resp.getStatus();

      if (status == StatusCode.OK)
      {
         channelId = resp.getChannelId();
         dataAddr = new InetSocketAddress(resp.getDataEndPoint().getAddress(), resp.getDataEndPoint().getPort());
      }

      return status;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void close()
   {
      closeQuiet();
      fireLinkClosed(new CloseEvent(this));
   }

   /**
    * Close the connection without firing a close event.
    */
   protected void closeQuiet()
   {
      LOGGER.debug("Closing KNXnet/IP connection");

      try
      {
         send(addr, new DisconnectRequest(TransportType.UDP, localAddr, socket.getLocalPort(),
               channelId), false);

         final Frame frame = receive(1000);
         if (!(frame instanceof DisconnectResponse))
            throw new ConnectException("No response to KNXnet/IP disconnect request");

         final DisconnectResponse resp = (DisconnectResponse) frame;
         if (resp.getStatus() != StatusCode.OK)
            throw new ConnectException("Cannot disconnect: " + resp.getStatus());
      }
      catch (IOException e)
      {
         LOGGER.error("Failed to disconnect from the KNXnet/IP server", e);
      }
      finally
      {
         channelId = -1;
      }
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean isConnected()
   {
      return socket != null && channelId >= 0;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void setLinkMode(LinkMode mode) throws IOException
   {
      if (!busMonitorModeSupported)
         mode = LinkMode.LinkLayer;

      if (this.mode == mode)
         return;

      closeQuiet();
      open(mode);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LinkMode getLinkMode()
   {
      return mode;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public LinkType getLinkType()
   {
      return LinkType.KNXNET_IP;
   }

   /**
    * Receive a frame. Up to <code>timeout</code> milliseconds is waited for a
    * frame to arrive.
    * 
    * @param timeout - wait up to timeout milliseconds, -1 waits infinitely.
    * 
    * @return the received KNXnet/IP frame, or null of no frame was received
    *         within the timeout.
    */
   public Frame receive(int timeout)
   {
      receiveSemaphore.drainPermits();
      receivedFrame = null;

      try
      {
         if (!receiveSemaphore.tryAcquire(timeout, TimeUnit.MILLISECONDS))
            return null;
      }
      catch (InterruptedException e)
      {
         e.printStackTrace();
         return null;
      }

      return receivedFrame;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void send(EmiFrame emiFrame, boolean blocking) throws IOException
   {
      final TunnelingRequest frame = new TunnelingRequest(channelId, ++sequence);
      frame.setFrame(emiFrame);
      send(dataAddr, frame, blocking);
   }

   /**
    * Send a KNXnet/IP frame to an address.
    * 
    * @param address - the address (IP number + port) to send to
    * @param frame - the frame to send
    * @param blocking - enable to wait for an acknowledge
    */
   public void send(InetSocketAddress address, Frame frame, boolean blocking) throws IOException
   {
      final ServiceType serviceType = frame.getServiceType();

      if (debugProto)
         LOGGER.debug("Send: " + serviceType + " (seq " + sequence + ")");

      final byte[] data = frame.toByteArray();
      if (debugData && LOGGER.isDebugEnabled())
         LOGGER.debug("IP-SEND: " + HexString.toString(data));

      socket.send(new DatagramPacket(data, data.length, address));
   }

   /**
    * Process the received data
    * 
    * @param data - the received data.
    * @param len - number of bytes in data that are valid.
    * 
    * @throws IOException
    */
   public void processData(final byte[] data, int len) throws IOException
   {
      if (len < 6)
         return;

      if (debugData && LOGGER.isDebugEnabled())
         LOGGER.debug("IP-RECV: " + HexString.toString(data, 0, len));

      final Frame frame = FrameFactory.createFrame(data);
      if (frame == null)
      {
         int serviceTypeCode = ((data[2] << 8) | data[3]) & 0xffff;
         final ServiceType serviceType = ServiceType.valueOf(serviceTypeCode);
         LOGGER.debug("Recv: " + serviceType + " (ignored)");
         return;
      }

      if (debugProto)
         LOGGER.debug("Recv: " + frame.getServiceType());

      if (receiveSemaphore.hasQueuedThreads())
      {
         receivedFrame = frame;
         receiveSemaphore.release();
      }
      else if (frame.getServiceType() == ServiceType.TUNNELING_REQUEST)
      {
         final TunnelingRequest tunnelFrame = (TunnelingRequest) frame;
         final int recvSequence = tunnelFrame.getSequence();

         if (debugData)
            LOGGER.debug("Recv: " + tunnelFrame.getFrame() + " (seq " + recvSequence + ")");

         send(dataAddr, new TunnelingAck(channelId, recvSequence, StatusCode.OK), false);

         fireFrameReceived(new FrameEvent(this, tunnelFrame.getFrame()));
      }
   }

   /**
    * Create the listener thread.
    */
   protected Thread createListenerThread()
   {
      return new Thread(new Runnable()
      {
         @Override
         public void run()
         {
            LOGGER.debug("Starting KNXnet/IP listener thread");

            final byte[] recvBuffer = new byte[recvBufferSize];
            final DatagramPacket p = new DatagramPacket(recvBuffer, recvBuffer.length);

            while (true)
            {
               try
               {
                  Arrays.fill(recvBuffer, (byte) 0);
                  socket.receive(p);
                  processData(p.getData(), p.getLength());
               }
               catch (IOException e)
               {
                  e.printStackTrace();
               }
               catch (IllegalArgumentException e)
               {
                  e.printStackTrace();
               }
            }
         }
      });
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public PhysicalAddress getPhysicalAddress()
   {
      return busAddr;
   }

   /**
    * Get the raw bytes of the network address of the given address.
    * 
    * @param addr - the address to process
    * @param maskLength - the length of the network mask in bits
    * 
    * @return The raw bytes of the address
    */
   static byte[] getNetworkAddressOf(InetAddress addr, int maskLength)
   {
      final byte[] raw = addr.getAddress();

      int pos = maskLength >> 3;
      int restBits = maskLength & 7;
         
      if (restBits != 0)
      {
         final int mask = new int[] { 0x80, 0xc0, 0xe0, 0xf0, 0xf8, 0xfc, 0xfe } [restBits - 1];
         raw[pos++] &= mask;
      }

      while (pos < raw.length)
         raw[pos++] = 0;
      
      return raw;
   }
   
   /**
    * Select the local IP address for connecting the given remote IP address.
    * 
    * @param remoteIP - the IP address of the remote host.
    */
   static InetAddress getLocalIpAddressFor(InetAddress remoteIP)
   {
      Validate.notNull(remoteIP, "no remote IP address given");

      try
      {
         final Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
         while (en.hasMoreElements())
         {
            final NetworkInterface iface = en.nextElement();

            for (final InterfaceAddress ifaceAddr: iface.getInterfaceAddresses())
            {
               final int maskLength = ifaceAddr.getNetworkPrefixLength();

               final byte[] remoteRaw = getNetworkAddressOf(remoteIP, maskLength);
               final byte[] localRaw = getNetworkAddressOf(ifaceAddr.getAddress(), maskLength);

               if (Arrays.equals(remoteRaw, localRaw))
                  return ifaceAddr.getAddress();
            }
         }

         LOGGER.warn("Did not find a matching network interface, using default local IP");
      }
      catch (SocketException e)
      {
         LOGGER.error("failed to get list of network interfaces, using default local IP", e);
      }

      try
      {
         return InetAddress.getLocalHost();
      }
      catch (UnknownHostException e1)
      {
         throw new RuntimeException("failed to get IP address of local host", e1);
      }
   }
}
