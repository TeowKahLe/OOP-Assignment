import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Item {
    private static ArrayList<Item> items = new ArrayList<>();
	private String itemId;
	private String itemName;
	private String itemCategory;
	private String itemDesc;
	private double unitCost;
	private double unitPrice;
	private int stockQty;
	private double stockValue;
	private int minStockQty;
	private int maxStockQty;
	
    //No-arg constructor
	public Item(){
	}
	
    public Item(String itemId, String itemName, String itemCategory, String itemDesc, double unitCost, double unitPrice, int stockQty, int minStockQty, int maxStockQty) {
    	this.itemId = itemId;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemDesc = itemDesc;
        this.unitCost = unitCost;
        this.unitPrice = unitPrice;
        this.stockQty = stockQty;
        this.stockValue = unitCost * stockQty;
        this.minStockQty = minStockQty;
        this.maxStockQty = maxStockQty;
    }
    
    //Getter
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
    
    public double getStockValue(){
    	return stockValue;
    }
    
    public int getMinStockQty(){
    	return minStockQty;
    }
    
    public int getMaxStockQty(){
    	return maxStockQty;
    }
    
    //Setter
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
    
    public void setStockValue(int stockValue){
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
        String itemId = scanner.nextLine();

        System.out.print("Enter Item Name: ");
        String itemName = scanner.nextLine();

        System.out.print("Enter Item Category: ");
        String itemCategory = scanner.nextLine();

        System.out.print("Enter Item Description: ");
        String itemDesc = scanner.nextLine();

        System.out.print("Enter Unit Cost: ");
        double unitCost = scanner.nextDouble();

        System.out.print("Enter Unit Price: ");
        double unitPrice = scanner.nextDouble();

        System.out.print("Enter Stock Quantity: ");
        int stockQty = scanner.nextInt();

        System.out.print("Enter Minimum Stock Quantity: ");
        int minStockQty = scanner.nextInt();

        System.out.print("Enter Maximum Stock Quantity: ");
        int maxStockQty = scanner.nextInt();

        Item newItem = new Item(itemId, itemName, itemCategory, itemDesc, unitCost, unitPrice, stockQty, minStockQty, maxStockQty);
        items.add(newItem);
        storeItemToFile(newItem);
    }

    public void deleteItem(String itemId){

    }

    public void modifyItem(String itemId, String itemName, double unitPrice, String itemDesc){

    }

    public String displayItem(){
        return "Item ID: " + itemId + ", Name: " + itemName + ", Category: " + itemCategory +
               ", Description: " + itemDesc + ", Unit Cost: " + unitCost + ", Unit Price: " + unitPrice +
               ", Stock Quantity: " + stockQty + ", Stock Value: " + stockValue +
               ", Min Stock Quantity: " + minStockQty + ", Max Stock Quantity: " + maxStockQty;
    }

    //Store Item To File---------------------------------------------------------------------------------
    public void storeItemToFile(Item item) {
    try (FileWriter itemWriter = new FileWriter("itemInfo.txt", true)) {
        itemWriter.write(item.toString() + "\n");
    } catch (IOException e) {
        System.out.println("Cannot store into the itemInfo.txt.");
        e.printStackTrace(); // Print the stack trace for debugging
    }

    System.out.println("Item added successfully!");
}
}