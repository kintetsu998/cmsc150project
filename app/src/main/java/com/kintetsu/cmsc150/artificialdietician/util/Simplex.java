package com.kintetsu.cmsc150.artificialdietician.util;

public class Simplex {
	private double[] ans;

	private Simplex(double[][] matrix) {
		ans = new double[matrix[0].length];
		calculate(matrix);
	}

	private void calculate(double[][] tableu) {
		int pc, pr;
		while((pc = getPivotCol(tableu)) > -1) {
			pr = getPivotRow(tableu, pc);
			normalize(tableu, pc, pr);
		}

		getAnswers(tableu);
	}

	private int getPivotCol(double[][] tableu) {
		int i = tableu.length - 1;
		int minIndex = -1;
		double minValue = 0;
		for(int j=0; j<tableu[0].length; j++) {
			if(minValue > tableu[i][j]) {
				minIndex = j;
				minValue = tableu[i][j];
			}
		}

		return minIndex;
	}

	private int getPivotRow(double[][] tableu, int col) {
		int lastCol = tableu[0].length - 1;
		int minIndex = -1;
		double minValue = Double.MAX_VALUE;
		for(int i=0; i<tableu.length; i++) {
			double testRatio;
			if(tableu[i][col] == 0) continue;

			testRatio = tableu[i][lastCol]/tableu[i][col];
			if(minValue > testRatio && testRatio > 0) {
				minValue = testRatio;
				minIndex = i;
			}
		}

		return minIndex;
	}

	private void normalize(double[][] tableu, int pc, int pr) {
		double pe = tableu[pr][pc];

		for(int i=0; i<tableu[0].length; i++) {
			tableu[pr][i] /= pe;
		}

		for(int i=0; i<tableu.length; i++) {
			double mult = tableu[i][pc];
			for(int j=0; j<tableu[i].length; j++) {
				if(i == pr) continue;
				tableu[i][j] = tableu[i][j] - (tableu[pr][j] * mult);
			}
		}
	}

	private void getAnswers(double[][] tableu) {
		int lastCol = tableu[0].length - 1;
		for(int i=0; i<tableu[0].length; i++) {
			boolean hasFoundOne = false, onlyOne = true;
			int oneRow = -1;
			for(int j=0; j<tableu.length; j++) {
				if(tableu[j][i] != 0) {
					if(tableu[j][i] == 1 && !hasFoundOne) {
						hasFoundOne = true;
						oneRow = j;
					} else {
						onlyOne = false;
					}
				}
			}

			if(hasFoundOne && onlyOne) {
				ans[i] = tableu[oneRow][lastCol];
			}
		}
	}

	private double[] getAns() {
		return ans;
	}

	public static double[] solve(double[][] tableu) {
		return new Simplex(tableu).getAns();
	}
}