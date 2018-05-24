package com.jhotel.steven.jhotel_android_nurhazbiy.fragment;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.jhotel.steven.jhotel_android_nurhazbiy.activities.MainActivity;
import com.jhotel.steven.jhotel_android_nurhazbiy.apirequest.*;
import com.jhotel.steven.jhotel_android_nurhazbiy.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.android.volley.VolleyLog.TAG;

/**
 *  This class is used for creating Order Fragment in Main Activity
 *
 *  @author Nur Hazbiy Shaffan
 *  @version 1.0.0
 *  @since May 24 2018
 **/
public class OrderFragment extends Fragment {
    private int currentUserId;

    private TextView idPesananTextView;
    private TextView biayaTextView;
    private TextView jumlahHariTextView ;
    private TextView tanggalPesananTextView;
    private Button batalPesananButton ;
    private Button selesaiPesananButton ;
    private ConstraintLayout consts;
    private int lastMenu;

    /**
     * Generating layout when Main Activity is created
     *
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        // Get Bundle Extra
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentUserId = bundle.getInt("currentUserId", 0);
            lastMenu = bundle.getInt("lastMenu", 0);
        }

        //System.out.println("Order "+ currentUserId);

        // Clear Layout
        consts = (ConstraintLayout) getView().findViewById(R.id.constratinLayoutPesanan);
        consts.setVisibility(View.GONE);

        idPesananTextView = (TextView) getView().findViewById(R.id.id_pesanan);
        biayaTextView = (TextView) getView().findViewById(R.id.biaya_pesanan);
        jumlahHariTextView = (TextView) getView().findViewById(R.id.jumlah_hari);
        tanggalPesananTextView = (TextView) getView().findViewById(R.id.tanggal_pesanan);
        batalPesananButton = (Button) getView().findViewById(R.id.batalPesanan);
        selesaiPesananButton = (Button) getView().findViewById(R.id.selesaiPesanan);

        batalPesananButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                //System.out.println("batal Clicked");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                builder1.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        ((MainActivity)getActivity()).changeMenu(lastMenu);
                                        //getActivity().getSupportFragmentManager().popBackStack();
                                    }
                                });
                                builder1.setMessage("Cancel Pesanan Berhasil")
                                .setCancelable(false)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException ex) {
                            System.out.println(ex.getMessage());
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setCancelable(false)
                                    .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                                    .setMessage("Cancel Pesanan Gagal")
                                    .create()
                                    .show();
                        }
                    }
                };
                PesananBatalRequest pesananBatalRequest = new PesananBatalRequest(idPesananTextView.getText().toString(),responseListener,new RequestErrorListener("Cancelling order failed", "Check your internet connection",getContext()));
                ApplicationVolley.getInstance().getRequestQueue().add(pesananBatalRequest);
            }
        });

        selesaiPesananButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                System.out.println("Selesai Clicked");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                                builder1.setCancelable(false)
                                        .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        ((MainActivity)getActivity()).changeMenu(lastMenu);
                                    }
                                })
                                    .setMessage("Selesaikan Pesanan Berhasil")
                                        .create()
                                        .show();
                            }
                        } catch (JSONException ex) {
                            System.out.println(ex.getMessage());
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder1.setMessage("Selesaikan Pesanan Gagal")
                                    .create()
                                    .show();
                        }
                    }
                };
                PesananSelesaiRequest pesananSelesaiRequest = new PesananSelesaiRequest(idPesananTextView.getText().toString(),responseListener,new RequestErrorListener("Finishing order failed", "Check your internet connection",getContext()));
                ApplicationVolley.getInstance().getRequestQueue().add(pesananSelesaiRequest);
            }
        });

    }

    /**
     * Refresh lists everytime fragment is resumed
     */
    @Override
    public void onResume(){
        super.onResume();

        // Get Bundle Extra
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentUserId = bundle.getInt("currentUserId", 0);
            lastMenu = bundle.getInt("lastMenu", 0);
            System.out.println("last"+lastMenu);
        }

        // refresh data
        fetchPesanan();
    }

    /**
     * Getting view value for generating layout
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     * @return current view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    /**
     * fetching orders data
     */
    private void fetchPesanan() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    Log.d(TAG,jsonResponse.toString());
                    if (jsonResponse != null) {
                        idPesananTextView.setText(String.valueOf(jsonResponse.getInt("id")));
                        biayaTextView.setText(String.valueOf(jsonResponse.getDouble("biaya")));
                        jumlahHariTextView.setText(String.valueOf(jsonResponse.getInt("jumlahHari")));

                        // parsing JSON String to date then to string with our own format
                        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                        Date date = inFormat.parse(jsonResponse.getString("tanggalPesanan"));
                        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy' at 'HH:mm");

                        //System.out.println(DATE_FORMAT.format(date));
                        tanggalPesananTextView.setText(DATE_FORMAT.format(date));
                        consts.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setCancelable(false)
                            .setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            // change to latest menu
                            ((MainActivity)getActivity()).changeMenu(lastMenu);

                            dialog.cancel();
                        }


                    })
                            .setMessage("Pesanan Kosong")
                            .create()
                            .show();
                } catch (ParseException e){
                    System.out.println(e.getMessage());
                }
            }
        };
        Log.d(TAG, String.valueOf(currentUserId));
        PesananFetchRequest pesananFetchRequest = new PesananFetchRequest(currentUserId,responseListener,new RequestErrorListener("Orders list fetching failed", "Check your internet connection",getContext()));
        ApplicationVolley.getInstance().getRequestQueue().add(pesananFetchRequest);
    }
}
