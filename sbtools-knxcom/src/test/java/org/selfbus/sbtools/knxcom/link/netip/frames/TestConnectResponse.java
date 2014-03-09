package org.selfbus.sbtools.knxcom.link.netip.frames;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;
import java.net.InetAddress;

import org.junit.Test;
import org.selfbus.sbtools.knxcom.link.netip.frames.ConnectResponse;
import org.selfbus.sbtools.knxcom.link.netip.frames.FrameFactory;
import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;
import org.selfbus.sbtools.knxcom.link.netip.types.StatusCode;
import org.selfbus.sbtools.knxcom.link.netip.types.TransportType;

public class TestConnectResponse
{
   @Test
   public final void testConnectResponse()
   {
      final ConnectResponse resp = new ConnectResponse();

      assertEquals(ServiceType.CONNECT_RESPONSE, resp.getServiceType());
      assertEquals(StatusCode.OK, resp.getStatus());
      assertEquals(0, resp.getChannelId());
      assertNotNull(resp.getDataEndPoint());
      assertNull(resp.getData());
   }

   @Test
   public final void testFromRawData() throws IOException
   {
      final byte data[] = new byte[] { 0x06, 0x10, 0x02, 0x06, 0x00, 0x14, 0x15, 0x00, 0x08, 0x01, (byte) 0xc0, (byte) 0xa8, (byte) 0xc8, 0x14, (byte) 0xc3, (byte) 0xb4, 0x04, 0x04, 0x11, 0x0A };

      final ConnectResponse resp = (ConnectResponse) FrameFactory.createFrame(data);
      assertNotNull(resp);

      assertEquals(StatusCode.OK, resp.getStatus());
      assertEquals(0x15, resp.getChannelId());
      assertEquals(0xc3b4, resp.getDataEndPoint().getPort());
      assertEquals(InetAddress.getByAddress(new byte[] { (byte) 0xc0, (byte) 0xa8, (byte) 0xc8, 0x14 }), resp.getDataEndPoint().getAddress());
   }

//   @Test
   public final void testToFromRawData() throws IOException
   {
      final int[] respData = new int[] { 17, 4 };
      final ConnectResponse resp = new ConnectResponse();

      resp.setStatus(StatusCode.E_CONNECTION_TYPE);
      assertEquals(StatusCode.E_CONNECTION_TYPE, resp.getStatus());

      resp.setChannelId(1);
      assertEquals(1, resp.getChannelId());

      final byte[] addrBytes = new byte[] { 1, 2, 3, 4 };
      resp.getDataEndPoint().setTransportType(TransportType.UDP);
      resp.getDataEndPoint().setAddress(InetAddress.getByAddress(addrBytes));
      resp.getDataEndPoint().setPort(40111);

      resp.setData(respData);
      assertEquals(respData, resp.getData());

      final byte[] data = resp.toByteArray();

//      assertEquals(20, data.length);
      assertEquals(0x06, data[0]); // header size

      final ConnectResponse respParsed = (ConnectResponse) FrameFactory.createFrame(data);
      assertNotNull(respParsed);

      assertEquals(StatusCode.E_CONNECTION_TYPE, respParsed.getStatus());
      assertEquals(1, respParsed.getChannelId());
      assertArrayEquals(respData, respParsed.getData());
      assertEquals(resp.getDataEndPoint(), respParsed.getDataEndPoint());
   }
}
