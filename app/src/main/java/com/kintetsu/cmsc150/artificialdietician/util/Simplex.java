package com.kintetsu.cmsc150.artificialdietician.util;

import android.content.Context;
import android.util.Log;

public class Simplex {
    public static final String TAG = Simplex.class.getSimpleName();
    public static final int MAX_ITER = 10000;

	private double[] ans;
    private Context c;

	private Simplex(double[][] matrix, Context c, boolean minimization) {
		this.ans = new double[matrix[0].length-1];
        this.c = c;
		calculate(matrix, minimization);
	}

	private void calculate(double[][] tableu, boolean minimization) {
		int pc, pr;
		int iter = 0;

        try {
            while ((pc = getPivotCol(tableu)) > -1) {
				if(iter > MAX_ITER) {
                    throw new RuntimeException("Max iteration reached.");
                }

                FileUtil.writeTableu(tableu, c, "Iteration " + iter);
                FileUtil.writeBasicAns(this.ans, c);

                pr = getPivotRow(tableu, pc);

                normalize(tableu, pc, pr);
                iter++;

                getAnswers(tableu, minimization);
            }

			FileUtil.writeTableu(tableu, c, "Iteration " + iter);
			FileUtil.writeBasicAns(this.ans, c);
            FileUtil.write("Solution found.", c);
        } catch(ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, e.getMessage(), e);
            FileUtil.write("No possible solution.", c);
            ans = null;
        } catch(RuntimeException e) {
            Log.e(TAG, e.getMessage(), e);
            FileUtil.write("Max iteration reached. Stopping the process", c);
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

	private void getAnswers(double[][] tableu, boolean minimization) {
		int lastCol = tableu[0].length - 1;

        if(minimization) {
            for(int i=0; i<tableu[0].length-1; i++) {
                ans[i] = tableu[tableu.length-1][i];
            }
        } else {
            for (int i = 0; i < tableu[0].length - 1; i++) {
                boolean hasFoundOne = false, onlyOne = true;
                int oneRow = -1;

                for (int j = 0; j < tableu.length; j++) {
                    if (tableu[j][i] != 0) {
                        if (tableu[j][i] == 1 && !hasFoundOne) {
                            hasFoundOne = true;
                            oneRow = j;
                        } else {
                            onlyOne = false;
                        }
                    }
                }

                if (hasFoundOne && onlyOne) {
                    ans[i] = tableu[oneRow][lastCol];
                    Log.d(TAG, "Putting " + Double.toString(tableu[oneRow][lastCol]) + " on index " + Integer.toString(i));
                } else {
                    ans[i] = 0;
                    Log.d(TAG, "No solution on index " + Integer.toString(i));
                }
            }
        }
	}

	private double[] getAns() {
		return ans;
	}

	public static double[] solve(double[][] tableu, Context c, boolean minimization) {
		return new Simplex(tableu, c, minimization).getAns();
	}
}