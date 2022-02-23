package Model;

import Controller.ProductDao;

import java.sql.ResultSet;
import java.util.Hashtable;
//TODO init locations from shelfproduct table when getting from resultset
public class ServicePart extends Product {
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
            servicePart.setPricePerUnit(resultSet.getDouble("price"));
            servicePart.setUnits(resultSet.getInt("units"));
            servicePart.setVendor(resultSet.getString("vendor"));
            servicePart.setMarketPrice(resultSet.getDouble("marketPrice"));
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
