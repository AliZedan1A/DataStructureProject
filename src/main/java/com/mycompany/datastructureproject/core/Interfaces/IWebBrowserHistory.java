
package com.mycompany.datastructureproject.core.Interfaces;

import com.mycompany.datastructureproject.core.models.*;



public interface IWebBrowserHistory {
    Result<Void> visitPage(String URI);
    Result<Void> back();
    Result<Void> forward();
    Result<WebHistoryStatus> getStatus();
    boolean canGoBack();
    boolean canGoForward();
}
