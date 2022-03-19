package Service;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import Controller.UserManager;
import CustomizedUtilities.TimeUtility;
import Model.InvoiceDetail;
import View.CartItem;

import Controller.UserManager;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;

import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
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
        createPDF();
        return FILE;
    }

    private void createPDF() {

        Document doc = new Document();
        PdfWriter docWriter = null;

        DecimalFormat df = new DecimalFormat("0.00");

        try {

            //special font sizes
            Font bfBold12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, new BaseColor(0, 0, 0));
            Font bfBold16 = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD, new BaseColor(0, 0, 0));
            Font bf12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);

            //file path
            String path = FILE;
            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));

            //document header attributes
            doc.addAuthor("John Cena");
            doc.addCreationDate();
            doc.addProducer();
            doc.addCreator("");
            doc.addTitle("");
            doc.setPageSize(PageSize.A4);

            doc.open();

            Paragraph paragraph = new Paragraph(UserManager.workshopName, bfBold16);

            //specify column widths
            float[] columnWidths = {4.5f, 2f, 2f, 2f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setWidthPercentage(90f);

            //insert column headings
            insertCell(table, "Invoice ID: " + invoiceDetail.getInvoiceId(), Element.ALIGN_LEFT, 4, bfBold12);

            insertCell(table, "Product Name", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Price", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Units", Element.ALIGN_LEFT, 1, bfBold12);
            insertCell(table, "Total", Element.ALIGN_LEFT, 1, bfBold12);
            table.setHeaderRows(1);

            //create section heading by cell merging
            double orderTotal = 0, total = 0;
            int items_sz = invoiceDetail.getItems().size();
            ArrayList<CartItem> items = invoiceDetail.getItems();

            //just some random data to fill
            for(int x=0; x<items_sz; x++){

                insertCell(table, items.get(x).getName(), Element.ALIGN_LEFT, 1, bf12);
                insertCell(table, String.valueOf(items.get(x).getPrice()), Element.ALIGN_LEFT, 1, bf12);
                insertCell(table, String.valueOf(items.get(x).getUnits()), Element.ALIGN_LEFT, 1, bf12);
                total = items.get(x).getPrice()*items.get(x).getUnits();
                orderTotal+=total;
                insertCell(table, df.format(total), Element.ALIGN_RIGHT, 1, bf12);
            }
            //merge the cells to create a footer for that section
            insertCell(table, "Order Total: ", Element.ALIGN_RIGHT, 3, bfBold12);
            insertCell(table, df.format(orderTotal), Element.ALIGN_RIGHT, 1, bfBold12);

            //add the PDF table to the paragraph
            paragraph.add(table);
            // add the paragraph to the document
            doc.add(paragraph);
            doc.add(new Paragraph(" "));

            Paragraph p2 = new Paragraph();
            p2.add("" + TimeUtility.getCurrentDate());
            doc.add(p2);

            Paragraph p3 = new Paragraph();
            p3.add("Cashier Name: " + UserManager.getInstance().getCurrentUser().getUserName());
            doc.add(p3);
        } catch (Exception dex) {
            dex.printStackTrace();
        } finally {
            //close the document
            doc.close();
            if (docWriter != null) {
                //close the writer
                docWriter.close();
            }
        }
    }

    private void insertCell(PdfPTable table, String text, int align, int colspan, Font font) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text.trim(), font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }
        //add the call to the table
        table.addCell(cell);

    }


}