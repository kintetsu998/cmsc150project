package com.kintetsu.cmsc150.artificialdietician;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aspire V3 on 10/28/2016.
 */

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "artificial_dietician.db";
    public static final int VERSION = 1;

    public static final String FOOD_TABLE = "food";
    public static final String FOOD_NAME = "food_name";
    public static final String FOOD_PRICE = "price";
    public static final String FOOD_SERV_SIZE = "serving_size";
    public static final String FOOD_CALORIES = "calories";
    public static final String FOOD_FAT = "fat";
    public static final String FOOD_CARBS = "carbohydrates";
    public static final String FOOD_FIBER = "fiber";
    public static final String FOOD_PROTEIN = "protein";
    public static final String FOOD_VIT_A = "vit_A";
    public static final String FOOD_VIT_C = "vit_C";
    public static final String FOOD_CALCIUM = "calcium";
    public static final String FOOD_IRON = "iron";

    public static final String RECIPE_TABLE = "recipe";
    public static final String RECIPE_NAME = "recipe_name";

    public static final String RECIPE_FOOD_TABLE = "recipe_food";
    public static final String RECIPE_FOOD_SERVE_AMT = "serve_amt";

    private static Database instance;

    private Database(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + FOOD_TABLE + " (" +
                FOOD_NAME       + " VARCHAR(255) PRIMARY KEY," +
                FOOD_PRICE      + " DECIMAL(5, 2) NOT NULL," +
                FOOD_SERV_SIZE  + " VARCHAR(63) NOT NULL," +
                FOOD_CALORIES   + " DECIMAL(6, 2) NOT NULL," +
                FOOD_FAT        + " DECIMAL(6, 2) NOT NULL," +
                FOOD_CARBS      + " DECIMAL(6, 2) NOT NULL," +
                FOOD_FIBER      + " DECIMAL(6, 2) NOT NULL," +
                FOOD_PROTEIN    + " DECIMAL(6, 2) NOT NULL," +
                FOOD_VIT_A      + " DECIMAL(6, 2) NOT NULL," +
                FOOD_VIT_C      + " DECIMAL(6, 2) NOT NULL," +
                FOOD_CALCIUM    + " DECIMAL(6, 2) NOT NULL," +
                FOOD_IRON       + " DECIMAL(6, 2) NOT NULL);"
        );

        db.execSQL("CREATE TABLE " + RECIPE_TABLE + " (" +
                RECIPE_NAME + " VARCHAR(255) PRIMARY KEY);"
        );

        db.execSQL("CREATE TABLE " + RECIPE_FOOD_TABLE + " (" +
                RECIPE_NAME             + " VARCHAR(255) NOT NULL," +
                FOOD_NAME               + " VARCHAR(255) NOT NULL," +
                RECIPE_FOOD_SERVE_AMT   + " INT NOT NULL," +
                "PRIMARY KEY (" + RECIPE_NAME + ", " + FOOD_NAME + ")," +
                "FOREIGN KEY (" + RECIPE_NAME + ") REFERENCES " +
                    RECIPE_TABLE + "(" + RECIPE_NAME + ") ON DELETE CASCADE," +
                "FOREIGN KEY (" + FOOD_NAME + ") REFERENCES " +
                    FOOD_TABLE + "(" + FOOD_NAME + ") ON DELETE CASCADE );"
        );

        populateTable(db);
    }

    private void populateTable(SQLiteDatabase db) {
        //TODO fill in initial data
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + RECIPE_FOOD_TABLE);
        onCreate(db);
    }

    public static Database getInstance(Context context) {
        if(instance == null) {
            instance = new Database(context);
        }

        return instance;
    }
}
