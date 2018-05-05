package com.jhotel.steven.jhotel_android_nurhazbiy;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        final EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final TextView registerTextView = (TextView) findViewById(R.id.registerTextView);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String email = emailEditText.getText().toString();
                final String password = passwordEditText.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String> () {
                    @Override
                    public void onResponse (String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                                builder1.setMessage("Login success")
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e){
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(LoginActivity.this);
                            builder1.setMessage("Login failed")
                                    .create()
                                    .show();
                        }
                    }
                };
                LoginRequest loginRequest = new LoginRequest(email,password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });

        registerTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent regisInt = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(regisInt);
            }
        });
    }
}
