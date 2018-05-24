package com.jhotel.steven.jhotel_android_nurhazbiy.apirequest;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 *  This class is used for creating order API POST request
 *
 *  @author Nur Hazbiy Shaffan
 *  @version 1.0.0
 *  @since May 24 2018
 */
public class BuatPesananRequest extends StringRequest {
    private static final String Regis_URL = "http://10.0.2.2:8080/bookpesanan";
    private Map<String, String> params;

    /**
     * Constructor of BuatPesananRequest
     *
     * @param jumlah_hari total of order's duration
     * @param id_customer costumer's ID
     * @param id_hotel hotel's ID
     * @param nomor_kamar room's number
     * @param listener response success listener
     * @param requestErrorListener response error listener
     */
    public BuatPesananRequest(int jumlah_hari, int id_customer, int id_hotel, String nomor_kamar, Response.Listener<String> listener, RequestErrorListener requestErrorListener) {
        super(Method.POST, Regis_URL, listener, requestErrorListener);
        params = new HashMap<>();
        params.put("jumlah_hari",String.valueOf(jumlah_hari));
        params.put("id_customer",String.valueOf(id_customer));
        params.put("id_hotel",String.valueOf(id_hotel));
        params.put("nomor_kamar",nomor_kamar);
    }

    /**
     * @return params from API request
     */
    @Override
    public Map<String, String> getParams() { return params;
    }

}
