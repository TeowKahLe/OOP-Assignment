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
		System.out.println("0. Add Order (Assume customer wants to add order)");
		System.out.println("1. Accept Order");
		System.out.println("2. Cancel Order");
		System.out.println("3. Return to Order Management");
		System.out.println("4. Exit");

		
		while(error){
			try{
				System.out.print("Selected action: ");
    			int opt = scanner.nextInt();
				switch(opt){
					case 0:
						customerInputOrder();
						//For customer to add order
    					break;
    				case 1:
						//Accept order
    					break;
    				case 2:
						//Cancel Order
    					break;
					case 3:
						error = false;
						orderManagement();
						break;
					case 4:
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

    //-----------------------------------------------------------------------------------Customer Input Order Method
    public static void customerInputOrder(){
        clearScreen();
		Line line = new Line();
		Scanner scanner = null;
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
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        System.out.print("+");
        line.printLineNoNewLine(78);
        System.out.println("+\n");
		
        scanner.nextLine();
        System.out.print("How many item you want to place order?\n--> ");
        int noItem = scanner.nextInt();

        for(int i = 0; i < noItem ; i++){

        }
        
	}

    public static void clearScreen() {
   		System.out.print("\033[H\033[2J");
  	 	System.out.flush();
	}
}