import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Person {
    private String id,name,contactNo,email,address;
    
    Pattern namePattern = Pattern.compile("[a-zA-Z]+");
    //'+' mean it check all char ensure it is alphabet.NOT'+' jst check first char
    //to check the name is all alphabet. 

    Pattern contactNoPattern = Pattern.compile("01\\d{1}-\\d{7,8}"); 
    //compile = create pattern "\\d" first \ escape character to use actual backslash character else it will consider as '\' char

    Pattern emailPattern = Pattern.compile(".+@.+\\.com"); //. mean any characaters

    public Person(){
        
    }

    public Person(String id,String name,String contactNo,String email,String address){
            this.id = id;
            this.name = name;
            this.contactNo = contactNo;
            this.email = email;
            this.address = address;
    }

    public String getId(){ //ID unable to set bcz ID is auto generated
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        Matcher nameMatcher = namePattern.matcher(name);
        if (nameMatcher.matches()){ //compare name variable with namePattern if(same pattern) 
            this.name = name;
        }else{
            System.out.println("Name just allow ALPHABET only.");
        }
        
    }
    
    public String getContactNo(){
        return this.contactNo;
    }

    public void setContactNo(String contactNo){
        Matcher contactNoMatcher = contactNoPattern.matcher(contactNo);
        if(contactNoMatcher.matches()){ 
            this.contactNo = contactNo; //true statement
        }else{
            System.out.println("Please follow the FORMAT of contact number EXAMPLE:(012-3456789)");
        }

    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        Matcher emailMatcher = emailPattern.matcher(email);
        if(emailMatcher.matches()){ 
            this.email = email;
        }else{
            System.out.println("Please follow the FORMAT of email EXAMPLE(abc@gmail.com)");
        }

    }

    public String getAddress(){
        return this.address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String toString(){
        return id + name + contactNo + email + address;
    }
}
