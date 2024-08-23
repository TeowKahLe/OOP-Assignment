import java.util.Scanner;
//import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Staff extends Person{
    Scanner scanner = new Scanner(System.in);
    private String jobRole,password;
    Line line = new Line();


    public Staff(){
        SignUp();
    }

    public Staff(String id,String name,String contactNo,String email,String address,String jobRole,String password){
        super(id,name,contactNo,email,address);
        this.jobRole = jobRole;
        this.password = password;
    }

    public String getJobRole(){
        return jobRole;
    }

    public void setJobRole(String jobRole){
        this.jobRole = jobRole;
    }

    public String getPassword(){
        return jobRole;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void SignUp(){
        String tempName="",tempContactNo="",tempEmail="",tempAddress,tempJobRole,tempPassword;
        boolean matchFormat = false;

        //sign up page header
        System.out.println("Sign Up");
        line.printLine("Sign Up".length());
        
        //enter and validate NAME
        while(matchFormat == false){
            System.out.print("\nEnter your NAME >> ");
            tempName = scanner.nextLine();
            matchFormat = super.checkFormatName(tempName);
        }

        super.setName(tempName);

        matchFormat = false; //reset matchFormat

        //enter and validate CONTACT NO
        while(matchFormat == false){
            System.out.print("\nEnter your CONTACT NUMBER  >> ");
            tempContactNo = scanner.nextLine();
            matchFormat = super.checkFormatContact(tempContactNo);
        }

        matchFormat = false; //reset matchFormat

        super.setContactNo(tempContactNo);

        //enter and validate EMAIL
        while(matchFormat == false){
            System.out.print("\nEnter your EMAIL address  >> ");
            tempEmail = scanner.nextLine();
            matchFormat = super.checkFormatEmail(tempEmail);
        }

        super.setEmail(tempEmail);

        //enter ADDRESS
        System.out.print("\nEnter your HOME address >> ");
        tempAddress = scanner.nextLine();

        super.setAddress(tempAddress);

        //enter JOB ROLE
        System.out.print("\nEnter your JOB position >> ");
        tempJobRole = scanner.nextLine();

        setJobRole(tempJobRole);

        //enter PASSWORD
        System.out.print("\nEnter your PASSWORD >> ");
        tempPassword = scanner.nextLine();
        
        setPassword(tempPassword);
    }

    public void storeData(){
        //create or open file
        /*try {
            File staffInfoFile = new File("staffInfo.txt");
            if(staffInfoFile.createNewFile()){
                System.out.println(staffInfoFile.getName() + " created");
            }else{
                System.out.println(staffInfoFile.getName() + " exist");
            }
        } catch (IOException e) {
            System.out.println("File unable to create.");
        }*/

        //write content in file    
        try {
            FileWriter writer1 = new FileWriter("staffInfo.txt",true); 
            writer1.write(super.getId()+"\t"+super.getName()+"\t"+super.getContactNo()+"\t"+super.getEmail()+"\t"+super.getAddress()+"\t"+ jobRole +"\t"+ password+"\n");
            writer1.close();
            System.out.println("The data are stored.");
        } catch (IOException e) {
            System.out.println("Text unable to store in file.");
        }

    }

}