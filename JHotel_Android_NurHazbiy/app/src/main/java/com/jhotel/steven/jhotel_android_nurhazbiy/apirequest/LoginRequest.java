package com.jhotel.steven.jhotel_android_nurhazbiy.apirequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 *  This class is used for creating Login API POST request
 *
 *  @author Nur Hazbiy Shaffan
 *  @version 1.0.0
 *  @since May 24 2018
 */
public class LoginRequest extends StringRequest {
    private static final String Regis_URL = "http://10.0.2.2:8080/logincust";
    private Map<String, String> params;

    /**
     * Constructor of LoginRequest Class
     *
     * @param email costumer's email
     * @param password costumer's password
     * @param listener response success listener
     * @param requestErrorListener response error listener
     */
    public LoginRequest(String email, String password, Response.Listener<String> listener, RequestErrorListener requestErrorListener) {
        super(Method.POST, Regis_URL, listener, requestErrorListener);
        params = new HashMap<>();
        params.put("email",email);
        params.put("password",password);
    }

    /**
     * Get param method
     *
     * @return param's value
     */
    @Override
    public Map<String, String> getParams() { return params;
    }

}
