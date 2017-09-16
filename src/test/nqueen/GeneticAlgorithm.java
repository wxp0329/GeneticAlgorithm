package test.nqueen;

import java.util.HashSet;

import test.nqueen.Matrix.Cell;

public class GeneticAlgorithm {
	public void fitness(IndexMatrix im) {
		HashSet<Cell> ind = im.getHs();
		int[][] mat = im.getM();
		int fit = 0;
		for (Cell ip : ind) {
			// 如果该cell的flag为1，则必有与它在某方向相遇的cell
			if (ip.getFlag() == 1) {
				continue;
			}
			for (Cell subIp : ind) {
				if (!ip.equals(subIp)) {
					int ipRow = ip.getRow();
					int ipCol = ip.getCol();
					int subIpRow = subIp.getRow();
					int subIpCol = subIp.getCol();
					// 判断行或列
					if (ipRow == subIpRow || ipCol == subIpCol) {
						// 该cell标记为1，外层循环遍历到它时自动跳过
						subIp.setFlag(1);
						break;
					}
					// 判断四个斜对角，如果这两个cell相遇返回true
					if (ifDiagonalEqual(ip, subIp)) {
						break;
					}

					fit++;
				}
			}
		}
	}

	// 判断四个斜对角，如果这两个cell相遇返回true
	private boolean ifDiagonalEqual(Cell ip, Cell subIp) {

		return false;
	}
}