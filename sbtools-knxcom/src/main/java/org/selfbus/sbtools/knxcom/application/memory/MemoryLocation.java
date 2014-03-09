package org.selfbus.sbtools.knxcom.application.memory;

/**
 * Symbolic names for known memory addresses of a device. Which addresses
 * are available depend on the type of the device.
 * 
 * @see MemoryAddressMapper
 */
public enum MemoryLocation
{
   /**
    *
    */
   AND_TAB,

   /**
    *
    */
   ApplicationID,

   /**
    * Pointer to association table.
    */
   AssocTabPtr,

   /**
    *
    */
   BootloaderROM,

   /**
    *
    */
   Bootloadervectors,

   /**
    *
    */
   CheckLim,

   /**
    *
    */
   CommsTabPtr,

   /**
    *
    */
   ConfigDes,

   /**
    *
    */
   I_O_space1,

   /**
    *
    */
   I_O_space2,

   /**
    *
    */
   IregB_N,

   /**
    * Manufacturer data.
    */
   ManData,

   /**
    *
    */
   MaskType,

   /**
    * Mask version.
    */
   MaskVersion,

   /**
    *
    */
   MS_Buffer,

   /**
    *
    */
   MxRstCnt,

   /**
    *
    */
   OptionReg,

   /**
    *
    */
   OR_TAB,

   /**
    *
    */
   Page0ROM,

   /**
    *
    */
   PEI_Buff,

   /**
    *
    */
   PEI_Info,

   /**
    *
    */
   PEI_Interface,

   /**
    *
    */
   PEI_RecBuf,

   /**
    *
    */
   PEI_SndBuf,

   /**
    *
    */
   PEI_Type,

   /**
    * Port-A data direction register.
    */
   PortADDR,

   /**
    * Port-C data direction register.
    */
   PortCDDR,

   /**
    *
    */
   protectedEEPROM,

   /**
    *
    */
   RegB_N,

   /**
    *
    */
   RouteCnt,

   /**
    *
    */
   RunError,

   /**
    *
    */
   Stack,

   /**
    *
    */
   SyncRate,

   /**
    *
    */
   System,

   /**
    *
    */
   system1,

   /**
    *
    */
   system2,

   /**
    *
    */
   SystemROM,

   /**
    *
    */
   SystemROMvectors,

   /**
    *
    */
   SystemState,

   /**
    *
    */
   UserRAM,

   /**
    *
    */
   UserRAM2,

   /**
    *
    */
   UsrEEPROM,

   /**
    *
    */
   UsrInitPtr,

   /**
    *
    */
   UsrPrgPtr,

   /**
    *
    */
   UsrSavPtr,

   ;
}
