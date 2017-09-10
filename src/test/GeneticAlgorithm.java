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
	 * ����Ⱦɫ��
	 * 
	 * @param ports
	 *            һ��Ⱦɫ��
	 * @param edges
	 *            һ��Ⱦɫ���и�������֮��ľ���
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
	 * ѡ��Ⱦɫ��
	 * 
	 * @param fitnesses
	 *            �洢����ÿ��Ⱦɫ�����Ӧ��ֵ
	 */
	public static ArrayList<Integer> randomSelect(ArrayList<Integer> fitnesses) {
		// probs�洢ÿ��fitness��ռ�ĸ���
		ArrayList<Double> probs = new ArrayList<>();
		int sum = 0;
		for (int fit : fitnesses) {
			sum += fit;
		}
		for (int fit : fitnesses) {
			probs.add((fit * 1. / sum));
		}
		System.out.println("probs: " + probs);
		// scope�洢ת�̵�ÿ����Χ
		ArrayList<Scope> scope = new ArrayList<>();
		double right = 0;
		double left = 0;
		for (double fit : probs) {
			right += fit;
			scope.add(new Scope(left, right));
			left = right;
		}
		System.out.println("scope: " + scope);
		// ����ת��ָ��
		ArrayList<Double> points = new ArrayList<>();
		points.add(Math.random());
		points.add(Math.random());
		System.out.println("points: " + points);
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
	 * @param left
	 *            ��Ҫ�����һ��Ⱦɫ��
	 * @param rigth
	 *            ��Ҫ�����һ��Ⱦɫ��
	 */
	public static ArrayList<ArrayList<Integer>> intersect(ArrayList<Integer> left, ArrayList<Integer> right) {
		ArrayList<ArrayList<Integer>> all = new ArrayList<>();
		all.add(left);
		all.add(right);
		int index = new Random().nextInt(left.size());

		// ����Ⱦɫ�廥��������
		if (index == 0) {
			return all;
		}
		// ��ʼ������
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
	 * ����Ⱦɫ��
	 * 
	 * @param selected
	 *            ��Ҫ�����һ��Ⱦɫ��
	 */
	public static ArrayList<Port> mutation(ArrayList<Port> selected) {
		ArrayList<Integer> hs = new ArrayList<>();
		while (true) {
			if (hs.size() == 2) {
				break;
			}
			int r = new Random().nextInt(selected.size());
			// ��ֹ��ʼλ�ñ��滻
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
	 * y=4sin[x+��/2]+6 max=10 min=2
	 * 
	 * @return
	 */
	public static double mySinX(double x) {
		return 4 * Math.sin(x + Math.PI / 2) + 6;
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
