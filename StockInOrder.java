import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
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
                   String[] itemOrderDetail = new String[10];
                   addPurchase(itemOrderDetail);
                   displayOrderList(itemOrderDetail);

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
public void addPurchase(String[] itemOrderDetail){    
    Alignment.clearScreen();
    String selectedItemName;
    String selectedItemID;
    int qty;
    String selectedSupplID;
    String nextItemAction;

    int itemCount=0;

    String[] filteredSupplID = new String[10];
    boolean found,isDigit,same;

    order.storeItemtoArr(); //move item info from file to Array
    storeSuppliertoArr();  //move supplier info from file to Array
    do{
        selectedItemName = "";
        selectedItemID = "";
        qty = 0;
        selectedSupplID = "";
        nextItemAction = "";
        found = false;
        isDigit=false;
        same = true;

        System.out.println("Purchase item " + (itemCount+1));
        line.printLine("Purchase item ".length());
        sortDisplayItemArr();
        
        //search from itemList if got proceed else retype
        while(!found || same){
            found = false;
            same = false;
            System.out.print(String.format("%-30s","Item(enter item name)") + ": ");
            selectedItemName = scanner.nextLine();
            //Find ID
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
            
            //compare ID have same as ordered item?
            if(itemOrderDetail[0] != null){
                for (String itemInfo : itemOrderDetail) {
                    if(itemInfo !=null){
                        String[] info = itemInfo.split("\\t");
                        if(selectedItemID.equals(info[0])){
                            same = true;
                            break;
                        }
                    }
                }
            }

            if(found && same){
                System.out.println("This item has ordered.Please select different product.");
            }
        }

        //enter qty
        while(!isDigit){
            try {
                System.out.print(String.format("%-30s","Quantity") + ": ");
                qty = scanner.nextInt();
                scanner.nextLine();
                if(qty > 0){
                    isDigit =true;
                }else{
                    System.out.println("Quantity only allow positive integers.\n");
                }    
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Quantity only allow positive integers.\n");
            }
        }
        //proceed to supplier file to find the respective supplier
        filterSupplier(selectedItemName,filteredSupplID);

        found = false;
        while(!found){
            System.out.print(String.format("%-30s","Supplier(enter supplier ID) ") + ": ");
            selectedSupplID = scanner.nextLine();
            selectedSupplID = selectedSupplID.substring(0,1).toUpperCase() + selectedSupplID.substring(1);
            for (String supplID : filteredSupplID) {
                if(selectedSupplID.equals(supplID)){
                    found = true;
                }
            }
            if(!found){
                System.out.println("Sorry,your supplier ID does not found in " + selectedItemName + " Supplier List.\nPlease refer above list.\n");
            }
        }

        itemOrderDetail[itemCount] = selectedItemID + "\t" + qty + "\t" + selectedSupplID;

        if(itemCount == 9){
            itemCount++;
        }
    
        if(itemCount < 10){
            do{
                System.out.print(String.format("%-30s","Next Item?(Y->Next item N->Display order)(Max 10 items) ") + ": ");// ask for next item else display order
                nextItemAction = scanner.nextLine();
                if(nextItemAction.equalsIgnoreCase("Y")){
                    itemCount++;
                }else if(nextItemAction.equalsIgnoreCase("N")){

                }else{
                    System.out.println("Please select valid action (Y or N)");
                }
            }while(!(nextItemAction.equalsIgnoreCase("Y")) && !(nextItemAction.equalsIgnoreCase("N")));
        }else{
            System.out.println("Your order item limit is reached\n");
        }
    
    //if selected item cannot choose multiple time
    //open a variable calculate itemCount then store all orderItem detail in an array so it can store and display to staff the order
    }while(nextItemAction.equalsIgnoreCase("Y") && itemCount < 10);
    System.out.println("Order list generated.");
}


