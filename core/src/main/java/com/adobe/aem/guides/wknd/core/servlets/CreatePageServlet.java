package com.adobe.aem.guides.wknd.core.servlets;

import java.io.IOException;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import com.adobe.aem.guides.wknd.core.service.ConfigTest;

import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;



@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Leadership servlet",
            "sling.servlet.methods=" + HttpConstants.METHOD_GET,
            "sling.servlet.paths=" + "/bin/createpage" })

public class CreatePageServlet extends SlingAllMethodsServlet {

      // private static final long serialVersionUID = -1315366566565122983L;

      @Reference
      ConfigTest configTest;

      @Reference
      ResourceResolverFactory resolverFactory;

      ResourceResolver resourceResolver;

      Session session;



      private static final Logger log = LoggerFactory.getLogger(CreatePageServlet.class);

      @Override
      protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response)
                  throws ServletException, IOException {

            try {
                  log.info("----------< Executing Query Builder Servlet >----------");

                  String pageName = "sampleServicePage";
                  String pageTitle = "Sample Service Page";
                  String path = "/content/wknd/us/en/service";
                  String renderer = "wknd/components/page";
                  Page prodPage = null;

                  String template = configTest.getTamplate();

                  response.getWriter().write("Hello!! New Page is Created");
                  resourceResolver = request.getResourceResolver();
                  session = resourceResolver.adaptTo(Session.class);

                  try {

                        if (session != null) {

                              // Create Page
                              PageManager pageManager = this.resourceResolver.adaptTo(PageManager.class);
                              prodPage = pageManager.create(path, pageName, template, pageTitle);
                              Node pageNode = prodPage.adaptTo(Node.class);

                              Node jcrNode = null;

                              if (prodPage.hasContent()) {
                                    jcrNode = prodPage.getContentResource().adaptTo(Node.class);
                              } else {
                                    jcrNode = pageNode.addNode("jcr:content", "cq:PageContent");
                              }
                              jcrNode.setProperty("sling:resourceType", renderer);

                              Node parNode = jcrNode.addNode("par");
                              parNode.setProperty("sling:resourceType", "foundation/components/parsys");

                              Node textNode = parNode.addNode("text");
                              textNode.setProperty("sling:resourceType", "foundation/components/text");
                              textNode.setProperty("textIsRich", "true");
                              textNode.setProperty("text", "Test page");

                        }
                  } catch (Exception e) {
                        log.error(e.getMessage(), e);
                  }

            } catch (Exception e) {
                  log.error(e.getMessage(), e);
            }

      }
}
