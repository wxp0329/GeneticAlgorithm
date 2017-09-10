package test.liang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = null;
		try {
			
			while ((str = br.readLine()) != null) {
				long start = System.currentTimeMillis();
				int num = Integer.valueOf(str.trim());
				ArrayList<Double> al = new ArrayList<>();
				for (int i = 1; i <= num; i++) {
					for (int j = 1; j <= num; j++) {
						al.add(Math.pow(i, j));
					}
				}
//				System.out.println(al);
//				int count = 0;
//				for (double s1 : al) {
//					for (double s2 : al) {
//						if (s1 == s2) {							
//							++count;
//						}
//					}
//				}
//				System.out.println(count);
				long end = System.currentTimeMillis();
				System.out.println("time: "+(end-start));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
