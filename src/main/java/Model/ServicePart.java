package Model;

import java.sql.ResultSet;

import Controller.ProductDao;

public class ServicePart extends Product {
    //wtf  is ZIIIIIIS?
    private String partName;

    public ServicePart() {
        super();
    }

    public ServicePart(String partName, String vendor, int units, double pricePerUnit, double marketPrice) {
        super(vendor, units, pricePerUnit, marketPrice);
        this.partName = partName;
        this.setProductName(partName);
    }

//    public ServicePart(String partName, String vendor, int units, double pricePerUnit, double marketPrice,String location) {
//        super(vendor, units, pricePerUnit, marketPrice,location);
//        this.partName = partName;
//        this.setProductName(partName);
//    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }


    public static ServicePart fromResultSet(ResultSet resultSet) {
        ServicePart servicePart = new ServicePart();

        try {
            servicePart.setProductId( resultSet.getString("productId") );
            servicePart.partName = resultSet.getString("name");
            servicePart.setProductName(servicePart.partName);
            servicePart.setPricePerUnit(resultSet.getDouble("price"));
            servicePart.setUnits(resultSet.getInt("units"));
            servicePart.setVendor(resultSet.getString("vendor"));
            servicePart.setMarketPrice(resultSet.getDouble("marketPrice"));
            ProductDao dao = new ProductDao();
            servicePart.setLocations(dao.getProductShelf(servicePart.getProductId()));
            servicePart.setProductHistory(dao.getHistory(servicePart.getProductId()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return servicePart;
    }

    @Override
    public String toString() {
        return "ServicePart {" +
                "partName='" + partName + '\'' +
                '}';
    }
}
