import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.io.File;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;


public class StockInOrder extends Order{
    private List<Supplier> supplierInfo = new ArrayList<>();
    private Date dateReceived;

    Alignment line = new Alignment();
    Item      item = new Item();

    Scanner scanner = new Scanner(System.in);

//Constructor------------------------------------------------------------------
    StockInOrder(){

    }   
    
    StockInOrder(List<Supplier> supplierInfo,Date dateReceived){
        this.supplierInfo = supplierInfo;
        this.dateReceived = dateReceived;
    }

//getter & setter --------------------------------------------------------------
    public List<Supplier> getSupplierInfo(){
        return supplierInfo;
    }  


    public Date getDateReceived(){
        return dateReceived;
    }

    
    public void setSupplierInfo(List<Supplier> supplierInfo){
        this.supplierInfo = supplierInfo;
    } 

    public void setDateReceived(Date dateReceived){
        this.dateReceived = dateReceived;
    }

//menu-------------------------------------------------------------------------------------

public void stockInOrderMenu(String orderStaffID){
    int opt = 0;
    boolean isDigit = false;
    boolean found = false;

    if(orderStaffID != null){
        super.setStaffId(orderStaffID);
    }

    while(opt != 9 || !isDigit){

        try{
            System.out.println("\nStock In Order");
            line.printLine("Stock In Order".length());
            System.out.println("1.Add Order");
            System.out.println("2.Cancel Order");
            System.out.println("3.Modify Order");
            System.out.println("4.Receive Order");
            System.out.println("5.Search Order");
            System.out.println("6.Display All Stock In Order");
            System.out.println("7.Display Stock In Order Schedule");
            System.out.println("8.Display Received Stock In Order");
            System.out.println("9.Back to staff menu");
            System.out.print("Enter your action >> ");
            opt = scanner.nextInt();
            scanner.nextLine();
            isDigit = true;
            if(!(opt>=1 && opt <=9)){
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
                   cancelPurchase();
                   break;
                case 3:
                   //modify
                   modifyPurchase();
                   break;
                case 4:
                    //update received
                    receiveOrder();
                    break;
                case 5:
                    found = false;
                        while(!found){
                            found = searchOrder();

                            if(!found){
                                System.out.println("Order ID does not found.");
                            }
                        }
                    stockInOrderMenu(super.getStaffId());
                break;   
                case 6:
                    displayAllStockInOrder();
                    int action = 0;
                    found = false;

                    do{
                        try {
                            System.out.println("1.Search order for more detail\n2.back");
                            System.out.print("Enter your action: ");
                            action = scanner.nextInt();
                            scanner.nextLine();
                            if(action == 1){
                                while(!found){
                                    found = searchOrder();
        
                                if(!found){
                                    System.out.println("Order ID does not found.");
                                }
                            }
                            }else if(action == 2){

                            }else{
                                System.out.println("Invalid action.Action only allow(1 or 2)");
                            }
                                            
                        } catch (Exception e) {
                            scanner.nextLine();
                            System.out.println("Invalid action.Action only allow(1 or 2)");
                        }
                    }while(action != 1 && action != 2);

                    stockInOrderMenu(super.getStaffId());
                   break;
                case 7:
                    trackDelivery();
                    break;
                case 8:
                    displayReceivedOrder();
                    break;    
                case 9:
                   fastFoodInventory.menu();
                break;
                   
           }

        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Action only allow integers.\n");
        }
    }
}

//Add purchase order--------------------------------------------------------------------------------------------------------
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

    
    System.out.println("ADD ORDER");
    line.printLine("ADD ORDER".length());

    super.getItemList().clear();
    supplierInfo.clear();
    super.storeItemtoArr(); //move item info from file to Array
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
            System.out.print(String.format("%-30s","Item(enter item name)(X or x for exit)") + ": ");
            selectedItemName = scanner.nextLine();

            
            if(selectedItemName.equals("X") || selectedItemName.equals("x")){
                stockInOrderMenu(super.getStaffId());//for exit
            }

