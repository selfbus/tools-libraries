package org.selfbus.sbtools.knxcom.link.netip;

import static org.junit.Assert.assertArrayEquals;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;
import org.selfbus.sbtools.knxcom.link.netip.KNXnetLink;

public class TestKNXnetLink
{
   @Test
   public void testGetNetworkAddressOf() throws UnknownHostException
   {
      final InetAddress addr = InetAddress.getByAddress("test", new byte[] { 0x7f, 0x22, 0x33, 0x44 });
      byte[] raw;

      raw = KNXnetLink.getNetworkAddressOf(addr, 40);
      assertArrayEquals(new byte[] { 0x7f, 0x22, 0x33, 0x44 }, raw);

      raw = KNXnetLink.getNetworkAddressOf(addr, 32);
      assertArrayEquals(new byte[] { 0x7f, 0x22, 0x33, 0x44 }, raw);

      raw = KNXnetLink.getNetworkAddressOf(addr, 24);
      assertArrayEquals(new byte[] { 0x7f, 0x22, 0x33, 0x00 }, raw);

      raw = KNXnetLink.getNetworkAddressOf(addr, 20);
      assertArrayEquals(new byte[] { 0x7f, 0x22, 0x30, 0x00 }, raw);

      raw = KNXnetLink.getNetworkAddressOf(addr, 16);
      assertArrayEquals(new byte[] { 0x7f, 0x22, 0x00, 0x00 }, raw);

      raw = KNXnetLink.getNetworkAddressOf(addr, 8);
      assertArrayEquals(new byte[] { 0x7f, 0x00, 0x00, 0x00 }, raw);

      raw = KNXnetLink.getNetworkAddressOf(addr, 7);
      assertArrayEquals(new byte[] { 0x7e, 0x00, 0x00, 0x00 }, raw);

      raw = KNXnetLink.getNetworkAddressOf(addr, 6);
      assertArrayEquals(new byte[] { 0x7c, 0x00, 0x00, 0x00 }, raw);

      raw = KNXnetLink.getNetworkAddressOf(addr, 5);
      assertArrayEquals(new byte[] { 0x78, 0x00, 0x00, 0x00 }, raw);

      raw = KNXnetLink.getNetworkAddressOf(addr, 4);
      assertArrayEquals(new byte[] { 0x70, 0x00, 0x00, 0x00 }, raw);

      raw = KNXnetLink.getNetworkAddressOf(addr, 3);
      assertArrayEquals(new byte[] { 0x60, 0x00, 0x00, 0x00 }, raw);

      raw = KNXnetLink.getNetworkAddressOf(addr, 2);
      assertArrayEquals(new byte[] { 0x40, 0x00, 0x00, 0x00 }, raw);

      raw = KNXnetLink.getNetworkAddressOf(addr, 1);
      assertArrayEquals(new byte[] { 0x00, 0x00, 0x00, 0x00 }, raw);

      raw = KNXnetLink.getNetworkAddressOf(addr, 0);
      assertArrayEquals(new byte[] { 0x00, 0x00, 0x00, 0x00 }, raw);
   }
}
