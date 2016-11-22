package com.kintetsu.cmsc150.artificialdietician;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kintetsu.cmsc150.artificialdietician.model.FoodAdapter;
import com.kintetsu.cmsc150.artificialdietician.util.Database;
import com.kintetsu.cmsc150.artificialdietician.util.MatrixBuilder;
import com.kintetsu.cmsc150.artificialdietician.util.Simplex;

import java.util.ArrayList;

public class ViewFoodActivity extends AppCompatActivity {
    private ArrayList<String> addedFood = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final Database db = Database.getInstance(ViewFoodActivity.this);
        final ArrayList<String> foods = db.getFoods();

        final Button selectAll = (Button) findViewById(R.id.select_all);
        final Button optimize = (Button) findViewById(R.id.optimize);

        final RecyclerView foodRecyclerView = (RecyclerView) findViewById(R.id.foodRecyclerView);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        final FoodAdapter foodAdapter = new FoodAdapter(foods, this, new FoodAdapter.OnItemClickListener() {
            @Override
            public void onItemCheck(String item) {
                boolean isFound = false;

                for (String str : addedFood) {
                    if(str.equals(item)) {
                        isFound = true;
                    }
                }

                if(!isFound) {
                    addedFood.add(item);
                }
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

        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: uncheck all if all are checked
                addedFood = foods;
                Toast.makeText(
                        ViewFoodActivity.this,
                        "Selected all of the foods!",
                        Toast.LENGTH_SHORT
                ).show();

                foodAdapter.checkAll();
            }
        });

        optimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double[][] tableu = MatrixBuilder.buildTableu(db.getFood(addedFood), ViewFoodActivity.this);
                double[] ans = Simplex.solve(tableu, ViewFoodActivity.this);
                if(ans != null) {
                    Toast.makeText(ViewFoodActivity.this, "Optimizing done!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ViewFoodActivity.this, "No solution found.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        foodRecyclerView.setLayoutManager(layoutManager);
        foodRecyclerView.setItemAnimator(new DefaultItemAnimator());
        foodRecyclerView.setAdapter(foodAdapter);
    }

    public ArrayList<String> getAddedFood() {
        return this.addedFood;
    }

}
