package com.kintetsu.cmsc150.artificialdietician;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Aspire V3 on 10/28/2016.
 */

public class Database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "artificial_dietician.db";
    public static final int VERSION = 1;

    public static final String FOOD_TABLE              = "food";
    public static final String FOOD_NAME               = "food_name";
    public static final String FOOD_PRICE              = "price";
    public static final String FOOD_SERVE_SIZE         = "serving_size";
    public static final String FOOD_CALORIES           = "calories";
    public static final String FOOD_CHOLESTEROL        = "cholesterol";
    public static final String FOOD_FAT                = "fat";
    public static final String FOOD_SODIUM             = "sodium";
    public static final String FOOD_CARBS              = "carbohydrates";
    public static final String FOOD_FIBER              = "fiber";
    public static final String FOOD_PROTEIN            = "protein";
    public static final String FOOD_VIT_A              = "vit_A";
    public static final String FOOD_VIT_C              = "vit_C";
    public static final String FOOD_CALCIUM            = "calcium";
    public static final String FOOD_IRON               = "iron";

    public static final String DIET_TABLE              = "diet";
    public static final String DIET_NAME               = "diet_name";

    public static final String DIET_FOOD_TABLE         = "diet_food";
    public static final String DIET_FOOD_SERVE_AMT     = "serve_amt";

    private static Database instance;

    private Database(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + FOOD_TABLE + " (" +
                FOOD_NAME           + " VARCHAR(255) PRIMARY KEY," +
                FOOD_PRICE          + " DECIMAL(5, 2) NOT NULL," +
                FOOD_SERVE_SIZE     + " VARCHAR(63) NOT NULL," +
                FOOD_CALORIES       + " DECIMAL(10, 2) NOT NULL," +
                FOOD_CHOLESTEROL    + " DECIMAL(10, 2) NOT NULL," +
                FOOD_FAT            + " DECIMAL(10, 2) NOT NULL," +
                FOOD_SODIUM         + " DECIMAL(10, 2) NOT NULL," +
                FOOD_CARBS          + " DECIMAL(10, 2) NOT NULL," +
                FOOD_FIBER          + " DECIMAL(10, 2) NOT NULL," +
                FOOD_PROTEIN        + " DECIMAL(10, 2) NOT NULL," +
                FOOD_VIT_A          + " DECIMAL(10, 2) NOT NULL," +
                FOOD_VIT_C          + " DECIMAL(10, 2) NOT NULL," +
                FOOD_CALCIUM        + " DECIMAL(10, 2) NOT NULL," +
                FOOD_IRON           + " DECIMAL(10, 2) NOT NULL);"
        );

        db.execSQL("CREATE TABLE " + DIET_TABLE + " (" +
                DIET_NAME + " VARCHAR(255) PRIMARY KEY);"
        );

        db.execSQL("CREATE TABLE " + DIET_FOOD_TABLE + " (" +
                DIET_NAME             + " VARCHAR(255) NOT NULL," +
                FOOD_NAME               + " VARCHAR(255) NOT NULL," +
                DIET_FOOD_SERVE_AMT   + " INT NOT NULL," +
                "PRIMARY KEY (" + DIET_NAME + ", " + FOOD_NAME + ")," +
                "FOREIGN KEY (" + DIET_NAME + ") REFERENCES " +
                DIET_TABLE + "(" + DIET_NAME + ") ON DELETE CASCADE," +
                "FOREIGN KEY (" + FOOD_NAME + ") REFERENCES " +
                    FOOD_TABLE + "(" + FOOD_NAME + ") ON DELETE CASCADE );"
        );

        populateTable(db);
    }

    private void populateTable(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Broccoli (Frozen)\", " +
                "0.16, \"10 Oz Pkg\", 73.8, 0, 0.8, 68.2, 13.6, 8.5, 8, 5867.4, 160.2, 159, 2.3);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Carrot (Raw)\", " +
                "0.07, \"1/2 Cup Shredded\", 23.7, 0, 0.1, 19.2, 5.6, 1.6, 0.6, 15471, 5.1, 14.9, 0.3);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Celery (Raw)\", " +
                "0.4, \"1 Stalk\", 6.4, 0, 0.1, 34.8, 1.5, 0.7, 0.3, 53.6, 2.8, 16, 0.2);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Corn (Frozen)\", " +
                "0.18, \"1/2 Cup\", 72.2, 0, 0.6, 2.5, 17.1, 2, 2.5, 106.6, 5.2, 3.3, 0.3);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Lettuce (Raw, Iceberg)\", " +
                "0.02, \"1 Leaf\", 2.6, 0, 0, 1.8, 0.4, 0.3, 0.2, 66, 0.8, 3.8, 0.1);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Peppers (Raw, Sweet)\", " +
                "0.53, \"1 Pepper\", 20, 0, 0.1, 1.5, 4.8, 1.3, 0.7, 467.7, 66.1, 6.7, 0.3);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Potato (Baked)\", " +
                "0.06, \"1/2 Cup\", 171.5, 0, 0.2, 15.2, 39.9, 3.2, 3.7, 0, 15.6, 22.7, 4.3);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Tofu\", " +
                "0.31, \"1/4 block\", 88.2, 0, 5.5, 8.1, 2.2, 1.4, 9.4, 98.6, 0.1, 121.8, 6.2);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Chicken (Roasted)\", " +
                "0.84, \"1 lb chicken\", 277.4, 129.9, 10.8, 125.6, 0, 0, 42.2, 77.4, 0, 21.9, 1.8);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Spaghetti (with Sauce)\", " +
                "0.78, \"1 1/2 Cup\", 358.2, 0, 12.3, 1237.1, 58.3, 11.6, 8.2, 3055.2, 27.9, 80.2, 2.3);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Tomato (Raw, Ripe, Red)\", " +
                "0.27, \"1 Tomato, 2-3/5 In\", 25.8, 0, 0.4, 11.1, 5.7, 1.4, 1, 766.3, 23.5, 6.2, 0.6);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Apple (Raw, with Skin)\", " +
                "0.24, \"1 Fruit, 3/Lb, Wo/Rf\", 81.4, 0, 0.5, 0, 21, 3.7, 0.3, 73.1, 7.9, 9.7, 0.2);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Banana\", " +
                "0.15, \"1 Fruit, Wo/Skn&Seeds\", 104.9, 0, 0.5, 1.1, 26.7, 2.7, 1.2, 92.3, 10.4, 6.8, 0.4);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Grapes\", " +
                "0.32, \"10 Fruits, Wo/Rf\", 15.1, 0, 0.1, 0.5, 4.1, 0.2, 0.2, 24, 1, 3.4, 0.1);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Kiwifruit (Raw, Fresh)\", " +
                "0.49, \"1 Med Frt, Wo/Skin\", 46.4, 0, 0.3, 3.8, 11.3, 2.6, 0.8, 133, 74.5, 19.8, 0.3);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Orange\", " +
                "0.15, \"1 Frt, 2-5/8 Diam\", 61.6, 0, 0.2, 0, 15.4, 3.1, 1.2, 268.6, 69.7, 52.4, 0.1);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Bagel\", " +
                "0.16, \"1 Oz\", 78, 0, 0.5, 151.4, 15.1, 0.6, 3, 0, 0, 21, 1);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Bread (Wheat)\", " +
                "0.05, \"1 Sl\", 65, 0, 1, 134.5, 12.4, 1.3, 2.2, 0, 0, 10.8, 0.7);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Bread (White)\", " +
                "0.06, \"1 Sl\", 65, 0, 1, 132.5, 11.8, 1.1, 2.3, 0, 0, 26.2, 0.8);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Cookie (Oatmeal)\", " +
                "0.09, \"1 Cookie\", 81, 0, 3.3, 68.9, 12.4, 0.6, 1.1, 2.9, 0.1, 6.7, 0.5);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Pie (Apple)\", " +
                "0.16, \"1 Oz\", 67.2, 0, 3.1, 75.4, 9.6, 0.5, 0.5, 35.2, 0.9, 3.1, 0.1);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Cookie (Chocolate Chip)\", " +
                "0.03, \"1 Cookie\", 78.1, 5.1, 4.5, 57.8, 9.3, 0, 0.9, 101.8, 0, 6.2, 0.4);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Butter (Regular)\", " +
                "0.05, \"1 Pat\", 35.8, 10.9, 4.1, 41.3, 0, 0, 0, 152.9, 0, 1.2, 0);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Cheese (Cheddar)\", " +
                "0.25, \"1 Oz\", 112.7, 29.4, 9.3, 173.7, 0.4, 0, 7, 296.5, 0, 202, 0.2);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Milk (Whole, 3.3% Fat)\", " +
                "0.16, \"1 C\", 149.9, 33.2, 8.1, 119.6, 11.4, 0, 8, 307.4, 2.3, 291.3, 0.1);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Milk (Low Fat, 2% Fat)\", " +
                "0.23, \"1 C\", 121.2, 18.3, 4.7, 121.8, 11.7, 0, 8.1, 500.2, 2.3, 296.7, 0.1);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Milk (Skim)\", " +
                "0.13, \"1 C\", 85.5, 4.4, 0.4, 126.2, 11.9, 0, 8.4, 499.8, 2.4, 302.3, 0.1);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Egg (Poached)\", " +
                "0.08, \"Lrg Egg\", 74.5, 211.5, 5, 140, 0.6, 0, 6.2, 316, 0, 24.5, 0.7);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Egg (Scrambled)\", " +
                "0.11, \"Lrg Egg\", 99.6, 211.2, 7.3, 168, 1.3, 0, 6.7, 409.2, 0.1, 42.6, 0.7);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Bologna Turkey\", " +
                "0.15, \"1 Oz\", 56.4, 28.1, 4.3, 248.9, 0.3, 0, 3.9, 0, 0, 23.8, 0.4);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Frankfurter (Beef)\", " +
                "0.27, \"1 Frankfurter\", 141.8, 27.4, 12.8, 461.7, 0.8, 0, 5.4, 0, 10.8, 9, 0.6);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Ham (Extra Lean, Sliced)\", " +
                "0.33, \"1 Sl, 6-1/4x4x1/16 In\", 37.1, 13.3, 1.4, 405.1, 0.3, 0, 5.5, 0, 7.4, 2, 0.2);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Kielbasa (Pork)\", " +
                "0.15, \"1 Sl,6x3-3/4x1/16 In\", 80.6, 17.4, 7.1, 279.8, 0.6, 0, 3.4, 0, 5.5, 11.4, 0.4);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Cap'N Crunch\", " +
                "0.31, \"1 Oz\", 119.6, 0, 2.6, 213.3, 23, 0.5, 1.4, 40.6, 0, 4.8, 7.5);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Cheerios\", " +
                "0.28, \"1 Oz\", 111, 0, 1.8, 307.6, 19.6, 2, 4.3, 1252.2, 15.1, 48.6, 4.5);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Kellogg's Corn Flakes\", " +
                "0.28, \"1 Oz\", 110.5, 0, 0.1, 290.5, 24.5, 0.7, 2.3, 1252.2, 15.1, 0.9, 1.8);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Kellogg's Raisin Bran\", " +
                "0.34, \"1.3 Oz\", 115.1, 0, 0.7, 204.4, 27.9, 4, 4, 1250.2, 0, 12.9, 16.8);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Rice Krispies\", " +
                "0.32, \"1 Oz\", 112.2, 0, 0.2, 340.8, 24.8, 0.4, 1.9, 1252.2, 15.1, 4, 1.8);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Special K\", " +
                "0.38, \"1 Oz\", 110.8, 0, 0.1, 265.5, 21.3, 0.7, 5.6, 1252.2, 15.1, 8.2, 4.5);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Oatmeal\", " +
                "0.82, \"1 C\", 145.1, 0, 2.3, 2.3, 25.3, 4, 6.1, 37.4, 0, 18.7, 1.6);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Malt-O-Meal Chocolate\", " +
                "0.52, \"1 C\", 607.2, 0, 1.5, 16.5, 128.2, 0, 17.3, 0, 0, 23.1, 47.2);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Pizza (with Pepperoni)\", " +
                "0.44, \"1 Slice\", 181, 14.2, 7, 267, 19.9, 0, 10.1, 281.9, 1.6, 64.6, 0.9);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Hamburger (with Toppings)\", " +
                "0.83, \"1 Burger\", 275, 42.8, 10.2, 563.9, 32.7, 0, 13.6, 126.3, 2.6, 51.4, 2.5);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Hotdog (Plain)\", " +
                "0.31, \"1 Hotdog\", 242.1, 44.1, 14.5, 670.3, 18, 0, 10.4, 0, 0.1, 23.5, 2.3);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Couscous\", " +
                "0.39, \"1/2 Cup\", 100.8, 0, 0.1, 4.5, 20.9, 1.3, 3.4, 0, 0, 7.2, 0.3);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"White Rice\", " +
                "0.08, \"1/2 Cup\", 102.7, 0, 0.2, 0.8, 22.3, 0.3, 2.1, 0, 0, 7.9, 0.9);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Macaroni (Cooked)\", " +
                "0.17, \"1/2 Cup\", 98.7, 0, 0.5, 0.7, 19.8, 0.9, 3.3, 0, 0, 4.9, 1);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Peanut Butter\", " +
                "0.07, \"2 Tbsp\", 188.5, 0, 16, 155.5, 6.9, 2.1, 7.7, 0, 0, 13.1, 0.6);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Pork\", " +
                "0.81, \"4 Oz\", 710.8, 105.1, 72.2, 38.4, 0, 0, 13.8, 14.7, 0, 59.9, 0.4);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Sardines (in Oil)\", " +
                "0.45, \"2 Sardines\", 49.9, 34.1, 2.7, 121.2, 0, 0, 5.9, 53.8, 0, 91.7, 0.7);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Tuna (White, in Water)\", " +
                "0.69, \"3 Oz\", 115.6, 35.7, 2.1, 333.2, 0, 0, 22.7, 68, 0, 3.4, 0.5);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Popcorn (Air Popped)\", " +
                "0.04, \"1 Oz\", 108.3, 0, 1.2, 1.1, 22.1, 4.3, 3.4, 55.6, 0, 2.8, 0.8);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Potato Chips (BBQ Flavor)\", " +
                "0.22, \"1 Oz\", 139.2, 0, 9.2, 212.6, 15, 1.2, 2.2, 61.5, 9.6, 14.2, 0.5);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Pretzels\", " +
                "0.12, \"1 Oz\", 108, 0, 1, 486.2, 22.5, 0.9, 2.6, 0, 0, 10.2, 1.2);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Tortilla Chip\", " +
                "0.19, \"1 Oz\", 142, 0, 7.4, 149.7, 17.8, 1.8, 2, 55.6, 0, 43.7, 0.4);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Chicken Noodle Soup\", " +
                "0.39, \"1 C (8 Fl Oz)\", 150.1, 12.3, 4.6, 1862.2, 18.7, 1.5, 7.9, 1308.7, 0, 27.1, 1.5);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Split Pea and Ham Soup\", " +
                "0.67, \"1 C (8 Fl Oz)\", 184.8, 7.2, 4, 964.8, 26.8, 4.1, 11.1, 4872, 7, 33.6, 2.1);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Vegetable Beef Soup\", " +
                "0.71, \"1 C (8 Fl Oz)\", 158.1, 10, 3.8, 1915.1, 20.4, 4, 11.2, 3785.1, 4.8, 32.6, 2.2);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Clam Chowder (New England)\", " +
                "0.75, \"1 C (8 Fl Oz)\", 175.7, 10, 5, 1864.9, 21.8, 1.5, 10.9, 20.1, 4.8, 82.8, 2.8);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Tomato Soup\", " +
                "0.39, \"1 C (8 Fl Oz)\", 170.7, 0, 3.8, 1744.4, 33.2, 1, 4.1, 1393, 133, 27.6, 3.5);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Clam Chowder (New England, with Milk)\", " +
                "0.99, \"1 C (8 Fl Oz)\", 163.7, 22.3, 6.6, 992, 16.6, 1.5, 9.5, 163.7, 3.5, 186, 1.5);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Cream Mushroom Soup (with Milk)\", " +
                "0.65, \"1 C (8 Fl Oz)\", 203.4, 19.8, 13.6, 1076.3, 15, 0.5, 6.1, 153.8, 2.2, 178.6, 0.6);");
        db.execSQL("INSERT INTO " + FOOD_TABLE + " VALUES (\"Bean Bacon Soup (with Water)\", " +
                "0.67, \"1 C (8 Fl Oz)\", 172, 2.5, 5.9, 951.3, 22.8, 8.6, 7.9, 888, 1.5, 81, 2);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + FOOD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DIET_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DIET_FOOD_TABLE);
        onCreate(db);
    }

    public Food getFood(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + FOOD_TABLE + " WHERE " + FOOD_NAME + " = ?";
        String[] field = new String[]{name};
        Cursor res = db.rawQuery(query, field);
        Food f = new Food(res);

        res.close();
        return f;
    }

    public ArrayList<String> getFoods() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + FOOD_NAME + " FROM " + FOOD_TABLE + " ORDER BY " + FOOD_NAME;
        ArrayList<String> foodNames = new ArrayList<>();
        Cursor res = db.rawQuery(query, null);
        res.moveToFirst();

        while(!res.isLast()) {
            foodNames.add(res.getString(res.getColumnIndex(FOOD_NAME)));
            res.moveToNext();
        }

        res.close();
        return foodNames;
    }

    public Diet getDiet(String name) {
        //TODO Get the diet and the food included given diet name
        return null;
    }

    public ArrayList<String> getDiets() {
        //TODO Get all diet names
        return null;
    }

    public static Database getInstance(Context context) {
        if(instance == null) {
            instance = new Database(context);
        }

        return instance;
    }
}
