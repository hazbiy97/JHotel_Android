package com.jhotel.steven.jhotel_android_nurhazbiy;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.Map;

/**
 * Created by hazbiy on 03/05/18.
 */

public class MenuRequest extends StringRequest {
    private static final String Regis_URL = "http://10.0.2.2:8080/vacantrooms";
    public MenuRequest(Response.Listener<String> listener) {
        super(Method.GET, Regis_URL, listener,null );
        //System.out.println("haiahai");
    }
}

