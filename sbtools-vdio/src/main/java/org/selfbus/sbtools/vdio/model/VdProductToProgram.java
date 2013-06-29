package org.selfbus.sbtools.vdio.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * Product to Program.
 */
@XmlType(name = "ProductToProgram", propOrder = {})
@XmlAccessorType(XmlAccessType.NONE)
public class VdProductToProgram
{
   @XmlAttribute(name = "prod2prog_id", required = true)
   private int id;

   @XmlAttribute(name = "product_id", required = true)
   private int productId;

   @XmlAttribute(name = "program_id", required = true)
   private int programId;

   @XmlAttribute(name = "prod2prog_status")
   private String status;

   @XmlAttribute(name = "pei_program_id")
   private Integer peiProgramId;

   @XmlAttribute(name = "prod2prog_status_code")
   private int statusCode;

   @XmlAttribute(name = "registration_number")
   private Integer registrationNumber;

   @XmlAttribute(name = "registration_year")
   private Integer registrationYear;

   @XmlAttribute(name = "original_registration_number")
   private Integer originalRegistrationNumber;

   @XmlAttribute(name = "original_registration_year")
   private Integer originalRegistrationYear;

   @XmlAttribute(name = "expiration_date")
   private String expirationDate;

   @XmlAttribute(name = "registration_ts")
   private String registrationTS;

   /**
    * Create an object type object.
    */
   public VdProductToProgram()
   {
   }

   /**
    * @return the id
    */
   public int getId()
   {
      return id;
   }

   /**
    * @param id the id to set
    */
   public void setId(int id)
   {
      this.id = id;
   }

   /**
    * @return the productId
    */
   public int getProductId()
   {
      return productId;
   }

   /**
    * @param productId the productId to set
    */
   public void setProductId(int productId)
   {
      this.productId = productId;
   }

   /**
    * @return the programId
    */
   public int getProgramId()
   {
      return programId;
   }

   /**
    * @param programId the programId to set
    */
   public void setProgramId(int programId)
   {
      this.programId = programId;
   }

   /**
    * @return the status
    */
   public String getStatus()
   {
      return status;
   }

   /**
    * @param status the status to set
    */
   public void setStatus(String status)
   {
      this.status = status;
   }

   /**
    * @return the peiProgramId
    */
   public Integer getPeiProgramId()
   {
      return peiProgramId;
   }

   /**
    * @param peiProgramId the peiProgramId to set
    */
   public void setPeiProgramId(Integer peiProgramId)
   {
      this.peiProgramId = peiProgramId;
   }

   /**
    * @return the statusCode
    */
   public int getStatusCode()
   {
      return statusCode;
   }

   /**
    * @param statusCode the statusCode to set
    */
   public void setStatusCode(int statusCode)
   {
      this.statusCode = statusCode;
   }

   /**
    * @return the registrationNumber
    */
   public Integer getRegistrationNumber()
   {
      return registrationNumber;
   }

   /**
    * @param registrationNumber the registrationNumber to set
    */
   public void setRegistrationNumber(Integer registrationNumber)
   {
      this.registrationNumber = registrationNumber;
   }

   /**
    * @return the registrationYear
    */
   public Integer getRegistrationYear()
   {
      return registrationYear;
   }

   /**
    * @param registrationYear the registrationYear to set
    */
   public void setRegistrationYear(Integer registrationYear)
   {
      this.registrationYear = registrationYear;
   }

   /**
    * @return the originalRegistrationNumber
    */
   public Integer getOriginalRegistrationNumber()
   {
      return originalRegistrationNumber;
   }

   /**
    * @param originalRegistrationNumber the originalRegistrationNumber to set
    */
   public void setOriginalRegistrationNumber(Integer originalRegistrationNumber)
   {
      this.originalRegistrationNumber = originalRegistrationNumber;
   }

   /**
    * @return the originalRegistrationYear
    */
   public Integer getOriginalRegistrationYear()
   {
      return originalRegistrationYear;
   }

   /**
    * @param originalRegistrationYear the originalRegistrationYear to set
    */
   public void setOriginalRegistrationYear(Integer originalRegistrationYear)
   {
      this.originalRegistrationYear = originalRegistrationYear;
   }

   /**
    * @return the expirationDate
    */
   public String getExpirationDate()
   {
      return expirationDate;
   }

   /**
    * @param expirationDate the expirationDate to set
    */
   public void setExpirationDate(String expirationDate)
   {
      this.expirationDate = expirationDate;
   }

   /**
    * @return the registrationTS
    */
   public String getRegistrationTS()
   {
      return registrationTS;
   }

   /**
    * @param registrationTS the registrationTS to set
    */
   public void setRegistrationTS(String registrationTS)
   {
      this.registrationTS = registrationTS;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public int hashCode()
   {
      return id;
   }

   /**
    * {@inheritDoc}
    */
   @Override
   public boolean equals(Object o)
   {
      if (o == this)
         return true;
      if (!(o instanceof VdProductToProgram))
         return false;
      final VdProductToProgram oo = (VdProductToProgram) o;
      return id == oo.id;
   }
}
