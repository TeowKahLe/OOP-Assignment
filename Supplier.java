import java.util.List;
import java.util.ArrayList;
//import java.io.FileWriter;
//import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

public class Supplier extends Person {
    private List<Item> supplyItem = new ArrayList<>();
    private String category;
    private LocalDateTime[] schedule = new LocalDateTime[5];
    private Date contractExpiryDate;

    Line line = new Line();

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

    public void addSupplier(){
        System.out.println("ADD SUPPLIER");
        line.printLine("ADD SUPPLIER".length());
        super.enterNewUserInfo();

    }

    public void deleteSupplier(){

    }

    public void modifySupplier(){
        
    }

    public void enterSupplyItem(){
        //String tempItemId, tempItemName, tempItemCategory, tempItemDesc;
        //double tempUnitCost, tempUnitPrice;

        System.out.println("Enter Item ID: ");
        //tempItemId = scanner.nextLine();

        // Find the Item ID from text file then retrieve all data in Item List
    }

    public void displaySupplyList(){
        // show all the item that incharge by a supplier
    }

    public void viewSchedule(){
        // show the schedule that delivery to invetory
    }


}
