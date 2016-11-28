package com.kintetsu.cmsc150.artificialdietician;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

public class UltimateSolutionActivity extends AppCompatActivity {
    public static final String TAG = UltimateSolutionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ultimate_solution);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Intent in = getIntent();
        final String[] var = in.getStringArrayExtra("vars");
        final boolean minimization = in.getBooleanExtra("minimization", false);
        final TextView answer = (TextView) findViewById(R.id.answer);
        final Button go_back = (Button) findViewById(R.id.go_back);

        double[] ans = in.getDoubleArrayExtra("ans");

        String ansStr= "";

        if(ans != null) {
            if(!minimization) {
                for (int i = 0; i < var.length; i++) {
                    ansStr += var[i] + ": " + Double.toString(ans[i]) + "\n";
                }
            } else {
                ans = Arrays.copyOfRange(ans,ans.length-var.length-1, ans.length);
                for (int i = 0; i < var.length; i++) {
                    ansStr += var[i] + ": " + Double.toString(ans[i]) + "\n";
                }
            }
        } else {
            ansStr = "There is no optimal solution.";
        }

        answer.setText(ansStr);

        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
