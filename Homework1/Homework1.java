/*
* Zhiyuan(James) Zhang, Alexander Vallorosi, Francisca Bueti
* 
* I pledge my honor that I have abided by the Stevens Honor System.
*
*
*/

public class Homework1{
	public static boolean palindrome(char[] word){
    	int i = 0;
    	int j = word.length - 1;
    	while ( j> i) {
       		if (word[i] != word[j]) {
            	return false;
        	}	
        		++i;
        		--j;
    	}
    	return true;
	}
	public static int findanswer(){
		int answer = 0;
		for (int i = 999; i >= 100; i--){
			for (int j = 999; j >= 100; j--){
				char[] result = ("" + (i*j)).toCharArray();
				if(palindrome(result)){
					if((i*j)>= answer){
						answer = i*j;
						System.out.println(i + "*" + j + "= ");
					}
				}
			}
		}

		return answer;
	}
		

	public static void main(String[] args){
		int res = findanswer();
		System.out.println("The answer is : ");
		System.out.println(res);
	}
}