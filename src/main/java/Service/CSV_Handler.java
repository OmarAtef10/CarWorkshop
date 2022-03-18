package Service;

import Context.DBContext;
import Controller.InvoiceDao;
import Controller.ProductDao;
import Controller.ReportDao;
import CustomizedUtilities.TimeUtility;
import Model.Cart;
import Model.Invoice;
import Model.Product;
import Model.Report;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;

enum CSV_MODES {
    DAILY,
    MONTHLY
}

public class CSV_Handler implements ICSVService {
    private CSV_MODES MODE = null;
    private String savePath;

    public CSV_Handler(String savePath) {
        this.savePath = savePath;
    }

    @Override
    public void getMonthlyReportCSV() {
        this.MODE = CSV_MODES.MONTHLY;
        try {
            getCSVReport();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getDailyReportCSV() {
        String date = TimeUtility.getCurrentDate();
        this.MODE = CSV_MODES.DAILY;
        try {
            getCSVReport(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getCSVReport(String date) throws IOException {
        InvoiceDao invoiceDao = new InvoiceDao();
        String reformedDate = TimeUtility.getReformedDate(date);
        ArrayList<Invoice> invoices = invoiceDao.getDailyInvoices(date);
        ArrayList<Cart> carts = getMonthlyCarts(invoices);
        HashMap<String, Integer> products = getMonthlyProducts(carts);
        generateCSV(products);
    }

    private void getCSVReport() throws IOException {
        ArrayList<Invoice> invoices = getMonthlyInvoices();
        ArrayList<Cart> carts = getMonthlyCarts(invoices);
        HashMap<String, Integer> products = getMonthlyProducts(carts);
        generateCSV(products);
    }

    private ArrayList<Invoice> getMonthlyInvoices() {
        InvoiceDao invoiceDao = new InvoiceDao();
        LocalDateTime dateTime = LocalDateTime.now();
        ArrayList<Invoice> invoices = new ArrayList<>();

        int start = 1;
        int end = dateTime.getDayOfMonth();
        String dateLooper = "";
        String day = "";
        String month = "";
        if (dateTime.getMonthValue() < 10) {
            month = "0";
            month += String.valueOf(dateTime.getMonthValue());
        } else {
            month = String.valueOf(dateTime.getMonthValue());
        }

        while (start <= end) {
            day = "";
            if (start < 10) {
                day += "0";
                day += String.valueOf(start);
            } else {
                day = String.valueOf(start);
            }
            dateLooper = dateTime.getYear() + "-" + month + "-" + day + "T";
            if (invoiceDao.getDailyInvoices(dateLooper).size() != 0) {
                ArrayList<Invoice> temp_invoices = invoiceDao.getDailyInvoices(dateLooper);
                invoices.addAll(temp_invoices);
            }
            start++;
        }

        return invoices;
    }

    private ArrayList<Cart> getMonthlyCarts(ArrayList<Invoice> invoices) {
        ArrayList<Cart> carts = new ArrayList<>();
        for (Invoice invoice : invoices) {
            carts.add(invoice.getCart());
        }
        return carts;
    }

    private HashMap<String, Integer> getMonthlyProducts(ArrayList<Cart> carts) {
        HashMap<String, Integer> products = new HashMap<>();
        for (Cart cart : carts) {
            HashMap<String, Integer> cartProducts = cart.getProducts();
            for (String prodId : cartProducts.keySet()) {
                if (products.containsKey(prodId)) {
                    int current = cartProducts.get(prodId);
                    int total_current = products.get(prodId) + current;
                    products.replace(prodId, total_current);
                } else {
                    products.put(prodId, cartProducts.get(prodId));
                }
            }
        }
        return products;
    }

    private void generateCSV(HashMap<String, Integer> products) throws IOException {
        if (products.size() == 0) {
            return;
        }
        ProductDao productDao = new ProductDao();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet("Report Data");
        XSSFRow row;
        double grand_total = 0.0;
        double grand_total_profit = 0.0;
        Map<String, Object[]> reportData = new TreeMap<String, Object[]>();

        reportData.put("1", new Object[]{"Product ID", "Product Name", "Units Sold", "Total Price", "Total Profit"});
        int counter = 2;
        for (String key : products.keySet()) {
            Product p = productDao.getOrNull(key);
            Object[] row_data = new Object[5];
            row_data[0] = key;
            row_data[1] = p.getProductName();
            row_data[2] = products.get(key);
            row_data[3] = (products.get(key) * p.getPricePerUnit());
            grand_total += ((Double) row_data[3]);
            row_data[4] = ((products.get(key) * p.getPricePerUnit()) - (p.getMarketPrice() * products.get(key)));
            grand_total_profit += ((Double) row_data[4]);
            reportData.put(String.valueOf(counter), row_data);
            counter++;
        }
        Object[] row_totals = new Object[5];
        row_totals[0] = "";
        row_totals[1] = "";
        row_totals[2] = "Grand Totals:";
        row_totals[3] = grand_total;
        row_totals[4] = grand_total_profit;
        reportData.put(String.valueOf(counter), row_totals);

        Set<String> keyid = reportData.keySet();

        int rowid = 0;

        // writing the data into the sheets...

        for (String key : keyid) {

            row = spreadsheet.createRow(rowid++);
            Object[] objectArr = reportData.get(key);
            int cellid = 0;

            for (Object obj : objectArr) {
                Cell cell = row.createCell(cellid++);
                if (obj instanceof Integer) {
                    cell.setCellValue((Integer) obj);
                } else if (obj instanceof Double) {
                    cell.setCellValue((Double) obj);
                } else {
                    cell.setCellValue((String) obj);
                }
            }
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileOutputStream out = null;
                    out = new FileOutputStream(new File(savePath));
                    workbook.write(out);
                    out.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
