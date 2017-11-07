package test;

import java.util.Vector;

/*******************************************************************************
 * Name        : AmicableNumbers.java
 * Author      : Weizhe Dou, Nicholas Tang Mifsud, Ankur Ramesh
 * Version     : 1.0
 * Date        : Feb 5, 2017
 * Pledge	    : We pledge our honor that we have abided by the Stevens Honor Systems
 * Description : Solution to Project Euler #21
 ******************************************************************************/
public class AmicableNumbers {
	
	//Checks what numbers divide evenly by numbers and adds them to sum
	
	private static int sumProperDivisors(int num) {
		int sum = 1;
		for (int i = 2; i <= Math.sqrt(num); i++) {
			if (num % i == 0) {
				sum += i + (num / i);
			}
		}
		return sum;
	}
	public static void main(String[] args) {
		//Starts logging the time 
		long start = System.nanoTime();
		int num = 4, num2, sum = 0;
		Vector<Integer> numbers = new Vector<Integer>();

		//Check which numbers are amicable numbers under 100,000 and returns the sum of all of them
		while(num < 100000){
			num2 = sumProperDivisors(num);
			if(sumProperDivisors(num2) == num  && num != num2 && !(numbers.contains(num))&& !(numbers.contains(num2))){
				sum += num + num2;
				numbers.add(num);
				numbers.add(num2);
				if(num < num2){
					System.out.println("(" + num +", " + num2 + ")");
				}
				else{
					System.out.println("(" + num2 +", " + num + ")");
				}
			}
			num++; 
		}
		System.out.println("Sum: " + sum);
		
		//Print out time used
		double elapsed = (System.nanoTime() - start) / 1e6;
        System.out.println("Elapsed time: " + elapsed + " ms");
		
	}
}