            //Find ID
            for (Item item : super.getItemList()) {
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
            if(Character.isLetter(selectedSupplID.charAt(0))){
                selectedSupplID = selectedSupplID.substring(0,1).toUpperCase() + selectedSupplID.substring(1);
            }
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
    for(int arrIndex = 0; arrIndex<super.getItemList().size();arrIndex++){
        String tempID = super.getItemList().get(arrIndex).getItemId();
        String tempcategoryCode = tempID.substring(0,2);
        if(storeCategoryCode(categoryCode,tempcategoryCode,storedCodeIndex)){
            storedCodeIndex++;
        }
    }

    //then loop the categoryCode the same category display first then continue next category
       for(int index=0;index<categoryCode.length;index++){
            if(categoryCode[index] != null){
                for (Item item : super.getItemList()) {
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
                    for (Item itemList : super.getItemList()) {
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
        List <String> tempItemDetail = new ArrayList<>();

        //itemOrderDetail = ItemID  "\t" + qty + "\t" + SupplID;
        //itemOrderDetail = [0] ItemID,[1] qty,[2] SupplID;  item Name item unitCost
        String orderDate = super.getFormattedOrderDate();
        String orderTime = super.getFormattedOrderTime();
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
    
                for (Item itemInfo : super.getItemList()) {
                    if(itemInfo != null){
                        if(itemInfo.getItemId().equals(itemID)){
                            itemName = itemInfo.getItemName();
                            unitCost = itemInfo.getUnitCost();
                            break;
                        }
                    }
                }

                total[no] = Transaction.calcSubTotal(unitCost, qty);
                System.out.println("|" + (no+1) + "\t" + String.format("%-11s", supplierID) + "\t" + String.format("%-10s", itemID) + "\t" + String.format("%-10s", itemName) + "\t" + String.format("%-8d", qty) + "\tRM" + String.format("%-9.2f", unitCost) + "\tRM" + String.format("%-10.2f",total[no]) +"|");
                
                tempItemDetail.add(supplierID + "\t" + itemID +  "\t" + itemName +  "\t" + String.valueOf(qty) + "\tRM" +(String.format("%.2f", unitCost)) + "\tRM" +String.format("%.2f", total[no]));
            }
        }

        totalAmount = super.transaction.calTotalAmount(total);

        line.printLine(101);
        System.out.println(String.format("%-88s","|Delivery Fee(-): ") + "RM"+String.format("%.2f", 0.0) + String.format("%7s", "|"));

        line.printEqualLine(101);
        System.out.println(String.format("%-50s",("|Purchase by staff(-)")) + String.format("%38s", "Total: ") + "RM"+ String.format("%-10.2f", totalAmount) + "|");
        line.printEqualLine(101);

        //store all the order detail into a list
        String orderOvrData = generateOrderID() + "\t" + orderDate + "\t" + orderTime + "\tRM" + String.format("%.2f",totalAmount) + "\t" + "-" + "\t" +"-";
        List<String> tempOrderDetail = new ArrayList<>();

        tempOrderDetail.add(orderOvrData);
        for (String itemContent : tempItemDetail) {
            tempOrderDetail.add(itemContent);
        }

        completeOrderAction(tempOrderDetail);
    }

    public void storeStockInOrderInfo(List <String>tempOrderDetail){
        String stockInFilePath = "stockInOrder.txt";

        try {
            FileWriter writer = new FileWriter(stockInFilePath,true);
            //write header content(ovr Content)
            writer.write(tempOrderDetail.get(0)+"\n");
            

            //write Item detail
            for(int noLineContent=1;noLineContent<tempOrderDetail.size();noLineContent++){
                if(tempOrderDetail.get(noLineContent) != null){
                    String[] elementContent = tempOrderDetail.get(noLineContent).split("\\t");
                    for(int element = 0; element< elementContent.length;element++){
                        if(elementContent[element]!=null){
                            if(element == 5){
                                writer.write(elementContent[element] +"\n");
                            }else{
                                writer.write(elementContent[element] +"\t");
                            }
                        }
                    }
                }else{
                    break;
                }
            }

            writer.close();
        } catch (Exception e) {
            System.out.println(stockInFilePath + "unable to open");
        }
    }

    public void completeOrderAction(List<String>tempOrderDetail){
        int action = 0;
        
        System.out.println("\nACTION");
        line.printLine("ACTION".length());
        System.out.println("1.Modify Order");
        System.out.println("2.Cancel Order");
        System.out.println("3.Confirm Order");
        do{
            try {
                System.out.print("Enter your action: ");
                action = scanner.nextInt();
                scanner.nextLine();

                switch (action) {
                    case 1:
                        modifyUnstoredOrder(tempOrderDetail);
                        break;
                    case 2:
                        System.out.println("Order cancelled...");
                    break;
                    case 3:
                    //payment
                        int opt = 0;

                        System.out.println("Delivery Method"); 
                        System.out.println("1.Ground(RM4.50/ship)\n2.Sea(RM7.50/ship)\n3.Air(RM15.00/ship)");
                        
                        do{
                            try {
                                System.out.print("Select method(1,2 or 3): ");
                                opt = scanner.nextInt();
                                scanner.nextLine();
                                if(opt == 1){
                                    addDeliverFeeStaffID(tempOrderDetail,4.5,"Ground",super.getStaffId());
                                }else if(opt == 2){
                                    addDeliverFeeStaffID(tempOrderDetail,7.5,"Sea",super.getStaffId());
                                }else if(opt == 3){
                                    addDeliverFeeStaffID(tempOrderDetail,15,"Air",super.getStaffId());
                                }else{
                                System.out.println("The option only allow (1,2 or 3)");    
                                }
                                
                            } catch (Exception e) {
                                scanner.nextLine();
                                System.out.println("The option only allow (1,2 or 3)");
                            }
                        }while(opt < 1 || opt > 3);

                        displayOrderListFromTemp(tempOrderDetail);
                        String[] elementContent = tempOrderDetail.get(0).split("\\t");
                        String purchaseOrderID = elementContent[0];
                        double transAmount = Double.parseDouble(elementContent[3].substring(2));

                        boolean ableTransfer = transaction.makeTransaction(purchaseOrderID,"Purchase",(transAmount * -1));
                        if(ableTransfer){
                            storeStockInOrderInfo(tempOrderDetail);
                        }else{
                            stockInOrderMenu(super.getStaffId());
                        }

                    break;
                    default:
                        break;
                }

                
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Action only allow (1,2 and 3)");
            }

            if(action < 1 || action > 3){
                System.out.println("Action only allow (1,2 or 3)");
            }
        }while(action < 1 || action > 3);    
    }

    public void modifyUnstoredOrder(List<String>tempOrderDetail){
        int action = 0;

        System.out.println("1.Add an item");
        System.out.println("2.Cancel an item");
        System.out.println("3.Change an item quantity");
        System.out.println("4.Back");


        do{
            try {
                System.out.print("Enter your action: ");
                action = scanner.nextInt();
                scanner.nextLine();
                
                String addItemName,addItemID="",selectedSupplID="";
                int qty = 0;
                double addItemUnitCost = 0;

                boolean isAlpha;
                boolean found;
                boolean same;

                switch (action) {
                    case 1:
                    //add an item
                        sortDisplayItemArr();
                        do{
                            found = false;
                            isAlpha = true;
                            same = false;
                           
                            //validate item name
                            System.out.print("Enter the item NAME you want add: ");
                            addItemName = scanner.nextLine();
    
                            for(char alphaCancelItem : addItemName.toCharArray()){
                                if(!(Character.isLetter(alphaCancelItem))){
                                    isAlpha = false;
                                    break;
                                }
                            }
    
                            if(isAlpha){
                                addItemName = addItemName.substring(0, 1).toUpperCase() + addItemName.substring(1).toLowerCase();
                            }
                            
                            if(tempOrderDetail.size() < 11){ //1 HEADER + 10 ITEM IS MAX
                                //check from item List (to check item available)
                                //check order got duplicate item
                                for (Item itemInfo : super.getItemList()) {
                                    if(addItemName.equals(itemInfo.getItemName())){
                                        found = true;
                                        for (int element = 1;element<tempOrderDetail.size();element++) { //tempOrderDetail is 0 is ovrData + >0 is item so start with 1
                                            String[] orderInfoElement = tempOrderDetail.get(element).split("\\t");
                                            if(orderInfoElement[2].equals(addItemName)){
                                                same = true;
                                            }else{
                                                addItemID = itemInfo.getItemId();
                                                addItemUnitCost = itemInfo.getUnitCost();
                                            }
                                        }
                                    }
                                }
                                if(same){
                                    System.out.println("This item has ordered.Please select different product.");
                                }
    
                                if(!found){
                                    System.out.println("Sorry, we don't have this item.\nPlease refer above list.Thank you.");
                                }
                            }else{
                                System.out.println("Your order item limit is reached\n");
                                modifyUnstoredOrder(tempOrderDetail);
                            }
    
                        }while(same || !found);

                        //validate qty
                        boolean isPosInt = false;
                        while (!isPosInt) {
                            try {
                                System.out.print("Enter item quantity: ");
                                qty = scanner.nextInt();
                                scanner.nextLine();
                                if(qty > 0){
                                    isPosInt = true;
                                }else{
                                    System.out.println("Quantity only allow positive integer.");    
                                }    
                            } catch (Exception e) {
                                scanner.nextLine();
                                System.out.println("Quantity only allow positive integer.");
                            }   
                        }

                        //filter and validate input of choosing supplier
                        String [] filteredSupplID = new String[10];
                filterSupplier(addItemName,filteredSupplID);

                found = false;
                while(!found){
                    System.out.print(String.format("%-30s","Supplier(enter supplier ID) ") + ": ");
                    selectedSupplID = scanner.nextLine();
                    if(Character.isLetter(selectedSupplID.charAt(0))){
                        selectedSupplID = selectedSupplID.substring(0,1).toUpperCase() + selectedSupplID.substring(1);
                    }
                    for (String supplID : filteredSupplID) {
                        if(selectedSupplID.equals(supplID)){
                            found = true;
                        }
                    }
                    if(!found){
                        System.out.println("Sorry,your supplier ID does not found in " + addItemName + " Supplier List.\nPlease refer above list.\n");
                    }
                }
                    double addItemSubTotal = Transaction.calcSubTotal(addItemUnitCost, qty);
                    tempOrderDetail.add(selectedSupplID + "\t" + addItemID + "\t" + addItemName + "\t" + qty + "\tRM" + String.format("%.2f", addItemUnitCost) + "\tRM" + String.format("%.2f", addItemSubTotal));

                    changeTotalModifyAdd(tempOrderDetail,addItemSubTotal);

                    displayOrderListFromTemp(tempOrderDetail);
                break;
                    case 2:
                    //cancel an item
                    List<String> modifiedContent = new ArrayList<>();
                    if(tempOrderDetail.size() > 2){ //mean if item only 1 in this order is unable to modify item out
                        found = false;
                        while(!found){
                            isAlpha = true;
                            System.out.print("Enter the item NAME you want delete: ");
                            String cancelItemName = scanner.nextLine();
    
                            for(char alphaCancelItem : cancelItemName.toCharArray()){
                                if(!(Character.isLetter(alphaCancelItem))){
                                    isAlpha = false;
                                    break;
                                }
                            }
                            if(isAlpha){  
                                cancelItemName = cancelItemName.substring(0,1).toUpperCase() + cancelItemName.substring(1).toLowerCase();
                            }
                            found = cancelItemToContent(tempOrderDetail, cancelItemName, modifiedContent);

                            //rearrange the List
                            String ovrData = modifiedContent.get(modifiedContent.size()-1);
                            String replaceValue = modifiedContent.get(0);
                            String nextContent = "";
            
                            for(int noLineContent = 0;noLineContent < modifiedContent.size();noLineContent++){ // last line is replace to one
                
                                if(noLineContent != (modifiedContent.size()-1)){    
                                    nextContent = modifiedContent.get(noLineContent+1);
                                    modifiedContent.set(noLineContent+1, replaceValue);
                                    replaceValue = nextContent;
                                }else{
                                    modifiedContent.set(0, ovrData);
                                }
                            }
                            
                            if(!found){
                                System.out.println("the item does not found in this order.");
                            }
                        }
                        //change tempOrderDetail(before cancel an item) to modifiedContent(after cancel an item)
                        tempOrderDetail.clear();
                        for (String newContent : modifiedContent) {
                            tempOrderDetail.add(newContent);
                        }

                        System.out.println("Modified order list");
                        line.printLine("Modified order list".length());
                        displayOrderListFromTemp(tempOrderDetail);
                    }else{
                        System.out.println("Sorry,you unable to modify the order to cancel an item because it only 1 item.Please select other option.\n");
                        
                    }
                    break;
                    case 3:
                        //change qty
                        found = false;
                        String modifyQtyItemName = "";

                        while(!found){
                            isAlpha = true;
                            System.out.print("Enter the item NAME that need to modify quantity: ");
                            modifyQtyItemName = scanner.nextLine();
                        
                            for(char alphaModifyItem : modifyQtyItemName.toCharArray()){
                                if(!(Character.isLetter(alphaModifyItem))){
                                    isAlpha = false;
                                    break;
                                }
                            }
                            if(isAlpha){  
                                modifyQtyItemName = modifyQtyItemName.substring(0,1).toUpperCase() + modifyQtyItemName.substring(1).toLowerCase();
                            }
                            
                            //check the order have this item or not
                            for (int element = 1;element<tempOrderDetail.size();element++) { //tempOrderDetail is 0 is ovrData + >0 is item so start with 1
                                  
                                String[] orderInfoElement = tempOrderDetail.get(element).split("\\t");
                                    if(orderInfoElement[2].equals(modifyQtyItemName)){
                                    found = true;
                                }
                            }

                            if(!found){
                                System.out.println("the item does not found in this order.");
                            }
                        }

                        boolean sameQty;
                        do{
                            isPosInt = false;
                            sameQty = false;
                            while (!isPosInt) {
                                try {
                                    System.out.print("Enter item NEW quantity: ");
                                    qty = scanner.nextInt();
                                    scanner.nextLine();
                                    if(qty > 0){
                                        isPosInt = true;
                                    }else{
                                        System.out.println("Quantity only allow positive integer.");    
                                    }    
                                } catch (Exception e) {
                                    scanner.nextLine();
                                    System.out.println("Quantity only allow positive integer.");
                                }   
                            }

                            List <String> modifiedQtyContent = new ArrayList<>();
                            sameQty = changeItemQty(tempOrderDetail, modifyQtyItemName, modifiedQtyContent, qty);

                            if(sameQty){
                                System.out.println("Sorry,you does not change the quantity.");
                            }else{
                                 //rearrange the List
                                String ovrData = modifiedQtyContent.get(modifiedQtyContent.size()-1);
                                String replaceValue = modifiedQtyContent.get(0);
                                String nextContent = "";
            
                                for(int noLineContent = 0;noLineContent < modifiedQtyContent.size();noLineContent++){ // last line is replace to one
                
                                    if(noLineContent != (modifiedQtyContent.size()-1)){    
                                       nextContent = modifiedQtyContent.get(noLineContent+1);
                                        modifiedQtyContent.set(noLineContent+1, replaceValue);
                                        replaceValue = nextContent;
                                    }else{
                                       modifiedQtyContent.set(0, ovrData);
                                    }
                                
                                    //change tempOrderDetail(before cancel an item) to modifiedQtyContent(after cancel an item)
                                    tempOrderDetail.clear();
                                    for (String newContent : modifiedQtyContent) {
                                    tempOrderDetail.add(newContent);
                                    }
                                }
                                System.out.println("Modified order list");
                                line.printLine("Modified order list".length());
                                displayOrderListFromTemp(tempOrderDetail);
                                storeStockInOrderInfo(tempOrderDetail);
                            }
                        }while(sameQty);  
                    break;
                    case 4:
                    //back
                    break;
                    default:
                        break;
                }
                completeOrderAction(tempOrderDetail);
                
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Action only allow (1,2 and 3)");
            }
        }while(action < 1 || action > 4);
    }

    public void addDeliverFeeStaffID(List <String> tempOrderDetail,double deliveryFee,String shippingType,String staffID){
        List<String> modifiedOrderDetail = new ArrayList<>();

        String[] elementDetail = tempOrderDetail.get(0).split("\\t");
        //[0]orderID,[1]Date,[2]Time,[3]Total,[4]shipping type,[5]staffID
        double newTotal = Double.parseDouble(elementDetail[3].substring(2)) + deliveryFee;

        modifiedOrderDetail.add(elementDetail[0] + "\t" + elementDetail[1] + "\t" + elementDetail[2] + "\tRM" + String.format("%.2f", newTotal) + "\t" + shippingType + "\t" + staffID);

        for(int lineContent = 1;lineContent<tempOrderDetail.size();lineContent++){
            modifiedOrderDetail.add(tempOrderDetail.get(lineContent));
        }

        //move update content to tempOrderDetail
        tempOrderDetail.clear();

        for (String modifiedContent : modifiedOrderDetail) {
            tempOrderDetail.add(modifiedContent);
        }
    }

    public void displayOrderListFromTemp(List<String> tempOrderDetail){
        
        String[] orderInfo = tempOrderDetail.get(0).split("\\t");

        line.printEqualLine(101);
        //0-orderID , 1-date, 2-time , 3.total , 4. deliveryMethod , 5. staff ID
        System.out.println("|Order ID: "+ orderInfo[0] + String.format("%84s", ("DATE: "+ orderInfo[1] + "  TIME: " + orderInfo[2] + "|")));

        line.printEqualLine(101);
        System.out.println("|No." + "\t" + String.format("%-11s", "Supplier ID") + "\t" + String.format("%-10s", "Item ID") + "\t" + String.format("%-10s", "Item Name") + "\t" + String.format("%-8s", "Quantity") + "\t" + String.format("%-9s", "Unit cost") + "\t" + String.format("%-12s", "Subtotal")+ "|");
        line.printEqualLine(101);
        
        for(int line = 1;line<tempOrderDetail.size();line++){
            String[] orderItemInfo = tempOrderDetail.get(line).split("\\t");
            //0-supplID ,1-itemID ,2-itemName ,3-qty ,4- unitCost ,5- subtotal
            System.out.println("|" + line + "\t" + String.format("%-11s", orderItemInfo[0]) + "\t" + String.format("%-10s", orderItemInfo[1]) + "\t" + String.format("%-10s", orderItemInfo[2]) + "\t" + String.format("%-8s", orderItemInfo[3]) + "\t" + String.format("%-11s", orderItemInfo[4]) + "\t" + String.format("%-12s",orderItemInfo[5]) +"|");
        }

        //"1.Ground(RM4.50/ship)\n2.Sea(RM7.50/ship)\n3.Air(RM15.00/ship)"
        double deliveryFee = 0;
        if(orderInfo[4].equals("Ground")){
            deliveryFee = 4.50;
        }else if(orderInfo[4].equals("Sea")){
            deliveryFee = 7.50;
        }else if (orderInfo[4].equals("Air")) {
            deliveryFee = 15.00;
        }else{
            deliveryFee = 0;
        }

        line.printLine(101);
        System.out.println(String.format("%-88s",("|" + "Delivery Fee(" + orderInfo[4] + "): ")) + "RM"+String.format("%5.2f", deliveryFee) + String.format("%6s", "|"));

        line.printEqualLine(101);
        System.out.println(String.format("%-50s",("|Purchase by staff(" + orderInfo[5] + ")")) + String.format("%38s", "Total: ") + String.format("%-12s", orderInfo[3]) + "|");
        line.printEqualLine(101);

        System.out.println("Press enter to continue...");
        scanner.nextLine();  // Waits for a key press
    }

    public void changeTotalModifyAdd(List<String>tempOrderDetail,double addItemSubTotal){
        List<String> tempModifiedContent = new ArrayList<>();

        String[] elementOvrData = tempOrderDetail.get(0).split("\\t");
        double newTotal = Double.parseDouble(elementOvrData[3].substring(2)) + addItemSubTotal;
        

        //0-Supplier ID 1- date 2- time 3-total
        tempModifiedContent.add(elementOvrData[0] + "\t" +elementOvrData[1] + "\t" +elementOvrData[2] + "\tRM" + String.format("%.2f",newTotal) + "\t" + "-" + "\t" + "-");

        for(int lineContent = 1;lineContent<tempOrderDetail.size();lineContent++){
            tempModifiedContent.add(tempOrderDetail.get(lineContent));
        }

        //move tempOrderDetail(before change) to tempModifiedContent(after change)
        tempOrderDetail.clear();
        for (String content : tempModifiedContent) {
            tempOrderDetail.add(content);
        }
    }


//Cancel purchase order--------------------------------------------------------------------------------------------
public void cancelPurchase(){
    String deletedOrderID ="";
    boolean found = false;
    
    String stockInFilePath = "stockInOrder.txt";

    System.out.println("CANCEL ORDER");
    line.printLine("CANCEL ORDER".length());

    displayAllStockInOrder();

    while (!found) {
        System.out.print("Enter the Order ID that you want cancel (X or x for exit): ");
        deletedOrderID = scanner.nextLine();

        
        if(deletedOrderID.equals("X") || deletedOrderID.equals("x")){
            stockInOrderMenu(super.getStaffId());//for exit
        }else{

            if(deletedOrderID.length() > 2){
                if(Character.isLetter(deletedOrderID.charAt(0)) && Character.isLetter(deletedOrderID.charAt(1))){
                    deletedOrderID = deletedOrderID.substring(0,2).toUpperCase() + deletedOrderID.substring(2);
                }
            }
        }
        
        found = findOrderIDFromFile(deletedOrderID);
        if(!found){
            System.out.println("Sorry,we cant find the order you want to deleted.Please try again.");
        }
    }
       displayOrderListFromID(deletedOrderID);

       //find total price
       double totalRefund = findTotalPrice(deletedOrderID);

       String confirm = "";

       do{
        System.out.print("Confirm to delete(Y - Yes|N - No): ");
        confirm = scanner.nextLine();

        if(confirm.equalsIgnoreCase("Y")){
            deleteOrderFromFile(stockInFilePath, deletedOrderID);
            transaction.makeTransaction(deletedOrderID, "Fully Refund", totalRefund);
            System.out.println("Record deleted");
        }else if(confirm.equalsIgnoreCase("N")){
            System.out.println("Cancel action terminated.");
        }else{
            System.out.println("Invalid action.Please select(Y or N)");
        }
       }while(!(confirm.equalsIgnoreCase("Y")) && !(confirm.equalsIgnoreCase("N")));

    }

public void displayOrderListFromID(String orderID){
    String stockInFilePath = "stockInOrder.txt";

    try {
        List<String> allLine = Files.readAllLines(Paths.get(stockInFilePath));//store all content
        List<String> selectedLine = new ArrayList<>();//find selected content
        boolean skip = false; //filter content

        for (String content : allLine) {
            if(content.startsWith("SI")){ //find the content next order?
                
                if(content.startsWith(orderID)){ //find deleted order
                    skip = true;
                }else{
                    skip = false;
                }
            }

            if(skip){
                selectedLine.add(content);
            }      
        }

        String[] orderInfo = selectedLine.get(0).split("\\t");

        line.printEqualLine(101);
        //0-orderID , 1-date, 2-time , 3.total, 4. ship method, 5.staff id handle this order
        System.out.println("|Order ID: "+ orderInfo[0] + String.format("%84s", ("DATE: "+ orderInfo[1] + "  TIME: " + orderInfo[2] + "|")));

        line.printEqualLine(101);
        System.out.println("|No." + "\t" + String.format("%-11s", "Supplier ID") + "\t" + String.format("%-10s", "Item ID") + "\t" + String.format("%-10s", "Item Name") + "\t" + String.format("%-8s", "Quantity") + "\t" + String.format("%-9s", "Unit cost") + "\t" + String.format("%-12s", "Subtotal")+ "|");
        line.printEqualLine(101);
        
        for(int line = 1;line<selectedLine.size();line++){
            String[] orderItemInfo = selectedLine.get(line).split("\\t");
            //0-supplID ,1-itemID ,2-itemName ,3-qty ,4- unitCost ,5- subtotal
            System.out.println("|" + line + "\t" + String.format("%-11s", orderItemInfo[0]) + "\t" + String.format("%-10s", orderItemInfo[1]) + "\t" + String.format("%-10s", orderItemInfo[2]) + "\t" + String.format("%-8s", orderItemInfo[3]) + "\t" + String.format("%-11s", orderItemInfo[4]) + "\t" + String.format("%-12s",orderItemInfo[5]) +"|");
        }
        
        //"1.Ground(RM4.50/ship)\n2.Sea(RM7.50/ship)\n3.Air(RM15.00/ship)"
        double deliveryFee = 0;
        if(orderInfo[4].equals("Ground")){
            deliveryFee = 4.50;
        }else if(orderInfo[4].equals("Sea")){
            deliveryFee = 7.50;
        }else if (orderInfo[4].equals("Air")) {
            deliveryFee = 15.00;
        }else{
            deliveryFee = 0;
        }

        line.printLine(101);
        System.out.println(String.format("%-88s",("|" + "Delivery Fee(" + orderInfo[4] + "): ")) + "RM"+String.format("%5.2f", deliveryFee) + String.format("%6s", "|"));

        line.printEqualLine(101);
        System.out.println(String.format("%-50s",("|Purchase by staff(" + orderInfo[5] + ")")) + String.format("%38s", "Total: ") + String.format("%-12s", orderInfo[3]) + "|");
        line.printEqualLine(101);

        System.out.println("Press enter to continue...");
        System.in.read();  // Waits for a key press
    } catch (IOException e) {
        System.out.println(stockInFilePath + "unable to open.");
    }   
}
public void deleteOrderFromFile(String stockInFilePath,String deletedOrderID){

    try {
        List<String> originalLine = Files.readAllLines(Paths.get(stockInFilePath));//before deleted content
        List<String> updatedLine = new ArrayList<>();//after deleted content
        boolean skip = false; //move original to update if founded deleted ID skip will true so the deleted content will not store in updated array

        for (String content : originalLine) {
            if(content.startsWith("SI")){ //find the content next order?
                
                if(content.startsWith(deletedOrderID)){ //find deleted order
                    skip = true;
                }else{
                    skip = false;
                }
            }

            if(!skip){
                updatedLine.add(content);
            }      
        }

        FileWriter writer = new FileWriter(stockInFilePath);
        for (String updatedContent : updatedLine) {
            writer.write(updatedContent + "\n");
        }
        writer.close();
    } catch (IOException e) {
        System.out.println(stockInFilePath + "unable to open.");
    }
}

public double findTotalPrice(String deleteOrderID){
    double totalPrice = 0.0;

    String stockInFilePath = "stockInOrder.txt";

    try (Scanner scanner = new Scanner (new File(stockInFilePath))){
        while(scanner.hasNextLine()){
            String lineContent = scanner.nextLine();
            if(lineContent.startsWith("SI")){
                String[] elementContent = lineContent.split("\\t");
                if(elementContent[0].equals(deleteOrderID)){
                    totalPrice = Double.parseDouble(elementContent[3].substring(2));
                }
            }

        }
        
    } catch (Exception e) {
        System.out.println(stockInFilePath + " unable to open.");
    }

    return totalPrice;
}

//modify purchase -------------------------------------------------------------------------------------------------
//cancel few item or change qty
public void modifyPurchase(){
    boolean found = false;
    String modifyOrderID = "";

    System.out.println("MODIFY ORDER");
    line.printLine("MODIFY ORDER".length());

    displayAllStockInOrder();

    while (!found) {
        System.out.print("Enter the Order ID that you want modify (X or x for exit): ");
        modifyOrderID = scanner.nextLine();
            
        if(modifyOrderID.equals("X") || modifyOrderID.equals("x")){
                found = true; //for exit
            
        }else{
        
            if(modifyOrderID.length() > 2){
                if(Character.isLetter(modifyOrderID.charAt(0)) && Character.isLetter(modifyOrderID.charAt(1))){
                    modifyOrderID = modifyOrderID.substring(0,2).toUpperCase() + modifyOrderID.substring(2);
                }
            }
            
            found = findOrderIDFromFile(modifyOrderID);
            if(!found){
                System.out.println("Sorry,we cant find the order you want to modify.Please try again.");
            }else{
                displayOrderListFromID(modifyOrderID);
                
                int modifyOpt = 0;
                do{
                    try {
                        System.out.println("\nModify option\n1.Cancel an item\n2.Modify an item quantity\n3.Back");
                        System.out.print("Enter your option: ");
                        modifyOpt = scanner.nextInt();
                        scanner.nextLine(); 
                
                        if(modifyOpt != 1 && modifyOpt != 2 && modifyOpt !=3){
                            System.out.println("option only allow (1 or 2 or 3)");    
                        }
                    } catch (Exception e) {
                        scanner.nextLine();
                        System.out.println("option only allow (1 or 2 or 3)");
                    }
                }while(modifyOpt != 1 && modifyOpt != 2  && modifyOpt !=3);
           
                modifyOrderFromFile(modifyOrderID, modifyOpt);
            }
        }
    }
}

    public void modifyOrderFromFile(String modifyOrderID,int modifyOpt){
        String stockInFilePath = "stockInOrder.txt";

        try {
            List<String> originalLine = Files.readAllLines(Paths.get(stockInFilePath));//before modify content
            List<String> requireModifyLine = new ArrayList<>(); // temp storage to store need modify data
            List<String> modifiedSpecificLine = new ArrayList<>(); //modified temp storage
            boolean need = false; //the allocate need modify content
            boolean found = false;
    
            // allocate modify line
            for (String content : originalLine) {
                if(content.startsWith("SI")){ //find the content next order?
                    
                    if(content.startsWith(modifyOrderID)){ //find modify content
                        need = true;
                    }else{
                        need = false;
                    }
                }
    
                if(need){
                    requireModifyLine.add(content);
                }      
            }

            boolean isAlpha;
            if(modifyOpt==1){
                if(requireModifyLine.size() > 2){ //mean if item only 1 in this order is unable to modify item out
                    found = false;
                    while(!found){
                        isAlpha = true;
                        System.out.print("Enter the item NAME you want delete: ");
                        String cancelItemName = scanner.nextLine();

                        for(char alphaCancelItem : cancelItemName.toCharArray()){
                            if(!(Character.isLetter(alphaCancelItem))){
                                isAlpha = false;
                                break;
                            }
                        }
                        if(isAlpha){  
                            cancelItemName = cancelItemName.substring(0,1).toUpperCase() + cancelItemName.substring(1).toLowerCase();
                        }
                        found = cancelItemToContent(requireModifyLine, cancelItemName, modifiedSpecificLine);

                        if(!found){
                            System.out.println("the item does not found in this order.");
                        }else{
                            double totalRefund = findTotalItemCost(requireModifyLine, cancelItemName);
                            transaction.makeTransaction(modifyOrderID, "Partial Refund", totalRefund);
                        }
                    }
                }else{
                    System.out.println("Sorry,u unable to modify the order to cancel an item because it only 1 item.Please go to delete order.\n");
                    cancelPurchase();
                    stockInOrderMenu(super.getStaffId());
                }

            }else if(modifyOpt == 2){
                found = false;
                String modifyQtyItemName = "";

                while(!found){ 
                    isAlpha = true;
                    System.out.print("Enter the item NAME that need to modify quantity: ");
                    modifyQtyItemName = scanner.nextLine();
    
                    for(char alphaModifyItem : modifyQtyItemName.toCharArray()){
                        if(!(Character.isLetter(alphaModifyItem))){
                            isAlpha = false;
                            break;
                        }
                    }
                    if(isAlpha){  
                        modifyQtyItemName = modifyQtyItemName.substring(0,1).toUpperCase() + modifyQtyItemName.substring(1).toLowerCase();
                    }

                    //find got this order and the item want to change qty have in this order
                    found = checkExist(modifyOrderID,modifyQtyItemName,stockInFilePath);

                    if(!found){
                        System.out.println("the item does not found in this order.");
                    }
                }  
    
                    boolean isInt = false;
                    boolean sameQty = false;
                    do{
                        isInt = false;
                        sameQty = false;
                        try {
                            System.out.print("Enter the new quantity: ");
                            int newQty = scanner.nextInt();
                            scanner.nextLine();
                            isInt = true;
                            sameQty = changeItemQty(requireModifyLine, modifyQtyItemName, modifiedSpecificLine, newQty);
                            
                            if(sameQty){
                                System.out.println("Sorry,you does not change the quantity.");
                            }else{
                                double diffTotal = findDiffItemCost(requireModifyLine, modifyQtyItemName, newQty);
                                // oldsubtotal - newsubtotal if(negative) need pay more else need refund
                                if (diffTotal > 0) {
                                    transaction.makeTransaction(modifyOrderID, "Reduce Item Quantity", diffTotal);
                                }else if(diffTotal < 0) {
                                    transaction.makeTransaction(modifyOrderID, "Add Item Quantity", diffTotal);
                                } else{
                                    System.out.println("No transaction make.");
                                }
                            }
                            
                        } catch (Exception e) {
                            scanner.nextLine();
                            System.out.println("Quantity only allow positive integer");
                        }

                    }while(!isInt || sameQty); 
           

            }else if(modifyOpt == 3){
                stockInOrderMenu(super.getStaffId());
            }

            if(modifyOpt == 1 || modifyOpt == 2){
                updateStoreFile(originalLine, requireModifyLine, modifiedSpecificLine, stockInFilePath, modifyOrderID);
                if(modifyOpt==1){
                    System.out.println("Item deleted from the order.");
                }else{
                    System.out.println("Item quantity changed.");
                }
                System.out.println("Updated order list");
                displayOrderListFromID(modifyOrderID);
            }


        } catch (IOException e) {
            System.out.println(stockInFilePath + "unable to open.");
        }
    }

    public boolean cancelItemToContent(List<String> requireModifiedContent,String cancelItemName, List<String> modifiedSpecificLine){
        boolean needCancel = false;
        boolean found = false;
        double minusAmount = 0;
        modifiedSpecificLine.clear();

        for(int lineNo = 1;lineNo<requireModifiedContent.size();lineNo++){ //bcz 0 is overall data
            String[] elementContent = requireModifiedContent.get(lineNo).split("\\t");
            if(elementContent[2].equals(cancelItemName)){
                needCancel = true;
                minusAmount = Double.parseDouble(elementContent[5].substring(2));
            }else{
                needCancel = false;
            }

            if(!needCancel){
                modifiedSpecificLine.add(requireModifiedContent.get(lineNo));
            }else{
                found = true;
            }         
        } 
         //modify ovr data
         if(found){
            String[] element = requireModifiedContent.get(0).split("\\t");
            double totalAmount = Double.parseDouble(element[3].substring(2)) - minusAmount;
            modifiedSpecificLine.add(element[0]+"\t"+super.getFormattedOrderDate()+"\t"+super.getFormattedOrderTime()+"\tRM"+String.format("%.2f", totalAmount) + "\t" + element[4] + "\t" + element[5]);
         }
        return found;
    }

        public boolean changeItemQty(List<String> requireModifiedContent,String modifyQtyItemName, List<String> modifiedSpecificLine,int newQty){
            boolean change = false;
            boolean sameQty = false;
            double oldSubTotal=0,newSubTotal=0,unitPrice;
            modifiedSpecificLine.clear();

            for(int lineNo = 1;lineNo<requireModifiedContent.size();lineNo++){
                String[] elementContent = requireModifiedContent.get(lineNo).split("\\t");
                if(elementContent[2].equals(modifyQtyItemName)){
                    change = true;
                }else{
                    change = false;
                }

                if(change){
                    if(Integer.parseInt(elementContent[3]) != newQty){
                        unitPrice = Double.parseDouble(elementContent[4].substring(2));
                        oldSubTotal = Double.parseDouble(elementContent[5].substring(2));
                        newSubTotal = newQty * unitPrice;
                        modifiedSpecificLine.add(elementContent[0] + "\t" + elementContent[1] + "\t" + elementContent[2] + "\t" + newQty + "\t" + elementContent[4] + "\tRM" + String.format("%.2f", newSubTotal));
                    }else{
                        sameQty = true;
                        break;
                    }
                }else{
                    modifiedSpecificLine.add(requireModifiedContent.get(lineNo));
                }
        }

            if(!sameQty){
                double diffSubtotal = newSubTotal - oldSubTotal;
    
                String[] element = requireModifiedContent.get(0).split("\\t");
                double totalAmount = Double.parseDouble(element[3].substring(2)) + diffSubtotal;
                modifiedSpecificLine.add(element[0]+"\t"+super.getFormattedOrderDate()+"\t"+super.getFormattedOrderTime()+"\tRM"+String.format("%.2f", totalAmount)+"\t"+element[4]+"\t"+element[5]);

            }

            return sameQty;
        }

        public void updateStoreFile(List<String> originalLine,List<String> requireModifiedContent,List<String> modifiedSpecificLine,String stockInFilePath,String modifyOrderID){
            List<String> updatedLine = new ArrayList<>(); //after modified content
            boolean need = false;

                String ovrData = modifiedSpecificLine.get(modifiedSpecificLine.size()-1);
                String replaceValue = modifiedSpecificLine.get(0);
                String nextContent = "";

                for(int noLineContent = 0;noLineContent < modifiedSpecificLine.size();noLineContent++){ // last line is replace to one
    
                    if(noLineContent != (modifiedSpecificLine.size()-1)){    
                        nextContent = modifiedSpecificLine.get(noLineContent+1);
                        modifiedSpecificLine.set(noLineContent+1, replaceValue);
                        replaceValue = nextContent;
                    }else{
                        modifiedSpecificLine.set(0, ovrData);
                    }
                }

                //move to updateLine
                int modifyListLineNo = 0;
                for (String content : originalLine) {
                    if(content.startsWith("SI")){ //find the content next order?
                    
                        if(content.startsWith(modifyOrderID)){ //find modify content
                            need = true;
                        }else{
                            need = false;
                        }
                    }
    
                    if(need){
                        if(modifyListLineNo < modifiedSpecificLine.size()){
                            updatedLine.add(modifiedSpecificLine.get(modifyListLineNo));
                            modifyListLineNo++;
                        }
                    }else{
                        updatedLine.add(content);
                    }
                }
                //save in file
                try {
                    FileWriter writer = new FileWriter(stockInFilePath);
                    for (String updatedContent : updatedLine) {
                        writer.write(updatedContent + "\n");
                    }
                    writer.close();                        
                } catch (IOException e) {
                    System.out.println(stockInFilePath + "unable to open");
                }
                

        }


        public boolean checkExist(String modifyOrderID,String modifyQtyItemName,String stockInFilePath){
            boolean found = false;
            boolean selected = false;

            try {
                List<String> allContent = Files.readAllLines(Paths.get(stockInFilePath));
                List<String> selectedOrderContent = new ArrayList<>();


                //find got this Order BY ID
                for (String content : allContent) {
                    if(content != null){
                        if(content.startsWith("SI")){
                            String[] elementContent = content.split("\\t");
                            if(elementContent[0].equals(modifyOrderID)){
                                selected = true;
                            }else{
                                selected = false;
                            }
                        }

                        if(selected){
                            selectedOrderContent.add(content);
                        }
                    }
                }

                //Find got this item in selected order
                for (String content : selectedOrderContent) {
                    String[] elementContent = content.split("\\t");

                    if(elementContent[2].equals(modifyQtyItemName)){
                        found = true;
                        break;
                    }
                }
               
            } catch (Exception e) {
                System.out.println(stockInFilePath + "unable to open.");
            }

            return found;
        }

        public double findTotalItemCost(List<String> requireModifyLine,String cancelItemName){
            double totalRefund = 0.0;

            for(int lineNo = 1;lineNo<requireModifyLine.size();lineNo++){ //bcz 0 is overall data
                String[] elementContent = requireModifyLine.get(lineNo).split("\\t");
                if(elementContent[2].equals(cancelItemName)){
                    totalRefund = Double.parseDouble(elementContent[5].substring(2));
                }
            }
            return totalRefund;
        }

        public double findDiffItemCost(List<String> requireModifyLine,String modifyQtyItemName,int newQty){
            double diffTotal = 0,unitPrice,oldSubTotal,newSubTotal;

            for(int lineNo = 1;lineNo<requireModifyLine.size();lineNo++){
                String[] elementContent = requireModifyLine.get(lineNo).split("\\t");
                if(elementContent[2].equals(modifyQtyItemName)){
                    unitPrice = Double.parseDouble(elementContent[4].substring(2));
                    oldSubTotal = Double.parseDouble(elementContent[5].substring(2));
                    newSubTotal = newQty * unitPrice;
                    diffTotal = oldSubTotal - newSubTotal;
                }
            }

            return diffTotal;
        }

//generate Order ID------------------------------------------------------------------------------------------------
    public String generateOrderID(){
        int noLine = 0;
        int idNum = 0;
        String stockInFilePath = "stockInOrder.txt";
        List<String> tempStore = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(stockInFilePath))){
            while(scanner.hasNextLine()){
                String lineContent = scanner.nextLine();
                if(lineContent.startsWith("SI")){
                    tempStore.add(lineContent);
                    noLine++;
                }
            }

            if(!tempStore.isEmpty()){
                idNum = Integer.parseInt(tempStore.get(noLine-1).substring(2,6));
            }
            

        } catch (IOException e) {
            System.out.println(stockInFilePath + " unable to open.");
        }

        return "SI" + String.format("%04d",(idNum+1));// find got how many line have start with SI
    }


