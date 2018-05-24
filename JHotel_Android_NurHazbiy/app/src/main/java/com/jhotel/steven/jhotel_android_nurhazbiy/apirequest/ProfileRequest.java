package com.jhotel.steven.jhotel_android_nurhazbiy.apirequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 *  This class is used for creating profile request API GET request
 *
 *  @author Nur Hazbiy Shaffan
 *  @version 1.0.0
 *  @since May 24 2018
 */
public class ProfileRequest extends StringRequest {
    private static final String Regis_URL = "http://10.0.2.2:8080/getcustomer/";

    /**
     * Constructor of ProfileRequest class
     *
     * @param id_customer costumer's ID
     * @param listener response success listener
     */
    public ProfileRequest(int id_customer, Response.Listener<String> listener, Response.ErrorListener requestErrorListener) {
        super(Method.GET, Regis_URL + id_customer, listener, requestErrorListener);
    }
}
