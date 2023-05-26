package com.adobe.aem.guides.wknd.core.models.impl;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import static org.apache.sling.api.resource.ResourceResolver.PROPERTY_RESOURCE_TYPE;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import com.adobe.aem.guides.wknd.core.models.CarContentFragment;
import com.adobe.cq.dam.cfm.ContentFragment;
import org.apache.commons.lang3.StringUtils;


import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;




@Model(adaptables = {Resource.class}, 
      adapters = {CarContentFragment.class}, 
      resourceType = {CarContentFragmentImpl.RESOURCE_TYPE},
      defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)

public class CarContentFragmentImpl implements CarContentFragment {

      protected static final String RESOURCE_TYPE = "wknd/components/carcontentfragment";

      protected static final String BRAND_NAME = "brandName";
      protected static final String INDUSTRY_NAME = "industryName";
      protected static final String COLOR_NAME = "color";
      protected static final String CAR_IMAGE = "carImage";

      private String brandName;
      private String industryName;
      private String color;
      private String carImage;
      private boolean empty;
      private String carColorModelPath;

      @Inject
      private ResourceResolver resourceResolver;

      @ValueMapValue
      private String carCfPath;

      @PostConstruct
      public void init() {
            empty = true;
            Resource carCfPathResource = resourceResolver.getResource(carCfPath);
            if(carCfPathResource != null) {
                  ContentFragment contentFragment = carCfPathResource.adaptTo(ContentFragment.class);
                  brandName = contentFragment.getElement(BRAND_NAME).getContent();
                  industryName = contentFragment.getElement(INDUSTRY_NAME).getContent();

                  carColorModelPath = contentFragment.getElement("carColorModel").getValue().getValue(String.class);
                  Resource carColorResource = resourceResolver.getResource(carColorModelPath);

                  if(carColorResource != null) {
                        ContentFragment colorContentFragment = carColorResource.adaptTo(ContentFragment.class);
                        color = colorContentFragment.getElement(COLOR_NAME).getContent();
                        carImage = colorContentFragment.getElement(CAR_IMAGE).getContent();
                  }
                  
                  empty = false;
            }
            
      }

      @Override
      public String getBrandName() {
            return brandName;
      }

      @Override
      public String getIndustryName() {
            return industryName;
      }

      @Override
      public String getColor() {
            return color;
      }

      @Override
      public String getCarImage() {
            return carImage;
      }

      @Override
      public boolean isEmpty() {
            return StringUtils.isBlank(brandName);
    }
      
}
