public class Transaction{
    private double subtotal;
    private double ovrTotal; //plus all subtotal


    public double calcSubTotal(double unitAmount,int qty){
        subtotal = unitAmount * qty;
        return subtotal;
    }

    public double calTotalAmount(double[] subAmount){
        for (double sub : subAmount) {
            ovrTotal += sub;   
        }
        return ovrTotal;
    }
}