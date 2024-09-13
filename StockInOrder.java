import java.util.Date;
//import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class StockInOrder{
    private List<Supplier> supplierInfo = new ArrayList<>();
    private List<Order> orderInfo = new ArrayList<>();
    private Date dateReceived;

    Alignment line = new Alignment();
    Item      item = new Item();
    Order    order = new Order();

    Scanner scanner = new Scanner(System.in);

//getter & setter --------------------------------------------------------------
    public List<Supplier> getSupplierInfo(){
        return supplierInfo;
    }  

    public List<Order> getOrderInfo(){
        return orderInfo;
    }  

    public Date getDateReceived(){
        return dateReceived;
    }

    
    public void setSupplierInfo(List<Supplier> supplierInfo){
        this.supplierInfo = supplierInfo;
    }  

    public void setOrderInfo(List<Order> orderInfo){
        this.orderInfo = orderInfo;
    }  

    public void setDateReceived(Date dateReceived){
        this.dateReceived = dateReceived;
    }

//menu-------------------------------------------------------------------------------------
public void StockInOrderMenu(){
    int opt = 0;
    boolean isDigit = false;

    System.out.println("Stock In Order");
    line.printLine("Stock In Order".length());
    System.out.println("1.Add Order");
    System.out.println("2.Modify Order");
    System.out.println("3.Cancel Order");
    System.out.println("4.Back to staff menu");

    while(opt != 4 || !isDigit){        
        try{
            System.out.print("Enter your action >> ");
            opt = scanner.nextInt();
            scanner.nextLine();
            isDigit = true;
            if(!(opt>=1 && opt <=4)){
                System.out.println("The action does not found.\nPlease refer to above list.\n");
            }

           switch(opt){
               case 1:
                   //add
                   break;
               case 2:
                   //cancel
                   break;
               case 3:
                   //modify
                   break;
               case 4:
                   fastFoodInventory.menu();
                   break;
           }

        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Action only allow integers.\n");
        }
    }
}

//Add purchase order
public void addPurchase(){    
    Alignment.clearScreen();
    String selectedItemName = "";
    String selectedItemID = "";
    int qty = 0;
    String selectedSupplID = "";
    String nextItemAction = "";

    String[] itemOrderDetail = new String[10];
    int itemCount=0;

    String[] filteredSupplID = new String[10];
    boolean found = false,isDigit=false;

    do{
        System.out.println("Purchase item " + (itemCount+1));
        line.printLine("Purchase item ".length());
        sortDisplayItemArr();
        
        //search from itemList if got proceed else retype
        while(!found){
            System.out.print(String.format("%-30s","Item(enter item name)") + ": ");
            selectedItemName = scanner.nextLine();
            for (Item item : order.getItemList()) {
                if(item.getItemName().equalsIgnoreCase(selectedItemName)){
                    selectedItemName = selectedItemName.substring(0,1).toUpperCase() + selectedItemName.substring(1).toLowerCase();
                    selectedItemID = item.getItemId();
                    found = true;
                }
            }

            if(!found){
                System.out.println("Sorry, we don't have this item.\nPlease refer above list.Thank you.\n");
            }
        }

        //enter qty
        while(!isDigit){
            try {
                System.out.print(String.format("%-30s","Quantity") + ": ");
                qty = scanner.nextInt();
                scanner.nextLine();
                isDigit =true;    
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Quantity only allow integers.\n");
            }
        }
        //proceed to supplier file to find the respective supplier
        filterSupplier(selectedItemName,filteredSupplID);

        found = false;
        while(!found){
            System.out.print(String.format("%-30s","Supplier(enter supplier ID) ") + ": ");
            selectedSupplID = scanner.nextLine();
            for (String supplID : filteredSupplID) {
                if(selectedSupplID.equals(supplID)){
                    found = true;
                }
            }
            if(!found){
                System.out.println("Sorry,your supplier ID does not found in " + selectedItemName + " Supplier List.\nPlease refer above list.\n");
            }
        }
    
        do{
            System.out.print(String.format("%-30s","Next Item?(Y->Next item N->Display order)(Max 10 items) ") + ": ");// ask for next item else display order
            nextItemAction = scanner.nextLine();
            if(nextItemAction.equals("Y")){
                itemOrderDetail[itemCount] = selectedItemID + "\t" + qty + "\t" + selectedSupplID;
                itemCount++;
            }else if(nextItemAction.equals("N")){
                itemOrderDetail[itemCount] = selectedItemID + "\t" + qty + "\t" + selectedSupplID;
            }else{
                System.out.println("Please select valid action (Y or N)");
            }
        }while(!(nextItemAction.equals("Y")) && !(nextItemAction.equals("N")));

        if(itemCount > 10){
            System.out.println("Your order item limit is reached\n");
        }
    
    //if selected item cannot choose multiple time
    //open a variable calculate itemCount then store all orderItem detail in an array so it can store and display to staff the order

    }while(nextItemAction.equals("Y") && itemCount <= 10);
    
}


