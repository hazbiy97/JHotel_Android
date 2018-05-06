package com.jhotel.steven.jhotel_android_nurhazbiy;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by hazbiy on 06/05/18.
 */

public class PesananFetchRequest extends StringRequest {
    private static final String Regis_URL = "http://10.0.2.2:8080/pesanancustomer/";
    public PesananFetchRequest(int id_customer, Response.Listener<String> listener) {
        super(Method.GET, Regis_URL+id_customer, listener,null );
    }
}
