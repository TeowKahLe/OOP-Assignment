//import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Staff extends Person{
    private String jobRole;

    public Staff(){

    }

    public Staff(String jobRole){
        this.jobRole = jobRole;
    }

    public String getJobRole(){
        return jobRole;
    }

    public void setJobRole(String jobRole){
        this.jobRole = jobRole;
    }

    public void SignUp(){
        try {
            File staffInfoFile = new File("staffInfo.txt");
            if(staffInfoFile.createNewFile()){
                System.out.println(staffInfoFile.getName() + " created");
            }else{
                System.out.println(staffInfoFile.getName() + " exist");
            }
        } catch (IOException e) {
            System.out.println("File unable to create.");
        }

        try {
            FileWriter writer1 = new FileWriter("staffInfo.txt"); 
            writer1.write("Hello World\n");
            writer1.close();
        } catch (Exception e) {
            System.out.println("Text unable to store in file.");
        }

    }
}