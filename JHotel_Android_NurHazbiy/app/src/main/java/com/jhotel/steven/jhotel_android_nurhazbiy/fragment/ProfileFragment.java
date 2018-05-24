package com.jhotel.steven.jhotel_android_nurhazbiy.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
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
import com.jhotel.steven.jhotel_android_nurhazbiy.activities.MainActivity;
import com.jhotel.steven.jhotel_android_nurhazbiy.adapter.SessionManager;
import com.jhotel.steven.jhotel_android_nurhazbiy.apirequest.ApplicationVolley;
import com.jhotel.steven.jhotel_android_nurhazbiy.apirequest.HistoryRequest;
import com.jhotel.steven.jhotel_android_nurhazbiy.apirequest.ProfileRequest;
import com.jhotel.steven.jhotel_android_nurhazbiy.apirequest.RequestErrorListener;
import com.jhotel.steven.jhotel_android_nurhazbiy.object.Customer;
import com.jhotel.steven.jhotel_android_nurhazbiy.object.Hotel;
import com.jhotel.steven.jhotel_android_nurhazbiy.object.Lokasi;
import com.jhotel.steven.jhotel_android_nurhazbiy.object.Pesanan;
import com.jhotel.steven.jhotel_android_nurhazbiy.object.Room;
import com.jhotel.steven.jhotel_android_nurhazbiy.R;
import com.jhotel.steven.jhotel_android_nurhazbiy.adapter.ProfileListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.android.volley.VolleyLog.TAG;

/**
 *  This class is used for creating Profile Fragment in Main Activity
 *
 *  @author Nur Hazbiy Shaffan
 *  @version 1.0.0
 *  @since May 24 2018
 **/
public class ProfileFragment extends Fragment {
    private TextView orderCountTextView;
    private TextView statusOrderTextView;
    private TextView profileNameTextView;
    private ListView orderHistoryListView;
    private Button profileSettingButton;
    private int currentUserId;
    private Customer customer;
    private ArrayList<Pesanan> listPesanan = new ArrayList<>();
    private View view;

    // Session Manager Class
    private SessionManager session;

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

        // Session class instance
        session = ((MainActivity)getActivity()).getSession();

