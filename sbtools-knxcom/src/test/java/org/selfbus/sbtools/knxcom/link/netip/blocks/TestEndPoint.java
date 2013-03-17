package org.selfbus.sbtools.knxcom.link.netip.blocks;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;
import org.selfbus.sbtools.knxcom.link.netip.blocks.EndPoint;
import org.selfbus.sbtools.knxcom.link.netip.types.TransportType;

public class TestEndPoint
{
   @Test
   public final void testEndPoint()
   {
      final EndPoint endPoint = new EndPoint();
      assertNotNull(endPoint.hashCode());
   }

   @Test
   public final void testEndPointTransportTypeInetAddressInt() throws UnknownHostException
   {
      final InetAddress addr = InetAddress.getByName("localhost");
      final EndPoint endPoint = new EndPoint(TransportType.UDP, addr, 3600);

      assertEquals(TransportType.UDP, endPoint.getTransportType());
      assertEquals(addr, endPoint.getAddress());
      assertEquals(3600, endPoint.getPort());

      assertNotNull(endPoint.hashCode());
   }

   @Test
   public final void testGetSetTransportType()
   {
      final EndPoint endPoint = new EndPoint();

      endPoint.setTransportType(TransportType.TCP);
      assertEquals(TransportType.TCP, endPoint.getTransportType());

      endPoint.setTransportType(TransportType.UDP);
      assertEquals(TransportType.UDP, endPoint.getTransportType());
   }

   @Test
   public final void testGetSetAddress() throws UnknownHostException
   {
      final EndPoint endPoint = new EndPoint();
      final InetAddress addr = InetAddress.getByName("localhost");

      endPoint.setAddress(addr);
      assertEquals(addr, endPoint.getAddress());
   }

   @Test
   public final void testGetSetPort() throws UnknownHostException
   {
      final EndPoint endPoint = new EndPoint();

      endPoint.setPort(1234);
      assertEquals(1234, endPoint.getPort());

      endPoint.setPort(10000);
      assertEquals(10000, endPoint.getPort());
   }

   @Test
   public final void testReadWriteData() throws IOException
   {
      final byte[] inData = new byte[] { 0x08, 0x01, 0x7f, 0x00, 0x00, 0x01, 0x30, 0x54 };
      final DataInputStream in = new DataInputStream(new ByteArrayInputStream(inData));

      final EndPoint endPoint = new EndPoint();
      endPoint.readData(in);

      assertEquals(TransportType.UDP, endPoint.getTransportType());
      assertEquals(InetAddress.getByName("127.0.0.1"), endPoint.getAddress());
      assertEquals(12372, endPoint.getPort());

      final ByteArrayOutputStream outStream = new ByteArrayOutputStream(256);
      final DataOutputStream out = new DataOutputStream(outStream);
      endPoint.writeData(out);

      assertArrayEquals(inData, outStream.toByteArray());
   }
}
