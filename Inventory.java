
public class Inventory extends Item {
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

    public Inventory(String itemId, String itemName, String itemCategory, String itemDesc, 
                     double unitCost, double unitPrice, int stockQty, 
                     double stockCost, double stockValue, int minStockQty, 
                     int maxStockQty) {
        super(itemId, itemName, itemCategory, itemDesc, unitCost, unitPrice);
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
}