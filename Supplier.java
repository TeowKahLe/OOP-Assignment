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
    private static String[] deletedSupplierID = new String[999];

    Alignment line = new Alignment();

    Supplier(){

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

    //manage delete supplier ID------------------------------------------------------------------------------------------------------------------------
    // if the supplier is over the number limit eg 999 it will reuse the deleted ID
    public static void retrieveDeleteIDToArray(){
        try (Scanner scanner = new Scanner(new File("deletedSupplierID.txt"))){
            int i = 0;
            while(scanner.hasNextLine()){
                deletedSupplierID[i] = scanner.nextLine();
                i++;
            }
        } catch (IOException e) {
            System.err.println("deletedSupplierID.txt unable to open");
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
    public void addSupplier(){
        boolean positiveInt = false;
        int noSupplyItem = 0;

        System.out.println("ADD SUPPLIER");
        line.printLine("ADD SUPPLIER".length());
        super.enterNewUserInfo();

        while(positiveInt == false){
            try {
                System.out.print("Enter the number of items supply >> ");
                noSupplyItem = scanner.nextInt();
                scanner.nextLine();
                if(noSupplyItem >= 1){
                    positiveInt = true;
                }else{
                    System.out.println("Only allow positive integers.");    
                }   
            } catch (Exception e) {
                System.out.println("Only allow positive integers.");
            }
        }

        setId('U', "supplierInfo.txt",calcContentDeletedIDArray());

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

        while(found == false){
            System.out.print("Enter the Supplier ID that need to MODIFY: ");
            String tempID = scanner.nextLine();

            try(Scanner scanner = new Scanner(new File(supplierFilePath))) {
                while(scanner.hasNextLine()){
                    String lineContent = scanner.nextLine();
                    if(lineContent != null){
                        String[] tokenContents = lineContent.split("\\t");
                        if(tempID.equals(tokenContents[0])){
                            found = true;
                            findDeleteRecord(tempID,supplierFilePath);
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

    public void modifySupplier(){
        
    }

    //Enter supply item ---------------------------------------------------------------------------------------------------
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



    public void displaySupplyList(){
        // show all the item that incharge by a supplier
    }

    public void viewSchedule(){
        // show the schedule that delivery to invetory
    }

}
