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
		// ͨ������ͼ�ļ�����graph
		Graph g = new Graph();
		g.loadUndirectedCompleteGraph("C:\\Users\\Administrator\\Desktop\\my.graph");

		GeneticAlgorithm genetic = new GeneticAlgorithm(g);
		// choromosomes�洢����Ⱦɫ�弰����Ӧ��ֵ
		ArrayList<ChoromFitnessPair> choromosomes = new ArrayList<ChoromFitnessPair>();
		ArrayList<Port> choromo = new ArrayList<>(g.getPorts().values());
		for (int i = 0; i < choromosomesNum; i++) {
			ArrayList<Port> choromo_copy = new ArrayList<>(choromo);
			Collections.shuffle(choromo_copy);
			int fit = genetic.fitness(choromo_copy);
			choromosomes.add(new ChoromFitnessPair(choromo_copy, fit));
		}

		for (int generation = 0; generation < loopNum; generation++) {
			// ѡ������Ⱦɫ��
			ArrayList<Integer> selected_ind = genetic.randomSelect(choromosomes);
			ChoromFitnessPair left = choromosomes.get(selected_ind.get(0));
			ChoromFitnessPair right = choromosomes.get(selected_ind.get(1));
			// ��������Ⱦɫ�壬���������ɵ�����Ⱦɫ����ӵ�choromosomes
			double rand = Math.random();
			if (rand < intersectRate) {
				choromosomes.addAll(genetic.intersect(left, right));
			}
			// ����һ��Ⱦɫ�壬���������ɵ�Ⱦɫ����ӵ�choromosomes
			double randMu = Math.random();
			if (randMu < mutationRate) {
				choromosomes.add(genetic.mutation(left));
			}
			// ����Ⱦɫ��Ĺ̶�����
			// ����Ӧ��ֵ��С��������
			Collections.sort(choromosomes, new Comparator<ChoromFitnessPair>() {

				@Override
				public int compare(ChoromFitnessPair o1, ChoromFitnessPair o2) {
					if (o1.getFitness() > o2.getFitness()) {
						return -1;
					}
					return 1;
				}
			});
			// ����Ⱦɫ��Ĺ̶�������ɾ�������Ⱦɫ��
			int loop = choromosomes.size() - choromosomesNum;
			for (int i = 0; i < loop; i++) {
				choromosomes.remove(0);
			}
			System.out.println(choromosomes);
		}
	}

}
