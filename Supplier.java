import java.util.List;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.io.File;
import java.util.Scanner;

public class Supplier extends Person {
    private List<Item> supplyItem = new ArrayList<>();
    private String category;
    private LocalDateTime[] schedule = new LocalDateTime[5];
    private Date contractExpiryDate;
    private static String[] deletedSupplierID = new String[100];

    Alignment line = new Alignment();

    Supplier(){

    }

    Supplier(String id,String name,String phoneNo,String email,String address,List<Item> supplyItem){
        super(id,name,phoneNo,email,address);
        this.supplyItem = supplyItem;
    }

    Supplier(List<Item> supplyItem,String category,LocalDateTime[] schedule,Date contractExpiryDate){
        this.supplyItem =supplyItem;
        this.category = category;
        this.schedule = schedule;
        this.contractExpiryDate = contractExpiryDate;
    }

    //setter and getter -----------------------------------------------------------------------------------------------------
    public void setSupplyItem(List<Item> supplyItem){
        this.supplyItem = supplyItem;
    }

    public List<Item> getSupplyItem(){
        return supplyItem;
    }

    public void category(String category){
        this.category = category;
    }

    public String category(){
        return category;
    }

    
    public void setSchedule(LocalDateTime[] schedule){
        this.schedule = schedule;
    }

    public LocalDateTime[] getSchedule(){
        return schedule;
    }
    
    public void setContractExpiryDate(Date contractExpiryDate){
        this.contractExpiryDate = contractExpiryDate;
    }

    public Date getContractExpiryDate(){
        return contractExpiryDate;
    }

    //manage delete supplier ID -----------------------------------------------------------------------------------------------------------------
    // if the supplier is over the number limit eg 999 it will reuse the deleted ID
    public static void retrieveDeleteIDToArray(){
        try (Scanner scanner = new Scanner(new File("deletedSupplierID.txt"))){
            int i = 0;
            while(scanner.hasNextLine()){
                deletedSupplierID[i] = scanner.nextLine();
                i++;
            }
        } catch (IOException e) {
            System.out.println("deletedSupplierID.txt unable to open");
        }
    }

    public static String[] getDeletedSupplierID(){
        retrieveDeleteIDToArray();
        return deletedSupplierID;
    }

    public static int calcContentDeletedIDArray(){
        int contentElement = 0;
        retrieveDeleteIDToArray();
        for(String element:deletedSupplierID){
            if(element!=null){
                contentElement++;
            }else{
                break;
            }
        }
        return contentElement;
    }

