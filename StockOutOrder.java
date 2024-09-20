import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
        StockOutOrder stockOutOrder = new StockOutOrder();

        System.out.print("\t\t\t\t     ");
		line.printEqualLine(" STOCK OUT ORDER ".length());
		System.out.println("\t\t\t\t      STOCK OUT ORDER ");
        System.out.print("\t\t\t\t     ");
		line.printEqualLine(" STOCK OUT ORDER ".length());
		displayStockOutOrder();
		System.out.println("Please select your action");
		System.out.println("1. Add Order (For Customer)");
		System.out.println("2. Search Order");
        System.out.println("3. Manage Order status");
        System.out.println("4. Delete Order");
		System.out.println("5. Return to Order Management");
		System.out.println("6. Exit");

		
		while(error){
			try{
				System.out.print("Selected action: ");
    			int option = scanner.nextInt();
                scanner.nextLine();
				switch(option){
					case 1:
                        stockOutOrder.customerInputOrder();
    					break;
    				case 2:
                        stockOutOrder.searchStockOutOrder();
                        int opt = 0;
                        boolean loop = true;
                        while (loop) {
                            System.out.print("\nPlease select your action\n1.Back to Stock Out Order Menu\n2.Exit\n");
                                try {
                                    System.out.print("Selected action: ");
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
                                            System.out.println("Invalid action selected\n");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Incorrect input (Please enter NUMBER only)\n");
                                    scanner.nextLine();
                                }
                            }
    					break;
    				case 3:
                        stockOutOrder.manageStockOutOrderStatus();
                        break;
                    case 4:
                        stockOutOrder.deleteStockOutOrder();
                        break;
					case 5:
						fastFoodInventory.orderManagement();
						break;
					case 6:
						System.exit(0);
						break;
    				default:
    					System.out.println("Invalid action selected\n");
    					break;	
    			}
			}catch (Exception e){
    			System.out.println("Incorrect input(Please enter NUMBER only)\n");
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
            System.out.println("No items available to display...\n");
            int opt = 0;
            boolean loop = true;
            System.out.println("Please select your action\n1.Back to Stock Out Order Menu\n2.Exit");
            while (loop) {
                try {
                    System.out.print("Selected action: ");
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
                            System.out.println("Invalid action selected\n");
                    }
                } catch (Exception e) {
                    System.out.println("Incorrect input (Please enter NUMBER only)\n");
                    scanner.nextLine();
                }
            }
        }

        System.out.print("\t\t\t\t ");
		line.printEqualLine(" PLACE ORDER ".length());
		System.out.println("\t\t\t\t  PLACE ORDER ");
        System.out.print("\t\t\t\t ");
		line.printEqualLine(" PLACE ORDER ".length());

        // Display items
        System.out.print("+");
        line.printLineNoNewLine(78);
        System.out.println("+");
        System.out.printf("| %-3s | %-15s | %-15s | %-15s | %-15s  |\n", "No.", "Name", "Category", "Description", "Unit Price (RM)");
        System.out.print("|");
        line.printLineNoNewLine(78);
        System.out.println("|");

        for (Item item : itemList) {
            num++;
            System.out.printf("| %-3d | %-15s | %-15s | %-15s | %-16.2f |\n", num, item.getItemName(), item.getItemCategory(), item.getItemDesc(), item.getUnitPrice());
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
        String orderId = generateId();
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
        StockOutOrder stockOutOrderInput = new StockOutOrder(
            custList[customerIndex][0],//customer id
            custList[customerIndex][1],//customer name
            custList[customerIndex][2]//customer address
        );
        
        // Store order to file
        storeStockOutOrderToFile(order, stockOutOrderInput);

        int opt = 0;
        boolean loop = true;
        while (loop) {
            System.out.println("\nPlease select your action\n1.Back to Stock Out Order Menu\n2.Exit");
            try {
                System.out.print("Selected action: ");
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
                        System.out.println("Invalid action selected\n");
                }
            } catch (Exception e) {
                System.out.println("Incorrect input (Please enter NUMBER only)\n");
                scanner.nextLine();
            }
        }
        scanner.close();
	}

    //-----------------------------------------------------------------------------------Generate Stock Out Order Id
    public String generateId() {
        String filePath = "stockOutOrderInfo.txt";
        String latestOrderId = null;
        int latestNumber = 0;
        String orderTypePrefix = "SO";

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

    //-----------------------------------------------------------------------------------Delete stock out order
    public void deleteStockOutOrder() {
        Alignment.clearScreen();
        Alignment alignmentLine = new Alignment();
        int option = 0;
        boolean validOption = false;
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("\t\t\t\t\t     ");
        alignmentLine.printEqualLine(" DELETE ORDER ".length());
        System.out.println("\t\t\t\t\t      DELETE ORDER ");
        System.out.print("\t\t\t\t\t     ");
        alignmentLine.printEqualLine(" DELETE ORDER ".length());
    
        System.out.print("+");
        alignmentLine.printLineNoNewLine(94);
        System.out.println("+");
        System.out.printf("| %-3s | %-15s | %-15s | %-15s | %-15s | %-14s |\n",
                "No.", "Order ID", "Approval", "Date", "Time", "Delivery");
        System.out.print("+");
        alignmentLine.printLineNoNewLine(94);
        System.out.println("+");
    
        // Display rejected orders
        List<String[]> rejectedOrders = new ArrayList<>();
        int num = 0;
        File orderFile = new File("stockOutOrderInfo.txt");
    
        try (Scanner fileScanner = new Scanner(orderFile)) {
            while (fileScanner.hasNextLine()) {
                String orderDetailsLine = fileScanner.nextLine();
                String customerDetailsLine = fileScanner.nextLine(); // Assuming the next line is customer details
                String itemListLine = fileScanner.nextLine(); // Assuming the next line is item list
    
                String[] orderDetails = orderDetailsLine.split("\\|");
                if (orderDetails[1].equals("Rejected")) { // Only process rejected orders
                    rejectedOrders.add(new String[]{orderDetailsLine, customerDetailsLine, itemListLine});
                    System.out.printf("| %-3d | %-15s | %-15s | %-15s | %-15s | %-14s |\n",
                            ++num, orderDetails[0], orderDetails[1], orderDetails[2], orderDetails[3], orderDetails[4]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    
        System.out.print("+");
        alignmentLine.printLineNoNewLine(94);
        System.out.println("+");
    
        // If no rejected orders found
        if (rejectedOrders.isEmpty()) {
            System.out.println("No Rejected Orders found.");
        }
    
        // Let the user choose which order to delete
        System.out.println("Which rejected order do you want to delete? (Please enter a number only): ");
    
        while (!validOption) {
            System.out.print("Selected action: ");
            try {
                option = scanner.nextInt();
                scanner.nextLine(); // Consume newline
    
                // Validate the selected option
                if (option < 1 || option > rejectedOrders.size()) {
                    System.out.println("Invalid choice. Please try again.");
                    continue;
                }
    
                // Get the selected order details
                String[] orderToDelete = rejectedOrders.get(option - 1);
                String[] orderDetails = orderToDelete[0].split("\\|"); 
                System.out.print("You have selected to delete the following order ID: ");
                System.out.println(orderDetails[0]);
    
                // Confirm deletion
                System.out.print("Are you sure you want to delete this order? (yes/no): ");
                String confirmation = scanner.nextLine();
                if (!confirmation.equalsIgnoreCase("yes")) {
                    System.out.println("Deletion cancelled.");
                    break;
                } else {
                    // Delete the selected order from the file
                    deleteOrder(orderFile, orderToDelete[0]);
    
                    // Show which order was deleted
                    System.out.println("Order with ID " + orderDetails[0] + " has been deleted.");
                    validOption = true;
                }
    
            } catch (Exception e) {
                System.out.println("Incorrect input (Please enter a NUMBER only)");
                scanner.nextLine(); // Clear the buffer
            }
        }
    
        int opt = 0;
        boolean loop = true;
        while (loop) {
            System.out.println("\nPlease select your action\n1.Back to Stock Out Order Menu\n2.Exit");
            try {
                System.out.print("Selected action: ");
                opt = scanner.nextInt();
                scanner.nextLine();
                switch (opt) {
                    case 1:
                        StockOutOrder.stockOutOrderMenu();
                        loop = false;
                        break;
                    case 2:
                        System.exit(0);
                    default:
                        System.out.println("Invalid action selected\n");
                }
            } catch (Exception e) {
                System.out.println("Incorrect input (Please enter NUMBER only)\n");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private void deleteOrder(File orderFile, String orderIdToDelete) {
        File tempFile = new File("stockOutOrderInfo.txt");
    
        try (Scanner fileScanner = new Scanner(orderFile);
             FileWriter writer = new FileWriter(tempFile)) {
    
            while (fileScanner.hasNextLine()) {
                String orderDetailsLine = fileScanner.nextLine();
                String customerDetailsLine = fileScanner.nextLine();
                String itemListLine = fileScanner.nextLine();
    
                // Only write back orders that do not match the orderIdToDelete
                String[] orderDetails = orderDetailsLine.split("\\|");
                if (!orderDetails[0].equals(orderIdToDelete)) {
                    // Write back the order details
                    writer.write(orderDetailsLine + "\n");
                    writer.write(customerDetailsLine + "\n");
                    writer.write(itemListLine + "\n");
                } else {
                    System.out.println("Skipping storing details back for order ID: " + orderIdToDelete);
                }
            }
    
        } catch (IOException e) {
            System.out.println("Error while deleting order: " + e.getMessage());
        }
    
        // Replace the original file with the temp file
        if (orderFile.delete()) {
            if (!tempFile.renameTo(orderFile)) {
                System.out.println("Error renaming temp file to original file.");
            }
        } else {
            System.out.println("Error deleting the original file.");
        }
    }

    //-----------------------------------------------------------------------------------Store stock out order to file
    public void storeStockOutOrderToFile(Order order, StockOutOrder stockOutOrder) {
        try (FileWriter writer = new FileWriter("stockOutOrderInfo.txt", true)) { // Open in append mode
            // Write Order details
            writer.write(order.getOrderId() + "|" +
                         order.getApprovalStatus() + "|" +
                         order.getFormattedOrderDate() + "|" +
                         order.getFormattedOrderTime() + "|" +
                         order.getDeliveryMethod() + "|" +
                         order.getOrderType() + "|" +
                         order.getStaffId() + "\n");
            if(stockOutOrder.getDateDispatched() == null){
                writer.write(stockOutOrder.getCustomerId() + "|" +
                         stockOutOrder.getCustomerName() + "|" +
                         stockOutOrder.getCustomerAddress() + "|" +
                         "-" + "\n");
            }else{
                writer.write(stockOutOrder.getCustomerId() + "|" +
                         stockOutOrder.getCustomerName() + "|" +
                         stockOutOrder.getCustomerAddress() + "|" +
                         stockOutOrder.getDateDispatched() + "\n");
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


    //-----------------------------------------------------------------------------------Display Stock Out Order
    public static boolean displayStockOutOrder(){
        Alignment alignmentLine = new Alignment();
        File file = new File("stockOutOrderInfo.txt");
        boolean noOrder = true;
        int num = 1;

        System.out.print("+");
        alignmentLine.printLineNoNewLine(94);
        System.out.println("+");
        System.out.printf("| %-3s | %-15s | %-15s | %-15s | %-15s | %-14s |\n",
                          "No.", "Order ID", "Approval", "Date", "Time", "Delivery");
        System.out.print("+");
        alignmentLine.printLineNoNewLine(94);
        System.out.println("+");

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] orderDetails = line.split("\\|");
                
                // Check if the line starts with "SO" (indicating a Stock Out Order)
                if (orderDetails[0].startsWith("SO")) {
                    noOrder = false;
                    System.out.printf("| %-3s | %-15s | %-15s | %-15s | %-15s | %-14s |\n", num++, orderDetails[0], orderDetails[1], orderDetails[2], orderDetails[3], orderDetails[4], orderDetails[5]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    
        if (noOrder) {
            System.out.printf("%-95s|\n", "|No Stock Out Orders record....");
            System.out.print("+");
            alignmentLine.printLineNoNewLine(94);
            System.out.println("+\n");
            return false;
        }
        System.out.print("+");
        alignmentLine.printLineNoNewLine(94);
        System.out.println("+\n");
        return true;
    }

    //-----------------------------------------------------------------------------------Display Stock Out Order Item List
    public boolean displayStockOutOrderItemList(String orderId, String manageType) {
        Alignment alignmentLine = new Alignment();
        File orderFile = new File("stockOutOrderInfo.txt");
        boolean noOrder = true;
        

        // Print table header
        if(manageType == "Approval"){
            System.out.print("+");
            alignmentLine.printLineNoNewLine(119);
            System.out.println("+");
            System.out.printf("| %-3s | %-7s | %-15s | %-17s | %-15s | %-15s | %-11s | %-13s |\n",
                          "No.", "Item Id", "Item Name", "Current Stock Qty", "Min Stock Limit", "Max Stock Limit", "Ordered Qty", "Ordered Value");
            System.out.print("+");
            alignmentLine.printLineNoNewLine(119);
            System.out.println("+");
        }else if(manageType == "Search"){
            System.out.printf("| %-3s | %-7s | %-15s | %-11s | %-13s |\n",
                          "No.", "Item Id", "Item Name", "Ordered Qty", "Ordered Value");
            System.out.print("+");
            alignmentLine.printLineNoNewLine(63);
            System.out.println("+");
        }
    
        // Search order ID in the file
        int row = 1;
        List<String> orderedItemIdList = new ArrayList<>();
        List<Integer> orderedItemQtyList = new ArrayList<>();
    
        try (Scanner scanner = new Scanner(orderFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] orderDetails = line.split("\\|");
    
                // Check if the line is an order detail line
                if (orderDetails.length > 0 && orderDetails[0].startsWith("SO")) {
                    if (orderDetails[0].equals(orderId)) {
                        noOrder = false;
                        row = 1; // Reset row for this order
                    } else {
                        // Skip over the next 2 lines if not matching order
                        for (int i = 0; i < 2; i++) {
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
                                // Handle number format exception
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
                    // Check for matching item ID
                    if (item.getItemId().equals(orderedItemId)) {
                        // Access Inventory attributes through Item
                        Inventory inventoryItem = item.getInventory();
    
                        // Display item information along with the ordered quantity
                        if(manageType == "Approval"){
                            System.out.printf("| %-3d | %-7s | %-15s | %-17d | %-15d | %-15d | %-11d | RM %-10.2f |\n",
                                          (i + 1), // Item number
                                          item.getItemId(), 
                                          item.getItemName(), 
                                          inventoryItem.getStockQty(), 
                                          inventoryItem.getMinStockQty(), 
                                          inventoryItem.getMaxStockQty(), 
                                          orderedQty, 
                                          item.getUnitPrice() * orderedQty);
                        }else if(manageType == "Search"){
                            System.out.printf("| %-3d | %-7s | %-15s | %-11d | RM %-10.2f |\n",
                                          (i + 1), // Item number
                                          item.getItemId(), 
                                          item.getItemName(), 
                                          orderedQty, 
                                          item.getUnitPrice() * orderedQty);
                        }
                        itemFound = true;
                        break; // Exit after finding the item
                    }
                }
    
                if (!itemFound) {
                    // Handle case where item was not found in the itemList
                    System.out.printf("[!] Item ID not found in Item List: %s\n\n", orderedItemId);
                    return false;
                }

            }
            if(manageType == "Approval"){
                System.out.print("+");
                alignmentLine.printLineNoNewLine(119);
                System.out.println("+");
            }
            
        }
        return true;
    }
    
    //-----------------------------------------------------------------------------------Search Search Stock Out Order
    public void searchStockOutOrder(){
        int ingnoreScanner = 1;
        Alignment.clearScreen();
        Alignment alignmentLine = new Alignment();
        int option = 0;
        boolean validOption = false;
        Scanner scanner = new Scanner(System.in);

        System.out.print("\t\t\t\t     ");
		alignmentLine.printEqualLine(" STOCK OUT ORDER ".length());
		System.out.println("\t\t\t\t      STOCK OUT ORDER ");
        System.out.print("\t\t\t\t     ");
		alignmentLine.printEqualLine(" STOCK OUT ORDER ".length());
        displayStockOutOrder();
    
        System.out.println("Which order do you want to search? (Please enter a number only): ");
    
        while (!validOption) {
            System.out.print("Selected action: ");
            try {
                option = scanner.nextInt();
                scanner.nextLine(); 

                // Validate the selected option and retrieve the corresponding order ID
                String selectedOrderId = null;
                int num = 0;
                File orderFile = new File("stockOutOrderInfo.txt");
            
                // Read the file to find the selected order ID
                try (Scanner fileScanner = new Scanner(orderFile)) {
                    while (fileScanner.hasNextLine()) {
                        String line = fileScanner.nextLine();
                        String[] orderDetails = line.split("\\|");

                        if (orderDetails[0].startsWith("SO")) {
                            num++;
                            if (num == option) {
                                selectedOrderId = orderDetails[0];
                                break;
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("File not found: " + e.getMessage());
                }

                if (selectedOrderId != null) {
                    validOption = true;

                    // Now, display the additional details
                    try (Scanner fileScanner = new Scanner(orderFile)) {
                        boolean orderFound = false;
                        while (fileScanner.hasNextLine()) {
                            String line = fileScanner.nextLine();
                            String[] orderDetails = line.split("\\|");

                            if (orderDetails[0].equals(selectedOrderId)) {
                                orderFound = true;
                                // Print additional details
                                Alignment.clearScreen();
                                System.out.print("+");
                                alignmentLine.printLineNoNewLine(63);
                                System.out.println("+");
                                System.out.printf("| Order Id        : %-43s |\n", orderDetails[0]);
                                System.out.printf("| Approval Status : %-43s |\n", orderDetails[1]);
                                System.out.printf("| Order Date      : %-43s |\n", orderDetails[2]);
                                System.out.printf("| Order Time      : %-43s |\n", orderDetails[3]);
                                System.out.printf("| Delivery method : %-43s |\n", orderDetails[4]);
                                System.out.printf("| Order Type      : %-43s |\n", orderDetails[5]);
                                System.out.printf("| Staff ID        : %-43s |\n", orderDetails[6]); 
                                System.out.print("+");
                                alignmentLine.printLineNoNewLine(63);
                                System.out.println("+");
                                // Display the details of the selected order
                                StockOutOrder displayItemList = new StockOutOrder();
                                if(displayItemList.displayStockOutOrderItemList(selectedOrderId, "Search") == true){
                                    System.out.print("+");
                                    alignmentLine.printLineNoNewLine(63);
                                    System.out.println("+");
                                }
                                break;
                            }
                        }
                    
                        if (!orderFound) {
                            System.out.println("Order ID not found in the file.");
                        }
                    } catch (FileNotFoundException e) {
                        System.out.println("File not found: " + e.getMessage());
                    }
                } else {
                    System.out.println("Invalid action\n");
                }
            
            } catch (Exception e) {
                System.out.println("Incorrect input (Please enter a NUMBER only)\n");
                scanner.nextLine(); // Clear the buffer
            }
        }

        if(ingnoreScanner != 1){
            scanner.close();
        }
    }

    //-----------------------------------------------------------------------------------Manage Stock Out Order Status
    public void manageStockOutOrderStatus() {
        Alignment.clearScreen();
        Alignment alignmentLine = new Alignment();
        Scanner scanner = new Scanner(System.in);
        File file = new File("stockOutOrderInfo.txt");
        
        System.out.print("\t\t\t\t    ");
        alignmentLine.printEqualLine(" MANAGE ORDER STATUS ".length());
        System.out.println("\t\t\t\t     MANAGE ORDER STATUS ");
        System.out.print("\t\t\t\t    ");
        alignmentLine.printEqualLine(" MANAGE ORDER STATUS ".length());
        if (displayStockOutOrder()) {
            int option = -1;
            String currentOrderId = null;
            boolean noOrder = true;
            int num = 0;

            System.out.print("Which order do you want to manage? (please enter a number)\n");
            // Validate user input for selecting an order
            while (noOrder) {
                System.out.print("Selected action: ");
                try {
                    option = scanner.nextInt();
                    scanner.nextLine(); // Clear the input buffer

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
                        System.out.println("File not found: " + file.getName());
                        return;
                    }

                    if (!noOrder) {
                        // Display the details of the selected order
                        Alignment.clearScreen();
                        System.out.print("\t\t\t\t\t\t         ");
                        alignmentLine.printEqualLine("ORDER ".length() + 8);
                        System.out.println("\t\t\t\t\t\t\t  ORDER " + currentOrderId);
                        System.out.print("\t\t\t\t\t\t         ");
                        alignmentLine.printEqualLine("ORDER ".length() + 8);
                        StockOutOrder displayItemList = new StockOutOrder();
                        if(displayItemList.displayStockOutOrderItemList(currentOrderId, "Approval") == false){
                            StockOutOrder modifyRejectStatus = new StockOutOrder();
                            modifyRejectStatus.modifyStockOutOrderStatus(currentOrderId, "Rejected");
                            int opt = 0;
                            boolean loop = true;
                            System.out.println("\nPlease select your action\n1.Back to Stock Out Order Menu\n2.Exit");
                            while (loop) {
                                try {
                                    System.out.print("Selected action: ");
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
                                            System.out.println("Invalid action selected\n");
                                    }
                                } catch (Exception e) {
                                    System.out.println("Incorrect input (Please enter NUMBER only)\n");
                                    scanner.nextLine();
                                }
                            }
                        }
                    break; // Exit loop if order found
                    } else {
                        System.out.println("No Stock Out Orders record....\n");
                    }
                } catch (Exception e) {
                    System.out.println("Invalid action selected\n");
                    scanner.nextLine();
                }
            }

            System.out.print("\nPlease select your action\n[1] Accept Order\n[2] Reject Order\n[3] Back to Stock Out Order Menu\n");
            // Handle actions for the selected order
            boolean error = true;
            while (error) {
                
                System.out.print("Selected action: ");
                int opt = 0;
                try {
                    opt = scanner.nextInt();
                    scanner.nextLine();
                    switch (opt) {
                        case 1:
                            StockOutOrder modifyAcceptStatus = new StockOutOrder();
                            modifyAcceptStatus.modifyStockOutOrderStatus(currentOrderId, "Accepted");
                            break;
                        case 2:
                            StockOutOrder modifyRejectStatus = new StockOutOrder();
                            modifyRejectStatus.modifyStockOutOrderStatus(currentOrderId, "Rejected");
                            break;
                        case 3:
                            StockOutOrder.stockOutOrderMenu();
                        default:
                            System.out.println("Invalid action selected\n");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("Incorrect input (Please enter a NUMBER only)\n");
                    scanner.nextLine();
                }
            }
        } else {
            System.out.println("Failed to display Stock Out Orders.");
        }
        scanner.nextLine();
        scanner.close();
    }

    //-----------------------------------------------------------------------------------Change order status in text file
    public void modifyStockOutOrderStatus(String orderId, String newStatus) {
        File orderFile = new File("stockOutOrderInfo.txt");
        List<String> stockOutOrderFileContent = new ArrayList<>();
        boolean orderFound = false;
        boolean modifyDispatchedDate = false;

        // Random transportation method options
        String[] transportOptions = {"Bus", "Van", "Truck"};
        Random random = new Random();

        try (Scanner scanner = new Scanner(orderFile)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] orderDetails = line.split("\\|");

                // First line - Modify the approval status and potentially the transportation method
                if (orderDetails[0].startsWith("SO")) {
                    if (orderDetails[0].equals(orderId)) { // Check for matching orderId
                        orderFound = true;
                        // Modify the status in the order line (first line)
                        orderDetails[1] = newStatus;

                        // Modify delivery method and dispatched date based on status
                        if (newStatus.equals("Accepted")) {
                            if (orderDetails[4].equals("-")) {
                                orderDetails[4] = transportOptions[random.nextInt(transportOptions.length)];
                            }
                            modifyDispatchedDate = true;
                        } else if (newStatus.equals("Rejected")) {
                            orderDetails[4] = "-"; // Reset delivery method
                            modifyDispatchedDate = true;
                        }

                        line = String.join("|", orderDetails);
                    }
                } 
                // Second line - Modify the dispatched date based on the status
                else if (modifyDispatchedDate && !orderDetails[0].startsWith("SO") && orderDetails.length >= 4) {
                    if (orderDetails[orderDetails.length - 1].equals("-")) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                        if (newStatus.equals("Accepted")) {
                            int daysToAdd = random.nextInt(8) + 3; // Random number between 3 and 10
                            long currentTime = new Date().getTime();
                            long addedTime = daysToAdd * 24L * 60 * 60 * 1000; // Add days in milliseconds
                            Date dispatchedDate = new Date(currentTime + addedTime);

                            // Update the last part of the second line (dispatched date)
                            orderDetails[orderDetails.length - 1] = dateFormat.format(dispatchedDate);
                        } else if (newStatus.equals("Rejected")) {
                            // Set dispatched date to "-"
                            orderDetails[orderDetails.length - 1] = "-";
                        }
                        line = String.join("|", orderDetails);
                    }
                    modifyDispatchedDate = false; // Reset flag after modifying the dispatched date
                } 
                // Add the (possibly modified) line to the list
                stockOutOrderFileContent.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + orderFile.getName());
        }

        // Write the modified content back to the file if the order was found
        if (orderFound) {
            try (PrintWriter writer = new PrintWriter(orderFile)) {
                for (String fileLine : stockOutOrderFileContent) {
                    writer.println(fileLine);
                }
                System.out.println("Order status updated to: " + newStatus);
            } catch (FileNotFoundException e) {
                System.out.println("Error writing to file: " + e.getMessage());
            }
        } else {
            System.out.println("Order ID not found.");
        }
    }

}
