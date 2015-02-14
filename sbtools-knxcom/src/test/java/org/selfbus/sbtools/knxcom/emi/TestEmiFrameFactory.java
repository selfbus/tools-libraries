package org.selfbus.sbtools.knxcom.emi;

import java.io.IOException;

import org.junit.Test;
import org.selfbus.sbtools.common.HexString;

public class TestEmiFrameFactory
{
   @Test
   public void createFrame() throws IOException
   {
      EmiFrameFactory.createFrame(HexString.valueOf("2E00A06011FF11010080"), EmiVersion.EMI2);
   }
}
