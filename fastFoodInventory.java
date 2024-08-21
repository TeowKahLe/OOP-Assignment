import java.util.Scanner;
 
public class fastFoodInventory {
   
    public static void main(String[] args) {
    	Line line = new Line();
    	Scanner scanner = new Scanner(System.in);
		Person test1 = new Person("F01","LIn","012-4123121"," ","");
    	Staff test2 = new Staff();
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
						test2.SignUp();
    					break;
    				case 2:
						//login	
						System.out.println(test1.toString());
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
