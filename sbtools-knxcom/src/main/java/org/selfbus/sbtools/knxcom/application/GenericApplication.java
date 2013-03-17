package org.selfbus.sbtools.knxcom.application;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A generic application without data bytes. For application types where no
 * application class exists.
 */
public class GenericApplication extends AbstractApplication
{
   private final ApplicationType type;

   /**
    * Create an instance for a specific application type.
    *
    * @param type - the application type.
    *
    * @throws IllegalArgumentException if the type is null
    */
   public GenericApplication(ApplicationType type)
   {
      if (type == null)
         throw new IllegalArgumentException("type is null");

      this.type = type;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public ApplicationType getType()
   {
      return type;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void readData(DataInput in, int length) throws IOException
   {
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public void writeData(DataOutput out) throws IOException
   {
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return type.getApci();
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;

      if (!(o instanceof GenericApplication))
         return false;

      final GenericApplication oo = (GenericApplication) o;
      return type == oo.type;
   }
}
