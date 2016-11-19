package com.kintetsu.cmsc150.artificialdietician.util;

import com.kintetsu.cmsc150.artificialdietician.model.Food;

import java.util.ArrayList;

/**
 * Created by Aspire V3 on 11/19/2016.
 */

public class MatrixBuilder {
    private static int[][] buildTableu(ArrayList<Food> foodlist) {
        final int cols = 23 + 2*foodlist.size();
        final int rows = 24 + 3*foodlist.size();
        int[][] tableu = new int[rows][cols];

        return tableu;
    }
}
