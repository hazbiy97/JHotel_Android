package com.jhotel.steven.jhotel_android_nurhazbiy.apirequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 *  This class is used for creating Cancel Order API POST request
 *
 *  @author Nur Hazbiy Shaffan
 *  @version 1.0.0
 *  @since May 24 2018
 */
public class PesananBatalRequest extends StringRequest {
    private static final String Regis_URL = "http://10.0.2.2:8080/cancelpesanan";
    private Map<String, String> params;

    /**
     * Constructor of PesananBatalRequest class
     *
     * @param id_pesanan order's ID
     * @param listener response success listener
     */
    public PesananBatalRequest(String id_pesanan, Response.Listener<String> listener,RequestErrorListener requestErrorListener) {
        super(Method.POST, Regis_URL, listener, requestErrorListener);
        params = new HashMap<>();
        params.put("id_pesanan",String.valueOf(id_pesanan));
    }

    /**
     * @return parameter value
     */
    @Override
    public Map<String, String> getParams() { return params;
    }
}
