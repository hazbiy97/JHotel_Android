package com.jhotel.steven.jhotel_android_nurhazbiy.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.jhotel.steven.jhotel_android_nurhazbiy.adapter.AlertDialogAdapter;
import com.jhotel.steven.jhotel_android_nurhazbiy.apirequest.RegisterRequest;
import com.jhotel.steven.jhotel_android_nurhazbiy.R;
import com.jhotel.steven.jhotel_android_nurhazbiy.apirequest.RequestErrorListener;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *  This class is used for creating order activity on application
 *
 *  @author Nur Hazbiy Shaffan
 *  @version 1.0.0
 *  @since May 24 2018
 */
public class RegisterActivity extends AppCompatActivity {
    // Alert Dialog Adapter Class
    private AlertDialogAdapter alertDialogAdapter = new AlertDialogAdapter();

    /**
     * Override onCreat to generate layout when activity is on create
     *
     * @param savedInstanceState last saved instance state if activity is recreated
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // set action bar style
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar);

        // generate layout
        final EditText fullnameEditText = (EditText) findViewById(R.id.fullnameEditText);
        final EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        final EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        final Button registerButton = (Button) findViewById(R.id.registerButton );

        // set register button behaviour
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                // hide keyboard
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                // get value from layout
                final String name = fullnameEditText.getText().toString();
                final String email = emailEditText.getText().toString();
                final String pass = passwordEditText.getText().toString();

                // Check if username, password is filled
                if(email.trim().length() > 0 && pass.trim().length() > 0 && name.trim().length() > 0) {
                    // set response listener for request
                // set response listener
                Response.Listener<String> responseListener = new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        if (response.length() !=  0) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    Intent regisInt = new Intent(RegisterActivity.this, LoginActivity.class);
                                    RegisterActivity.this.startActivity(regisInt);}
                            })
                                    .setMessage("Registration Success")
                                    .setCancelable(false)
                                    .create()
                                    .show();
                        }
                        else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();}
                            })
                                    .setMessage("Registration Failed.\n" +
                                            "\nUse correct email and use at least 1 Upper word, 1 number greater than 8 character for password.")
                                    .setCancelable(false)
                                    .create()
                                    .show();
                        }
                    }
                };

                // add API request to volley
                RegisterRequest registerRequest = new RegisterRequest(name,email,pass,responseListener,new RequestErrorListener("Menu fetching failed", "Check your internet connection",RegisterActivity.this));
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
                } else {
                    alertDialogAdapter.showAlertDialog(RegisterActivity.this,"Register failed..","Please fill all field", null);
                }
            }
        });
    }
}