package com.kintetsu.cmsc150.artificialdietician;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kintetsu.cmsc150.artificialdietician.model.ConstraintAdapter;
import com.kintetsu.cmsc150.artificialdietician.util.MatrixBuilder;
import com.kintetsu.cmsc150.artificialdietician.util.Simplex;

import java.util.ArrayList;

public class AddConstraintActivity extends AppCompatActivity {
    public static final String TAG = AddConstraintActivity.class.getSimpleName();
    public static final int TEST_CASE = 0;

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
        final ArrayList<String> constraints = createTestCase(obj_func_field, TEST_CASE);
        final ConstraintAdapter constraintAdapter = new ConstraintAdapter(constraints);

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
                            double[] ans;
                            boolean minimization;

                            String[] vars = MatrixBuilder.getVariables(obj_text.getText().toString());
                            String ansStr = "";
                            Intent intent = new Intent(AddConstraintActivity.this, UltimateSolutionActivity.class);
                            switch (i) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    tableu = MatrixBuilder.buildTableu(
                                            constraints.toArray(new String[1]),
                                            AddConstraintActivity.this,
                                            true
                                    );
                                    minimization = true;
                                    break;
                                default:
                                    tableu = MatrixBuilder.buildTableu(
                                            constraints.toArray(new String[1]),
                                            AddConstraintActivity.this,
                                            false
                                    );
                                    minimization = false;
                                    break;
                            }

                            ans = Simplex.solve(tableu, AddConstraintActivity.this, minimization);

                            if(ans != null) {
                                for (double d : ans) {
                                    ansStr += Double.toString(d) + " ";
                                }
                                Log.d(TAG, ansStr);
                            } else {
                                Log.d(TAG, "null is the answer.");
                            }

                            intent.putExtra("ans", ans);
                            intent.putExtra("vars", vars);
                            intent.putExtra("minimization", minimization);
                            startActivity(intent);
                            finish();
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

    private ArrayList<String> createTestCase(EditText obj_func_field, int ctr) {
        ArrayList<String> constraints = new ArrayList<>();
        switch(ctr) {
            case 1:
                obj_func_field.setText("1a+2b+1c-1d=Z");
                constraints.add("1a+1b+3c+4d<=60");
                constraints.add("1b+2c+5d<=50");
                constraints.add("2a+3b+6d<=72");
                break;
            case 2:
                obj_func_field.setText("1a+0.5b+2.5c+3d=W");
                constraints.add("1.5a+1b+2d>=35");
                constraints.add("2b+6c+4d>=120");
                constraints.add("1a+1b+1c+1d>=50");
                constraints.add("0.5a+2.5c+1.5d>=75");
                break;
            case 3:
                obj_func_field.setText("1a+1b+1c=W");
                constraints.add("1a+2b+c>=30");
                constraints.add("-6b-c>=-54");
                constraints.add("1a+1b+2c>=20");
                break;
            case 4:
                obj_func_field.setText("-200a-300b=Z");
                constraints.add("-2a-3b<=-1200");
                constraints.add("1a+1b<=400");
                constraints.add("-2a-1.5b<=900");
                break;
            case 5:
                obj_func_field.setText("0.12x+0.15y=w");
                constraints.add("60x+60y>=300");
                constraints.add("12x+6y>=36");
                constraints.add("10x+30y>=90");
                break;
        }
        return constraints;
    }

}
