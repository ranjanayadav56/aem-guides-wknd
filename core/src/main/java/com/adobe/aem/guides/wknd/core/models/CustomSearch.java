package com.adobe.aem.guides.wknd.core.models;


public interface CustomSearch {

    public String getSearchContent();
    public String getRootPath();
    public String getTempFilterPath(); 
    public String getPageSize();  
    public boolean isEmpty();
    
}
