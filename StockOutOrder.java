import java.util.Date;

public class StockOutOrder{
    private String customerId;
    private String customerName;
    private String customerAddress;
    private Date dateDispatched;

    //Constructors--------------------------------------------------------------
    public StockOutOrder() {
    }

    public StockOutOrder(String customerId, String customerName, String customerAddress, Date dateDispatched) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.dateDispatched = dateDispatched;
    }

    //Getters-------------------------------------------------------------------
    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public Date getDateDispatched() {
        return dateDispatched;
    }

    //Setters-------------------------------------------------------------------
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public void setDateDispatched(Date dateDispatched) {
        this.dateDispatched = dateDispatched;
    }
}