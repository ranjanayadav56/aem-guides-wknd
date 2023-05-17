package com.adobe.aem.guides.wknd.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.jcr.Session;
import com.day.cq.search.result.Hit;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.adobe.aem.guides.wknd.core.service.CustomSearchService;
import com.day.cq.search.PredicateGroup;
import com.day.cq.search.Query;
import com.day.cq.search.QueryBuilder;
import com.day.cq.search.result.SearchResult;
import com.day.cq.wcm.api.Page;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Component(service = CustomSearchService.class, immediate = true)

public class CustomSearchServiceImpl implements CustomSearchService{
    private static final Logger LOG = LoggerFactory.getLogger(CustomSearchServiceImpl.class);
    
    @Reference
    QueryBuilder queryBuilder;

    @Reference
    ResourceResolverFactory resourceResolverFactory;

    public Map<String,String> createTextSearchQuery(String searchContent, String rootPath, String tempFilterPath)
    { 
        Map<String, String> predicate = new HashMap<>();
            predicate.put("path",rootPath);
            predicate.put("property","jcr:content/cq:template");
            predicate.put("property.value",tempFilterPath);
            predicate.put("type", "cq:Page");
            predicate.put("fulltext",searchContent);
            predicate.put("p.limit", Long.toString(-1));
            return predicate;
    }

    @Override
    public JsonObject searchresult(String searchContent , String rootPath, SlingHttpServletRequest req, String tempFilterPath) {
        LOG.info("\n ------SEARCH RESULT ----- ");
        JsonObject searchResult = new JsonObject();
        try {
            ResourceResolver resourceResolver = req.getResourceResolver();
            final Session session = resourceResolver.adaptTo(Session.class);
            Query query= queryBuilder.createQuery(PredicateGroup.create(createTextSearchQuery(searchContent, rootPath, tempFilterPath)), session);
            query.setStart(0);
            SearchResult result = query.getResult();
            long totalResults = result.getTotalMatches();
           

            searchResult.addProperty("totalresult",totalResults);
            
            List<Hit> hits = result.getHits();
            JsonArray resultArray = new JsonArray();
            for (Hit hit : hits) { 
                Page page = hit.getResource().adaptTo(Page.class);
                JsonObject resulObject = new JsonObject();
                resulObject.addProperty("title", page.getTitle()!=null?page.getTitle():"");
                resulObject.addProperty("path", page.getPath());
                resulObject.addProperty("description", page.getDescription()!=null?page.getDescription():"");
                resultArray.add(resulObject);
                LOG.info("\n Page {} ", page.getPath());
            }
            searchResult.add("results", resultArray);
        } catch (Exception e) {
            LOG.info("\n ----ERROR-----{}", e.getMessage());
        }

        return searchResult;
    }
}
