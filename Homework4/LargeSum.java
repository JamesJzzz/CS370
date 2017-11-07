
/*
*  Zhiyuan(James) Zhang, Francesca Bueti, Alexander Vallorosi 
*  I pledge my honor that I have abided by the Stevens Honor System.
*/


import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;



public class LargeSum{
	public static void main(String[] args){
		long startTime = System.currentTimeMillis();
		int sum = 0;
		int temp = 0;
		int carry = 0;

		int[] res = new int[5000];

		try{
			FileReader text = new FileReader("input.txt");
			BufferedReader txt = new BufferedReader(text);
			String line = txt.readLine();

		
		while (line != null) 
		{
			char[] num = line.toCharArray();
			for (int i = res.length - 1, j = num.length - 1; i >= 0; i--,j--){
				if(j>=0){
					sum = (res[i] + (num[j]-'0') + carry);
					res[i] = sum % 10;
					carry = sum / 10;
				}
				else{
					sum = (res[i] + carry);
					res[i] = sum % 10;
					carry = sum / 10;
					if(carry == 0){
						break;
					}
				}
			}
        line = txt.readLine();
    	}
    	}
    	catch( FileNotFoundException filenotfoundexcption )
           {
             System.out.println( "input.txt does not exist you mofo" );
           }
          
        catch( IOException ioexception )
           {
             ioexception.printStackTrace( );
           }

    	StringBuilder sb = new StringBuilder();
    	int index = 0;
		for(int i = 0; i < res.length; i++){
			if(res[i] != 0){
				index = i;
				break;
			}
		}
		for(;index < res.length; index++){
			sb.append(String.valueOf(res[index]));
		}

		System.out.println("Full sum: " + sb.toString());
		System.out.println("First 10 digits: "+ sb.toString().substring(0,10));
		
		long estimatedTime = System.currentTimeMillis() - startTime; //calculates time
        System.out.println("Elapsed time: " + estimatedTime + " ms");
	}
	
}
