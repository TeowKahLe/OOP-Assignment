import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Time;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Order{
    private String orderId;
    private String approvalStatus;
    private Date orderDate;
    private Date orderTime;
    private String deliveryMethod;
    private String orderType;
    private String staffId;
    private static List<Item> itemList;
    private int[] itemQty;

    Transaction transaction = new Transaction();

    //-----------------------------------------------------------------------------------Constructors
    public Order(){
        itemList = new ArrayList<>();
        this.itemQty = new int[0];
        this.orderDate = new Date();
        this.orderTime = new Time(System.currentTimeMillis());//Converts the current time into a Time object representing only the time portion (hours, minutes, seconds).
    }

    public Order(String orderId, String approvalStatus, Date orderDate, Date orderTime, 
                 String deliveryMethod, String orderType, String staffId, 
                 List<Item> itemList, int[] itemQty) {
        this.orderId = orderId;
        this.approvalStatus = approvalStatus;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.deliveryMethod = deliveryMethod;
        this.orderType = orderType;
        this.staffId = staffId;
        Order.itemList = itemList;
        this.itemQty = itemQty;
    }

    //--------------------------------------------------------------------------------------Getters
    public String getOrderId() {
        return orderId;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public String getFormattedOrderDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        return dateFormat.format(orderDate);  
    }

    public String getFormattedOrderTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a");
        return timeFormat.format(orderTime); 
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

    public List<Item> getItemList() {
        return itemList;
    }

    public int[] getItemQty() {
        return itemQty;
    }

    //-----------------------------------------------------------------------------------Setters
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public void setOrderDate() {
        this.orderDate = new Date();
    }

    public void setOrderTime() {
        this.orderTime = new Date();
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

    public void setItemList(List<Item> itemList) {
        Order.itemList = itemList;
    }

    public void setItemQty(int[] itemQty) {
        this.itemQty = itemQty;
    }

    //-----------------------------------------------------------------------------------read item from file then update ItemList
    public static List<Item> readItemFromFile(String filePath) {
        List<Item> items = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String[] itemFields = scanner.nextLine().split("\\|");
                if (itemFields.length >= 11) {
                    // Create the Inventory object first
                    Inventory inventory = new Inventory(
                        Integer.parseInt(itemFields[6]), // stockQty
                        Double.parseDouble(itemFields[7]), // stockCost
                        Double.parseDouble(itemFields[8]), // stockValue
                        Integer.parseInt(itemFields[9]), // minStockQty
                        Integer.parseInt(itemFields[10]) // maxStockQty
                    );
    
                    // Create the Item object, passing in the Inventory object
                    Item item = new Item(
                        itemFields[0], // itemId
                        itemFields[1], // itemName
                        itemFields[2], // itemCategory
                        itemFields[3], // itemDesc
                        Double.parseDouble(itemFields[4]), // unitCost
                        Double.parseDouble(itemFields[5]), // unitPrice
                        inventory // Pass the Inventory object to the Item constructor
                    );
    
                    // Add Item to the list
                    items.add(item);
    
                } else {
                    System.out.println("Invalid data format in file.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot locate file: " + filePath);
        }
        return items; // Return the list of Item objects
    }
    

    //-----------------------------------------------------------------------------------Store Item To Array
    public static List<Item> storeItemtoArr(){
        String itemFilePath = "itemInfo.txt";
        String []tokenContents;

        try(Scanner scanner = new Scanner (new File(itemFilePath))) {
           while(scanner.hasNextLine()){
                String lineContent = scanner.nextLine();
                if(lineContent != null){
                    tokenContents = lineContent.split("\\|");
                    //0-ID,1-Item Name,2-Category,3-Description,4-unitCost,5-unitPrice
                    itemList.add(new Item(tokenContents[0],tokenContents[1],tokenContents[2],tokenContents[3],Double.parseDouble(tokenContents[4]),Double.parseDouble(tokenContents[5])));
                }
            }

        } catch (IOException e) {
            System.out.println(itemFilePath + " unable to open");
        }
        return itemList;
    }

    public boolean updateItemQty(List <String> orderItemDetail){
        //check this is add or delete item so need check first(1) in list is positive or negative
        //bcz stock in order qty always positive and stock out is always negative in qty
        boolean enoughQty = true;

        //for check purpose
        String[] checkItemInfo = orderItemDetail.get(0).split("\\t");
        int checkSignQty = Integer.parseInt(checkItemInfo[1]);

        //if positive no need check
        if(checkSignQty < 0){
            enoughQty = checkAvailableQty(orderItemDetail);
        }

        //change inventory qty
        if(enoughQty){
        for (String itemInfo : orderItemDetail) {
            String[] elementItemInfo = itemInfo.split("\\t");
            String itemID = elementItemInfo[0];
            int modifyQty = Integer.parseInt(elementItemInfo[1]);
            
                if(modifyQty > 0 || modifyQty < 0){
                    modifyItemQty(itemID,modifyQty);
                }else{
                    System.out.println("No quantity changed.");
                }
            }
        }else{
            System.out.println("Insufficient item quantity.");
        }

        return enoughQty;
    }
    
    public void modifyItemQty(String itemID,int modifyQty){
        String itemInfoFilePath = "itemInfo.txt";

        try {
            List<String> originalLine = Files.readAllLines(Paths.get(itemInfoFilePath));//before modify content
            List<String> updatedLine = new ArrayList<>(); // temp storage to store updated data

            for (String content : originalLine) {
                String[] elementContent = content.split("\\|");

                if(elementContent[0].equals(itemID)){
                    int updatedQty = Integer.parseInt(elementContent[6]) + modifyQty; //bcz modifyQty is negative so minus

                    updatedLine.add(elementContent[0] + "|" + elementContent[1] + "|" + elementContent[2] + "|" + elementContent[3] + "|" + elementContent[4] + "|" + elementContent[5] + "|" + updatedQty + "|" + elementContent[7] + "|" + elementContent[8] + "|" + elementContent[9] + "|" + elementContent[10]);

                }else{
                    updatedLine.add(content);
                }
            }

            FileWriter writer = new FileWriter(itemInfoFilePath);
            for (String updatedContent : updatedLine) {
                writer.write(updatedContent + "\n");
            }
            writer.close();

        } catch (IOException e) {
            System.out.println(itemInfoFilePath + " unable to open.");
        }
    }

    public boolean checkAvailableQty(List<String> orderItemDetail){
        String itemInfoFilePath = "itemInfo.txt";
        boolean enoughQty = true;

        for (String itemDetail : orderItemDetail) {
            String[] elementItemDetail = itemDetail.split("\\t");
            String itemID = elementItemDetail[0];
            int minusQty = Integer.parseInt(elementItemDetail[1]);

            try (Scanner scanner = new Scanner(new File(itemInfoFilePath))){
                    while (scanner.hasNextLine()) {
                        String lineContent = scanner.nextLine();
                        String[] elementLineContent = lineContent.split("\\|");

                        if(elementLineContent[0].equals(itemID)){
                            //elementLineContent[6] is available stock
                            if(Integer.parseInt(elementLineContent[6]) < Math.abs(minusQty)){
                                enoughQty = false;
                                break;
                            }
                        }

                    }
            
            } catch (IOException e) {
                System.out.println(itemInfoFilePath + " unable to open.");
            }
        }

        return enoughQty;
    }

}

