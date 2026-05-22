
package com.mycompany.datastructureproject.core.models;

import java.util.List;

public class PrinterStatus {
    private List<String> MainQueue;
    private List<String>WaitingQueue;
    private int MainQueueCount;
    private int WaitingQueueCount;
    private String CurrentlyProcessed;

    public PrinterStatus(List<String> MainQueue, List<String> WaitingQueue, int MainQueueCount, int WaitingQueueCount, String CurrentlyProcessed) {
        this.MainQueue = MainQueue;
        this.WaitingQueue = WaitingQueue;
        this.MainQueueCount = MainQueueCount;
        this.WaitingQueueCount = WaitingQueueCount;
        this.CurrentlyProcessed = CurrentlyProcessed;
    }

    public List<String> getMainQueue() {
        return MainQueue;
    }

    public List<String> getWaitingQueue() {
        return WaitingQueue;
    }

    public int getMainQueueCount() {
        return MainQueueCount;
    }

    public int getWaitingQueueCount() {
        return WaitingQueueCount;
    }

    public String getCurrentlyProcessed() {
        return CurrentlyProcessed;
    }
    
}
