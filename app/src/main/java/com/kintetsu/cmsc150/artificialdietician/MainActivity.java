package com.kintetsu.cmsc150.artificialdietician;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_toolbar);
        setSupportActionBar(toolbar);

        Button create_diet = (Button) findViewById(R.id.create_diet);
        Button ultim_opt = (Button) findViewById(R.id.ultim_opt);

        ultim_opt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddConstraintActivity.class);
                startActivity(i);
            }
        });

        create_diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ViewFoodActivity.class);
                startActivity(i);
            }
        });
    }

}
