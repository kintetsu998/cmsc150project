package com.kintetsu.cmsc150.artificialdietician.util;

import android.content.Context;

import com.kintetsu.cmsc150.artificialdietician.model.Food;

import java.util.ArrayList;

/**
 * Created by Aspire V3 on 11/19/2016.
 */

public class MatrixBuilder {
    public static double[][] buildTableu(ArrayList<Food> foodlist, Context c) {
        final int rows = 23 + foodlist.size();
        final int cols = 24 + 2*foodlist.size();
        double[][] tableu = new double[rows][cols];

        for(int i = 0; i < rows; i++) {
            if(i < 22) {
                for (int j = 0; j < foodlist.size(); j++) {
                    Food f = foodlist.get(j);
                    tableu[i][j] = f.getNutrionValue(i / 2);
                }

                tableu[i][foodlist.size() + i] = 1;
                tableu[i][cols-1] = Food.MAX_NUTRIENTS[i/2];
                i++;

                for (int j = 0; j < foodlist.size(); j++) {
                    Food f = foodlist.get(j);
                    tableu[i][j] = -1 * f.getNutrionValue(i / 2);
                }

                tableu[i][foodlist.size() + i] = 1;
                tableu[i][cols-1] = -1 * Food.MIN_NUTRIENTS[i/2];
            } else if(i < rows-1) {
                tableu[i][i-22] = foodlist.get(i-22).getPrice();
                tableu[i][foodlist.size() + i] = 1;
                tableu[i][cols-1] = 10;
            } else {
                tableu[i][0] = -1;
                tableu[i][1] = -1;
                tableu[i][cols-2] = 1;
            }
        }

        tableu = transposeMatrix(tableu);

        FileUtil.writeTableu(tableu, c, "Initial Matrix", true);

        return tableu;
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
