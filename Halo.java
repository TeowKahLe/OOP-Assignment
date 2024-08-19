public static void main(String[] args){
    System.out.println("Halo!!!!");

import java.util.Scanner;

public class Halo{
	
    public static void main(String[] args) {
    	int sum1=0,sum2=0;
    	
    	System.out.print("Enter 8 digit Credit card number: ");
    	Scanner scanner = new Scanner(System.in);
    	int cardNum = scanner.nextInt();
    	scanner.nextLine();
    	String cardNumStr = Integer.toString(cardNum);
    	
    	for(int position=0;position<cardNumStr.length();position++){
    		char digitChar = cardNumStr.charAt(position);
    		int digit = Character.getNumericValue(digitChar);
    		if(position%2!=0){
    			sum1 += digit;
    		}else{
    			int doubleDigit = digit*2;
    			int digit1 = doubleDigit/10;
    			int digit2 = doubleDigit%10;
    			sum2 += (digit1+digit2);
    		}
    	}
    	System.out.println("SUM1="+sum1+ "\tSUM2="+sum2);
    	if ((sum1+sum2) %10 == 0){
    		System.out.println("Valid");
    	}else{
    		System.out.println("Invalid");
    	}
    }
}