import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
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
        System.out.print("+");
        line.printLineNoNewLine(112);
        System.out.println("+");
        System.out.printf("|%-3s | %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |\n",
                          "No.", "Order ID", "Approval", "Date", "Time", "Delivery", "Type");
        System.out.print("+");
        line.printLineNoNewLine(112);
        System.out.println("+");
		displayStockOutOrder();
        System.out.print("+");
        line.printLineNoNewLine(112);
        System.out.println("+\n");
		System.out.println("Please select your action");
		System.out.println("1. Add Order (For Customer)");
		System.out.println("2. Accept order");
        System.out.println("3. Reject Order ");
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
						//Acccept order
    					break;
    				case 3:
						//Reject order
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

        // Retrieve itemList from Order
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
        String orderId = Order.generateOrderId("SO");
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
        Order.storeOrderToFile(order, null, stockOutOrder);

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

    //-----------------------------------------------------------------------------------Display Stock Out Order
    public static void displayStockOutOrder(){
        File file = new File("orderInfo.txt");
        boolean noOrder = true;
        int num = 0;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                // Read the first line (Order line)
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

    // If no Stock Out Orders were found, display a message
        if (noOrder) {
            System.out.println("No Stock Out Orders record....");
        }
    }

    //-----------------------------------------------------------------------------------Accept Stock Out Order
    public static void acceptStockOutOrder(){
        Alignment.clearScreen();
        displayStockOutOrder();

        System.out.print("Please ");
    }


    
    
}