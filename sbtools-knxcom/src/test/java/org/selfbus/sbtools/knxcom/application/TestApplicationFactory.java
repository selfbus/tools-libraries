package org.selfbus.sbtools.knxcom.application;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.selfbus.sbtools.knxcom.application.Application;
import org.selfbus.sbtools.knxcom.application.ApplicationFactory;
import org.selfbus.sbtools.knxcom.application.ApplicationType;
import org.selfbus.sbtools.knxcom.application.GenericApplication;
import org.selfbus.sbtools.knxcom.application.GenericDataApplication;
import org.selfbus.sbtools.knxcom.application.IndividualAddressRead;
import org.selfbus.sbtools.knxcom.application.IndividualAddressResponse;
import org.selfbus.sbtools.knxcom.application.Restart;

public class TestApplicationFactory
{
   /**
    * These tests might suddenly fail if new Application classes are implemented
    * and added to ApplicationType.clazz
    */
   @Test
   @SuppressWarnings("deprecation")
   public final void testCreateApplication()
   {
      Application app;

      app = ApplicationFactory.createApplication(ApplicationType.None);
      assertEquals(GenericApplication.class, app.getClass());

      app = ApplicationFactory.createApplication(ApplicationType.Read_Router_Status_Response);
      assertEquals(GenericDataApplication.class, app.getClass());

      app = ApplicationFactory.createApplication(ApplicationType.IndividualAddress_Read);
      assertEquals(IndividualAddressRead.class, app.getClass());

      app = ApplicationFactory.createApplication(ApplicationType.Restart);
      assertEquals(Restart.class, app.getClass());

      app = ApplicationFactory.createApplication(ApplicationType.IndividualAddress_Response);
      assertEquals(IndividualAddressResponse.class, app.getClass());
   }
}
