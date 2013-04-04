package org.selfbus.sbtools.devtool.project.model.test;

import java.io.IOException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

import org.selfbus.sbtools.common.HexString;
import org.selfbus.sbtools.devtool.project.Namespaces;
import org.selfbus.sbtools.knxcom.BusInterface;
import org.selfbus.sbtools.knxcom.application.GroupValueWrite;
import org.selfbus.sbtools.knxcom.telegram.Telegram;

/**
 * Test step that sends a write-value telegram to a group.
 */
@XmlType(name = "WriteGroupValue", namespace = Namespaces.PROJECT, propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class WriteGroupValue extends AbstractGroupValue
{
   private byte[] value;

   /**
    * @return The value that is sent.
    */
   public byte[] getValue()
   {
      return value;
   }

   /**
    * Set the value to send.
    * 
    * @param value - the value to set
    */
   public void setValue(byte[] value)
   {
      this.value = value;
   }

   /**
    * @return The value as string.
    */
   @XmlAttribute(name = "value")
   public String getValueStr()
   {
      return HexString.toString(value);
   }

   /**
    * Set the value.
    *
    * @param value - the value to set
    */
   public void setValueStr(String value)
   {
      this.value = HexString.valueOf(value);
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean perform(BusInterface bus) throws IOException
   {
      Telegram telegram = new Telegram(new GroupValueWrite(value));
      telegram.setDest(address);

      bus.send(telegram);

      return true;
   }
}
