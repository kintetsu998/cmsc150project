package com.kintetsu.cmsc150.artificialdietician;

import android.database.Cursor;

/**
 * Created by Aspire V3 on 10/28/2016.
 */

public class Food {
    public static final int FOOD_CALORIES     = 0;
    public static final int FOOD_CHOLESTEROL  = 1;
    public static final int FOOD_FAT          = 2;
    public static final int FOOD_SODIUM       = 3;
    public static final int FOOD_CARBS        = 4;
    public static final int FOOD_FIBER        = 5;
    public static final int FOOD_PROTEIN      = 6;
    public static final int FOOD_VIT_A        = 7;
    public static final int FOOD_VIT_C        = 8;
    public static final int FOOD_CALCIUM      = 9;
    public static final int FOOD_IRON         = 10;

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
        this.calories       = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_CALORIES)));
        this.cholesterol    = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_CHOLESTEROL)));
        this.fat            = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_FAT)));
        this.sodium         = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_SODIUM)));
        this.carbs          = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_CARBS)));
        this.fiber          = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_FIBER)));
        this.protein        = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_PROTEIN)));
        this.vitA           = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_VIT_A)));
        this.vitC           = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_VIT_C)));
        this.calcium        = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_CALCIUM)));
        this.iron           = Float.parseFloat(res.getString(res.getColumnIndex(Database.FOOD_IRON)));
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
            case FOOD_CALORIES:     return this.calories;
            case FOOD_CHOLESTEROL : return this.cholesterol;
            case FOOD_FAT:          return this.fat;
            case FOOD_SODIUM:       return this.sodium;
            case FOOD_CARBS:        return this.carbs;
            case FOOD_FIBER:        return this.fiber;
            case FOOD_PROTEIN:      return this.protein;
            case FOOD_VIT_A:        return this.vitA;
            case FOOD_VIT_C:        return this.vitC;
            case FOOD_CALCIUM:      return this.calcium;
            case FOOD_IRON:         return this.iron;
            default:                return 0.0f;
        }
    }

    public float[] getNutritionalValue(){
        return new float[]{
                this.calories, this.cholesterol, this.fat, this.sodium, this.carbs, this.fiber,
                this.protein, this.vitA, this.vitC, this.calcium, this.iron
        };
    }

}
