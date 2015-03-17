package org.selfbus.sbtools.knxio.model.manufacturer.program.parameter;

import com.jgoodies.binding.beans.Model;


/**
 * Abstract base class for parameter data types.
 */
public abstract class AbstractType extends Model
{
   private static final long serialVersionUID = 1L;

   /**
    * @return The atomic type of this type.
    */
   abstract ParameterAtomicType getAtomicType();
}
