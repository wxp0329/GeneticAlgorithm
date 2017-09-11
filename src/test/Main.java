package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) {

		double intersectRate = 0.5;
		double mutationRate = 0.5;
		int choromosomesNum = 10;
		int loopNum = 30000;
		// 通过加载图文件生成graph
		Graph g = new Graph();
		g.loadUndirectedCompleteGraph("C:\\Users\\Administrator\\Desktop\\my.graph");

		GeneticAlgorithm genetic = new GeneticAlgorithm(g);
		// choromosomes存储各个染色体及其适应度值
		ArrayList<ChoromFitnessPair> choromosomes = new ArrayList<ChoromFitnessPair>();
		ArrayList<Port> choromo = new ArrayList<>(g.getPorts().values());
		for (int i = 0; i < choromosomesNum; i++) {
			ArrayList<Port> choromo_copy = new ArrayList<>(choromo);
			Collections.shuffle(choromo_copy);
			int fit = genetic.fitness(choromo_copy);
			choromosomes.add(new ChoromFitnessPair(choromo_copy, fit));
		}

		for (int generation = 0; generation < loopNum; generation++) {
			// 选择两个染色体
			ArrayList<Integer> selected_ind = genetic.randomSelect(choromosomes);
			ChoromFitnessPair left = choromosomes.get(selected_ind.get(0));
			ChoromFitnessPair right = choromosomes.get(selected_ind.get(1));
			// 交叉两个染色体，并把新生成的两个染色体添加到choromosomes
			double rand = Math.random();
			if (rand < intersectRate) {
				choromosomes.addAll(genetic.intersect(left, right));
			}
			// 变异一个染色体，并把新生成的染色体添加到choromosomes
			double randMu = Math.random();
			if (randMu < mutationRate) {
				choromosomes.add(genetic.mutation(left));
			}
			// 保持染色体的固定个数
			// 按适应度值从小到大排序
			Collections.sort(choromosomes, new Comparator<ChoromFitnessPair>() {

				@Override
				public int compare(ChoromFitnessPair o1, ChoromFitnessPair o2) {
					if (o1.getFitness() > o2.getFitness()) {
						return -1;
					}
					return 1;
				}
			});
			// 保持染色体的固定个数，删除多余的染色体
			int loop = choromosomes.size() - choromosomesNum;
			for (int i = 0; i < loop; i++) {
				choromosomes.remove(0);
			}
			System.out.println(choromosomes);
		}
	}

}
