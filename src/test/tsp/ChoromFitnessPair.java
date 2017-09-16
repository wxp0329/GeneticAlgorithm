package test.tsp;

import java.util.ArrayList;

class ChoromFitnessPair implements Comparable<ChoromFitnessPair> {
	private ArrayList<Port> chorom;
	private int fitness;

	public ChoromFitnessPair() {
	}

	public ChoromFitnessPair(ArrayList<Port> chorom, int fitness) {
		super();
		this.chorom = chorom;
		this.fitness = fitness;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return fitness + "";
	}

	@Override
	public int compareTo(ChoromFitnessPair o) {
		if (this.fitness > o.fitness) {
			return 1;
		}
		return -1;
	}

	public ArrayList<Port> getChorom() {
		return chorom;
	}

	public void setChorom(ArrayList<Port> chorom) {
		this.chorom = chorom;
	}

	public int getFitness() {
		return fitness;
	}

	public void setFitness(int fitness) {
		this.fitness = fitness;
	}

}