
package com.mycompany.datastructureproject.core.models;

import java.util.List;

public class WebHistoryStatus {
    private List<String> BackHistory;
    private List<String> ForwardHistory;
    private String CurrentPage;
    
    public WebHistoryStatus(List<String>backHistory,
            List<String>forwardHistory,
            String currentPage)
    {
        this.BackHistory = backHistory;
        this.ForwardHistory = forwardHistory;
        this.CurrentPage = currentPage;
    }
    
    public List<String> getBackHistory()
    {
        return this.BackHistory;
    }
    public List<String> getForwardHistory()
    {
        return this.ForwardHistory;
    }
    public String getCurrentPage(){
        return this.CurrentPage;
    }
}
