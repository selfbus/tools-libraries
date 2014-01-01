package org.selfbus.sbtools.prodedit.model.prodgroup.program;

import org.selfbus.sbtools.prodedit.model.global.Mask;

/**
 * A program adapter for BCU1 style programs.
 */
public class Bcu1ProgramAdapter extends AbstractProgramAdapter
{
   /**
    * Create a program adapter for BCU1 style programs.
    */
   public Bcu1ProgramAdapter()
   {
      super();
   }

   /**
    * Create a program adapter for BCU1 style programs.
    *
    * @param program - the application program
    * @param mask - the mask
    */
   public Bcu1ProgramAdapter(ApplicationProgram program, Mask mask)
   {
      super(program, mask);
   }

   
}
