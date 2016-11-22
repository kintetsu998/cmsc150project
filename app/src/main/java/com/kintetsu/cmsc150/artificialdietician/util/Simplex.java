package com.kintetsu.cmsc150.artificialdietician.util;

import android.content.Context;
import android.util.Log;

public class Simplex {
    public static final String TAG = Simplex.class.getSimpleName();

	private double[] ans;
    private Context c;

	private Simplex(double[][] matrix, Context c) {
		ans = new double[matrix[0].length];
        c = c;
		calculate(matrix);
	}

	private void calculate(double[][] tableu) {
		int pc, pr;
		int iter = 0;

        try {
            while ((pc = getPivotCol(tableu)) > -1) {
                FileUtil.writeTableu(tableu, c, "Iteration " + iter, false);
                FileUtil.writeBasicAns(this.ans, c);

                pr = getPivotRow(tableu, pc);

                normalize(tableu, pc, pr);
                iter++;

                getAnswers(tableu);
            }
        } catch(Exception e) {
            Log.e(TAG, e.getMessage(), e);
            FileUtil.write("No possible solution.", c);
            ans = null;
        }
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

	public static double[] solve(double[][] tableu, Context c) {
		return new Simplex(tableu, c).getAns();
	}
}