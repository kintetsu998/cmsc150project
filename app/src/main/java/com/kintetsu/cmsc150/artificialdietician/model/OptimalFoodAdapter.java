package com.kintetsu.cmsc150.artificialdietician.model;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kintetsu.cmsc150.artificialdietician.InspectFoodActivity;
import com.kintetsu.cmsc150.artificialdietician.R;

/**
 * Created by Aspire V3 on 11/28/2016.
 */

public class OptimalFoodAdapter extends RecyclerView.Adapter<OptimalFoodAdapter.ViewHolder> {
    private Activity activity;
    private Food[] foods;
    private double[] servings;

    public OptimalFoodAdapter(Food[] foods, double[] servings, Activity context) {
        this.foods = foods;
        this.servings = servings;
        this.activity = context;
    }

    @Override
    public OptimalFoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_opt_food, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return foods.length;
    }

    @Override
    public void onBindViewHolder(OptimalFoodAdapter.ViewHolder holder, int position) {
        final Food food = foods[position];
        final double serving = servings[position];

        holder.food_name.setText(food.getName());
        holder.food_cost.setText("Price per serving: " + Float.toString(food.getPrice()));
        holder.food_serving.setText("Serving: " + Double.toString(serving));
        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(activity, InspectFoodActivity.class);
                i.putExtra("foodName", food.getName());
                activity.startActivity(i);
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView food_name;
        TextView food_cost;
        TextView food_serving;
        Button detail;

        public ViewHolder(View itemView) {
            super(itemView);
            this.food_name = (TextView) itemView.findViewById(R.id.food_name);
            this.food_cost = (TextView) itemView.findViewById(R.id.food_cost);
            this.food_serving = (TextView) itemView.findViewById(R.id.food_serving);
            this.detail = (Button) itemView.findViewById(R.id.inspect);
        }
    }
}
