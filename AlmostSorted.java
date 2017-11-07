/*
*  Zhiyuan(James) Zhang, Francesca Bueti, Alexander Vallorosi 
*  I pledge my honor that I have abided by the Stevens Honor System.
*  Apr, 26, 2017
*/

import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;
import java.util.Arrays;
import java.util.Scanner;

public class AlmostSorted{

	// check whether the array is sorted, all element should less than the one after it.
	public static boolean isSorted(int[] arr) {
		boolean bool = true;
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i+1]) {
                bool = false;
            }
        }
        return bool;
    }

    //we are going return a message later.

    public static String info = "";

    //check if we need to reverse. 

	public static boolean reverse(int[] arr){
		Integer head = null; 
		Integer tail = null;
		// find the first thing that are not in sorted order.

		for(int i = 0; i < arr.length - 1; i++){
			if(arr[i] > arr[i + 1]){
				if(head == null){
					head = i;
				}
			}
			if(head != null && arr[i] < arr[i + 1]){
				tail = i + 1;
				// since we have tail and head, so break.
				break;
			}
		}
		// sort that specific part.
		Arrays.sort(arr, head, tail);
		//if it is sorted, then we are good to go.
		boolean bool = isSorted(arr);
		if(bool){
			//preset the message for print statement.
			info = "reverse " + (head+1) + " " + tail;
		}

		return bool;
	}

	//swap two elements in the array.
	//credit to TA Matt Crepea.
	public static void swapint(int[] arr, int a, int b){
			int x = arr[a];
			arr[a] = arr[b];
			arr[b] = x;

		}

	// if we only need to swap two elements instead of reversing stuff
	public static boolean swap(int[] arr){
		Integer first = null;
		Integer second = null;

		for(int i = 0; i< arr.length -1; i++){
			if(arr[i]>arr[i+1]){
				//only give the value for "first"at there,
				if(first ==null){
					first = i;

				}
				//if find the pair, then give value for second. 
				//if they are next to each other, then it wont give a value
				else if(second == null){
					second = i+1;
				}
				//else it doesnt work with swap. 
				else{
					return false;
				}
			}
		}

		//when we have first and second
		if(second != null && first != null){
			//int swap = arr[first];
            //arr[first] = arr[second];
            //arr[second] = swap;
            swapint(arr, first, second);
		}
		//this means that the two element we need to swap is next to each other. 
		//as the problem states, in this case, we swap instead of reverse the two.
		//by default, we swap the first and the one after first.
		else if(first!= null && second == null){
            swapint(arr, first, first+1);
		}
		//if they are sorted.
		boolean bool = isSorted(arr);
		if(bool){
			if(second != null){
				//change the print out statement info.
				info = "swap " + (first +1) + " "+(second+1);
			}
			else{
				info = "swap " + (first +1) + " " + (first +1 +1);
			}
		}
		return bool;
	}

	public static void main(String[] args){
		Scanner scan = new Scanner(System.in);
		int arrayLength = scan.nextInt();
		int arr[] = new int[arrayLength];
		for(int i = 0; i<arrayLength; i++){
			arr[i] = scan.nextInt();
		}
		// since we need to check whether we can do swap or reverse.
		// then we need two same array in order to do so.
		// one use for swap, one use for reverse.
		int[] array =  arr.clone();
		// already sorted, cant do anything.
		if(isSorted(arr)){
			System.out.print("yes");
		}

		else if (swap(arr) || reverse(array)) {
			System.out.println("yes");
			System.out.println(info);
		}
		//cant sort in one step, then its over, we cant do anything.
		else{
			System.out.println("no");
		}
	}
}
