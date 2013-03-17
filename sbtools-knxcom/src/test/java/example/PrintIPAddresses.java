package example;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Print all local IP addresses to the console.
 */
public class PrintIPAddresses
{
   final static Logger LOGGER = LoggerFactory.getLogger(PrintIPAddresses.class);

   public static void main(String[] args)
   {
      try
      {
         InetAddress localhost = InetAddress.getLocalHost();
         LOGGER.info("Localhost IP Addr: " + localhost.getHostAddress());

         // Just in case this host has multiple IP addresses....
         InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
         if (allMyIps != null && allMyIps.length > 1)
         {
            LOGGER.info("Full list of IP addresses:");
            for (int i = 0; i < allMyIps.length; i++)
               LOGGER.info("    " + allMyIps[i]);
         }
      }
      catch (UnknownHostException e)
      {
         LOGGER.info(" (error retrieving server host name)");
      }

      try
      {
         LOGGER.info("Full list of Network Interfaces:");
         for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();)
         {
            NetworkInterface intf = en.nextElement();
            LOGGER.info("    " + intf.getName() + " " + intf.getDisplayName());
            for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();)
               LOGGER.info("        " + enumIpAddr.nextElement().toString());
         }
      }
      catch (SocketException e)
      {
         LOGGER.info(" (error retrieving network interface list)");
      }

   }
}
