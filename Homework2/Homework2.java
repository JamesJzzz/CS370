
/*
*  Zhiyuan(James) Zhang, Francesca Bueti, Alexander Vallorosi 
*  I pledge my honor that I have abided by the Stevens Honor System.
*/


public class AmicableNumbers{

	public static int sum_devisors(int number) {
        if ((number == 1) || (number == 2)) {
            return 1;
        }
        int sum = 0;
        int k = (int) Math.sqrt(number);
        for (int i = 2; i <= k; i++) {
            if (number % i == 0) {
                sum += i;
                sum += (number / i);
            }
        }
        if (k * k == number) {
            sum += k;
        }
        return (sum + 1);
    }


	public static void main(String[] args){
        long startTime = System.currentTimeMillis(); 
        int sumofall = 0; 
        int onenum = 0;
        int secondnum = 0;
        
        for (int j = 1; j < 100000; j++) {
           
            onenum = sum_devisors(j);

            if (((sum_devisors(onenum)) == j) && (j != onenum)) {
                if(j < onenum){
                    secondnum = onenum;
                System.out.println("(" + j + ", " + secondnum + ")");
                sumofall += j;
                sumofall += secondnum;
            }
            }
        }
        System.out.println("Sum: "+ sumofall);
        long estimatedTime = System.currentTimeMillis() - startTime; //calculates time
        System.out.println("Elapsed time: " + estimatedTime + " ms");
    }
}