public void sortDisplayItemArr(){
    String[] categoryCode = new String[10];
    int storedCodeIndex=0;

    order.storeItemtoArr();
    line.printLine(100);
    System.out.print("ID" + "\t" + String.format("%-20s","Item Name") + "\t" + String.format("%-10s","Category") + "\t" + String.format("%-20s", "Description") + "\t" + String.format("%-7s", "Unit Cost") + "\t" + String.format("%-7s", "Unit Price") + "\n");
    line.printLine(100);
    //Store the category code from itemList and ensure not same code store in
    for(int arrIndex = 0; arrIndex<order.getItemList().size();arrIndex++){
        String tempID = order.getItemList().get(arrIndex).getItemId();
        String tempcategoryCode = tempID.substring(0,2);
        if(storeCategoryCode(categoryCode,tempcategoryCode,storedCodeIndex)){
            storedCodeIndex++;
        }
    }

    //then loop the categoryCode the same category display first then continue next category
       for(int index=0;index<categoryCode.length;index++){
            if(categoryCode[index] != null){
                for (Item item : order.getItemList()) {
                    if((item.getItemId().startsWith(categoryCode[index]))){
                        System.out.println(item.getItemId() + "\t" + String.format("%-20s",item.getItemName()) + "\t" + String.format("%-10s", item.getItemCategory()) + "\t" + String.format("%-20s", item.getItemDesc()) + "\tRM" + String.format("%5.2f", item.getUnitCost()) + "\t\tRM" + String.format("%5.2f", item.getUnitPrice()) + "\n");
                    }
                }
            }else{
                break;
            }      
        }       
}

public boolean storeCategoryCode(String[] categoryCode,String tempCategoryCode,int storedCodeIndex){
    if(categoryCode[0] == null){
        categoryCode[0] = tempCategoryCode;
        return true;
    }
    for (String storeCode : categoryCode) {
        if(tempCategoryCode.equals(storeCode)){
            return false;
        }
    }

    categoryCode[storedCodeIndex] = tempCategoryCode;        
    return true;
}


public void storeSuppliertoArr(){
    String supplierFilePath = "supplierInfo.txt";
    String[] supplier = null;
    List<Item> tempSuppItem = new ArrayList<>();

    order.storeItemtoArr();

    try (Scanner scanner = new Scanner(new File(supplierFilePath))){
        while(scanner.hasNextLine()){
            String lineContent = scanner.nextLine();
            if(lineContent !=null){
                String[] tokenContents = lineContent.split("\\t");

                if(lineContent.startsWith("U")){
                    if(supplier != null){
                        //supplier data store in arrayList
                        //create new ArrayList of SuppItem bcz it will point to same reference at tempSuppItem and the value will change even store in supplier ArrayList
                        //copy tempSuppItem arrayList to another List which is suppList
                        List<Item> suppList = new ArrayList<>(tempSuppItem);
                        supplierInfo.add(new Supplier(supplier[0],supplier[1],supplier[2],supplier[3],supplier[4],suppList));
                        //clear the tempData for next Supplier info in
                        supplier = null;
                        tempSuppItem.clear();
                    }
                    supplier = tokenContents;
                }else{
                    for (Item itemList : order.getItemList()) {
                        //tokenContents[0] = "Item[1]: ID" so i need substring to take ID

                        if(tokenContents[0].substring(9).equals(itemList.getItemId())){
                            tempSuppItem.add(itemList);
                            break;
                        }
                    }
                }
            }
        }
        //Bcz last list not stored due to the next content is null so it unable to perform the storeArr function in loop so need to explicit add to store last element
        if(supplier != null){
            List<Item> suppList = new ArrayList<>(tempSuppItem);
            supplierInfo.add(new Supplier(supplier[0],supplier[1],supplier[2],supplier[3],supplier[4],suppList));
        }

    } catch (IOException e) {
        System.out.println(supplierFilePath + "unable to open");
    }
}

public void filterSupplier(String selectedItemName,String[] filteredSupplID){
    int indexFilteredSupplID = 0;

    storeSuppliertoArr();

    System.out.println("\n" + selectedItemName + " Supplier List");
    line.printLine("Supplier List".length());
    System.out.println("ID\tName");
    for (Supplier supplInfo: supplierInfo) {
        List<Item> respecSupplyItems = supplInfo.getSupplyItem(); 
        for (Item item : respecSupplyItems) {
            if(item.getItemName().equals(selectedItemName)){
                filteredSupplID[indexFilteredSupplID] = supplInfo.getId();
                indexFilteredSupplID++;
                System.out.println(supplInfo.getId() + "\t" + supplInfo.getName());
                break;
            }
        }
    }
    System.out.println();
}

}