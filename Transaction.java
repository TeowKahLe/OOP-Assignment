import java.util.*;
import java.io.*;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class Transaction{
    private String transactionID;
    private static double balance;
    private Date transDate;
    private Date transTime;
    private String transactionType;
    private double transAmount;
    
    private List<Order> OrderInfo = new ArrayList<>();

    static Alignment line = new Alignment();

    Transaction(){
        transDate = new Date();
        transTime = new Time(System.currentTimeMillis());
    }

    Transaction( String transactionID,double balance,Date transDate,Date transTime,String transactionType,double transAmount){
        this.transactionID = transactionID;
        Transaction.balance = balance;
        transDate = new Date();
        transTime = new Time(System.currentTimeMillis());
        this.transactionType = transactionType;
        this.transAmount = transAmount;
    }

    //format date and time
    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd MMM yyyy");
    SimpleDateFormat timeFormatter = new SimpleDateFormat("hh:mm a");


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
        Transaction.balance += amount;
    }

    public static void minusBalance(double amount){
        Transaction.balance -= amount;
    }

    public boolean makeTransaction(String orderID,String transactionType,double amount){
        String transFilePath = "Transaction.txt";

        
        /*writer.write(generateTransID(transFilePath) + "\t" + orderID + "\t" + dateFormatter.format(transDate) + "\t" + timeFormatter.format(transTime) + "\t" + transactionType + "\tRM" + amount + "\n");*/

        //find current balance
        try (Scanner scanner = new Scanner(new File(transFilePath))){
            if(scanner.hasNextLine()){

                String lineContent = scanner.nextLine();
                String[] tokenContent = lineContent.split("\\t");
                Transaction.balance = Double.parseDouble(tokenContent[1].substring(2));
            }

        } catch (Exception e) {
            System.out.println(transFilePath +" unable to open.");
        }

        boolean ableTransfer = displayTransaction(amount,transFilePath);

        if(ableTransfer){
            calcNewBalance(amount,transFilePath);
            try {
                FileWriter writer = new FileWriter(transFilePath,true);
                writer.write(generateTransID(transFilePath) + "\t" + orderID + "\t" + dateFormatter.format(transDate) + "\t" + timeFormatter.format(transTime) + "\t" + transactionType + "\tRM" + String.format("%.2f", amount) + "\n");
    
                writer.close();  
                
                System.out.println(String.format("%-20s", "New Balance: ") + "RM" + String.format("%.2f", balance));
            } catch (IOException e) {
                System.out.println("unable to write in " + transFilePath);
            }
        }else{
            System.out.println("Sorry,insuffient balance unable to continue transaction.Transaction cancelled.");
        }
        return ableTransfer;
    }

    public boolean displayTransaction(double transAmount,String transFilePath){
        boolean ableTransfer = false;

        line.printEqualLine("----------Transaction----------".length());
        System.out.println("----------Transaction----------");
        line.printEqualLine("----------Transaction----------".length());
        System.out.println(String.format("%-20s", "Current Balance: ") + "RM" + String.format("%.2f", balance));

        if(transAmount > 0){
            System.out.println(String.format("%-20s", "Received Amount: ") + "RM" + String.format("%.2f",transAmount));
            ableTransfer = true;

        }else if(transAmount < 0){

            System.out.println(String.format("%-20s", "Pay Amount: ") + "RM" + String.format("%.2f",Math.abs(transAmount))); //abs have abosolute num which is alway positive
            if(balance >= Math.abs(transAmount)){
                ableTransfer = true;
            }else{
                ableTransfer = false;
            }

        }else{
            System.out.println("No transaction.");
            ableTransfer = false;
        }  

        return ableTransfer;
    }

    public void calcNewBalance(double transAmount,String transFilePath){
        if(transAmount > 0){
            addBalance(transAmount);
        }else{
            minusBalance(Math.abs(transAmount));
        }

        List<String> tempStorage = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(transFilePath))){
            while(scanner.hasNextLine()){
                tempStorage.add(scanner.nextLine());
            }
            
        } catch (Exception e) {
            System.out.println(transFilePath + " unable to open.");
        }

        try {
            // write with updated current balance
            FileWriter writer = new FileWriter(transFilePath);
            writer.write("Current Balance: " + "\tRM" + String.format("%.2f", balance) + "\n");

            //write the transaction record back
            for(int noLine = 1;noLine<tempStorage.size();noLine++){//0 is balance
                if(tempStorage.get(noLine) != null){
                    writer.write(tempStorage.get(noLine) + "\n");
                }
               
            }
            writer.close();
        } catch (Exception e) {
            System.out.println("unable to write in " + transFilePath);
        }
        
    }

    public String generateTransID(String transFilePath){
        int noLine = 0;
        try (Scanner scanner = new Scanner(new File(transFilePath))){
            while(scanner.hasNextLine()){
                noLine++;
                scanner.nextLine();
            }

        } catch (Exception e) {
            System.out.println(transFilePath + " unable to open.");
        }
        return ("T" + String.format("%05d", noLine));
    }

    public static void generateReport(){
        //T00001	SI0005	19 Sept 2024	02:18 am	Purchase	RM-1357.50
        String transFilePath = "Transaction.txt";
        double profit = 0.0;

        try (Scanner scanner = new Scanner(new File(transFilePath))){
            line.printLine(117);
            System.out.printf("%-116s%s\n","|TRANSACTION REPORT","|");
            line.printLine(117);
            System.out.printf("|%-15s|%-15s|%-25s|%-20s|%-20s|%-15s|\n","Transaction ID","Order ID","Transaction Date","Transaction Time","Transaction Type","Total(RM)");
            line.printLine(117);
            while(scanner.hasNextLine()){
                String lineContent = scanner.nextLine();
                if(lineContent.startsWith("T")){
                    String[] elementContent = lineContent.split("\\t");
                    double total = Double.parseDouble(elementContent[5].substring(2));
                    profit+=total;
                    System.out.printf("|%-15s|%-15s|%-25s|%-20s|%-20s|%15s|\n",elementContent[0],elementContent[1],elementContent[2],elementContent[3],elementContent[4],elementContent[5].substring(2));
                }
            }
            line.printLine(117);
            if(profit < 0){
                System.out.printf("|%-98s %-3s %12.2f%s\n","Loss:","|",profit,"|");
            }else{
                
                System.out.printf("|%-98s %-3s %12.2f%s\n","Profit:","|",profit,"|");
            }
            line.printLine(117);
            
        } catch (Exception e) {
            System.out.println(transFilePath + " unable to open");
        }
    }

}