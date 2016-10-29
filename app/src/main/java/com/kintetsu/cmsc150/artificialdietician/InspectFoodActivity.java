package com.kintetsu.cmsc150.artificialdietician;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kintetsu.cmsc150.artificialdietician.model.Food;
import com.kintetsu.cmsc150.artificialdietician.util.Database;

public class InspectFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspect_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String foodName = i.getStringExtra("foodName");
        Button close = (Button) findViewById(R.id.close);
        Database db = Database.getInstance(this);
        Food food = db.getFood(foodName);

        showDetails(food);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Toast.makeText(this, foodName, Toast.LENGTH_SHORT).show();
    }

    private void showDetails(Food food) {
        TextView foodNameView = (TextView) findViewById(R.id.foodNameView);
        TextView priceField = (TextView) findViewById(R.id.priceField);
        TextView serveSizeField = (TextView) findViewById(R.id.serveSizeField);
        TextView caloriesField = (TextView) findViewById(R.id.caloriesField);
        TextView cholesterolField = (TextView) findViewById(R.id.cholesterolField);
        TextView fatField = (TextView) findViewById(R.id.fatField);
        TextView sodiumField = (TextView) findViewById(R.id.sodiumField);
        TextView carbsField = (TextView) findViewById(R.id.carbsField);
        TextView fiberField = (TextView) findViewById(R.id.fiberField);
        TextView proteinField = (TextView) findViewById(R.id.proteinField);
        TextView vitAField = (TextView) findViewById(R.id.vitAField);
        TextView vitCField = (TextView) findViewById(R.id.vitCField);
        TextView calciumField = (TextView) findViewById(R.id.calciumField);
        TextView ironField = (TextView) findViewById(R.id.ironField);

        foodNameView.setText(food.getName());
        priceField.setText(Float.toString(food.getPrice()));
        serveSizeField.setText(food.getServeSize());
        caloriesField.setText(Float.toString(food.getNutrionValue(Food.CALORIES)));
        cholesterolField.setText(Float.toString(food.getNutrionValue(Food.CHOLESTEROL)));
        fatField.setText(Float.toString(food.getNutrionValue(Food.FAT)));
        sodiumField.setText(Float.toString(food.getNutrionValue(Food.SODIUM)));
        carbsField.setText(Float.toString(food.getNutrionValue(Food.CARBS)));
        fiberField.setText(Float.toString(food.getNutrionValue(Food.FIBER)));
        proteinField.setText(Float.toString(food.getNutrionValue(Food.PROTEIN)));
        vitAField.setText(Float.toString(food.getNutrionValue(Food.VIT_A)));
        vitCField.setText(Float.toString(food.getNutrionValue(Food.VIT_C)));
        calciumField.setText(Float.toString(food.getNutrionValue(Food.CALCIUM)));
        ironField.setText(Float.toString(food.getNutrionValue(Food.IRON)));
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
