package org.selfbus.sbtools.prodedit.model.prodgroup.program;

import org.selfbus.sbtools.prodedit.model.global.Mask;

/**
 * Interface for adapters that encapsulate accessing an application program and mask
 * for different BCU types.
 */
public interface ProgramAdapter
{
   /**
    * @return The program
    */
   public ApplicationProgram getProgram();

   /**
    * @return The mask
    */
   public Mask getMask();

}
