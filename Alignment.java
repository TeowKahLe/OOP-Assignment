public class Alignment {
	int printQty;
	
    Alignment() {
    	this.printQty = 0;
    }
    
    Alignment(int printQty) {
    	this.printQty = printQty;
    }
    
    public void printLine(int printQty){
    	for(int qty = 0; qty < printQty; qty++){
    		System.out.print("-");
    	}
    	System.out.println();
    }

	public void printLineNoNewLine(int printQty){
    	for(int qty = 0; qty < printQty; qty++){
    		System.out.print("-");
    	}
    }

	public void printEqualLine(int printQty){
    	for(int qty = 0; qty < printQty; qty++){
    		System.out.print("=");
    	}
    	System.out.println();
    }
    
}
