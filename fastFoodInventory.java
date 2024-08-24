import java.util.Scanner;

public class fastFoodInventory {
    public static void main(String arg[]) {
		clearScreen();
    	Line line = new Line();
    	Scanner scanner = new Scanner(System.in);
		int error = 0;

    	//header
    	System.out.println("Welcome to Fast Food Inventory System");
    	line.printLine("Welcome to Fast Food Inventory System".length());
    	System.out.println("Please select your action");
    	System.out.println("1)Sign up\n2)Login\n3)Exit");
    	
    	while(error == 0){  //error occur
    		try{
    			//select action
    			System.out.print("Selected action: ");
    			int opt = scanner.nextInt();
    	
    			//go to selected method
    			switch(opt){
    				case 1:
						Staff newUser = new Staff();
						newUser.storeData();
    					break;
    				case 2:
    					break;
					case 3:
						System.exit(0);
    					break;
    				default:
    					System.out.println("Invalid action selected");
    					break;	
    			}
    			
    			if (opt == 1 || opt == 2){
    				error = 1;
    			}
 
    		}catch (Exception e){
    			System.out.println("Incorrect input(Please entry NUMBER only)");
    			scanner.nextLine(); //clear input buffer allow go into try block
    		}	
	  }
    	scanner.close();

	}

	public static void menu(){
		clearScreen();
		Line line = new Line();
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
				switch(opt){
    				case 1:
						itemManagement();
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
						error = false;
						main(new String[]{});
						break;
					case 6:
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
		
	}

	public static void itemManagement(){
		clearScreen();
		Line line = new Line();
    	Scanner scanner = new Scanner(System.in);
		boolean error = true;

		System.out.println("Please choose one option");
		line.printEqualLine("Please choose one option".length());
		System.out.println("1. Add item");
		System.out.println("2. Modify item");
		System.out.println("3. Delete item");
		System.out.println("4. Search item");
		System.out.println("5. Display all item");
		System.out.println("5. Return to menu");
		System.out.println("6. Exit");

		while(error){
			try{
				System.out.print("Selected action: ");
    			int opt = scanner.nextInt();
				switch(opt){
    				case 1:
						Item item = new Item();
						item.addItem();
						itemManagement();
    					break;
    				case 2:
						//modify
    					break;
					case 3:
						//delete
    					break;
					case 4:
						//search
    					break;
					case 5:
						//Display all
					case 6:
						error = false;
						menu();
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
	}

	public static void orderManagement(){
		clearScreen();
		Line line = new Line();
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
    			int opt = scanner.nextInt();
				switch(opt){
    				case 1:
						//Display all order
						orderManagement();
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
						stockOutOrder();
    					break;
					case 6:
						error = false;
						menu();
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
	}

	public static void stockOutOrder(){
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
	}

	public static void clearScreen() {
   		System.out.print("\033[H\033[2J");
  	 	System.out.flush();
	}
}