 //Find ID from file------------------------------------------------------------------------------------------------------
 public boolean findOrderIDFromFile(String orderID){
    String stockInFilePath = "stockInOrder.txt";
    boolean found = false;
    try {
        List<String> allContentOrderFile = Files.readAllLines(Paths.get(stockInFilePath));

        for (String content : allContentOrderFile) {
            String[] element = content.split("\\t");
            if(element[0].equals(orderID)){
                found = true;
            }
        }
        
    } catch (IOException e) {
        System.out.println(stockInFilePath + "unable to open.");
    }
    return found;
 }
 
 //Display all stock in order----------------------------------------------------------------------
    public void displayAllStockInOrder(){
        String stockInFilePath = "stockInOrder.txt";

        System.out.print("+");
        line.printLineNoNewLine(112);
        System.out.println("+");
        System.out.printf("| %-3s | %-15s | %-15s | %-15s | %-15s | %-14s | %-15s |\n",
        "No.", "Order ID", "Status", "Date", "Time", "Delivery","Total");
        System.out.print("+");
        line.printLineNoNewLine(112);
        System.out.println("+");

        try(Scanner scanner = new Scanner(new File(stockInFilePath))) {
            int noOrder = 1;
            while(scanner.hasNextLine()){
                String lineContent = scanner.nextLine();
                if(lineContent.startsWith("SI")){
                    String[] elementContent = lineContent.split("\\t");
                    //[0]orderID,[1]orderDate,[2]orderTime,[3]total price,[4]delivery method,[5]staffID
                    System.out.printf("| %-3s | %-15s | %-15s | %-15s | %-15s | %-14s | %-15s |\n",
        noOrder, elementContent[0], "Paid", elementContent[1], elementContent[2], elementContent[4],elementContent[3]);
                    noOrder++;
                }
            }

        System.out.print("+");
        line.printLineNoNewLine(112);
        System.out.println("+");
        } catch (IOException e) {
            System.out.println(stockInFilePath + " unable to open.");
        }

    }

