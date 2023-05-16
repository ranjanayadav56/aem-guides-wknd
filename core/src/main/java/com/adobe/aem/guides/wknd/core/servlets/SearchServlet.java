package com.adobe.aem.guides.wknd.core.servlets;

import com.google.gson.JsonObject;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import com.adobe.aem.guides.wknd.core.service.CustomSearchService;


import org.slf4j.Logger;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import org.slf4j.LoggerFactory;
import java.io.IOException;



@Component(service = Servlet.class)
@SlingServletResourceTypes(resourceTypes = "wknd/components/page", 
                           selectors = {"myservlet","searchservlet"},
                           extensions = {"txt","xml"}, 
                           methods = {HttpConstants.METHOD_GET})

public class SearchServlet extends SlingAllMethodsServlet {
   
    private static final Logger LOG = LoggerFactory.getLogger(SearchServlet.class);

    
    @Reference
    CustomSearchService CustomSearchService;

    

    @Override
    protected void doGet( SlingHttpServletRequest req,  SlingHttpServletResponse resp) throws ServletException, IOException {

        LOG.info("Inside doget");

        JsonObject searchResult = null;
        
                
                 try {
                        String searchContent = req.getParameter("searchContent");
                        String rootPath = req.getParameter("rootPath");
                        String tempFilterPath = req.getParameter("tempFilterPath");
                            searchResult = CustomSearchService.searchresult(searchContent,rootPath,req, tempFilterPath);
                      } catch (Exception e) {
                                      LOG.info("\n ERROR {} ", e.getMessage());
                         }

              resp.setContentType("application/json");
              resp.getWriter().write(searchResult.toString());

    
    }


}