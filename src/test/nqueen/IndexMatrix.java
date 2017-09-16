package test.nqueen;

import java.util.HashSet;

import test.nqueen.Matrix.Cell;

class IndexMatrix {
	private HashSet<Cell> hs;
	private int[][] m;

	public IndexMatrix(HashSet<Cell> hs, int[][] m) {
		super();
		this.hs = hs;
		this.m = m;
	}

	public HashSet<Cell> getHs() {
		return hs;
	}

	public void setHs(HashSet<Cell> hs) {
		this.hs = hs;
	}

	public int[][] getM() {
		return m;
	}

	public void setM(int[][] m) {
		this.m = m;
	}

}