 //Display received order ------------------------------------
 public void displayReceivedOrder(){
    String receivedFilePath = "receivedOrderItem.txt";

    System.out.print("+");
    line.printLineNoNewLine(112);
    System.out.println("+");
    System.out.printf("| %-3s | %-15s | %-15s | %-15s | %-15s | %-14s | %-15s |\n",
    "No.", "Order ID", "Status", "Date", "Time", "Delivery","Total");
    System.out.print("+");
    line.printLineNoNewLine(112);
    System.out.println("+");

    try(Scanner scanner = new Scanner(new File(receivedFilePath))) {
        int noOrder = 1;
        while(scanner.hasNextLine()){
            String lineContent = scanner.nextLine();
            if(lineContent.startsWith("SI")){
                String[] elementContent = lineContent.split("\\t");
                //[0]orderID,[1]orderDate,[2]orderTime,[3]total price,[4]delivery method,[5]staffID
                System.out.printf("| %-3s | %-15s | %-15s | %-15s | %-15s | %-14s | %-15s |\n",
    noOrder, elementContent[0], "Paid", elementContent[1], elementContent[2], elementContent[4],elementContent[3]);
                noOrder++;
            }
        }

    System.out.print("+");
    line.printLineNoNewLine(112);
    System.out.println("+");

    System.out.println("Press enter to continue...");
    System.in.read();  // Waits for a key press
    } catch (IOException e) {
        System.out.println(receivedFilePath + " unable to open.");
    }
} 

//Search order-----------------------------------------------
    public boolean searchOrder(){
        String stockInFilePath = "stockInOrder.txt";
        boolean selected = false;
        boolean found = false;
        boolean isAlpha = false;
        int itemNo = 1;
        double deliveryFee = 0.0;
        String deliveryMethod = "",total="",staffID = "";

        System.out.println("SEARCH ORDER");
        line.printLine("SEARCH ORDER".length());
        System.out.print("Enter Order ID that you want to view (X or x for exit): ");
        String searchID = scanner.nextLine();

      if(searchID.equals("X") || searchID.equals("x")){
        found = true; //for exit
      }else{

        if(searchID.length()>2){
            if(Character.isLetter(searchID.charAt(0)) && Character.isLetter(searchID.charAt(1))){
                isAlpha = true;
            }
        }

        if(isAlpha){
            searchID = searchID.substring(0, 2).toUpperCase() + searchID.substring(2);
        }

        try (Scanner scanner = new Scanner(new File(stockInFilePath))){
            while(scanner.hasNextLine()){
                String lineContent = scanner.nextLine();
                if(lineContent.startsWith("SI")){
                    String[] elementOvrContent = lineContent.split("\\t");
                    if(elementOvrContent[0].equals(searchID)){

                         deliveryMethod = elementOvrContent[4];
                         total = elementOvrContent[3];
                         staffID = elementOvrContent[5]; 

                        //print ovr order info
                        line.printEqualLine(101);
                        System.out.println("|Order ID: "+ elementOvrContent[0] + String.format("%84s", ("DATE: "+ elementOvrContent[1] + "  TIME: " + elementOvrContent[2] + "|")));
                        line.printEqualLine(101);

                        System.out.println("|No." + "\t" + String.format("%-11s", "Supplier ID") + "\t" + String.format("%-10s", "Item ID") + "\t" + String.format("%-10s", "Item Name") + "\t" + String.format("%-8s", "Quantity") + "\t" + String.format("%-9s", "Unit cost") + "\t" + String.format("%-12s", "Subtotal")+ "|");
                        line.printEqualLine(101);

                        selected = true;
                        found = true;
                    }else{
                        selected = false;
                    }
                }else{
                    //print item order detail
                    if(selected && found){
                        String[] elementItemContent = lineContent.split("\\t");

                        //0-supplID ,1-itemID ,2-itemName ,3-qty ,4- unitCost ,5- subtotal
                        System.out.println("|" + itemNo + "\t" + String.format("%-11s", elementItemContent[0]) + "\t" + String.format("%-10s", elementItemContent[1]) + "\t" + String.format("%-10s", elementItemContent[2]) + "\t" + String.format("%-8s", elementItemContent[3]) + "\t" + String.format("%-11s", elementItemContent[4]) + "\t" + String.format("%-12s",elementItemContent[5]) +"|");
                        itemNo++;
                    }
                }
            }

            if(found){
                //"1.Ground(RM4.50/ship)\n2.Sea(RM7.50/ship)\n3.Air(RM15.00/ship)"
                if(deliveryMethod.equals("Ground")){
                    deliveryFee = 4.50;
                }else if(deliveryMethod.equals("Sea")){
                    deliveryFee = 7.50;
                }else if (deliveryMethod.equals("Air")) {
                    deliveryFee = 15.00;
                }else{
                    deliveryFee = 0;
                }

                line.printLine(101);
                System.out.println(String.format("%-88s",("|" + "Delivery Fee(" + deliveryMethod + "): ")) + "RM"+String.format("%5.2f", deliveryFee) + String.format("%6s", "|"));

                line.printEqualLine(101);
                System.out.println(String.format("%-50s",("|Purchase by staff(" + staffID + ")")) + String.format("%38s", "Total: ") + String.format("%-12s", total) + "|");
                line.printEqualLine(101);

                System.out.println("Press enter to continue...");
                System.in.read();  // Waits for a key press
            }
            
        } catch (Exception e) {
            System.out.println(stockInFilePath + " unable to open.");
        }
      }
        return found;
    }

