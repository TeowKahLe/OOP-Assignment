import java.io.File;
import java.io.FileNotFoundException;
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
						menu();
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
		scanner.close();
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
		System.out.println("6. Return to menu");
		System.out.println("7. Exit");

		while(error){
			try{
				System.out.print("Selected action: ");
    			int opt = scanner.nextInt();
				switch(opt){
    				case 1:
						clearScreen();
						Item addItem = new Item();
						addItem.addItem();
						try {
							System.out.println("Add again or back to itemManagement or Exit?(1 = Add, 2 = itemManagement, 3 = Exit)");
							opt = scanner.nextInt();
							switch(opt){
								case 1: 
									addItem.addItem();
									break;
								case 2:
									itemManagement();
									break;
								case 3:
									System.exit(0);
									break;
								default:
									System.out.println("Invalid option");
							}
						} catch (Exception e) {
							System.out.println("Incorrect input(Please enter NUMBER only)");
    			            scanner.nextLine();
						}
    					break;
    				case 2:
						clearScreen();
						//modify
    					break;
					case 3:
						clearScreen();
						//delete
    					break;
					case 4:
						searchItem();
						try {
							System.out.println("Search again or back to itemManagement or Exit?(1 = Search, 2 = itemManagement, 3 = Exit)");
							opt = scanner.nextInt();
							switch(opt){
								case 1: 
									searchItem();
									break;
								case 2:
									itemManagement();
									break;
								case 3:
									System.exit(0);
									break;
								default:
									System.out.println("Invalid option");
							}
						} catch (Exception e) {
							System.out.println("Incorrect input(Please enter NUMBER only)");
    			            scanner.nextLine();
						}
    					break;
					case 5:
						clearScreen();
						Item displayItem = new Item();
						displayItem.displayAllItem();
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
		scanner.close();
	}

	public static void searchItem() {
		clearScreen();
		Scanner scanner = new Scanner(System.in);
		Line line = new Line();
	  	System.out.println("Search Item");
		line.printLine("Search Item".length());
		Item searchItem = new Item();
		System.out.print("Enter Item ID: ");
		String itemId = scanner.nextLine();
		searchItem.searchItem(itemId);
		scanner.close();
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
		scanner.close();
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

	public static void customerInputOrder(){
		clearScreen();
		Line line = new Line();
		Scanner scanner = null;
		int num = 0;
		try {
            scanner = new Scanner(new File("itemInfo.txt"));

            while (scanner.hasNextLine()) {
                String[] itemFields = scanner.nextLine().split("\\|");
				num++;
				line.printLine(50);
				System.out.println("[Item " + num + "]");
                System.out.println("Food Name   : " + itemFields[1]);
				System.out.println("Category    : " + itemFields[2]);
                System.out.println("Description : " + itemFields[3]);
                System.out.println("Unit Price  : " + itemFields[5]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot locate item.txt");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

		
	}

	public static void clearScreen() {
   		System.out.print("\033[H\033[2J");
  	 	System.out.flush();
	}
}


