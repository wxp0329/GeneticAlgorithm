package test.nqueen;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {

	public static void main(String[] args) {
		int[][] a=new int[][]{{1,2,3},{11,22,33},{4,5,6},{44,55,66}};
		int[][] aa = Arrays.copyOfRange(a, 2, 4);
		int[][] bb = Arrays.copyOfRange(a, 0, 2);
		ArrayList<int[]> al = new ArrayList<>();
		for(int[] sub : aa){
			al.add(sub);
		}
		for(int[] sub : bb){
			al.add(sub);
		}
		int[][] aw=new int[4][];
		al.toArray(aw);
		System.out.println(aw);
	}

}
