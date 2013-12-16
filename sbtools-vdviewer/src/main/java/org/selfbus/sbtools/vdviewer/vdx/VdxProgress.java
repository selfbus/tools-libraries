package org.selfbus.sbtools.vdviewer.vdx;

/**
 * Interface for progress information.
 */
public interface VdxProgress
{
   /**
    * Set the total number for 100% progress.
    * 
    * @param total - the total number.
    */
   public void setTotal(long total);
   
   /**
    * Set the current progress (0..total).
    * 
    * @param progress - the current number.
    */
   public void setProgress(long progress);
}