    // track delivery ----------------------------------------------------------------------------------------
    public void trackDelivery(){
        //display the unreach date stock in order info
        String stockInFilePath = "stockInOrder.txt";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        Calendar calendar = Calendar.getInstance(); //initial with current date and time

        try (Scanner scanner = new Scanner(new File(stockInFilePath))){
            System.out.println("\nSTOCK IN ORDER SCHEDULE");
            line.printLine("STOCK IN ORDER SCHEDULE".length());
            line.printLine(152);
            System.out.printf("|%-15s|\t%-30s|\t%-30s|\t%-20s|\t%-20s|\t%-15s|\n","Order ID","Order Date","Estimate Receive Date","Delivery Method","Handle by","Status");
            line.printLine(152);
            while(scanner.hasNextLine()){
                String lineContent = scanner.nextLine();
                if(lineContent.startsWith("SI")){
                    String[] elementContent = lineContent.split("\\t");
                    
                    Date orderDate = dateFormat.parse(elementContent[1]);//convert from string to date
                    calendar.setTime(orderDate); // set to order date

                    if(elementContent[4].equals("Ground")){
                        calendar.add(Calendar.DAY_OF_MONTH,7);
                    }else if(elementContent[4].equals("Sea")){ 
                        calendar.add(Calendar.DAY_OF_MONTH,5);
                    }else{ //air
                        calendar.add(Calendar.DAY_OF_MONTH,3); // add 3 day
                    }                    


                    String estimateReceiveDate = dateFormat.format(calendar.getTime());
                    Date    currentDate = new Date();
                    String status ;
                    if(currentDate.before(calendar.getTime())){
                        status = "delivering";
                    }else if(currentDate.after(calendar.getTime())){
                        status = "late";
                    }else{
                        status = "reach estimate time";
                    }

                    System.out.printf("|%-15s|\t%-30s|\t%-30s|\t%-20s|\t%-20s|\t%-15s|\n",elementContent[0],elementContent[1],estimateReceiveDate,elementContent[4],elementContent[5],status);
                    line.printLine(152);
                    
                    System.out.println("Press enter to continue...");
                    System.in.read();  // Waits for a key press
                }
            }
        } catch (Exception e) {
            System.out.println(stockInFilePath + " unable to open.");
        }
    }

