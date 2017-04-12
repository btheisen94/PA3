package PA3;
import java.util.ArrayList;



public class DynamicProgramming {

	public DynamicProgramming(){
		
	}
	

	public static ArrayList<Integer> minCostVC(int[][] M){
		//If M has n rows, then return list has 2n entries
		//Must be iterative, not recursive, or use memoization
		ArrayList<Integer> ret = new ArrayList<Integer>();
		int currentCost = 0; //Used for the dynamice programming paradigm
							 //Holds the current cost so that The entire cost
							 //does not need to be computed each time
		
		int n = M.length; //Holds the number of rows in the matrix
		int m = M[0].length; //Holds the number of columns in the matrix
		int least = 0;
		int j = 0;
		for(int i = 0; i < n; i++){
			if(i == 0){
				ret.add(i);
				j = minCost(i,m,currentCost, M);
			} else {
				
			}
			
		}
		return ret;
	}
	
	private static int minCost(int i, int j, int currentCost, int[][] M){
		int least = 0;
		int index = 0;
		if(i == 0){
			
			for(int k = 0; k < M[i].length; k++){
				if(k == 0){
					least = M[i][k];
					index = k;
				} else {
					if(M[i][k] < least){
						least = M[i][k];
						index = k;
					}
				}
			}
		} else {
			int first = M[i][j-1];
			int second = M[i][j];
			int third = M[i][j+1];
			if(first < second && first < third){
				index = j-1;
			} else if(second < first && second < third){
				index = j;
			} else if()
		}
		return index;
	}
	
	public static String stringAlignment(String x, String y){
		
		
		return "";
	}
}
