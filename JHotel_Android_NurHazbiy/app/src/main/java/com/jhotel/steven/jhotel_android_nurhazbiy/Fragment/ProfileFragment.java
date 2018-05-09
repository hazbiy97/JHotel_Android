package com.jhotel.steven.jhotel_android_nurhazbiy.Fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
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
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.jhotel.steven.jhotel_android_nurhazbiy.APIRequest.ApplicationVolley;
import com.jhotel.steven.jhotel_android_nurhazbiy.APIRequest.HistoryRequest;
import com.jhotel.steven.jhotel_android_nurhazbiy.APIRequest.PesananBatalRequest;
import com.jhotel.steven.jhotel_android_nurhazbiy.APIRequest.PesananFetchRequest;
import com.jhotel.steven.jhotel_android_nurhazbiy.APIRequest.PesananSelesaiRequest;
import com.jhotel.steven.jhotel_android_nurhazbiy.APIRequest.ProfileRequest;
import com.jhotel.steven.jhotel_android_nurhazbiy.Object.Customer;
import com.jhotel.steven.jhotel_android_nurhazbiy.Object.Room;
import com.jhotel.steven.jhotel_android_nurhazbiy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.android.volley.VolleyLog.TAG;


public class ProfileFragment extends Fragment {
    private TextView orderCountTextView;
    private TextView statusOrderTextView;
    private TextView profileNameTextView;
    private ListView orderHistoryListView;
    private Button profileSettingButton;
    private int currentUserId;
    private Customer customer;

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        //TODO Get Bundle Extra
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentUserId = bundle.getInt("currentUserId", 0);
        }

        orderCountTextView = (TextView) getView().findViewById(R.id.profile_orderCount);
        statusOrderTextView = (TextView) getView().findViewById(R.id.profile_orderStatus);
        profileNameTextView = (TextView) getView().findViewById(R.id.profile_name);
        orderHistoryListView = (ListView) getView().findViewById(R.id.order_history);
        profileSettingButton = (Button) getView().findViewById(R.id.profile_setting);

        profileSettingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                System.out.println("batal Clicked");
            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();

        //TODO refresh data
        fetchProfile();
        fetchHistory();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private void fetchProfile() {
        Response.Listener<String> profileResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    Log.d(TAG,jsonResponse.toString());
                    if (jsonResponse != null) {
                        String nama = jsonResponse.getString("nama");
                        String email = jsonResponse.getString("nama");
                        String password = jsonResponse.getString("password");
                        //TODO parsing JSON String to date then to string with our own format
                        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                        Date dob = inFormat.parse(jsonResponse.getString("dob"));

                        profileNameTextView.setText(nama);

                        customer = new Customer(currentUserId,nama,email,dob,password);
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
        ProfileRequest profileRequest = new ProfileRequest(currentUserId,profileResponseListener);
        ApplicationVolley.getInstance().getRequestQueue().add(profileRequest);
    }
    private void fetchHistory(){
        Response.Listener<String> profileResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    Log.d(TAG,jsonResponse.toString());
                    if (jsonResponse != null) {
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONObject getResponse = jsonResponse.getJSONObject(i);
                            JSONObject f = jsonResponse.getJSONObject(i);

                            //TODO parsing JSON String to date then to string with our own format
                            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                            Date date = inFormat.parse(f.getString("tanggalPesanan"));

                            int id = f.getInt("id");
                            int biaya = f.getInt("biaya");
                            int jumlahHari = f.getInt("jumlahHari");;
                            boolean statusAktif = f.getBoolean("statusAktif");
                            boolean statusDiproses = f.getBoolean("statusDiproses");
                            boolean statusSelesai = f.getBoolean("statusSelesai");
                        }
                        orderCountTextView.setText(String.valueOf(jsonResponse.length()));
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
        HistoryRequest historyRequest = new HistoryRequest(currentUserId,profileResponseListener);
        ApplicationVolley.getInstance().getRequestQueue().add(historyRequest);
    }
}
