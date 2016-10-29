package com.kintetsu.cmsc150.artificialdietician.model;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.kintetsu.cmsc150.artificialdietician.InspectFoodActivity;
import com.kintetsu.cmsc150.artificialdietician.R;

import java.util.ArrayList;

/**
 * Created by Aspire V3 on 10/29/2016.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private ArrayList<String> foodList;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public FoodAdapter(ArrayList<String> foodList, Context context, OnItemClickListener listener) {
        this.foodList = foodList;
        this.context = context;
        this.onItemClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_list_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String food = foodList.get(position);

        holder.foodName.setText(food);
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(holder.checkBox.isChecked()) {
                    onItemClickListener.onItemCheck(food);
                } else {
                    onItemClickListener.onItemUncheck(food);
                }
            }
        });
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, InspectFoodActivity.class);
                i.putExtra("foodName", food);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public interface OnItemClickListener {
        void onItemCheck(String item);
        void onItemUncheck(String item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView foodName;
        public TextView details;
        public CheckBox checkBox;

        public ViewHolder(View itemView) {
            super(itemView);
            this.foodName = (TextView) itemView.findViewById(R.id.foodName);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.foodCheckBox);
            this.details = (TextView) itemView.findViewById(R.id.details);
        }
    }
}
