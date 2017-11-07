/*
*  Zhiyuan(James) Zhang, Francesca Bueti, Alexander Vallorosi 
*  I pledge my honor that I have abided by the Stevens Honor System.
*/

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileNotFoundException;

public class PuzzleSolver {

	public static class Puzzle{
		public int val;
		public HashMap<Integer, String> pattern = new HashMap<Integer, String>();
		
		// store values as a "puzzle" 
		public Puzzle(int val, String top, String right, String bot, String left){
			this.val = val;
			pattern.put(0, top);
			pattern.put(1, right);
			pattern.put(2, bot);
			pattern.put(3, left);
		}
		
		// basically check the value we need whether it is a square or trangle. 
		// the method we use here is to reverse the 0 or 1 which stand for the trangle or 
		// square, and then see if there is a mathch. 
		// if mathch, then we return the index of where it matches. 
		
		public int checkValue(String str){
			int index = -1;
			str = str.charAt(1) == '0' ? str.replace('0', '1') : str.replace('1', '0');
			for(int i = 0; i < 4; i++){
				if(pattern.get(i).equals(str)){
					index = i;
					break;
				}
			}
			return index;
		}

		// turnning the puzzle pieces, give in how many times we want it to turn,
		// and then using mod to struct the new pattern after the turn.

		public void turnArround(int indexL, int indexU){
			if(indexL == -2 && indexU == -2)
				return;
			
			int times = indexL == -2 ? indexU : indexL + 1;
			String[] rotation = new String[4];
			for(int i = 0; i < 4; i++)
				rotation[i] = pattern.get((i + times) % 4);
			
			for(int i = 0; i < 4; i++)
				pattern.put(i, rotation[i]);
		}
		// turn around rotations.		
		public void turnArround(){
			String[] rotation = new String[4];
			for(int i = 0; i < 4; i++)
				rotation[i] = pattern.get((i + 1) % 4);
			
			for(int i = 0; i < 4; i++)
				pattern.put(i, rotation[i]);
		}
	}

	static HashMap<List<Integer>, Integer> map = new HashMap<List<Integer>, Integer>();
	public static void main(String[] args){
		long startTime = System.currentTimeMillis();
		Puzzle[] puzzles = new Puzzle[9];
		try{
			// read in input from txt file
			// split it by "," and then read in as each element of one puzzle piece.
			FileReader text = new FileReader("input.txt");
			BufferedReader txt = new BufferedReader(text);
			System.out.println("Input tiles:");
			for (int i = 0; i < 9; i++){
				String line = txt.readLine();
				String[] parts = line.split(",");
				puzzles[i] = new Puzzle(i+1, parts[0],parts[1],parts[2],parts[3]);
				//int a = Integer.parseInt(parts[0]);
				System.out.println(i+1 + ". <" + parts[0]+", " + parts[1] + ", " + parts[2] + 
					", " + parts[3] + ">" );

			}
			System.out.print('\n');

		}

		// if file can't be found then return exception.
		catch( FileNotFoundException filenotfoundexcption )
           {
             System.out.println( "can't find input.txt" );
           }
          
        catch( IOException ioexception )
           {
             ioexception.printStackTrace( );
           }
		/*Puzzle[] puzzles = new Puzzle[9];
		puzzles[0] = new Puzzle(1, "G0", "R0","B1", "Y1");
		puzzles[1] = new Puzzle(2, "Y0", "B1","R1", "B0");
		puzzles[2] = new Puzzle(3, "Y1", "R1","G0", "B0");
		puzzles[3] = new Puzzle(4, "Y1", "G0","B0", "G1");
		puzzles[4] = new Puzzle(5, "B0", "G0","R1", "Y1");
		puzzles[5] = new Puzzle(6, "Y0", "G1","R1", "B0");
		puzzles[6] = new Puzzle(7, "R0", "B1","Y1", "G0");
		puzzles[7] = new Puzzle(8, "Y0", "R1","B1", "G0");
		puzzles[8] = new Puzzle(9, "R0", "G1","Y1", "B0");
		*/


		boolean[] used = new boolean[puzzles.length];
		List<List<List<String>>> res = new ArrayList<List<List<String>>>();
		int count = 0;
		for(int i = 0; i < puzzles.length; i++){
			while(count < 4){
				used[i] = true;
				ArrayList<Puzzle> list = new ArrayList<Puzzle>();
				list.add(puzzles[i]);
				placePuzzles(res, list, puzzles, 1, used, false);
				puzzles[i].turnArround();
				count++;
			}
			used[i] = false;
			count = 0;
		}
		getUniquePuzzles(res);
		// test time. 
		long estimatedTime = System.currentTimeMillis() - startTime; //calculates time
        System.out.println("Elapsed time: " + estimatedTime + " ms");
	}
	

