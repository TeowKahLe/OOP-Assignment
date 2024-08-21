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

        public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
}