package com.jhotel.steven.jhotel_android_nurhazbiy.apirequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 *  This class is used for creating get Order API POST request
 *
 *  @author Nur Hazbiy Shaffan
 *  @version 1.0.0
 *  @since May 24 2018
 */
public class RegisterRequest extends StringRequest {
    private static final String Regis_URL = "http://10.0.2.2:8080/newcustomer";
    private Map<String, String> params;

    /**
     * Constructor of RegisterRequest class
     *
     * @param name costumer's name
     * @param email costumer's email
     * @param password costumer's password
     * @param listener response success listener
     */
    public RegisterRequest(String name, String email, String password, Response.Listener<String> listener, Response.ErrorListener requestErrorListener) {
        super(Method.POST, Regis_URL, listener, requestErrorListener);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email",email);
        params.put("password",password);
    }

    /**
     * returning parameter from requet
     *
     * @return parameter's value
     */
    @Override
    public Map<String, String> getParams() { return params;
    }

}

