//import java.io.File;
//import java.io.FileWriter;
import java.util.Scanner;

public class fastFoodInventory {
	static Order order = new Order(); // all fastFoodInvetory method use same Order
	
    public static void main(String arg[]) {
    	Alignment line = new Alignment();
    	Scanner scanner = new Scanner(System.in);
		boolean error = true;
		
    	//header
    	System.out.println("Welcome to Fast Food Inventory System");
    	line.printLine("Welcome to Fast Food Inventory System".length());
    	System.out.println("Please select your action");
    	System.out.println("1)Sign up\n2)Login\n3)Exit");
    	
    	while(error){  //error occur
    		try{
    			//select action
    			System.out.print("Selected action: ");
    			int option = scanner.nextInt();
				scanner.nextLine();

    			//go to selected method
    			switch(option){
    				case 1:
						Alignment.clearScreen();
						Staff newUser = new Staff();
						newUser.register();
						newUser.storeStaffData();
						break;
    				case 2:
						Alignment.clearScreen();
						Staff userLogin = new Staff();
						userLogin.login(order);
						break;
					case 3:
						System.exit(0);
    					break;
    				default:
    					System.out.println("Invalid action selected");
    					continue;
    			}
			error = false;
    		}catch (Exception e){
				scanner.nextLine(); //clear input buffer allow go into try block and prevent infinitely loop
    			System.out.println("Incorrect input(Please entry NUMBER only!)");
    		}	
	  }
	  scanner.close();
	}

	public static void menu(){
		Alignment.clearScreen();
		Alignment line = new Alignment();
    	Scanner scanner = new Scanner(System.in);
		boolean error = true;
		
		
		System.out.println("Fast Food Ordering System");
		line.printLine("Fast Food Ordering System".length());
		System.out.println("Please select your action");
		System.out.println("1. Item Management");
		System.out.println("2. Order Management");
		System.out.println("3. Transaction");
   	 	System.out.println("4. Report");
		System.out.println("5. Supplier Management");
   		System.out.println("6. Return to Main Menu");
		System.out.println("7. Exit");

		while(error){
			try{
				System.out.print("Selected action: ");
    			int opt = scanner.nextInt();
				scanner.nextLine();
				switch(opt){
    				case 1:
						Inventory.inventoryManagement();
    					break;
    				case 2:
						orderManagement();
    					break;
					case 3:
						Alignment.clearScreen();
						Transaction.generateReport();
						returnMenu();
    					break;
					case 4:
						Alignment.clearScreen();
						StockInOrder stockInOrder = new StockInOrder();
						stockInOrder.generateReport();
						System.out.println("\n\n");
						StockOutOrder stockOutOrder = new StockOutOrder();
						stockOutOrder.generateReport();
						returnMenu();
    					break;
					case 5:
						supplierManagement();
						break;
					case 6:
						Alignment.clearScreen();
						main(new String[]{});
						break;
					case 7:
						System.exit(0);
						break;
    				default:
    					System.out.println("Invalid action selected");
    					continue;
    			}
				error = false;
			}catch (Exception e){
    			System.out.println("Incorrect input(Please enter NUMBER only)");
    			scanner.nextLine();
    		}	
		}
		scanner.close();
	}


	 //-----------------------------------------------------------------------------------Order Management
	 public static void orderManagement(){
		Alignment.clearScreen();
		Alignment line = new Alignment();
		Scanner scanner = new Scanner(System.in);
		boolean error = true;
		StockInOrder stockInOrder = new StockInOrder();
	
		line.printEqualLine(" ORDER MANAGEMENT ".length());
		System.out.println(" ORDER MANAGEMENT ");
		line.printEqualLine(" ORDER MANAGEMENT ".length());
		System.out.println("Please select your action");
		System.out.println("1. Stock In Order");
		System.out.println("2. Stock Out Order");
		System.out.println("3. Return to Menu");
		System.out.println("4. Exit");
	
		while(error){
			try{
				System.out.print("Selected action: ");
				int option = scanner.nextInt();
				scanner.nextLine();
				switch(option){
					case 1:
						//Stock In Order
						Alignment.clearScreen();
						stockInOrder.stockInOrderMenu(order.getStaffId());
						break;
					case 2:
						//Stock Out Order
						Alignment.clearScreen();
						StockOutOrder.stockOutOrderMenu();
						break;
					case 3:
						Alignment.clearScreen();
						fastFoodInventory.menu();
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

	//------------------------------------------------------------------------ Supplier Management
	public static void supplierManagement(){
		Alignment.clearScreen();
		Alignment line = new Alignment();
		Scanner scanner = new Scanner(System.in);
		boolean error = true;
		Supplier supplier = new Supplier();
	
		line.printEqualLine(" SUPPLIER MANAGEMENT ".length());
		System.out.println(" SUPPLIER MANAGEMENT ");
		line.printEqualLine(" SUPPLIER MANAGEMENT ".length());
		System.out.println("Please select your action");
		System.out.println("1. Add Supplier");
		System.out.println("2. Delete Supplier");
		System.out.println("3. Modify Supplier");
		System.out.println("4. Return to Menu");
		System.out.println("5. Exit");
	
		while(error){
			try{
				System.out.print("Selected action: ");
				int option = scanner.nextInt();
				scanner.nextLine();
				switch(option){
					case 1:
						supplier.register();
						System.out.println("Press enter to continue...");
	                    System.in.read();  // Waits for a key press
						supplierManagement();
						break;
					case 2:
						supplier.deleteSupplier();
						System.out.println("Press enter to continue...");
	                    System.in.read();  // Waits for a key press
						supplierManagement();
						break;
					case 3:
						supplier.modifySupplier();
						System.out.println("Press enter to continue...");
	                    System.in.read();  // Waits for a key press
						supplierManagement();
						break;
					case 4:
						Alignment.clearScreen();
						fastFoodInventory.menu();
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

	public static void returnMenu(){
		// Back to menu or exit
		Scanner scanner = new Scanner(System.in);
        int opt = 0;
        boolean loop = true;
        while (loop) {
            System.out.println("\nPlease select your action\n1.Back to Fast Food Inventory Menu\n2.Exit");
            try {
                System.out.print("Selected action: ");
                opt = scanner.nextInt();
                scanner.nextLine();
                switch (opt) {
                    case 1:
                        menu();
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
}





