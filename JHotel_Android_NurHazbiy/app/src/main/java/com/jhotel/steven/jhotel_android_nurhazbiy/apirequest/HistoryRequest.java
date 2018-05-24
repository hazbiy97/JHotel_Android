package com.jhotel.steven.jhotel_android_nurhazbiy.apirequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.Map;

/**
 *  This class is used for creating history API GET request
 *
 *  @author Nur Hazbiy Shaffan
 *  @version 1.0.0
 *  @since May 24 2018
 */
public class HistoryRequest extends StringRequest {
    private static final String Regis_URL = "http://10.0.2.2:8080/pesananhistory/";
    private Map<String, String> params;

    /**
     * Constructor history request
     *
     * @param id_customer costumer's ID
     * @param listener response listener success
     */
    public HistoryRequest(int id_customer, Response.Listener<String> listener,RequestErrorListener requestErrorListener) {
        super(Method.GET, Regis_URL + id_customer, listener, requestErrorListener);
    }
}
