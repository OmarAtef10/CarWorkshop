package Model;

public class ServicePart extends Product{
    private String partName;

    public ServicePart(String partName,String vendor, int units, double pricePerUnit, double marketPrice) {
        super(vendor, units, pricePerUnit, marketPrice);
        this.partName=partName;
        this.setProductName(partName);
    }

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    @Override
    public String toString() {
        return "ServicePart{" +
                "partName='" + partName + '\'' +
                '}';
    }
}
