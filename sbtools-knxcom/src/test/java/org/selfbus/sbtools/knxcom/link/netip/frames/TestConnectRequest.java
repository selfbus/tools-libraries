package org.selfbus.sbtools.knxcom.link.netip.frames;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.net.InetAddress;

import org.junit.Test;
import org.selfbus.sbtools.knxcom.link.netip.frames.ConnectRequest;
import org.selfbus.sbtools.knxcom.link.netip.frames.FrameFactory;
import org.selfbus.sbtools.knxcom.link.netip.types.ConnectionType;
import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;
import org.selfbus.sbtools.knxcom.link.netip.types.TransportType;

public class TestConnectRequest
{
   @Test
   public final void testConnectRequest()
   {
      final ConnectRequest req = new ConnectRequest();

      assertEquals(ServiceType.CONNECT_REQUEST, req.getServiceType());
   }

   @Test
   public final void testFromToRawData() throws IOException
   {
      final byte data[] = new byte[]
      {
            0x06, 0x10, 0x02, 0x05, 0x00, 0x1a,
            0x08, 0x01, (byte) 0xc0, (byte) 0xa8, (byte) 0xc8, 0x14, (byte) 0xc3, (byte) 0xb4,
            0x08, 0x01, (byte) 0xc0, (byte) 0xa8, (byte) 0xc9, 0x14, (byte) 0xc3, (byte) 0xb5,
            0x04, 0x03, 0x01, 0x00
      };

      final ConnectRequest req = (ConnectRequest) FrameFactory.createFrame(data);
      assertNotNull(req);

      assertEquals(ConnectionType.DEVICE_MGMT, req.getType());
      assertEquals(InetAddress.getByAddress(new byte[] { (byte) 0xc0, (byte) 0xa8, (byte) 0xc8, 0x14 }), req.getEndPoint().getAddress());
      assertEquals(1, req.getLayer());

      final byte[] outData = req.toByteArray();
      assertArrayEquals(data, outData);
   }

   @Test
   public final void testToFromRawData() throws IOException
   {
      final ConnectRequest req = new ConnectRequest();

      final byte[] ctrlEndPointData = new byte[] { 1, 2, 3, 4 };
      final byte[] dataEndPointData = new byte[] { 2, 3, 6, 8 };

      req.getEndPoint().setTransportType(TransportType.UDP);
      req.getEndPoint().setAddress(InetAddress.getByAddress(ctrlEndPointData));
      req.getEndPoint().setPort(10200);

      req.getDataEndPoint().setTransportType(TransportType.TCP);
      req.getDataEndPoint().setAddress(InetAddress.getByAddress(dataEndPointData));
      req.getDataEndPoint().setPort(10201);

      req.setLayer(7);
      req.setType(ConnectionType.OBJECT_SERVER);

      final byte[] outData = req.toByteArray();
      final ConnectRequest reqParsed = (ConnectRequest) FrameFactory.createFrame(outData);

      assertNotNull(reqParsed);

      assertEquals(TransportType.UDP, reqParsed.getEndPoint().getTransportType());
      assertArrayEquals(ctrlEndPointData, reqParsed.getEndPoint().getAddress().getAddress());
      assertEquals(10200, reqParsed.getEndPoint().getPort());

      assertEquals(TransportType.TCP, reqParsed.getDataEndPoint().getTransportType());
      assertArrayEquals(dataEndPointData, reqParsed.getDataEndPoint().getAddress().getAddress());
      assertEquals(10201, reqParsed.getDataEndPoint().getPort());

      assertEquals(7, reqParsed.getLayer());
      assertEquals(ConnectionType.OBJECT_SERVER, reqParsed.getType());
   }
}
