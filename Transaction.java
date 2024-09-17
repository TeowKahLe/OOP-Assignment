public class Transaction{
    private double subtotal;
    private double ovrTotal; //plus all subtotal


    public double calcSubTotal(double unitAmount,int qty){
        subtotal = unitAmount * qty;
        return subtotal;
    }

    double calTotalAmount(double[] subAmount){
        ovrTotal = 0;
        for (double sub : subAmount) {
            ovrTotal += sub;   
        }
        return ovrTotal;
    }
}