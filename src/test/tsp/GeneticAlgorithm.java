package test.tsp;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeMap;
import java.util.TreeSet;

public class GeneticAlgorithm {
	public static void main(String[] args) {
		ArrayList<Port> fitnesses = new ArrayList<>();
		fitnesses.add(new Port("20"));
		fitnesses.add(new Port("32"));
		fitnesses.add(new Port("50"));
		fitnesses.add(new Port("10"));

	}

	private TreeMap<String, Edge> edges;
	private Graph g;
	private int portNum;

	public GeneticAlgorithm(Graph g) {
		this.edges = g.getGraph();
		this.g = g;
		this.portNum = g.getPorts().values().size();
	}

	/**
	 * 评估染色体
	 * 
	 * @param ports
	 *            一条染色体
	 * @param edges
	 *            一条染色体中各个基因之间的距离
	 */
	public int fitness(ArrayList<Port> ports) {
		HashSet<Port> test = new HashSet<>(ports);
		// 该染色体的基因数不等于portNum说明有重复走得点，非法路径返回0
		if (test.size() != portNum) {
			return Integer.MAX_VALUE;
		}
		int dist = 0;
		for (int i = 0; i < ports.size() - 1; i++) {
			Port fore = ports.get(i);
			Port after = ports.get(i + 1);
			Edge e = edges.get(fore.getName() + ":" + after.getName());
			dist += e.getLen();
		}
		dist += edges.get(
				ports.get(ports.size() - 1).getName() + ":" + ports.get(0))
				.getLen();
		return dist;
	}

	/**
	 * 选择染色体
	 * 
	 * @param fitnesses
	 *            存储的是每个染色体的适应度值
	 * @return selected 存储被选中的染色体在fitnesses中的染色体
	 */
	public ArrayList<Integer> randomSelect(
			Collection<ChoromFitnessPair> fitnesses) {
		// probs存储每个fitness所占的概率
		ArrayList<Double> probs = new ArrayList<>();
		double sum = 0;
		for (ChoromFitnessPair fit : fitnesses) {
			sum += fit.getFitness();
		}
		for (ChoromFitnessPair fit : fitnesses) {
			probs.add((fit.getFitness() * 1. / sum));
		}
		// scope存储转盘的每个范围
		ArrayList<Scope> scope = new ArrayList<>();
		double right = 0;
		double left = 0;
		for (double fit : probs) {
			right += fit;
			scope.add(new Scope(left, right));
			left = right;
		}
		// 生成转盘指针
		ArrayList<Double> points = new ArrayList<>();
		points.add(Math.random());
		points.add(Math.random());
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
	 * @param leftChorom
	 *            需要交叉的一个染色体
	 * @param rigth
	 *            需要交叉的一个染色体
	 */
	public ArrayList<ChoromFitnessPair> intersect(ChoromFitnessPair left,
			ChoromFitnessPair right) {
		// 获取两个pair的染色体
		ArrayList<Port> leftChorom = new ArrayList<>(left.getChorom());
		ArrayList<Port> rightChorom = new ArrayList<>(right.getChorom());
		// 创建新的两个pair
		ChoromFitnessPair leftNew = new ChoromFitnessPair();
		ChoromFitnessPair rightNew = new ChoromFitnessPair();

		ArrayList<ChoromFitnessPair> all = new ArrayList<>();
		// 从坐标1开始，因为从0开始没意义
		int index = new Random().nextInt(leftChorom.size() - 1) + 1;

		// 开始换基因
		ArrayList<Port> temp = new ArrayList<>();
		for (int i = index; i < leftChorom.size(); i++) {
			temp.add(leftChorom.get(i));
			leftChorom.set(i, rightChorom.get(i));
		}
		for (int i = index, j = 0; i < leftChorom.size(); i++, j++) {
			rightChorom.set(i, temp.get(j));
		}
		// 设置两个新pair的染色体和适应度值
		leftNew.setChorom(leftChorom);
		leftNew.setFitness(fitness(leftChorom));
		rightNew.setChorom(rightChorom);
		rightNew.setFitness(fitness(rightChorom));
		all.add(leftNew);
		all.add(rightNew);
		return all;
	}

	/**
	 * 变异染色体
	 * 
	 * @param copy
	 *            需要变异的一个染色体
	 */
	public ChoromFitnessPair mutation(ChoromFitnessPair selected) {
		// 创建一个新染色体
		ChoromFitnessPair selectedNew = new ChoromFitnessPair();
		ArrayList<Port> copy = new ArrayList<Port>(selected.getChorom());
		ArrayList<Integer> hs = new ArrayList<>();
		while (true) {
			if (hs.size() == 2) {
				break;
			}
			// (copy.size() - 1) + 1防止开始节点被换掉
			int r = new Random().nextInt(copy.size() - 1) + 1;
			if (!hs.contains(r))
				hs.add(r);
		}
		Port temp = copy.get(hs.get(0));
		Port temp1 = copy.get(hs.get(1));
		copy.set(hs.get(0), temp1);
		copy.set(hs.get(1), temp);

		// 创建变异的pair，并设置新的染色体和适应度值
		selectedNew.setChorom(copy);
		selectedNew.setFitness(fitness(copy));
		return selectedNew;
	}

	/**
	 * y=4sin[x+π/2]+6 max=10 min=2
	 * 
	 * @return
	 */
	public int mySinX(double x) {
		return (int) (4 * Math.sin(x + Math.PI / 2) + 6);
	}

	/**
	 * y=6logn[10,x+40]-7.5
	 */
	public int myLogn(double x) {
		return (int) (6*Math.log10(x+40)-7.5);
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
