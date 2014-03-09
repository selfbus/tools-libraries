package org.selfbus.sbtools.knxcom.emi.types;

import org.selfbus.sbtools.knxcom.emi.EmiFrame;
import org.selfbus.sbtools.knxcom.emi.EmiVersion;
import org.selfbus.sbtools.knxcom.emi.L_Busmon_ind;
import org.selfbus.sbtools.knxcom.emi.L_Data_con;
import org.selfbus.sbtools.knxcom.emi.L_Data_ind;
import org.selfbus.sbtools.knxcom.emi.L_Data_req;
import org.selfbus.sbtools.knxcom.emi.L_Poll_Data_req;
import org.selfbus.sbtools.knxcom.emi.PEI_Identify_con;
import org.selfbus.sbtools.knxcom.emi.PEI_Identify_req;
import org.selfbus.sbtools.knxcom.emi.PEI_Switch_req;

/**
 * External Message Interface (EMI) frame types.
 */
public enum EmiFrameType
{
   /**
    * A telegram in bus monitor mode was received.
    */
   EMI1_L_BUSMON_IND(EmiVersion.EMI2, 0x49, EmiFrameClass.RECEIVE, L_Busmon_ind.class),
   EMI2_L_BUSMON_IND(EmiVersion.EMI2, 0x2b, EmiFrameClass.RECEIVE, L_Busmon_ind.class),
   cEMI_L_BUSMON_IND(EmiVersion.cEMI, 0x2b, EmiFrameClass.RECEIVE, L_Busmon_ind.class),

   /**
    * Request to send plain data.
    */
   EMI2_L_PLAIN_DATA_REQ(EmiVersion.EMI2, 0x10, EmiFrameClass.SEND),

   /**
    * Link data send request.
    */
   EMI1_L_DATA_REQ(EmiVersion.EMI1, 0x11, EmiFrameClass.SEND, L_Data_req.class),
   EMI2_L_DATA_REQ(EmiVersion.EMI2, 0x11, EmiFrameClass.SEND, L_Data_req.class),
   cEMI_L_DATA_REQ(EmiVersion.cEMI, 0x11, EmiFrameClass.SEND, L_Data_req.class, null, EmiFrameFormat.cEMI),

   /**
    * Link data send confirmation.
    */
   EMI1_L_DATA_CON(EmiVersion.EMI1, 0x4e, EmiFrameClass.CONFIRM, L_Data_con.class, EMI1_L_DATA_REQ),
   EMI2_L_DATA_CON(EmiVersion.EMI2, 0x2e, EmiFrameClass.CONFIRM, L_Data_con.class, EMI2_L_DATA_REQ),
   cEMI_L_DATA_CON(EmiVersion.cEMI, 0x2e, EmiFrameClass.CONFIRM, L_Data_con.class, cEMI_L_DATA_REQ, EmiFrameFormat.cEMI),

   /**
    * Link data receive indication.
    */
   EMI1_L_DATA_IND(EmiVersion.EMI1, 0x49, EmiFrameClass.RECEIVE, L_Data_ind.class),
   EMI2_L_DATA_IND(EmiVersion.EMI2, 0x29, EmiFrameClass.RECEIVE, L_Data_ind.class),
   cEMI_L_DATA_IND(EmiVersion.cEMI, 0x29, EmiFrameClass.RECEIVE, L_Data_ind.class, null, EmiFrameFormat.cEMI),

   /**
    * Poll data request.
    */
   EMI2_L_POLL_DATA_REQ(EmiVersion.EMI2, 0x13, EmiFrameClass.SEND, L_Poll_Data_req.class),
   cEMI_L_POLL_DATA_REQ(EmiVersion.cEMI, 0x13, EmiFrameClass.SEND, L_Poll_Data_req.class, null, EmiFrameFormat.cEMI),

   /**
    * Poll data send confirmation.
    */
   EMI2_L_POLL_DATA_CON(EmiVersion.EMI2, 0x25, EmiFrameClass.CONFIRM, null, EMI2_L_POLL_DATA_REQ),
   cEMI_L_POLL_DATA_CON(EmiVersion.cEMI, 0x25, EmiFrameClass.CONFIRM, null, cEMI_L_POLL_DATA_REQ, EmiFrameFormat.cEMI),

   /**
    * Raw data send request. cEMI frames only.
    */
   cEMI_L_RAW_REQ(EmiVersion.cEMI, 0x10, EmiFrameClass.SEND),

   /**
    * Raw data receive indication. cEMI frames only.
    */
   cEMI_L_RAW_IND(EmiVersion.cEMI, 0x2d, EmiFrameClass.RECEIVE),

   /**
    * Raw data send confirmation. cEMI frames only.
    */
   cEMI_L_RAW_CON(EmiVersion.cEMI, 0x2f, EmiFrameClass.CONFIRM),

   EMI2_N_DATA_INDIVIDUAL_REQ(EmiVersion.EMI2, 0x21, EmiFrameClass.SEND),

