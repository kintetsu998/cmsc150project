package com.kintetsu.cmsc150.artificialdietician;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.kintetsu.cmsc150.artificialdietician.model.Food;
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

        Intent i = getIntent();
        Database db = Database.getInstance(this);
        ArrayList<Food> food = db.getFood(i.getStringArrayExtra("foods"));
        double[] prevAns = i.getDoubleArrayExtra("ans");
        double[] ans = Arrays.copyOfRange(prevAns, prevAns.length-food.size()-1, prevAns.length);

        Button go_back = (Button) findViewById(R.id.go_back);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /*private void showAns(double[] ans) {
        for(double d : ans) {
            Log.d(TAG, Double.toString(d));
        }
    }*/
}
