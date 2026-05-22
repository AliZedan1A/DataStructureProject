
package com.mycompany.datastructureproject.core.Interfaces;

import com.mycompany.datastructureproject.core.models.PrinterStatus;
import com.mycompany.datastructureproject.core.models.Result;

public interface IPrinterService {
   Result<Void> addPrintJob(String job);
   Result<String> processJob();
   void showMainQueue();
   void showWaitingQueue();
   Result<PrinterStatus> showStatus();
}
