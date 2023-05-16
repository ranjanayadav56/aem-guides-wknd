package com.adobe.aem.guides.wknd.core.service;

import org.apache.sling.api.SlingHttpServletRequest;
import com.google.gson.JsonObject;


public interface CustomSearchService {
    public JsonObject searchresult(String searchContent, 
                                    String rootPath, 
                                    SlingHttpServletRequest req, 
                                    String tempFilterPath);

}
