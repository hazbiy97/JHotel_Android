package com.jhotel.steven.jhotel_android_nurhazbiy.APIRequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by hazbiy on 09/05/18.
 */

public class ProfileRequest extends StringRequest {
    private static final String Regis_URL = "http://10.0.2.2:8080/getcustomer/";
    public ProfileRequest(int id_customer, Response.Listener<String> listener) {
        super(Method.GET, Regis_URL + id_customer, listener, null);
    }
}
