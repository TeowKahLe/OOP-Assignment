import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.Scanner;

public class StockOutOrder extends Order{
    private String customerId;
    private String customerName;
    private String customerAddress;
    private Date dateDispatched;

    //-----------------------------------------------------------------------------------Constructors
    public StockOutOrder() {
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
						StockOutOrder.customerInputOrder();
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
    public static void customerInputOrder(){
        clearScreen();
		Line line = new Line();
		Scanner scanner = new Scanner(System.in);
		int num = 0;
        System.out.print("+");
        line.printLineNoNewLine(78);
        System.out.println("+");
        System.out.printf("|%-3s | %-15s | %-15s | %-15s | %-15s   |\n", "No.", "Name", "Category", "Description", "Unit Price (RM)");
        System.out.print("|");
        line.printLineNoNewLine(78);
        System.out.println("|");
		try {
            scanner = new Scanner(new File("itemInfo.txt"));

            while (scanner.hasNextLine()) {
                String[] itemFields = scanner.nextLine().split("\\|");
				num++;
                double itemField5 = Double.parseDouble(itemFields[5]);
                System.out.printf("|%-3d | %-15s | %-15s | %-15s | %-17.2f |\n", num, itemFields[1], itemFields[2], itemFields[3], itemField5);
                
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot locate item.txt");
        }
        System.out.print("+");
        line.printLineNoNewLine(78);
        System.out.println("+\n");
		
        System.out.print("How many item you want to place order?\n--> ");
        int noItem = scanner.nextInt();
        scanner.nextLine();
        System.out.print("\nPlease enter Item number and quantity\n");
        line.printLine("Please enter Item number and quantity".length());
        
        int qty = 0;
        for(int i = 1; i <= noItem ; i++){
            System.out.print("Item " + noItem + " : ");
            qty = scanner.nextInt();
            scanner.nextLine();

        }
        scanner.close();
	}

    //-----------------------------------------------------------------------------------Cls
    public static void clearScreen() {
   		System.out.print("\033[H\033[2J");
  	 	System.out.flush();
	}
}