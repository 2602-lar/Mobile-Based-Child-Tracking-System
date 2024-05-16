package com.example.mbcts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddChildActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_child);

    }

    public  void Submit(View v){


        Intent i = getIntent();
        String guardian_id = i.getStringExtra("guardian_id");
        String firstname = ((EditText) findViewById(R.id.txt_name)).getText().toString();
        String surname = ((EditText) findViewById(R.id.txt_surname)).getText().toString();

        String date_of_birth = ((EditText) findViewById(R.id.txt_dob)).getText().toString();

        String agestr = ((EditText) findViewById(R.id.txt_age)).getText().toString();
        Integer age;
        try {
            age = Integer.parseInt(agestr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please provide a valid value for age!", Toast.LENGTH_SHORT).show();
            age = null; // or any other appropriate action
        }

        String number_of_guardiansstr = ((EditText) findViewById(R.id.txt_no_of_guardians)).getText().toString();
        Integer number_of_guardians;
        try {
            number_of_guardians = Integer.parseInt(number_of_guardiansstr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please provide a valid value for number of guardians !", Toast.LENGTH_SHORT).show();
            number_of_guardians = null; // or any other appropriate action
        }

        sendData(firstname, surname, date_of_birth, age, number_of_guardians, guardian_id);
    }

    public void sendData ( String firstname, String surname,
                           String date_of_birth, Integer age, Integer number_of_guardians, String guardian_id){
        String child_id = "Cal";
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Child> call = methods.SubmitChild(firstname, surname,
                date_of_birth,age,  number_of_guardians, child_id,
                 guardian_id
        );

        call.enqueue(new Callback<Child>() {
            @Override
            public void onResponse(Call<Child> call, Response<Child> response) {
                Child responseData = response.body();
                Log.d("Response", response.body().toString());
                // Convert response data to JSON string using Gson
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(responseData);

                // Log the JSON string
                Log.d("ApiService", "Response Data: " + jsonResponse);

                // Parse JSON string to JSON object
                JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

                // Access value with key "guardian_id"
                String guardianId = jsonObject.get("guardian_id").getAsString();
                String childId = jsonObject.get("child_id").getAsString();

                proceed(guardianId, childId);

            }



            @Override
            public void onFailure(Call<Child> call, Throwable t) {
                Toast.makeText(AddChildActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void proceed(String guardian_id,String child_id){
        Intent i = new Intent(this,  UserAccountActivity.class);
        i.putExtra("guardian_id", guardian_id);
        i.putExtra("child_id", child_id);
        startActivity(i);
    }
}