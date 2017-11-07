
/*
*  Zhiyuan(James) Zhang, Francesca Bueti, Alexander Vallorosi 
*  I pledge my honor that I have abided by the Stevens Honor System.
*/

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PathSumThreeWays {
    //add a pathSums so we can use it whenever we want
    // and the minimum too
    private int[][] values, pathSums;
    private int minimum;

    public PathSumThreeWays(String filename) throws FileNotFoundException,
            IOException,
            NumberFormatException {
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        List<String> valueList = new ArrayList<String>();
        String line;
        while ((line = reader.readLine()) != null) {
            valueList.add(line);
        }
        reader.close();
        values = new int[valueList.size()][];
        for (int row = 0; row < values.length; row++) {
            line = valueList.get(row);
            String[] parts = line.split(",");
            values[row] = new int[parts.length];
            for (int col = 0; col < parts.length; col++) {
                try {
                    values[row][col] = Integer.parseInt(parts[col]);
                } catch (NumberFormatException nfe) {
                    throw new NumberFormatException("Bad value " + parts[col]
                            + " on line " + (row + 1) + ".");
                }
            }
        }
    }

    public int solver() {
        // the solver function does all the comparison and output a pathSums table.
        // the minimum is the result we want
        pathSums = new int[values.length][values[0].length];
        
        for(int i = 0; i < values[0].length; i++) {
            for(int j = 0; j < values.length; j++) {
                pathSums[j][i] = values[j][i];
                if(i>=1 && i<values[j].length && j>=1 &&j<values.length) {
                    pathSums[j][i] += Math.min(pathSums[j][i-1], pathSums[j-1][i]);
                }else if(i >=1){
                    if(j>1 && j<values.length){
                        pathSums[j][i] += pathSums[j-1][i];
                    }
                    else if( i< values[j].length){
                        pathSums[j][i] += pathSums[j][i-1];
                    }
                }
            }
            for(int j = values.length-1; j>= 0; j--){
                if(j<values.length-1){
                    pathSums[j][i] = Math.min(values[j][i]+pathSums[j+1][i], pathSums[j][i]);
                }
            }
        }
        minimum = pathSums[0][values[0].length -1];
        for (int x = 0; x< values.length; x++){
            minimum = Math.min(pathSums[x][values[0].length - 1], minimum);
        }
        
        //displayTable(pathSums);
        //commented out
        return minimum;
    }

    /**
     * Displays a two-dimensional array of integers on the screen, nicely
     * formatted to the width of the widest cell.
     * @param table a two-dimensional array of integers
     */

    // this is given 

    public void displayTable(int[][] table) {
        int m = table[0].length,
            n = table.length,
            maxCellWidth = numDigits(Math.max(Math.max(m, n), getMax(table))),
            maxRowWidth = numDigits(m);
        for (int i = 0, len = numDigits(n); i < len; i++) {
            System.out.print(" ");
        }
        for (int col = 0; col < m; col++) {
            System.out.print(" ");
            int cellLength = numDigits(col);
            for (int i = maxCellWidth - cellLength; i > 0; i--) {
                System.out.print(" ");
            }
            System.out.print(col);
        }
        System.out.println();
        for (int row = 0; row < n; row++) {
            int cellLength = numDigits(row);
            for (int i = maxRowWidth - cellLength; i > 0; i--) {
                System.out.print(" ");
            }
            System.out.print(row);
            for (int col = 0; col < m; col++) {
                cellLength = numDigits(table[row][col]);
                for (int i = maxCellWidth - cellLength; i > 0; i--) {
                    System.out.print(" ");
                }
                System.out.print(" " + table[row][col]);
            }
            System.out.println();
        }
    }
    
    /**
     * Backtracks over the cost table to determine the integers that comprise
     * the minimum sum, starting in the top left and ending in the bottom right
     * cell.
     * @return an array of integers that comprise the minimum sum.
     */
    public int[] getSolution() {
        int mini = pathSums[0][pathSums[0].length - 1];
        int rownum = 0;
        ArrayList<Integer> solutionList = new ArrayList<Integer>();

        // find the smallest number on the last col,
        // then start from there, back track
        // compare with three directions and get the smallest then keep back tracking. 
        // put all numbers in a array list, then reverse it for output. 

        int row = pathSums.length - 1, col = pathSums[0].length - 1;
        for (row = 0, col = pathSums[0].length - 1; row <=pathSums.length - 1; row++){
            if (mini>pathSums[row][col]){
                mini = pathSums[row][col]; 
                rownum = row;
            }
        }
        while (col > 0) {
            solutionList.add(values[rownum][col]);

            // case 1 top row

            if (rownum == 0) {
                //only at the top row
                //if bot small than left
                if (pathSums[rownum][col-1] > pathSums[rownum+1][col]) {
                    rownum++;
                }
                else
                {
                    col--;
                }
            }

            //case 2 bottom row

            else if(rownum == pathSums.length - 1){
                //last row, at bottom
                //left<top
                if(pathSums[rownum][col-1]> pathSums[rownum-1][col]){
                    rownum--;
                }
                else{
                    col--;
                }
            }
            
            // otherwise we do comparison.

            else if (pathSums[rownum - 1][col] < pathSums[rownum][col - 1] && pathSums[rownum- 1][col]<pathSums[rownum+ 1][col]) {
                    rownum--;
                }
            // bot < left
            // bot < top
            else if (pathSums[rownum+ 1][col] < pathSums[rownum][col - 1] && pathSums[rownum+ 1][col] < pathSums[rownum- 1][col]) {
                    rownum++;
                }
            else
                {
                    col--;
                }
        }
        solutionList.add(values[rownum][0]);
        /*
        while(row > 0 && col>0){
            if (pathSums[row - 1][col] < pathSums[row][col - 1] && pathSums[row - 1][col]<pathSums[row + 1][col]) {
                row--;
            } else if (pathSums[row + 1][col] < pathSums[row][col - 1] && pathSums[row + 1][col] < pathSums[row - 1][col]) {
                row++;
            } else{
                col--;
            }
        }
        while (row > 0 && col > 0) {

        }

        while (row > 0 && col > 0) {
            solutionList.add(values[row][col]);
            if (pathSums[row - 1][col] < pathSums[row][col - 1]) {
                row--;
            } else {
                col--;
            }
        }
        while (row > 0) {
            solutionList.add(values[row][0]);
            row--;
        }
        while (col > 0) {
            solutionList.add(values[0][col]);
            col--;
        }
        solutionList.add(values[0][0]);
        */
        int[] solution = new int[solutionList.size()];
        for (int i = solutionList.size() - 1, j = 0; i >= 0; i--, j++) {
            solution[j] = solutionList.get(i);
        }
        // Sanity check to verify the sum of the numbers found during back-
        // tracking is equal to the minimum sum found in the cost table.
        assert getSum(solution) == minimum;
        return solution;
    }

    public static int numDigits(int num) {
        int count = 1;
        while (num >= 10) {
            num /= 10;
            ++count;
        }
        return count;
    }

    public static int getSum(int[] array) {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    private int getMax(int[][] table) {
        int m = table[0].length, n = table.length, max = Integer.MIN_VALUE;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (table[row][col] > max) {
                    max = table[row][col];
                }
            }
        }
        return max;
    }


    public static void main(String[] args) {

        //returns the time of how long it takes. 

        long startTime = System.currentTimeMillis();
        String filename = "matrix.txt";
        PathSumThreeWays pathSum = null;
        try {
            pathSum = new PathSumThreeWays(filename);
        } catch (FileNotFoundException fnfe) {
            System.err.println("Error: File '" + filename + "' not found.");
            System.exit(1);
        } catch (IOException ioe) {
            System.err.println("Error: Cannot read '" + filename + "'.");
            System.exit(1);
        } catch (NumberFormatException nfe) {
            System.err.println("Error: " + nfe.getMessage());
            System.exit(1);
        }
        System.out.println("Min sum: " + pathSum.solver());
        System.out.println(
            "Values:  " + Arrays.toString(pathSum.getSolution()));
        long estimatedTime = System.currentTimeMillis() - startTime; //calculates time
        System.out.println("Elapsed time: " + estimatedTime + " ms");
        System.exit(0);
    }
}
