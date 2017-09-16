package test.nqueen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Random;
import test.nqueen.Matrix.Cell;

public class GeneticAlgorithm {
	// ！！！！！在更新fitness前必须先更新im的坐标集合（因为fitness需要用到）
	public static int fitness(IndexMatrix im) {
		HashSet<Cell> ind = im.getHs();
		int[][] mat = im.getM();
		int fit = 0;
		for (Cell ip : ind) {
			// 如果该cell的flag为1，则必有与它在某方向相遇的cell
			if (ip.getFlag() == 1) {
				continue;
			}
			// 扫描行或列中的各个cell
			for (Cell subIp : ind) {
				if (subIp.equals(ip)) {
					continue;
				}
				int ipRow = ip.getRow();
				int ipCol = ip.getCol();
				int subIpRow = subIp.getRow();
				int subIpCol = subIp.getCol();
				if (subIpRow == ipRow || subIpCol == ipCol) {
					// 如果行或列相遇cell，则两个cell的flag设为1
					ip.setFlag(1);
					subIp.setFlag(1);
				}
			}
			// 扫描左上角
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
						// 如果左上角相遇cell，则两个cell的flag设为1
						ip.setFlag(1);
						subIp.setFlag(1);
					}
					ipRow--;
					ipCol--;
				}
			}
			// 扫描右上角
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
						// 如果左上角相遇cell，则两个cell的flag设为1
						ip.setFlag(1);
						subIp.setFlag(1);
					}
					ipRow--;
					ipCol++;
				}
			}
			// 扫描左下角
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
						// 如果左上角相遇cell，则两个cell的flag设为1
						ip.setFlag(1);
						subIp.setFlag(1);
					}
					ipRow++;
					ipCol--;
				}
			}
			// 扫描右下角
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
						// 如果左上角相遇cell，则两个cell的flag设为1
						ip.setFlag(1);
						subIp.setFlag(1);
					}
					ipRow++;
					ipCol++;
				}
			}
			// 如果该cell的flag为0，说明没有其他cell在行、列、斜与其相遇
			if (ip.getFlag() == 0) {
				fit++;
			}
		}
		// 设置该染色体的适应度值
		im.setFitness(fit);
		return fit;
	}

	/**
	 * 选择染色体
	 * 
	 * @param fitnesses
	 *            存储的是每个染色体的适应度值
	 * @return selected 存储被选中的染色体在fitnesses中的染色体
	 */
	public ArrayList<Integer> randomSelect(Collection<IndexMatrix> fitnesses) {
		// probs存储每个fitness所占的概率
		ArrayList<Double> probs = new ArrayList<>();
		int sum = 0;
		for (IndexMatrix fit : fitnesses) {
			sum += fit.getFitness();
		}
		for (IndexMatrix fit : fitnesses) {
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
	public ArrayList<IndexMatrix> intersect(IndexMatrix left, IndexMatrix right) {
		// 获取两个pair的染色体
		int[][] leftChorom = left.getM();
		int[][] rightChorom = right.getM();

		int index = new Random().nextInt(leftChorom.length - 1) + 1;
		// 创建两个List存放两个染色体的前半部分
		ArrayList<int[]> leftChoromAl = new ArrayList<>();
		ArrayList<int[]> rightChoromAl = new ArrayList<>();
		for (int i = 0; i < index; i++) {
			leftChoromAl.add(leftChorom[i]);
			rightChoromAl.add(rightChorom[i]);
		}
		// 把leftChorom的后半部分放到rightChoromAl的后面
		for (int[] ii : Arrays.copyOfRange(leftChorom, index, leftChorom.length)) {
			rightChoromAl.add(ii);
		}
		// 把rightChorom的后半部分放到leftChoromAl的后面
		for (int[] ii : Arrays.copyOfRange(rightChorom, index, rightChorom.length)) {
			leftChoromAl.add(ii);
		}
		// 得到交叉后的矩阵
		int[][] leftNew = new int[leftChorom.length][leftChorom.length];
		int[][] rightNew = new int[leftChorom.length][leftChorom.length];
		leftChoromAl.toArray(leftNew);
		rightChoromAl.toArray(rightNew);

		// 创建两个新的染色体
		IndexMatrix im1 = new IndexMatrix(leftNew);
		IndexMatrix im2 = new IndexMatrix(rightNew);

		// 更新两个染色体矩阵的坐标集合
		updateIndexSet(im1);
		updateIndexSet(im2);
		// 更新两个染色体矩阵的fitness
		updateFitness(im1);
		updateFitness(im2);

		ArrayList<IndexMatrix> al = new ArrayList<IndexMatrix>();
		al.add(im1);
		al.add(im2);
		return al;
	}

	/**
	 * 变异染色体
	 * 
	 * @param copy
	 *            需要变异的一个染色体
	 */
	public IndexMatrix mutation(IndexMatrix selected) {

		// 复制selected的矩阵和坐标集合
		int[][] m1 = selected.getM();
		int[][] m = Arrays.copyOf(m1, m1.length);
		HashSet<Cell> hs = new HashSet<>(selected.getHs());
		// 创建一个新染色体
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
		// 变异两个cell
		m[row][col] = 1;
		int num = rnd.nextInt(hs.size());
		int i = 0;
		for (Cell cc : hs) {
			if (i++ == num) {
				m[cc.getRow()][cc.getCol()] = 0;
			}
		}
		// 更新变异IndexMatrix的矩阵坐标集合
		updateIndexSet(selectedNew);
		// 更新变异IndexMatrix的fitness
		updateFitness(selectedNew);

		return selectedNew;
	}

	// 更新两个染色体矩阵的fitness
	private void updateFitness(IndexMatrix im1) {

		int f1 = fitness(im1);
		im1.setFitness(f1);
	}

	// 更新im1、im2的矩阵坐标集合
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