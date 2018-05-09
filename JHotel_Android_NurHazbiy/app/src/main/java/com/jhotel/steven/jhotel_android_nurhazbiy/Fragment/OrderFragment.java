package com.jhotel.steven.jhotel_android_nurhazbiy.Fragment;


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
import com.jhotel.steven.jhotel_android_nurhazbiy.APIRequest.*;
import com.jhotel.steven.jhotel_android_nurhazbiy.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.android.volley.VolleyLog.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    public OrderFragment() {
        // Required empty public constructor
    }

    private int currentUserId;

    private TextView idPesananTextView;
    private TextView biayaTextView;
    private TextView jumlahHariTextView ;
    private TextView tanggalPesananTextView;
    private Button batalPesananButton ;
    private Button selesaiPesananButton ;
    private ConstraintLayout consts;
    private View view;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        //TODO Clear Layout
        consts = (ConstraintLayout) getView().findViewById(R.id.constratinLayoutPesanan);
        consts.setVisibility(View.GONE);

        //TODO Get Bundle Extra
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentUserId = bundle.getInt("currentUserId", 0);
        }

        idPesananTextView = (TextView) getView().findViewById(R.id.id_pesanan);
        biayaTextView = (TextView) getView().findViewById(R.id.biaya_pesanan);
        jumlahHariTextView = (TextView) getView().findViewById(R.id.jumlah_hari);
        tanggalPesananTextView = (TextView) getView().findViewById(R.id.tanggal_pesanan);
        batalPesananButton = (Button) getView().findViewById(R.id.batalPesanan);
        selesaiPesananButton = (Button) getView().findViewById(R.id.selesaiPesanan);

        batalPesananButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                System.out.println("batal Clicked");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null) {
                                DialogFragment newFragment = new DialogFragment();
                                newFragment.show(getFragmentManager(), "dialog");
                            }
                        } catch (JSONException ex) {
                            System.out.println(ex.getMessage());
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                    //TODO finish activity and change activity from stack
                                }
                            });
                            builder1.setMessage("Cancel Pesanan Gagal")
                                    .create()
                                    .show();
                        }
                    }
                };
                PesananBatalRequest pesananBatalRequest = new PesananBatalRequest(idPesananTextView.getText().toString(),responseListener);
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
                                builder1.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();

                                        //TODO finish activity and change activity from stack
                                    }
                                });
                                builder1.setMessage("Selesaikan Pesanan Berhasil")
                                        .create()
                                        .show();
                            }
                        } catch (JSONException ex) {
                            System.out.println(ex.getMessage());
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                            builder1.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();

                                    //TODO finish activity and change activity from stack
                                }
                            });
                            builder1.setMessage("Selesaikan Pesanan Gagal")
                                    .create()
                                    .show();
                        }
                    }
                };
                PesananSelesaiRequest pesananSelesaiRequest = new PesananSelesaiRequest(idPesananTextView.getText().toString(),responseListener);
                ApplicationVolley.getInstance().getRequestQueue().add(pesananSelesaiRequest);
            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();

        //TODO refresh data
        fetchPesanan();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //view = inflater.inflate(R.layout.fragment_order,container,false);
        LayoutInflater lf = getActivity().getLayoutInflater();
        view =  lf.inflate(R.layout.fragment_order, container, false); //pass the correct layout name for the fragment

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

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
                        //tanggalPesananTextView.setText(jsonResponse.getString("tanggalPesanan"));

                        //TODO parsing JSON String to date then to string with our own format
                        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                        Date date = inFormat.parse(jsonResponse.getString("tanggalPesanan"));
                        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMM yyyy' at 'HH:mm");

                        //System.out.println(DATE_FORMAT.format(date));
                        tanggalPesananTextView.setText(DATE_FORMAT.format(date));
                        consts.setVisibility(View.VISIBLE);

                        //System.out.println("fetch reponse id = " + String.valueOf(jsonResponse.getInt("id")));
                    }
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                    builder1.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();

                        }
                    });
                    builder1.setMessage("Pesanan Kosong")
                            .create()
                            .show();
                } catch (ParseException e){}
            }
        };
        Log.d(TAG, String.valueOf(currentUserId));
        PesananFetchRequest pesananFetchRequest = new PesananFetchRequest(currentUserId,responseListener);
        ApplicationVolley.getInstance().getRequestQueue().add(pesananFetchRequest);
    }
}
