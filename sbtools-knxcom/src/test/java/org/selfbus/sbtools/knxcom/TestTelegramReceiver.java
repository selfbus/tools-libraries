package org.selfbus.sbtools.knxcom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;
import org.selfbus.sbtools.common.HexString;
import org.selfbus.sbtools.common.address.GroupAddress;
import org.selfbus.sbtools.knxcom.application.ApplicationType;
import org.selfbus.sbtools.knxcom.internal.SimulatedBusInterface;
import org.selfbus.sbtools.knxcom.telegram.Telegram;
import org.selfbus.sbtools.knxcom.telegram.TelegramFactory;
import org.selfbus.sbtools.knxcom.telegram.TelegramReceiver;

public class TestTelegramReceiver
{
   @Test
   public final void testTelegramReceiverBusInterface()
   {
      final SimulatedBusInterface busInterface = new SimulatedBusInterface();
      final TelegramReceiver recv = new TelegramReceiver(busInterface);

      assertEquals(busInterface, recv.getBusInterface());
      assertNotNull(recv.getDest());
      assertNull(recv.getApplicationType());
      assertFalse(recv.isConfirmations());
   }

   @Test
   public final void testFilter()
   {
      final SimulatedBusInterface busInterface = new SimulatedBusInterface();
      final TelegramReceiver recv = new TelegramReceiver(busInterface);

      final Telegram telegram = new Telegram();
      telegram.setDest(busInterface.getPhysicalAddress());

      assertTrue(recv.filter(telegram, false));
      assertFalse(recv.filter(telegram, true));

      telegram.setDest(GroupAddress.BROADCAST);
      assertFalse(recv.filter(telegram, false));

      recv.setDest(GroupAddress.BROADCAST);
      assertTrue(recv.filter(telegram, false));
   }

   @Test
   public final void testFilter2() throws IOException
   {
      final SimulatedBusInterface busInterface = new SimulatedBusInterface();

      final TelegramReceiver recv = new TelegramReceiver(busInterface);
      recv.setApplicationType(ApplicationType.IndividualAddress_Response);
      recv.setDest(GroupAddress.BROADCAST);

      final byte[] data = HexString.valueOf("b0 11 12 00 00 e1 01 40 ec");
      final Telegram telegram = TelegramFactory.createTelegram(data);

      assertTrue(recv.filter(telegram, false));

      recv.clear();
      recv.processTelegram(telegram, false);
      assertEquals(telegram, recv.receive(10));
      assertNull(recv.receive(10));

      recv.clear();
      recv.processTelegram(telegram, false);
      assertEquals(1, recv.receiveMultiple(0).size());
   }
}
