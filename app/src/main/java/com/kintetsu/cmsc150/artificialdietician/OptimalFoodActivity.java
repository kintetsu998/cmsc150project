package com.kintetsu.cmsc150.artificialdietician;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OptimalFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_optimal_food);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String ans = parseAns((double[]) i.getExtras().get("ans"));
        TextView answer = (TextView) findViewById(R.id.answer);
        Button go_back = (Button) findViewById(R.id.go_back);

        answer.setText("answer: " + ans);
        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OptimalFoodActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private String parseAns(double[] ans) {
        String str = "";

        for(double d : ans) {
            str = Double.toString(d) + " ";
        }

        return str;
    }
}
