import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Order{
    private String orderId;
    private List<Item> itemList;
    private int itemQty;
    private String approvalStatus;
    private String orderDate;
    private String orderTime;
    private String deliveryMethod;
    private String orderType;
    private String staffId;

    //-----------------------------------------------------------------------------------Constructors
    public Order(){
        this.itemList = new ArrayList<>();
    }

    public Order(String orderId, List<Item> itemList, int itemQty, String approvalStatus, 
                 String orderDate, String orderTime, String deliveryMethod, 
                 String orderType, String staffId){
        this.orderId = orderId;
        this.itemList = itemList;
        this.itemQty = itemQty;
        this.approvalStatus = approvalStatus;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.deliveryMethod = deliveryMethod;
        this.orderType = orderType;
        this.staffId = staffId;
    }

    //--------------------------------------------------------------------------------------Getters
    public String getOrderId() {
        return orderId;
    }

     public List<Item> getItemList() {
        return itemList;
    }

    public int getItemQty() {
        return itemQty;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getStaffId() {
        return staffId;
    }

    //-----------------------------------------------------------------------------------Setters
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public void generateOrderId(String prefix){

    }

    //-----------------------------------------------------------------------------------Order Management
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
    			int option = scanner.nextInt();
                scanner.nextLine();
				switch(option){
    				case 1:
						//Display all order
						Order.orderManagement();
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
						StockOutOrder.stockOutOrderMenu();
    					break;
					case 6:
						error = false;
						//menu();
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
    //-----------------------------------------------------------------------------------read item from file then update ItemList
    public void readItemFromFile(String filePath) {
        List<Item> items = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextLine()) {
                String[] itemFields = scanner.nextLine().split("\\|");
                if (itemFields.length >= 11) {
                    Inventory inventory = new Inventory(
                        itemFields[0], // itemId
                        itemFields[1], // itemName
                        itemFields[2], // itemCategory
                        itemFields[3], // itemDesc
                        Double.parseDouble(itemFields[4]), // unitCost
                        Double.parseDouble(itemFields[5]), // unitPrice
                        Integer.parseInt(itemFields[6]), // stockQty
                        Double.parseDouble(itemFields[7]), // stockCost
                        Double.parseDouble(itemFields[8]), // stockValue
                        Integer.parseInt(itemFields[9]), // minStockQty
                        Integer.parseInt(itemFields[10]) // maxStockQty
                    );

                    // Cast Inventory to Item and add to itemList
                    items.add((Item) inventory);
                } else {
                    System.out.println("Invalid data format in file.");
                }
            }
            setItemList(items);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot locate file: " + filePath);
        }
    }   

    //-----------------------------------------------------------------------------------Cls
    public static void clearScreen() {
   		System.out.print("\033[H\033[2J");
  	 	System.out.flush();
	}
}




