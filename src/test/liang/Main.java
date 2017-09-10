package test.liang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String str = null;
		try {
			while (true) {
				String sss =br.readLine();
				if(sss == null){
					break;
				}
				TreeSet<Integer> hs = new TreeSet<>();
				for(int i = 0;i<2;i++){
					str = br.readLine().trim();
					String [] strs = str.split(" ");
					for(String s : strs){
						hs.add(Integer.valueOf(s));
					}
				}
				int ii = 0;
				for(int i : hs){
					if(ii++ == hs.size()-1){
						System.out.println(i);
						break;
					}					
					System.out.print(i+" ");
					//44444444444444444444444
				}
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
	
}
