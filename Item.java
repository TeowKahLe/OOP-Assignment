/**
 * @(#)Order.java
 *
 *
 * @author 
 * @version 1.00 2024/8/17
 */


public class Item {
	private String itemId;
	private String itemName;
	private String itemDesc;
	private double unitPrice;
	private int stockQty;
	private int stockValue;
	private int minStockQty;
	private int maxStockQty;
	
    public Item() {
    }
    
    //Getter
    public String getItemId(){
    	return itemId;
    }
    
    public String getItemName(){
    	return itemName;
    }
    
    public String getItemDesc(){
    	return itemDesc;
    }
    
    public double getUnitPrice(){
    	return unitPrice;
    }
    
    public int getStockQty(){
    	return stockQty;
    }
    
    public int getStockValue(){
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
    
    public void setUnitPrice(double unitPrice){
    	this.unitPrice = 
    }
}