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
import com.kintetsu.cmsc150.artificialdietician.ViewFoodActivity;

import java.util.ArrayList;

/**
 * Created by Aspire V3 on 10/29/2016.
 */

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private ArrayList<String> foodList;
    private ArrayList<CheckBox> boxes;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public FoodAdapter(ArrayList<String> foodList, Context context, OnItemClickListener listener) {
        this.foodList = foodList;
        this.context = context;
        this.onItemClickListener = listener;
        boxes = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_food_list, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final String food = foodList.get(position);

        holder.foodName.setText(food);
        holder.checkBox.setOnCheckedChangeListener(null);
        holder.checkBox.setChecked(checkOnList(food));
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

        addToBoxes(holder.checkBox);
    }

    private void addToBoxes(CheckBox checkbox) {
        boolean exists = false;

        for(CheckBox cb : boxes) {
            if(cb == checkbox) {
                exists = true;
                break;
            }
        }

        if(!exists) {
            boxes.add(checkbox);
        }
    }

    private boolean checkOnList(String food) {
        for(String str : ((ViewFoodActivity) context).getAddedFood()) {
            if(food.equals(str)) {
                return true;
            }
        }

        return false;
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
        private TextView foodName;
        private TextView details;
        private CheckBox checkBox;

        private ViewHolder(View itemView) {
            super(itemView);
            this.foodName = (TextView) itemView.findViewById(R.id.foodName);
            this.checkBox = (CheckBox) itemView.findViewById(R.id.foodCheckBox);
            this.details = (TextView) itemView.findViewById(R.id.details);
        }
    }

    public void checkAll() {
        for(CheckBox cb : boxes) {
            cb.setChecked(true);
        }
    }

    public void unCheckAll() {
        for(CheckBox cb : boxes) {
            cb.setChecked(false);
        }
    }

    public ArrayList<String> getFoodList() {
        return this.foodList;
    }
}