    //receive order ---------------------------------------------------------------------------------------------------------------------
    public void receiveOrder(){
        String receivedOrderID ="";
        boolean found = false;
        
        String stockInFilePath = "stockInOrder.txt";
    
        System.out.println("UPDATE RECEIVED ORDER");
        line.printLine("UPDATE RECEIVED ORDER".length());
    
        while (!found) {
            System.out.print("Enter the Order ID that received (X or x for exit): ");
            receivedOrderID = scanner.nextLine();
    
            
            if(receivedOrderID.equals("X") || receivedOrderID.equals("x")){
                stockInOrderMenu(super.getStaffId());//for exit
            }else{
    
                if(receivedOrderID.length() > 2){
                    if(Character.isLetter(receivedOrderID.charAt(0)) && Character.isLetter(receivedOrderID.charAt(1))){
                        receivedOrderID = receivedOrderID.substring(0,2).toUpperCase() + receivedOrderID.substring(2);
                    }
                }
            }
            
            found = findOrderIDFromFile(receivedOrderID);
            if(!found){
                System.out.println("Sorry,we cant find the order that you received.Please try again.");
            }
        }
           displayOrderListFromID(receivedOrderID);
    
           String confirm = "";
    
           do{
            System.out.print("Confirm received(Y - Yes|N - No): ");
            confirm = scanner.nextLine();
    
            if(confirm.equalsIgnoreCase("Y")){
                storeReceivedOrder(stockInFilePath, receivedOrderID);
                System.out.println("Record updated");
            }else if(confirm.equalsIgnoreCase("N")){
                System.out.println("Action terminated.");
            }else{
                System.out.println("Invalid action.Please select(Y or N)");
            }
           }while(!(confirm.equalsIgnoreCase("Y")) && !(confirm.equalsIgnoreCase("N")));
    }

