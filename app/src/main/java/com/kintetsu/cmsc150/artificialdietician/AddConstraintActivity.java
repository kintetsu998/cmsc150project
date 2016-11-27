package com.kintetsu.cmsc150.artificialdietician;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kintetsu.cmsc150.artificialdietician.model.ConstraintAdapter;
import com.kintetsu.cmsc150.artificialdietician.util.MatrixBuilder;

import java.util.ArrayList;

public class AddConstraintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_constraint);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button add_obj =              (Button) findViewById(R.id.add_obj);
        final Button add_constraint =       (Button) findViewById(R.id.add_constraint);
        final Button reset =                (Button) findViewById(R.id.reset);
        final Button optimize =             (Button) findViewById(R.id.optimize);

        final TextView obj_text =           (TextView) findViewById(R.id.obj_text);

        final EditText obj_func_field =     (EditText) findViewById(R.id.obj_func_field);
        final EditText constraint_field =   (EditText) findViewById(R.id.constraint_field);

        final RecyclerView constraint_rv = (RecyclerView) findViewById(R.id.constraint_rv);
        final RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        final ConstraintAdapter constraintAdapter = new ConstraintAdapter();

        obj_func_field.setText("5x+3y-1z=Z");
        constraint_field.setText("1x+1y<=10");

        constraint_rv.setLayoutManager(layoutManager);
        constraint_rv.setItemAnimator(new DefaultItemAnimator());
        constraint_rv.setAdapter(constraintAdapter);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                constraintAdapter.clear();
                obj_text.setText(R.string.obj_function_temp);
                obj_func_field.setText("");
                constraint_field.setText("");
                add_obj.setEnabled(true);
            }
        });

        add_obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obj_text.setText(obj_func_field.getText());
                obj_func_field.setText("");
                add_obj.setEnabled(false);
            }
        });

        add_constraint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!constraint_field.getText().toString().trim().equals("")) {
                    constraintAdapter.addConstraint(constraint_field.getText().toString());
                    constraint_field.setText("");
                } else {
                    Toast.makeText(AddConstraintActivity.this, "Constraint field is empty!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        optimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(constraintAdapter.getItemCount() > 0 && !obj_text.getText().toString().equals("")) {
                    final ArrayList<String> constraints = new ArrayList<>(constraintAdapter.getConstraints());
                    constraints.add(obj_text.getText().toString());

                    //code from: http://stackoverflow.com/questions/2478517/how-to-display-a-yes-no-dialog-box-on-android
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            double[][] tableu;
                            switch (i) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    tableu = MatrixBuilder.buildTableu(
                                            constraints.toArray(new String[1]),
                                            AddConstraintActivity.this,
                                            true
                                    );

                                    break;
                                default:
                                    tableu = MatrixBuilder.buildTableu(
                                            constraints.toArray(new String[1]),
                                            AddConstraintActivity.this,
                                            false
                                    );

                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(AddConstraintActivity.this);
                    builder
                            .setMessage("Minimization?")
                            .setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener)
                            .show();
                } else if(constraintAdapter.getItemCount() > 0) {
                    Toast.makeText(
                            AddConstraintActivity.this,
                            "Empty constraints!",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(
                            AddConstraintActivity.this,
                            "Empty objective function!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
