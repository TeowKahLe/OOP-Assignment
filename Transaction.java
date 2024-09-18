import java.util.*;

public class Transaction{
    private String transactionID;
    private static double balance;
    private Date transDate = new Date();
    private Date transTime;
    private String transactionType;
    private double transAmount;
    
    private List<Order> OrderInfo = new ArrayList<>();


    public String getTransactionID() {
        return transactionID;
    }

    public static double getBalance() {
        return balance;
    }

    public Date getTransDate() {
        return transDate;
    }

    public Date getTransTime() {
        return transTime;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getTransAmount() {
        return transAmount;
    }

    public List<Order> getOrderInfo() {
        return OrderInfo;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public static void setBalance(double balance) {
        Transaction.balance = balance;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setTransAmount(double transAmount) {
        this.transAmount = transAmount;
    }

    public void setOrderInfo(List<Order> orderInfo) {
        OrderInfo = orderInfo;
    }
    
    public static double calcSubTotal(double unitAmount,int qty){
        double subtotal;
        subtotal = unitAmount * qty;
        return subtotal;
    }

    public double calTotalAmount(double[] subAmount){
        double ovrTotal = 0;
        for (double sub : subAmount) {
            ovrTotal += sub;   
        }
        return ovrTotal;
    }

    public static void addBalance(double amount){
        balance += amount;
    }

    
    public static void minusBalance(double amount){
        balance -= amount;
    }


    //store OrderList from file
    public void storeOrderList(){
        //String stockInFilePath = "stockInOrder.txt";
        //String stockOutFilePath = "stockOutOrderInfo.txt";

        //List<Item> itemList = new ArrayList<>();
        //order List structure (order id,approvalStatus,orderDate,orderTime,delivMethod,orderType,staffID,itemList,itemQty[])

        //stock in
        

    }
}