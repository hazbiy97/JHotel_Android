package com.jhotel.steven.jhotel_android_nurhazbiy.apirequest;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 *  This class is used for creating queue for api request using volley
 *
 *  @author Nur Hazbiy Shaffan
 *  @version 1.0.0
 *  @since May 24 2018
 */
public class ApplicationVolley extends Application {
    // set instance value
    private static ApplicationVolley sInstance;
    private RequestQueue mRequestQueue;

    /**
     * overriding onCreate, creating volley request
     */
    @Override
    public void onCreate() {
        super.onCreate();

        mRequestQueue = Volley.newRequestQueue(this);

        sInstance = this;
    }

    /**
     * @return this class instance
     */
    public synchronized static ApplicationVolley getInstance() {
        return sInstance;
    }

    /**
     * @return created queue
     */
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
}
