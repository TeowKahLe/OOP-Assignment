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
        newItem.setStockValue(inputStockQty * inputUnitPrice);
        newItem.setMinStockQty(inputMinStockQty);
        newItem.setMaxStockQty(inputMaxStockQty); 
        newItem.storeItemToFile();
    }

    public void deleteItem(String itemId){

    }

    public void modifyItem(String itemId, String itemName, double unitPrice, String itemDesc){

    }

    public String displayItem(String itemId){
        return "";
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