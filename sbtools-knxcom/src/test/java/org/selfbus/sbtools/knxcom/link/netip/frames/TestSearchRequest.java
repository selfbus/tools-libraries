package org.selfbus.sbtools.knxcom.link.netip.frames;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.Inet4Address;

import org.junit.Test;
import org.selfbus.sbtools.knxcom.link.netip.frames.FrameFactory;
import org.selfbus.sbtools.knxcom.link.netip.frames.SearchRequest;
import org.selfbus.sbtools.knxcom.link.netip.types.TransportType;

public class TestSearchRequest
{
   @Test
   public final void testSearchRequest()
   {
      final SearchRequest req = new SearchRequest();
      assertNotNull(req.getEndPoint());

      assertFalse(req.equals(null));
      assertFalse(req.equals(new Object()));
      assertTrue(req.equals(req));
   }

   @Test
   public final void testToFromRawData() throws IOException
   {
      final SearchRequest req = new SearchRequest(TransportType.UDP, Inet4Address.getLocalHost(), 1234);

      final byte[] data = req.toByteArray();
      assertEquals(14, data.length);

      assertEquals(0x06, data[0]); // header size

      final SearchRequest reqParsed = (SearchRequest) FrameFactory.createFrame(data);
      assertNotNull(reqParsed);

      assertEquals(req.getEndPoint(), reqParsed.getEndPoint());
   }
}
