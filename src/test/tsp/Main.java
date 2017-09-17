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
		// 交叉的概率
		double intersectRate = .5;
		// 变异的概率
		double mutationRate = .5;
		// 染色体个数的初始值
		int choromosomesNum = 10;
		// 代迭代的次数
		int loopNum = 200000;
		// 运行代码的次数
		int COUNT = 10;
		// 三角函数的步长
		double stride = .0005;
		// 设置存储历史最小值个数
		int historyNum = 30;
		// 对历史最小值的描述
		String COMMENT = "log10-";
		// 运行结果文件名
		String fileName = "C:\\Users\\Administrator\\Desktop\\遗传算法数据\\运行结果\\minVals0005.log";
		//图数据
		String gName = "C:\\Users\\Administrator\\Desktop\\遗传算法数据\\图节点数据\\my.graph1";
		// 通过加载图文件生成graph

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
			// 获取图的开始节点（同时也是结束节点）
			startPort = br.readLine().split(", ")[0];
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// choromosomes存储各个染色体及其适应度值
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
		// 对历史最小值的描述
		// 大循环
		for (int count = 0; count < COUNT; count++) {
			String comment = COMMENT + count;
			for (int i = 0; i < choromosomesNum; i++) {
				ArrayList<Port> choromo_copy = new ArrayList<>(choromo);
				Collections.shuffle(choromo_copy);
				swapStartPort(choromo_copy, startPort);
				int fit = genetic.fitness(choromo_copy);
				choromosomes.add(new ChoromFitnessPair(choromo_copy, fit));
			}
			// 存放历史最小值前historyNum个记录
			TreeSet<ChoromFitnessPair> histTS = new TreeSet<ChoromFitnessPair>(
					com);
			int minScore = Integer.MAX_VALUE;
			double du = 0;
			// 存放新生代
			ArrayList<ChoromFitnessPair> newChoroms = new ArrayList<ChoromFitnessPair>();
			long start = System.currentTimeMillis();
			for (int generation = 0; generation < loopNum; generation++) {

				System.out.println(choromosomes);
				// 清理之前的旧代
				newChoroms.clear();
				while (true) {
					// 选择两个染色体
					ArrayList<Integer> selected_ind = genetic
							.randomSelect(choromosomes);
					ChoromFitnessPair left = choromosomes.get(selected_ind
							.get(0));
					ChoromFitnessPair right = choromosomes.get(selected_ind
							.get(1));
					// 交叉两个染色体，并把新生成的两个染色体添加到choromosomes
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
								// 防止新生代加入很多Integer.MAX_VALUE
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
				// 更新换代
				choromosomes.clear();
				choromosomes.addAll(newChoroms);
				// 查找最小值
				for (ChoromFitnessPair c : newChoroms) {
					histTS.add(new ChoromFitnessPair(c.getChorom(), c
							.getFitness()));
				}
				int len = histTS.size();
				for (int i = 0; i < len - historyNum; i++) {
					histTS.pollLast();
				}
				// 更新种群中染色体个数
				choromosomesNum = genetic.myLogn(du += stride);
				if (choromosomesNum == 10) {
					du = 0;
				}
			}
			long end = System.currentTimeMillis();
			System.out.println("用时（毫秒）：" + (end - start));
			for (ChoromFitnessPair cfp : histTS) {
				System.out.println("历史最小值：" + cfp.getFitness() + " 历史最段路径："
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
