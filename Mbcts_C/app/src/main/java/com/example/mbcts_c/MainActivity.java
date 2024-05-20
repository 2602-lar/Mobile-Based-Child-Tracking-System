package com.example.mbcts_c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_LOCATION_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        } else {
            startLocationService();
        }

        FileInputStream fis = null;
        try {
            fis = openFileInput("MBCTS");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            String fileContents = stringBuilder.toString();
            Intent i = new Intent(this, HomeActivity.class);
            i.putExtra("file", fileContents);
            startActivity(i);
        } catch (Exception e) {
            Toast.makeText(this, "New Child Setup", Toast.LENGTH_SHORT).show();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void verify(View v){
        String childId = ((EditText) findViewById(R.id.txt_child_id)).getText().toString();
        String guardianId = ((EditText) findViewById(R.id.txt_parent_id)).getText().toString();

        if(childId.length() == 0 || guardianId.length() == 0){
            Toast.makeText(this, "PLease provide all details to proceed", Toast.LENGTH_SHORT).show();
        }else{
            verifyData(childId, guardianId);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationService();
            }
        }
    }
    private void startLocationService() {
        Intent serviceIntent = new Intent(this, LocationService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(this, serviceIntent);
        } else {
            startService(serviceIntent);
        }
    }

    public void verifyData (String child_id, String guardian_id){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<ChildSetup> call = methods.VerifyChild(child_id, guardian_id);

        call.enqueue(new Callback<ChildSetup>() {
            @Override
            public void onResponse(Call<ChildSetup> call, Response<ChildSetup> response) {
                ChildSetup responseData = response.body();
                if (responseData != null) {
                    Log.d("response_data", "Response Data: " + responseData);
                } else {
                    Log.e("response_data", "Response body is null");
                }
                try {
                    // Convert response data to JSON string using Gson
                    Gson gson = new Gson();
                    String jsonResponse = gson.toJson(responseData);
                    if (jsonResponse != null) {
                        // Log the JSON string
                        Log.d("ApiService_response", "Response Data: " + jsonResponse);

                        // Parse JSON string to JSON object
                        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

                        // Access value with key "guardian_id"
                        String child_id_response = jsonObject.get("child_id").getAsString();
                        String guardian_id_response = jsonObject.get("guardian_id").getAsString();
                        // Log the guardian ID
                        Toast.makeText(MainActivity.this, "Verification Complete", Toast.LENGTH_SHORT).show();

                        String filename = "MBCTS";
                        String fileContents = jsonObject.toString();
                        FileOutputStream fos = null;
                        try {
                            fos = openFileOutput(filename, Context.MODE_PRIVATE);
                            fos.write(fileContents.getBytes());
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (fos != null) {
                                try {
                                    fos.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        Intent i = new Intent(MainActivity.this,  HomeActivity.class);
                        i.putExtra("file", fileContents);
                        startActivity(i);
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Incorrect details! Child not found", Toast.LENGTH_SHORT).show();
                }
            }



            @Override
            public void onFailure(Call<ChildSetup> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("response_data", t.getMessage());

            }
        });
    }
}