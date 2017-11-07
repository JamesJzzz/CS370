import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;




public class Puzzle_Solver {

	public static class Puzzle{
		public int val;
		public HashMap<Integer, String> pattern = new HashMap<Integer, String>();
		
		public Puzzle(int val, String top, String right, String bot, String left){
			this.val = val;
			pattern.put(0, top);
			pattern.put(1, right);
			pattern.put(2, bot);
			pattern.put(3, left);
		}
		
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
		Puzzle[] puzzles = new Puzzle[9];
		puzzles[0] = new Puzzle(1, "G0", "R0","B1", "Y1");
		puzzles[1] = new Puzzle(2, "Y0", "B1","R1", "B0");
		puzzles[2] = new Puzzle(3, "Y1", "R1","G0", "B0");
		puzzles[3] = new Puzzle(4, "Y1", "G0","B0", "G1");
		puzzles[4] = new Puzzle(5, "B0", "G0","R1", "Y1");
		puzzles[5] = new Puzzle(6, "Y0", "G1","R1", "B0");
		puzzles[6] = new Puzzle(7, "R0", "B1","Y1", "G0");
		puzzles[7] = new Puzzle(8, "Y0", "R1","B1", "G0");
		puzzles[8] = new Puzzle(9, "R0", "G1","Y1", "B0");
		List<List<Puzzle>> res = new ArrayList<List<Puzzle>>();
		boolean[] used = new boolean[puzzles.length];
		
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
	
	public static void placePuzzles(List<List<Puzzle>> res, ArrayList<Puzzle> list, Puzzle[] puzzle, int i, boolean[] used, boolean canBePlaced){
		if(list.size() == 9){
			if(!puzzleExists(list))
				res.add(new ArrayList<Puzzle>(list));
		}
		
		for(int n = 0; n < 9; n++){
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
	
	public static void getUniquePuzzles(List<List<Puzzle>> res){
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
									int num = res.get(i).get(3*(rowIndex/4) + colIndex/9).val;
									System.out.print(num);
								}
								else if(colIndex % 9 == 4){
									String top = res.get(i).get(3*(rowIndex/4) + colIndex/9).pattern.get(0);
									System.out.print(top);
									colIndex++;
								}
								else
									System.out.print(" ");
							}
							else if(rowIndex % 4 == 2){
								if(colIndex % 9 == 1){
									String left = res.get(i).get(3*(rowIndex/4) + colIndex/9).pattern.get(3);
									System.out.print(left);
									colIndex++;
								}
								else if(colIndex % 9 == 7){
									String right = res.get(i).get(3*(rowIndex/4) + colIndex/9).pattern.get(1);
									System.out.print(right);
									colIndex++;
								}
								else
									System.out.print(" ");
							}
							else if(rowIndex % 4 == 3){
								if(colIndex % 9 == 4){
									String bot = res.get(i).get(3*(rowIndex/4) + colIndex/9).pattern.get(2);
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
	
