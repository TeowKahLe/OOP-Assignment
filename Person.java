import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

public class Person {
    private String id,name,contactNo,email,address;
    Scanner scanner = new Scanner(System.in);
    
    Pattern namePattern = Pattern.compile("[a-zA-Z\s]+");
    //'+' mean it check all char ensure it is alphabet.NOT'+' jst check first char
    //to check the name is all alphabet. 

    Pattern contactNoPattern = Pattern.compile("01\\d{1}-\\d{7,8}"); 
    //compile = create pattern "\\d" first \ escape character to use actual backslash character else it will consider as '\' char

    Pattern emailPattern = Pattern.compile(".+@.+\\.com"); //. mean any characaters

    Pattern idPattern = Pattern.compile("S(00[1-9]|0[1-9][0-9]|[1-9][0-9]{2})");

    public Person(){
        
    }

    public Person(String id,String name,String contactNo,String email,String address){
            this.id = id;
            this.name = name;
            this.contactNo = contactNo;
            this.email = email;
            this.address = address;
    }

    public String getId(){ 
        return this.id;
    }

    //staff
    public void setId(char firstCharId,String fileName){
        this.id = firstCharId + String.format("%03d",generateIdNum(fileName)+1);
    }

    //supplier
    public void setId(char firstCharId,String fileName,int deletedSupplierIdQty){
        this.id = firstCharId + String.format("%03d",generateIdNum(fileName)+1+deletedSupplierIdQty);
    }

    //NAME---------------------------------------------------------------------------------------------
    public String getName(){
        return this.name;
    }

    public boolean checkFormatName(String name){
        Matcher nameMatcher = namePattern.matcher(name);
        if (nameMatcher.matches()){ //compare name variable with namePattern if(same pattern) 
            return true;
        }else{
            System.out.println("Name just allow ALPHABET only.");
            return false;
        }
    }

    public void setName(String name){
        boolean matchFormat = checkFormatName(name);
        if (matchFormat == true){ 
            this.name = name;
        }
    }
    
    //CONTACT NO------------------------------------------------------------------------------
    public String getContactNo(){
        return this.contactNo;
    }

    public boolean checkFormatContact(String contactNo){
        Matcher contactNoMatcher = contactNoPattern.matcher(contactNo);
        if (contactNoMatcher.matches()){
            return true;
        }else{
            System.out.println("Please follow the FORMAT of contact number EXAMPLE:(012-3456789)");
            return false;
        }
    }

    public void setContactNo(String contactNo){
        boolean matchFormat = checkFormatContact(contactNo);
        if(matchFormat == true){ 
            this.contactNo = contactNo;
        }

    }

    //EMAIL---------------------------------------------------------------------------------------------------
    public String getEmail(){
        return this.email;
    }

    public boolean checkFormatEmail(String email){
        Matcher emailMatcher = emailPattern.matcher(email);
        if(emailMatcher.matches()){ 
            return true;
        }else{
            System.out.println("Please follow the FORMAT of email EXAMPLE(abc@gmail.com)");
            return false;
        }
    }

    public void setEmail(String email){
        boolean matchFormat = checkFormatEmail(email);
        if(matchFormat == true){ 
            this.email = email;
        }

    }

    //ADDRESS--------------------------------------------------------------------------------------
    public String getAddress(){
        return this.address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    //ID Num-----------------------------------------------------------------------------------------------
    public long generateIdNum(String fileName){
                            //location of the file
        try {   
            long row = Files.lines(Paths.get(fileName)).count(); //count the text file got how many row
            return row;
        } catch (IOException e) {
            return 0L; // return default long value; L indicate num in long type
        }
    }

    //ENTER NEW USER INFO----------------------------------------------------------------------------------------
    public void enterNewUserInfo(){
        boolean matchFormat = false;
        String tempName="",tempContactNo="",tempEmail="",tempAddress;
        //enter and validate NAME
        while(matchFormat == false){
            System.out.print(String.format("%-34s","Enter NAME") + " >> ");
            tempName = scanner.nextLine();
            matchFormat = checkFormatName(tempName);
        }

        setName(tempName);

        matchFormat = false; //reset matchFormat

        //enter and validate CONTACT NO
        while(matchFormat == false){
            System.out.print(String.format("%-35s","\nEnter CONTACT NUMBER") + " >> ");
            tempContactNo = scanner.nextLine();
            matchFormat = checkFormatContact(tempContactNo);
        }

        matchFormat = false; //reset matchFormat

        setContactNo(tempContactNo);

        //enter and validate EMAIL
        while(matchFormat == false){
            System.out.print(String.format("%-35s","\nEnter EMAIL address") + " >> ");
            tempEmail = scanner.nextLine();
            matchFormat = checkFormatEmail(tempEmail);
        }

        setEmail(tempEmail);

        //enter ADDRESS
        System.out.print(String.format("%-35s","\nEnter HOME address") + " >> ");
        tempAddress = scanner.nextLine();
        
        setAddress(tempAddress);
    }

    //Validate----------------------------------------------------------------------------------------------
    public boolean checkFormatID(String id){
        Matcher idMatcher = idPattern.matcher(id);
        if(idMatcher.matches()){ 
            return true;
        }else{
            System.out.println("Please follow the FORMAT of ID EXAMPLE(SXXX))");
            return false;
        }
    }
}
