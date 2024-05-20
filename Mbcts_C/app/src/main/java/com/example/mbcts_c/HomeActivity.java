package com.example.mbcts_c;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private TextView safetyTpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        safetyTpTextView = findViewById(R.id.txt_safety_tip);
        try {
            Intent i = getIntent();
            String fileContents = i.getStringExtra("file");
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(fileContents).getAsJsonObject();

            // Extract value of child_id
            String childId = jsonObject.get("child_id").getAsString();
            String guardianId = jsonObject.get("guardian_id").getAsString();
            Log.d("File_contents", "child id = " + childId + " GuardianId = " + guardianId);
            ((TextView) findViewById(R.id.textLayoutHeader)).setText("Child : " + childId);

            //sending request for tip
            Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
            Call<ChildSetup> call = methods.HomeTip(childId);

            call.enqueue(new Callback<ChildSetup>() {
                @Override
                public void onResponse(Call<ChildSetup> call, Response<ChildSetup> response) {
                    ChildSetup responseData = response.body();
                    Log.d("Response", response.body().toString());
                    // Convert response data to JSON string using Gson
                    try {
                        Gson gson = new Gson();
                        String jsonResponse = gson.toJson(responseData);

                        // Log the JSON string
                        Log.d("ApiService", "Response Data: " + jsonResponse);

                        // Parse JSON string to JSON object
                        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

                        // Access value with key "guardian_id"
                        String tip = jsonObject.get("tip").getAsString();
                        safetyTpTextView.setText("Tip : " + tip);
                    }catch(Exception e){
                        safetyTpTextView.setText("error");
                    }

                }



                @Override
                public void onFailure(Call<ChildSetup> call, Throwable t) {
                    safetyTpTextView.setText("Network Error Open the app with internet connectivity");
                }
            });

        }catch (Exception e) {
        Intent i = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(i);
        }



    }

    public void playTicTacToe(View v){
        Intent i = new Intent(HomeActivity.this, TicTacToeActivity.class);
        startActivity(i);
    }

    public void Panic(View v){

    }

    public static String get_tip ( String child_id){
        final String[] serverResponse = new String[1];


        return (serverResponse[0]);

    }
}