   EMI2_N_DATA_INDIVIDUAL_CON(EmiVersion.EMI2, 0x4e, EmiFrameClass.CONFIRM, null, EMI2_N_DATA_INDIVIDUAL_REQ),

   EMI2_N_DATA_INDIVIDUAL_IND(EmiVersion.EMI2, 0x49, EmiFrameClass.RECEIVE),

   EMI2_N_DATA_GROUP_REQ(EmiVersion.EMI2, 0x22, EmiFrameClass.SEND),

   EMI2_N_DATA_GROUP_CON(EmiVersion.EMI2, 0x3e, EmiFrameClass.CONFIRM, null, EMI2_N_DATA_GROUP_REQ),

   EMI2_N_DATA_GROUP_IND(EmiVersion.EMI2, 0x3a, EmiFrameClass.RECEIVE),

   EMI2_N_DATA_BROADCAST_REQ(EmiVersion.EMI2, 0x2c, EmiFrameClass.SEND),

   EMI2_N_DATA_BROADCAST_CON(EmiVersion.EMI2, 0x4f, EmiFrameClass.CONFIRM, null, EMI2_N_DATA_BROADCAST_REQ),

   EMI2_N_DATA_BROADCAST_IND(EmiVersion.EMI2, 0x4d, EmiFrameClass.RECEIVE),

   EMI2_N_POLL_DATA_REQ(EmiVersion.EMI2, 0x23, EmiFrameClass.SEND),

   EMI2_N_POLL_DATA_CON(EmiVersion.EMI2, 0x35, EmiFrameClass.CONFIRM, null, EMI2_N_POLL_DATA_REQ),

   EMI1_T_DATA_CONNECTED_REQ(EmiVersion.EMI1, 0x21, EmiFrameClass.SEND),
   EMI2_T_DATA_CONNECTED_REQ(EmiVersion.EMI2, 0x41, EmiFrameClass.SEND),

   EMI2_T_DATA_CONNECTED_CON(EmiVersion.EMI2, 0x8e, EmiFrameClass.CONFIRM),

   EMI1_T_DATA_CONNECTED_IND(EmiVersion.EMI1, 0x49, EmiFrameClass.RECEIVE),
   EMI2_T_DATA_CONNECTED_IND(EmiVersion.EMI2, 0x89, EmiFrameClass.RECEIVE),

   EMI1_T_CONNECT_REQ(EmiVersion.EMI1, 0x23, EmiFrameClass.SEND),
   EMI2_T_CONNECT_REQ(EmiVersion.EMI2, 0x43, EmiFrameClass.SEND),

   EMI2_T_CONNECT_CON(EmiVersion.EMI2, 0x86, EmiFrameClass.CONFIRM, null, EMI2_T_CONNECT_REQ),

   EMI1_T_CONNECT_IND(EmiVersion.EMI1, 0x43, EmiFrameClass.RECEIVE),
   EMI2_T_CONNECT_IND(EmiVersion.EMI2, 0x85, EmiFrameClass.RECEIVE),

   EMI1_T_DISCONNECT_REQ(EmiVersion.EMI1, 0x24, EmiFrameClass.SEND),
   EMI2_T_DISCONNECT_REQ(EmiVersion.EMI2, 0x44, EmiFrameClass.SEND),

   EMI2_T_DISCONNECT_CON(EmiVersion.EMI2, 0x88, EmiFrameClass.CONFIRM, null, EMI2_T_DISCONNECT_REQ),

   EMI1_T_DISCONNECT_IND(EmiVersion.EMI1, 0x44, EmiFrameClass.RECEIVE),
   EMI2_T_DISCONNECT_IND(EmiVersion.EMI2, 0x87, EmiFrameClass.RECEIVE),

   EMI1_U_VALUE_READ_REQ(EmiVersion.EMI1, 0x35, EmiFrameClass.SEND),
   EMI2_U_VALUE_READ_REQ(EmiVersion.EMI2, 0x74, EmiFrameClass.SEND),

   EMI1_U_VALUE_READ_CON(EmiVersion.EMI1, 0x45, EmiFrameClass.CONFIRM),
   EMI2_U_VALUE_READ_CON(EmiVersion.EMI2, 0xe4, EmiFrameClass.CONFIRM),

   EMI1_U_FLAGS_READ_REQ(EmiVersion.EMI1, 0x37, EmiFrameClass.SEND),
   EMI2_U_FLAGS_READ_REQ(EmiVersion.EMI2, 0x7c, EmiFrameClass.SEND),

   EMI1_U_FLAGS_READ_CON(EmiVersion.EMI1, 0x47, EmiFrameClass.CONFIRM),
   EMI2_U_FLAGS_READ_CON(EmiVersion.EMI2, 0xec, EmiFrameClass.CONFIRM),

   EMI1_U_EVENT_IND(EmiVersion.EMI1, 0x4d, EmiFrameClass.RECEIVE),
   EMI2_U_EVENT_IND(EmiVersion.EMI2, 0xe7, EmiFrameClass.RECEIVE),

