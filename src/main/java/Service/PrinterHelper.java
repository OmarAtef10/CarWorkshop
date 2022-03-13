package Service;

import java.awt.print.PrinterJob;
import java.io.File;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;


public class PrinterHelper{
    private String filePath;
    private String printerName;

    public PrinterHelper(String filePath, String printerName) {
        this.filePath = filePath;
        this.printerName = printerName;
    }

    public PrinterHelper(String filePath) {
        this.filePath = filePath;
    }


    public void start() {
        try {
            PDDocument document = PDDocument.load(new File(filePath));

            PrintService myPrintService = findPrintService(printerName);

            PrinterJob job = PrinterJob.getPrinterJob();
            job.setPageable(new PDFPageable(document));
            job.setPrintService(myPrintService);
            job.print();
            document.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static PrintService findPrintService(String printerName) {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService printService : printServices) {
            if (printService.getName().trim().equals(printerName)) {
                return printService;
            }
        }
        return null;
    }
}