public void sortDisplayItemArr(){
    String[] categoryCode = new String[10];
    int storedCodeIndex=0;


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
                            tempSuppItem.add(itemList);//store info of item in temp Array
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

    //clear buffer of filtered SupplId else the previous item supplier still remain
    for(int index=0 ; index < filteredSupplID.length;index++){
        filteredSupplID[index] = null;
    }

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

    public void displayOrderList(String[] itemOrderDetail){
        int qty;
        double unitCost=0;
        double totalAmount = 0;
        String itemID,itemName="",supplierID;
        double[] total = new double[10];
        String[][] tempItemDetail = new String[10][6];

        //itemOrderDetail = ItemID  "\t" + qty + "\t" + SupplID;
        //itemOrderDetail = [0] ItemID,[1] qty,[2] SupplID;  item Name item unitCost
        String orderDate = order.getFormattedOrderDate();
        String orderTime = order.getFormattedOrderTime();
        line.printEqualLine(101);
        System.out.println("|Order ID: "+ generateOrderID() + String.format("%84s", ("DATE: "+ orderDate + "  TIME: " + orderTime + "|")));
        line.printEqualLine(101);
        System.out.println("|No." + "\t" + String.format("%-11s", "Supplier ID") + "\t" + String.format("%-10s", "Item ID") + "\t" + String.format("%-10s", "Item Name") + "\t" + String.format("%-8s", "Quantity") + "\t" + String.format("%-9s", "Unit cost") + "\t" + String.format("%-12s", "Subtotal")+ "|");
        line.printEqualLine(101);
        
        for (int no = 0 ; no < itemOrderDetail.length;no++) {
            if(itemOrderDetail[no] != null){
                String[] itemDetail = itemOrderDetail[no].split("\\t");
                itemID = itemDetail[0];
                qty = Integer.parseInt(itemDetail[1]);
                supplierID = itemDetail[2];
    
                for (Item itemInfo : order.getItemList()) {
                    if(itemInfo != null){
                        if(itemInfo.getItemId().equals(itemID)){
                            itemName = itemInfo.getItemName();
                            unitCost = itemInfo.getUnitCost();
                            break;
                        }
                    }
                }

                total[no] = order.transaction.calcSubTotal(unitCost, qty);
                System.out.println("|" + (no+1) + "\t" + String.format("%-11s", supplierID) + "\t" + String.format("%-10s", itemID) + "\t" + String.format("%-10s", itemName) + "\t" + String.format("%-8d", qty) + "\tRM" + String.format("%-9.2f", unitCost) + "\tRM" + String.format("%-10.2f",total[no]) +"|");
                
                tempItemDetail[no][0]=supplierID;
                tempItemDetail[no][1]=itemID;
                tempItemDetail[no][2]=itemName;
                tempItemDetail[no][3]=String.valueOf(qty);
                tempItemDetail[no][4]=("RM"+String.format("%.2f", unitCost));
                tempItemDetail[no][5]=("RM"+String.format("%.2f", total[no]));
            }
        }

        totalAmount = order.transaction.calTotalAmount(total);
        storeStockInOrderInfo(tempItemDetail,generateOrderID(), orderDate,orderTime,totalAmount);

        line.printEqualLine(101);
        System.out.println("|" + String.format("%87s", "Total: ") + "RM"+ String.format("%-10.2f", totalAmount) + "|");
        line.printEqualLine(101);
    }

    public void storeStockInOrderInfo(String[][]tempItemDetail,String orderID, String orderDate,String orderTime,double totalAmount){
        String stockInFilePath = "stockInOrder.txt";

        try {
            FileWriter writer = new FileWriter(stockInFilePath,true);
            writer.write(orderID + "\t" + orderDate + "\t" + orderTime + "\tRM" + String.format("%.2f", totalAmount) + "\n");

            for (String[] itemDetail : tempItemDetail) {
                if(itemDetail != null){
                    for(int element = 0; element< itemDetail.length;element++){
                        if(itemDetail[element]!=null){
                            writer.write(itemDetail[element] +"\t");

                            if (element == 5){
                                writer.write("\n");   
                            }
                        }
                    }
                }

            }

            writer.close();
        } catch (Exception e) {
            System.out.println(stockInFilePath + "unable to open");
        }
    }





//generate Order ID------------------------------------------------------------------------------------------------
    public String generateOrderID(){
        int noLine = 0;
        String stockInFilePath = "stockInOrder.txt";

        try (Scanner scanner = new Scanner(new File(stockInFilePath))){
            while(scanner.hasNextLine()){
                String lineContent = scanner.nextLine();
                if(lineContent.startsWith("SI")){
                    noLine++;
                }
            }

        } catch (Exception e) {
            System.out.println(stockInFilePath + " unable to open.");
        }

        return "SI" + String.format("%04d",(noLine+1));// find got how many line have start with SI
    }
}