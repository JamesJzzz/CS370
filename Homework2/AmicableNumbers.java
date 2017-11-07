/*
*  Zhiyuan(James) Zhang, Francesca Bueti, Alexander Vallorosi 
*  I pledge my honor that I have abided by the Stevens Honor System.
*/

public class AmicableNumbers {
	// finds the sum of every factor of a given number
	public static int factorSum(int num){
		  int sum = 1;
		  for (int i = 2; i <= Math.sqrt(num); i++) {
			  if (num % i == 0){
				  sum += i + num/i;
			  }
		  }
	return sum;
	}
	
	// determines whether a given number has an amicable pair
	public static boolean isAmicable(int num){
		  int x = factorSum(num);
		  if (x != num && factorSum(x) == num){
			  if(x<num){
				  System.out.println("(" + x + ", " + num + ")");				  
			  }
			  return true;
		  }
		  return false;
	}

	// iterates through every number from 1 to 100000 and if it's amicable, adds it to sum
	public static void main(String[] args){
		long startTime = System.currentTimeMillis(); //measures elapsed time
		  int sum = 0;
		  for (int i = 1; i < 100000; i++) {
		   if (isAmicable(i))
		    sum += i;
		  }
		System.out.println("\nSum: " + sum); //prints sum
		long estimatedTime = System.currentTimeMillis() - startTime; //calculates time elapsed
		System.out.println("Elapsed time: " + estimatedTime + " ms"); //prints time
	}
}