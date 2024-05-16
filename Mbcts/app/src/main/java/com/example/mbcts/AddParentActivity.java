package com.example.mbcts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddParentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_parent);
    }

    public  void Submit (View v){

        String name = ((EditText) findViewById(R.id.txt_name)).getText().toString();
        String surname = ((EditText) findViewById((R.id.txt_surname))).getText().toString();
        String numberOfChildrenStr = ((EditText) findViewById(R.id.txt_numberOfChildren)).getText().toString();
        Integer numberOfChildren;
        try {
            numberOfChildren = Integer.parseInt(numberOfChildrenStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please provide a valid value for number of children !", Toast.LENGTH_SHORT).show();
            numberOfChildren = null; // or any other appropriate action
        }
        String address = ((EditText) findViewById(R.id.txt_address)).getText().toString();
        String phoneNumber = ((EditText) findViewById(R.id.txt_phone1)).getText().toString();
        String phoneNumber2 = ((EditText) findViewById(R.id.txt_phone2)).getText().toString();
        String relationship = ((EditText) findViewById(R.id.txt_relationship)).getText().toString();

       // Intent i = new Intent(this,  AddChildActivity.class);
        //startActivity(i);
        sendData(name, surname, numberOfChildren, address, phoneNumber, phoneNumber2, relationship);
    }

    public void sendData ( String name, String surname, Integer number_of_children,
                           String address, String phonenumber, String phonenumber_2,
                           String relationship){
        String guardian_id = "cal";
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Guardian> call = methods.SubmitGuardian(name, surname, address,number_of_children,  phonenumber,
                phonenumber_2, relationship, guardian_id
                );

        call.enqueue(new Callback<Guardian>() {
            @Override
            public void onResponse(Call<Guardian> call, Response<Guardian> response) {
                Guardian responseData = response.body();

                // Convert response data to JSON string using Gson
                Gson gson = new Gson();
                String jsonResponse = gson.toJson(responseData);

                // Log the JSON string
                Log.d("ApiService", "Response Data: " + jsonResponse);

                // Parse JSON string to JSON object
                JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

                // Access value with key "guardian_id"
                String guardianId = jsonObject.get("guardian_id").getAsString();

                // Log the guardian ID
                Log.d("ApiService", "Guardian ID: " + guardianId);

                proceed(guardianId);
            }



            @Override
            public void onFailure(Call<Guardian> call, Throwable t) {
                Toast.makeText(AddParentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void proceed(String guardian_id){
        Intent i = new Intent(this,  AddChildActivity.class);
        i.putExtra("guardian_id", guardian_id);
        startActivity(i);
    }
}