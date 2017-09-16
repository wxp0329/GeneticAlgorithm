package test.nqueen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import test.nqueen.Matrix.Cell;

public class GeneticAlgorithm {
	// �����������ڸ���fitnessǰ�����ȸ���im�����꼯�ϣ���Ϊfitness��Ҫ�õ���
	public static int fitness(IndexMatrix im) {
		HashSet<Cell> ind = im.getHs();
		int[][] mat = im.getM();
		int fit = 0;
		for (Cell ip : ind) {
			// �����cell��flagΪ1�������������ĳ����������cell
			if (ip.getFlag() == 1) {
				continue;
			}
			// ɨ���л����еĸ���cell
			for (Cell subIp : ind) {
				if (subIp.equals(ip)) {
					continue;
				}
				int ipRow = ip.getRow();
				int ipCol = ip.getCol();
				int subIpRow = subIp.getRow();
				int subIpCol = subIp.getCol();
				if (subIpRow == ipRow || subIpCol == ipCol) {
					// ����л�������cell��������cell��flag��Ϊ1
					ip.setFlag(1);
					subIp.setFlag(1);
				}
			}
			// ɨ�����Ͻ�
			for (Cell subIp : ind) {
				if (subIp.equals(ip)) {
					continue;
				}
				int ipRow = ip.getRow();
				int ipCol = ip.getCol();
				int subIpRow = subIp.getRow();
				int subIpCol = subIp.getCol();
				while (ipRow >= 0) {

					if (subIpRow == ipRow && subIpCol == ipCol) {
						// ������Ͻ�����cell��������cell��flag��Ϊ1
						ip.setFlag(1);
						subIp.setFlag(1);
					}
					ipRow--;
					ipCol--;
				}
			}
			// ɨ�����Ͻ�
			for (Cell subIp : ind) {
				if (subIp.equals(ip)) {
					continue;
				}
				int ipRow = ip.getRow();
				int ipCol = ip.getCol();
				int subIpRow = subIp.getRow();
				int subIpCol = subIp.getCol();
				while (ipRow >= 0) {
					if (subIpRow == ipRow && subIpCol == ipCol) {
						// ������Ͻ�����cell��������cell��flag��Ϊ1
						ip.setFlag(1);
						subIp.setFlag(1);
					}
					ipRow--;
					ipCol++;
				}
			}
			// ɨ�����½�
			for (Cell subIp : ind) {
				if (subIp.equals(ip)) {
					continue;
				}
				int ipRow = ip.getRow();
				int ipCol = ip.getCol();
				int subIpRow = subIp.getRow();
				int subIpCol = subIp.getCol();
				while (ipRow < mat.length) {

					if (subIpRow == ipRow && subIpCol == ipCol) {
						// ������Ͻ�����cell��������cell��flag��Ϊ1
						ip.setFlag(1);
						subIp.setFlag(1);
					}
					ipRow++;
					ipCol--;
				}
			}
			// ɨ�����½�
			for (Cell subIp : ind) {
				if (subIp.equals(ip)) {
					continue;
				}
				int ipRow = ip.getRow();
				int ipCol = ip.getCol();
				int subIpRow = subIp.getRow();
				int subIpCol = subIp.getCol();
				while (ipRow < mat.length) {

					if (subIpRow == ipRow && subIpCol == ipCol) {
						// ������Ͻ�����cell��������cell��flag��Ϊ1
						ip.setFlag(1);
						subIp.setFlag(1);
					}
					ipRow++;
					ipCol++;
				}
			}
			// �����cell��flagΪ0��˵��û������cell���С��С�б��������
			if (ip.getFlag() == 0) {
				fit++;
			}
		}
		// ���ø�Ⱦɫ�����Ӧ��ֵ
		im.setFitness(fit);
		return fit;
	}

