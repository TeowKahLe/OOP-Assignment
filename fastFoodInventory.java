import java.util.Scanner;

public class fastFoodInventory {
    public static void main(String arg[]) {
		clearScreen();
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
						newUser.signUp();
						newUser.storeStaffData();
    					break;
    				case 2:
						//Staff userLogin = new Staff();
						//userLogin.login();
    					//Supplier test = new Supplier();
						//test.modifySupplier();
						menu();
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
    			System.out.println("Incorrect input(Please entry NUMBER only)");
    			scanner.nextLine(); //clear input buffer allow go into try block and prevent infinitely loop
    		}	
	  }
	  scanner.close();
	}

	public static void menu(){
		clearScreen();
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
						Item.itemManagement();
    					break;
    				case 2:
						Order.orderManagement();
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

	public static void clearScreen() {
   		System.out.print("\033[H\033[2J");
  	 	System.out.flush();
	}
}


