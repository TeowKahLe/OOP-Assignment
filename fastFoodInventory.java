import java.util.Scanner;
 
public class fastFoodInventory {
    public static void main(String arg[]) {
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
    					//sign up
						Staff test1 = new Staff();
						test1.storeData();
						Staff test2 = new Staff();
						test2.storeData();
    					break;
    				case 2:
						login();
    					break;
    				default:
    					System.out.println("Invalid action selection");
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

	public static void login(){
		// perform login then enter item management method if successful.
		itemManagement();
	}

	public static void itemManagement(){
		Item item1 = new Item();
		int option = 0;
		System.out.println("Please choose one option");
		System.out.println("=========================");
		System.out.println("1. Add item");
		System.out.println("2. Modify item");
		System.out.println("3. Delete item");
		System.out.println("4. Display item");
		System.out.println("5. Return to menu");
		//will do validate
		switch (option) {
			case 1:
				//Add item and will do it 2day
				break;
			case 2:
				//Modify item
				break;
			case 3:
				//Delete item
				break;
			case 4:
				//Display item
				break;
			case 5:
				//Back to menu
				break;
			default:
				System.out.println("Invalid option");
		}
	}
}
