package com.jhotel.steven.jhotel_android_nurhazbiy.apirequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 *  This class is used for getting Order's data API POST request
 *
 *  @author Nur Hazbiy Shaffan
 *  @version 1.0.0
 *  @since May 24 2018
 */
public class PesananFetchRequest extends StringRequest {
    private static final String Regis_URL = "http://10.0.2.2:8080/pesanancustomer/";

    /**
     * Constructer of PesananFetchRequest class
     *
     * @param id_customer customer's ID
     * @param listener response success listener
     */
    public PesananFetchRequest(int id_customer, Response.Listener<String> listener,RequestErrorListener requestErrorListener) {
        super(Method.GET, Regis_URL+id_customer, listener,requestErrorListener );
    }
}
