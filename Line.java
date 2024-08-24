public class Line {
	int printQty;
	
    Line() {
    	this.printQty = 0;
    }
    
    Line(int printQty) {
    	this.printQty = printQty;
    }
    
    public void printLine(int printQty){
    	for(int qty = 0; qty < printQty; qty++){
    		System.out.print("-");
    	}
    	System.out.println();
    }

	public void printEqualLine(int printQty){
    	for(int qty = 0; qty < printQty; qty++){
    		System.out.print("=");
    	}
    	System.out.println();
    }
    
}
