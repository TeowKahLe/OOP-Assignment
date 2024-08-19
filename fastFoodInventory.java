import java.util.Scanner;
 
public class fastFoodInventory {
   
    public static void main(String[] args) {
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
    					System.out.println("1111");//sign up
    					break;
    				case 2:	
    					System.out.println("2222");//login
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

}
