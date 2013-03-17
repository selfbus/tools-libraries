package org.selfbus.sbtools.knxcom.emi;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AdditionalInfos
{
   private final static Logger LOGGER = LoggerFactory.getLogger(AdditionalInfos.class);

   private List<AdditionalInfo> infos;

   public AdditionalInfos()
   {
      super();
      infos = new ArrayList<AdditionalInfo>();
   }

   public void readFrom(DataInput in) throws IOException
   {
      int length = in.readUnsignedByte();
      while (length > 0)
      {
         AdditionalInfo info = new AdditionalInfo();
         try
         {
            info.readFrom(in, length);
            length = length - (info.getLength() + 2);
            infos.add(info);
         }
         catch (IllegalArgumentException e)
         {
            LOGGER.warn(e.getMessage());
            length = length - 1;
         }
      }
   }
   
   public void writeTo(DataOutput out) throws IOException
   {
      int length = 0;
      for (AdditionalInfo info : infos)
      {
         length += info.getLength();
      }
      out.write(length);
      for (AdditionalInfo info : infos)
      {
         info.writeTo(out);
      }
   }

   public List<AdditionalInfo> getInfos()
   {
      return infos;
   }

   public void setInfos(List<AdditionalInfo> infos)
   {
      this.infos = infos;
   }
}
