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
	 * ����Ⱦɫ��
	 * 
	 * @param ports
	 *            һ��Ⱦɫ��
	 * @param edges
	 *            һ��Ⱦɫ���и�������֮��ľ���
	 */
	public int fitness(ArrayList<Port> ports) {
		HashSet<Port> test = new HashSet<>(ports);
		// ��Ⱦɫ��Ļ�����������portNum˵�����ظ��ߵõ㣬�Ƿ�·������0
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
	 * ѡ��Ⱦɫ��
	 * 
	 * @param fitnesses
	 *            �洢����ÿ��Ⱦɫ�����Ӧ��ֵ
	 * @return selected �洢��ѡ�е�Ⱦɫ����fitnesses�е�Ⱦɫ��
	 */
	public ArrayList<Integer> randomSelect(
			Collection<ChoromFitnessPair> fitnesses) {
		// probs�洢ÿ��fitness��ռ�ĸ���
		ArrayList<Double> probs = new ArrayList<>();
		double sum = 0;
		for (ChoromFitnessPair fit : fitnesses) {
			sum += fit.getFitness();
		}
		for (ChoromFitnessPair fit : fitnesses) {
			probs.add((fit.getFitness() * 1. / sum));
		}
		// scope�洢ת�̵�ÿ����Χ
		ArrayList<Scope> scope = new ArrayList<>();
		double right = 0;
		double left = 0;
		for (double fit : probs) {
			right += fit;
			scope.add(new Scope(left, right));
			left = right;
		}
		// ����ת��ָ��
		ArrayList<Double> points = new ArrayList<>();
		points.add(Math.random());
		points.add(Math.random());
		// �洢ת��ת����λ��
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
	 * ���㽻��Ⱦɫ��
	 * 
	 * @param leftChorom
	 *            ��Ҫ�����һ��Ⱦɫ��
	 * @param rigth
	 *            ��Ҫ�����һ��Ⱦɫ��
	 */
	public ArrayList<ChoromFitnessPair> intersect(ChoromFitnessPair left,
			ChoromFitnessPair right) {
		// ��ȡ����pair��Ⱦɫ��
		ArrayList<Port> leftChorom = new ArrayList<>(left.getChorom());
		ArrayList<Port> rightChorom = new ArrayList<>(right.getChorom());
		// �����µ�����pair
		ChoromFitnessPair leftNew = new ChoromFitnessPair();
		ChoromFitnessPair rightNew = new ChoromFitnessPair();

		ArrayList<ChoromFitnessPair> all = new ArrayList<>();
		// ������1��ʼ����Ϊ��0��ʼû����
		int index = new Random().nextInt(leftChorom.size() - 1) + 1;

		// ��ʼ������
		ArrayList<Port> temp = new ArrayList<>();
		for (int i = index; i < leftChorom.size(); i++) {
			temp.add(leftChorom.get(i));
			leftChorom.set(i, rightChorom.get(i));
		}
		for (int i = index, j = 0; i < leftChorom.size(); i++, j++) {
			rightChorom.set(i, temp.get(j));
		}
		// ����������pair��Ⱦɫ�����Ӧ��ֵ
		leftNew.setChorom(leftChorom);
		leftNew.setFitness(fitness(leftChorom));
		rightNew.setChorom(rightChorom);
		rightNew.setFitness(fitness(rightChorom));
		all.add(leftNew);
		all.add(rightNew);
		return all;
	}

	/**
	 * ����Ⱦɫ��
	 * 
	 * @param copy
	 *            ��Ҫ�����һ��Ⱦɫ��
	 */
	public ChoromFitnessPair mutation(ChoromFitnessPair selected) {
		// ����һ����Ⱦɫ��
		ChoromFitnessPair selectedNew = new ChoromFitnessPair();
		ArrayList<Port> copy = new ArrayList<Port>(selected.getChorom());
		ArrayList<Integer> hs = new ArrayList<>();
		while (true) {
			if (hs.size() == 2) {
				break;
			}
			// (copy.size() - 1) + 1��ֹ��ʼ�ڵ㱻����
			int r = new Random().nextInt(copy.size() - 1) + 1;
			if (!hs.contains(r))
				hs.add(r);
		}
		Port temp = copy.get(hs.get(0));
		Port temp1 = copy.get(hs.get(1));
		copy.set(hs.get(0), temp1);
		copy.set(hs.get(1), temp);

		// ���������pair���������µ�Ⱦɫ�����Ӧ��ֵ
		selectedNew.setChorom(copy);
		selectedNew.setFitness(fitness(copy));
		return selectedNew;
	}

	/**
	 * y=4sin[x+��/2]+6 max=10 min=2
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

	// �ȽϷ��ŵ�����ֵ
	private static class Scope {
		double left;
		double right;

		public Scope(double left, double right) {
			this.left = left;
			this.right = right;
		}
	}
}
