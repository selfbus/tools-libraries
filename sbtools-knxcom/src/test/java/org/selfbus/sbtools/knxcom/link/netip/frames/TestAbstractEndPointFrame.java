package org.selfbus.sbtools.knxcom.link.netip.frames;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;
import org.selfbus.sbtools.knxcom.link.netip.blocks.EndPoint;
import org.selfbus.sbtools.knxcom.link.netip.frames.AbstractEndPointFrame;
import org.selfbus.sbtools.knxcom.link.netip.frames.Frame;
import org.selfbus.sbtools.knxcom.link.netip.frames.FrameFactory;
import org.selfbus.sbtools.knxcom.link.netip.frames.SearchRequest;
import org.selfbus.sbtools.knxcom.link.netip.types.TransportType;

public class TestAbstractEndPointFrame
{
   @Test
   public final void testBasicRequest()
   {
      final AbstractEndPointFrame req = new SearchRequest();
      assertNotNull(req.getEndPoint());
   }

   @Test
   public final void testAbstractEndPointFrame() throws UnknownHostException
   {
      final InetAddress addr = Inet4Address.getLocalHost();
      final SearchRequest req = new SearchRequest(TransportType.TCP, addr, 10230);

      final EndPoint endPoint = req.getEndPoint();
      assertNotNull(endPoint);
      assertEquals(TransportType.TCP, endPoint.getTransportType());
      assertEquals(addr, endPoint.getAddress());
      assertEquals(10230, endPoint.getPort());
   }

   @Test
   public final void testFromToData() throws IOException
   {
      final byte[] data = new byte[] { 0x06, 0x10, 0x02, 0x01, 0x00, 0x0e, 0x08, 0x02, 0x7f, 0x00, 0x01, 0x01, 0x34, 0x56 };

      final Frame abstractFrame = FrameFactory.createFrame(data);
      assertEquals(SearchRequest.class, abstractFrame.getClass());

      final byte[] dataOut = abstractFrame.toByteArray();
      assertArrayEquals(data, dataOut);
   }
}
