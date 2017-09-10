package test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeMap;

public class GeneticAlgorithm {
	public static void main(String[] args) {
		ArrayList<Port> fitnesses = new ArrayList<>();
		fitnesses.add(new Port("20"));
		fitnesses.add(new Port("32"));
		fitnesses.add(new Port("50"));
		fitnesses.add(new Port("10"));

		System.out.println(mutation(fitnesses));
	}

	/**
	 * 评估染色体
	 * 
	 * @param ports
	 *            一条染色体
	 * @param edges
	 *            一条染色体中各个基因之间的距离
	 */
	public static int fitness(ArrayList<Port> ports, TreeMap<String, Edge> edges) {
		int dist = 0;
		for (int i = 0; i < ports.size() - 1; i++) {
			Port fore = ports.get(i);
			Port after = ports.get(i + 1);
			Edge e = edges.get(fore.getName() + ":" + after.getName());
			dist += e.getLen();
		}
		dist += edges.get(ports.get(ports.size() - 1).getName() + ":" + ports.get(0)).getLen();
		return dist;
	}

	/**
	 * 选择染色体
	 * 
	 * @param fitnesses
	 *            存储的是每个染色体的适应度值
	 */
	public static ArrayList<Integer> randomSelect(ArrayList<Integer> fitnesses) {
		// probs存储每个fitness所占的概率
		ArrayList<Double> probs = new ArrayList<>();
		int sum = 0;
		for (int fit : fitnesses) {
			sum += fit;
		}
		for (int fit : fitnesses) {
			probs.add((fit * 1. / sum));
		}
		System.out.println("probs: " + probs);
		// scope存储转盘的每个范围
		ArrayList<Scope> scope = new ArrayList<>();
		double right = 0;
		double left = 0;
		for (double fit : probs) {
			right += fit;
			scope.add(new Scope(left, right));
			left = right;
		}
		System.out.println("scope: " + scope);
		// 生成转盘指针
		ArrayList<Double> points = new ArrayList<>();
		points.add(Math.random());
		points.add(Math.random());
		System.out.println("points: " + points);
		// 存储转盘转到的位置
		ArrayList<Integer> selected = new ArrayList<>();

		for (double p : points) {
			for (int i = 0; i < scope.size(); i++) {
				Scope s = scope.get(i);
				if ((p >= s.left) && (p < s.right)) {
					selected.add(i);
				}
			}
		}
		return selected;
	}

	/**
	 * 单点交叉染色体
	 * 
	 * @param left
	 *            需要交叉的一个染色体
	 * @param rigth
	 *            需要交叉的一个染色体
	 */
	public static ArrayList<ArrayList<Integer>> intersect(ArrayList<Integer> left, ArrayList<Integer> right) {
		ArrayList<ArrayList<Integer>> all = new ArrayList<>();
		all.add(left);
		all.add(right);
		int index = new Random().nextInt(left.size());

		// 整个染色体互换无意义
		if (index == 0) {
			return all;
		}
		// 开始换基因
		ArrayList<Integer> temp = new ArrayList<>();
		for (int i = index; i < left.size(); i++) {
			temp.add(left.get(i));
			left.set(i, right.get(i));
		}
		for (int i = index, j = 0; i < left.size(); i++, j++) {
			right.set(i, temp.get(j));
		}

		return all;
	}

	/**
	 * 变异染色体
	 * 
	 * @param selected
	 *            需要变异的一个染色体
	 */
	public static ArrayList<Port> mutation(ArrayList<Port> selected) {
		ArrayList<Integer> hs = new ArrayList<>();
		while (true) {
			if (hs.size() == 2) {
				break;
			}
			int r = new Random().nextInt(selected.size());
			// 防止开始位置被替换
			if (r == 0) {
				continue;
			}
			if (!hs.contains(r))
				hs.add(r);
		}
		Port temp = selected.get(hs.get(0));
		Port temp1 = selected.get(hs.get(1));
		selected.set(hs.get(0), temp1);
		selected.set(hs.get(1), temp);
		return selected;
	}

	/**
	 * y=4sin[x+π/2]+6 max=10 min=2
	 * 
	 * @return
	 */
	public static double mySinX(double x) {
		return 4 * Math.sin(x + Math.PI / 2) + 6;
	}

	// 比较符号的左右值
	private static class Scope {
		double left;
		double right;

		public Scope(double left, double right) {
			this.left = left;
			this.right = right;
		}
	}
}
