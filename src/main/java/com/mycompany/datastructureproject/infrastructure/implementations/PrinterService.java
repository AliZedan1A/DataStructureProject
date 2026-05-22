
package com.mycompany.datastructureproject.infrastructure.implementations;

import com.mycompany.datastructureproject.core.Interfaces.IPrinterService;
import com.mycompany.datastructureproject.core.models.PrinterStatus;
import com.mycompany.datastructureproject.core.models.Result;
import com.mycompany.datastructureproject.infrastructure.CircularQueue;


public class PrinterService implements IPrinterService {

     private CircularQueue mainQueue;
    private CircularQueue waitingQueue;
    private String currentlyProcessed;

    public PrinterService(int mainQueueCapacity, int waitingQueueCapacity) {
        this.mainQueue = new CircularQueue(mainQueueCapacity);
        this.waitingQueue = new CircularQueue(waitingQueueCapacity);
        this.currentlyProcessed = "None";
    }

    @Override
    public Result<Void> addPrintJob(String job) {

        if (job == null || job.trim().isEmpty()) {
            return new Result(false,"Print job cannot be empty");
        }

        if (!mainQueue.isFull()) {
            mainQueue.enqueue(job);
            return new Result(true,null, "Job added to main queue");
        }

        if (!waitingQueue.isFull()) {
            waitingQueue.enqueue(job);
            return new Result(true,null, "Main queue is full, job added to waiting queue");
        }

        return new Result(false,"Both queues are full, job rejected");
    }

    @Override
    public Result<String> processJob() {

        if (mainQueue.isEmpty()) {
            currentlyProcessed = "None";
            return new Result(false,"Main queue is empty, no job to process");
        }

        String processedJob = mainQueue.dequeue();
        currentlyProcessed = processedJob;

        moveFromWaiting();

        return new Result(true,processedJob, "Job processed successfully");
    }

    private void moveFromWaiting() {

        if (!waitingQueue.isEmpty() && !mainQueue.isFull()) {
            String job = waitingQueue.dequeue();
            mainQueue.enqueue(job);
        }
    }

    @Override
    public void showMainQueue() {
        System.out.print("Main Queue: ");
        mainQueue.display();
    }

    @Override
    public void showWaitingQueue() {
        System.out.print("Waiting Queue: ");
        waitingQueue.display();
    }

    @Override
    public Result<PrinterStatus> showStatus() {

        PrinterStatus status = new PrinterStatus(
                mainQueue.toList(),
                waitingQueue.toList(),
                mainQueue.getSize(),
                waitingQueue.getSize(),
                currentlyProcessed
        );

        return new Result(true,status, "Printer status loaded successfully");
    }

    
}
