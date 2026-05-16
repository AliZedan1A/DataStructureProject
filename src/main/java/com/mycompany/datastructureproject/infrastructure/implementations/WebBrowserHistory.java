package com.mycompany.datastructureproject.infrastructure.implementations;

import com.mycompany.datastructureproject.core.Interfaces.IWebBrowserHistory;
import com.mycompany.datastructureproject.core.models.Result;
import com.mycompany.datastructureproject.core.models.WebHistoryStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class WebBrowserHistory implements IWebBrowserHistory {

    private Stack<String> backStack;
    private Stack<String> forwardStack;
    private String currentPage;

    public WebBrowserHistory()
    {
        backStack = new Stack<>();
        forwardStack = new Stack<>();
    }
    public Result<Void> visitPage(String URI) {
        if (currentPage == null) {
            currentPage = URI;
        } else {
            backStack.push(currentPage);
            currentPage = URI;
        }
        forwardStack.clear(); // like real websites
        return new Result(true);
    }

    public Result<Void> back() {
        if (canGoBack()) {
            forwardStack.push(currentPage);
            currentPage = backStack.pop();
            return new Result(true);
        }
        else{
            return new Result(false,"there is no pages to back");
        }

    }

    public Result<Void> forward() {
        if(canGoForward())
        {
            backStack.push(currentPage);
            currentPage = forwardStack.pop();
            return new Result(true);
        }else{
            return new Result(false,"there is no pages to forward");
        }
    }

    public Result<WebHistoryStatus> getStatus() {
        List<String> backs = new ArrayList<>(backStack);
        List<String> forwards = new ArrayList<>(forwardStack);
        return new Result(true,
                new WebHistoryStatus(backs,forwards,this.currentPage
                ));
        
        
        
    }

    public boolean canGoForward() {
        return !forwardStack.empty();
    }

    public boolean canGoBack() {
        return !backStack.empty();
    }
}