	/**
	 * ѡ��Ⱦɫ��
	 * 
	 * @param fitnesses
	 *            �洢����ÿ��Ⱦɫ�����Ӧ��ֵ
	 * @return selected �洢��ѡ�е�Ⱦɫ����fitnesses�е�Ⱦɫ��
	 */
	public ArrayList<Integer> randomSelect(Collection<IndexMatrix> fitnesses) {
		// probs�洢ÿ��fitness��ռ�ĸ���
		ArrayList<Double> probs = new ArrayList<>();
		int sum = 0;
		for (IndexMatrix fit : fitnesses) {
			sum += fit.getFitness();
		}
		for (IndexMatrix fit : fitnesses) {
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
	public ArrayList<IndexMatrix> intersect(IndexMatrix left, IndexMatrix right) {
		// ��ȡ����pair��Ⱦɫ��
		int[][] leftChorom = left.getM();
		int[][] rightChorom = right.getM();

		int index = new Random().nextInt(leftChorom.length - 1) + 1;
		// ��������List�������Ⱦɫ���ǰ�벿��
		ArrayList<int[]> leftChoromAl = new ArrayList<>();
		ArrayList<int[]> rightChoromAl = new ArrayList<>();
		for (int i = 0; i < index; i++) {
			leftChoromAl.add(leftChorom[i]);
			rightChoromAl.add(rightChorom[i]);
		}
		// ��leftChorom�ĺ�벿�ַŵ�rightChoromAl�ĺ���
		for (int[] ii : Arrays.copyOfRange(leftChorom, index, leftChorom.length)) {
			rightChoromAl.add(ii);
		}
		// ��rightChorom�ĺ�벿�ַŵ�leftChoromAl�ĺ���
		for (int[] ii : Arrays.copyOfRange(rightChorom, index, rightChorom.length)) {
			leftChoromAl.add(ii);
		}
		// �õ������ľ���
		int[][] leftNew = new int[leftChorom.length][leftChorom.length];
		int[][] rightNew = new int[leftChorom.length][leftChorom.length];
		leftChoromAl.toArray(leftNew);
		rightChoromAl.toArray(rightNew);

		// ���������µ�Ⱦɫ��
		IndexMatrix im1 = new IndexMatrix(leftNew);
		IndexMatrix im2 = new IndexMatrix(rightNew);

		// ��������Ⱦɫ���������꼯��
		updateIndexSet(im1);
		updateIndexSet(im2);
		// ��������Ⱦɫ������fitness
		updateFitness(im1);
		updateFitness(im2);

		ArrayList<IndexMatrix> al = new ArrayList<IndexMatrix>();
		al.add(im1);
		al.add(im2);
		return al;
	}

	/**
	 * ����Ⱦɫ��
	 * 
	 * @param copy
	 *            ��Ҫ�����һ��Ⱦɫ��
	 */
	public IndexMatrix mutation(IndexMatrix selected) {

		// ����selected�ľ�������꼯��
		int[][] m1 = selected.getM();
		int[][] m = Arrays.copyOf(m1, m1.length);
		HashSet<Cell> hs = new HashSet<>(selected.getHs());
		// ����һ����Ⱦɫ��
		IndexMatrix selectedNew = new IndexMatrix();
		selectedNew.setM(m);

		Random rnd = new Random();
		Cell c = null;
		int row = 0;
		int col = 0;
		while (true) {
			row = rnd.nextInt(hs.size());
			col = rnd.nextInt(hs.size());
			c = new Cell(row, col);
			if (!hs.contains(c)) {
				break;
			}
		}
		// ��������cell
		m[row][col] = 1;
		int num = rnd.nextInt(hs.size());
		int i = 0;
		for (Cell cc : hs) {
			if (i++ == num) {
				m[cc.getRow()][cc.getCol()] = 0;
			}
		}
		// ���±���IndexMatrix�ľ������꼯��
		updateIndexSet(selectedNew);
		// ���±���IndexMatrix��fitness
		updateFitness(selectedNew);

		return selectedNew;
	}

	// ��������Ⱦɫ������fitness
	private void updateFitness(IndexMatrix im1) {

		int f1 = fitness(im1);
		im1.setFitness(f1);
	}

	// ����im1��im2�ľ������꼯��
	private void updateIndexSet(IndexMatrix im1) {

		HashSet<Cell> leftHS = new HashSet<>();
		im1.setHs(leftHS);

		int[][] im1M = im1.getM();
		for (int i = 0; i < im1M.length; i++) {
			for (int j = 0; j < im1M.length; j++) {
				if (im1M[i][j] == 1) {
					leftHS.add(new Cell(i, j));
				}
			}
		}
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