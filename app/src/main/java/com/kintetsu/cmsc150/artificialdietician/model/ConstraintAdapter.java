package com.kintetsu.cmsc150.artificialdietician.model;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kintetsu.cmsc150.artificialdietician.R;

import java.util.ArrayList;

/**
 * Created by Aspire V3 on 11/27/2016.
 */

public class ConstraintAdapter extends RecyclerView.Adapter<ConstraintAdapter.ViewHolder> {
    private ArrayList<String> constraints;

    public ConstraintAdapter(ArrayList<String> constraints) {
        this.constraints = constraints;
    }

    public ConstraintAdapter() {
        this.constraints = new ArrayList<>();
    }

    @Override
    public ConstraintAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_constraint_list, parent, false);

        return new ConstraintAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ConstraintAdapter.ViewHolder holder, int position) {
        final String constraint = constraints.get(position);
        holder.constraint.setText(constraint);
    }

    @Override
    public int getItemCount() {
        return constraints.size();
    }

    public ArrayList<String> getConstraints() {
        return this.constraints;
    }

    public void clear() {
        this.constraints.clear();
    }

    public void addConstraint(String constraint) {
        this.constraints.add(constraint);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView constraint;

        public ViewHolder(View itemView) {
            super(itemView);
            this.constraint = (TextView) itemView.findViewById(R.id.constraint);
        }
    }
}
