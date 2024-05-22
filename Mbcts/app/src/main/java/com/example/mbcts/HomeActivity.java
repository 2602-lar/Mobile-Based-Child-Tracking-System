package com.example.mbcts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent i = getIntent();
        String guardian_id = i.getStringExtra("username").toString();

        ((TextView) findViewById(R.id.textViewHeader)).setText("Welcome : " + guardian_id);
    }
    public void LiveTracking(View v){

        Intent l = getIntent();
        String guardian_id = l.getStringExtra("username");

        Intent i = new Intent(this, LiveLocationActivity.class);
        i.putExtra("guardian_id", guardian_id);
        startActivity(i);
    }

    public void GeoFence(View v){

        Intent l = getIntent();
        String guardian_id = l.getStringExtra("username");

        Intent i = new Intent(this, GeoFencingActivity.class);
        i.putExtra("guardian_id", guardian_id);
        startActivity(i);
    }

    public void Routine(View v){
        ((Button) findViewById(R.id.btn_routine)).setEnabled(false);
    }
}
