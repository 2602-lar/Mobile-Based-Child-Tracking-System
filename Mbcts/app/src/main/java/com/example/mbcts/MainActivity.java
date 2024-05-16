package com.example.mbcts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View v){
        String txt_username = ((EditText) findViewById(R.id.txt_username)).getText().toString();
        String txt_password = ((EditText) findViewById(R.id.txt_password)).getText().toString();

        if(txt_password.equals("") || txt_username.equals("")){
            Toast.makeText(this, "Please provide all the details to login !", Toast.LENGTH_SHORT).show();
        }else {
            ((Button) v).setText("Authenticating...");
            login_request(txt_username, txt_password);
        }
    }


    public void login_request (String username, String password){

        Methods methods = RetrofitClient.getRetrofitInstance().create(Methods.class);
        Call<Model> call = methods.getUserData(username, password);

        call.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                Toast.makeText(MainActivity.this,response.toString(),Toast.LENGTH_SHORT).show();
                login_success(username);
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                try {
                    JSONObject errorJson = new JSONObject(t.getMessage());
                    String errorMessage = errorJson.optString("message", "An error occurred");
                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    // JSON parsing error, handle it
                    Toast.makeText(MainActivity.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void login_success(String username){
        Intent i = new Intent(this,  HomeActivity.class);
        i.putExtra("username", username);
        startActivity(i);
    }

    public void Register(View v){
        Intent i = new Intent(this, AddParentActivity.class);
        startActivity(i);
    }

}