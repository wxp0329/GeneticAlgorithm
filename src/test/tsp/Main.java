package test.tsp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) {
		// ����ĸ���
		double intersectRate = .5;
		// ����ĸ���
		double mutationRate = .5;
		// Ⱦɫ������ĳ�ʼֵ
		int choromosomesNum = 10;
		// �������Ĵ���
		int loopNum = 200000;
		// ���д���Ĵ���
		int COUNT = 10;
		// ���Ǻ����Ĳ���
		double stride = .0005;
		// ���ô洢��ʷ��Сֵ����
		int historyNum = 30;
		// ����ʷ��Сֵ������
		String COMMENT = "log10-";
		// ���н���ļ���
		String fileName = "C:\\Users\\Administrator\\Desktop\\�Ŵ��㷨����\\���н��\\minVals0005.log";
		//ͼ����
		String gName = "C:\\Users\\Administrator\\Desktop\\�Ŵ��㷨����\\ͼ�ڵ�����\\my.graph1";
		// ͨ������ͼ�ļ�����graph

		Graph g = new Graph();
		g.loadUndirectedCompleteGraph(gName);
		System.out.println("size : " + g.getPorts().size());
		GeneticAlgorithm genetic = new GeneticAlgorithm(g);
		BufferedReader br = null;
		String startPort = null;
		try {
			br = new BufferedReader(new FileReader(new File(
					gName)));
			br.readLine();
			// ��ȡͼ�Ŀ�ʼ�ڵ㣨ͬʱҲ�ǽ����ڵ㣩
			startPort = br.readLine().split(", ")[0];
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// choromosomes�洢����Ⱦɫ�弰����Ӧ��ֵ
		ArrayList<ChoromFitnessPair> choromosomes = new ArrayList<ChoromFitnessPair>();
		ArrayList<Port> choromo = new ArrayList<>(g.getPorts().values());

		Comparator<ChoromFitnessPair> com = new Comparator<ChoromFitnessPair>() {

			@Override
			public int compare(ChoromFitnessPair o1, ChoromFitnessPair o2) {
				if (o1.getFitness() > o2.getFitness()) {
					return 1;
				} else if (o1.getFitness() < o2.getFitness()) {
					return -1;
				}
				return 0;
			}
		};
		// ����ʷ��Сֵ������
		// ��ѭ��
		for (int count = 0; count < COUNT; count++) {
			String comment = COMMENT + count;
			for (int i = 0; i < choromosomesNum; i++) {
				ArrayList<Port> choromo_copy = new ArrayList<>(choromo);
				Collections.shuffle(choromo_copy);
				swapStartPort(choromo_copy, startPort);
				int fit = genetic.fitness(choromo_copy);
				choromosomes.add(new ChoromFitnessPair(choromo_copy, fit));
			}
			// �����ʷ��СֵǰhistoryNum����¼
			TreeSet<ChoromFitnessPair> histTS = new TreeSet<ChoromFitnessPair>(
					com);
			int minScore = Integer.MAX_VALUE;
			double du = 0;
			// ���������
			ArrayList<ChoromFitnessPair> newChoroms = new ArrayList<ChoromFitnessPair>();
			long start = System.currentTimeMillis();
			for (int generation = 0; generation < loopNum; generation++) {

				System.out.println(choromosomes);
				// ����֮ǰ�ľɴ�
				newChoroms.clear();
				while (true) {
					// ѡ������Ⱦɫ��
					ArrayList<Integer> selected_ind = genetic
							.randomSelect(choromosomes);
					ChoromFitnessPair left = choromosomes.get(selected_ind
							.get(0));
					ChoromFitnessPair right = choromosomes.get(selected_ind
							.get(1));
					// ��������Ⱦɫ�壬���������ɵ�����Ⱦɫ����ӵ�choromosomes
					double rand = Math.random();
					if (rand < intersectRate) {
						ArrayList<ChoromFitnessPair> two = genetic.intersect(
								left, right);
						double randMu = Math.random();
						if (randMu < mutationRate) {
							for (ChoromFitnessPair cfp : two) {
								if (newChoroms.size() == choromosomesNum) {
									break;
								}
								ChoromFitnessPair mut = genetic.mutation(cfp);
								// ��ֹ����������ܶ�Integer.MAX_VALUE
								if (mut.getFitness() < Integer.MAX_VALUE) {
									newChoroms.add(mut);
								}

							}
						}
					}
					if (newChoroms.size() == choromosomesNum) {
						break;
					}
				}
				// ���»���
				choromosomes.clear();
				choromosomes.addAll(newChoroms);
				// ������Сֵ
				for (ChoromFitnessPair c : newChoroms) {
					histTS.add(new ChoromFitnessPair(c.getChorom(), c
							.getFitness()));
				}
				int len = histTS.size();
				for (int i = 0; i < len - historyNum; i++) {
					histTS.pollLast();
				}
				// ������Ⱥ��Ⱦɫ�����
				choromosomesNum = genetic.myLogn(du += stride);
				if (choromosomesNum == 10) {
					du = 0;
				}
			}
			long end = System.currentTimeMillis();
			System.out.println("��ʱ�����룩��" + (end - start));
			for (ChoromFitnessPair cfp : histTS) {
				System.out.println("��ʷ��Сֵ��" + cfp.getFitness() + " ��ʷ���·����"
						+ cfp.getChorom());
			}
			writeMinVals(comment, histTS, fileName);
		}
		DrawLineChart.main(new String[] { fileName });
	}

	public static void swapStartPort(ArrayList<Port> ports, String startName) {
		int startPosition = 0;
		for (int i = 0; i < ports.size(); i++) {
			if (ports.get(i).getName().equals(startName)) {
				startPosition = i;
				break;
			}
		}

		Port temp = new Port(ports.get(startPosition).getName());
		ports.set(startPosition, new Port(ports.get(0).getName()));
		ports.set(0, temp);

	}

	public static void writeMinVals(String comment,
			Collection<ChoromFitnessPair> ss, String fileName) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
					fileName), true));
			bw.write(comment);
			bw.newLine();
			bw.write(ss.toString().replaceAll("[\\[\\]]", ""));
			bw.newLine();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
