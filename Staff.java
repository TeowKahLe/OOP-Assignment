import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Staff extends Person{
    Scanner scanner = new Scanner(System.in);
    private String jobRole,password;
    Line line = new Line();


    public Staff(){
        
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

    public void signUp(){
        String tempName="",tempContactNo="",tempEmail="",tempAddress,tempJobRole,tempPassword;
        boolean matchFormat = false;

        //sign up page header
        System.out.println("\nSign Up");
        line.printLine("Sign Up".length());
        
        //enter and validate NAME
        while(matchFormat == false){
            System.out.print(String.format("%-34s","Enter your NAME") + " >> ");
            tempName = scanner.nextLine();
            matchFormat = super.checkFormatName(tempName);
        }

        super.setName(tempName);

        matchFormat = false; //reset matchFormat

        //enter and validate CONTACT NO
        while(matchFormat == false){
            System.out.print(String.format("%-35s","\nEnter your CONTACT NUMBER") + " >> ");
            tempContactNo = scanner.nextLine();
            matchFormat = super.checkFormatContact(tempContactNo);
        }

        matchFormat = false; //reset matchFormat

        super.setContactNo(tempContactNo);

        //enter and validate EMAIL
        while(matchFormat == false){
            System.out.print(String.format("%-35s","\nEnter your EMAIL address") + " >> ");
            tempEmail = scanner.nextLine();
            matchFormat = super.checkFormatEmail(tempEmail);
        }

        super.setEmail(tempEmail);

        //enter ADDRESS
        System.out.print(String.format("%-35s","\nEnter your HOME address") + " >> ");
        tempAddress = scanner.nextLine();

        super.setAddress(tempAddress);

        //enter JOB ROLE
        System.out.print(String.format("%-35s","\nEnter your JOB position") + " >> ");
        tempJobRole = scanner.nextLine();

        setJobRole(tempJobRole);

        //enter PASSWORD
        System.out.print(String.format("%-35s","\nEnter your PASSWORD") + " >> ");
        tempPassword = scanner.nextLine();
        
        setPassword(tempPassword);


        setId('S',"staffInfo.txt");

        //print staff ID card ------------------------------------------------------------------------------------------
        System.out.println();
        line.printLine(25);
		System.out.println(String.format("%-24s","|STAFF ID CARD") + "|");
        line.printLine(25);
        System.out.println(String.format("%-24s",String.format("%-10s", "|Staff ID ")+ ": " + getId()) + "|");
        System.out.println(String.format("%-24s",String.format("%-10s", "|Name ")+ ": " + getName()) + "|");
        System.out.println(String.format("%-24s",String.format("%-10s", "|Job Role ")+ ": " + getJobRole()) + "|");
        line.printLine(25);
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

    public void login(){
        String tempID="",tempPassword="";

        String filePath = "staffInfo.txt";
        boolean idFound = false;
        boolean pairPassword = false;
        
        //login page header----------------------------------------------------------
        System.out.println("Login");
        line.printLine("Login".length());

        
        do{
            boolean matchFormat = false;
            
            while(matchFormat == false){
                System.out.print(String.format("%-34s","Enter Staff ID(Enter X QUIT):") + " >> ");
                tempID = scanner.nextLine();
                if(tempID.equals("X")){
                    System.out.println("HALO");
                    break;
                }
                matchFormat = super.checkFormatID(tempID);
            }

            if(tempID.equals("X")){
                String[] emptyArr ={};
                fastFoodInventory.main(emptyArr);
            }

             System.out.print(String.format("%-35s","\nEnter Password:") + " >> ");
                tempPassword = scanner.nextLine();

           try (Scanner scanner = new Scanner(new File(filePath))){
                while(scanner.hasNextLine()){ // read the content line by line until EOF
                    String lineContent = scanner.nextLine();
                
                    if(lineContent != null){
                        String[] tokenContent = lineContent.split("\t");
                        
                        if(tempID.equals(tokenContent[0]) && tempPassword.equals(tokenContent[tokenContent.length-1])){ // ID founded
                            System.out.println("Login Successful");
                            idFound = true;
                            pairPassword = true;
                            break;

                        }else if(tempID.equals(tokenContent[0]) && !tempPassword.equals(tokenContent[tokenContent.length-1])){// founded ID but incorrect password
                            idFound = true;
                            pairPassword = false;
                            break;

                        }else{
                            // want to display the error message about ID so make pairPassword true to hidden error message about password
                             idFound = false; 
                            pairPassword = true;
                        }
                    
                }
            }

            if(!idFound){
                System.out.println("Staff ID not found.\n");
            }

            if(!pairPassword){
                System.out.println("Incorrect Password.\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace(); // print error message
        }
    }while(!idFound || !pairPassword);
    }

}