	public static boolean puzzleExists(ArrayList<Puzzle> list){
		List<Integer> res = new ArrayList<Integer>();
		for(int i = 0; i < list.size(); i++){
			if(i % 2 == 0)
				res.add(list.get(i).val);
		}
		Collections.sort(res);
		if(!map.containsKey(res)){
			map.put(res, list.get(0).val);
			return false;
		}
		return true;

	}
	
	// the algorithm that try to put puzzle piece in the correct location, 
	// if not, then we start with the lowest value of the puzzle from 1 to 9.
	// when there is no solution with the current set, then we remove the last one and back track.


	public static void placePuzzles(List<List<List<String>>> res, ArrayList<Puzzle> list, Puzzle[] puzzle, int i, boolean[] used, boolean canBePlaced){
		if(list.size() == 9){
			if(!puzzleExists(list)){
				List<List<String>> result = new ArrayList<List<String>>();
				for(int k = 0; k < list.size(); k++){
					List<String> newList = new ArrayList<String>();
					newList.add(String.valueOf(list.get(k).val));
					newList.add(list.get(k).pattern.get(0));
					newList.add(list.get(k).pattern.get(1));
					newList.add(list.get(k).pattern.get(2));
					newList.add(list.get(k).pattern.get(3));
					result.add(newList);
				}
				res.add(result);
			}
		}

		for(int n = 0; n < puzzle.length; n++){
			if(!used[n]){
				int matchedIdL = -2, matchedIdU = -2;
				if (i % 3 != 0) {
					String str = list.get(i - 1).pattern.get(1);
					matchedIdL = puzzle[n].checkValue(str);
				}

				if (i >= 3) {
					String str = list.get(i - 3).pattern.get(2);
					matchedIdU = puzzle[n].checkValue(str);
				}

				if (matchedIdL != -1 && matchedIdU != -1) {
					puzzle[n].turnArround(matchedIdL, matchedIdU);
					if (i >= 3) {
						String strU = list.get(i - 3).pattern.get(2);
						matchedIdU = puzzle[n].checkValue(strU);
					}
					if (matchedIdU == 0 || matchedIdU == -2)
						canBePlaced = true;
				}

				if(canBePlaced){
					used[n] = true;
					list.add(puzzle[n]);
					placePuzzles(res, list, puzzle, i + 1, used, false); 
					used[n] = false;
					canBePlaced = false;
					list.remove(list.size() - 1);
				}
			}
		}
	}
	
	// prints out unique solutions in specified ascii-art format

	public static void getUniquePuzzles(List<List<List<String>>> res){

		System.out.println(res.size() + " unique solutions found:");
		

		for(int i = 0; i < res.size(); i++){
			for(int rowIndex = 0; rowIndex < 13; rowIndex++){
				if(rowIndex % 4 == 0)
					System.out.println("+--------+--------+--------+");
				else{
					for(int colIndex = 0; colIndex < 28; colIndex++){
						if(colIndex % 9 == 0 && colIndex != 27)
							System.out.print("|");
						else if(colIndex == 27)
							System.out.println("|");
						else{
							if(rowIndex % 4 == 1){
								if(colIndex % 9 == 1){
									String num = res.get(i).get(3*(rowIndex/4) + colIndex/9).get(0);
									System.out.print(num);
								}
								else if(colIndex % 9 == 4){
									String top = res.get(i).get(3*(rowIndex/4) + colIndex/9).get(1);
									System.out.print(top);
									colIndex++;
								}
								else
									System.out.print(" ");
							}
							else if(rowIndex % 4 == 2){
								if(colIndex % 9 == 1){
									String left = res.get(i).get(3*(rowIndex/4) + colIndex/9).get(4);
									System.out.print(left);
									colIndex++;
								}
								else if(colIndex % 9 == 7){
									String right = res.get(i).get(3*(rowIndex/4) + colIndex/9).get(2);
									System.out.print(right);
									colIndex++;
								}
								else
									System.out.print(" ");
							}
							else if(rowIndex % 4 == 3){
								if(colIndex % 9 == 4){
									String bot = res.get(i).get(3*(rowIndex/4) + colIndex/9).get(3);
									System.out.print(bot);
									colIndex++;
								}
								else
									System.out.print(" ");
							}
						}
					}
				}
			}
		}
	}
}
	
