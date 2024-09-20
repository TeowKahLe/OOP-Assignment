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
						Staff newUser = new Staff();
						newUser.register();
						newUser.storeStaffData();
    					break;
    				case 2:
						//Staff userLogin = new Staff(); //(real)
						//userLogin.login(order); //(real)
    					//Supplier test3 = new Supplier();
						//menu();
						//Order test = new Order();
						//test.storeItemtoArr();
						//StockInOrder test1 = new StockInOrder();
						//test1.stockInOrderMenu("LIN1");
						//StockInOrder.storeItemtoArr();
						//test1.sortDisplayItemArr();
						//Transaction transaction = new Transaction();
						//Order.orderManagement();
						//Inventory.inventoryManagement();
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
    			System.out.println("Incorrect input(Please entry NUMBER only)");
    		}	
	  }
	  scanner.close();
	}

	public static void menu(){
		Alignment.clearScreen();
		Alignment line = new Alignment();
    	Scanner scanner = new Scanner(System.in);
		boolean error = true;
		
		
		System.out.print("Fast Food Ordering System");
		line.printLine("Fast Food Ordering System".length());
		System.out.println("Please select your action");
		System.out.println("1. Item Management");
		System.out.println("2. Order Management");
		System.out.println("3. *pending*");
   	 	System.out.println("4. *pending*");
   		System.out.println("5. Return to Main Menu");
		System.out.println("6. Exit");

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
						//*pending*
    					break;
					case 4:
						//*pending*
    					break;
					case 5:
						main(new String[]{});
						break;
					case 6:
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
						Order.displayAllOrder();
						break;
					case 2:
						//Search Order
						break;
					case 3:
						//Track Delivery
						break;
					case 4:
						//Stock In Order
						Alignment.clearScreen();
						stockInOrder.stockInOrderMenu(order.getStaffId());
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
}





