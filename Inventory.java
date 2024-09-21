import java.util.Scanner;

public class Inventory{
    private int stockQty;
    private double stockCost;
	private double stockValue;
	private int minStockQty;
	private int maxStockQty;

    //---------------------------------------------------------------------------------------------------------Constructor
    public Inventory(){
        this.stockQty = 0;
        this.stockCost = 0.0;
        this.stockValue = 0.0;
        this.minStockQty = 0;
        this.maxStockQty = 0;
    }
    
    public Inventory(int stockQty, double stockCost, double stockValue, int minStockQty, int maxStockQty){
        this.stockQty = stockQty;
        this.stockCost = stockCost;
        this.stockValue = stockValue;
        this.minStockQty = minStockQty;
        this.maxStockQty = maxStockQty;
    }

    //-----------------------------------------------------------------------------------Getters
    public int getStockQty(){
    	return stockQty;
    }

    public double getStockCost(){
        return stockCost;
    }
    
    public double getStockValue(){
    	return stockValue;
    }
    
    public int getMinStockQty(){
    	return minStockQty;
    }
    
    public int getMaxStockQty(){
    	return maxStockQty;
    }

    //-----------------------------------------------------------------------------------Setters
    public void setStockQty(int stockQty){
    	this.stockQty = stockQty;
    }

    public void setStockCost(double stockCost){
        this.stockCost = stockCost;
    }
    
    public void setStockValue(double stockValue){
    	this.stockValue = stockValue;
    }
    
    public void setMinStockQty(int minStockQty){
    	this.minStockQty = minStockQty;
    }
    
    public void setMaxStockQty(int maxStockQty){
    	this.maxStockQty = maxStockQty;
    }

    //-----------------------------------------------------------------------------------Inventory Management
    public static void inventoryManagement(){
		Alignment.clearScreen();
		Alignment line = new Alignment();
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
				scanner.nextLine();
				switch(opt){
    				case 1:
						Item addItem = new Item();
                        boolean loop = true;
                        while (loop) {
                        addItem.addItem();
                        System.out.println("1.Add more item\n2.Back to Item Management\n3.Exit");
                        try {
                            System.out.print("--> ");
                            opt = scanner.nextInt();
                            scanner.nextLine();
                            switch(opt) {
                                case 1: 
                                    continue;
                                case 2:
                                    Inventory.inventoryManagement();
                                    loop = false;
                                    break;
                                case 3:
                                    System.exit(0);
                                default:
                                    System.out.println("Invalid option");
                            }
                        } catch (Exception e) {
                            System.out.println("Incorrect input (Please enter NUMBER only)");
                            scanner.nextLine(); // Clear the buffer to avoid infinite loop
                        }
                    }
                    break;
    				case 2:
						Alignment.clearScreen();
						Item item = new Item();
						item.modifyItem();
    					break;
					case 3:
						Alignment.clearScreen();
						Item item1 = new Item();
						item1.deleteItem();;
    					break;
					case 4:
						Alignment.clearScreen();
                    	Item.searchItem();
                    	break;
					case 5:
                        Alignment.clearScreen();
						Item.displayAllItem();
						break;
					case 6:
						error = false;
						fastFoodInventory.menu();
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