    public void storeReceivedOrder(String stockInFilePath,String receivedOrderID){

    try {
        List<String> originalLine = Files.readAllLines(Paths.get(stockInFilePath));//before deleted received order content
        List<String> updatedLine = new ArrayList<>();//after deleted received order content
        List<String> receivedContent = new ArrayList<>();//stored received Content move to received order file
        boolean skip = false; //move original to update if founded deleted ID skip will true so the deleted content will not store in updated array

        for (String content : originalLine) {
            if(content.startsWith("SI")){ //find the content next order?
                
                if(content.startsWith(receivedOrderID)){ //find deleted order
                    skip = true;
                }else{
                    skip = false;
                }
            }

            if(!skip){
                updatedLine.add(content);
            }else{
                receivedContent.add(content);
            }      
        }

        FileWriter writer = new FileWriter(stockInFilePath);
        //remove received detail from stockInOrder.txt
        for (String updatedContent : updatedLine) {
            writer.write(updatedContent + "\n");
        }
        writer.close();

        //move the received content to receivedOrderItem.txt
        FileWriter moveWriter = new FileWriter("receivedOrderItem.txt",true);
            for (String receivedInfo : receivedContent) {
                moveWriter.write(receivedInfo + "\n");
            }
        moveWriter.close();

    } catch (IOException e) {
        System.out.println(stockInFilePath + "unable to open.");
    }
    }

