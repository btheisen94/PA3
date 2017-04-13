package PA3;

import java.util.ArrayList;

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

		int[][] temp = M;
		int n = M.length; // Holds the number of rows in the matrix
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
		System.out.println(lowest);
		// We are forming the vertical cut from the bottom up, so the
		// indices need to be added to the ArrayList at the head of the
		// list in order to have the right order

		ret.add(0, lowestIndex);
		ret.add(0, n - 1);
		int j = lowestIndex;
		// Trace back lowest cost path getting the indices of the path
		for (int i = n - 1; i > 0; i--) {
			if ((j - 1) < 0) {
				// Upper left is out of bounds
				min = Math.min(temp[i - 1][j], temp[i - 1][j + 1]);
				if (min == temp[i - 1][j]) {
					ret.add(0, j);
					ret.add(0, i - 1);

				} else {
					j = j + 1;
					ret.add(0, j);
					ret.add(0, i - 1);

				}

			} else if ((j + 1) == m) {
				// Upper right is out of bounds
				min = Math.min(temp[i - 1][j], temp[i - 1][j - 1]);
				if (min == temp[i - 1][j]) {
					ret.add(0, j);
					ret.add(0, i - 1);

				} else {
					j = j - 1;
					ret.add(0, j);
					ret.add(0, i - 1);

				}

			} else {
				// All options above are valid
				min = Math.min(temp[i - 1][j - 1], Math.min(temp[i - 1][j], temp[i - 1][j + 1]));
				if (min == temp[i - 1][j - 1]) {
					j = j - 1;
					ret.add(0, j);
					ret.add(0, i - 1);

				} else if (min == temp[i - 1][j + 1]) {
					j = j + 1;
					ret.add(0, j);
					ret.add(0, i - 1);

				} else {
					ret.add(0, j);
					ret.add(0, i - 1);

				}
			}

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

	/**
	 * Function to find the optimal allignment of strings x and y
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public static String stringAlignment(String x, String y) {
		int n = x.length();
		int m = y.length();
		int lowestCost = 0;
		int lowestCostIndex = 0;
		String temp = y;
		if (n == m) {
			// If the strings are the same length, then the allignment will
			// never be better than it is.
			return y;
		} else {
			for (int i = 0; i < n - m; i++) {
				for (int j = 0; j <= y.length(); j++) {
					temp = y;
					
					temp = temp.substring(0, j) + "$" + temp.substring(j, temp.length());
					System.out.println(temp);
					
					int cost = 0;
					for (int k = 0; k < temp.length(); k++) {
						cost += penalty(x.charAt(k), temp.charAt(k));
					}
					if (j == 0) {
						lowestCost = cost;
						lowestCostIndex = j;
					} else if (cost < lowestCost) {
						lowestCost = cost;
						lowestCostIndex = j;
					}
				}
				y = y.substring(0, lowestCostIndex) + "$" + y.substring(lowestCostIndex, y.length());

			}
			
		}
		
		System.out.println(lowestCost);
		return y;
	}

	private static int penalty(char a, char b) {
		if (a == b) {
			return 0;
		} else if ((a == '$') || (b == '$')) {
			return 4;
		} else {
			return 2;
		}
	}

	public static void main(String args[]) {

		int[][] test = { { 7, 3, 6, 2, 3 }, { 9, 7, 3, 6, 7 }, { 11, 4, 8, 9, 10 } };

		ArrayList<Integer> testList = new ArrayList<Integer>();
		testList = minCostVC(test);
		System.out.println(testList.toString());
		System.out.println(stringAlignment("xcdlxa","cla"));
	}
}
