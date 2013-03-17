package org.selfbus.sbtools.knxcom.application;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import org.selfbus.sbtools.knxcom.telegram.Telegram;

/**
 * Factory class for {@link Application} application instances.
 * 
 * @see Application
 * @see Telegram
 */
public final class ApplicationFactory
{
   /**
    * Create an {@link Application application} instance for a specific
    * application type.
    * <p>
    * If no application class exists for the application type, a
    * {@link GenericDataApplication} object is created if the application type
    * can contain data bytes, or a {@link GenericDataApplication} object is
    * created if the application type cannot contain data bytes.
    * 
    * @param type - the {@link ApplicationType application type}.
    * 
    * @return the created application object.
    */
   public static Application createApplication(final ApplicationType type)
   {
      final Class<? extends Application> appClass = type.getApplicationClass();

      if (appClass == null)
      {
         if (type.getMaxDataBytes() == 0)
            return new GenericApplication(type);
         return new GenericDataApplication(type);
      }

      try
      {
         return appClass.newInstance();
      }
      catch (InstantiationException e)
      {
         throw new RuntimeException(e);
      }
      catch (IllegalAccessException e)
      {
         throw new RuntimeException(e);
      }
   }

   /**
    * Create an {@link Application application} instance from the data. It is
    * expected that the first byte of the data contains the ACPI byte. This
    * method is mainly used for testing.
    * 
    * @param data - the data to process.
    * 
    * @return the created application object.
    * @throws IOException if the data cannot be read.
    */
   public static Application createApplication(final byte[] data) throws IOException
   {
      return createApplication(0, data);
   }

   /**
    * Create an {@link Application application} instance from the data. It is
    * expected that the first byte of the data contains the ACPI byte. This
    * method is mainly used for testing.
    * 
    * @param apciHighBits - the APCI high bits from the TCPI byte (?)
    * @param data - the data to process.
    * 
    * @return the created application object.
    * @throws IOException if the data cannot be read.
    */
   public static Application createApplication(int apciHighBits, byte[] data) throws IOException
   {
      final DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
      final int apci = (apciHighBits << 8) | in.readUnsignedByte();

      final ApplicationType type = ApplicationType.valueOf(apci);
      final Application app = createApplication(type);
      app.setApciValue(apci & type.getDataMask());
      app.readData(in, data.length - 1);

      return app;
   }

   /*
    * Disabled
    */
   private ApplicationFactory()
   {
   }
}
