package com.jhotel.steven.jhotel_android_nurhazbiy.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.jhotel.steven.jhotel_android_nurhazbiy.APIRequest.BuatPesananRequest;
import com.jhotel.steven.jhotel_android_nurhazbiy.R;

import org.json.JSONException;
import org.json.JSONObject;

public class BuatPesananActivity extends AppCompatActivity {
    private int currentUserId, banyakHari ,idHotel;
    private double tariff;
    private String roomNumber;
    private String roomType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);

        //getIntent
        Bundle extra = getIntent().getExtras();

        idHotel = extra.getInt("id_hotel",0);
        currentUserId = extra.getInt("currentUserId",0);
        roomNumber = extra.getString("room_number");
        tariff = extra.getDouble("daily_tariff", 0);
        roomType = extra.getString("room_type");

        final TextView room_numberTextView = (TextView) findViewById(R.id.room_number);
        final TextView tipe_kamarTextView = (TextView) findViewById(R.id.room_type);
        final TextView tariffTextView = (TextView) findViewById(R.id.tariff);
        final EditText durasi_hariEditText = (EditText) findViewById(R.id.durasi_hari);
        final TextView total_biayaTextView = (TextView) findViewById(R.id.total_biaya);
        final Button hitungButton = (Button) findViewById(R.id.hitung);
        final Button pesanButton = (Button) findViewById(R.id.pesan);

        pesanButton.setVisibility(View.GONE);
        room_numberTextView.setText(roomNumber);
        tariffTextView.setText(String.valueOf(tariff));
        total_biayaTextView.setText("0");
        tipe_kamarTextView.setText(roomType);

        hitungButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                banyakHari = Integer.parseInt(durasi_hariEditText.getText().toString());
                total_biayaTextView.setText(String.valueOf(tariff * banyakHari));
                hitungButton.setVisibility(View.GONE);
                pesanButton.setVisibility(View.VISIBLE);
            }
        });

        pesanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            if (jsonResponse != null) {
                                AlertDialog.Builder builder1 = new AlertDialog.Builder(BuatPesananActivity.this);
                                builder1.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();

                                        //TODO finish activity and change activity from stack
                                        finish();
                                    }
                                });
                                builder1.setMessage("Booking success")
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            System.out.println(e.getMessage());
                            final AlertDialog.Builder builder1 = new AlertDialog.Builder(BuatPesananActivity.this);
                            builder1.setNeutralButton("Dismiss", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder1.setMessage("Booking failed!\nError Occurred")
                                    .create()
                                    .show();
                        }
                    }
                };
                System.out.println(banyakHari +" "+ currentUserId +" "+  idHotel +" "+  roomNumber);
                BuatPesananRequest buatPesananRequest = new BuatPesananRequest(banyakHari, currentUserId, idHotel, roomNumber, responseListener);
                RequestQueue queue = Volley.newRequestQueue(BuatPesananActivity.this);
                queue.add(buatPesananRequest);
            }
        });
    }
}
