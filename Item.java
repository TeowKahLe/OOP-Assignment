public class Item {
	private String itemId;
	private String itemName;
	private String itemCategory;
	private String itemDesc;
	private double unitCost;
	private double unitPrice;
	private int stockQty;
	private int stockValue;
	private int minStockQty;
	private int maxStockQty;
	
	public Item(){
		
	}
	
    public Item(String itemId, String itemName, String itemCategory, String itemDesc, double unitCost, double unitPrice, int stockQty, int stockValue, int minStockQty, int maxStockQty) {
    	
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
    
    public void addItem(String itemId, String itemName, String itemDesc, double unitPrice, int stockQty, int minStockQty, int maxStockQty){
    	this.itemId = itemId;
    	this.itemName = itemName;
    	this.itemDesc = itemDesc;
    	this.unitPrice = unitPrice;
    	this.stockQty = stockQty;
    	this.minStockQty = minStockQty;
    	this.maxStockQty = maxStockQty;
    }

    public void deleteItem(String itemId){

    }

    public void modifyItem(String itemId, String itemName, double unitPrice, String itemDesc){

    }

    public String displayItem(String itemId){
        return ""; //will modify
    }
}