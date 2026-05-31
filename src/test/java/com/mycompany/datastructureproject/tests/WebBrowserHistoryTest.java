package com.mycompany.datastructureproject.tests;

import com.mycompany.datastructureproject.core.models.Result;
import com.mycompany.datastructureproject.core.models.WebHistoryStatus;
import com.mycompany.datastructureproject.infrastructure.implementations.WebBrowserHistory;

public class WebBrowserHistoryTest {

    private static int failures;

    public static boolean runTests() {
        failures = 0;
        testFirstVisit();
        testVisitBuildsBackStack();
        testBack();
        testForward();
        testForwardClearedOnNewVisit();
        testBackWhenEmpty();
        testForwardWhenEmpty();
        testGetStatus();
        return failures == 0;
    }

    private static void check(boolean condition, String name) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + name);
        }
    }

    private static void testFirstVisit() {
        WebBrowserHistory history = new WebBrowserHistory();
        Result<Void> result = history.visitPage("https://google.com");
        check(result.isSuccess(), "firstVisitSuccess");
        check(!history.canGoBack(), "noBackOnFirstVisit");
        check("https://google.com".equals(history.getStatus().getData().getCurrentPage()), "firstCurrentPage");
    }

    private static void testVisitBuildsBackStack() {
        WebBrowserHistory history = new WebBrowserHistory();
        history.visitPage("https://google.com");
        history.visitPage("https://youtube.com");
        check(history.canGoBack(), "canGoBackAfterSecondVisit");
        WebHistoryStatus status = history.getStatus().getData();
        check(status.getBackHistory().size() == 1, "backStackSize");
        check("https://google.com".equals(status.getBackHistory().get(0)), "backStackContent");
        check("https://youtube.com".equals(status.getCurrentPage()), "currentAfterSecondVisit");
    }

    private static void testBack() {
        WebBrowserHistory history = new WebBrowserHistory();
        history.visitPage("https://google.com");
        history.visitPage("https://youtube.com");
        Result<Void> result = history.back();
        check(result.isSuccess(), "backSuccess");
        check("https://google.com".equals(history.getStatus().getData().getCurrentPage()), "currentAfterBack");
        check(history.canGoForward(), "canForwardAfterBack");
    }

    private static void testForward() {
        WebBrowserHistory history = new WebBrowserHistory();
        history.visitPage("https://google.com");
        history.visitPage("https://youtube.com");
        history.back();
        Result<Void> result = history.forward();
        check(result.isSuccess(), "forwardSuccess");
        check("https://youtube.com".equals(history.getStatus().getData().getCurrentPage()), "currentAfterForward");
    }

    private static void testForwardClearedOnNewVisit() {
        WebBrowserHistory history = new WebBrowserHistory();
        history.visitPage("https://a.com");
        history.visitPage("https://b.com");
        history.back();
        history.visitPage("https://c.com");
        check(!history.canGoForward(), "forwardCleared");
        check("https://c.com".equals(history.getStatus().getData().getCurrentPage()), "currentAfterNewVisit");
    }

    private static void testBackWhenEmpty() {
        WebBrowserHistory history = new WebBrowserHistory();
        history.visitPage("https://solo.com");
        Result<Void> result = history.back();
        check(!result.isSuccess(), "backEmptyFails");
        check("there is no pages to back".equals(result.getComment()), "backEmptyMessage");
    }

    private static void testForwardWhenEmpty() {
        WebBrowserHistory history = new WebBrowserHistory();
        history.visitPage("https://solo.com");
        Result<Void> result = history.forward();
        check(!result.isSuccess(), "forwardEmptyFails");
        check("there is no pages to forward".equals(result.getComment()), "forwardEmptyMessage");
    }

    private static void testGetStatus() {
        WebBrowserHistory history = new WebBrowserHistory();
        history.visitPage("https://one.com");
        history.visitPage("https://two.com");
        WebHistoryStatus status = history.getStatus().getData();
        check(status.getBackHistory().size() == 1, "statusBackSize");
        check(status.getForwardHistory().isEmpty(), "statusForwardEmpty");
        check("https://two.com".equals(status.getCurrentPage()), "statusCurrentPage");
    }
}
