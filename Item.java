import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Item {
	private String itemId;
	private String itemName;
	private String itemCategory;
	private String itemDesc;
	private double unitCost;
	private double unitPrice;
	private int stockQty;
    private double stockCost;
	private double stockValue;
	private int minStockQty;
	private int maxStockQty;
	
    //No-arg constructor
	public Item(){
	}
	
    public Item(String itemId, String itemName, String itemCategory, String itemDesc, double unitCost, double unitPrice, int stockQty, double stockValue, int minStockQty, int maxStockQty) {
    	this.itemId = itemId;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemDesc = itemDesc;
        this.unitCost = unitCost;
        this.unitPrice = unitPrice;
        this.stockQty = stockQty;
        this.stockValue = stockValue;
        this.minStockQty = minStockQty;
        this.maxStockQty = maxStockQty;
    }
    
    //Getter----------------------------------------------
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
    
    //Setter--------------------------------------------------------------
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
    
    //Add Item-------------------------------------------------------------------------------------
    public void addItem() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Item ID: ");
        String inputItemId = scanner.nextLine();

        System.out.print("Enter Item Name: ");
        String inputItemName = scanner.nextLine();

        System.out.print("Enter Item Category: ");
        String inputItemCategory = scanner.nextLine();

        System.out.print("Enter Item Description: ");
        String inputItemDesc = scanner.nextLine();

        System.out.print("Enter Unit Cost: ");
        double inputUnitCost = scanner.nextDouble();

        System.out.print("Enter Unit Price: ");
        double inputUnitPrice = scanner.nextDouble();

        System.out.print("Enter Stock Quantity: ");
        int inputStockQty = scanner.nextInt();

        System.out.print("Enter Minimum Stock Quantity: ");
        int inputMinStockQty = scanner.nextInt();

        System.out.print("Enter Maximum Stock Quantity: ");
        int inputMaxStockQty = scanner.nextInt();

        Item newItem = new Item();
        newItem.setItemId(inputItemId);
        newItem.setItemName(inputItemName);
        newItem.setItemCategory(inputItemCategory);
        newItem.setItemDesc(inputItemDesc);
        newItem.setUnitCost(inputUnitCost);
        newItem.setUnitPrice(inputUnitPrice);
        newItem.setStockQty(inputStockQty);
        newItem.setStockCost(inputStockQty * inputUnitCost);
        newItem.setStockValue(inputStockQty * inputUnitPrice);
        newItem.setMinStockQty(inputMinStockQty);
        newItem.setMaxStockQty(inputMaxStockQty); 
        newItem.storeItemToFile();
    }

    public void deleteItem(String itemId){

    }

    public void modifyItem(String itemId, String itemName, double unitPrice, String itemDesc){

    }

    public boolean searchItem(String itemId) {
        Scanner scanner = null;
        Line line = new Line();
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

    public void displayAllItem(){
        Scanner scanner = null;
        Line line = new Line();
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
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    //Store Item To File---------------------------------------------------------------------------------
    public void storeItemToFile() {
    try (FileWriter itemWriter = new FileWriter("itemInfo.txt", true)) {
        itemWriter.write(getItemId() + "|" +
                         getItemName() + "|" +
                         getItemCategory() + "|" +
                         getItemDesc() + "|" +
                         getUnitCost() + "|" +
                         getUnitPrice() + "|" +
                         getStockQty() + "|" +
                         getStockCost() + "|" +
                         getStockValue() + "|" +
                         getMinStockQty() + "|" +
                         getMaxStockQty() + "\n");
    }catch (IOException e) {
        System.out.println("Cannot store into the itemInfo.txt.");
            e.printStackTrace();
        }
        System.out.println("Item added successfully!");
    }
}