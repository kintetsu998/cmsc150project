package com.kintetsu.cmsc150.artificialdietician.util;

import android.content.Context;
import android.util.Log;

import com.kintetsu.cmsc150.artificialdietician.model.Food;

import java.util.ArrayList;

/**
 * Created by Aspire V3 on 11/19/2016.
 */

public class MatrixBuilder {
    public static final String TAG = MatrixBuilder.class.getSimpleName();

    public static double[][] buildTableu(String[] constraints, Context c, boolean minimization) {
        double[][] matrix = buildMatrix(constraints, minimization);
        return createTableu(matrix, c);
    }

    public static double[][] buildTableu(ArrayList<Food> foodlist, Context c, boolean minimization) {
        double[][] matrix = buildMatrix(foodlist, minimization);
        return createTableu(matrix, c);
    }

    private static double[][] createTableu(double[][] matrix, Context c) {
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

    private static double[][] buildMatrix(String[] constraints, boolean minimization) {
        String[] variables = getVariables(constraints[constraints.length-1]);
        ArrayList<double[]> coeffs = new ArrayList<>();
        double[][] mat;
        String vars = "";

        for(String var : variables) {
            vars += var + " ";
        }
        Log.d(TAG, vars);

        for(String constraint : constraints) {
            double[] coeff = getCoeff(constraint, variables);
            coeffs.add(coeff);
        }

        mat = coeffs.toArray(new double[1][1]);
        if(minimization) {
            mat = transposeMatrix(mat);
        }

        for(int i=0; i<mat[0].length; i++) {
            mat[mat.length-1][i] *= -1;
        }

        return mat;
    }

    private static double[] getCoeff(String constraint, String[] vars) {
        double[] coeffs = new double[vars.length+1];
        String num = "";
        String var = "";
        String coeffStr = "";

        boolean isPositive = true;
        boolean foundChar = false;
        boolean foundEquals = false;

        int index = -1;
        char prevC = '\n';

        Log.d(TAG, constraint);
        for(char c : constraint.toCharArray()) {
            if(c == '+' || c == '-' || c == '<' || c == '>' || (c == '=' && (prevC != '<' || prevC != '>'))) {
                if(!num.equals("") || !var.equals("")) {
                    for(int i=0; i<vars.length; i++) {
                        if(var.equals(vars[i])) {
                            index = i;
                            break;
                        }
                    }
                    if(index == -1 && !foundEquals) {
                        throw new RuntimeException(constraint + " is not a valid constraint. Variable is " + var);
                    }

                    Double d = (num.equals(""))? 1 : Double.valueOf(num);
                    if(!isPositive) d*=-1;
                    coeffs[index] = d;
                }

                if(c == '-') {
                    isPositive = false;
                } else {
                    isPositive = true;
                }

                index = -1;
                foundChar = false;
                num = "";
                var = "";
            } else if((Character.isDigit(c) || c == '.') && !foundChar) {
                num += c;
            } else if(c == ' ' || c == '=') {
                continue;
            } else {
                foundChar = true;
                var += c;
            }

            prevC = c;
        }

        if(!constraint.contains("<") && !constraint.contains(">")) {
            coeffs[coeffs.length-1] = 0.0;
        } else {
            Double d = Double.valueOf(num);
            if(!isPositive) d*=-1;
            coeffs[coeffs.length-1] = d;
        }

        for(double co : coeffs) {
            coeffStr += Double.toString(co) + " ";
        }

        Log.d(TAG, coeffStr);
        return coeffs;
    }

    public static String[] getVariables(String objFunction) {
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
            } else if(((Character.isDigit(c) || c == '.') && !foundChar) || c == ' '){
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
                    matrix[i][j] = -1 * f.getNutrionValue(i / 2);
                }

                matrix[i][cols-1] = -1 * Food.MAX_NUTRIENTS[i/2];
                i++;

                for (int j = 0; j < foodlist.size(); j++) {
                    Food f = foodlist.get(j);
                    matrix[i][j] = f.getNutrionValue(i / 2);
                }

                matrix[i][cols-1] = Food.MIN_NUTRIENTS[i/2];
                k = i-22;
            } else if(i < rows-1) {
                //for serving constraint
                matrix[i][k] = -1;
                matrix[i][cols-1] = -10;
                matrix[i+1][k] = 1;
                matrix[i+1][cols-1] = 0;
                i++;
                k++;
            } else {
                //objective function
                for(int j=0; j<foodlist.size(); j++) {
                    matrix[i][j] = foodlist.get(j).getPrice();
                }
            }
        }

        if(minimization) {
            matrix = transposeMatrix(matrix);
        }

        for(int i=0; i<matrix[matrix.length-1].length; i++) {
            matrix[matrix.length-1][i] *= -1;
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
