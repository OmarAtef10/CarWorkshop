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

    public String getInvoicePDF() {
        FILE = s + "\\Invoice.pdf";
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
        String strFormat = "%-25s %-25s %-25s %-25s";
        String s1= "PRODUCT NAME"+insertEmptySpace(40 - "PRODUCT NAME".length());
        String s2="PRICE"+insertEmptySpace(40 - "PRICE".length());
        String s3="UNITS"+insertEmptySpace(40 - "UNITS".length());
        String s4="TOTAL"+insertEmptySpace(40 - "TOTAL".length());
        formattedItems.add(
                (s1+s2+s3+s4)
        );
        if (cartItems != null) {
            double grandTotal = 0.0;
            for (int i = 1; i <= cartItems.size(); i++) {
                CartItem cartItem = cartItems.get(i - 1);
                double total = cartItem.getUnits() * cartItem.getPrice();
                grandTotal += total;

                String ss1= cartItem.getName()+insertEmptySpace(40 - cartItem.getName().length());
                String ss2=cartItem.getPrice()+insertEmptySpace(40 - String.valueOf(cartItem.getPrice()).length());
                String ss3=cartItem.getUnits()+insertEmptySpace(40 - String.valueOf(cartItem.getUnits()).length());
                String ss4=total+insertEmptySpace(40 - String.valueOf(total).length());

                formattedItems.add(
                        (ss1+ss2+ss3+ss4)
                );
            }
            formattedItems.add(String.format("\n%-25s", "TOTAL PAID : "));
            formattedItems.add(String.format("%-25s", grandTotal));
        }
        for (int i = 0; i < formattedItems.size(); i++) {
            preface.add(new Paragraph(formattedItems.get(i), smallNormal));
        }
        addEmptyLine(preface, 2);
        //Add cashier name
        preface.add(new Paragraph("Cashier name: " + UserManager.getInstance().getCurrentUser().getUserName() +
                " \n" + new Date(), smallBold));
        addEmptyLine(preface, 2);


        document.add(preface);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private static String insertEmptySpace(int n){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<n; i++){
            stringBuilder.append(" ");
        }
        return stringBuilder.toString();
    }
}