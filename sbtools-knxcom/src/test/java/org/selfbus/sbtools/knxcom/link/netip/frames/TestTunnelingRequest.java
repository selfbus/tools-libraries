package org.selfbus.sbtools.knxcom.link.netip.frames;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Test;
import org.selfbus.sbtools.common.HexString;
import org.selfbus.sbtools.knxcom.emi.EmiTelegramFrame;
import org.selfbus.sbtools.knxcom.link.netip.types.ServiceType;
import org.selfbus.sbtools.knxcom.telegram.Telegram;
import org.selfbus.sbtools.knxcom.telegram.TelegramFactory;

public class TestTunnelingRequest
{
   @Test
   public final void testTunnelRequest()
   {
      final TunnelingRequest req = new TunnelingRequest();

      assertEquals(ServiceType.TUNNELING_REQUEST, req.getServiceType());
   }

   @Test
   public final void testFromToRawData() throws IOException
   {
      // Recv L_Data low from 1.1.6 to 1/0/10 hops: 06 T_DATA_XXX_REQ A_GroupValue_Write (small) 01
      final byte data[] = HexString.valueOf("06 10 04 20 00 15 04 01 00 00 29 00 3C E0 11 06 08 0A 01 00 81");

      // The same data, telegram only
      final byte telegramData[] = HexString.valueOf("BC 11 06 08 0A E1 00 81");

      final Telegram reqTelegram = TelegramFactory.createTelegram(telegramData);
      assertNotNull(reqTelegram);

      final TunnelingRequest req = (TunnelingRequest) FrameFactory.createFrame(data);
      assertNotNull(req);

      final EmiTelegramFrame frame = (EmiTelegramFrame) req.getFrame();
      assertNotNull(frame);

      assertEquals(reqTelegram.getDest(), frame.getTelegram().getDest());
      assertEquals(reqTelegram.getApplication(), frame.getTelegram().getApplication());

      final byte[] outData = req.toByteArray();
      assertArrayEquals(data, outData);
   }

   @Test
   public final void testFromRawData1() throws IOException
   {
      // IP-RECV: 06 10 04 20 00 1b 04 01 00 00 2b 04 03 01 01 01 b0 11 0a 00 fe 63 43 40 07 b0 7d [KNXnetLink.java:380]
      // See 3.8.4 Tunneling, 4.4.6 TUNNELLING_REQUEST
      // 06 - header size
      // 10 - version
      // 04 20 - tunnelling request
      // 00 1b - total size
      // 04 - structure length
      // 01 - communication channel id
      // 00 - sequence counter
      // 00 - reserved
      // 2b - frame type
      // ...
      final byte data[] = HexString.valueOf("06 10 04 20 00 1b 04 01 00 00 2b 04 03 01 01 01 b0 11 0a 00 fe 63 43 40 07 b0 7d");
      assertNotNull(FrameFactory.createFrame(data));
   }
}
