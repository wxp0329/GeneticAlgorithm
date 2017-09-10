package test.liang;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
//		System.out.println(pipei(" stg","s?g"));
		delDulp(new StringBuilder("ccbbbdddcc"));
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

	}
	//src=" string" target="s*g"
	public static boolean pipei(String src,String target){
		
		Pattern p = Pattern.compile(target);
		 Matcher m = p.matcher(src);
		 boolean b = m.find();
		return b;
	}
}
