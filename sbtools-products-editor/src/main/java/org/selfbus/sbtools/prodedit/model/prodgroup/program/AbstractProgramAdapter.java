package org.selfbus.sbtools.prodedit.model.prodgroup.program;

import org.selfbus.sbtools.prodedit.model.global.Mask;
import org.selfbus.sbtools.prodedit.model.prodgroup.program.ApplicationProgram;

/**
 * An adapter that encapsulates accessing an application program and mask
 * for different BCU types.
 */
public abstract class AbstractProgramAdapter implements ProgramAdapter
{
   protected ApplicationProgram program;
   protected Mask mask;

   /**
    * Create an adapter that encapsulates accessing an application program and mask
    * for different BCU types.
    */
   public AbstractProgramAdapter()
   {
      this(null, null);
   }

   /**
    * Create an adapter that encapsulates accessing an application program and mask
    * for different BCU types.
    *
    * @param program - the application program
    * @param mask - the mask
    */
   public AbstractProgramAdapter(ApplicationProgram program, Mask mask)
   {
      this.program = program;
      this.mask = mask;
   }

   /**
    * @return the program
    */
   @Override
   public ApplicationProgram getProgram()
   {
      return program;
   }

   /**
    * @param program the program to set
    */
   public void setProgram(ApplicationProgram program)
   {
      this.program = program;
   }

   /**
    * @return the mask
    */
   @Override
   public Mask getMask()
   {
      return mask;
   }

   /**
    * @param mask the mask to set
    */
   public void setMask(Mask mask)
   {
      this.mask = mask;
   }
}
