package com.example.mbcts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeoFencingActivity extends AppCompatActivity {

    private TextView currentLocationTxt;
    private TextView errortxt;
    private Button btn;
    String latitude_val;
    String longitude_val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_fencing);

        GetLiveLocation();
    }

    public void GetLiveLocation(){
        //sending request for tip
        Intent i = getIntent();
        String guardian_id = i.getStringExtra("guardian_id");
        currentLocationTxt = findViewById(R.id.txt_longitude);
        btn = findViewById(R.id.btn_activate_geo_fence);
        errortxt= findViewById(R.id.txt_error);

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<User> call = methods.LiveLocation(guardian_id);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User responseData = response.body();
                // Convert response data to JSON string using Gson
                try {
                    Gson gson = new Gson();
                    String jsonResponse = gson.toJson(responseData);

                    // Log the JSON string
                    Log.d("ApiService", "Response Data: " + jsonResponse);

                    // Parse JSON string to JSON object
                    JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

                    // Access value with key "guardian_id"
                    latitude_val = jsonObject.get("latitude").getAsString();
                    longitude_val = jsonObject.get("longitude").getAsString();
                    currentLocationTxt.setText(latitude_val + "," + longitude_val);
                }catch(Exception e){
                    errortxt.setText("error");
                    btn.setEnabled(false);
                }

            }



            @Override
            public void onFailure(Call<User> call, Throwable t) {
                errortxt.setText("Network Error Open the app with internet connectivity");
                btn.setEnabled(false);
            }
        });
    }

    public void ActivateGeoFence(View v) {
        String StrDistance = ((EditText) findViewById(R.id.txt_distance)).getText().toString();
        errortxt = findViewById(R.id.txt_error);
        Intent i = getIntent();
        String guardian_id = i.getStringExtra("guardian_id");

        if (StrDistance.length() == 0) {
            Toast.makeText(this, "Provide a valid distance", Toast.LENGTH_SHORT).show();
        } else {
            double distance = Double.parseDouble(StrDistance);
            try {
                Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
                Call<User> call = methods.SubmitGeoLock(guardian_id, distance);

                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User responseData = response.body();
                        // Convert response data to JSON string using Gson
                        try {
                            Gson gson = new Gson();
                            String jsonResponse = gson.toJson(responseData);

                            // Log the JSON string
                            Log.d("ApiService", "Response Data: " + jsonResponse);

                            // Parse JSON string to JSON object
                            JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

                            // Access value with key "guardian_id"
                            latitude_val = jsonObject.get("latitude").getAsString();
                            longitude_val = jsonObject.get("longitude").getAsString();
                            currentLocationTxt.setText(latitude_val + "," + longitude_val);
                        }catch(Exception e){
                            errortxt.setText("Device Geo Locked");
                            errortxt.setTextColor(Color.GREEN);
                            btn.setEnabled(false);
                        }

                    }



                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        errortxt.setText("Network Error Open the app with internet connectivity");
                        btn.setEnabled(false);
                    }
                });

            } catch (Exception e) {
                errortxt.setText("Error processing request");
            }
        }
    }
}