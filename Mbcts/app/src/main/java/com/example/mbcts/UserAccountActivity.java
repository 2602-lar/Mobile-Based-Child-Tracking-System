package com.example.mbcts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);

        Intent i = getIntent();
        String guardian_id = i.getStringExtra("guardian_id");
        String child_id = i.getStringExtra("child_id");

        ((TextView) findViewById(R.id.textViewHeader)).setText(
                "Guardian ID : " + guardian_id +System.lineSeparator()+ " Child ID : "+child_id
                );
        ((EditText) findViewById(R.id.txt_username_readonly)).setText(guardian_id);
    }

    public void Finish (View v){
        String username = ((EditText) findViewById(R.id.txt_username_readonly)).getText().toString();
        String password = ((EditText) findViewById(R.id.txt_password)).getText().toString();
        String conPassword = ((EditText) findViewById(R.id.txt_con_password)).getText().toString();

        if(password.equals(conPassword)){
            sendData(username, password);
        }else{
            Toast.makeText(this, "Passwords provided do not match" + password + " " + conPassword, Toast.LENGTH_SHORT).show();
        }
    }

    public void sendData ( String username, String password){
        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<User> call = methods.SubmitUser(username, password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User responseData = response.body();

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
                        String message = jsonObject.get("message").getAsString();

                        Log.d("ApiService_message", message);
                        // Log the guardian ID
                        Toast.makeText(UserAccountActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(UserAccountActivity.this, "User successfully created", Toast.LENGTH_SHORT).show();
                }
                Intent i = new Intent(UserAccountActivity.this,  MainActivity.class);
                startActivity(i);
            }



            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UserAccountActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


}