        // Get Bundle Extra
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentUserId = bundle.getInt("currentUserId", 0);
        }

        orderCountTextView = (TextView) view.findViewById(R.id.profile_orderCount);
        statusOrderTextView = (TextView) view.findViewById(R.id.profile_orderStatus);
        profileNameTextView = (TextView) view.findViewById(R.id.profile_name);
        orderHistoryListView = (ListView) view.findViewById(R.id.order_history);
        profileSettingButton = (Button) view.findViewById(R.id.profile_setting);

        profileSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
            }
        });
    }

    /**
     * Refresh lists everytime fragment is resumed
     */
    @Override
    public void onResume(){
        super.onResume();

        // refresh data
        fetchProfile();
        fetchHistory();
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
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        return view;
    }

    /**
     * Fetching profile data method
     */
    private void fetchProfile() {
        Response.Listener<String> profileResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response.length() != 0) {
                        JSONObject jsonResponse = new JSONObject(response);
                        Log.d(TAG,jsonResponse.toString());

                        String nama = jsonResponse.getString("nama");
                        String email = jsonResponse.getString("email");
                        String password = jsonResponse.getString("password");

                        // parsing JSON String to date then to string with our own format
                        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                        Date dob = inFormat.parse(jsonResponse.getString("dob"));

                        profileNameTextView.setText(nama);

                        customer = new Customer(currentUserId,nama,email,dob,password);
                    }
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                } catch (ParseException e){}
            }
        };
        ProfileRequest profileRequest = new ProfileRequest(currentUserId,profileResponseListener,new RequestErrorListener("Profile fetching failed", "Check your internet connection",getContext()));
        ApplicationVolley.getInstance().getRequestQueue().add(profileRequest);
    }

    /**
     * Fetching history data
     */
    private void fetchHistory(){
        Response.Listener<String> profileResponseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                listPesanan = new ArrayList<>();
                TextView emptyText = (TextView) view.findViewById(R.id.order_historyEmpty);
                emptyText.setVisibility(View.GONE);
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    Log.d(TAG,jsonResponse.toString());
                    if (jsonResponse != null) {
                        for (int i = 0; i < jsonResponse.length(); i++) {
                            JSONObject jsonResponseJSONObject = jsonResponse.getJSONObject(i);
                            JSONObject roomJSONObject = jsonResponseJSONObject.getJSONObject("room");
                            JSONObject hotelJSONObject = roomJSONObject.getJSONObject("hotel");
                            JSONObject lokasi = hotelJSONObject.getJSONObject("lokasi");

                            // parsing JSON String to date then to string with our own format
                            SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
                            Date tanggalPesanan = inFormat.parse(jsonResponseJSONObject.getString("tanggalPesanan"));

                            // JSON mapping pesanan
                            int pesananId = jsonResponseJSONObject.getInt("id");
                            int biaya = jsonResponseJSONObject.getInt("biaya");
                            int jumlahHari = jsonResponseJSONObject.getInt("jumlahHari");;
                            boolean statusAktif = jsonResponseJSONObject.getBoolean("statusAktif");
                            boolean statusDiproses = jsonResponseJSONObject.getBoolean("statusDiproses");
                            boolean statusSelesai = jsonResponseJSONObject.getBoolean("statusSelesai");

                            // JSON mapping Room
                            String nomorKamar = roomJSONObject.getString("nomorKamar");
                            String statusKamar = roomJSONObject.getString("statusKamar");
                            double dailyTariff = roomJSONObject.getDouble("dailyTariff");
                            String tipeKamar = roomJSONObject.getString("tipeKamar");

                            // JSON mapping Hotel
                            float x_coord = BigDecimal.valueOf(lokasi.getDouble("x")).floatValue();
                            float y_coord = BigDecimal.valueOf(lokasi.getDouble("y")).floatValue();
                            String description = lokasi.getString("deskripsi");
                            int hotelId = hotelJSONObject.getInt("id");
                            String nama = hotelJSONObject.getString("nama");
                            int bintang = hotelJSONObject.getInt("bintang");

                            Hotel hotel = new Hotel(hotelId, nama, new Lokasi(x_coord, y_coord, description), bintang);
                            Room room = new Room(hotel, nomorKamar, statusKamar, dailyTariff, tipeKamar);
                            Pesanan pesanan = new Pesanan(pesananId,biaya,jumlahHari,tanggalPesanan, statusAktif,statusDiproses,statusSelesai,room);
                            listPesanan.add(pesanan);
                        }
                        orderCountTextView.setText(String.valueOf(jsonResponse.length()));

                        // set latest order status
                        if(jsonResponse.length() != 0) {
                            String order_status = null;
                            boolean isDiproses = listPesanan.get(jsonResponse.length() - 1).isStatusDiproses();
                            boolean isSelesai = listPesanan.get(jsonResponse.length() - 1).isStatusSelesai();

                            if (isDiproses && !isSelesai) {
                                order_status = "PROCESSING";
                                statusOrderTextView.setTextColor(getResources().getColor(R.color.colorProcessed));
                            } else if (!isDiproses && !isSelesai) {
                                order_status = "CANCELLED";
                                statusOrderTextView.setTextColor(getResources().getColor(R.color.colorCancelled));
                            } else {
                                order_status = "FINISHED";
                                statusOrderTextView.setTextColor(getResources().getColor(R.color.colorActive));
                            }
                            statusOrderTextView.setText(order_status);
                        }

                        ProfileListAdapter profileListAdapter = new ProfileListAdapter(getActivity(), listPesanan);
                        orderHistoryListView.setAdapter(profileListAdapter);

                        if(listPesanan.size() == 0){
                            String noHistory = "No history yet";
                            emptyText.setHint(noHistory);
                            emptyText.setVisibility(View.VISIBLE);
                            statusOrderTextView.setText("-");
                            //orderHistoryListView.setEmptyView(emptyText);
                        }
                    }
                } catch (JSONException ex) {
                    System.out.println(ex.getMessage());
                } catch (ParseException e){}
            }
        };

        // set request
        HistoryRequest historyRequest = new HistoryRequest(currentUserId,profileResponseListener,new RequestErrorListener("History fetching failed", "Check your internet connection",getContext()));
        ApplicationVolley.getInstance().getRequestQueue().add(historyRequest);
    }
}
