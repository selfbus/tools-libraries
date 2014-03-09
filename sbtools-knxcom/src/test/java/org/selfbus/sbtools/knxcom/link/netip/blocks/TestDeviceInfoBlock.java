package org.selfbus.sbtools.knxcom.link.netip.blocks;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;
import org.selfbus.sbtools.common.address.PhysicalAddress;
import org.selfbus.sbtools.knxcom.types.MediumType;

public class TestDeviceInfoBlock
{
   @Test
   public final void testDeviceInfoBlock()
   {
      final DeviceInfoBlock dib = new DeviceInfoBlock();

      assertEquals(MediumType.TWISTED_PAIR, dib.getMedium());
      assertEquals(PhysicalAddress.NULL, dib.getBusAddress());
   }

   @Test
   public final void testDeviceInfoBlockMediumType()
   {
      final DeviceInfoBlock dib = new DeviceInfoBlock(MediumType.RADIO_FREQ);

      assertEquals(MediumType.RADIO_FREQ, dib.getMedium());
      assertEquals(PhysicalAddress.NULL, dib.getBusAddress());
   }

   @Test
   public final void testGetSetMedium()
   {
      final DeviceInfoBlock dib = new DeviceInfoBlock();

      dib.setMedium(MediumType.RADIO_FREQ);
      assertEquals(MediumType.RADIO_FREQ, dib.getMedium());

      dib.setMedium(MediumType.POWER_LINE);
      assertEquals(MediumType.POWER_LINE, dib.getMedium());
   }

   @Test
   public final void testGetSetStatus()
   {
      final DeviceInfoBlock dib = new DeviceInfoBlock();

      dib.setStatus(41);
      assertEquals(41, dib.getStatus());

      dib.setStatus(0);
      assertEquals(0, dib.getStatus());
   }

   @Test
   public final void testGetSetBusAddress()
   {
      final DeviceInfoBlock dib = new DeviceInfoBlock();

      dib.setBusAddress(PhysicalAddress.ONE);
      assertEquals(PhysicalAddress.ONE, dib.getBusAddress());

      dib.setBusAddress(PhysicalAddress.NULL);
      assertEquals(PhysicalAddress.NULL, dib.getBusAddress());
   }

   @Test
   public final void testGetSetProjectId()
   {
      final DeviceInfoBlock dib = new DeviceInfoBlock();

      dib.setProjectId(0x0815);
      assertEquals(0x0815, dib.getProjectId());

      dib.setProjectId(0x0817);
      assertEquals(0x0817, dib.getProjectId());
   }

   @Test
   public final void testGetSetName()
   {
      final DeviceInfoBlock dib = new DeviceInfoBlock();

      dib.setName("dib-1");
      assertEquals("dib-1", dib.getName());
   }

   @Test
   public final void testGetSetRoutingAddr() throws UnknownHostException
   {
      final DeviceInfoBlock dib = new DeviceInfoBlock();

      dib.setRoutingAddr(InetAddress.getByName("localhost"));
      assertEquals(InetAddress.getByName("localhost"), dib.getRoutingAddr());

      dib.setRoutingAddr(null);
      assertNull(dib.getRoutingAddr());
   }

   @Test
   public final void testReadWriteData() throws IOException
   {
      final DeviceInfoBlock dib = new DeviceInfoBlock();
      dib.setMedium(MediumType.RADIO_FREQ);
      dib.setRoutingAddr(InetAddress.getByName("localhost"));
      dib.setName("test-1");
      dib.setBusAddress(PhysicalAddress.ONE);
      dib.setProjectId(1240);

      final ByteArrayOutputStream outStream = new ByteArrayOutputStream(256);
      final DataOutputStream out = new DataOutputStream(outStream);
      dib.writeData(out);

      final byte[] outData = outStream.toByteArray();
      assertEquals(outData[0], outData.length);

      final DataInputStream in = new DataInputStream(new ByteArrayInputStream(outData));
      final DeviceInfoBlock dibRead = new DeviceInfoBlock();
      dibRead.readData(in);

      assertEquals(MediumType.RADIO_FREQ, dibRead.getMedium());
      assertEquals(InetAddress.getByName("localhost"), dibRead.getRoutingAddr());
      assertEquals("test-1", dibRead.getName());
      assertEquals(PhysicalAddress.ONE, dibRead.getBusAddress());
      assertEquals(1240, dibRead.getProjectId());
   }
}
