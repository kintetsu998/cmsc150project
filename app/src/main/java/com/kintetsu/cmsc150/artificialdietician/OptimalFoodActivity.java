package com.kintetsu.cmsc150.artificialdietician;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kintetsu.cmsc150.artificialdietician.model.Food;
import com.kintetsu.cmsc150.artificialdietician.model.OptimalFoodAdapter;
import com.kintetsu.cmsc150.artificialdietician.util.Database;

import java.util.ArrayList;
import java.util.Arrays;

public class OptimalFoodActivity extends AppCompatActivity {
    public static final String TAG = OptimalFoodActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimal_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent i = getIntent();
        final Database db = Database.getInstance(this);
        final ArrayList<Food> food = db.getFood(i.getStringArrayExtra("foods"));
        final double[] prevAns = i.getDoubleArrayExtra("ans");
        final double[] ans = Arrays.copyOfRange(prevAns, prevAns.length-food.size()-1, prevAns.length-1);
        final double totalCost = computeTotalCost(food, ans);

        final RecyclerView opt_food_rv = (RecyclerView) findViewById(R.id.opt_food_rv);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        final OptimalFoodAdapter adapter = new OptimalFoodAdapter(food.toArray(new Food[1]), ans, this);
        final TextView total_cost = (TextView) findViewById(R.id.total_cost);

        total_cost.setText("Total Cost: $" + Double.toString(totalCost));

        opt_food_rv.setLayoutManager(layoutManager);
        opt_food_rv.setItemAnimator(new DefaultItemAnimator());
        opt_food_rv.setAdapter(adapter);

        Button go_back = (Button) findViewById(R.id.go_back);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private double computeTotalCost(ArrayList<Food> foods, double[] servings) {
        double totalCost = 0.0;

        for(int i=0; i<foods.size(); i++) {
            totalCost += (double) foods.get(i).getPrice() * servings[i];
        }

        return Math.round(totalCost*100.0)/100.0;
    }

    /*private void showAns(double[] ans) {
        for(double d : ans) {
            Log.d(TAG, Double.toString(d));
        }
    }*/
}
