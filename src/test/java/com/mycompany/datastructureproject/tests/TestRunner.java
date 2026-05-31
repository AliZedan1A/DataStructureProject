package com.mycompany.datastructureproject.tests;

public class TestRunner {

    public static void main(String[] args) {
        int failedSuites = 0;

        System.out.println("=== PrinterServiceTest ===");
        if (!PrinterServiceTest.runTests()) {
            failedSuites++;
            System.out.println("PrinterServiceTest FAILED");
        } else {
            System.out.println("PrinterServiceTest PASSED");
        }

        System.out.println("=== TextEditorServiceTest ===");
        if (!TextEditorServiceTest.runTests()) {
            failedSuites++;
            System.out.println("TextEditorServiceTest FAILED");
        } else {
            System.out.println("TextEditorServiceTest PASSED");
        }

        System.out.println("=== WebBrowserHistoryTest ===");
        if (!WebBrowserHistoryTest.runTests()) {
            failedSuites++;
            System.out.println("WebBrowserHistoryTest FAILED");
        } else {
            System.out.println("WebBrowserHistoryTest PASSED");
        }

        if (failedSuites > 0) {
            System.out.println(failedSuites + " suite(s) failed.");
            System.exit(1);
        }

        System.out.println("All tests passed.");
    }
}
