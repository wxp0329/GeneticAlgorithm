package test.nqueen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Random;

public class Matrix {
	private int n;

	public Matrix(int n) {
		this.n = n;
	}
public void writeMatrixes(String fileName,int num){
	BufferedWriter bw = null;
	try {
		bw = new BufferedWriter(new FileWriter(new File(fileName)));
		bw.write("queens:"+n);
		bw.newLine();
		bw.write("matrixes:"+num);
		bw.newLine();
		for(int i = 0;i<num;i++){
			IndexMatrix im = createRandomMatrix();
			int[][] mat = im.getM();
			for(int m = 0;m<mat.length;m++){
				for(int n = 0;n<mat.length;n++){
					bw.write(mat[m][n]+" ");
				}
				bw.newLine();
				bw.flush();
			}
			bw.newLine();
		}
		bw.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
	public IndexMatrix createRandomMatrix() {
		int[][] matrix = new int[n][n];
		HashSet<Cell> hs = new HashSet<>();
		Random rnd = new Random();
		while (hs.size() < n) {
			hs.add(new Cell(rnd.nextInt(n), rnd.nextInt(n)));
		}
		for (Cell ind : hs) {
			matrix[ind.row][ind.col] = 1;
		}
		return new IndexMatrix(hs, matrix, 0);
	}

	public static void printMatrix(int[][] m) {
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m.length; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}
	}

	static class Cell {
		private int row;
		private int col;
		private int flag = 0;

		public Cell(int row, int col) {
			super();
			this.row = row;
			this.col = col;
		}

		@Override
		public boolean equals(Object o) {
			if (!(o instanceof Cell)) {
				return false;
			}
			Cell inp = (Cell) o;
			if ((this.row == inp.row) && (this.col == inp.col)) {
				return true;
			}
			return false;
		}

		@Override
		public int hashCode() {
			int h = 0;
			h = h * 31 + this.row;
			h = h * 31 + this.col;
			return h;
		}

		public int getRow() {
			return row;
		}

		public void setRow(int row) {
			this.row = row;
		}

		public int getCol() {
			return col;
		}

		public void setCol(int col) {
			this.col = col;
		}

		public int getFlag() {
			return flag;
		}

		public void setFlag(int flag) {
			this.flag = flag;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return row + ":" + col;
		}
	}

	public static void main(String[] args) {
		Matrix mat = new Matrix(4);
		mat.writeMatrixes("C:\\Users\\Administrator\\Desktop\\queen.mat", 5);
	}

}
