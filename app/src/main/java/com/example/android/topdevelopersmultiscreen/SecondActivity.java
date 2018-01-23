package com.example.android.topdevelopersmultiscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Adriana on 1/23/2018.
 */

public class SecondActivity extends AppCompatActivity {

    TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.secondPage_textView);

        Intent intent = getIntent();
        String item = intent.getStringExtra("selected-item");
        textView.setText("you selected " + item);
    }
}