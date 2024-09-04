import java.util.Date;
import java.util.List;

public class StockInOrder extends Order{
    private String supplierId;
    private String supplierName;
    public Date dateReceived;

    public StockInOrder(String orderId, String approvalStatus, Date orderDate, Date orderTime, 
                        String deliveryMethod, String orderType, String staffId, 
                        List<Item> itemList, int[] itemQty, 
                        String supplierId, String supplierName, Date dateReceived) {
        super(orderId, approvalStatus, orderDate, orderTime, deliveryMethod, orderType, staffId, itemList, itemQty);
        this.supplierId = supplierId;
        this.supplierName = supplierName;
        this.dateReceived = dateReceived;
    }// I put this for display order


    public String getSupplierId(){
        return supplierId;
    }

    public String getSupplierName(){
        return supplierName;
    }

    public Date getDateReceived(){
        return dateReceived;
    }
}