package org.selfbus.sbtools.knxcom.emi;

import java.util.EventListener;


/**
 * Interface for classes that want to be notified when an EMI frame is received.
 */
public interface EmiFrameListener extends EventListener
{
   /**
    * An EMI frame was received.
    * The called object must not change the frame.
    * 
    * @param frame - the received frame.
    */
   public void frameReceived(EmiFrame frame);
}
