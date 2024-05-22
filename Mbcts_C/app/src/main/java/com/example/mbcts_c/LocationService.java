package com.example.mbcts_c;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.Manifest;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationService extends Service {
    private static final String CHANNEL_ID = "LocationServiceChannel";
    private static final int INTERVAL = 0000; // 10 seconds
    private Handler handler = new Handler();
    private Runnable runnable;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Child : Panic")
                .setContentText("Your child has pressed the panic button something is wrong!.")
                .setSmallIcon(R.drawable.ic_notification)
                .build();
        startForeground(1, notification);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        runnable = new Runnable() {
            @Override
            public void run() {
                requestLocation();
                handler.postDelayed(this, INTERVAL);
            }
        };
        handler.post(runnable);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    private void requestLocation() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(INTERVAL);
        locationRequest.setFastestInterval(INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult != null) {
                        for (Location location : locationResult.getLocations()) {
                            uploadLocation(location);
                        }
                    }
                }
            }, getMainLooper());
        }
    }

    private void uploadLocation(Location location) {
        // getting child_id
        FileInputStream fis = null;
        String fileContents = "";
        try {
            fis = openFileInput("MBCTS");
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            fileContents = stringBuilder.toString();
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(fileContents).getAsJsonObject();

            // Extract value of child_id
            String childId = jsonObject.get("child_id").getAsString();
            String guardianId = jsonObject.get("guardian_id").getAsString();


            Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
            Call<LocationData> call = methods.liveLocation(childId, location.getLatitude(),  location.getLongitude());
            call.enqueue(new Callback<LocationData>() {
                @Override
                public void onResponse(Call<LocationData> call, Response<LocationData> response) {
                    if (response.isSuccessful()) {
                        Log.d("LocationService", "Location uploaded successfully");
                    } else {
                        Log.d("LocationService", "Failed to upload location: " + response.message());
                    }
                }

                @Override
                public void onFailure(Call<LocationData> call, Throwable t) {
                    Log.d("LocationService", "Upload error: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, "Child Unavailable", Toast.LENGTH_SHORT).show();
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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Location Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }
}

