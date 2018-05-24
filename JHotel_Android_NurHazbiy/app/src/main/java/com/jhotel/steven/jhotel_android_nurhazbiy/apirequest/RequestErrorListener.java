package com.jhotel.steven.jhotel_android_nurhazbiy.apirequest;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jhotel.steven.jhotel_android_nurhazbiy.activities.LoginActivity;
import com.jhotel.steven.jhotel_android_nurhazbiy.adapter.AlertDialogAdapter;

/**
 *  This class is used for creating response error listener
 *
 *  @author Nur Hazbiy Shaffan
 *  @version 1.0.0
 *  @since May 24 2018
 */
public class RequestErrorListener implements Response.ErrorListener{
    private String title;
    private String message;
    private Context _context;
    private AlertDialogAdapter alertDialogAdapter = new AlertDialogAdapter();

    /**
     * Constructor of RequestErrorListener class
     *
     * @param title Alert dialog's title
     * @param message Alert dialog's title
     * @param context Activity's context whereas this class created from
     */
    public RequestErrorListener(String title, String message, Context context){
        this.title = title;
        this.message = message;
        this._context = context;
    }

    /**
     * Method for showing alert dialog
     *
     * @param error error from Volley
     */
    @Override
    public void onErrorResponse(VolleyError error) {
        alertDialogAdapter.showAlertDialog(_context,title,message, null);
    }
}
