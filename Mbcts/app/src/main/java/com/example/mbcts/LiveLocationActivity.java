package com.example.mbcts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LiveLocationActivity extends AppCompatActivity {

    private TextView latitude;
    private TextView longitude;
    private TextView errortxt;
    private Button btn;
    private Handler handler;
    String latitude_val;
    String longitude_val;
    private Runnable locationRunnable;
    private static final long LOCATION_INTERVAL = 10000; // 20 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_location);

        handler = new Handler(Looper.getMainLooper());

        // Define the runnable to get the live location
        locationRunnable = new Runnable() {
            @Override
            public void run() {
                GetLiveLocation();
                // Schedule the runnable to run again after the interval
                handler.postDelayed(this, LOCATION_INTERVAL);
            }
        };
        handler.post(locationRunnable);

    }

    public void GetLiveLocation(){
        //sending request for tip
        Intent i = getIntent();
        String guardian_id = i.getStringExtra("guardian_id");
        longitude = findViewById(R.id.txt_longitude);
        latitude = findViewById(R.id.txt_Latitude);
        errortxt= findViewById(R.id.txt_error);
        btn = findViewById(R.id.btn_view_map);
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
                    latitude.setText(latitude_val);
                    longitude.setText(longitude_val);
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

    public void ViewLocation(View v){

        double lat_coordinates = Double.parseDouble(latitude_val);
        double long_coordinate = Double.parseDouble(longitude_val);
        btn = findViewById(R.id.btn_view_map);
        // Create a URI with the coordinates
        //Uri gmmIntentUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + latitude_val + "%2C" + longitude_val);
        Uri gmmIntentUri = Uri.parse("geo:" + latitude_val +"," + longitude_val);
        //Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
        //https://www.google.com/maps/search/?api=1&query=47.5951518%2C-122.3316393
        // Create an Intent to open Google Maps
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            btn.setEnabled(false);
            Toast.makeText(this, "Install Google maps to have access to this feature" + "geo:" + latitude_val + "," + longitude_val, Toast.LENGTH_SHORT).show();
        }
    }
}