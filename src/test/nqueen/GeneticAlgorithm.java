package test.nqueen;

import java.util.HashSet;

import test.nqueen.Matrix.Cell;

public class GeneticAlgorithm {
	public void fitness(IndexMatrix im) {
		HashSet<Cell> ind = im.getHs();
		int[][] mat = im.getM();
		int fit = 0;
		for (Cell ip : ind) {
			// �����cell��flagΪ1�������������ĳ����������cell
			if (ip.getFlag() == 1) {
				continue;
			}
			for (Cell subIp : ind) {
				if (!ip.equals(subIp)) {
					int ipRow = ip.getRow();
					int ipCol = ip.getCol();
					int subIpRow = subIp.getRow();
					int subIpCol = subIp.getCol();
					// �ж��л���
					if (ipRow == subIpRow || ipCol == subIpCol) {
						// ��cell���Ϊ1�����ѭ����������ʱ�Զ�����
						subIp.setFlag(1);
						break;
					}
					// �ж��ĸ�б�Խǣ����������cell��������true
					if (ifDiagonalEqual(ip, subIp)) {
						break;
					}

					fit++;
				}
			}
		}
	}

	// �ж��ĸ�б�Խǣ����������cell��������true
	private boolean ifDiagonalEqual(Cell ip, Cell subIp) {

		return false;
	}
}