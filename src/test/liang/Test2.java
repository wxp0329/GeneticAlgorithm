package test.liang;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test2 {

	public static void main(String[] args) {
		char[] inputs = "db".toCharArray();
		String src = "cddbbc";
		String[][] sum = new String[inputs.length][inputs.length];
		for (int i = 0; i < inputs.length; i++) {
			char[] copy = new String(inputs).toCharArray();
			StringBuilder sb1 = new StringBuilder(src);
			int index1 = sb1.indexOf(copy[i] + "");
			if (index1 == -1) {
				sb1.insert(0, copy[i]);
			} else {
				sb1.insert(index1, copy[i]);
			}
			delDulp(sb1);
			sum[i][0] = sb1.toString();
			System.out.println(sum[i][0]);
			for (int j = 1; j < inputs.length; j++) {
				if ((j == i)) {
					// sb1 = new StringBuilder(sum[i][j-1]);
					sum[i][j] = sb1.toString();

					continue;
				}
				int index = sb1.indexOf(copy[j] + "");
				if (index == -1) {
					sum[i][j] = sb1.toString();

					continue;
				} else {
					sb1.insert(index, copy[j]);
				}
				delDulp(sb1);

				if (sb1.length() == 0) {
					break;
				} else {
					sum[i][j] = sb1.toString();
				}
			}
		}
		for (int i = 0; i < inputs.length; i++) {
			for (int j = 0; j < inputs.length; j++) {
				System.out.print(sum[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void delDulp(StringBuilder src) {
		String s = src.toString();
		Pattern p = Pattern.compile("(.)\\1+");
		Matcher m = p.matcher(s);
		while (m.find()) {
				String catchStr = m.group();
				if (catchStr.length() >= 3) {
					System.out.println(catchStr);
					int start = src.indexOf(catchStr);
					src.delete(start, start + catchStr.length());
				}
			 
		}
		String s1 = src.toString();
		Pattern p1 = Pattern.compile("(.)\\1+");
		Matcher m1 = p.matcher(s1);
		while (m1.find()) {
				String catchStr = m1.group();
				if (catchStr.length() >= 3) {
					System.out.println(catchStr);
					int start = src.indexOf(catchStr);
					src.delete(start, start + catchStr.length());
				}
			 
		}

	}
}
