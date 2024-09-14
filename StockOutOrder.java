import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class StockOutOrder{
    private String customerId;
    private String customerName;
    private String customerAddress;
    private Date dateDispatched;

    //-----------------------------------------------------------------------------------Constructors
    public StockOutOrder() {
    }

    public StockOutOrder(String customerId, String customerName, String customerAddress) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
    }

    //-----------------------------------------------------------------------------------Getters
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

   

    //-----------------------------------------------------------------------------------Setters
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
    
    //-----------------------------------------------------------------------------------Stock Out Order Menu
    public static void stockOutOrderMenu(){
        Alignment.clearScreen();
		Alignment line = new Alignment();
    	Scanner scanner = new Scanner(System.in);
		boolean error = true;

		line.printEqualLine("STOCK OUT ORDER".length());
		System.out.println("STOCK OUT ORDER");
		line.printEqualLine("STOCK OUT ORDER".length());
		displayStockOutOrder();
		System.out.println("Please select your action");
		System.out.println("1. Add Order (For Customer)");
		System.out.println("2. Search Order");
        System.out.println("3. Manage Order status");
		System.out.println("4. Return to Order Management");
		System.out.println("5. Exit");

		
		while(error){
			try{
				System.out.print("Selected action: ");
    			int option = scanner.nextInt();
                scanner.nextLine();
				switch(option){
					case 1:
                        StockOutOrder stockOutOrder = new StockOutOrder();
                        stockOutOrder.customerInputOrder();
    					break;
    				case 2:
                        //Search Order
    					break;
    				case 3:
                        manageStockOutOrderStatus();
					case 4:
						error = false;
						Order.orderManagement();
						break;
					case 5:
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

    //-----------------------------------------------------------------------------------Customer Input Order(Dummy cuz not part of Inventory System)
    public void customerInputOrder(){
        Alignment.clearScreen();
        Alignment line = new Alignment();
        Scanner scanner = new Scanner(System.in);
        int num = 0;
        List<Item> itemList = Order.readItemFromFile("itemInfo.txt");

        // Check if itemList is null or empty
        if (itemList == null || itemList.isEmpty()) {
            System.out.println("No items available to display.");
            scanner.close();
            return;
        }

        // Display items
        System.out.print("+");
        line.printLineNoNewLine(78);
        System.out.println("+");
        System.out.printf("|%-3s | %-15s | %-15s | %-15s | %-15s   |\n", "No.", "Name", "Category", "Description", "Unit Price (RM)");
        System.out.print("|");
        line.printLineNoNewLine(78);
        System.out.println("|");

        for (Item item : itemList) {
            num++;
            System.out.printf("|%-3d | %-15s | %-15s | %-15s | %-17.2f |\n", num, item.getItemName(), item.getItemCategory(), item.getItemDesc(), item.getUnitPrice());
        }

        System.out.print("+");
        line.printLineNoNewLine(78);
        System.out.println("+\n");
		

        // Customer input
        System.out.print("How many items do you want to place an order for?\n--> ");
        int noOfItemToOrder = scanner.nextInt();
        scanner.nextLine();

        int[] inputItemNo = new int[noOfItemToOrder];
        int[] inputItemQty = new int[noOfItemToOrder];
        List<Item> orderedItems = new ArrayList<>();

        System.out.print("\nPlease enter Item number and quantity\n");
        System.out.println("-------------------------------------");

        for (int i = 0; i < noOfItemToOrder; i++) {
            System.out.print("Item " + (i + 1) + " : ");
            int itemNumber = scanner.nextInt();
            scanner.nextLine();

            Item selectedItem = itemList.get(itemNumber - 1); // Adjust for 0-based index

            System.out.print("Quantity : ");
            int quantity = scanner.nextInt();
            scanner.nextLine(); 

            // Store the valid item and quantity
            inputItemNo[i] = itemNumber;
            inputItemQty[i] = quantity;
            orderedItems.add(selectedItem);
            System.out.println("You have ordered " + quantity + " " + selectedItem.getItemName() + "\n");
        }
        

        // Set order details
        Order order = new Order();
        String orderId = generateOrderId("SO");
        order.setOrderId(orderId);
        order.setApprovalStatus("Pending");
        order.setOrderDate(); // Set current date
        order.setOrderTime(); // Set current time
        order.setDeliveryMethod("-");
        order.setOrderType("Stock Out Order");
        order.setStaffId("-");
        order.setItemList(orderedItems);
        order.setItemQty(inputItemQty);

        // Customer list
        String[][] custList = {
            {"C0001", "Emily Brown", "101 Pine Rd"},
            {"C0002", "Lily Green", "456 Elm St"},
            {"C0003", "Michael Johnson", "456 Elm St"},
            {"C0004", "David Lee", "124 Main St"},
            {"C0005", "James Lee", "12 Mount Fo"}
        };

        // Randomly select a customer
        Random random = new Random();
        int customerIndex = random.nextInt(custList.length); // Random index from 0 to custList.length - 1
        StockOutOrder stockOutOrder = new StockOutOrder(
            custList[customerIndex][0],//customer id
            custList[customerIndex][1],//customer name
            custList[customerIndex][2]//customer address
        );
        
        // Store order to file
        storeStockOutOrderToFile(order, stockOutOrder);

        int opt = 0;
        boolean loop = true;
        while (loop) {
            System.out.println("\nPlease select your action\n1.Back to Stock Out Order Menu\n2.Exit");
            try {
                System.out.print("--> ");
                opt = scanner.nextInt();
                scanner.nextLine();
                switch(opt) {
                    case 1: 
                        StockOutOrder.stockOutOrderMenu();
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

    //-----------------------------------------------------------------------------------Generate Stock Out Order Id
    public static String generateOrderId(String orderTypePrefix) {
        String filePath = "stockOutOrderInfo.txt";
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

    //-----------------------------------------------------------------------------------Store stock out order to file
    public static void storeStockOutOrderToFile(Order order, StockOutOrder stockOutOrder) {
        try (FileWriter writer = new FileWriter("stockOutOrderInfo.txt", true)) { // Open in append mode
            // Write Order details
            writer.write(order.getOrderId() + "|" +
                         order.getApprovalStatus() + "|" +
                         order.getFormattedOrderDate() + "|" +
                         order.getFormattedOrderTime() + "|" +
                         order.getDeliveryMethod() + "|" +
                         order.getOrderType() + "|" +
                         order.getStaffId() + "|" + "\n");

            writer.write(stockOutOrder.getCustomerId() + "|" +
                         stockOutOrder.getCustomerName() + "|" +
                         stockOutOrder.getCustomerAddress() + "|" +
                         stockOutOrder.getDateDispatched() + "|" + "\n");
    
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


    //-----------------------------------------------------------------------------------Display Stock Out Order
    public static void displayStockOutOrder(){
        Alignment alignmentLine = new Alignment();
        File file = new File("stockOutOrderInfo.txt");
        boolean noOrder = true;
        int num = 1;

        System.out.print("+");
        alignmentLine.printLineNoNewLine(112);
        System.out.println("+");
        System.out.printf("|%-3s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |\n",
                          "No.", "Order ID", "Approval", "Date", "Time", "Delivery", "Type");
        System.out.print("+");
        alignmentLine.printLineNoNewLine(112);
        System.out.println("+");

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] orderDetails = line.split("\\|");
                
                // Check if the line starts with "SO" (indicating a Stock Out Order)
                if (orderDetails[0].startsWith("SO")) {
                    noOrder = false;
                    System.out.printf("|%-3s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |\n", num++, orderDetails[0], orderDetails[1], orderDetails[2], orderDetails[3], orderDetails[4], orderDetails[5], orderDetails[6]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    
        if (noOrder) {
            System.out.println("No Stock Out Orders record....");
        }

        System.out.print("+");
        alignmentLine.printLineNoNewLine(112);
        System.out.println("+\n");
    }

    //-----------------------------------------------------------------------------------Display Stock Out Order Item List
    public static void displayStockOutOrderItemList(String orderId) {
        Alignment.clearScreen();
        Alignment alignmentLine = new Alignment();
        File orderFile = new File("stockOutOrderInfo.txt");
        boolean noOrder = true;
    
        alignmentLine.printEqualLine("MANAGE ORDER".length() + 7);
        System.out.println("MANAGE ORDER " + orderId);
        alignmentLine.printEqualLine("MANAGE ORDER".length() + 7);
    
        // Print table header
        System.out.print("+");
        alignmentLine.printLineNoNewLine(124);
        System.out.println("+");
        System.out.printf("| %-3s | %-7s | %-15s | %-17s | %-15s | %-15s | %-11s | %-18s |\n",
                          "No.", "Item Id", "Item Name", "Current Stock Qty", "Min Stock Limit", "Max Stock Limit", "Ordered Qty", "Ordered Item Value");
        System.out.print("+");
        alignmentLine.printLineNoNewLine(124);
        System.out.println("+");
    
        // Search order ID in the file
        int row = 1;
        List<String> orderedItemIdList = new ArrayList<>();
        List<Integer> orderedItemQtyList = new ArrayList<>();
    
        try (Scanner scanner = new Scanner(orderFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] orderDetails = line.split("\\|");
    
                // Check if the line is an order detail line
                if (orderDetails[0].startsWith("SO")) {
                    if (orderDetails[0].equals(orderId)) {
                        noOrder = false;
                        row = 1; // Reset row for this order
                    } else {
                        // Skip over the next 3 lines if not matching order
                        for (int i = 0; i < 3; i++) {
                            if (scanner.hasNextLine()) {
                                scanner.nextLine();
                            }
                        }
                    }
                } else if (row == 1) {
                    row++; // Move to next row if current row is customer details
                } else if (row == 2) {
                    row++; // Move to next row if current row is ordered items
                    // Process ordered items
                    for (int i = 0; i < orderDetails.length; i += 2) {
                        if (i + 1 < orderDetails.length) {
                            try {
                                int qty = Integer.parseInt(orderDetails[i].trim());
                                String itemId = orderDetails[i + 1].trim();
                                orderedItemIdList.add(itemId);
                                orderedItemQtyList.add(qty);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid quantity format for item ID: " + orderDetails[i + 1].trim());
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("stockOutOrderInfo.txt not found: " + e.getMessage());
        }
    
        // If no order was found
        if (noOrder) {
            System.out.println("No searched order....");
        } else {
            // Read all items from itemInfo.txt
            List<Item> itemList = Order.readItemFromFile("itemInfo.txt");
    
            // Display items and ordered quantities
            for (int i = 0; i < orderedItemIdList.size(); i++) {
                String orderedItemId = orderedItemIdList.get(i);
                int orderedQty = orderedItemQtyList.get(i);
            
                boolean itemFound = false;
                for (Item item : itemList) {
                    // Check if the item is an instance of Inventory
                    if (item instanceof Inventory) {
                        Inventory inventoryItem = (Inventory) item;
                        
                        // Check for matching item ID
                        if (inventoryItem.getItemId().equals(orderedItemId)) {
                            // Display item information along with the ordered quantity
                            System.out.printf("| %-3d | %-7s | %-15s | %-17d | %-15d | %-15d | %-11d | RM%-16.2f |\n",
                                              (i + 1), // Item number
                                              inventoryItem.getItemId(), 
                                              inventoryItem.getItemName(), 
                                              inventoryItem.getStockQty(), 
                                              inventoryItem.getMinStockQty(), 
                                              inventoryItem.getMaxStockQty(), 
                                              orderedQty, 
                                              inventoryItem.getUnitPrice() * orderedQty);
                            itemFound = true;
                            break; // Exit after finding the item
                        }
                    }
                }
                
                if (!itemFound) {
                    // Debug message if the item was not found in the itemList
                    System.out.println("Item ID not found in itemList: " + orderedItemId);
                }
            }
    
            System.out.print("+");
            alignmentLine.printLineNoNewLine(124);
            System.out.println("+");
        }
    }

    //-----------------------------------------------------------------------------------Accept Stock Out Order
    public static void manageStockOutOrderStatus() {
        Alignment.clearScreen();
        Scanner scanner = new Scanner(System.in);
        File file = new File("stockOutOrderInfo.txt");
        boolean noOrder = true;
        int option = 0;
        int num = 0;
        String currentOrderId = "";
    
        displayStockOutOrder(); // Display available stock-out orders
    
        System.out.print("Which order do you want to manage? (please enter a number)\n--> ");
        option = scanner.nextInt();
        scanner.nextLine(); 
    
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] orderDetails = line.split("\\|");
    
                if (orderDetails[0].startsWith("SO")) {
                    num++;
                    if (num == option) {
                        currentOrderId = orderDetails[0];
                        noOrder = false;
                        break;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("orderInfo.txt not found: " + e.getMessage());
        }
    
        if (noOrder) {
            System.out.println("No Stock Out Orders record....");
        } else {
            // Display the details of the selected order
            displayStockOutOrderItemList(currentOrderId);
        }

        boolean error = true;
        int opt = 0;
        try{
            while(error){
                System.out.print("[1] Accept Order\n[2] Reject Order\n-->");
                opt = scanner.nextInt();
                scanner.nextLine();
                if(opt == 1){
                    //accept order
                }else if(opt == 2){
                    //reject order
                }
            }
            error = false;
        } catch (Exception e){
            System.out.println("Incorrect input(Please entry NUMBER only)");
            scanner.nextLine(); //clear input buffer allow go into try block and prevent infinitely loop
        }
        

        scanner.nextLine();
        scanner.close();
    }
}