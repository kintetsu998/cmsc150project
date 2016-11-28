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

}
