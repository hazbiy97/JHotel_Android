package com.jhotel.steven.jhotel_android_nurhazbiy;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hazbiy on 06/05/18.
 */

public class PesananSelesaiRequest extends StringRequest {
    private static final String Regis_URL = "http://10.0.2.2:8080/finishpesanan";
    private Map<String, String> params;
    public PesananSelesaiRequest(int id_pesanan, Response.Listener<String> listener) {
        super(Method.POST, Regis_URL, listener, null);
        params = new HashMap<>();
        params.put("id_pesanan",String.valueOf(id_pesanan));
    }

    @Override
    public Map<String, String> getParams() { return params;
    }
}