   EMI1_U_VALUE_WRITE_REQ(EmiVersion.EMI1, 0x36, EmiFrameClass.SEND),
   EMI2_U_VALUE_WRITE_REQ(EmiVersion.EMI2, 0x71, EmiFrameClass.SEND),

   /**
    * Request for an identification of the PEI (physical external interface).
    * Reply is {@link #PEI_IDENTIFY_CON}.
    */
   EMI2_PEI_IDENTIFY_REQ(EmiVersion.EMI2, 0xa7, EmiFrameClass.SEND, PEI_Identify_req.class),

   /**
    * Reply to {@link #PEI_IDENTIFY_REQ}: the physical address of the BCU.
    */
   EMI2_PEI_IDENTIFY_CON(EmiVersion.EMI2, 0xa8, EmiFrameClass.CONFIRM, PEI_Identify_con.class, EMI2_PEI_IDENTIFY_REQ),

   /**
    * Switch the PEI mode of the BCU.
    */
   EMI2_PEI_SWITCH_REQ(EmiVersion.EMI2, 0xa9, EmiFrameClass.SEND, PEI_Switch_req.class),

   /**
    * Timer.
    */
   EMI2_TM_TIMER_IND(EmiVersion.EMI2, 0xc1, EmiFrameClass.RECEIVE),

   /**
    * Bus-level acknowledgment. This is not a real frame type. It is used to transport
    * the acknowledgments as frames.
    */
   BUS_ACK(EmiVersion.UNKNOWN, 0xcc, EmiFrameClass.CONFIRM),

   /**
    * An unknown message (this is an internal type).
    */
   UNKNOWN(EmiVersion.UNKNOWN, 0x00, null);

   /**
    * The message-type code.
    */
   public final int code;
   
   /**
    * The emi version (EMI1/EMI2/cEMI).
    */
   public final EmiVersion version;

   /**
    * The classification of the frame.
    */
   public final EmiFrameClass frameClass;

   /**
    * The message-type for which this message-type is a confirmation. E.g. for
    * L_DATA_CON is a confirmation for L_DATA_REQ. Null if this is no
    * confirmation message-type.
    */
   public final EmiFrameType confirmationForType;

   /**
    * Format of the frame.
    */
   public final EmiFrameFormat frameFormat;

   /**
    * The class that is used for messages of this type. May be null.
    */
   public final Class<? extends EmiFrame> clazz;

   /**
    * @return true if the message-type is a confirmation for another message
    *         type.
    */
   public boolean isConfirmation()
   {
      return frameClass == EmiFrameClass.CONFIRM;
   }

   /**
    * @return a human readable label for the frame type.
    */
   public String getLabel()
   {
      int pos = name().lastIndexOf('_');
      if (pos < 1)
         return name();

      return name().substring(0, pos) + '.' + name().substring(pos + 1).toLowerCase();
   }

   /**
    * Lookup the EMI frame type for a frame type code.
    *
    * @param code - the frame type code
    * @return the message-code for the given id.
    *
    * @throws IllegalArgumentException if the frame type code is invalid
    */
   static public EmiFrameType valueOf(int code, EmiVersion version)
   {
      for (EmiFrameType e : values())
         if (e.code == code && e.version == version)
            return e;

      throw new IllegalArgumentException("Unknown EMI frame type code 0x" + Integer.toHexString(code));
   }

   /**
    * Test if the frame type code is valid.
    *
    * @param code - the frame type code to test.
    * @return true if the given code is valid.
    */
   static public boolean isValid(int code, EmiVersion version)
   {
      for (EmiFrameType e : values())
         if (e.code == code && e.version == version)
            return true;

      return false;
   }

   /*
    * Internal constructor
    */
   private EmiFrameType(EmiVersion version, int code, EmiFrameClass frameClass, Class<? extends EmiFrame> clazz, EmiFrameType confirmationForType, EmiFrameFormat frameFormat)
   {
      this.code = code;
      this.frameClass = frameClass;
      this.clazz = clazz;
      this.confirmationForType = confirmationForType;
      this.version = version;
      this.frameFormat = frameFormat;
   }

   /*
    * Internal constructor
    */
   private EmiFrameType(EmiVersion version, int code, EmiFrameClass frameClass, Class<? extends EmiFrame> clazz, EmiFrameType confirmationForType)
   {
      this(version, code, frameClass, clazz, confirmationForType, EmiFrameFormat.RAW);
   }

   /*
    * Internal constructor
    */
   private EmiFrameType(EmiVersion version, int code, EmiFrameClass frameClass, Class<? extends EmiFrame> clazz)
   {
      this(version, code, frameClass, clazz, null, EmiFrameFormat.RAW);
   }

   /*
    * Internal constructor
    */
   private EmiFrameType(EmiVersion version, int code, EmiFrameClass frameClass)
   {
      this(version, code, frameClass, null, null, EmiFrameFormat.RAW);
   }
}
