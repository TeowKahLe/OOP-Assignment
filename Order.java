import java.util.List;

public class Order{
    private String orderId;
    private List<Item> itemList;
    private int itemQty;
    private String approvalStatus;
    private String orderDate;
    private String orderTime;
    private String deliveryMethod;
    private String orderType;
    private String staffId;

    //Constructors--------------------------------------------------------------------
    public Order(){
    }

    public Order(String orderId, List<Item> itemList, int itemQty, String approvalStatus, 
                 String orderDate, String orderTime, String deliveryMethod, 
                 String orderType, String staffId){
        this.orderId = orderId;
        this.itemList = itemList;
        this.itemQty = itemQty;
        this.approvalStatus = approvalStatus;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.deliveryMethod = deliveryMethod;
        this.orderType = orderType;
        this.staffId = staffId;
    }

    //Getters-------------------------------------------------------------------------
    public String getOrderId() {
        return orderId;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public int getItemQty() {
        return itemQty;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getStaffId() {
        return staffId;
    }

    //Setters-------------------------------------------------------------------------
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    
}