    //stock in order
    public void generateReport(){
        int[] itemQty = new int[50];
        String[] itemName = new String[50];
        double[] unitCost = new double[50];
        double[] itemValue = new double[50];
        boolean sameItem = false;

        int itemNo = 0;

        String stockInFilePath = "stockInOrder.txt";

        try (Scanner scanner = new Scanner(new File(stockInFilePath))){
            while(scanner.hasNextLine()){
                String lineContent = scanner.nextLine();

                if(!(lineContent.startsWith("SI"))){
                    String[] elementContent = lineContent.split("\\t");
                    if(itemName[0] != null){
                        sameItem = false;
                        for (int noItem=0;noItem <= itemNo;noItem++) {
                            if(itemName[noItem] != null){ 
                                if(elementContent[2].equals(itemName[noItem])){ //got this item alrdy no need store the item name
                                    itemQty[noItem] += Integer.parseInt(elementContent[3]);
                                    sameItem = true;
                                    break;
                                }
                            }     
                        }
                    }
                    
                    if(!sameItem){
                        unitCost[itemNo] = Double.parseDouble(elementContent[4].substring(2));
                        itemName[itemNo] = elementContent[2];
                        itemQty[itemNo] = Integer.parseInt(elementContent[3]);
                        itemNo++;
                    } 
                }
            }
            double totalSpend =0.0;

            line.printLine(55);
            System.out.printf("%-54s%s\n","|STOCK IN ORDER REPORT","|");
            line.printLine(55);
            System.out.printf("|%-15s |%-20s| %-14s|\n","Item Name","Total Quantity","Item Value(RM)");
            line.printLine(55);
            for(int i =0 ; i< itemNo;i++){
                itemValue[i] = unitCost[i] * itemQty[i];
                totalSpend += itemValue[i];
                
            System.out.printf("|%-15s |%-20d| %14.2f|\n",itemName[i],itemQty[i],itemValue[i]);
            }
            
            line.printLine(55);
            System.out.printf("|%37s|%15.2f|\n","Total Spend:" ,totalSpend);
            line.printLine(55);
        } catch (Exception e) {
            System.out.println(stockInFilePath + " unable to open.");
        }

    } 
}
