package org.selfbus.sbtools.knxcom.emi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.junit.Test;
import org.selfbus.sbtools.common.HexString;
import org.selfbus.sbtools.knxcom.application.ApplicationType;
import org.selfbus.sbtools.knxcom.application.GroupValueWrite;
import org.selfbus.sbtools.knxcom.telegram.Telegram;

public class TestL_Busmon_ind
{
   @Test
   public final void testReadDataNack() throws IOException
   {
      final byte[] data = HexString.valueOf("2b 01 22 33 90 11 06 11 ff 60 cb 3d");

      final L_Busmon_ind frame = (L_Busmon_ind) EmiFrameFactory.createFrame(data, EmiVersion.EMI2);
      assertNotNull(frame);

      assertEquals(1, frame.getStatus());
      assertEquals(0x2233, frame.getTimestamp());
   }

   @Test()
   public final void testRead1() throws IOException
   {
      final byte[] data = HexString.valueOf("2b 04 03 01 01 01 b0 11 0a 00 fe 63 43 40 07 b0 7d");

      final CEmiFrame frame = new CEmiFrame();
      frame.readData(new DataInputStream(new ByteArrayInputStream(data)));
   }

   @Test
   public final void testGroupValueWrite() throws IOException
   {
      final byte[] data = HexString.valueOf("2b 01 22 33 bc 11 0c 08 0a e1 00 81 3c");

      final L_Busmon_ind frame = (L_Busmon_ind) EmiFrameFactory.createFrame(data, EmiVersion.EMI2);
      assertNotNull(frame);

      final Telegram telegram = frame.getTelegram();
      assertNotNull(telegram);
      assertEquals(ApplicationType.GroupValue_Write, telegram.getApplicationType());

      final GroupValueWrite app = (GroupValueWrite) telegram.getApplication();
      assertNotNull(app);

      assertEquals(1, app.getApciValue());
      
   }
}
