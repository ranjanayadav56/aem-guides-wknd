package com.adobe.aem.guides.wknd.core.service.impl;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.AttributeType;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.adobe.aem.guides.wknd.core.service.ConfigTest;




@Component(service = ConfigTest.class, immediate = true)
@Designate(ocd = ConfigTestImpl.ServiceConfig.class)


public class ConfigTestImpl implements ConfigTest {

      private static final Logger log = LoggerFactory.getLogger(ConfigTestImpl.class);

      @ObjectClassDefinition(name = "Registering-OSGi Configuration", description = "This is OSGI configuration for Tamplate Path.")

            
      public @interface ServiceConfig {

            @AttributeDefinition(
            name = "Tamplate Path", description = "Enter path here", type = AttributeType.STRING)
            public String myName();

      }
      
      private String tamplate;

      @Activate
      protected void activate(ServiceConfig serviceConfig) {
            tamplate = serviceConfig.myName();
            log.info(tamplate);
      }

            
      @Override
      public String getTamplate() {
            return tamplate;
      }

}