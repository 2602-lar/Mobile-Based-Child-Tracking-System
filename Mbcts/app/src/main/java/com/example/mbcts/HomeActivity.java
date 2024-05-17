package com.example.mbcts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent i = getIntent();
        String guardian_id = i.getStringExtra("username");

        ((TextView) findViewById(R.id.textViewHeader)).setText("Welcome : " + guardian_id);
    }
}