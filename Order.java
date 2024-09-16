import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.sql.Time;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Order{
    private String orderId;
    private String approvalStatus;
    private Date orderDate = new Date();
    private Date orderTime;
    private String deliveryMethod;
    private String orderType;
    private String staffId;
    private List<Item> itemList;
    private int[] itemQty;

    Transaction transaction = new Transaction();

    //-----------------------------------------------------------------------------------Constructors
    public Order(){
        this.itemList = new ArrayList<>();
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
        this.itemList = itemList;
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
        this.itemList = itemList;
    }

    public void setItemQty(int[] itemQty) {
        this.itemQty = itemQty;
    }

    //-----------------------------------------------------------------------------------Order Management
    public static void orderManagement(){
        Alignment.clearScreen();
	    Alignment line = new Alignment();
    	Scanner scanner = new Scanner(System.in);
		boolean error = true;

		line.printEqualLine(" ORDER MANAGEMENT ".length());
		System.out.println(" ORDER MANAGEMENT ");
		line.printEqualLine(" ORDER MANAGEMENT ".length());
		System.out.println("Please select your action");
		System.out.println("1. Display All Order");
		System.out.println("2. Search Order");
		System.out.println("3. Track Delivery");
		System.out.println("4. Stock In Order");
   	 	System.out.println("5. Stock Out Order");
   		System.out.println("6. Return to Menu");
		System.out.println("7. Exit");

		while(error){
			try{
				System.out.print("Selected action: ");
    			int option = scanner.nextInt();
                scanner.nextLine();
				switch(option){
    				case 1:
						displayAllOrder();
    					break;
    				case 2:
						//Search Order
    					break;
					case 3:
						//Track Delivery
    					break;
					case 4:
						//Stock In Order
    					break;
					case 5:
						StockOutOrder.stockOutOrderMenu();
    					break;
					case 6:
						fastFoodInventory.main(null);
						break;
					case 7:
						System.exit(0);
						break;
    				default:
    					System.out.println("Invalid action selected");
    					break;	
    			}
			}catch (Exception e){
    			System.out.println("Incorrect input(Please enter NUMBER only)");
    			scanner.nextLine();
    		}	
		}
        scanner.close();
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
    
    //-----------------------------------------------------------------------------------Display All Order
    public static void displayAllOrder() {
        //display stock in order
        StockOutOrder.displayStockOutOrder();

        Scanner scanner = new Scanner(System.in);
        int opt = 0;
        boolean loop = true;
        while (loop) {
            System.out.println("\nPlease select your action\n1.Back to Order Management\n2.Exit");
            try {
                System.out.print("--> ");
                opt = scanner.nextInt();
                scanner.nextLine();
                switch(opt) {
                    case 1: 
                        Order.orderManagement();
                        loop = false;
                        break;
                    case 2:
                        System.exit(0);
                    default:
                        System.out.println("Invalid option");
                }
            } catch (Exception e) {
                System.out.println("Incorrect input (Please enter NUMBER only)");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    //-----------------------------------------------------------------------------------Track Delivery
    public static void trackDelivery(){
        
    }

    //-----------------------------------------------------------------------------------Store Item To Array
    public void storeItemtoArr(){
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
            //System.out.println(itemFilePath + " unable to open");
        }
    }
}




