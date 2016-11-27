package com.kintetsu.cmsc150.artificialdietician.util;

import android.content.Context;
import android.widget.Toast;

import com.kintetsu.cmsc150.artificialdietician.model.Food;

import java.util.ArrayList;

/**
 * Created by Aspire V3 on 11/19/2016.
 */

public class MatrixBuilder {
    public static final String TAG = MatrixBuilder.class.getSimpleName();
    private static Context c;

    public static double[][] buildTableu(String[] constraints, Context c, boolean minimization) {
        //TODO: Create tableu
        MatrixBuilder.c = c;
        double[][] matrix = buildMatrix(constraints, minimization);

        return null;
    }

    public static double[][] buildTableu(ArrayList<Food> foodlist, Context c, boolean minimization) {
        double[][] matrix = buildMatrix(foodlist, minimization);
        return createTableu(matrix, c);
    }

    public static double[][] createTableu(double[][] matrix, Context c) {
        double[][] tableu = new double[matrix.length][matrix[0].length + matrix.length];

        for(int i = 0; i < matrix.length; i++) {
            for(int j=0; j<matrix[i].length-1; j++) {
                tableu[i][j] = matrix[i][j];
            }

            tableu[i][matrix[i].length-1 + i] = 1;
            tableu[i][tableu[i].length-1] = matrix[i][matrix[i].length-1];
        }

        FileUtil.clearFile();
        //FileUtil.writeTableu(matrix, c, "Initial Matrix");
        FileUtil.writeTableu(tableu, c, "Initial Tableu");

        return tableu;
    }

    private static double[][] buildMatrix(String[] constraint, boolean minimization) {
        String[] variables = getVariables(constraint[constraint.length-1]);

        for(String var : variables) {
            Toast.makeText(c, var, Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private static String[] getVariables(String objFunction) {
        ArrayList<String> vars = new ArrayList<>();
        String var = "";
        boolean foundChar = false;

        for(char c : objFunction.toCharArray()) {
            if(c == '+' || c == '-' || c == '=') {
                if(!var.equals("")) {
                    vars.add(var);
                }

                var = "";
                foundChar = false;
                if(c == '=') {
                    break;
                }
            } else if((Character.isDigit(c) && !foundChar) || c == ' '){
                continue;
            } else {
                foundChar = true;
                var += c;
            }
        }

        return vars.toArray(new String[1]);
    }

    private static double[][] buildMatrix(ArrayList<Food> foodlist, boolean minimization) {
        final int rows = 23 + 2*foodlist.size();
        final int cols = 1 + foodlist.size();
        double[][] matrix = new double[rows][cols];

        for(int i = 0; i < rows; i++) {
            int k = 0;
            if(i < 22) {
                //for nutritional content
                for (int j = 0; j < foodlist.size(); j++) {
                    Food f = foodlist.get(j);
                    matrix[i][j] = f.getNutrionValue(i / 2);
                }

                matrix[i][cols-1] = Food.MAX_NUTRIENTS[i/2];
                i++;

                for (int j = 0; j < foodlist.size(); j++) {
                    Food f = foodlist.get(j);
                    matrix[i][j] = -1 * f.getNutrionValue(i / 2);
                }

                matrix[i][cols-1] = -1 * Food.MIN_NUTRIENTS[i/2];
                k = i-22;
            } else if(i < rows-1) {
                //for serving constraint
                matrix[i][k] = 1;
                matrix[i][cols-1] = 10;
                matrix[i+1][k] = -1;
                matrix[i+1][cols-1] = 0;
                i++;
                k++;
            } else {
                //objective function
                for(int j=0; j<foodlist.size(); j++) {
                    matrix[i][j] = -1 * foodlist.get(j).getPrice();
                }
            }
        }

        if(minimization) {
            matrix = transposeMatrix(matrix);
        }

        return matrix;
    }

    private static double[][] transposeMatrix(double[][] mat) {
        double[][] mat2 = new double[mat[0].length][mat.length];

        for(int i=0; i<mat.length; i++) {
            for(int j=0; j<mat[i].length; j++) {
                mat2[j][i] = mat[i][j];
            }
        }

        return mat2;
    }
}
