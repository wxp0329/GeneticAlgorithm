package test.liang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main1 {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = null;
		try {
			while ((str = br.readLine().trim()) != null) {
				int num = Integer.valueOf(str);
				ArrayList<Double> al = new ArrayList<>();
				for (int i = 0; i < num; i++) {
					al.add(Double.valueOf(br.readLine().trim()));
				}

				double[][] d = new double[al.size()][al.size()];
				double max = Double.MIN_VALUE;
				for (int i = 0; i < al.size(); i++) {
					for (int j = 0; j < al.size(); j++) {
						double val = al.get(i) - al.get(j);
						d[i][j] = val > 180 ? 360 - val : val;
						if (d[i][j] > max) {
							max = d[i][j];
						}
					}
				}
				System.out.format("%.8f\n", max);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	while ((str = br.readLine()) != null) {
		int num = Integer.valueOf(str.trim());
		ArrayList<Double> al = new ArrayList<>();
		for (int i = 1; i <= num; i++) {
			for (int j = 1; j <= num; j++) {
				al.add(Math.pow(i, j));
			}
		}
		System.out.println(al);
		int count = 0;
		for (Double s1 : al) {
			for (Double s2 : al) {

				if (s1 == s2) {
					count++;
				}
			}
		}
		System.out.println(count);
	}
}
