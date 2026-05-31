package com.mycompany.datastructureproject.tests;

import com.mycompany.datastructureproject.core.models.PrinterStatus;
import com.mycompany.datastructureproject.core.models.Result;
import com.mycompany.datastructureproject.infrastructure.implementations.PrinterService;

public class PrinterServiceTest {

    private static int failures;

    public static boolean runTests() {
        failures = 0;
        testRejectEmptyJob();
        testAddToMainQueue();
        testOverflowToWaitingQueue();
        testRejectWhenBothQueuesFull();
        testProcessJob();
        testMoveFromWaitingAfterProcess();
        testProcessEmptyQueue();
        testShowStatus();
        return failures == 0;
    }

    private static void check(boolean condition, String name) {
        if (!condition) {
            failures++;
            System.err.println("FAIL: " + name);
        }
    }

    private static void testRejectEmptyJob() {
        PrinterService printer = new PrinterService(2, 2);
        check(!printer.addPrintJob("").isSuccess(), "rejectEmptyJob");
        check(!printer.addPrintJob("   ").isSuccess(), "rejectWhitespaceJob");
        check(!printer.addPrintJob(null).isSuccess(), "rejectNullJob");
    }

    private static void testAddToMainQueue() {
        PrinterService printer = new PrinterService(3, 2);
        Result<Void> result = printer.addPrintJob("Doc1");
        check(result.isSuccess(), "addToMainSuccess");
        check(result.getComment().contains("main queue"), "addToMainComment");
        PrinterStatus status = printer.showStatus().getData();
        check(status.getMainQueueCount() == 1, "mainQueueCountAfterAdd");
        check("Doc1".equals(status.getMainQueue().get(0)), "mainQueueContent");
    }

    private static void testOverflowToWaitingQueue() {
        PrinterService printer = new PrinterService(2, 2);
        printer.addPrintJob("A");
        printer.addPrintJob("B");
        Result<Void> result = printer.addPrintJob("C");
        check(result.isSuccess(), "overflowSuccess");
        check(result.getComment().contains("waiting queue"), "overflowComment");
        PrinterStatus status = printer.showStatus().getData();
        check(status.getMainQueueCount() == 2, "mainFullCount");
        check(status.getWaitingQueueCount() == 1, "waitingCount");
        check("C".equals(status.getWaitingQueue().get(0)), "waitingContent");
    }

    private static void testRejectWhenBothQueuesFull() {
        PrinterService printer = new PrinterService(1, 1);
        printer.addPrintJob("A");
        printer.addPrintJob("B");
        Result<Void> result = printer.addPrintJob("C");
        check(!result.isSuccess(), "bothFullRejected");
        check(result.getComment().contains("rejected"), "bothFullComment");
    }

    private static void testProcessJob() {
        PrinterService printer = new PrinterService(3, 2);
        printer.addPrintJob("First");
        printer.addPrintJob("Second");
        Result<String> result = printer.processJob();
        check(result.isSuccess(), "processSuccess");
        check("First".equals(result.getData()), "processedJobName");
        PrinterStatus status = printer.showStatus().getData();
        check("First".equals(status.getCurrentlyProcessed()), "currentlyProcessed");
        check(status.getMainQueueCount() == 1, "mainCountAfterProcess");
        check("Second".equals(status.getMainQueue().get(0)), "remainingInMain");
    }

    private static void testMoveFromWaitingAfterProcess() {
        PrinterService printer = new PrinterService(2, 2);
        printer.addPrintJob("M1");
        printer.addPrintJob("M2");
        printer.addPrintJob("W1");
        printer.processJob();
        PrinterStatus status = printer.showStatus().getData();
        check(status.getWaitingQueueCount() == 0, "waitingEmptied");
        check(status.getMainQueueCount() == 2, "waitingMovedToMain");
        check(status.getMainQueue().contains("W1"), "waitingJobInMain");
    }

    private static void testProcessEmptyQueue() {
        PrinterService printer = new PrinterService(2, 2);
        Result<String> result = printer.processJob();
        check(!result.isSuccess(), "emptyProcessFails");
        check("None".equals(printer.showStatus().getData().getCurrentlyProcessed()), "noneWhenEmpty");
    }

    private static void testShowStatus() {
        PrinterService printer = new PrinterService(2, 1);
        printer.addPrintJob("X");
        Result<PrinterStatus> result = printer.showStatus();
        check(result.isSuccess(), "statusSuccess");
        check(result.getData().getMainQueueCount() == 1, "statusMainCount");
        check(result.getComment().contains("successfully"), "statusComment");
    }
}
