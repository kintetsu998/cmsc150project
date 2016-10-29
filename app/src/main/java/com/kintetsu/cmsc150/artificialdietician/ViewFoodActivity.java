package com.kintetsu.cmsc150.artificialdietician;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.kintetsu.cmsc150.artificialdietician.model.FoodAdapter;
import com.kintetsu.cmsc150.artificialdietician.util.Database;

import java.util.ArrayList;

public class ViewFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayList<String> addedFood = new ArrayList<>();
        final Database db = Database.getInstance(ViewFoodActivity.this);
        final ArrayList<String> foods = db.getFoods();
        final RecyclerView foodRecyclerView = (RecyclerView) findViewById(R.id.foodRecyclerView);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        final FoodAdapter foodAdapter = new FoodAdapter(foods, this, new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemCheck(String item) {
                addedFood.add(item);
            }

            @Override
            public void onItemUncheck(String item) {
                for(String s : addedFood) {
                    if (s.equals(item)) {
                        addedFood.remove(s);
                        break;
                    }
                }
            }
        });

        foodRecyclerView.setLayoutManager(layoutManager);
        foodRecyclerView.setItemAnimator(new DefaultItemAnimator());
        foodRecyclerView.setAdapter(foodAdapter);
    }

}
