package com.jhotel.steven.jhotel_android_nurhazbiy.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.jhotel.steven.jhotel_android_nurhazbiy.adapter.AlertDialogAdapter;
import com.jhotel.steven.jhotel_android_nurhazbiy.adapter.SessionManager;
import com.jhotel.steven.jhotel_android_nurhazbiy.apirequest.LoginRequest;
import com.jhotel.steven.jhotel_android_nurhazbiy.R;
import com.jhotel.steven.jhotel_android_nurhazbiy.apirequest.RequestErrorListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *  This class is used for creating Login activity on application
 *
 *  @author Nur Hazbiy Shaffan
 *  @version 1.0.0
 *  @since May 24 2018
 */
public class LoginActivity extends AppCompatActivity{
    // Session Manager Class
    private SessionManager session;

    // Alert Dialog Adapter Class
    private AlertDialogAdapter alertDialogAdapter = new AlertDialogAdapter();

    /**
     * This method is used to override onBackPressed method. This method will close application
     * when user pressing back button so the application didn't call activity from stackActivity
     * (hence calling MainActivity when user pressing back button after logging out)
     */
    @Override
    public void onBackPressed() {
        Log.d("CDA", "onBackPressed Called");

        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    /**
     * This metod is used to override onCreate method from activity to generate layout.
     *
     * @param savedInstanceState saved instance state from activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Session Manager
        session = new SessionManager(getApplicationContext());

        //Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

        // Session Manager
        session = new SessionManager(getApplicationContext());

        // Title Modifier
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        // Layout Mapping
        final EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        final EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        final Button loginButton = (Button) findViewById(R.id.loginButton);
        final TextView registerTextView = (TextView) findViewById(R.id.registerTextView);

        // set button listener
        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // hide keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                // get user input
                final String email = emailEditText.getText().toString();
                final String password = passwordEditText.getText().toString();

                // Check if username, password is filled
                if(email.trim().length() > 0 && password.trim().length() > 0) {
                    // set response listener for request
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                if (response != null && !response.isEmpty()) {
                                    JSONObject jsonResponse = new JSONObject(response);

                                    // get required data from response
                                    final int currentUserId = jsonResponse.getInt("id");
                                    final String email = jsonResponse.getString("email");

                                    // Creating user login session
                                    session.createLoginSession(currentUserId, email);

                                    // Change to Main Activity
                                    Intent regisInt = new Intent(LoginActivity.this, MainActivity.class);
                                    LoginActivity.this.startActivity(regisInt);
                                    finish();
                                } else {
                                    alertDialogAdapter.showAlertDialog(LoginActivity.this,"Login failed..","Username/Password is incorrect", null);
                                }
                            } catch (JSONException e) {
                                System.out.println(e.getMessage());
                                alertDialogAdapter.showAlertDialog(LoginActivity.this,"Login failed..","Error (1), please contact administrator", null);
                            }
                        }
                    };

                    // adding API request to Volley
                    LoginRequest loginRequest = new LoginRequest(email, password, responseListener, new RequestErrorListener("Login failed", "Check your internet connection", LoginActivity.this));
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                    queue.add(loginRequest);
                }else{
                    alertDialogAdapter.showAlertDialog(LoginActivity.this,"Login failed..","Please enter username and password", null);
                }
            }
        });

        // set reqister onClickListener
        registerTextView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // change to register activity
                Intent regisInt = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(regisInt);
            }
        });
    }
}
