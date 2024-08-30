

public class StockInOrder extends Order{
    private String supplierId;
    private String supplierName;
    public String dateReceived;


    public String getSupplierId(){
        return supplierId;
    }

    public String getSupplierName(){
        return supplierName;
    }

    public String getDateReceived(){
        return dateReceived;
    }
}