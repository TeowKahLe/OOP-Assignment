import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Item {
	private String itemId;
	private String itemName;
	private String itemCategory;
	private String itemDesc;
	private double unitCost;
	private double unitPrice;
    private Inventory inventory;
    //-----------------------------------------------------------------------------------Constructors
    public Item(){
    }

    public Item(String itemId, String itemName, String itemCategory, String itemDesc, double unitCost, double unitPrice) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemDesc = itemDesc;
        this.unitCost = unitCost;
        this.unitPrice = unitPrice;
    }

    public Item(String itemId, String itemName, String itemCategory, String itemDesc, double unitCost, double unitPrice, Inventory inventory) {
    	this.itemId = itemId;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemDesc = itemDesc;
        this.unitCost = unitCost;
        this.unitPrice = unitPrice;
        this.inventory = inventory;
    }
    
    //-----------------------------------------------------------------------------------Getters
    public String getItemId(){
    	return itemId;
    }
    
    public String getItemName(){
    	return itemName;
    }
    
    public String getItemCategory(){
    	return itemCategory;
    }
    
    public String getItemDesc(){
    	return itemDesc;
    }
    
    public double getUnitCost(){
    	return unitCost;
    }
    
    public double getUnitPrice(){
    	return unitPrice;
    }

    public Inventory getInventory() {
        return inventory;
    }
    
    //-----------------------------------------------------------------------------------Setters
    public void setItemId(String itemId){
    	this.itemId = itemId;
    }
    
    public void setItemName(String itemName){
    	this.itemName = itemName;
    }
    
    public void setItemDesc(String itemDesc){
    	this.itemDesc = itemDesc;
    }
    
    public void setItemCategory(String itemCategory){
    	this.itemCategory = itemCategory;
    }
    
    public void setUnitCost(double unitCost){
    	this.unitCost = unitCost;
    }
    
    public void setUnitPrice(double unitPrice){
    	this.unitPrice = unitPrice;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    
    
    

    //-----------------------------------------------------------------------------------Generate item ID
    public String generateItemId(String category) {
    int num = 1;
    String categoryPrefix = "";

    switch(category) {
        case "Meat":
            categoryPrefix = "MT";
            break;
        case "Seafood":
            categoryPrefix = "SD";
            break;
        case "Vegetable":
            categoryPrefix = "VG";
            break;
        case "Dairy":
            categoryPrefix = "DY";
            break;
        case "Condiments":
            categoryPrefix = "CS";
            break;
        case "Beverages":
            categoryPrefix = "BS";
            break;
        case "Grains":
            categoryPrefix = "GS";
            break;
        case "Packaging":
            categoryPrefix = "PG";
            break;
        case "Bread":
            categoryPrefix = "BD";
            break;
        default:
            System.out.println("Invalid category: " + category);
    }

    File file = new File("itemInfo.txt");

    try (Scanner scanner = new Scanner(file)) {
        while (scanner.hasNextLine()) {
            String[] itemFields = scanner.nextLine().split("\\|");
            if (itemFields.length > 0){
                String countPrefix = itemFields[0].substring(0, 2);
                if (countPrefix.equals(categoryPrefix)) {
                    num++;
                }
            }
        }
    } catch (FileNotFoundException e) {
        System.out.println("Cannot locate itemInfo.txt");
    }

    return categoryPrefix + String.format("%03d", num);
}


    
    //-----------------------------------------------------------------------------------Add Item
    public void addItem() {
    Alignment.clearScreen();
    Scanner scanner = new Scanner(System.in);
    Boolean removeScanner = false;
    Alignment line = new Alignment();
    System.out.println("Add Item");
    line.printLine(45);

    // Define categories
    String[] category = {"Meat", "Seafood", "Vegetable", "Dairy", "Condiments", "Beverages", "Grains", "Packaging", "Bread"};
    System.out.printf("| [1]%-10s [2]%-10s [3]%-10s |\n", category[0], category[1], category[2]);
    System.out.printf("| [4]%-10s [5]%-10s [6]%-10s |\n", category[3], category[4], category[5]);
    System.out.printf("| [7]%-10s [8]%-10s [9]%-10s |\n", category[6], category[7], category[8]);
    line.printLine(45);

    String inputItemCategory = "";
    boolean error = true;

    // Handle input for category
    while (error) {
        try {
            System.out.print("Enter Item Category: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // Clear newline character
            if (option >= 1 && option <= 9) {
                inputItemCategory = category[option - 1];
                error = false;
            } else {
                System.out.println("Invalid option. Please select a number between 1 and 9.");
            }
        } catch (Exception e) {
            System.out.println("Incorrect input (Please enter a number only).");
            scanner.nextLine(); // Clear buffer
        }
    }

    // Collect remaining item details
    System.out.print("Enter Item Name: ");
    String inputItemName = scanner.nextLine();

    System.out.print("Enter Item Description: ");
    String inputItemDesc = scanner.nextLine();

    // Read Unit Cost with validation
    double inputUnitCost = 0.0;
    while (true) {
        try {
            System.out.print("Enter Unit Cost: ");
            inputUnitCost = scanner.nextDouble();
            scanner.nextLine(); // Clear newline character
            break; // Exit loop if input is valid
        } catch (Exception e) {
            System.out.println("Incorrect input (Please enter a valid number for Unit Cost).");
            scanner.nextLine(); // Clear buffer
        }
    }

    // Read Unit Price with validation
    double inputUnitPrice = 0.0;
    while (true) {
        try {
            System.out.print("Enter Unit Price: ");
            inputUnitPrice = scanner.nextDouble();
            scanner.nextLine(); // Clear newline character
            break; // Exit loop if input is valid
        } catch (Exception e) {
            System.out.println("Incorrect input (Please enter a valid number for Unit Price).");
            scanner.nextLine(); // Clear buffer
        }
    }

    // Create an Inventory instance
    Inventory inventory = new Inventory(0, 0.0, 0.0, 0, 0); // Pass default values for Inventory attributes

    // Create an Item instance with the category, name, description, cost, and price
    Item newItem = new Item(generateItemId(inputItemCategory), inputItemName, inputItemCategory, inputItemDesc, inputUnitCost, inputUnitPrice);
    
    // Associate the Inventory object with the Item object
    newItem.setInventory(inventory); // Ensure that Item class has a setInventory(Inventory inventory) method

    // Store the details to itemInfo.txt (ensure the storeItemToFile method includes both Item and Inventory details)
    newItem.storeItemToFile(); 
    int opt = 0;
        boolean loop = true;
        while (loop) {
            System.out.println("\nPlease select your action\n1.Back to add item menu\n2.Back to item management menu\n3.Exit");
            try {
                System.out.print("Selected action: ");
                opt = scanner.nextInt();
                scanner.nextLine();
                switch (opt) {
                    case 1:
                        addItem();
                        loop = false;
                        break;
                    case 2:
                        Inventory.inventoryManagement();
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid action selected\n");
                }
            } catch (Exception e) {
                System.out.println("Incorrect input (Please enter NUMBER only)\n");
                scanner.nextLine();
            }
        }
    if(removeScanner){
        scanner.close();
    }
}

    public void deleteItem(){
        Scanner scanner = new Scanner(System.in);
        Alignment Line = new Alignment();
        File inputFile = new File("itemInfo.txt");
        File tempFile = new File("deletedItemId.txt");
        boolean ignoreScanner = true;
        Boolean checkItem = false;
        List<Item> itemList = Order.readItemFromFile("itemInfo.txt");

        String itemIdToDelete = null;
        while(!checkItem){
            try {
                System.out.println("Delete Item\n");
                Line.printLine("Delete Item\n".length());
                System.out.print("\nEnter item id you wish to delete: ");
                String newId = scanner.nextLine();
                for(Item item : itemList){
                if (item.getItemId().equals(newId)) {
                itemIdToDelete = newId;
                checkItem = true;
                break;
                }
            }
            } catch (Exception e) {
                System.out.println("Item ID doesnt exists, please reenter a valid item ID");
            }
            if (!checkItem) {
                System.out.println("Item ID doesnt exists, please reenter a valid item ID");
            }
        }
            if(itemIdToDelete != null){
                for(int i = 0 ; i < itemList.size() ; i++){
                    if(itemList.get(i).getItemId().equals(itemIdToDelete)){
                        itemList.remove(i);
                        break;
                    }
                }
            }
            saveItemToFile(itemList, tempFile.getName());

            if(inputFile.delete()){
                if(tempFile.renameTo(inputFile)){
                    System.out.println("Item ID " + itemIdToDelete + " is being deleted successfully");
                }else{
                    System.out.println("Error renaming temp file");
                }
            }else{
                System.out.println("Item ID " + itemIdToDelete + "does not exists");
            }
            int opt = 0;
        boolean loop = true;
        while (loop) {
            System.out.println("\nPlease select your action\n1.Back to delete item menu\n2.Back to item management menu\n3.Exit");
            try {
                System.out.print("Selected action: ");
                opt = scanner.nextInt();
                scanner.nextLine();
                switch (opt) {
                    case 1:
                        deleteItem();
                        loop = false;
                        break;
                    case 2:
                        Inventory.inventoryManagement();
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid action selected\n");
                }
            } catch (Exception e) {
                System.out.println("Incorrect input (Please enter NUMBER only)\n");
                scanner.nextLine();
            }
        }

            if(ignoreScanner == true){
                scanner.close();
            }
        }
            
    public void saveItemToFile(List<Item> itemList, String filePath){
        FileWriter writer = null;
        
        try {
            writer = new FileWriter(filePath);
            for(Item item : itemList){
                Inventory inventory = item.getInventory();
                writer.write(item.getItemId() + "|" + item.getItemName() + "|" + item.getItemCategory() + "|" + item.getItemDesc() + "|" + item.getUnitCost() + "|" + item.getUnitPrice() + "|" + inventory.getStockQty() + "|" + (inventory.getStockQty()*item.getUnitCost()) + "|" + (inventory.getStockQty()*item.getUnitPrice()) + "|" + inventory.getMinStockQty() + "|" + inventory.getMaxStockQty() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(writer != null){
                try{
                    writer.close();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public void modifyItem(){
        Scanner scanner = new Scanner(System.in);
        boolean ignoreScanner = true;
        Alignment Line = new Alignment();
        boolean checkItem = false;
        Item itemModify = null;
        List<Item> itemList = Order.readItemFromFile("itemInfo.txt");

        for(Item item: itemList){
            System.out.print(item.getItemId() + "" + "|");
        }

        while(!checkItem){
            try {
                System.out.println("Modify Item");
                Line.printLine("Modify item".length());
                System.out.println("\nEnter item id you wish to modify: ");
                String newId = scanner.nextLine();
                for(Item item : itemList){
                if (item.getItemId().equals(newId)) {
                itemModify = item;
                checkItem = true;
                break;
                }
            }
            } catch (Exception e) {
                System.out.println("Enter the correct Item ID");
            }
            if (!checkItem) {
                System.out.println("Enter the correct Item ID");
            }
        }

        if(itemModify != null){
            System.out.println("Modify item detail");
            Line.printLine("Modify item detail".length());
	        System.out.println("1. Stock Quantity\n2. Minimum Stock Quantity\n3. Maximum Stock Quantity\n");
            System.out.println("Selected action: ");
            int opt = scanner.nextInt();

            switch(opt){
                case 1:
                System.out.println("Enter new Stock Quantity\n");
                int newStkQty = scanner.nextInt();
                scanner.nextLine();
                itemModify.inventory.setStockQty(newStkQty);
                break;

                case 2:
                System.out.println("Enter new Minimum Stock Quantity\n");
                int newMinStkQty = scanner.nextInt();
                scanner.nextLine();
                itemModify.inventory.setMinStockQty(newMinStkQty);
                break;

                case 3:
                System.out.println("Enter new Maximum Stock Quantity\n");
                int newMaxStkQty = scanner.nextInt();
                scanner.nextLine();
                itemModify.inventory.setMaxStockQty(newMaxStkQty);
                break;

                default:
                System.out.println("Invalid option");
            }
            saveItemToFile(itemList, "itemInfo.txt");
        }else{
            System.out.println("Item ID" + itemId + "does not exists");
        }

        int opt = 0;
        boolean loop = true;
        while (loop) {
            System.out.println("\nPlease select your action\n1.Back to item modify menu\n2.Back to item management menu\n3.Exit");
            try {
                System.out.print("Selected action: ");
                opt = scanner.nextInt();
                scanner.nextLine();
                switch (opt) {
                    case 1:
                        modifyItem();
                        loop = false;
                        break;
                    case 2:
                        Inventory.inventoryManagement();
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid action selected\n");
                }
            } catch (Exception e) {
                System.out.println("Incorrect input (Please enter NUMBER only)\n");
                scanner.nextLine();
            }
        }

        if(ignoreScanner == true){
            scanner.close();
        }
 	
    }

    public boolean searchItem(String itemId) {
        Scanner scanner = null;
        Alignment line = new Alignment();
        boolean itemFound = false;
        try {
            scanner = new Scanner(new File("itemInfo.txt"));
            while (scanner.hasNextLine()) {
                String[] itemFields = scanner.nextLine().split("\\|");
                if (itemFields[0].equals(itemId)) {
                    System.out.printf("%-7s | %-15s | %-15s | %-15s\n", "Item ID", "Item Name", "Category", "Description");
                    System.out.printf("%-7s | %-15s | %-15s | %-15s\n", itemFields[0], itemFields[1], itemFields[2], itemFields[3]);
                    line.printLine(93);
                    System.out.printf("%-9s | %-10s | %-9s | %-10s | %-11s | %-13s | %-13s\n", "Unit Cost", "Unit Price", "Stock Qty", "Stock Cost", "Stock Value", "Min Stock Qty", "Max Stock Qty");
                    double itemField4 = Double.parseDouble(itemFields[4]);
                    double itemField5 = Double.parseDouble(itemFields[5]);
                    double itemField7 = Double.parseDouble(itemFields[7]);
                    double itemField8 = Double.parseDouble(itemFields[8]);
                    System.out.printf("RM%-7.2f | RM%-8.2f | %-9s | RM%-8.2f | RM%-9.2f | %-13s | %-13s\n\n", itemField4, itemField5, itemFields[6], itemField7, itemField8, itemFields[9], itemFields[10]);
                    itemFound = true;
                    break;  // Exit loop once item is found
                }
            }
        } catch (FileNotFoundException e) {
        System.out.println("Cannot locate itemInfo.txt");
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
        if (!itemFound) {
            System.out.println("Item ID: " + itemId + " does not exist.");
        }
        return itemFound;  // Return true if found, false if not
    }

    public static void searchItem() {
    	Scanner scanner = new Scanner(System.in);
    	Alignment line = new Alignment();
    	boolean found;
    	Item item = new Item(); 
        Boolean ignoreScanner = true;
        System.out.println("Search Item");
        line.printLine("Search Item".length());

        // Display all item IDs with names first
        
        System.out.println("ID    | NAME");
        line.printLine("ID    | NAME".length());
        List<Item> itemList = Order.readItemFromFile("itemInfo.txt");  // Assuming this method loads items from file
        for (Item i : itemList) {
            System.out.println(i.getItemId() + " | " + i.getItemName());
        }
        line.printLine("ID    | NAME".length());

    	do { 
        	System.out.print("Enter Item ID: ");
        	String itemId = scanner.nextLine();
            System.out.println("");
        	found = item.searchItem(itemId);  // This should return true if item is found
            
        	if (found) {
            	System.out.println("Item found");
        	} else {
            	System.out.println("Item not found, try again.");
        	}
    	} while (!found);  // Loop until item is found
    
        int opt = 0;
        boolean loop = true;
        while (loop) {
            System.out.println("Search again or back to itemManagement or Exit?(1 = Search, 2 = itemManagement, 3 = Exit)");
            try {
                System.out.print("Selected action: ");
                opt = scanner.nextInt();
                scanner.nextLine();
                switch (opt) {
                    case 1:
                        Item.searchItem();
                        loop = false;
                        break;
                    case 2:
                        Inventory.inventoryManagement();
                        break;
                    case 3:
                        System.exit(0);
                    default:
                        System.out.println("Invalid action selected\n");
                }
            } catch (Exception e) {
                System.out.println("Incorrect input (Please enter NUMBER only)\n");
                scanner.nextLine();
            }
        }   
        
        if(!ignoreScanner){
            scanner.close();
        }
	}

    //-----------------------------------------------------------------------------------Display All Item
    public static void displayAllItem(){
        Alignment.clearScreen();
        Boolean ignoreScanner = true;
        Scanner scanner = null;
        Alignment line = new Alignment();
        int num = 0;
        try {
            scanner = new Scanner(new File("itemInfo.txt"));
            while(scanner.hasNextLine()){ //hasNextLine: if scan dao \n, will quit the looping 
                    String[] itemFields = scanner.nextLine().split("\\|");
                    num++;
                    System.out.println("Item " + num);
                    line.printEqualLine(6);
                    System.out.printf( "%-7s | %-15s | %-15s | %-15s\n", "Item ID", "Item Name", "Category", "Description");
                    System.out.printf("%-7s | %-15s | %-15s | %-15s\n", itemFields[0], itemFields[1], itemFields[2], itemFields[3]);
                    line.printLine(93);
                    System.out.printf("%-9s | %-10s | %-9s | %-10s | %-11s | %-13s | %-13s\n", "Unit Cost", "Unit Price", "Stock Qty", "Stock Cost", "Stock Value", "Min Stock Qty", "Max Stock Qty");
                    double itemField4 = Double.parseDouble(itemFields[4]); //convert string to double
                    double itemField5 = Double.parseDouble(itemFields[5]);
                    double itemField7 = Double.parseDouble(itemFields[7]);
                    double itemField8 = Double.parseDouble(itemFields[8]);
                    System.out.printf("RM%-7.2f | RM%-8.2f | %-9s | RM%-8.2f | RM%-9.2f | %-13s | %-13s\n\n", itemField4, itemField5, itemFields[6], itemField7, itemField8, itemFields[9], itemFields[10]);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Cannot locate item.txt");
        } 

        scanner = new Scanner(System.in);
        int opt = 0;
        boolean loop = true;
        while (loop) {
            System.out.println("\nPlease select your action\n1.Back to item management menu\n2.Exit\n");
            try {
                System.out.print("Selected action: ");
                opt = scanner.nextInt();
                scanner.nextLine();
                switch (opt) {
                    case 1:
                        Inventory.inventoryManagement();
                        loop = false;
                        break;
                    case 2:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid action selected\n");
                }
            } catch (Exception e) {
                System.out.println("Incorrect input (Please enter NUMBER only)\n");
                scanner.nextLine();
            }

            if(!ignoreScanner){
                scanner.close();
            }
        }
    }


// Method to store item details to file, including Inventory-specific details
    public void storeItemToFile() {
    try (FileWriter itemWriter = new FileWriter("itemInfo.txt", true)) {
        // Ensure `getInventory()` returns an `Inventory` object properly
        Inventory inv = getInventory();

        itemWriter.write(getItemId() + "|" +
                         getItemName() + "|" +
                         getItemCategory() + "|" +
                         getItemDesc() + "|" +
                         String.format("%.2f", getUnitCost()) + "|" +
                         String.format("%.2f", getUnitPrice()) + "|" +
                         String.format("%d", inv.getStockQty()) + "|" +
                         String.format("%.2f", inv.getStockQty()*getUnitCost()) + "|" +
                         String.format("%.2f", inv.getStockQty()*getUnitPrice()) + "|" +
                         String.format("%d", inv.getMinStockQty()) + "|" +
                         String.format("%d", inv.getMaxStockQty()) + "\n");
        System.out.println("Item stored successfully!");
    } catch (IOException e) {
        System.out.println("Error: Cannot store into itemInfo.txt.");
        e.printStackTrace();
    }
}

    public String toString(){
        return itemId + "\t" + itemName + "\t" +  itemCategory + "\t" + itemDesc + "\t" + unitCost + "\t" + unitPrice + "\n";
    }

/*    public String toString(){
        return itemId + "\t" + String.format("%-20s",itemName) + "\t" + String.format("%-10s", itemCategory) + "\t" + String.format("%-20s", itemDesc) + "\tRM" + String.format("%5.2f", unitCost) + "\t\tRM" + String.format("%5.2f", unitPrice) + "\n";
    } */
    //-----------------------------------------------------------------------------------Cls
}