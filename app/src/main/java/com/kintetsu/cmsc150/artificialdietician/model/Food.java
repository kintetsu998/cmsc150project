package com.kintetsu.cmsc150.artificialdietician.model;

import android.database.Cursor;

import com.kintetsu.cmsc150.artificialdietician.util.Database;

import static com.kintetsu.cmsc150.artificialdietician.util.Database.FOOD_CALORIES;
import static com.kintetsu.cmsc150.artificialdietician.util.Database.FOOD_CHOLESTEROL;
import static com.kintetsu.cmsc150.artificialdietician.util.Database.FOOD_IRON;
import static com.kintetsu.cmsc150.artificialdietician.util.Database.FOOD_SODIUM;
import static com.kintetsu.cmsc150.artificialdietician.util.Database.FOOD_VIT_C;

/**
 * Created by Aspire V3 on 10/28/2016.
 */

public class Food {
    public static final int CALORIES     = 0;
    public static final int CHOLESTEROL  = 1;
    public static final int FAT          = 2;
    public static final int SODIUM       = 3;
    public static final int CARBS        = 4;
    public static final int FIBER        = 5;
    public static final int PROTEIN      = 6;
    public static final int VIT_A        = 7;
    public static final int VIT_C        = 8;
    public static final int CALCIUM      = 9;
    public static final int IRON         = 10;

    private String name;
    private String serveSize;
    private float price;
    private float calories;
    private float cholesterol;
    private float fat;
    private float sodium;
    private float carbs;
    private float fiber;
    private float protein;
    private float vitA;
    private float vitC;
    private float calcium;
    private float iron;


    public Food(Cursor res) {
        res.moveToFirst();

        this.name           = res.getString(res.getColumnIndex(Database.FOOD_NAME));
        this.serveSize      = res.getString(res.getColumnIndex(Database.FOOD_SERVE_SIZE));
        this.price          = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_PRICE)));
        this.calories       = Float.parseFloat(res.getString(res.getColumnIndex(FOOD_CALORIES)));
        this.cholesterol    = Float.parseFloat(res.getString(res.getColumnIndex(FOOD_CHOLESTEROL)));
        this.fat            = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_FAT)));
        this.sodium         = Float.parseFloat(res.getString(res.getColumnIndex(FOOD_SODIUM)));
        this.carbs          = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_CARBS)));
        this.fiber          = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_FIBER)));
        this.protein        = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_PROTEIN)));
        this.vitA           = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_VIT_A)));
        this.vitC           = Float.parseFloat(res.getString(res.getColumnIndex(FOOD_VIT_C)));
        this.calcium        = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_CALCIUM)));
        this.iron           = Float.parseFloat(res.getString(res.getColumnIndex(FOOD_IRON)));
    }

    public String getName() {
        return this.name;
    }

    public String getServeSize() {
        return this.serveSize;
    }

    public float getPrice() {
        return this.price;
    }

    public float getNutrionValue(int value) {
        switch(value) {
            case CALORIES:     return this.calories;
            case CHOLESTEROL : return this.cholesterol;
            case FAT:          return this.fat;
            case SODIUM:       return this.sodium;
            case CARBS:        return this.carbs;
            case FIBER:        return this.fiber;
            case PROTEIN:      return this.protein;
            case VIT_A:        return this.vitA;
            case VIT_C:        return this.vitC;
            case CALCIUM:      return this.calcium;
            case IRON:         return this.iron;
            default:                return 0.0f;
        }
    }

    public float[] getNutrionValue(){
        return new float[]{
                this.calories, this.cholesterol, this.fat, this.sodium, this.carbs, this.fiber,
                this.protein, this.vitA, this.vitC, this.calcium, this.iron
        };
    }

}
