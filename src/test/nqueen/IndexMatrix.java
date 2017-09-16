package test.nqueen;

import java.util.HashSet;

import test.nqueen.Matrix.Cell;

class IndexMatrix {
	private HashSet<Cell> hs;
	private int[][] m;
	private int fitness;

	public IndexMatrix() {
	}

	public IndexMatrix(int[][] m) {
		this.m = m;
	}

	public IndexMatrix(HashSet<Cell> hs, int[][] m, int fitness) {
		super();
		this.hs = hs;
		this.m = m;
		this.fitness = fitness;
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

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

}