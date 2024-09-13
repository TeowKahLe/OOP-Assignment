import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.Date;

public class Order{
    private String orderId;
    private String approvalStatus;
    private Date orderDate;
    private Date orderTime;
    private String deliveryMethod;
    private String orderType;
    private String staffId;
    private List<Item> itemList;
    private int[] itemQty;

    //-----------------------------------------------------------------------------------Constructors
    public Order(){
        this.itemList = new ArrayList<>();
        this.itemQty = new int[0];
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

		line.printEqualLine("ORDER MANAGEMENT".length());
		System.out.println("ORDER MANAGEMENT");
		line.printEqualLine("ORDER MANAGEMENT".length());
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
						//Order.orderManagement();
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
                        Order.orderManagement();
    					break;
					case 6:
						error = false;
						//menu();
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

    //-----------------------------------------------------------------------------------Generate Order Id
    public static String generateOrderId(String orderTypePrefix) {
        String filePath = "orderInfo.txt";
        String latestOrderId = null;
        int latestNumber = 0;

        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Check if the line starts with the given prefix
                if (line.startsWith(orderTypePrefix)) {
                    // Extract the number part from the ID
                    String idPart = line.substring(orderTypePrefix.length(), orderTypePrefix.length() + 4);
                    try {
                        int number = Integer.parseInt(idPart);
                        if (number > latestNumber) {
                            latestNumber = number;
                            latestOrderId = orderTypePrefix + String.format("%04d", latestNumber);
                        }
                    } catch (NumberFormatException e) {
                        System.out.print("ID number not valid");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file: " + e.getMessage());
        }

        // Generate new order ID
        if (latestOrderId == null) {
            return orderTypePrefix + "0001";
        } else {
            int newNumber = Integer.parseInt(latestOrderId.substring(orderTypePrefix.length())) + 1;
            return String.format("%s%04d", orderTypePrefix, newNumber);
        }
    }

    //-----------------------------------------------------------------------------------Store order to file
    public static void storeOrderToFile(Order order, StockInOrder stockInOrder, StockOutOrder stockOutOrder) {
        try (FileWriter writer = new FileWriter("orderInfo.txt", true)) { // Open in append mode
    
            // Write Order details
            writer.write(order.getOrderId() + "|" +
                         order.getApprovalStatus() + "|" +
                         order.getOrderDate() + "|" +
                         order.getOrderTime() + "|" +
                         order.getDeliveryMethod() + "|" +
                         order.getOrderType() + "|" +
                         order.getStaffId() + "|" + "\n");
    
            // Write StockOutOrder or StockInOrder specific details
            if (order.getOrderType() == "Stock OutOrder") {

               // Write StockInOrder specific details

            } else if (order.getOrderType() == "Stock OutOrder") {
                writer.write(stockOutOrder.getCustomerId() + "|" +
                stockOutOrder.getCustomerName() + "|" +
                stockOutOrder.getCustomerAddress() + "|" +
                stockOutOrder.getDateDispatched() + "|" + "\n");
            }
    
            // Write item quantities and IDs
            boolean first = true; // Flag to handle leading separator
            List<Item> items = order.getItemList();
            int[] itemQty = order.getItemQty();
            for (int i = 0; i < items.size(); i++) {
                if (!first) {
                    writer.write("|");
                }
                Item item = items.get(i);
                writer.write(itemQty[i] + "|" + item.getItemId());
                first = false;
            }
            writer.write("\n"); // Newline after item list
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //-----------------------------------------------------------------------------------read item from file then update ItemList
    public static List<Item> readItemFromFile(String filePath) {
        List<Item> items = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String[] itemFields = scanner.nextLine().split("\\|");
                if (itemFields.length >= 11) {
                    Inventory inventory = new Inventory(
                        itemFields[0], // itemId
                        itemFields[1], // itemName
                        itemFields[2], // itemCategory
                        itemFields[3], // itemDesc
                        Double.parseDouble(itemFields[4]), // unitCost
                        Double.parseDouble(itemFields[5]), // unitPrice
                        Integer.parseInt(itemFields[6]), // stockQty
                        Double.parseDouble(itemFields[7]), // stockCost
                        Double.parseDouble(itemFields[8]), // stockValue
                        Integer.parseInt(itemFields[9]), // minStockQty
                        Integer.parseInt(itemFields[10]) // maxStockQty
                    );
    
                    // Cast Inventory to Item and add to itemList
                    items.add((Item) inventory);
                } else {
                    System.out.println("Invalid data format in file.");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot locate file: " + filePath);
        }
        return items; // Return the itemList
    }
    
    //-----------------------------------------------------------------------------------Display All Order
    public static void displayAllOrder() {
        File file = new File("orderInfo.txt");
        boolean hasOrders = false; // Flag to check if any orders are found
    
        System.out.println("+------------------------------------------------------------------------------+");
        System.out.printf("|%-3s | %-15s | %-15s | %-15s | %-15s | %-15s | %-10s |\n",
                          "No.", "Order ID", "Approval", "Date", "Time", "Delivery", "Type");
        System.out.println("|------------------------------------------------------------------------------|");
    
        try (Scanner scanner = new Scanner(file)) {
            int orderNo = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Parse the order details
                String[] details = line.split("\\|");
    
                if (details.length < 7) {
                    System.out.println("Invalid line format.");
                    continue;
                }
    
                // Display order details
                System.out.printf("|%-3d | %-15s | %-15s | %-15s | %-15s | %-15s | %-10s |\n",
                                  orderNo++,
                                  details[0], // Order ID
                                  details[1], // Approval Status
                                  details[2], // Order Date
                                  details[3], // Order Time
                                  details[4], // Delivery Method
                                  details[5]  // Order Type
                );
                System.out.println("|------------------------------------------------------------------------------|");
    
                // Read the next line for specific order details
                if (scanner.hasNextLine()) {
                    line = scanner.nextLine();
                    String[] specifics = line.split("\\|");
    
                    if (details[5].equals("Stock Out Order")) {
                        if (specifics.length < 4) {
                            System.out.println("Invalid stock out order details.");
                            continue;
                        }
                        System.out.println("Stock Out Order Details:");
                        System.out.printf("Customer ID: %s\n", specifics[0]);
                        System.out.printf("Customer Name: %s\n", specifics[1]);
                        System.out.printf("Customer Address: %s\n", specifics[2]);
                        System.out.printf("Date Dispatched: %s\n", specifics[3]);
                    } else if (details[5].equals("Stock In Order")) {
                        if (specifics.length < 3) {
                            System.out.println("Invalid stock in order details.");
                            continue;
                        }
                        System.out.println("Stock In Order Details:");
                        System.out.printf("Supplier ID: %s\n", specifics[0]);
                        System.out.printf("Supplier Name: %s\n", specifics[1]);
                        System.out.printf("Date Received: %s\n", specifics[2]);
                    }
                }
                System.out.println(); // Empty line between orders
                hasOrders = true; // At least one order has been found
            }  
    
            if (!hasOrders) {
                System.out.println("No Order Record...");
            }
            
        } catch (FileNotFoundException e) {
            System.out.println("Error: File not found.");
            e.printStackTrace();
        }
    }

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
            System.out.println(itemFilePath + " unable to open");
        }
    }
}




