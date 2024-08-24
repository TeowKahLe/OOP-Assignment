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
    	System.out.println("1)Sign up\n2)Login");
    	
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
						signup();
    					break;
    				case 2:
						login();
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

	public static void signup(){
		//
	}

	public static void login(){
		menu();
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
		System.out.println("3. Transaction Management");
   	 	System.out.println("4. Personal Information Management");
   		System.out.println("5. Return to Main Menu");

		while(error){
			try{
				System.out.print("Selected action: ");
    			int opt = scanner.nextInt();
				switch(opt){
    				case 1:
						itemManagement();
    					break;
    				case 2:
						//orderManagement()
    					break;
					case 3:
						//transactionManagement()
    					break;
					case 4:
						//personInfoManagement()
    					break;
					case 5:
						error = false;
						main(new String[]{});
						break;
    				default:
    					System.out.println("Invalid action selected");
    					break;	
    			}
			}catch (Exception e){
    			System.out.println("Incorrect input(Please entry NUMBER only)");
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
		System.out.println("4. Display item");
		System.out.println("5. Return to menu");

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
						//display
    					break;
					case 5:
						error = false;
						menu();
						break;
    				default:
    					System.out.println("Invalid action selected");
    					break;	
    			}
			}catch (Exception e){
    			System.out.println("Incorrect input(Please entry NUMBER only)");
    			scanner.nextLine();
    		}	
		}
	}

	public static void clearScreen() {
   		System.out.print("\033[H\033[2J");
  	 	System.out.flush();
	}
}


