import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.nio.file.Files;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class StockInOrder extends Order{
    private List<Supplier> supplierInfo = new ArrayList<>();
    private Date dateReceived;

    Alignment line = new Alignment();
    Item      item = new Item();

    Scanner scanner = new Scanner(System.in);

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
public void StockInOrderMenu(){
    int opt = 0;
    boolean isDigit = false;

    while(opt != 4 || !isDigit){
        System.out.println("Stock In Order");
        line.printLine("Stock In Order".length());
        System.out.println("1.Add Order");
        System.out.println("2.Cancel Order");
        System.out.println("3.Modify Order");
        System.out.println("4.Back to staff menu");

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
                   cancelPurchase();
                   break;
               case 3:
                   //modify
                   modifyPurchase();
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
            System.out.print(String.format("%-30s","Item(enter item name)") + ": ");
            selectedItemName = scanner.nextLine();
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
        String[][] tempItemDetail = new String[10][6];

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

                total[no] = super.transaction.calcSubTotal(unitCost, qty);
                System.out.println("|" + (no+1) + "\t" + String.format("%-11s", supplierID) + "\t" + String.format("%-10s", itemID) + "\t" + String.format("%-10s", itemName) + "\t" + String.format("%-8d", qty) + "\tRM" + String.format("%-9.2f", unitCost) + "\tRM" + String.format("%-10.2f",total[no]) +"|");
                
                tempItemDetail[no][0]=supplierID;
                tempItemDetail[no][1]=itemID;
                tempItemDetail[no][2]=itemName;
                tempItemDetail[no][3]=String.valueOf(qty);
                tempItemDetail[no][4]=("RM"+String.format("%.2f", unitCost));
                tempItemDetail[no][5]=("RM"+String.format("%.2f", total[no]));
            }
        }

        totalAmount = super.transaction.calTotalAmount(total);
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


//Cancel purchase order--------------------------------------------------------------------------------------------
public void cancelPurchase(){
    String deletedOrderID ="";
    boolean found = false;
    
    String stockInFilePath = "stockInOrder.txt";

    while (!found) {
        System.out.print("Enter the Order ID that you want cancel: ");
        deletedOrderID = scanner.nextLine();
        if(Character.isLetter(deletedOrderID.charAt(0)) && Character.isLetter(deletedOrderID.charAt(1))){
            deletedOrderID = deletedOrderID.substring(0,2).toUpperCase() + deletedOrderID.substring(2);
        }
        
        found = findOrderIDFromFile(deletedOrderID);
        if(!found){
            System.out.println("Sorry,we cant find the order you want to deleted.Please try again.");
        }
       }
       displayOrderListFromID(deletedOrderID);

       String confirm = "";

       do{
        System.out.print("Confirm to delete(Y - Yes|N - No): ");
        confirm = scanner.nextLine();

        if(confirm.equalsIgnoreCase("Y")){
            deleteOrderFromFile(stockInFilePath, deletedOrderID);
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
        //0-orderID , 1-date, 2-time , 3.total
        System.out.println("|Order ID: "+ orderInfo[0] + String.format("%84s", ("DATE: "+ orderInfo[1] + "  TIME: " + orderInfo[2] + "|")));

        line.printEqualLine(101);
        System.out.println("|No." + "\t" + String.format("%-11s", "Supplier ID") + "\t" + String.format("%-10s", "Item ID") + "\t" + String.format("%-10s", "Item Name") + "\t" + String.format("%-8s", "Quantity") + "\t" + String.format("%-9s", "Unit cost") + "\t" + String.format("%-12s", "Subtotal")+ "|");
        line.printEqualLine(101);
        
        for(int line = 1;line<selectedLine.size();line++){
            String[] orderItemInfo = selectedLine.get(line).split("\\t");
            //0-supplID ,1-itemID ,2-itemName ,3-qty ,4- unitCost ,5- subtotal
            System.out.println("|" + line + "\t" + String.format("%-11s", orderItemInfo[0]) + "\t" + String.format("%-10s", orderItemInfo[1]) + "\t" + String.format("%-10s", orderItemInfo[2]) + "\t" + String.format("%-8s", orderItemInfo[3]) + "\t" + String.format("%-11s", orderItemInfo[4]) + "\t" + String.format("%-12s",orderItemInfo[5]) +"|");
        }
        line.printEqualLine(101);
        System.out.println("|" + String.format("%87s", "Total: ") + String.format("%-12s", orderInfo[3]) + "|");
        line.printEqualLine(101);

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

//modify purchase -------------------------------------------------------------------------------------------------
//cancel few item or change qty
    public void modifyPurchase(){
        boolean found = false;
        String modifyOrderID = "";

        while (!found) {
            System.out.print("Enter the Order ID that you want modify: ");
            modifyOrderID = scanner.nextLine();
            
            if(Character.isLetter(modifyOrderID.charAt(0)) && Character.isLetter(modifyOrderID.charAt(1))){
                modifyOrderID = modifyOrderID.substring(0,2).toUpperCase() + modifyOrderID.substring(2);
            }

            found = findOrderIDFromFile(modifyOrderID);
            if(!found){
                System.out.println("Sorry,we cant find the order you want to deleted.Please try again.");
            }
           }
           displayOrderListFromID(modifyOrderID);

           int modifyOpt = 0;
           do{
            try {
                System.out.println("\nModify option\n1.Cancel an item\n2.Modify an item quantity");
                System.out.print("Enter your option: ");
                modifyOpt = scanner.nextInt();
                scanner.nextLine(); 
                if(modifyOpt != 1 && modifyOpt != 2){
                    System.out.println("option only allow (1 or 2)");    
                }
               } catch (Exception e) {
                scanner.nextLine();
                System.out.println("option only allow (1 or 2)");
               }
           }while(modifyOpt != 1 && modifyOpt != 2);
           
           modifyOrderFromFile(modifyOrderID, modifyOpt);
    }

    public void modifyOrderFromFile(String modifyOrderID,int modifyOpt){
        String stockInFilePath = "stockInOrder.txt";

        try {
            List<String> originalLine = Files.readAllLines(Paths.get(stockInFilePath));//before modify content
            List<String> requireModifyLine = new ArrayList<>(); // temp storage to store need modify data
            List<String> modifiedSpecificLine = new ArrayList<>(); //modified temp storage 
            List<String> updatedLine = new ArrayList<>();//after modified content
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

            if(modifyOpt==1){
                if(requireModifyLine.size() > 2){ //mean if item only 1 in this order is unable to modify item out
                    found = false;
                    while(!found){
                        boolean isAlpha = true;
                        System.out.print("Enter the item u want delete: ");
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
                        }
                    }                    
                }else{
                    System.out.println("Sorry,u unable to modify the order to cancel an item because it only 1 item.Please go to delete order\n");
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
            
            FileWriter writer = new FileWriter(stockInFilePath);
            for (String updatedContent : updatedLine) {
                writer.write(updatedContent + "\n");
            }
            writer.close();
            System.out.println("Item deleted from the order.");
            System.out.println("Updated order list");
            displayOrderListFromID(modifyOrderID);
        } catch (IOException e) {
            System.out.println(stockInFilePath + "unable to open.");
        }
    }

    public boolean cancelItemToContent(List<String> requireModifiedContent,String cancelItemName, List<String> modifiedSpecificLine){
        boolean needCancel = false;
        boolean found = false;
        double minusAmount = 0;

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
            modifiedSpecificLine.add(element[0]+"\t"+super.getFormattedOrderDate()+"\t"+super.getFormattedOrderTime()+"\tRM"+String.format("%.2f", totalAmount));

            // move ovr data to get(0)
            String ovrData = modifiedSpecificLine.get(modifiedSpecificLine.size()-1);
            String replaceValue = modifiedSpecificLine.get(0);
            String nextContent = "";
            for(int noLineContent = 1;noLineContent < requireModifiedContent.size();noLineContent++){ // last line is replace to one

                if(noLineContent != (requireModifiedContent.size()-1)){    
                    nextContent = modifiedSpecificLine.get(noLineContent);
                    modifiedSpecificLine.set(noLineContent, replaceValue);
                    replaceValue = nextContent;
                }else{
                    modifiedSpecificLine.set(0, ovrData);
                }
            }
         }
        return found;
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
            idNum = Integer.parseInt(tempStore.get(noLine-1).substring(2,6));

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
}