package Service;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import Controller.UserManager;
import Model.InvoiceDetail;
import View.CartItem;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class PDF_Invoice {
    private static Path currentRelativePath = Paths.get("");
    private static String s = currentRelativePath.toAbsolutePath().toString();
    private static String FILE;
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font smallNormal = new Font(Font.FontFamily.TIMES_ROMAN, 9, Font.NORMAL);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private InvoiceDetail invoiceDetail;

    public PDF_Invoice(InvoiceDetail invoiceDetail) {
        this.invoiceDetail = invoiceDetail;
    }

    public String getInvoicePDF(){
        FILE = s+"\\Invoice.pdf";
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(FILE));
            document.open();
            addMetaData(document);
            addTitlePage(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return FILE;
    }

    private void addMetaData(Document document) {
        document.addTitle("Invoice");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }

    private void addTitlePage(Document document) throws DocumentException {
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        preface.add(new Paragraph(UserManager.workshopName, catFont));
        addEmptyLine(preface, 1);

        ArrayList<CartItem> cartItems = this.invoiceDetail.getItems();
        ArrayList<String> formattedItems = new ArrayList<>();
        formattedItems.add(
          String.format("%20s %20s %20s %20s","PRODUCT NAME","PRICE","UNITS","TOTAL")
        );
        if(cartItems!=null) {
            for (int i = 1; i <= cartItems.size(); i++) {
                CartItem cartItem = cartItems.get(i-1);
                double total = cartItem.getUnits() * cartItem.getPrice();
                formattedItems.add(
                        String.format("%25s %25s %25s %25s", cartItem.getName() + ": ",
                                cartItem.getPrice() + ": ", cartItem.getUnits() + ": ", total)
                );
            }
        }
        for(int i=0; i<formattedItems.size(); i++){
            preface.add(new Paragraph(formattedItems.get(i),smallNormal));
        }
        addEmptyLine(preface, 2);
        //Add cashier name
        preface.add(new Paragraph("Cashier name: " + UserManager.getInstance().getCurrentUser().getUserName()+
                " \n" + new Date(), smallBold));
        addEmptyLine(preface, 2);


        document.add(preface);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}