/*
*  Zhiyuan(James) Zhang, Francesca Bueti, Alexander Vallorosi 
*  I pledge my honor that I have abided by the Stevens Honor System.
*/

public class LargestPalindromeProduct{
	// helper function to findAnswer(), returned true if input is a palindrome and false if not
	public static boolean palindrome(char[] word){
    	int i = 0;
    	int j = word.length - 1;
    	while ( j> i) {
       		if (word[i] != word[j]) {	//if it is not a palindrome
            	return false;
        	}	
        		++i;
        		--j;
    	}
    	return true;
	}
	
	// returns the largest palindrome made from multiplying two three-digit numbers and the two three-digit numbers
	public static int[] findanswer(){
		int[] answer;
		answer = new int[3];
		answer[0] = 0;
		answer[1] = 0;
		answer[2] = 0;
		for (int i = 999; i >= 100; i--){
			for (int j = 999; j >= 100; j--){
				char[] result = ("" + (i*j)).toCharArray();
				if(palindrome(result)){
					if((i*j)>= answer[2]){
						answer[2] = i*j;
						answer[0] = i;
						answer[1] = j;
					}
				}
			}
		}
		return answer;
	}
		

	public static void main(String[] args){
		long startTime = System.currentTimeMillis(); //measures elapsed time
		System.out.println(findanswer()[0] + " x " + findanswer()[1] + " = " + findanswer()[2]); //prints answer
		long estimatedTime = System.currentTimeMillis() - startTime; //calculates time
		System.out.println("Elapsed time: " + estimatedTime + " ms"); //prints time
	}
}