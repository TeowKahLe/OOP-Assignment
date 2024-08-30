import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class StockOutOrder extends Order{
    private String customerId;
    private String customerName;
    private String customerAddress;
    private Date dateDispatched;

    //-----------------------------------------------------------------------------------Constructors
    public StockOutOrder() {
        super();
    }

    public StockOutOrder(String customerId, String customerName, String customerAddress, Date dateDispatched) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.dateDispatched = dateDispatched;
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
        clearScreen();
		Line line = new Line();
    	Scanner scanner = new Scanner(System.in);
		boolean error = true;

		line.printEqualLine("STOCK OUT ORDER".length());
		System.out.println("STOCK OUT ORDER");
		line.printEqualLine("STOCK OUT ORDER".length());
		//Display all stock out order only
		System.out.println("Please select your action");
		System.out.println("1. Accept Order");
		System.out.println("2. Cancel Order");
        System.out.println("3. Add Order (For Customer)");
		System.out.println("4. Return to Order Management");
		System.out.println("5. Exit");

		
		while(error){
			try{
				System.out.print("Selected action: ");
    			int option = scanner.nextInt();
                scanner.nextLine();
				switch(option){
					case 1:
						//Accept order
    					break;
    				case 2:
						//Reject order
    					break;
    				case 3:
						StockOutOrder stockOutOrder = new StockOutOrder();
                        stockOutOrder.customerInputOrder();
    					break;
					case 4:
						error = false;
						orderManagement();
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
        clearScreen();
        Line line = new Line();
        Scanner scanner = new Scanner(System.in);
        int num = 0;

        // Populate itemList
        readItemFromFile("itemInfo.txt");
        // Retrieve itemList from Order
        List<Item> itemList = getItemList(); 

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
        int noItem = scanner.nextInt();
        scanner.nextLine();

        int[] inputItemNo = new int[noItem];
        int[] inputItemQty = new int[noItem];
        List<Item> orderedItems = new ArrayList<>();

        System.out.print("\nPlease enter Item number and quantity\n");
        line.printLine("Please enter Item number and quantity".length());

        for (int i = 0; i < noItem; i++) {
            System.out.print("Item " + (i + 1) + " : ");
            inputItemNo[i] = scanner.nextInt();
            scanner.nextLine();
        
            if (inputItemNo[i] <= 0 || inputItemNo[i] > itemList.size()) {
                System.out.println("Invalid item number. Please try again.");
                i--;
                continue;
            }
        
            Item selectedItem = itemList.get(inputItemNo[i] - 1); // Adjust for 0-based index
            System.out.print("Quantity : ");
            inputItemQty[i] = scanner.nextInt();
            scanner.nextLine();
            orderedItems.add(selectedItem);
            System.out.println("You have ordered " + inputItemQty[i] + " " + selectedItem.getItemName() + "\n");
        }
        
        // Set order details
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MMM/yyyy"));
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
        String orderId = generateOrderId("SO"); 
        setOrderId(orderId);
        setItemQty(inputItemQty); 
        setApprovalStatus("pending");
        setOrderDate(currentDate); // Set current date as orderDate
        setOrderTime(currentTime); // Set current time as orderTime
        setDeliveryMethod("-");
        setOrderType("Stock Out Order");
        setStaffId("-");
        setItemList(orderedItems);

        storeOrderToFile();

        int opt = 0;
        boolean loop = true;
        while (loop) {
            System.out.println("1.Back to Stock Out Order Menu\n2.Exit");
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
                scanner.nextLine(); // Clear the buffer to avoid infinite loop
            }
        }
        scanner.close();
	}

    //-----------------------------------------------------------------------------------Cls
    public static void clearScreen() {
   		System.out.print("\033[H\033[2J");
  	 	System.out.flush();
	}
}