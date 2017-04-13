package PA3;

import java.util.ArrayList;
import java.util.Stack;

public class DynamicProgramming {

	public DynamicProgramming() {

	}

	/**
	 * This function finds the lowest cost vertical cut through a NxM matrix.
	 * 
	 * @param M
	 * @return
	 */
	public static ArrayList<Integer> minCostVC(int[][] M) {
		// If M has n rows, then return list has 2n entries
		// Must be iterative, not recursive, or use memoization
		ArrayList<Integer> ret = new ArrayList<Integer>();
		int currentCost = 0; // Used for the dynamice programming paradigm
								// Holds the current cost so that The entire
								// cost
								// does not need to be computed each time
		int[][] temp = M;
		int n = M.length;    // Holds the number of rows in the matrix
		int m = M[0].length; // Holds the number of columns in the matrix
		int min;

		// Caching the values for least costly vertical cuts in the matrix
		for (int i = 1; i < n; i++) {
			for (int j = 0; j < m; j++) {

				if ((j - 1) < 0) {
					// Upper left is out of bounds
					min = Math.min(temp[i - 1][j], temp[i - 1][j + 1]);

				} else if ((j + 1) == m) {
					// Upper right is out of bounds
					min = Math.min(temp[i - 1][j], temp[i - 1][j - 1]);

				} else {
					// All options above are valid
					min = Math.min(temp[i - 1][j - 1], Math.min(temp[i - 1][j], temp[i - 1][j + 1]));
				}

				temp[i][j] = temp[i][j] + min;
			}
		}

		int lowest = temp[n - 1][0];
		int lowestIndex = 0;
		// Get index of lowest item in the bottom row of the temp matrix
		for (int j = 1; j < m; j++) {
			if (temp[n - 1][j] < lowest) {
				lowestIndex = j;
				lowest = temp[n - 1][j];
			}
		}

		// System.out.println(j);

		// The path will be traced bottom up in the matrix so we will use a
		// stack
		// and then pop off the stack to form the path in the ArrayList
		Stack<Integer> path = new Stack<Integer>();
		path.push(lowestIndex);
		path.push(n - 1);
		int j = lowestIndex;
		// Trace back lowest cost path getting the indices of the path
		for (int i = n - 1; i > 0; i--) {
			if ((j - 1) < 0) {
				// Upper left is out of bounds
				min = Math.min(temp[i - 1][j], temp[i - 1][j + 1]);
				if(min == temp[i-1][j]){
					path.push(j);
					System.out.println("x: " + (i-1) + "y: " + j);
					path.push(i-1);
					//j = j;
				} else {
					path.push(j+1);
					System.out.println("x: " + (i-1) + "y: " + j);
					path.push(i-1);
					j = j + 1;
				}
				
			} else if ((j + 1) == m) {
				// Upper right is out of bounds
				min = Math.min(temp[i - 1][j], temp[i - 1][j - 1]);
				if(min == temp[i-1][j]){
					path.push(j);
					System.out.println("x: " + (i-1) + "y: " + j);
					path.push(i-1);
					//j = j;
				} else {
					path.push(j-1);
					path.push(i-1);
					j = j - 1;
				}
				
			} else {
				// All options above are valid
				min = Math.min(temp[i + 1][j - 1], Math.min(temp[i + 1][j], temp[i + 1][j + 1]));
				if(min == temp[i-1][j-1]){
					path.push(j-1);
					path.push(i-1);
					//j = j;
				} else if(min == temp[i-1][j+1]){
					path.push(j+1);
					path.push(i-1);
					j = j + 1;
				} else {
					path.push(j);
					path.push(i-1);
				}
			}

		}

		// Pop items off of the stack and into the ArrayList to get the vertical
		// cut
		while (!path.isEmpty()) {
			ret.add(path.pop());
		}

		return ret;
	}

	/*	*//**
			 * Helper method to return the minimum of three integers. Used for
			 * the Dynamic Programming in the vertical cut method.
			 * 
			 * Sometime either the first or third integer will be passed in as
			 * (Integer) null, this is because they would otherwise be obtained
			 * from an index out of bounds exception.
			 * 
			 * @param x
			 *            - First integer to be prepared
			 * @param y
			 *            - Second integer to be prepared
			 * @param z
			 *            - Third integer to be prepared
			 * @return - The minimum of the three integers
			 *//*
			 * private static int minThree(int x, int y, int z){
			 * 
			 * if(x == (Integer) null){ return Math.min(y, z);
			 * 
			 * } else if(z == (Integer) null){ return Math.min(x, y);
			 * 
			 * } else { return Math.min(x, Math.min(y, z));
			 * 
			 * } }
			 */

	public static String stringAlignment(String x, String y) {

		return "";
	}

	public static void main(String args[]) {

		int[][] test = { { 1, 3, 1 }, 
						 { 9, 2, 3 }, 
						 { 11, 4, 1 } };

		ArrayList<Integer> testList = new ArrayList<Integer>();
		testList = minCostVC(test);
		System.out.println(testList.toString());
		
	}
}