    //Override method ----------------------------------------------------------------------------------
    @Override
    public long generateIdNum(String fileName){
        long noLineU = 0;
        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName)); // read all the line and store each line str in in List
            for(int i = 0;i<lines.size();i++){
                if(lines.get(i).startsWith("U")){
                    noLineU++;
                }
            }
            return noLineU;// find got how many line have start with U
        } catch (IOException e) {
            return 0L; // return default long value; L indicate num in long type
        }
    }

    //Add supplier---------------------------------------------------------------------------------------------
    public void register(){
        boolean positiveIntLower5 = false;
        int noSupplyItem = 0;

        System.out.println("ADD SUPPLIER");
        line.printLine("ADD SUPPLIER".length());
        super.enterNewUserInfo();

        while(positiveIntLower5 == false){
            try {
                System.out.print("Enter the number of items supply >> ");
                noSupplyItem = scanner.nextInt();
                scanner.nextLine();
                if(noSupplyItem >= 1 && noSupplyItem <= 5){
                    positiveIntLower5 = true;
                }else if(noSupplyItem > 5){
                    System.out.println("Each supplier allow handle max 5 items.");
                }else{
                    System.out.println("Only allow positive integers.");    
                }   
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Only allow positive integers.");
            }
        }

        setId('U', "supplierInfo.txt",calcContentDeletedIDArray());

        displayItem();

        for(int i=0;i< noSupplyItem;i++){
            enterSupplyItem();
        }

        storeSupplierData(noSupplyItem);
    }

    //Delete supplier------------------------------------------------------------------------------------------------
    public void deleteSupplier(){
        String supplierFilePath = "supplierInfo.txt";
        boolean found = false;
        
        System.out.println("DELETE SUPPLIER");
        line.printLine("DELETE SUPPLIER".length());

        displaySupplierList();

        while(found == false){
            System.out.print("Enter the Supplier ID that need to DELETE (Enter X or x to QUIT): ");
            String tempID = scanner.nextLine();

            if(tempID.length() > 1){
                tempID = tempID.substring(0, 1).toUpperCase() + tempID.substring(1);
             }
             
             if(tempID.equals("X")||tempID.equals("x")){
                break;
             }

            try(Scanner scanner = new Scanner(new File(supplierFilePath))) {
                while(scanner.hasNextLine()){
                    String lineContent = scanner.nextLine();
                    if(lineContent != null){
                        String[] tokenContents = lineContent.split("\\t");
                        if(tempID.equals(tokenContents[0])){
                            found = true;
                            findDeleteRecord(tempID,supplierFilePath);
                            displaySupplierList();
                            System.out.println(tempID + " data is deleted.");
                            saveDeletedSupplierID(tempID);
                            break;
                    }
                }
            }

                if(!found){
                    System.out.println("ID does not found.");
                }

            } catch (IOException e) {
                System.out.println(supplierFilePath + " unable to open");
            }

        }
    }

    public void findDeleteRecord(String startDeleteID,String supplierFilePath){
        String endDeleteID = startDeleteID.charAt(0) + String.format("%03d", (Integer.parseInt(startDeleteID.substring(1)))+1);
        try {
            List<String> tempLines = Files.readAllLines(Paths.get(supplierFilePath)); //current store;store all line content in temp
            List<String> updatedLines = new ArrayList<>(); // after delete store; for update txt file
            boolean skip = false; //to skip the deleted contents for storing

            for(String lineContent: tempLines){
                if(lineContent.startsWith(startDeleteID)){
                    skip = true;
                }

                if(lineContent.startsWith(endDeleteID)){
                    skip = false;
                }

                if(!skip){
                    updatedLines.add(lineContent);
                }

                FileWriter writer = new FileWriter(supplierFilePath);
                for(String lineContents: updatedLines){
                    writer.write(lineContents + "\n");
                }
                writer.close();
            }
            
            
        } catch (IOException e) {
            System.out.println(supplierFilePath + " unable to open");
        }
        
    }

    public void saveDeletedSupplierID(String deletedID){
        try {
            FileWriter writer = new FileWriter("deletedSupplierID.txt",true);
            writer.write(deletedID + "\n");
            writer.close();
        } catch (Exception e) {
            System.out.println("deletedSupplierID.txt unable to open");
        }
        
    }

    //Modify supplier detail-----------------------------------------------------------------------------------------------

    public void modifySupplier(){
        String supplierFilePath = "supplierInfo.txt";
        boolean found = false;
        boolean isOpt = false;

        //modify NAME, PHONE NO,EMAIL,ADDRESS,ITEM LIST

        String[] supplierInfo = new String[100];
        String[][] supplyItemInfo = new String[100][5];

        System.out.println("MODIFY SUPPLIER DETAIL");
        line.printLine("MODIFY SUPPLIER DETAIL".length());
        
        // select modify what detail
        System.out.println("1.Name\n2.Phone number\n3.Email\n4.Address\n5.Supply Item List");

        int opt = 0;
        while(!isOpt){
            try {
                System.out.print("Enter option: ");
                opt = scanner.nextInt();
                scanner.nextLine();

                if(opt >=1 && opt <=5){
                    isOpt = true;
                }else{
                    System.out.println("Only allow positive integers between(1-5)");
                }

            } catch (Exception e) {
                scanner.nextLine(); // due to opt that line error direct go catch so nextInt need clearInputBuffer
                System.out.println("Only allow positive integers between(1-5)");
            }
            
        }
        
        displaySupplierList();
        while(!found){    
            System.out.print("Enter the Supplier ID that need to MODIFY (Enter X or x to QUIT): ");
            String tempID = scanner.nextLine();

            if(tempID.length() > 1){
                tempID = tempID.substring(0, 1).toUpperCase() + tempID.substring(1);
             }
             
             if(tempID.equals("X")||tempID.equals("x")){
                break;
             }
    
            try (Scanner scanner = new Scanner(new File(supplierFilePath))){
                while(scanner.hasNextLine()){
                    String lineContent = scanner.nextLine();
                    if(lineContent != null){
                        String[] tokenContents = lineContent.split("\\t");
                        if(tempID.equals(tokenContents[0])){
                            found = true;
                            extractIntoArr(supplierInfo,supplyItemInfo,supplierFilePath);
                                                       
                            String detailModify = "";
                            switch(opt){
                                case 1:
                                    detailModify = "name";
                                    replaceNewData(supplierInfo, supplyItemInfo, tempID, opt,enterName());//supplierInfoArr,SupplyItemArr,check which want modify,change what, what value
                                    break;
                                case 2:
                                    detailModify = "phone number";
                                    replaceNewData(supplierInfo, supplyItemInfo, tempID, opt,enterContactNo());
                                    break;
                                case 3:
                                    detailModify = "email";
                                    replaceNewData(supplierInfo, supplyItemInfo, tempID, opt,enterEmail());
                                    break;
                                case 4:
                                    detailModify = "address";
                                    replaceNewData(supplierInfo, supplyItemInfo, tempID, opt,enterAddress());
                                    break;
                                case 5:
                                    detailModify = "Item List";
                                    enterReplaceItemList(supplierInfo,supplyItemInfo,tempID);
                                    break;
                                default:
                                    System.out.println("Invalid option");
                                    break;
                            }
                                modifyFile(supplierInfo,supplyItemInfo,supplierFilePath);//update file with modify value
                                displaySupplierList();
                                System.out.println(detailModify + " has been updated.");
                        }
                    }
                }

                if(!found){
                    System.out.println("ID does not found.");
                }

            } catch (IOException e) {
                System.out.println(supplierFilePath + " unable to open");
            }
        }
    }

    public void replaceNewData(String[] supplierInfo,String[][] supplyItemInfo,String tempID,int opt,String newName){

        for(int index = 0; index < supplierInfo.length;index++){
            String[] tokenSupplierInfo = supplierInfo[index].split("\\t");

            if(tempID.equals(tokenSupplierInfo[0])){
                tokenSupplierInfo[opt] = newName;

                supplierInfo[index] = ""; // make lineContent empty first so it can store the updated data without duplicate info
                for(int tokenNum = 0 ;tokenNum < tokenSupplierInfo.length;tokenNum++){
                    //put all the data with a update data into a lineContent
                    if(tokenNum == tokenSupplierInfo.length-1){
                        supplierInfo[index] += tokenSupplierInfo[tokenNum];    
                    }else{
                        supplierInfo[index] += tokenSupplierInfo[tokenNum] + "\t";
                    }
                }
                    

                break;     
            }
        }
    }

    public void enterReplaceItemList(String[] supplierInfo,String[][] supplyItemInfo,String tempID){
        boolean positiveIntLower5 = false;
        int noSupplyItem = 0;

        while(positiveIntLower5 == false){
            try {
                System.out.print("Enter the number of items supply >> ");
                noSupplyItem = scanner.nextInt();
                scanner.nextLine();
                if(noSupplyItem >= 1 && noSupplyItem <= 5){
                    positiveIntLower5 = true;
                }else if(noSupplyItem > 5){
                    System.out.println("Each supplier allow handle max 5 items.");
                }else{
                    System.out.println("Only allow positive integers.");    
                }   
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Only allow positive integers.");
            }
        }

        displayItem();
        //find the index need modify first
        int indexNeedModify = 0;
        for(int index = 0; index < supplierInfo.length;index++){
            String[] tokenSupplierInfo = supplierInfo[index].split("\\t");
            if(tempID.equals(tokenSupplierInfo[0])){
                indexNeedModify = index;
                break;
            }
        }    

        for(int item = 0; item < supplyItemInfo[indexNeedModify].length;item++){
            if(supplyItemInfo[indexNeedModify][item] != null){
                supplyItemInfo[indexNeedModify][item] = null; //clear all item list that need to modify EG if (orginal got 2 then modify to 1) if not clear, it just replace first, second still old value NOT null
            }
        }
        
        for(int item = 0;item < noSupplyItem;item++){
            enterSupplyItem(supplyItemInfo,indexNeedModify,item);
        }
    }

    public void modifyFile(String[] supplierInfo,String[][] supplyItemInfo,String filePath){
        try (FileWriter writer = new FileWriter(filePath)){
            for(int index =0; index < supplierInfo.length;index++){
                if(supplierInfo[index] != null){
                    writer.write(supplierInfo[index] + "\n");

                    for(int item = 0; item<supplyItemInfo[index].length;item++){
                        if(supplyItemInfo[index][item] != null){
                            writer.write(supplyItemInfo[index][item] + "\n");
                        }else{
                            break;
                        }
                    }
                }else{
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(filePath + "unable to open");
        }

    }

    public void extractIntoArr(String[] supplierInfo,String[][] supplyItemInfo,String filePath){
        try (Scanner scanner = new Scanner(new File(filePath))){
            int index = 0, item = 0;
            while(scanner.hasNextLine()){
                String lineContent = scanner.nextLine();

                if(lineContent.startsWith("U")){
                    item = 0; // execute this section mean NEW supplier so the itemArr will start from 0
                    index++;
                    supplierInfo[index-1] = lineContent; 
                }else if(lineContent.startsWith("Item")){
                    supplyItemInfo[index-1][item] = lineContent;
                    item++;
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println(filePath + " unable to open");
        }
    }
    //Enter supply item ---------------------------------------------------------------------------------------------------
    //for add
    public void enterSupplyItem(){
        String []tokenContents;
        boolean found = false;

        String itemFilePath = "itemInfo.txt";
        //String supplierFilePath = "supplierInfo.txt";

        String tempItemName;

        while(found == false){
            System.out.print("Enter Item Name: ");
            tempItemName = scanner.nextLine();

            try (Scanner scanner = new Scanner(new File(itemFilePath))){
                while(scanner.hasNextLine()){ // read the content line by line until EOF
                    String lineContent = scanner.nextLine();

                    if(lineContent != null){ 
                        tokenContents = lineContent.split("\\|");

                       String upperTempItemName = tempItemName.toUpperCase();
                        String upperStoredItemName = tokenContents[1].toUpperCase();

                        if(upperTempItemName.equals(upperStoredItemName)){
                           //0 - itemID , 1 - itemName , 2 - itemCategory , 3 - itemDesc , 4 - UnitCost , 5 - UnitPrice
                           Item itemSupply = new Item(tokenContents[0],tokenContents[1],tokenContents[2],tokenContents[3],Double.parseDouble(tokenContents[4]),Double.parseDouble(tokenContents[5])); 

                           supplyItem.add(itemSupply);
                           found = true;
                           break;
                        }
                    }
                }
                if(!found){
                    System.out.println("Item does not founded");
                }
            } catch (Exception e) {
                 System.out.println(itemFilePath + " unable to open");
            }
        }

          // Find the Item ID from text file then retrieve all data in Item List
    }


    //for modify
    public void enterSupplyItem(String[][] supplyItemInfo,int indexNeedModify,int item){
        String[] tokenContents;
        boolean found = false;

        String itemFilePath = "itemInfo.txt";

        String tempItemName;

        while(found == false){
            System.out.print("Enter Item Name: ");
            tempItemName = scanner.nextLine();

            try (Scanner scanner = new Scanner(new File(itemFilePath))){
                while(scanner.hasNextLine()){ // read the content line by line until EOF
                    String lineContent = scanner.nextLine();

                    if(lineContent != null){ 
                        tokenContents = lineContent.split("\\|");

                       String upperTempItemName = tempItemName.toUpperCase();
                        String upperStoredItemName = tokenContents[1].toUpperCase();

                        if(upperTempItemName.equals(upperStoredItemName)){
                           //0 - itemID , 1 - itemName , 2 - itemCategory , 3 - itemDesc , 4 - UnitCost , 5 - UnitPrice
                           supplyItemInfo[indexNeedModify][item] = "";
                           supplyItemInfo[indexNeedModify][item] = "Item[" + (item+1) + "]: " + tokenContents[0] + "\t" + tokenContents[1] + "\t" + tokenContents[2] + "\t" + tokenContents[3] + "\t" + tokenContents[4] + "\t" + tokenContents[5];
                           found = true;
                           break;
                        }
                    }
                }
                if(!found){
                    System.out.println("Item does not founded");
                }
            } catch (Exception e) {
                 System.out.println(itemFilePath + " unable to open");
            }
        }
          // Find the Item ID from text file then retrieve all data in Item List
    }

    //display supplier list --------------------------------------------------------------------------------------
    public void displaySupplierList(){
        String supplierFilePath = "supplierInfo.txt";
        int noSupplyItem = 0;
        boolean ignoreNewLine = true;

        try (Scanner scanner = new Scanner(new File(supplierFilePath))){
            System.out.println("SUPPLIER LIST");
            line.printLine("SUPPLIER LIST".length());
            
            line.printLine(95);
            System.out.printf("|%-10s %-20s %-20s %-20s %-20s|\n","ID","Name","Phone Number","Gmail","Address");

            while(scanner.hasNextLine()){
                String lineContent = scanner.nextLine();
                //U001	Jackson	012-1234567	lo@gmail.com	jalan pisang
                String[] elementContent = lineContent.split("\\t"); 
                if(lineContent.startsWith("U")){
                    noSupplyItem = 0;
                    if(!ignoreNewLine){
                        System.out.println("\n");
                    }
                    ignoreNewLine = false;
                    line.printLine(95);
                    System.out.printf("|%-10s %-20s %-20s %-20s %-20s|\n",elementContent[0],elementContent[1],elementContent[2],elementContent[3],elementContent[4]);
                    line.printLine(95);
                }else{
                    if(noSupplyItem == 0){
                        System.out.print("Supplier Item: ");
                    }
                    noSupplyItem++;
                    System.out.printf("%d%s %-15s",noSupplyItem , ")" , elementContent[1]);
                    
                }
            }
            if(!ignoreNewLine){
                System.out.println("\n");
            }
        } catch (IOException e) {
            System.out.println(supplierFilePath + " unable to open.");
        }
    }

    //store in supplierInfo.txt------------------------------------------------------------------------------------------------------
    public void storeSupplierData(int noSupplyItem){
        try {
            FileWriter writer = new FileWriter("supplierInfo.txt",true); 
            writer.write(super.getId()+"\t"+super.getName()+"\t"+super.getContactNo()+"\t"+super.getEmail()+"\t"+super.getAddress()+"\n");
            for(int i=0 ; i< noSupplyItem; i++){
                writer.write("Item[" + (i+1) + "]: " + supplyItem.get(i));
            }
            writer.close();
            System.out.println("The data are stored.");
        } catch (IOException e) {
            System.out.println("Text unable to store in file.");
        }
    }

    /*        super(id,name,phoneNo,email,address);
        this.supplyItem = supplyItem; */
    public String toString(){
        return super.getId()+"\t"+super.getName()+"\t"+super.getContactNo()+"\t"+super.getEmail()+"\t"+super.getAddress()+"\t"+ supplyItem + "\n";   
    }

    
    public void displayItem(){
        String itemFilePath = "itemInfo.txt";

        String[] categoryCode = new String[10];
        int storedCodeIndex=0;

        //store category id code first
        try (Scanner scanner = new Scanner(new File(itemFilePath))){

            line.printLine(100);
            System.out.print("ID" + "\t" + String.format("%-20s","Item Name") + "\t" + String.format("%-10s","Category") + "\t" + String.format("%-20s", "Description") + "\t" + String.format("%-7s", "Unit Cost") + "\t" + String.format("%-7s", "Unit Price") + "\n");
            line.printLine(100);

            while(scanner.hasNextLine()){
                String[] elementContent = scanner.nextLine().split("\\|");

                //cmp got same category code
                if(categoryCode[0] == null){
                    categoryCode[0] = elementContent[0].substring(0,2);
                    storedCodeIndex++;
                }else{
                    boolean sameCategory = false;
                    for (String storeCode : categoryCode) {
                        if((elementContent[0].substring(0,2).equals(storeCode))){
                            sameCategory = true;
                            break;
                        }
                    }
                    if(!sameCategory){
                        categoryCode[storedCodeIndex] = elementContent[0].substring(0,2);
                        storedCodeIndex++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(itemFilePath + " unable to open.");
        }

        //display according category id
        for(int index=0;index<storedCodeIndex;index++){
            try (Scanner scanner = new Scanner(new File(itemFilePath))){
                while(scanner.hasNextLine()){
                    String lineContent = scanner.nextLine();
                    if(lineContent.startsWith(categoryCode[index])){
                        String[] elementContent = lineContent.split("\\|");

                        System.out.println(elementContent[0] + "\t" + String.format("%-20s",elementContent[1]) + "\t" + String.format("%-10s", elementContent[2]) + "\t" + String.format("%-20s", elementContent[3]) + "\tRM" + String.format("%5.2f", Double.parseDouble(elementContent[4])) + "\t\tRM" + String.format("%5.2f", Double.parseDouble(elementContent[5])) + "\n");
                    }
                }
            } catch (IOException e) {
                System.out.println(itemFilePath + " unable to open.");
            }
        }
        
        }
}
