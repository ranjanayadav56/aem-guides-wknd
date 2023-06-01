package com.adobe.aem.guides.wknd.core.models.impl;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.core.dto.CTAsData;
import com.adobe.aem.guides.wknd.core.dto.SlideData;
import com.adobe.aem.guides.wknd.core.models.SlideBanner;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;


import org.apache.commons.lang3.StringUtils;


@Model(adaptables = {Resource.class,SlingHttpServletRequest.class}, 
      adapters = {SlideBanner.class}, 
      resourceType = {SlideBannerImpl.RESOURCE_TYPE},
      defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)

public class SlideBannerImpl implements SlideBanner {

      private static final Logger LOG = LoggerFactory.getLogger(SlideBannerImpl.class);
      protected static final String RESOURCE_TYPE = "wknd/components/slidebanner";


      @SlingObject
      Resource comResource;

      @ValueMapValue
      String text;

      @ValueMapValue
      String description;

      @ChildResource
       List<CTAsData> CTAs;

       @ChildResource
       List<SlideData> slide;

      @PostConstruct
       public void init()
       {
            CTAs = new ArrayList<>();
            slide =new  ArrayList<>();

            Resource resCTA = comResource.getChild("CTAs");
            Resource resSlide = comResource.getChild("slide");

            if(null!=resSlide && resSlide.hasChildren())
            {
                  Iterator<Resource> cards = resSlide.listChildren();
                  while(cards.hasNext())
                  {
                        Resource card = cards.next();
                        SlideData slideData = new SlideData();
                        slideData.setImage(card.getValueMap().get("image", String.class));
                        slideData.setImageText(card.getValueMap().get("imageText", String.class));
                        slide.add(slideData);
                  }
            }
       

            if(null!=resCTA && resCTA.hasChildren())
            {
                  Iterator<Resource> cards = resCTA.listChildren();
                  while(cards.hasNext())
                  {
                        Resource card = cards.next();
                        CTAsData ctAsData =new CTAsData();
                        ctAsData.setLinkText(card.getValueMap().get("linkText", String.class));
                        ctAsData.setLinkUrl(card.getValueMap().get("linkUrl", String.class));
                        ctAsData.setTarget(card.getValueMap().get("target", String.class));
                        CTAs.add (ctAsData);
                  }
            }
      }


      @Override
      public String getText() {
            return text;
      }

      @Override
      public String getDescription() {
            return description;
      }

      @Override
      public List<SlideData> getSlideData() {
            return slide;
      }

      @Override
      public List<CTAsData> getCtAsData() {
            return CTAs;
      }

      @Override
      public boolean isEmpty() {
          return StringUtils.isBlank(text);
      }
      
}
